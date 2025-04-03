---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# AB-3 Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well }_

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* teacher in a primary or secondary school 
* interacts with many different students, parents and colleagues
* uses a computer or laptop very often
* prefers typing to mouse interactions
* needs help to track all her students' progress

**Value proposition**: manage contacts and provide convenient access to all the people teachers may work with


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                                     | I want to …​                                                              | So that I can…​                                                                |
|----------|-------------------------------------------------------------|---------------------------------------------------------------------------|--------------------------------------------------------------------------------|
| `* * *`  | first time user                                             | keep track of all my school contacts                                      | quickly find their details                                                     |
| `* * *`  | first time user                                             | add a new person                                                          | store all of the contacts                                                      |
| `* * *`  | first time user                                             | delete a person                                                           | remove contacts that I no longer need                                          |
| `* * *`  | first time user                                             | find a person by name                                                     | find them without scrolling through the entire list                            |
| `* * *`  | beginner user                                               | sort contacts in alphabetical order or some sort                          | easily find the contacts                                                       |
| `* * *`  | expert user                                                 | categorize different contacts                                             | better organize my contact lists for easier management                         |
| `* * *`  | expert                                                      | get a child's grade and tasks by searching for their name                 | quickly see performance and pending tasks related to a student                 |
| `* *`    | beginner                                                    | access a tutorial for the app                                             | navigate and find the functions I struggle with easier                         |
| `* *`    | user familiar with the basics of the product                | find a user guide on the advanced features                                | make full use of the product                                                   | 
| `* *`    | first time user                                             | go back and edit the contact's information                                | make corrections                                                               |
| `* *`    | beginner user                                               | easily find out if a contact has already been added                       | the address book is not flooded with duplicate entries                         |
| `* *`    | beginner                                                    | favourite/mark certain contacts                                           | I can easily access them                                                       |
| `* *`    | teacher who does not use the product during school holidays | get a quick refresher on the basic functions of the product               | better recall how to use the basic functions                                   |
| `* *`    | expert                                                      | create custom tags to categorize contacts more efficiently                | manage my contacts more systematically and find them faster                    |
| `* *`    | expert                                                      | export data and reports                                                   | generate necessary reports for record-keeping or sharing                       |
| `* *`    | expert                                                      | edit information synchronously on different devices                       | access the address book using different devices                                |
| `*`      | beginner                                                    | report a bug found on the app                                             | necessary fixes can be carried out                                             |
| `*`      | first time user                                             | add notes to each contact                                                 | keep relevant information                                                      |
| `*`      | expert user                                                 | customize app settings like notifications                                 | it works the way that i would like it to                                       |
| `*`      | beginner                                                    | view a list of students                                                   | access the details of class efficiently                                        |
| `*`      | user familiar with the basics of the product                | access a list of contacts I frequently search for                         | I can efficiently find their information whenever I need to contact them       |
| `*`      | user of the product for a month                             | delete tasks that I have completed long ago                               | my to-do lists do not seem so messy and long                                   |                                                 
| `*`      | user familiar with the basics of the product                | change the contact information of many contacts at once                   | edit contact information of families quicker                                   |                               
| `*`      | user with many contacts outside of school                   | access a separate list of contacts of my friends and family               | all my contacts (not only school-related contacts) are managed in the same app |
| `*`      | user familiar with the basics of the product                | quickly access an overview of all my tasks to complete                    | have a rough idea of what I need to do once I launch the app                   |                     
| `*`      | expert	                                                     | set reminders which can alert me about upcoming deadlines                 | stay organized and never miss important deadlines                              |
| `*`      | expert	                                                     | set frequent contact indicators for a list of frequently contacted people | quickly access my most-used contacts without searching repeatedly              |

### Use cases

(For all use cases below, the **System** is the `AddressBook` and the **Actor** is the `user`, unless specified otherwise)

## Use case: UC01 - Categorize Contacts by Role

**MSS (Main Success Scenario)**

1. User requests to group contacts by a specific role.
2. System validates the input.
3. System displays the grouped contacts and their count.

   Use case ends.

**Extensions**

* 2a. The user does not provide a role.
  * 2a1. System shows an appropriate error message.
  * 2a1. Use case ends.

* 2b. The user provides an invalid role.
  * 2b1. System shows an appropriate error message.
  * 2b2. Use case ends.

* 2c. The user provides multiple roles.
  * 2c1. System indicates that only one role can be selected.
  * 2c2. Use case ends.

* 2d. The user provides a role with incorrect format.
  * 2d1. System shows a format guidance message.
  * 2d2. Use case ends.

* 3a. No contacts are found for the specified role.
  * 3a1. System indicates no matching contacts.
  * 3a2. Use case ends.

* 3b. The system is processing a large contact list.
  * 3b1. System indicates processing is in progress.
  * 3b2. Use case continues normally.

## Use case: UC02 - Add a new contact

**MSS (Main Success Scenario)**

1. User selects option to add new contact.
2. System displays contact input form.
3. User enters contact details.
4. System validates the information.
5. System checks for duplicates.
6. System saves the contact and confirms successful addition.

   Use case ends.

**Extensions**

* 3a. User enters incomplete information.
  * 3a1. System indicates missing required fields.
  * 3a2. Use case resumes at step 3.

* 3b. User enters duplicate parameters.
  * 3b1. System indicates the duplication issue.
  * 3b2. Use case resumes at step 3.

* 4a. System detects invalid values.
  * 4a1. System indicates the validation issues.
  * 4a2. Use case resumes at step 3.

* 5a. System detects a duplicate contact.
  * 5a1. System indicates the contact already exists.
  * 5a2. Use case ends.

* 5b. System storage is full.
  * 5b1. System indicates storage limitation.
  * 5b2. Use case ends.

## Use case: UC03 - Delete a contact

**MSS (Main Success Scenario)**

1. User requests to list contacts.
2. System shows the contact list.
3. User selects a contact to delete.
4. System requests confirmation.
5. User confirms the deletion.
6. System deletes the contact and shows confirmation.

   Use case ends.

**Extensions**

* 2a. The contact list is empty.
  * 2a1. System indicates no contacts available.
  * 2a2. Use case ends.

* 3a. User provides incomplete selection information.
  * 3a1. System indicates required selection criteria.
  * 3a2. Use case resumes at step 3.

* 4a. The specified contact cannot be found.
  * 4a1. System indicates contact not found.
  * 4a2. Use case resumes at step 3.

## Use case: UC04 - Search for contacts

**MSS (Main Success Scenario)**

1. User requests to find contacts.
2. User enters search criteria.
3. System processes the search.
4. System displays matching contacts.

   Use case ends.

**Extensions**

* 2a. User enters empty search criteria.
  * 2a1. System indicates search criteria required.
  * 2a2. Use case resumes at step 2.

* 3a. System is processing a large contact list.
  * 3a1. System indicates search is in progress.
  * 3a2. Use case continues normally.

* 4a. No contacts match the search criteria.
  * 4a1. System indicates no matches found.
  * 4a2. Use case ends.

## Use case: UC05 - Sort contacts

**MSS (Main Success Scenario)**

1. User requests to view contacts.
2. System displays the contact list.
3. User requests to sort contacts by a field and order.
4. System validates sorting parameters.
5. System displays sorted contacts.

   Use case ends.

**Extensions**

* 2a. The contact list is empty.
  * 2a1. System indicates no contacts to sort.
  * 2a2. Use case ends.

* 3a. User provides incomplete sorting parameters.
  * 3a1. System indicates proper sorting parameters required.
  * 3a2. Use case resumes at step 3.

* 4a. User specifies invalid sorting criteria.
  * 4a1. System indicates valid sorting options.
  * 4a2. Use case resumes at step 3.
  
### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  Changes to contacts should be immediately reflected across all relevant sections.
5.  App start up time should be under 3 seconds.
6.  Command exceution should be processed within a second.
7.  The interface should be easy to navigate and intuitive enough for users who are not IT-savy.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **API**: Defines how software components interact
* **CLI (Command Line Interface)**: Text-based command input
* **GUI (Graphical User Interface)**: Visual interaction with software
* **Parser**: Converts user input into executable commands
* **Version Control**: Tracks changes in files (e.g., Git)
* **JavaFX**: Platform for building desktop applications
* **PlantUML**: Text-based tool for creating UML diagrams
* **Activity Diagram**: UML diagram for workflow representation
* **Sequence Diagram**: UML diagram for object interactions
* **Class Diagram**: UML diagram for system structure
* **Undo/Redo Mechanism**: Reverses or reapplies changes
* **Performance Testing**: Evaluates system speed and stability
* **Unit Testing**: Tests individual software components
* **Integration Testing**: Tests combined software components
* **DevOps**: Combines development and operations for efficiency
* **Role**: Defines the type of contact (**Student**, **Parent**, or **Staff**) and determines required information fields.
* **Grade**: Represents a **Student**'s academic level, restricted to **PRI1-PRI6** or **SEC1-SEC5**.
* **Tasks**: To-do items associated with a contact, such as scheduling meetings or tracking progress. Stored in the system but not executed.
* **Parent**: A **Student**'s guardian, required to provide a name, phone number, and email.
* **Class**: A **Student**'s class designation with no format restrictions, allowing flexibility across different schools.
* **Phone**: An **8-digit integer** representing a contact's phone number. If a **Student** lacks a phone number, their **Parent**'s phone number is used.
* **Email**: Must contain **"@"** and **".com"** to ensure validity.
* **Add Command**: Command used to register a new contact, with fields varying by role. Example formats:
    - **Student**: `add n/Johnny Tan ph/09876543 e/john.tan@email.com r/Student p/James Tan c/1A g/Sec 1`
    - **Parent**: `add n/James Tan ph/98765432 e/james.tan@email.com r/Parent`
    - **Staff**: `add n/Regina Hancox ph/29312432 e/regina.hancox@email.com r/Staff`

    
--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Sorting Contacts

1. Sorting by name

   1. Prerequisites: Contact list must not be empty.
    
   1. Test case: `sort by/name asc`  
      Expected: Displays contacts sorted by name in ascending order.
      
   1. Test case: `sort by/name desc`  
      Expected: Displays contacts sorted by name in descending order.

1. Sorting by date
   
   1. Prerequisites: Contact list must not be empty.
      
   1. Test case: `sort by/date asc`  
      Expected: Displays contacts sorted by date added in ascending order.
      
   1. Test case: `sort by/date desc`  
      Expected: Displays contacts sorted by date added in descending order.

1. Default sorting
   
   1. Prerequisites: Contact list must not be empty.
    
   1. Test case: `sort`  
      Expected: Displays contacts sorted by name in ascending order (same behavior as `sort by/name asc`).

### Adding and Replacing Notes

1. Adding a note to a contact

   1. Prerequisites: A valid index within the displayed contact list must be provided.
    
   1. Test case: `note 1 Needs help with Math`  
      Expected: Adds note "Needs help with Math" to the first contact.
      
   1. Test case: `note 3 Call parent tomorrow`  
      Expected: Adds note "Call parent tomorrow" to the third contact.

1. Replacing an existing note

   1. Prerequisites: Contact already has a note.
      
   1. Test case: `note 1 Student is doing well`  
      Expected: Replaces the existing note on the first contact with "Student is doing well".

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
