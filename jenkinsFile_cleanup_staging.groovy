/*******************************************************************************
 * Copyright (c) 2017, 2025 IBM Corp. and others
 *
 * This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License 2.0 which accompanies this
 * distribution and is available at https://www.eclipse.org/legal/epl-2.0/
 * or the Apache License, Version 2.0 which accompanies this distribution and
 * is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * This Source Code may also be made available under the following
 * Secondary Licenses when the conditions for such availability set
 * forth in the Eclipse Public License, v. 2.0 are satisfied: GNU
 * General Public License, version 2 with the GNU Classpath
 * Exception [1] and GNU General Public License, version 2 with the
 * OpenJDK Assembly Exception [2].
 *
 * [1] https://www.gnu.org/software/classpath/license.html
 * [2] https://openjdk.org/legal/assembly-exception.html
 *
 * SPDX-License-Identifier: EPL-2.0 OR Apache-2.0 OR GPL-2.0-only WITH Classpath-exception-2.0 OR GPL-2.0-only WITH OpenJDK-assembly-exception-1.0
 *******************************************************************************/

import groovy.json.JsonSlurper;

HTTP = 'https://'
OPENJ9_STAGING_REPO = 'github.com/eclipse-openj9/openj9-docs-staging'
CREDENTIAL_ID = 'github-bot'
PUSH_BRANCH = 'master'

def cleanupPrs = []

timeout(time: 6, unit: 'HOURS') {
    timestamps {
        node('worker') {
            try {
                def TMP_DESC = (currentBuild.description) ? currentBuild.description + "<br>" : ""
                currentBuild.description = TMP_DESC + "<a href=${JENKINS_URL}computer/${NODE_NAME}>${NODE_NAME}</a>"

                stage('Clone') {
                    git "${HTTP}${OPENJ9_STAGING_REPO}"
                    sh "git status"
                }

                stage("Cleanup") {
                    def prList = sh (returnStdout: true, script: "find . -maxdepth 1 -type d -printf '%f\n' | grep -v git | grep -v '\\.'").trim()
                    prArray = prList.tokenize('\n')
                    println "PRs currently staged: ${prArray}"

                    withCredentials([usernameColonPassword(credentialsId: CREDENTIAL_ID, variable: 'userpass')]) {
                        for (pr in prArray) {
                            retry(3) {
                                def prJSON = sh (returnStdout: true, script: "curl -u ${userpass} https://api.github.com/repos/eclipse-openj9/openj9-docs/pulls/${pr} 2>/dev/null").trim()
                                def slurper = new groovy.json.JsonSlurper()
                                def prInfo = slurper.parseText(prJSON)
                                def state = prInfo.state
                                switch (state) {
                                    case "open":
                                        println "PR '${pr}' is open."
                                        break
                                    case "closed":
                                        println "PR '${pr}' is closed."
                                        sh "git rm -rq ${pr}"
                                        cleanupPrs.add(pr)
                                        break
                                    default:
                                        error "PR '${pr}' is in unknown state '${state}."
                                }
                            }
                        }
                    }
                }

                stage ('Push') {
                    if (cleanupPrs) {
                        withCredentials([usernamePassword(credentialsId: CREDENTIAL_ID, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                            def message = "Remove PRs that are no longer active\n${cleanupPrs}"
                            sh "git commit -qsm '${message}'"
                            sh "git push ${HTTP}${USERNAME}:${PASSWORD}@${OPENJ9_STAGING_REPO} ${PUSH_BRANCH}"
                            currentBuild.description += "<br>${cleanupPrs}"
                        }
                    } else {
                        println "Nothing to cleanup"
                    }
                }
            } catch(e) {
                slackSend channel: '#jenkins', color: 'danger', message: "Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)"
                throw e
            } finally {
                cleanWs()
            }
        }
    }
}
