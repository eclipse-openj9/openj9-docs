# Testing OpenJ9 MKDocs dependencies

## Before you begin
- You must have access to an Ubuntu system. The production builds run on Ubuntu so test builds must also use that operating system at the same level (see the `FROM ubuntu:` line in the [`Dockerfile`](https://github.com/eclipse-openj9/openj9-docs/blob/master/buildenv/Dockerfile) for the level that is used by the production OpenJ9 doc builds).
- Docker must be installed on the Ubuntu system.

## About this task

OpenJ9 docs are transformed (from Markdown files into html) by using [MkDocs](https://www.mkdocs.org/) (an open source static site generator) with the [Material for MkDocs](https://squidfunk.github.io/mkdocs-material/) theme.

MkDocs and Material for MkDocs come with many dependencies on other software. You can't automatically build with the latest levels of all this software because changes in MkDocs, Material, or a software package on which they depend, can break the OpenJ9 docs. For example, issues with the navigation, duplicate footer images, missing search bar, and icons in topics failing altogether due to the icons being renamed in the FontAwesome package.

The MkDocs [release notes](https://www.mkdocs.org/about/release-notes/) and Material for MkDocs [changelogs](https://squidfunk.github.io/mkdocs-material/changelog/) can be useful in figuring out whether the OpenJ9 docs might be affected by the changes listed.

You must, therefore, test the latest levels every so often (and especially if the automated `dependabot` checker detects a security vulnerability), then "pin" them in a Docker image. This image specifies the software environment - the packages and the specific levels - in which the doc builds are run.

The Docker image is built (by jobs in the Eclipse OpenJ9 Jenkins build server) from the following files in the [openj9-docs repo](https://github.com/eclipse-openj9/openj9-docs/tree/master/buildenv).

- `requirements.in`: This text file contains the high-level list of software packages that you require. For example:

   ```
   # requirements.in
   mkdocs
   mkdocs-material
   mkdocs-macros-plugin
   ```
- `requirements.txt`: This text file lists the software dependencies (and their levels) that the packages that are specified in `requirements.in` require. The file is generated automatically from `requirements.in` by using the python `pip-tools` command. Example snippet:

   ```
   # Mkdocs build dependencies pinned
   #
   markdown==3.4.4
      # via
      #   mkdocs
      #   mkdocs-material
      #   pymdown-extensions
   markupsafe==2.1.1
      # via
      #   jinja2
      #   mkdocs
   mergedeep==1.3.4
      # via mkdocs
   mkdocs==1.5.3
      # via
      #   -r requirements.in
      #   mkdocs-macros-plugin
      #   mkdocs-material
   mkdocs-macros-plugin==1.0.4
      # via -r requirements.in
   mkdocs-material==9.3.2
      # via -r requirements.in
   ```
- `Dockerfile`: This standard Docker artifact contains the instructions that define the Docker image. These instructions include commands that install software (by using `requirements.txt`, among other things), create directories, move files around, and so on, so that a Docker container that is created from the image has all the packages and configuration that is required to build the OpenJ9 docs.

## Updating the list of dependencies

1. Get the `requirements.in` file from the [OpenJ9 repo](https://github.com/eclipse-openj9/openj9-docs/blob/master/buildenv/requirements.in) and save it in a directory.
2. Ensure that you have [`pip-tools`](https://pypi.org/project/pip-tools/) installed on your test Ubuntu system. If not, install it by running the following commands:

   ```
   sudo apt install python3-pip
   ```
   (Uses the `apt` package manager to install Python 3.)

   ```
   pip install --upgrade pip
   ```
   (Upgrades `pip`, the Python package manager.)

   ```
   pip install pip-tools
   ```
   (Uses `pip` to install `pip-tools`.)

3. In the same directory as `requirements.in`, run the following command. This command generates a `requirements.txt` file that lists the latest versions of the software in `requirements.in`, and any dependencies. If the requirements.txt exists because of an earlier dependency update test, delete that file (`rm requirements.txt`) before generating a new one.

   ```
   pip-compile --output-file requirements.txt requirements.in
   ```

   (If `pip-compile` does not work, for example, command not found, try `python3 -m piptools compile` instead.)

**Note:** The operating system (Ubuntu) and level is specified in the `Dockerfile` file, so sometimes that file must be updated too. This update is generally a manual update.

## Updating and running the Docker image

1. Create a Docker image that uses the updated `requirements.txt` file. You have two options:
   - Commit the updated `requirements.txt` file (and `Dockerfile` if necessary) to the openj9-docs repo, then add the comment "Jenkins build container" to the PR. This comment automatically runs the [Build-Doc-Docker_Container](https://openj9-jenkins.osuosl.org/view/Website-Doc/job/Build-Doc-Docker_Container/) Eclipse build (which in turn runs the [JenkinsFile_build_container.groovy](https://github.com/eclipse-openj9/openj9-docs/blob/master/JenkinsFile_build_container.groovy) pipeline script in the openj9-docs repo). The build creates a Docker image then uploads it to the production area on docker hub ([eclipseopenj9/openj9-docs](https://hub.docker.com/r/eclipseopenj9/openj9-docs/tags)) with a tag that matches the PR number. For example, pull request 56 is stored with a tag of `PR56`. Download the image by running the following command:

      ```
      sudo docker pull eclipse/openj9-docs:PR<pr_number>
      ```
   - Use your local `requirements.txt` file (and `Dockerfile` if necessary) to create a Docker image locally:
      1. Create a new, temporary directory (everything in the directory will be included in the Docker image). For example, `/var/lib/docker/test`.
      2. Add to the directory the `requirements.txt` file that you created earlier and the `Dockerfile` and `requirements.in` files from the [OpenJ9 repository](https://github.com/eclipse-openj9/openj9-docs/tree/master/buildenv). (Or your own `Dockerfile` if you updated it.)
      3. From your new directory, run the following command to build the Docker image with a tag of `latest`.
Note that if you upload this image to docker hub (for example, you want to share it with another member of the team), it goes to the test area ([ibmjavaid/mkdocs_test_env](https://hub.docker.com/r/ibmjavaid/mkdocs_test_env/tags)) not the production area that is used by the first option.

         ```
         docker build --no-cache -f Dockerfile -t ibmjavaid/mkdocs_test_env:latest .
         ```

         To check that the image was created, run the `docker images` command then look for the image name in the output. If you get a TLS handshake error, especially in an environment where Docker has worked in the past, try again a bit later.

2. Start a Docker container that uses the image that you created.

   ```
   docker run -v /home/<your_username>:/root/hostdir -it <image> /bin/bash
   ```

   Where `<image>` is either `eclipseopenj9/openj9-docs/PR<pr_number>` if you created the Docker image by using the build automation, or `ibmjavaid/mkdocs_test_env` if you created it locally.

   The `-v` argument maps the `/home/<your_username>` directory to `root/hostdir` in the container. This mapping gives you access to the container from outside - you can put files in `root/hostdir`, then access them from `/home/<your_username>`.

   When the container starts, you get a command prompt inside the container and you are running as the `ROOT` user. By default, you should be in the `/docs` directory (for example,
`root@2ec10af99963:/docs#`) but you can use the `cd` command to move around and check the environment.

   Run the `ls -l` command to list files and look at permissions.
All the packages that were setup by the image are in the `/myenv/lib/python3.x/site-packages directory` (in the virtualenv environment, which is specified in `Dockerfile`; virtualenv isolates the dependencies that you require and prevents conflicts between system and user libraries).

   To stop the container, press **CTRL-D**.
Other useful Docker commands include `docker ps` (to see whether there are any containers already running) and `docker stop <container_id>` (to stop a running container, whose ID can be found from the `docker ps` command).

## Updating the document source files and running an OpenJ9 doc build for MkDocs testing

1. When the Docker container starts, you should be in the `docs` directory by default. Clone the openj9-docs repo there:

   ```
   git clone https://github.com/eclipse-openj9/openj9-docs.git
   ```

2. Update the `mkdocs.yml` file to add `- offline` to the list of plug-ins. This line automatically sets the `use_directory_urls` property to `false` (among other things).

3. Update other source files, if required.
Sometimes, changes to the software levels require corresponding changes in the source files. For example, when the FontAwesome package renamed some icons that were used in OpenJ9 docs, references to those icons were updated in the .md files. You have two options for updating the source files:
   - Update the files, create a PR in the openj9-docs repo, then fetch the PR that contains the changes by running the following commands:

      ```
      cd openj9-docs
      ```
      ```
      git fetch origin pull/<pr_number>/head
      ```
      ```
      git checkout -b pullrequest FETCH_HEAD
      ```
    - Update the files directly in the cloned repository. Use this option if you don't want to update the repository for some reason. You can use the mapping directory (which you specified as the `-v` parameter when you started the Docker container) to copy files into the running container from outside.

4. Copy the base theme file from mkdocs-material into the docs build to confirm the current location of the base theme file:

   ```
   cp /myenv/lib/python3.10/site-packages/material/templates/base.html theme/base.html
   ```

5. From the `docs/openj9-docs` directory, run an MkDocs build with verbose output:

   ```
   mkdocs build -v
   ```

6. Check the console output. If the build is successful, copy the resulting `site` folder out of the container so that you can check it elsewhere (the Docker image doesn't include a browser).
Open `site/index.html` in a browser to view the documents. See the [Things to test](mkdocsdependencies.md#things-to-test) section for a list of things to look for when you check the output.

   If the build failed, try to analyze the problem. The Dockerfile might need updating. To analyze the Docker container itself, you might need to run the virtual environment inside the container (see command in [Updating and running the Docker image](mkdocsdependencies.md#updating-and-running-the-docker-image)) which contains libraries that are used to run useful commands, such as `pip3 list`.

7. Remove `- offline` from the `mkdocs.yml` file then build the documents again. This is how the documents are built externally for the [live OpenJ9 docs site](https://eclipse.dev/openj9/docs). This output must be served for the links between topics to work.

      You don't have to retest everything from this build as most things should be the same. The links will be different and search behavior can be different too.

## Things to test

Here's a list (not necessarily exhaustive) of things to check. Compare the test docs to the live ones to spot differences. Some of the items in the list are specific to MkDocs and some to IBM Docs. Issues in the OpenJ9 repo might cover existing problems (for example, with search). Don't forget to check the site appearance on a mobile phone too. You can use the developer tools in your browser to simulate a mobile phone-sized screen.

- Anchor links within a topic
- Links to other OpenJ9 topics
- External links
- Left side table of contents
- Bulleted lists
- Codeph and codeblock-style text (including, in IBM Docs only: copy icon, horizontal scrollbar, and the fancy drop-down sample in the JVMTI topic)
- Content within tables (for example, code, icons)
- Definition lists. The markup for these lists is a colon followed by a space at the beginning of a line in the .md file.
- Trademark symbols
- Italics
- Bold text
- Diagrams
- Java&trade; version icons
- font-awesome icons (they should look fine in OpenJ9 docs and not show at all in IBM Docs)
- API doc
- Headers (not IBM Docs)
- Banner (not IBM Docs) - the orange banner that says "CAUTION: This site hosts draft documentation for the next release..." in the draft OpenJ9 doc site. This banner is inserted as part of the doc build by the [JenkinsFile_build_doc.groovy](https://github.com/eclipse-openj9/openj9-docs/blob/master/JenkinsFile_build_doc.groovy) pipeline script.
- Footer (not IBM Docs)
- Search function (works differently offline, if it's there at all - see https://github.ibm.com/runtimes/idteam/issues/1096#issuecomment-51238210)


## Updating the OpenJ9 docs production environment

When testing is complete, make the changes live:

1. Replace `requirements.txt` (and `Dockerfile` if necessary) in the openj9-docs repository. This replacing might be just merging the relevant PR, if you chose to create test files by updating the repo.
2. Run the [Build-Doc-Docker_Container](https://openj9-jenkins.osuosl.org/job/Build-Doc-Docker_Container/) build in Eclipse OpenJ9 Jenkins to re-create the production Docker image.
This build uses the same pipeline script as mentioned in "Updating and running the Docker image", but with different parameters. The build uses the updated `requirements.txt` (and `Dockerfile` if updated) files from the openj9-docs repo to create the Docker image, then uploads the image to docker hub as [eclipseopenj9/openj9-docs](https://hub.docker.com/r/eclipseopenj9/openj9-docs/tags) with a tag of `latest`.

   Check that the build ran successfully and that the image appears on docker hub. For example, you should see output similar to the following in the console log:
   ```
   11:47:44  + docker push eclipseopenj9/openj9-docs:31
   11:47:44  The push refers to repository [docker.io/eclipseopenj9/openj9-docs]
   ...
   11:49:49  + docker push eclipseopenj9/openj9-docs:latest
   11:49:49  The push refers to repository [docker.io/eclipseopenj9/openj9-docs]
   ```

   When the image is available on docker hub, the [staging](https://openj9-jenkins.osuosl.org/view/Website-Doc/job/Build-Doc-Push_to_ghpages/) and [build](https://openj9-jenkins.osuosl.org/view/Website-Doc/job/Build-Doc-Push_to_Eclipse/) jobs can use it.

3. Update the source files with any fixes that you had to make. Again, you might already have a relevant PR if you chose to create test files by updating the repo. When you add a comment of "Jenkins doc stage", the build should use the new Docker image from docker hub, and any changes that you made should be visible (the aim is for the docs to look the same as before, but if you changed some css, for example, you should be able to see that by using the browser's inspection tools). You can then do a final check and merge the PR.
