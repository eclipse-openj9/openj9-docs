<!--
* Copyright (c) 2017, 2018 IBM Corp. and others
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution and is available at
* https://www.eclipse.org/legal/epl-2.0/ or the Apache
* License, Version 2.0 which accompanies this distribution and
* is available at https://www.apache.org/licenses/LICENSE-2.0.
*
* This Source Code may also be made available under the
* following Secondary Licenses when the conditions for such
* availability set forth in the Eclipse Public License, v. 2.0
* are satisfied: GNU General Public License, version 2 with
* the GNU Classpath Exception [1] and GNU General Public
* License, version 2 with the OpenJDK Assembly Exception [2].
*
* [1] https://www.gnu.org/software/classpath/license.html
* [2] http://openjdk.java.net/legal/assembly-exception.html
*
* SPDX-License-Identifier: EPL-2.0 OR Apache-2.0 OR GPL-2.0 WITH
* Classpath-exception-2.0 OR LicenseRef-GPL-2.0 WITH Assembly-exception
-->

# Contributing to the documentation

:warning: The ID team are not accepting contributions at this time.

The user documentation is authored in markdown and built into HTML using [Mkdocs](http://www.mkdocs.org/), with a [mkdocs-material](https://github.com/squidfunk/mkdocs-material) theme. If you want to
contribute to the documentation, we recommend that you install a local test environment for editing and previewing your changes.

- [Setting up a local MkDocs environment](#setting-up-a-local-mkdocs-environment)
- [Editing the documentation](#editing-the-documentation)
- [- Documentation structure](#documentation-structure)
- [- Style guidelines](#style-guidelines)
- [- Writing tips](#writing-tips)
- [- Which platform?](#which-platform)
- [- Which OpenJDK version?](#which-openjdk-version)
- [- Using images](#using-images)
- [- Accessibility](#accessibility)
- [Testing your changes locally](#testing-your-changes-locally)
- [Submitting a contribution](#submitting-a-contribution)
- [Accepting contributions](#accepting-contributions)


## Setting up a local MkDocs environment

Basic pre-requisites:

- Git
- Python 3
- Pip 9.0.1
- a markdown editor E.g. Atom

Use **pip** to install the other components, reusing the **requirements.txt** file in the repository to ensure that you are mirroring build levels:

`pip install -r requirements.txt`

Next:

- Fork the repository: https://github.ibm.com/runtimes/openj9-docs
- Clone the repository: `git clone git@github.ibm.com:<your_github_account>/openj9-docs.git`

If you are working on a Windows<sup>&trade;</sup> system, set the following Git configuration, which allows you to edit with Windows line endings (CRLF), but converts to Unix-style endings (LF) when you push changes:

`git config --global core.autocrlf true`

## Editing the documentation

The source files are written in GitHub-flavoured Markdown format. We recommend using [Atom](https://atom.io/) for editing, which includes a markdown preview package so that you can check
your changes as you write. Here is a 3 minute read on [Mastering markdown](https://guides.github.com/features/mastering-markdown/).

For improved structure and styling options, a markdown extension for definition lists is also included. This simple extension allows you to create a title and indented definition by using a semi-colon (:).
For example:

```
Title
: definition
```

### Documentation structure

All files in the ROOT folder of this repository are concerned with the structure, build, and automation of the documentation hosting solution.

The key configuration file for the documentation is the `/mkdocs.yml` file, which controls the following aspects:

- website and repository addresses
- MKdocs theme and customisation values
- structure and content, under the **pages:** setting

Under **pages:**, topics are added and indented to reflect their position in the table of contents. Care must be taken to preserve the indentation when making changes to this file.

Documentation content is contained in the following directories:

- All markdown files are in the `/docs` folder.
- All diagrams used in content are in the `/docs/images` folder.

### Style guidelines

For consistency across the user documentation, there are a few style guidelines that you should follow. These are not set in stone, so if you have any suggestions or concerns,
open up a discussion in the [Slack channel](https://openj9.slack.com/). Join the channel [here](https://www.eclipse.org/openj9/oj9_joinslack.html).

### Writing tips

Make your contributions valuable to all users, including those who have English as a second language.  Ensure that you write clearly and precisely. For example:

- Use simple language.
- Avoid needless words.
- Use short sentences but avoid using too many sentence fragments.
- Be direct: use active voice and present tense.
- Avoid ambiguity: what exactly does the "this" you wrote refer to?
- Use second person ("you" and "your") to better engage the audience.
- Don't overuse punctuation for effect, especially avoid making everything either a question or an exclamation!!
- Make humor universal and timeless: if in doubt, avoid.

### Which platform?

Although OpenJ9 supports a number of platforms and architectures, there might be different configuration and tuning options, or different default behavior. When adding content to the user documentation
that does not apply to all environments, call it out. For example:

      **(Linux<sup>&trade;</sup> only)**

If content is not marked it should apply to any platform.

### Which OpenJDK version?

The user documentation supports the configuration, tuning, and diagnosis of the OpenJ9 VM in an OpenJDK version 8 or OpenJDK version 9 runtime. However, due to differences between the Java<sup>&trade;</sup> SE class libraries, specific options might apply only to one environment. The following icons are available to indicate where differences apply:

- ![Java 8 icon](docs/cr/java8.png) - For content that applies only to an OpenJDK version 8.
- ![Java 9 icon](docs/cr/java9.png) - For content that applies only to an OpenJDK version 9.
- ![Java 9 and later icon](docs/cr/java9plus.png) -  For content that applies to an OpenJDK version 9 or later version.

Different colors are used for the icons to differentiate long term service (LTS) releases from feature releases. For accessibility reasons it is important to use alternative text with these icons that differentiates an LTS release.

Follow these guidelines:

- If a topic is exclusive to a particular version, use the icon near the top of the topic.
- If the content applies to a cell or row in a table, use the icon within the table.
- If the content applies to a sentence within a topic, encapsulate the content by using an end tag ![end tag for LTS releases](docs/cr/java_close_lts.png) for LTS releases or end tag ![end tag for feature releases](docs/cr/java_close.png) for feature releases.

Here are some examples:

```
![Start of content that applies only to Java 8 (LTS)](cr/java8.png) This sentence applies only to Java 8 runtime environments that include the OpenJ9 VM. ![End of content that applies only to Java 8 (LTS)](cr/java_close_lts.png)
```

```
![Start of content that applies only to Java 9 and later](cr/java9plus.png) This sentence applies only to Java 9 or later runtime environments that include the OpenJ9 VM. ![End of content that applies only to Java 9 or later](cr/java_close.png)
```

### Using images

If you believe that a diagram can be used to enhance the content, add the .gif or .png file to the `docs/images` folder and reference it in the markdown file using the following syntax:

```
![Add alternative text here to describe the image for users who are using a screen reader](images/my_image.png)
```

[Font-awesome](http://fontawesome.io/icons/) icons can also be used to highlight user "notes" (:pencil:) and "restrictions" (:warning:). The following examples show how to embed these icons, which incude some important attributes for accessibility:

```
<i class="fa fa-pencil-square-o" aria-hidden="true"></i><span class="sr-only">Note</span> **Note:** Here is something you should be aware of.
```

```
<i class="fa fa-exclamation-triangle" aria-hidden="true"></i><span class="sr-only">Restrictions</span> **Restrictions:** Here is something you should be aware of.
```

For examples that embed the ![Java 8 icon](docs/cr/java8.png) and ![Java 9 icon](docs/cr/java9.png) icons, see [Which OpenJDK version?](#which-openjdk-version?).

### Accessibility

The accessibility of the documentation to users with disabilities is important to this project. Here are some things to consider:

- Don't use terms that convey "position". For example, do not write "the example below", "the above table", "the right-hand column" and so on.
- Don't use color on its own to differentiate text. For example, "the string shown in green" or "the code element in blue".
- If you embed a diagram, use alternative text (ALT-TEXT) to describe the image for screen readers. See [Using images](using-images).
- If you use icons to indicate content for a specific OpenJDK version, or for "notes" and "restrictions", add the correct attributes for screen readers. See [Using images](using-images).


## Testing your changes locally

When you've made the changes that you want to contribute, build and preview the website. If you set up an MkDocs environment locally, follow these steps:

- Run `mkdocs build`
- Run `mkdocs serve`
- Open a browser and view the following URL: http://127.0.0.1:8000

:pencil: MkDocs creates a `/site` folder, which contains the HTML for the website.

## Submitting a contribution to OpenJ9 documentation

When you are happy with your changes, create a pull request, following the guidelines for submitting a contribution to OpenJ9 [here](https://github.com/eclipse/openj9/blob/master/CONTRIBUTING.md).
In particular, if your changes address an issue, quote the issue number in the commit message.

## Accepting contributions

**(Project committers only)**

Before merging changes with master, use the Jenkins job "Test Pull Request on staging server" to verify that changes are good.

- Jenkins (temporary): http://edgar.hursley.ibm.com:8080/
- Staging server site: https://pages.github.ibm.com/runtimes/openj9-docs-staging/

When the changes are merged with the master branch, Travis-CI will test the build before updating the gh-pages site.

Check that your changes make it to the public site: https://pages.github.ibm.com/runtimes/openj9-docs/

For Build & Deployment Status, see: https://travis.ibm.com/runtimes/openj9-docs
