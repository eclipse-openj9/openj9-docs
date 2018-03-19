# Things to do while editing

<!--
## Things to decide before starting:

- How to deal with platform-specific stuff. Currently using the following icons:

    - ![AIX](cr/_aix.png)     `![AIX](cr/_aix.png)`
    - ![Linux](cr/_lnx.png)   `![Linux](cr/_lnx.png)`
    - ![Windows](cr/_win.png) `![Windows](cr/_win.png)`
    - ![z/OS](cr/_zos.png)    `![z/OS](cr/_zos.png)`

- What to add to H3 parameter headings (platforms, "Utility option", etc.?). See [-Xshareclasses](xshareclasses.md) for an example.
-->

- Create an H2 called "Syntax" (and other H2s as needed). See [Template](command_template.md).

- Create an H2 called "Parameters".

- For each parameter under "Parameters", create an H3 and a code block for syntax. The H3 should be parameter name (in `monospace font`) only; put platform restrictions, etc. on following line, on their own.

- Make sure all links work. (NB - links to `defaultLinkTopic.md` are placeholders for links that would otherwise go to locations outside this subset of the full documentation. Make sure they're changed!)

- Remove all platform icons.

- Make sure images are displayed correctly.

- Check trademarking is correct on first occurrence of a term:

    | Term                      | Tags                 |
    |---------------------------|----------------------|
    | AIX<sup>&reg;</sup>       | `<sup>&trade;</sup>`   |
    | IBM<sup>&reg;</sup>         | `<sup>&trade;</sup>`   |
    | Java<sup>&trade;</sup>      | `<sup>&trade;</sup>` |
    | Linux<sup>&trade;</sup>     | `<sup>&trade;</sup>`   |
    | Windows<sup>&trade;</sup>   | `<sup>&trade;</sup>`   |
    | z/OS<sup>&trade;</sup>      | `<sup>&trade;</sup>`   |

- Check formatting of tables and convert remaining HTML tables to Markdown, as in table above, for example:

        | Term                      | Tags                 |
        |---------------------------|----------------------|
        | AIX                       | `<sup>&trade;</sup>` |
        | IBM                       | `<sup>&trade;</sup>` |
        | etc...                    |                      |

- Indicate external links. For example: "...see <i class="fa fa-external-link" aria-hidden="true"></i> [Link text]()..."

        ...see <i class="fa fa-external-link" aria-hidden="true"></i> [Link text]()...

- Add draft comments as required. See [Template](command_template.md) for an example.

        <aside>
        <p>Draft comment - pmh</p>
        <p>A little query...</p>
        <p>Some more text...</p>
        </aside>

- Replace all remaining HTML `<span>` tags that have DITA parameters with Markdown equivalents.

- Status marker at top of topic:
    - When you start updating a topic, change status marker at top of page:  
    from <i class="fa fa-hourglass-start"></i> (`<i class="fa fa-hourglass-start"></i>`)  
    to <i class="fa fa-hourglass-half"></i> (`<i class="fa fa-hourglass-half"></i>`)  

    - When you finish updating a topic and it's ready to be contributed, change status marker at top of page:  
    from <i class="fa fa-hourglass-half"></i> (`<i class="fa fa-hourglass-half"></i>`)  
    to <i class="fa fa-hourglass-end"></i> (`<i class="fa fa-hourglass-end"></i>`)  

(N.B. Text is added automatically.)



