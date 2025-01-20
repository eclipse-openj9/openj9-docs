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

NAMESPACE = 'eclipseopenj9'
CONTAINER_NAME = 'openj9-docs'
ARCHIVE = 'doc.tar.gz'
HTTP = 'https://'
OPENJ9_REPO = 'github.com/eclipse-openj9/openj9-docs'
OPENJ9_STAGING_REPO = 'github.com/eclipse-openj9/openj9-docs-staging'
OPENJ9_WEBSITE_REPO = 'github.com/eclipse-openj9/openj9-website-publish'

BUILD_DIR = 'built_doc'
CREDENTIAL_ID = 'github-bot'

switch (params.BUILD_TYPE) {
    case "MERGE":
        PUSH_REPO = OPENJ9_REPO
        PUSH_BRANCH = 'gh-pages'
        REFSPEC = "+refs/heads/*:refs/remotes/origin/*"
        // MERGE_COMMIT will be set by the webhook
        // unless launched manually, then we will need to figure it out
        if (params.MERGE_COMMIT) {
            CLONE_BRANCH = MERGE_COMMIT
            GET_SHA = false
        } else {
            CLONE_BRANCH = "refs/heads/master"
            GET_SHA = true
        }
        SERVER = 'Github'
        break
    case "PR":
        PUSH_REPO = OPENJ9_STAGING_REPO
        PUSH_BRANCH = 'master'
        REFSPEC = "+refs/pull/${ghprbPullId}/merge:refs/remotes/origin/pr/${ghprbPullId}/merge"
        GET_SHA = false
        CLONE_BRANCH = sha1
        MERGE_COMMIT = ghprbActualCommit
        break
    case "RELEASE":
        PUSH_REPO = OPENJ9_WEBSITE_REPO
        PUSH_BRANCH = 'main'
        REFSPEC = "+refs/heads/*:refs/remotes/origin/*"
        GET_SHA = true
        if (!params.RELEASE_BRANCH) {
            error("Must specify what release branch to push to the web site")
        }
        CLONE_BRANCH = "refs/heads/${RELEASE_BRANCH}"
        SERVER = 'Github'
        ZIP_FILENAME="${RELEASE_BRANCH}.zip"
        break
    default:
        error("Unknown BUILD_TYPE:'${params.BUILD_TYPE}'")
        break
}

timeout(time: 6, unit: 'HOURS') {
    timestamps {
        node('hw.arch.x86&&sw.tool.docker&&sw.os.cent') {
            try {
                def TMP_DESC = (currentBuild.description) ? currentBuild.description + "<br>" : ""
                currentBuild.description = TMP_DESC + "<a href=${JENKINS_URL}computer/${NODE_NAME}>${NODE_NAME}</a>"

                docker.image("${NAMESPACE}/${CONTAINER_NAME}:latest").pull()
                docker.image("${NAMESPACE}/${CONTAINER_NAME}:latest").inside() {
                    stage('Build Doc') {
                        dir(BUILD_DIR) {
                            checkout changelog: false, poll: false,
                                scm: [$class: 'GitSCM',
                                    branches: [[name: CLONE_BRANCH]],
                                    extensions: [
                                        [$class: 'CheckoutOption', timeout: 30],
                                        [$class: 'CloneOption', timeout: 30]
                                    ],
                                    userRemoteConfigs: [[refspec: REFSPEC, url: "${HTTP}${OPENJ9_REPO}"]]]

                            if (GET_SHA) {
                                MERGE_COMMIT = sh (script: 'git rev-parse HEAD', returnStdout: true).trim()
                            }
                            COMMIT_MSG = sh (script: 'git log -1 --oneline', returnStdout: true).trim()
                            echo "COMMIT_MSG:'${COMMIT_MSG}'"
                            echo "COMMIT_SHA:'${MERGE_COMMIT}'"

                            if (!params.ghprbPullId) {
                                // Set status on the Github commit for merge builds
                                setBuildStatus("${HTTP}${OPENJ9_REPO}", MERGE_COMMIT, 'PENDING', 'In Progress')
                                currentBuild.description += "<br>${COMMIT_MSG}"
                            }

                            // Need to insert website banners to identify the documentation
                            if (params.ghprbPullId) {
                                // If a pull request, use staging site banner
                                sh """
                                    cp /myenv/lib/python3.10/site-packages/material/templates/base.html theme/base.html
                                    sed -i 's|{% block hero %}|<!--Staging site notice --><div class="md-container"><div style="margin:-2rem 0 -2rem 0; background: linear-gradient(white, #69c1bd, white); color:white; text-align:center; font-size:1.5rem; font-weight:bold; text-shadow: 2px 2px 4px #000000;"><p style="padding:2rem 0 2rem 0;"><i class="fa fa-exclamation-triangle" aria-hidden="true"></i> CAUTION: This site is for reviewing draft documentation. For published content, visit <a style="color:#af6e3d; text-shadow:none; padding-left:1rem;" href="http://www.eclipse.org/openj9/docs/index.html">www.eclipse.org/openj9/docs</a></p></div>{% block hero %}|' theme/base.html
                                    """
                            }
                            else if (params.BUILD_TYPE != "RELEASE") {
                                // Otherwise, and if this isn't for a release, use ghpages site banner
                                sh """
                                    cp /myenv/lib/python3.10/site-packages/material/templates/base.html theme/base.html
                                    sed -i 's|{% block hero %}|<!--Ghpages site notice --><div class="md-container"><div style="margin:-2rem 0 -2rem 0; background: linear-gradient(white, #ffa02e, white); color:white; text-align:center; font-size:1.5rem; font-weight:bold; text-shadow: 2px 2px 4px #000000;"><p style="padding:2rem 0 2rem 0;"><i class="fa fa-cogs" aria-hidden="true"></i> CAUTION: This site hosts draft documentation for the next release. For published content of the latest release, visit <a style="color:#af6e3d; text-shadow:none; padding-left:1rem;" href="http://www.eclipse.org/openj9/docs/index.html">www.eclipse.org/openj9/docs</a></p></div>{% block hero %}|' theme/base.html
                                    """
                            }

                            sh 'mkdocs build -v'
                        }
                    }
                    // If we're pushing to Github, use https user/password
                    // If we're pushing to Eclipse, use eclipse ssh key
                    if ((params.BUILD_TYPE == "PR") || (params.BUILD_TYPE == "MERGE")) {
                        stage("Push Doc") {
                            dir('push_repo') {
                                // avoid "fatal: unable to create threaded lstat" from git status
                                sh """
                                    git config --global core.preloadIndex false
                                    """
                                checkout changelog: false, poll: false,
                                    scm: [$class: 'GitSCM',
                                        branches: [[name: PUSH_BRANCH]],
                                        extensions: [
                                            [$class: 'CheckoutOption', timeout: 30],
                                            [$class: 'CloneOption', timeout: 30],
                                            [$class: 'LocalBranch', localBranch: PUSH_BRANCH]
                                        ],
                                        userRemoteConfigs: [[url: "${HTTP}${PUSH_REPO}"]]]
                                if (params.BUILD_TYPE == "PR") {
                                    dir("${ghprbPullId}") {
                                        copy_built_doc(BUILD_DIR)
                                        push_doc_with_cred(PUSH_REPO, PUSH_BRANCH, "Generated from commit: ${MERGE_COMMIT}")
                                    }
                                } else {
                                    copy_built_doc(BUILD_DIR)
                                    push_doc_with_cred(PUSH_REPO, PUSH_BRANCH, "Generated from commit: ${MERGE_COMMIT}")
                                    // Set status on the Github commit for merge builds
                                    setBuildStatus("${HTTP}${OPENJ9_REPO}", MERGE_COMMIT, 'SUCCESS', "Doc built and pushed to ${SERVER} openj9-docs:${PUSH_BRANCH}")
                                }
                            }
                        }
                    } else { // RELEASE
                        dir(BUILD_DIR) {
                            sh "tar -zcf ${ARCHIVE} site/"
                            stash includes: "${ARCHIVE}", name: 'doc'

                            // Cleanup and rebuild the Doc for push to Github repo as a downlaodable zip.
                            // Build, save zip file, cleanup and checkout master branch, bring back zip file and commit/push.
                            sh """
                                git clean -ffxd
                                git status
                                sed -i "s|site_dir: 'site'|use_directory_urls: false\\nsite_dir: 'site'|" mkdocs.yml
                                sed -i "/- search/d" mkdocs.yml
                                mkdocs build -v
                                cd site
                                zip -r ${ZIP_FILENAME} *
                                mv ${ZIP_FILENAME} ${WORKSPACE}/
                                cd ..
                                git clean -ffxd
                                git reset --hard
                                git status
                                git checkout master
                                mv ${WORKSPACE}/${ZIP_FILENAME} downloads/
                            """
                            push_doc_with_cred(OPENJ9_REPO, 'master', "Add zip download of ${RELEASE_BRANCH} release user documentation")
                        }
                    }
                } // Exit container

                if (params.BUILD_TYPE == "RELEASE") {
                    stage("Push Doc") {
                        dir(BUILD_DIR) {
                            unstash 'doc'
                            sh "tar -zxf ${ARCHIVE}"
                        }
                        dir('eclipse') {
                            git branch: PUSH_BRANCH, url: "${HTTP}${PUSH_REPO}", credentialsId: CREDENTIAL_ID
                            dir('docs') {
                                copy_built_doc(BUILD_DIR)
                                push_doc_with_cred(PUSH_REPO, PUSH_BRANCH, "Generated from commit: ${MERGE_COMMIT}")
                            }
                        }
                        // Set status on the Github commit for release builds
                        setBuildStatus("${HTTP}${OPENJ9_REPO}", MERGE_COMMIT, 'SUCCESS', "Doc built and pushed to ${SERVER} openj9-docs:${PUSH_BRANCH}")
                    }
                }
            } catch(e) {
                if (!params.ghprbPullId) {
                    // Set status on the Github commit for non PR builds
                    setBuildStatus("${HTTP}${OPENJ9_REPO}", MERGE_COMMIT, 'FAILURE', 'Failed to build and push doc')
                    slackSend channel: '#jenkins', color: 'danger', message: "Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)"
                }
                throw e
            } finally {
                cleanWs()
            }
        }
    }
}

def copy_built_doc(DIR) {
    sh "rm -rf *"
    sh "cp -r ${WORKSPACE}/${DIR}/site/* ."
}

def push_doc_with_cred(REPO, BRANCH, MESSAGE){
    withCredentials([usernamePassword(credentialsId: CREDENTIAL_ID, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
        push_doc("${HTTP}${USERNAME}:${PASSWORD}@${REPO}", BRANCH, MESSAGE)
    }
}
def push_doc(REPO, BRANCH, MESSAGE) {
    sh "git status"
    STATUS = sh (script: 'git status --porcelain', returnStdout: true).trim()
    if ("x${STATUS}" != "x") {
        sh 'git config user.name "genie-openj9"'
        sh 'git config user.email "openj9-bot@eclipse.org"'
        sh 'git add .'
        sh "git commit -sm '${MESSAGE}'"
        sh "git push ${REPO} ${BRANCH}"
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
