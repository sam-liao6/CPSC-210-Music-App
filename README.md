# Note Taking App
## Why?
### What will the application do?

To create an application capable of jotitng down notes, having functionality to both type and write/draw if needed. Furthermore, the application should aim to have a very user friendly and organized sorting system.

Some aspects for organization would include:
- ***Grouping***: Grouping notes into a single folder/package
- ***Sorting***: Sorting notes based on select categories (newest, oldest, longest, etc...)
- ***Tags***: Add tagging functionality to notes (tags: homework, practice, lab, lecture, random, etc...)

### Who will use it?

The ideal user would be for students or users who find the need to often jot things down into a notepad. 

### Why is this project of interest to you?

A lot of the note taking apps I am currently using are incredibly annoying to get used to, often including too much functionality or not enough. Furthermore, the way the notes are organized is incredibly frustrating to navigate and I've felt the need to move onto something else. While I might not be able to create a application fitting my needs, at least this will help in my understanding of java and general programming -- specifically in this area -- should I plan to attempt this again.

## User Stories

- As a user, I want to be able to create notes
- As a user, I want to be able to create groups of notes
- As a user, I want to be able to add notes to a group of notes
- As a user, I want to be able to create tags
- As a user, I want to be able to add tags to a note/group of notes
- As a user, I want to be able to remove tags from a note/group of notes
- As a user, I want to be able to delete tags 
- As a user, I want to be able to delete notes
- As a user, I want to be able to delete groups of notes
- As a user, I want to be able to type my notes
- As a user, I want to be able to draw my notes
- As a user, I want to be able to order my notes to some category
- As a user, I want to be able to create note templates
- As a user, I want to be able to use note templates
- As a user, I want to be able to delete note templates
- As a user, I want to be able to see a snippet of each of my notes when looking at a giant group (like a photo library style)

## Phase 2 User Stories

- As a user, I want the application to automatically save the state of my application including the state of all notes, note groups, and tags.
- As a user, I want to be able to load the previous state of my application including the previous state of all notes, note groups, and tags when launching.
- As a user, I want to be able to create my own save states (similar to backups) of the program at any time.

## Phase 3 User Stories

Your GUI must include a panel in which all the Xs that have already been added to Y are displayed. In addition, you are required to implement two related actions. For example, you could have a button or menu item that can be used to add an X to a Y, and another button or menu item that displays a subset of the Xs that satisfy some criterion specified by the user. Note that these are just examples - you are free to adapt these actions for your particular application but the actions that you implement must be somehow related to the Xs and Y. 

## Instructions for End User

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by first creating notes by writing a name into the search bar and clicking add note, if the note title is distinct, it will create a new note. Using the same process create a note group in the group tab. Once both are done, click the edit button for your group which will open up a new tab allowing you to edit the components of your newly created group. From this simply select all the note(s) you wish to add and click add note. The note will now be added to a note group.

- You can generate the second required action related to the user story "filter X" by entering the string/filter you wish to search for in the search bar then click the search button. 

- You can locate my visual component by looking at the top left of the note tab / home page.
- You can save the state of my application by clicking the save button on the note tab / home page.
- You can refresh the state of my application by clicking the refresh button on the note tab / home page.
- You can reload the state of my application to the saved state by clicking the reload button on the note tab / home page.

## Phase 4: Task 2

- Events that occur on setup 
Loaded notes from ./data/notes.json
Loaded tags from ./data/tags.json
Loaded groups from ./data/groups.json
Sun Mar 30 00:27:02 PDT 2025
hello set group to School Work
Sun Mar 30 00:27:02 PDT 2025
school added note {"note":"{\"note\":\"{\\\"note\\\":\\\"{\\\\\\\"note\\\\\\\":\\\\\\\"{\\\\\\\\\\\\\\\"note\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"{\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"note\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"hello\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"}\\\\\\\\\\\\\\\"}\\\\\\\"}\\\"}\"}"}
Sun Mar 30 00:27:02 PDT 2025
school added note {"note":"{\"note\":\"{\\\"note\\\":\\\"{\\\\\\\"note\\\\\\\":\\\\\\\"{\\\\\\\\\\\\\\\"note\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"hello\\\\\\\\\\\\\\\"}\\\\\\\"}\\\"}\"}"}
Sun Mar 30 00:27:02 PDT 2025
school added note {"note":"{\"note\":\"{\\\"note\\\":\\\"{\\\\\\\"note\\\\\\\":\\\\\\\"hello\\\\\\\"}\\\"}\"}"}
Sun Mar 30 00:27:02 PDT 2025
school added note {"note":"{\"note\":\"{\\\"note\\\":\\\"hello\\\"}\"}"}
Sun Mar 30 00:27:02 PDT 2025
school added note {"note":"{\"note\":\"hello\"}"}
Sun Mar 30 00:27:02 PDT 2025
school added note {"note":"hello"}
Sun Mar 30 00:27:02 PDT 2025
school added note hello
Sun Mar 30 00:27:02 PDT 2025
hello added tag school
Sun Mar 30 00:27:02 PDT 2025
Hello set group to School Work
Sun Mar 30 00:27:02 PDT 2025
school added note {"note":"{\"note\":\"{\\\"note\\\":\\\"{\\\\\\\"note\\\\\\\":\\\\\\\"{\\\\\\\\\\\\\\\"note\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"hello\\\\\\\\\\\\\\\"}\\\\\\\"}\\\"}\"}"}
Sun Mar 30 00:27:02 PDT 2025
school added note {"note":"{\"note\":\"{\\\"note\\\":\\\"{\\\\\\\"note\\\\\\\":\\\\\\\"{\\\\\\\\\\\\\\\"note\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"Hello\\\\\\\\\\\\\\\"}\\\\\\\"}\\\"}\"}"}
Sun Mar 30 00:27:02 PDT 2025
school added note {"note":"{\"note\":\"{\\\"note\\\":\\\"{\\\\\\\"note\\\\\\\":\\\\\\\"Hello\\\\\\\"}\\\"}\"}"}
Sun Mar 30 00:27:02 PDT 2025
school added note {"note":"{\"note\":\"{\\\"note\\\":\\\"Hello\\\"}\"}"}
Sun Mar 30 00:27:02 PDT 2025
school added note {"note":"{\"note\":\"Hello\"}"}
Sun Mar 30 00:27:02 PDT 2025
school added note {"note":"Hello"}
Sun Mar 30 00:27:02 PDT 2025
school added note Hello
Sun Mar 30 00:27:02 PDT 2025
Hello added tag school
Sun Mar 30 00:27:02 PDT 2025
school added note {"note":"{\"note\":\"{\\\"note\\\":\\\"Hello\\\"}\"}"}
Sun Mar 30 00:27:02 PDT 2025
school added note {"note":"{\"note\":\"Hello\"}"}
Sun Mar 30 00:27:02 PDT 2025
school added note {"note":"Hello"}
Sun Mar 30 00:27:02 PDT 2025
school added note Hello
Sun Mar 30 00:27:02 PDT 2025
Hello added tag school
Sun Mar 30 00:27:02 PDT 2025
ABC set group to 

- Events that occur when adding a note to a note group

Sun Mar 30 00:27:02 PDT 2025
newNote set group to School Work
Sun Mar 30 00:27:02 PDT 2025
school added note {"note":"{\"note\":\"Hello\"}"}
Sun Mar 30 00:27:02 PDT 2025
school added note {"note":"{\"note\":\"newNote\"}"}
Sun Mar 30 00:27:02 PDT 2025
school added note {"note":"newNote"}
Sun Mar 30 00:27:02 PDT 2025
school added note newNote
Sun Mar 30 00:27:02 PDT 2025
newNote added tag school
Sun Mar 30 00:27:02 PDT 2025
school added note hello
Sun Mar 30 00:27:02 PDT 2025
Hello set group to School Work
Sun Mar 30 00:27:02 PDT 2025
school added note {"note":"{\"note\":\"hello\"}"}
Sun Mar 30 00:27:02 PDT 2025
school added note {"note":"{\"note\":\"Hello\"}"}
Sun Mar 30 00:27:02 PDT 2025
school added note {"note":"Hello"}
Sun Mar 30 00:27:02 PDT 2025
school added note Hello
Sun Mar 30 00:27:02 PDT 2025
Hello added tag school
Sun Mar 30 00:27:02 PDT 2025
school added note Hello
Sun Mar 30 00:27:02 PDT 2025
Hello added tag school
Sun Mar 30 00:27:02 PDT 2025
school added note Hello
Sun Mar 30 00:27:02 PDT 2025
school added note newNote
Sun Mar 30 00:27:02 PDT 2025
Hello added tag school
Sun Mar 30 00:27:02 PDT 2025
school added note Hello
Sun Mar 30 00:27:02 PDT 2025
Hello added tag school
Sun Mar 30 00:27:02 PDT 2025
Hello set group to School Work
Sun Mar 30 00:27:02 PDT 2025
School Work added note Hello
Sun Mar 30 00:27:02 PDT 2025
school added note Hello
Sun Mar 30 00:27:02 PDT 2025
Hello added tag school
Sun Mar 30 00:27:02 PDT 2025
School Work added tag school
Sun Mar 30 00:27:07 PDT 2025
school added note ABC
Sun Mar 30 00:27:07 PDT 2025
ABC added tag school
Sun Mar 30 00:27:07 PDT 2025
ABC set group to School Work
Sun Mar 30 00:27:07 PDT 2025
School Work added note ABC

## Phase 4: Task 3

One minor bit of refactoring I would like to do is to replace all relevant calls to button tags, names, etc. are replaced with constants of the same name in the button names class. This was originally done for one or two buttons but over time I dropped the habit. However, this would be useful in the future. Given the size of program if I were ever wanted to change a button name, this would make it a lot easier to change the button names without having to go through the program and finding every instance. It would also allow me to reduce the amount of variables and just overall increase the run time since a lot of different tabs will use reoccuring button names. 

Following the previous line of though, I would like to create a new interface/class of just prebuilt buttons with preloaded methods executing certain behaviours given certain parameters that I can extend or implement. Similar to the names, a lot of the buttons share similar functionality, while not exactly the same, I believe there is some way to refactor it so that similar functionality can be replaced with a single or a set of methods. Again this would just make the code just more legibile overall and improve efficiency. Similarly, a lot of the UI elements initalize rather similarly with minor differences. For example, I believe all of the starting tabs has a search bar with similar functionality with the only difference being what they search for, however, the overall method of which they use to search is the same. This would just be one example of another bit of refactoring I would like to do with the UI given more time.