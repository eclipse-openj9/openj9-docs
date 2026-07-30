# Copyright (c) 2017, 2026 IBM Corp. and others
#
# This program and the accompanying materials are made
# available under the terms of the Eclipse Public License 2.0
# which accompanies this distribution and is available at
# https://www.eclipse.org/legal/epl-2.0/ or the Apache
# License, Version 2.0 which accompanies this distribution and
# is available at https://www.apache.org/licenses/LICENSE-2.0.
#
# This Source Code may also be made available under the
# following Secondary Licenses when the conditions for such
# availability set forth in the Eclipse Public License, v. 2.0
# are satisfied: GNU General Public License, version 2 with
# the GNU Classpath Exception [1] and GNU General Public
# License, version 2 with the OpenJDK Assembly Exception [2].
#
# [1] https://www.gnu.org/software/classpath/license.html
# [2] https://openjdk.org/legal/assembly-exception.html
#
# SPDX-License-Identifier: EPL-2.0 OR Apache-2.0 OR GPL-2.0-only WITH Classpath-exception-2.0 OR GPL-2.0-only WITH OpenJDK-assembly-exception-1.0
#

FROM public.ecr.aws/lts/ubuntu:24.04_stable@sha256:22a8228e1e48cbe7e0e0f2056e752ffb8a35950cda150a4e5e16417200bec648
MAINTAINER openj9.bot <openj9.bot@gmail.com>

# Install basic requirementes from APT. Set up for SSH (including SSH login fix to prevent user being logged out), install and setup  virtualenv from pip3
COPY requirements.* /tmp/
RUN echo "UTC" > /etc/timezone \
    && apt-get update && apt-get install -y --no-install-recommends python3 python3-dev python3-pip python3-virtualenv git openjdk-11-jdk zip \
    && DEBIAN_FRONTEND="noninteractive" apt-get -q upgrade -y -o Dpkg::Options::="--force-confnew" --no-install-recommends \
    && DEBIAN_FRONTEND="noninteractive" apt-get -q install -y -o Dpkg::Options::="--force-confnew" --no-install-recommends openssh-server \
    && rm -rf /var/lib/apt/lists/* \
    && sed -i 's|session    required     pam_loginuid.so|session    optional     pam_loginuid.so|g' /etc/pam.d/sshd \
    && mkdir -p /var/run/sshd \
    && virtualenv myenv -p python3
# Update LANG to fix error with pip-tools
ENV LANG=C.UTF-8
# Update path to include the virtual env install location
ENV PATH=/myenv/bin:$PATH
# Activate the virtualenv environment, install build and test tools, create a new dependency list, and change permissions to ensure that Jenkins modify packages where necessary
RUN /bin/bash -c "source /myenv/bin/activate && pip3 install pip pip-tools setuptools && pip3 install --requirement /tmp/requirements.txt && mv /tmp/requirements.txt /tmp/requirements.orig && pip-compile /tmp/requirements.in && chmod -R ugo+w /myenv"
# Setup jenkins user
RUN userdel -r ubuntu \
    && groupadd -g 1000 jenkins \
    && useradd -u 1000 -g 1000 -m -d /home/jenkins -s /bin/sh jenkins \
    && echo "jenkins:jenkinspass" | chpasswd
# Standard SSH port
EXPOSE 22
# Default command
CMD ["/usr/sbin/sshd", "-D"]
# Create and set a working directory /docs
RUN mkdir /docs
WORKDIR /docs
