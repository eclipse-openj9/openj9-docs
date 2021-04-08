<!--
* Copyright (c) 2017, 2021 IBM Corp. and others
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

This short guide will help you if you want to make a contribution to the Eclipse OpenJ9 user documentation.

**Note:** There is a short video presentation explaining the process for contributing to the OpenJ9 documentation. (Save and open the [`.zip` file](process/Contributing.to.OpenJ9.docs.zip) in the `/process` directory to view the `.mp4` video.) You can also view the [PowerPoint presentation](process/openj9-doc-contibutions.pptx) used in that video. (Either save the file to view locally, or click "View raw".)

- If you don't want to contribute directly by modifying the documentation yourself, but you have a suggestion or want to report an error, please raise an issue in this repository.

    The following templates are available to help you provide the correct information for someone else to handle any changes:

    - [General documentation enhancements / ideas for improvements](https://github.com/eclipse/openj9-docs/issues/new?template=documentation-enhancement.md)
    - [Documentation errors or inaccuracies](https://github.com/eclipse/openj9-docs/issues/new?template=documentation-error.md)
    - [New content as a result of code changes at the Eclipse OpenJ9 repo](https://github.com/eclipse/openj9-docs/issues/new?template=new-documentation-change.md)

- If you want to contribute to the documentation, we recommend that you install a local test environment for editing and previewing your changes.
The user documentation is authored in Markdown and built into HTML using [Mkdocs](http://www.mkdocs.org/), with a [mkdocs-material](https://github.com/squidfunk/mkdocs-material) theme.

    To get started, follow the guidance in the following sections:

    - [Setting up a local MkDocs environment](#setting-up-a-local-mkdocs-environment)
    - [Editing the documentation](#editing-the-documentation)
    - [- Documentation structure](#documentation-structure)
    - [- Style guidelines](#style-guidelines)
    - [- Writing tips](#writing-tips)
    - [- Which platform?](#which-platform)
    - [- Which OpenJDK version?](#which-openjdk-version)
    - [- Trademarks](#trademarks)
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
- a Markdown editor (Atom, for example)

Use **pip** to install the other components, reusing the **requirements.txt** file in the `/buildenv` directory of the repository to ensure that you are mirroring build levels:

`pip install -r requirements.txt`

Next:

- Fork the repository: https://github.com/eclipse/openj9-docs
- Clone the repository: `git clone git@github.com:<your_github_account>/openj9-docs.git`

If you are working on a Windows<sup>&trade;</sup> system, set the following Git configuration, which allows you to edit with Windows line endings (CRLF), but converts to Unix-style endings (LF) when you push changes:

`git config --global core.autocrlf true`

## Editing the documentation

The source files are written in GitHub-flavoured Markdown format.
We recommend using [Atom](https://atom.io/) for editing, which includes a markdown preview package so that you can check your changes as you write.
Here is a 3 minute read on [Mastering markdown](https://guides.github.com/features/mastering-markdown/).

For improved structure and styling options, a Markdown extension for definition lists is also included. This simple extension allows you to create a title and indented definition by using a semi-colon (:).
For example:

```
Title
: definition
```

### Documentation structure

All files in the ROOT folder of this repository are concerned with the structure, build, and automation of the documentation hosting solution.

#### MkDocs .yml file

The key configuration file for the documentation is the `/mkdocs.yml` file, which controls the following aspects:

- website and repository addresses
- MKdocs theme and customisation values
- structure and content, under the `pages:` section

Under `pages:` in the `.yml` file, topics are added and indented to reflect their position in the table of contents. Care must be taken to preserve the indentation when making changes to this file.

If you want to add a new file, see [Document files](#document-files) about file naming, and follow the existing structure and layout when you add a reference to it in the `.yml` file.
Insert new options in the appropriate position in the list, which is typically arranged in alphabetical order within a section.

**Note:** From version 1.0 of MkDocs, the `pages:` section is renamed to `nav:`

#### Document files

Documentation content is contained in the following directories:

- All Markdown files are in the `/docs` folder.
- All diagrams used in content are in the `/docs/cr` folder.

New file names should follow the existing naming convention. They _must_ be in lowercase. They typically omit punctuation and other symbols present in the full option title. Topics that form part of a group might have a distinguishing prefix.

Some examples:

|Topic / option name               | File name                       |
|----------------------------------|---------------------------------|
|-Dcom.ibm.lang.management.verbose |`dcomibmlangmanagementverbose.md`|
|Java stack (jstack) tool          |`tool_jstack.md`                 |
|-Xconcurrentbackground            |`xconcurrentbackground.md`       |
|-XX:[+\|-]ShareAnonymousClasses   |`xxshareanonymousclasses.md`     |

### Style guidelines

For consistency across the user documentation, there are a few style guidelines that you should follow.
These are not set in stone, so if you have any suggestions or concerns, open up a discussion in the [Slack channel](https://openj9.slack.com/).
If you haven't done so already, [join the channel](https://www.eclipse.org/openj9/oj9_joinslack.html).

Generally, follow the style and structure of existing topics.
If you are creating a new topic, you might find it helpful to copy a similar existing topic and modify it. (Don't forget to modify the file name in the footer!)

However you create a new topic, you must ensure that the header comment with copyright and license information is copied exactly as it is from an existing topic. _Do not_ change either of the copyright dates, even for a new topic; the dates refer to the complete documentation set, not to individual files and so should be the same in all topics.

### Writing tips

Make your contributions valuable to all users, including those who have English as a second language.  Ensure that you write clearly and precisely. For example:

- Use simple language.
- Avoid needless words.
- Use short sentences but avoid using too many sentence fragments.
- Be direct: use active voice and present tense.
- Avoid ambiguity: what exactly does the "this" you wrote refer to?
- Use second person ("you" and "your") to better engage the audience.
- Don't overuse punctuation for effect, especially avoid making everything either a question or an exclamation!!
- Make humor universal and timeless, but in technical documentation, humor is best avoided.

### Which platform?

Although OpenJ9 supports a number of platforms and architectures, there might be different configuration and tuning options, or different default behavior.
When adding content to the user documentation that does not apply to all environments, call it out. For example:

      **(Linux&reg; only)**

If content is not marked it should apply to any platform.

See [Trademarks](#trademarks) for information about marking trade names appropriately.

### Which OpenJDK version?

The user documentation supports the configuration, tuning, and diagnosis of the OpenJ9 VM in various versions of the OpenJDK runtime. However, due to differences between the Java<sup>&trade;</sup> SE class libraries, specific options might apply only to one environment. The following icons are available to indicate where differences apply:

- ![Java 8 (LTS)](docs/cr/java8.png) - For content that applies only to an OpenJDK version 8.
- ![Java 8 (LTS) and later](docs/cr/java8plus.png) -  For content that applies to an OpenJDK version 8 or later version.
- ![Java 11 (LTS)](docs/cr/java11.png) - For content that applies only to an OpenJDK version 11.
- ![Java 11 (LTS) and later](docs/cr/java11plus.png) -  For content that applies to an OpenJDK version 11 or later version.
- ![Java 15](docs/cr/java15.png) - For content that applies only to an OpenJDK version 15.
- ![Java 15 and later](docs/cr/java15plus.png) -  For content that applies to an OpenJDK version 15 or later version.
- ![Java 16](docs/cr/java16.png) - For content that applies only to an OpenJDK version 16.
- ![Java 16 and later](docs/cr/java16plus.png) -  For content that applies to an OpenJDK version 16 or later version.
- ![Java 17 (LTS)](docs/cr/java17.png) - For content that applies only to an OpenJDK version 17.
- ![Java 17 (LTS) and later](docs/cr/java17plus.png) -  For content that applies to an OpenJDK version 17 or later version.
- ![Java 18](docs/cr/java18.png) - For content that applies only to an OpenJDK version 18.
- ![Java 18 and later](docs/cr/java18plus.png) -  For content that applies to an OpenJDK version 18 or later version.

Different colors are used for the icons to differentiate long term service (LTS) releases from feature releases. For accessibility reasons it is important to use alternative text with these icons that differentiates an LTS release.

Follow these guidelines:

- If a topic is exclusive to a particular version, use the icon near the top of the topic.
- If the content applies to a cell or row in a table, use the icon within the table.
- If the content applies to a sentence within a topic, encapsulate the content by using an end tag ![end tag for LTS releases](docs/cr/java_close_lts.png) for LTS releases or ![end tag for feature releases](docs/cr/java_close.png) for feature releases.

Here are some examples:

```
![Start of content that applies only to Java 8 (LTS)](cr/java8.png) This sentence applies only to Java 8 runtime environments that include the OpenJ9 VM. ![End of content that applies only to Java 8 (LTS)](cr/java_close_lts.png)
```

```
![Start of content that applies only to Java 16 and later](cr/java16plus.png) This sentence applies only to Java 16 or later runtime environments that include the OpenJ9 VM. ![End of content that applies only to Java 16 and later](cr/java_close.png)
```

### Trademarks

In each topic, the _first occurence only_ of trademarked terms should be marked appropriately. Avoid including trademark symbols in titles, headings, etc., unless the term appears nowhere else in the topic.

Here are some examples that you might come across: 

AIX&reg;, IBM&reg;, IBM z15&reg;, Java&trade;, Linux&reg;, Linux on IBM Z&reg;, Linux on Power Systems&trade;, macOS&reg;, MVS&trade;, OpenJ9 VM Language Environment&reg;, Oracle&reg;, Windows&trade;, z/OS&reg;.

Mark trademarks&trade; with `&trade;` (as in `Java&trade;`) and registered trademarks&reg; with `&reg;` (as in `Linux&reg;`).

### Using images

If you believe that a diagram can be used to enhance the content, add the .gif or .png file to the `docs/cr` folder and reference it in the markdown file using the following syntax:

```
![Add alternative text here to describe the image for users who are using a screen reader](cr/<my_image>.png)
```

[Font-awesome](http://fontawesome.io/icons/) icons can also be used to highlight user "notes" and "restrictions". The following examples show how to embed these icons:

```
:fontawesome-solid-pencil-alt:{: .note aria-hidden="true"} **Note:** Here is something you should be aware of...
```

```
:fontawesome-solid-exclamation-triangle:{: .warn aria-hidden="true"} **Restrictions:** Here is some limitations...
```

Font-awesome icons are also used in option tables to indicate defaults. The following examples show how to embed these "ticks" and "crosses".

```
:fontawesome-solid-check:{: .yes aria-hidden="true"}<span class="sr-only">yes</span>
```

```
:fontawesome-solid-times:{: .no aria-hidden="true"}<span class="sr-only">no</span>
```

Note that these require an extra `<span>`, which is used by screen readers.

For examples that embed Java version icons such as ![Java 8 icon](docs/cr/java8.png) and ![Java 14 and later icon](docs/cr/java14plus.png), see [Which OpenJDK version?](#which-openjdk-version?).

### Accessibility

The accessibility of the documentation to users with disabilities is important to this project. Here are some things to consider:

- Don't use terms that convey "position". For example, do not write "the example below", "the above table", "the right-hand column" and so on.
- Don't use color on its own to differentiate text. For example, "the string shown in green" or "the code element in blue".
- If you embed a diagram, use alternative text (ALT-TEXT) to describe the image for screen readers. See [Using images](using-images).
- If you use icons to indicate content for a specific OpenJDK version, or for "notes" and "restrictions", add the correct attributes for screen readers. See [Using images](using-images).


## Testing your changes locally

When you've made the changes that you want to contribute, build and preview the website. If you have [set up an MkDocs environment locally](#setting-up-a-local-mkdocs-environment), follow these steps:

- Run `mkdocs serve`.
- Open a browser and view the following URL: http://127.0.0.1:8008 (specifically, the URL set in the `dev_addr:` section of the `mkdocs.yml` file.

## Submitting a contribution

When you are happy with your changes, create a pull request, following the guidelines for submitting a contribution to OpenJ9 [here](https://github.com/eclipse/openj9/blob/master/CONTRIBUTING.md).

In particular, if your changes address an issue, quote the issue number in the commit message.

If work is ongoing, or if there is further work to be done after you create your pull request, (either in the docs or in the code) add a "WIP" (Work In Progress) prefix to the title.

### Previewing pull requests

Pull requests must be previewed before merging. Stage your pull request (i.e. create a temporary draft of your changes) by triggering a Jenkins-ci job. To run the job, add the following trigger comment into a pull request:  
```
Jenkins doc stage
```  
Pull request builds are staged at the gh-pages branch of the https://github.com/eclipse/openj9-docs-staging repository. To view
the staged draft of your documentation, visit the following URL, substituting &lt;PR&gt; with the number of your pull request:

[`https://eclipse.github.io/openj9-docs-staging/<PR>`](https://eclipse.github.io/openj9-docs-staging/<PR>)
     
## Accepting contributions

_(approved users only)_

Project committers are responsible for checking pull requests and merging changes.

Documentation pull requests must not be merged before code pull requests are merged in case subsequent code changes alter the documented behavior.

Before merging, check with the developers involved and make sure that there is not work still to be done. This is usually indicated by a "WIP" prefix on the pull request title.

When pull requests are merged, the documentation is published to the gh-pages branch of the https://github.com/eclipse/openj9-docs repository as part of the "current working draft" at the following URL:

https://eclipse.github.io/openj9-docs

When released, the latest version of Eclipse OpenJ9 is published at:

https://www.eclipse.org/openj9/docs
