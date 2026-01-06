/*******************************************************************************
 * Copyright (c) 2017, 2026 IBM Corp. and others
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

timeout(time: 6, unit: 'HOURS') {
    timestamps {
        node('hw.arch.x86&&sw.tool.docker&&!sw.os.ubuntu.18') {
            try {
                stage('Clone') {
                    checkout scm
                }

                stage('Build') {
                    if (params.ghprbPullId) {
                        // Tag PullRequest Containers with a PR in the name
                        TAGS = "-t ${NAMESPACE}/${CONTAINER_NAME}:PR${params.ghprbPullId}"
                    } else {
                        // Tag Regular build Containers with BUILD_NUMBER and 'latest'
                        TAGS = "-t ${NAMESPACE}/${CONTAINER_NAME}:${BUILD_NUMBER} -t ${NAMESPACE}/${CONTAINER_NAME}:latest"
                    }
                    dir('buildenv') {
                        sh "docker build -f Dockerfile ${TAGS} ."
                    }
                }

                // Push container to Dockerhub
                stage('Push') {
                    withCredentials([usernamePassword(credentialsId: 'docker-login', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                        sh "docker login --username=\"${USER}\" --password=\"${PASS}\""
                    }
                    if (params.ghprbPullId) {
                        sh "docker push ${NAMESPACE}/${CONTAINER_NAME}:PR${params.ghprbPullId}"
                    } else {
                        sh "docker push ${NAMESPACE}/${CONTAINER_NAME}:${BUILD_NUMBER}"
                        sh "docker push ${NAMESPACE}/${CONTAINER_NAME}:latest"
                    }
                    sh 'docker logout'
                }
            } finally {
                cleanWs()
            }
        }
    }
}
