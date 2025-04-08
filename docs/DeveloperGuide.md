# Better Call TA Developer Guide

--------------------------------------------------------------------------------------------------------------------

## Table of Contents

The table of contents cover the sections in this Developer Guide.

- [Acknowledgements](#acknowledgements)
- [Setting up, getting started](#setting-up-getting-started)
- [Design](#design)
  - [Architecture](#architecture)
  - [UI Component](#ui-component)
  - [Logic Component](#logic-component)
  - [Model Component](#model-component)
  - [Storage Component](#storage-component)
  - [Common Classes](#common-classes)
- [Implementation](#implementation)
  - [File Save Feature](#file-save-feature)
  - [File Load Feature](#file-load-feature)
  - [Histogram Feature](#histogram-feature)
- [Proposed Implementation](#proposed-implementation)
  - [Design considerations](#design-considerations)
- [Documentation, Logging, Testing, Configuration, DevOps](#documentation-logging-testing-configuration-dev-ops)
- [Appendix: Requirements](#appendix-requirements)
  - [Product Scope](#product-scope)
  - [User Stories](#user-stories)
  - [Use Cases](#use-cases)
  - [Non-Functional Requirements](#non-functional-requirements)
  - [Glossary](#glossary)
- [Appendix: Instructions for Manual Testing](#appendix-instructions-for-manual-testing)
  - [Launch and shutdown](#launch-and-shutdown)
  - [Deleting a person](#deleting-a-person)
  - [Visualizing Grade Histogram](#visualizing-grade-histogram)
  - [Save and Load from a Save File](#save-and-load-from-a-save-file)
- [Appendix: Planned Enhancements](#appendix-planned-enhancements)
- [Appendix: Effort](#appendix-effort)

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

## **Acknowledgements**

[AddressBook Level-3](https://se-education.org/addressbook-level3), of which we modified our application from.

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S2-CS2103T-T10-3/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2425S2-CS2103T-T10-3/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with one another.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete /id A0111111N`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="900" />
<br>

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class, which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S2-CS2103T-T10-3/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S2-CS2103T-T10-3/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S2-CS2103T-T10-3/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S2-CS2103T-T10-3/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete /id A0123456N")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete /id A0123456N` Command" />

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
**API** : [`Model.java`](https://github.com/AY2425S2-CS2103T-T10-3/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="1200" />

The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Note` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Note` object per unique note, instead of each `Person` needing their own `Note` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="600" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S2-CS2103T-T10-3/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

## **Implementation**

This sections describes some noteworthy details on how certain features are implemented.

### File Save Feature

**API** : [`FileCommand.java`](https://github.com/AY2425S2-CS2103T-T10-3/tp/blob/master/src/main/java/seedu/address/logic/commands/FileCommand.java)

The file save feature allows users to save the current state of the address book to a specified file in the `data` directory. This ensures that users can maintain multiple save files for different contexts (e.g., different classes or groups). The sequence diagram below illustrates the interactions within the system when the `file /save` command is executed.

The following sequence is illustrated below.

<puml src="diagrams/FileSaveSequenceDiagram.puml" width="900" />

<br>

### File Load Feature

**API** : [`FileCommand.java`](https://github.com/AY2425S2-CS2103T-T10-3/tp/blob/master/src/main/java/seedu/address/logic/commands/FileCommand.java)

The file load feature allows users to load a previously saved state of the address book from a specified file in the `data` directory. This enables users to switch between different save files seamlessly. The sequence diagram below illustrates the interactions within the system when the `file /load` command is executed.

The following sequence is illustrated below

<puml src="diagrams/FileLoadSequenceDiagram.puml" width="900" />

<br>

### Histogram feature

**API** : [`Histogram.java`](https://github.com/AY2425S2-CS2103T-T10-3/tp/blob/master/src/main/java/seedu/address/ui/Histogram.java)

The histogram feature shows users a distribution of the grades of the students in the address book as a histogram. The sequence diagram below illustrates how the histogram gets updated when details in the address book change.

<puml src="diagrams/HistogramSequenceDiagram.puml" width="900" />

<br>

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

## **Proposed Implementation**

This section describes how certain features might be implemented.

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

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

* has a need to manage a significant number of student contacts
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: Better Call TA is a contact management platform for teaching assistants to manage the details of students in their class(es) as well as relevant administrative details


### User stories

| Priority  | As a …​                              | I want to …​                                       | So that I can…​                                             |
|-----------|----------------------------------|------------------------------------------------|------------------------------------------------|
| ***       | New user                         | See usage instructions                        | Refer to instructions when I forget how to use the App |
| ***       | TA                               | View my student’s contacts                   | Reference them when writing emails              |
| ***       | TA                               | Add my student’s contact                     | Modify the list when a new student joins        |
| **        | TA                               | Add a student without filling in all the information | Add a student without having all their details |
| ***       | TA                               | Delete my student’s contact                  | Modify the list if they drop the module        |
| **        | TA                               | Edit my student’s contact                    | Change minute details as the course goes on    |
| **        | TA                               | Track the attendance of a student            | Mark them on participation                     |
| *         | TA                               | View each student’s participation     | Track their participation progress in class               |
| *         | TA                               | View the general grade of a student          | Filter out those who may need the most help    |
| *         | TA                               | Add detailed notes to student profiles       | Keep track of important details               |
| *         | TA                               | Keep track of additional notes regarding a student  | Keep track of other information, for my needs      |
| **        | TA                               | Filter my student’s contact by name          | Easily find them for emergencies               |
| * (Epic)  | TA                               | Sort my students according to different categories | Track their progress against certain metrics |
| *         | TA                               | Sort students by name                  | Arrange their names alphabetically        |
| *         | TA                               | Sort students by attendance                  | Track which classes they have attended        |
| *         | TA                               | Sort students by participation                  | Track how much effort they put in        |
| *         | TA                               | Sort students by grades                      | See who needs more guidance                   |
| **        | TA                               | Import student contact lists                 | Separate student into multiple groups of teams or classes  |
| **        | TA                               | Save student contact information locally          | Track past student records for my reference should the need arise       |
| *         | TA                               | Clear my student’s contacts                  | Start with a new list after every semester     |
| *         | TA                               | Clear only my student’s attendance and participation                  | Reset them for the new week     |
| *         | TA                               | Generate visual graphs of student grades | Present data clearly during meetings     |


### Use cases

<div style="background-color: #fde68a; padding: 10px; border: 1px solid #000; border-radius: 5px; color: #000">
    <b>📝 Note:</b><br>
    For all use cases below, <b>System</b> is interchangable with <code>BetterCallTA</code> and <b>Actor</b> with <code>User</code>, unless specified otherwise.
</div><br>

<br>

**<u>UC1: Add a student</u>**

**MSS**

1. User requests to add a student
2. BetterCallTA checks validity of provided information
3. BetterCallTA adds the student

   Use case ends.

**Extensions**

* 1a. Not all information is given.

  * 1a1. BetterCallTA sets fields where information is not given to their default values.

      Use case ends.


* 2a. Student ID has incorrect format.

  * 2a1. BetterCallTA shows an error message, specifying the correct format.

    Use case ends.

* 2b. Phone is not NA and does not have a correct format.

  * 2b1. BetterCallTA shows an error message, specifying that the format is incorrect.

    Use case ends.

* 2c. Email is not NA and does not have a correct format.

  * 2c1. BetterCallTA shows an error message, specifying that the format is incorrect.

    Use case ends.

* 2d. Course is not NA and does not have a correct format.

  * 2d1. BetterCallTA shows an error message, specifying that the format is incorrect.

    Use case ends.

* 2e. Attendance is not NA and does not match any valid attendance statuses.

  * 2e1. BetterCallTA shows an error message, specifying that the input for attendance is invalid.

    Use case ends.

* 2f. Participation is not NA and does not match any valid participation statuses.

  * 2f1. BetterCallTA shows an error message, specifying that the input for participation is invalid.

    Use case ends.

* 2g. Grade is neither NA nor within the valid range.

  * 2g1. BetterCallTA shows an error message, specifying that the format is incorrect.

    Use case ends.

* 3a. The student cannot be added to the list.

  * 3a1. BetterCallTA shows an error message.

    Use case ends.

<br>

**<u>UC2: Delete a student</u>**

**MSS**

1.  User requests to <u>list students (UC1)</u>
2.  BetterCallTA shows a list of students
3.  User requests to delete a specific student in the list
4.  BetterCallTA deletes the student

    Use case ends.

**Extensions**

* 2a. The list is empty.

* 3a. The given student ID is invalid.

  * 3a1. BetterCallTA shows an error message, specifying that the student ID has an incorrect format.

    Use case ends.

<br>

**<u>UC3: Edit a student</u>**

**MSS**

1. User requests to edit a student
2. BetterCallTA checks validity of the new details
3. BetterCallTA edits the student details to the new ones and displays the new student list

   Use case ends.

**Extensions**

* 1a. No student ID was provided.

  * 1a1. BetterCallTA shows an error message, specifying that the student ID must be provided.

    Use case ends.

* 1b. No details to edit were provided.

  * 1b1. BetterCallTA shows an error message, specifying that at least one detail should be edited.

    Use case ends.

* 2a. Student ID has incorrect format.

  * 2a1. BetterCallTA shows an error message, specifying the correct format.

    Use case ends.

* 2b. Phone is not NA and does not have a correct format.

  * 2b1. BetterCallTA shows an error message, specifying that the format is incorrect.

    Use case ends.

* 2c. Email is not NA and does not have a correct format.

  * 2c1. BetterCallTA shows an error message, specifying that the format is incorrect.

    Use case ends.

* 2d. Course is not NA and does not have a correct format.

  * 2d1. BetterCallTA shows an error message, specifying that the format is incorrect.

    Use case ends.

* 2e. Attendance is not NA and does not match any valid attendance statuses.

  * 2e1. BetterCallTA shows an error message, specifying that the input for attendance is invalid.

    Use case ends.

* 2f. Participation is not NA and does not match any valid participation statuses.

  * 2f1. BetterCallTA shows an error message, specifying that the input for participation is invalid.

    Use case ends.

* 2g. Grade is neither NA nor within the valid range.

  * 2g1. BetterCallTA shows an error message, specifying that the format is incorrect.

    Use case ends.

* 3a. The edited student cannot be added to the list.

  * 3a1. BetterCallTA shows an error message.

    Use case ends.

<br>

**<u>UC4: Clear the list</u>**

**MSS**

1. User requests to clear the list
2. BetterCallTA checks if there are any records in the list
3. BetterCallTA clears the list of all records, leaving it empty

   Use case ends.

**Extensions**

* 2a. The list is empty.

  * 2a1. BetterCallTA shows an error message.

    Use case ends.

<br>

**<u>UC5: Find students(s) from keywords</u>**

**MSS**

1.  User requests to show students with name/id/course matching the provided keyword(s) (separated by space).
2.  BetterCallTA checks the validity of provided keywords.
3.  BetterCallTA shows a list composed of students, each of them having their name/id/course match at least one of the keywords.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  * 2a1. An empty list is displayed.

    Use case ends.

* 2b. The keyword(s) do not match the name/id/course of any student.
  * 2b1. An empty list is displayed.

    Use case ends.

* 2c. User does not provide any keywords to the command.

  * 2c1. BetterCallTA shows an error message.

    Use case ends.

<br>

**<u>UC6: Sorting the list</u>**

MSS

1. User requests to sort by a keyword.
2. BetterCallTA checks validity of the given keyword.
3. BetterCallTA the sorts the list by the given keyword and displays the new student list.

   Use case ends.

**Extensions**

* 2a. The student list is empty.

  * 2a1. An empty list is displayed.

    Use case ends.

* 2b. The given keyword is invalid.

  * 2b1. BetterCallTA shows an error message.

    Use case ends.

* 2c. No keyword is entered.
  * 2c1. BetterCallTA shows an error message.

    Use case ends.

* 2d. Multiple keywords are entered.
  * 2d1. BetterCallTA shows an error message.

    Use case ends.

<br>


**<u>UC7: List all students</u>**

**MSS**

1. User requests to list all students
2. BetterCallTA displays all students in an ordered list.

   Use case ends.

**Extensions**

* 1a. List is empty

  Use case ends.


<br>

**<u>UC8: Save Data</u>**

**Preconditions: BetterCallTA is running and has data to be saved.</u>**

**MSS**
1. User requests to save a file
2. System validates the filename
3. System writes the current data to the specified file
4. System displays confirmation message that data was saved successfully

   Use case ends.

**Extensions**

* 2a. Filename is invalid.

  * 2a1. System displays error message about invalid filename format.

    Use case ends.

* 3a. File cannot be written (e.g., permission issues).

  * 3a1. System displays error message about unable to save.

    Use case ends.

<br>

**<u>UC9: Load Data</u>**

**Preconditions: The specified save file exists and is accessible.</u>**

**MSS**
1. User requests to load a file
2. System validates the filename and checks if the file exists
3. System reads the data from the specified file
4. System loads the data into BetterCallTA
5. System displays a success message confirming the data was loaded

   Use case ends.

**Extensions**

* 2a. Filename is invalid or missing.

  * 2a1. System displays an error message about invalid filename format.

    Use case ends.

* 3a. Specified file does not exist.

  * 3a1. System displays error message about unable to save the file.

    Use case ends.

<br>

**<u>UC10: List all Saved Files</u>**

**MSS**
1. User enters the command
2. System scans the designated save directory for valid files
3. System compiles a list of saved files
4. System displays the list of files in a readable format

<br>

**<u>UC11: See usage instructions</u>**

**MSS**

1. User requests to see all usage instructions
2. BetterCallTA displays a link to the User Guide

   Use case ends.

<br>

**<u>UC12: Reset all students’ attendance and participation status</u>**

**MSS**

1. User requests to reset all attendance and participation status.
2. BetterCallTA sets the attendance and participation status of all students to unmarked.
3. BetterCallTA shows the confirmation message.

   Use case ends.

**Extensions**

* 1a. The student list is empty.

  * 1a1. BetterCallTA shows the error message specifying that the list is empty.

    Use case ends.

* 1b. All students' statuses are already unmarked (no changes needed).

  * 1b1. BetterCallTA shows the error message that all students' status are already unmarked.

    Use case ends.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 students without a noticeable sluggishness in performance for typical usage.
3.  A user with above-average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.


### Glossary

| Term | Definition |
|------|------------|
| **TA** | Teaching Assistant - Supports the instructor in teaching a course by conducting tutorials, grading assignments, and assisting students |
| **CLI** | Command Line Interface - Text-based interface for interacting with software |
| **GUI** | Graphical User Interface - Visual interface with graphical elements |
| **NUS** | National University of Singapore - Institution where this system is primarily used |
| **ID** | Student Identification Number - Unique identifier following NUS format |
| **Home folder** | The folder in your file system that serves as your personal storage space |
| **Command terminal** | **Powershell** on Windows, **Terminal** on Mac and Linux
| **Unicode** | A **text encoding standard** maintained by the Unicode Consortium designed to support the use of text in all of the world's writing systems that can be digitized. |

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

  1. Download the jar file and copy into an empty folder

  2. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

  1. Resize the window to an optimum size. Move the window to a different location. Close the window.

  2. Re-launch the app by double-clicking the jar file.<br>
     Expected: The most recent window size and location is retained.


### Deleting a person

1. Deleting a person while all persons are being shown

  1. Prerequisites: List all persons using the `list` command. Multiple persons in the list, one with the `Id` of `A0237297R` and none with the `Id` of `A9999999R`.

  2. Test case: `delete /id A0237297R`<br>
     Expected: Contact with `Id` of `A0237297R` is deleted from the list. Details of the deleted contact shown in the status message.

  3. Test case: `delete /id A9999999R`<br>
     Expected: Assume contact with `Id` of `A9999999R` does not exist. No person is deleted. Error details shown in the status message.

  4. Other incorrect delete commands to try: `delete`, `delete 69`,
     Expected: Similar to previous


### Visualizing Grade Histogram.

1. Add a person from an empty save file
  * Test case: `add /id A0000001R /name teststudent`

2. Edit the person with the `/grade` flag with the `edit` command.
  * Test case: `edit /id A0000001R /grade 100`
  * Expected: The histogram will be updated and will show 1 student under [90-100] in the histogram.

3. Delete the person
  * Test case: `delete /id A0000001R`
  * Expected: The histogram will be updated to become empty as there are no students in the list.


### Save and Load from a Save File

1. Save the current list of students to a save file.
  * Test case `file /save CS2103T-T10`
  * Expected: A file `CS2103T-T10.json` will be created in `data/` directory.

2. Exit the application with the `exit` command.

3. Relaunch the application with `java -jar BetterCallTA.jar` from the command line terminal.
  *  Expected: BetterCallTA will load successfully.

4. Load the application with the Load command.
  * Test Case: `file /load CS2103T-T10`
  * Expected: The contents of `CS2103T-T10.json` will be loaded.

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

## **Appendix: Planned Enhancements**

**Team Size : 5**

This section contains Planned Enhancements of future enhancements for BetterCallTA. This section is immune from PE bug reporting, as you should know (for us to award you your PE marks. We want to help you as well, but will not if we deem your report too unreasonable).

**1. Do additional error checking in the edge case whereby there are no data save files already in the <code>data</code> directory.**

* Currently, there will be no error message thrown if the <code>data</code> directory is empty, and the BetterCallTA will fail to load a save file until a user is added into the application. We plan to add a validation to ensure that a descriptive error message is shown to the user in this case as there is currently no feedback.

**2.  Add additional commands for adding, deleting, and clearing of individual notes.**

* Currently, there is no easy method for individual notes belonging to a student to be edited, modified, and cleared. We plan to add an additional command for the management of student’s notes to be easier.

**3. Command output box is too small**

* Currently, the command output box is too small for certain error messages. We plan to allow users to increase the size of the output box to view more text to enhance user experience.

**4. Detect when a flag is misspelled and display an error message for it.**

* Currently, there are no detailed error messages in the case that a user misspells a flag, such as mistyping <code>/attendance</code> as <code>/attendence</code>. Adding more detailed error messages (instead of a more general error message in our latest iteration) would allow the user to narrow down the errors in their typed command, improving their CLI experience.

**5. Allow deletion of save files from BetterCallTA**

* Currently, there are no means to delete a save file as saved with the `file /save SAVE_FILE` command. Users would need to manually delete the file on their local system if they wish to do so. In the future, we will introduce means to delete save files in the `data` directory.

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div> 

## **Appendix: Effort**

**Challenges**

As a team, we did not initially realise the amount of work that the project would entail. Our proposed idea, to make an app that's suitable for TAs to use as a class management app, involved
making many small changes to the original AB3 database that rippled out and had consequences in areas of the code that we had not even thought to involve initially. For example, adding the ability to import and export
files was a uniquely challenging effort, especially when it came to handling potential data conflicts between files and parsing the commands properly. Moreover, dividing up the workload in a fair way without overloading 
any single member was another issue that became apparent after realising that work which we had thought ot be more complex (such as changing the behaviour of the `find` command) ended up being more simple than expected, 
and other problems (such as modifying the Argument Tokenizer) took precedence due to side effects. 

**Achievements**

For the most part, we managed to achieve almost all of our actual initial development targets, and every command you can interact with 
was planned in advance. We believe that BetterCallTA is usable in its current state for the target audience, and for us that is a big achievement
considering the problems we faced along the way, and especially near the end. 

**Code Reuse**

Much of AB3 has remained intact in our implementation of BetterCallTA, but crucially, our removal of tags (to better reflect the use of Student IDs)
required heavy changes to the code, diagrams and test classes. Moreover, the addition of the other new attributes such as Participation, Grade, Notes etc. also marked
a change in direction from the original code base which required us to come up with our own ideas. We primarily made use of existing Java and JavaFX libraries to help. 

--------------------------------------------------------------------------------------------------------------------






