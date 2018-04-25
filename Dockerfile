# Copyright (c) 2017, 2018 IBM Corp.
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
# [2] http://openjdk.java.net/legal/assembly-exception.html
#
# SPDX-License-Identifier: EPL-2.0 OR Apache-2.0 OR GPL-2.0 WITH
# Classpath-exception-2.0 OR LicenseRef-GPL-2.0 WITH Assembly-exception


FROM ubuntu:16.04
MAINTAINER openj9.bot <openj9.bot@gmail.com> 

# Set up for SSH (including SSH login fix to prevent user being logged out) and add PIP 
RUN apt-get update && apt-get install -y --no-install-recommends python3 python3-pip python-setuptools git openjdk-8-jdk \
    && DEBIAN_FRONTEND="noninteractive" apt-get -q upgrade -y -o Dpkg::Options::="--force-confnew" --no-install-recommends \
    && DEBIAN_FRONTEND="noninteractive" apt-get -q install -y -o Dpkg::Options::="--force-confnew" --no-install-recommends openssh-server \
    && rm -rf /var/lib/apt/lists/* \
    && pip3 install --upgrade pip \
    && python3 -m pip install -U setuptools \
    && sed -i 's|session    required     pam_loginuid.so|session    optional     pam_loginuid.so|g' /etc/pam.d/sshd \
    && mkdir -p /var/run/sshd

# Add MkDocs and dependencies
COPY requirements.txt /tmp/
RUN pip3 install --requirement /tmp/requirements.txt
COPY . /tmp/

# Setup jenkins user
RUN  useradd -m -d /home/jenkins -s /bin/sh jenkins \
    && echo "jenkins:jenkinspass" | chpasswd

ENV LC_ALL=C.UTF-8 \
    LANG=C.UTF-8

# Standard SSH port
EXPOSE 22

# Deafult command
CMD ["/usr/sbin/sshd", "-D"]

# Create and set a working directory /docs
RUN mkdir /docs
WORKDIR /docs
