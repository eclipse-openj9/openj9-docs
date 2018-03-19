# Working with GitHub

## Initial fork setup (from Joe's notes)

1. Fork the original repo to your own account.

2. In your own FORKED repo, click “Clone or download” and copy https URL. For example:


        git@github.ibm.com:pmhayward/openj9-docs.git

3. On local m/c open command prompt at parent level of where you want your repo, and clone repo:

        git clone <FORKED_REPO_URL>

    For example:

        git clone git@github.ibm.com:pmhayward/openj9-docs.git

4. Change directory into the new local folder that was created by the clone process.

5. Just to be sure:

        git checkout master

6. In the ORIGINAL repo, click “Clone or download” and copy URL.

        git@github.ibm.com:runtimes/openj9-docs.git

7. Create the upstream link between your local repository and the original repository. For example:

        git remote add upstream git@github.ibm.com:runtimes/openj9-docs.git

8. Review setup

        git remote -v 

typical output:

        origin    git@github.ibm.com:pmhayward/openj9-docs.git (fetch)
        origin    git@github.ibm.com:pmhayward/openj9-docs.git (push)
        upstream  git@github.ibm.com:runtimes/openj9-docs.git (fetch)
        upstream  git@github.ibm.com:runtimes/openj9-docs.git (push)

## Standard cycle for making a set of changes:

1. Just to be sure…

        git checkout master

2. Fetch changes from original repo; compares to local fork

        git fetch upstream

3. Merge these changes into local fork

        git merge upstream/master

4. Push the local master to your personal origin master to ensure everything is up to date

        git push

5. If one doesn’t already exist, create an Issue ‘XYZ’ in GitHub with details of the problem you’re going to fix.

6. Create a new branch and switch to it

        git checkout -b <BRANCH_NAME>

7. Create a copy of local branch on the server

        git push origin <BRANCH_NAME>

8. Link the local and remote branches

        git push --set-upstream origin <BRANCH_NAME>

9. Make changes locally

10. Add changes in the working directory to the staging area

        git add <path/filename.ext> or
        git add .  (to add all changed files)

11. Commit changes on the local branch

        git commit -m “<message>”

12. Push to remote branch

        git push

13. Create a pull request for the original repository owner to merge your changes into the original project:
Log in to GitHub, find your new branch, click Pull Request. Include “…a fix for Issue XYZ” in the Pull Request title.

13. Repeat

