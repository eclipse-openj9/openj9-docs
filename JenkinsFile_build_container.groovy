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

timeout(time: 6, unit: 'HOURS') {
    timestamps {
        node('docker') {
            try {
                stage('Clone') {
                    checkout scm
                }

                stage('Build') {
                    if (params.ghprbPullId) {
                        // Tag PullRequest Containers with a PR in the name
                        TAGS = "-t ${NAMESPACE}/${CONTAINER_NAME}:PR${BUILD_NUMBER}"
                    } else {
                        // Tag Regular build Containers with BUILD_NUMBER and 'latest'
                        TAGS = "-t ${NAMESPACE}/${CONTAINER_NAME}:${BUILD_NUMBER} -t ${NAMESPACE}/${CONTAINER_NAME}:latest"
                    }
                    sh "docker build -f Dockerfile ${TAGS} ."
                }

                if (params.ghprbPullId) {
                    // Archive PullRequest containers to Jenkins for manual download/verification
                    DATE = sh(
                        script: 'date +%Y%m%d_%H%M%S',
                        returnStdout: true
                        ).trim()
                    ARCHIVE_NAME = "Container_PR${ghprbPullId}_${DATE}.tar"
                    stage('Archive') {
                        sh "docker save -o ${WORKSPACE}/${ARCHIVE_NAME} ${NAMESPACE}/${CONTAINER_NAME}:PR${BUILD_NUMBER}"
                        sh "gzip ${ARCHIVE_NAME}"
                        archiveArtifacts "${ARCHIVE_NAME}.gz"
                        echo "Container archived to Jenkins. Download it from the build page here: ${BUILD_URL}artifact/\nTest it out by loading it into Docker\n\$ docker load -i ${ARCHIVE_NAME}.gz"
                    }
                } else {
                    // Push non-PullRequest containers to Dockerhub
                    stage('Push') {
                        withCredentials([usernamePassword(credentialsId: '7fb9f8f0-14bf-469a-9132-91db4dd80c48', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                            sh "docker login --username=\"${USER}\" --password=\"${PASS}\""
                        }
                        sh "docker push ${NAMESPACE}/${CONTAINER_NAME}:${BUILD_NUMBER}"
                        sh "docker push ${NAMESPACE}/${CONTAINER_NAME}:latest"
                        sh 'docker logout'
                    }
                }
            } finally {
                cleanWs()
            }
        }
    }
}
