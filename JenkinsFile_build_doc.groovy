/*******************************************************************************
 * Copyright (c) 2018, 2018 IBM Corp. and others
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
 * [2] http://openjdk.java.net/legal/assembly-exception.html
 *
 * SPDX-License-Identifier: EPL-2.0 OR Apache-2.0 OR GPL-2.0 WITH
 * Classpath-exception-2.0 OR LicenseRef-GPL-2.0 WITH Assembly-exception
 *******************************************************************************/

NAMESPACE = 'eclipse'
CONTAINER_NAME = 'openj9-docs'
ARCHIVE = 'doc.tar.gz'
OPENJ9_REPO = 'https://github.com/eclipse/openj9-docs'
OPENJ9_STAGING = 'https://github.com/eclipse/openj9-docs-staging'
// ECLIPSE_REPO = 'ssh://genie.openj9@git.eclipse.org:29418/www.eclipse.org/openj9/docs.git'

timeout(time: 6, unit: 'HOURS') {
    timestamps {
        try {
            node('hw.arch.x86&&sw.tool.docker') {
                stage('Build Doc') {
                    docker.image("${NAMESPACE}/${CONTAINER_NAME}:latest").inside {
                        REFSPEC = ''
                        // Target Branch for push
						// SUE: Was BRANCH = 'master' ... we will now push latest doc to ghpages of openj9-docs
                        BRANCH = 'ghpages'
                        if (params.ghprbPullId) {
                            // If building a PullRequest, checkout PR merge commit
                            REFSPEC = "+refs/pull/${ghprbPullId}/merge:refs/remotes/origin/pr/${ghprbPullId}/merge"
                            MERGE_COMMIT = sha1
							// SUE: Was BRANCH = 'staging' ... we will now stage PRs at the openj9-docs-staging:master repo
                            BRANCH = 'master'
                        }
                        // If launched manually, MERGE_COMMIT won't be populated from the webhook so default to master
                        GET_SHA = false
                        if (!MERGE_COMMIT || MERGE_COMMIT == 'master') {
                            MERGE_COMMIT = 'master'
                            GET_SHA = true
                            echo "Manually determine MERGE_COMMIT..."
                        }

                        checkout changelog: false, poll: false,
                            scm: [$class: 'GitSCM',
                                branches: [[name: MERGE_COMMIT]],
                                userRemoteConfigs: [[refspec: REFSPEC, url: OPENJ9_REPO]]]

								if (GET_SHA) {
                            MERGE_COMMIT = sh (script: 'git rev-parse HEAD', returnStdout: true).trim()
                        }
                        COMMIT_MSG = sh (script: 'git log -1 --oneline', returnStdout: true).trim()
                        echo "COMMIT_MSG:'${COMMIT_MSG}'"
                        echo "COMMIT_SHA:'${MERGE_COMMIT}'"

                        if (!params.ghprbPullId) {
                            // Set status on the Github commit for merge builds
                            setBuildStatus(OPENJ9_REPO, MERGE_COMMIT, 'PENDING', 'In Progress')
                            currentBuild.description = "${MERGE_COMMIT.take(8)} - ${COMMIT_MSG}"
                        }
						// Need to insert website banners to identify the documentation

                        if (params.ghprbPullId) {
                            // Staging site banner
                            sh "sed -i 's|{% block hero %}|<!--Staging site notice --><div class="md-container"><div style="margin:-5rem 0 -5rem 0; background: linear-gradient(white, #69c1bd, white); color:white; text-align:center; font-size:1.5rem; font-weight:bold; text-shadow: 2px 2px 4px #000000;"><p style="padding:2rem 0 2rem 0;"><i class="fa fa-exclamation-triangle" aria-hidden="true"></i> CAUTION: This site is for reviewing draft documentation. For published content, visit <a style="color:#af6e3d; text-shadow:none; padding-left:1rem;" href="http://www.eclipse.org/openj9/docs/index.html">www.eclipse.org/openj9/docs</a></p></div>{% block hero %}|' theme/base.html"
                        }
                        else {
                            // Ghpages site banner
                            sh "sed -i 's|{% block hero %}|<!--Ghpages site notice --><div class="md-container"><div style="margin:-5rem 0 -5rem 0; background: linear-gradient(white, #ffa02e, white); color:white; text-align:center; font-size:1.5rem; font-weight:bold; text-shadow: 2px 2px 4px #000000;"><p style="padding:2rem 0 2rem 0;"><i class="fa fa-cogs" aria-hidden="true"></i> CAUTION: This site hosts draft documentation for the next release. For published content of the latest release, visit <a style="color:#af6e3d; text-shadow:none; padding-left:1rem;" href="http://www.eclipse.org/openj9/docs/index.html">www.eclipse.org/openj9/docs</a></p></div>{% block hero %}|' theme/base.html"
                        }
                        sh 'mkdocs build -v'
                        sh "tar -zcf ${ARCHIVE} site/"
                        stash includes: "${ARCHIVE}", name: 'doc'
                    // Exit container, no need to cleanWs()
                    }
                }
            }

            node('master') {
                stage('Push') {
                    try {
                        dir('built_doc') {
                            unstash 'doc'
                            sh "tar -zxf ${ARCHIVE}"
                        }

                        if (params.ghprbPullId) {
                           dir('openj9-staging') {
                                git branch: BRANCH, url: OPENJ9_STAGING
                                // SUE: Was `sh 'rm -rf *'`
                                // SUE: Set the PR directory name and remove any content if it exists & copy the built doc into it
                                dir("${ghprbPullId}") {
                                    deleteDir()
                                    sh "cp -r ${WORKSPACE}/built_doc/site/* ."
                                }
                                sh 'git status'
                                STATUS = sh (script: 'git status --porcelain', returnStdout: true).trim()
                                if ("x${STATUS}" != "x") {
                                    sh 'git add -A'
                                    sh "git commit -m 'Generated from commit: ${MERGE_COMMIT}'"
                                    sh "git push origin ${BRANCH}"
                                }
                            }
                        }

                        if (!params.ghprbPullId) {
                            dir('openj9-ghpages') {
                                git branch: BRANCH, url: OPENJ9_REPO
                                sh 'rm -rf *'
                                sh "cp -r ${WORKSPACE}/built_doc/site/* ."
                                sh 'git status'
                                STATUS = sh (script: 'git status --porcelain', returnStdout: true).trim()
                                if ("x${STATUS}" != "x") {
                                    sh 'git add -A'
                                    sh "git commit -m 'Generated from commit: ${MERGE_COMMIT}'"
                                    sh "git push origin ${BRANCH}"
                                }
                            }
                            // Set status on the Github commit for merge builds
                            setBuildStatus(OPENJ9_REPO, MERGE_COMMIT, 'SUCCESS', 'Doc built and pushed to openj9-docs:ghpages')
                        }
                    } finally {
                        cleanWs()
                    }
                }
            }
        } catch(e) {
            if (!params.ghprbPullId) {
                node('worker') {
                    // Set status on the Github commit for merge builds
                    setBuildStatus(OPENJ9_REPO, MERGE_COMMIT, 'FAILURE', 'Failed to build doc and push to openj9-docs ghpages')
                    slackSend channel: '#jenkins', color: 'danger', message: "Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)"
                }
            }
            throw e
        }
    }
}

def setBuildStatus(REPO, SHA, STATE, MESSAGE) {
    step([
        $class: "GitHubCommitStatusSetter",
        reposSource: [$class: "ManuallyEnteredRepositorySource", url: REPO],
        contextSource: [$class: "ManuallyEnteredCommitContextSource", context: JOB_NAME],
        errorHandlers: [[$class: "ChangingBuildStatusErrorHandler", result: "UNSTABLE"]],
        commitShaSource: [$class: "ManuallyEnteredShaSource", sha: SHA ],
        statusBackrefSource: [$class: "ManuallyEnteredBackrefSource", backref: BUILD_URL],
        statusResultSource: [$class: "ConditionalStatusResultSource", results: [[$class: "AnyBuildResult", message: MESSAGE, state: STATE]] ]
    ]);
}
