

# Better Call TA User Guide
**BetterCallTA** enables you to **effortlessly manage your students' contacts**. Optimized for use via a **Command Line Interface (CLI)** while still having the benefits of a **Graphical User Interface (GUI)**, **BetterCallTA** handles all the nitty gritty details of your session so that you can focus on **delivering the best to your students**.

## Table of Contents
1. [Overview](#overview)
1. [Quick start](#quick-start)
1. [Student](#student)
   - [ID - Unique student identifier](#-id---unique-student-identifier)
   - [Name - Student's name](#name---your-students-name)
   - [Phone - Student's phone number](#-phone---students-phone-number)
   - [Email - Student's email address](#-email---students-email-address)
   - [Course - NUS course code](#-course---nus-course-code)
   - [Attendance - Student's class participation status](#-attendance---students-class-participation-status)
   - [Participation - Student's engagement level](#-participation---students-engagement-level)
   - [Grade - Predicted grade](#grade---the-predicted-grade-of-your-student-based-on-their-previous-graded-assignments)
   - [Notes - Additional details](#notes---additional-details-that-you-may-want-to-keep-about-individual-students)
1. [Features](#features)
   - [Command Syntax Notes](#command-syntax-notes)
   - [Viewing Help](#viewing-help-help)
   - [Adding a Person](#adding-a-person-add)
   - [Listing All Persons](#listing-all-persons-list)
   - [Editing a Person](#editing-a-person-edit)
   - [Locating Persons by Name](#locating-persons-by-name-find)
   - [Sorting Your List](#sorting-your-list-sort)
   - [Deleting a Person](#deleting-a-person-delete)
   - [Saving Contact Data](#saving-contact-data-file-save)
   - [Loading Contact Data](#loading-contact-data-file-load)
   - [Listing Save Files](#listing-save-files-file-list)
   - [Clearing All Entries](#clearing-all-entries-clear)
   - [Resetting All Attendance and Participation Records](#resetting-all-attendance-and-participation-records-resetrecords)
   - [Exiting the Program](#exiting-the-program-exit)
1. [FAQ](#faq)
1. [Known Issues](#known-issues)
1. [Command Summary](#command-summary)
1. [Glossary](#glossary)

---

## Overview

### Target audience
Teaching Assistants (TA) who can type fast, preferring typing over mouse/voice commands

### Value proposition
**Better Call TA** is a **contact management platform** for teaching assistants to manage the details of students in their class(es) as well as **relevant administrative details**, such as their attendance, participation and grades.

### Assumptions
- You prefer typing to mouse or voice commands.
- The student's name has an English translation. For example,
   - `陈李` can be translated to `Chen Li`.
   - `X Æ A-12 Musk` can be translated to `Ash Musk`.

### Notations
- Angular brackets `< number x '(list of characters) >` means choose `number` from the list of characters, allowing duplicates. For example:
   - <2 x (A/B/C)> means choose two of A, B or C.
   - <(0-2) x (A-Z)> means choose 0 to 2 of any capital letters.

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have `Java 17` or above installed in your Computer.
   - If you do not have it installed, follow the instructions [here](https://se-education.org/guides/tutorials/javaInstallation.html), selecting your appropriate Operating System (OS).

1. Download the latest `.jar` file from [here](https://github.com/AY2425S2-CS2103T-T10-3/tp/releases).
1. Copy the file to the folder you want to use as the _home folder_ for BetterCallTA.
1. Open a command terminal (refer to the Glossary for a recommended terminal) and use the `cd` command with the folder you put the jar file in.
   - For example,  `cd .\Downloads`
1. Use the `java -jar BetterCallTA.jar` command to run the application.<br>
   - A GUI similar to the below should appear in a few seconds.

   > ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.


   > Here are some example commands you can try:
   >
   > | Command                                      | Description                                               |
   > |----------------------------------------------|-----------------------------------------------------------|
   > | `add /id A3478231B /name John Doe /phone 98765432 /email johnd@example.com` | Adds a student with the ID `A3478231B` to the Address Book.      |
   > | `edit /id A3478231B /attendance present /participation good /grade 84` | Edits the attendance, participation and grade of the student with ID `A3478231B` to `PRESENT`, `GOOD` and `84` respectively.
   > | `delete /id A1234567B`                       | Deletes the student with ID `A1234567B` in the student list. |
   > | `list`                                       | Lists all students.                                       |
   > | `clear`                                      | Deletes all students.                                     |
   > | `exit`                                       | Exits the app.                                            |

Do refer to the [Features](#features) below for details of each command!

--------------------------------------------------------------------------------------------------------------------
## Student

This section covers the `Person` field used in BetterCallTA, which encapsulates a student's contact details in BetterCallTA.

> ![Student descriptor](images/person.png)

---
## 🧑‍🎓 Student

This section covers the `Person` field used in BetterCallTA, which encapsulates a student's contact details in BetterCallTA.

> ![Student descriptor](images/person.png)

---
### Properties

   - [🆔 ID - Unique student identifier](#-🆔-id---unique-student-identifier)
   - [Name - Student's name](#name---your-students-name)
   - [📞 Phone - Student's phone number](#-📞-phone---students-phone-number)
   - [📧 Email - Student's email address](#-📧-email---students-email-address)
   - [🎓 Course - NUS course code](#-🎓-course---nus-course-code)
   - [✅ Attendance - Student's class participation status](#-✅-attendance---students-class-participation-status)
   - [🗣️ Participation - Student's engagement level](#-🗣️-participation---students-engagement-level)
   - [💯 Grade - Predicted grade](#💯-grade---the-predicted-grade-of-your-student-based-on-their-previous-graded-assignments)
   - [📝 Notes - Additional details](#-📝-notes---additional-details-that-you-may-want-to-keep-about-individual-students)

---

#### 🆔 **`ID`** - Unique student identifier

**Format**:
```
<1 x (A/U/HT/NT)> + 7 digits + <1 x (A/B/E/H/J/L/M/N/R/U/W/X/Y)>
```

<div style="background-color: #fde68a; padding: 10px; border: 1px solid #000; border-radius: 5px; color: #000">
    <b>📌 ID Format Rules</b> Follows NUS Student ID format:
    <ol>
      <li>Must start with <code>A</code>, <code>U</code>, <code>HT</code>, or <code>NT</code></li>
      <li>Starting letters must be followed by <b>7 digits</b></li>
      <li>Must end with one of: <code>A, B, E, H, J, L, M, N, R, U, W, X, Y</code></li>
    </ol>
</div><br>

**Examples**:
- `A0738475N`
- `HT0347856U`

---

#### `Name` - Your student's name

Multiple students with the same name are allowed, as long as they have a unique Student ID. 

---

#### 📞 **`Phone`** - Student's phone number

<div style="background-color: #fde68a; padding: 10px; border: 1px solid #000; border-radius: 5px; color: #000">
    <b>📌 Phone Number Format Rules</b>
    
    <ol>
      <li>Can start with <code>+</code></li>
      <li>Otherwise, must only contain numbers <code>(0-9)</code> or <code>-</code></li>
      <li>Cannot contain consecutive hyphens</li>
      <li>Must be at least 7 digits long and no more than 15 digits long</li>
    </ol>
</div><br>

**Default**: `00000000`

**Example**:
- 86566289
- +65-8576-9485

---

#### 📧 **`Email`** - Student's email address

**Format**: `local-part@domain`

<div style="background-color: #fde68a; padding: 10px; border: 1px solid #000; border-radius: 5px; color: #000">
    <b>📌 Email Format Rules</b>
    
    <b>Local-Part:</b>
    <ul>
      <li>Only alphanumeric characters <code>(a-z, A-Z, 0-9)</code></li>
      <li>Cannot contain these special characters: <code>+</code>, <code>_</code>, <code>.</code>, <code>-</code></li>
      <li>Cannot start or end with special characters</li>
    </ul>

    <b>Domain:</b>
    <ul>
      <li>Must end with domain label of at least 2 characters</li>
      <li>Each label must:</li>
      <ul>
            <li>Start and end with alphanumeric characters</li>
            <li>Contain only alphanumerics and hyphens</li>
      </ul>
    </ul>
</div><br>

**Default**: `example@u.nus.edu`

**Valid Examples**:
- `johndoe@u.nus.edu`
- `example32@gmail.com`

---

#### 🎓 **`Course`** - NUS course code

**Format**:
```
<(2/3) x (A-Z)> + 4 digits + <(0-2) x (A-Z)>
```

<div style="background-color: #fde68a; padding: 10px; border: 1px solid #000; border-radius: 5px; color: #000">
    <b>📌 Course Format Rules</b>
    
    <ol>
      <li>Must start with 2 or 3 letters</li>
      <li>Followed by exactly 4 digits</li>
      <li>May optionally end with up to 2 letters</li>
    </ol>
</div><br>

**Default**: `AAA0000AA`

**Valid Examples**:
- `CS2103T`
- `IS1108`
- `MA1101R`

---

#### ✅ **`Attendance`** - Student's class participation status

| Status      | Description                          | Case Sensitivity |
|-------------|--------------------------------------|------------------|
| `PRESENT` | Present             | Case-insensitive |
| `ABSENT`      | Absent without valid reason          | Case-insensitive |
| `EXCUSED`   | Absent with valid reason             | Case-insensitive |

<div style="background-color: #fde68a; padding: 10px; border: 1px solid #000; border-radius: 5px; color: #000">
    <b>📌 Attendance Format Rules</b>
    
    <ol>
      <li>Only the above status values are valid</li>
      <li>Matches must be exact (e.g., "Absent" not "Absent again")</li>
      <li>Input can be in any capitalization (e.g., "Present" or "PRESENT")</li>
    </ol>
</div><br>

**Default**: `UNMARKED`

---

#### 🗣️ **`Participation`** - Student's engagement level

| Status      | Description                          | Case Sensitivity |
|-------------|--------------------------------------|------------------|
| `EXCELLENT` | Outstanding contribution             | Case-insensitive |
| `GOOD`      | Above average participation          | Case-insensitive |
| `AVERAGE`   | Meets basic expectations             | Case-insensitive |
| `POOR`      | Minimal or insufficient engagement   | Case-insensitive |
| `NONE`      | No observable participation          | Case-insensitive |

<div style="background-color: #fde68a; padding: 10px; border: 1px solid #000; border-radius: 5px; color: #000">
    <b>📌 Participation Format Rules</b>
    
    <ol>
      <li>Only the above status values are valid</li>
      <li>Matches must be exact (e.g., "Good" not "Good enough")</li>
      <li>Input can be in any capitalization (e.g., "Excellent" or "EXCELLENT")</li>
    </ol>
</div><br>

**Default**: `UNMARKED`

---

#### `Grade` - The predicted grade of your student based on their previous graded assignments

<div style="background-color: #fde68a; padding: 10px; border: 1px solid #000; border-radius: 5px; color: #000">
    <b>📌 Grade Format Rules</b>
    
    <ol>
      <li>You can only enter an integer from <code>0</code> to <code>100</code> (inclusive), or <code>NA</code></li>
    </ol>
</div><br>

---

#### `Notes` - Additional details that you may want to keep about individual students

- You can attach any number of notes to each student.


--------------------------------------------------------------------------------------------------------------------

## Features
This section covers all the features of BetterCallTA. Navigate to the following sections to know more about BetterCallTA's features.

- [Command Syntax Notes](#command-syntax-notes)
- [Viewing Help](#viewing-help-help)
- [Adding a Person](#adding-a-person-add)
- [Listing All Persons](#listing-all-persons-list)
- [Editing a Person](#editing-a-person-edit)
- [Locating Persons by Name](#locating-persons-by-name-find)
- [Sorting Your List](#sorting-your-list-sort)
- [Deleting a Person](#deleting-a-person-delete)
- [Clearing All Entries](#clearing-all-entries-clear)
- [Exiting the Program](#exiting-the-program-exit)
- [Saving the Data](#saving-the-data)
- [Editing the Data File](#editing-the-data-file)
- [Archiving data files (Coming in v2.0)](#archiving-data-files-coming-in-v20)

---

### Command Syntax Notes
This section covers the Command Syntax Notes you should be aware of when you are using BetterCallTA

**Command Syntax Notation**

| Notation               | Meaning                                                                 | Example                                                                 |
|------------------------|-------------------------------------------------------------------------|-------------------------------------------------------------------------|
| `UPPER_CASE`           | Required parameters you must provide                                    | `add /id ID` → `add /id A1234567X`                                     |
| `[square brackets]`    | Optional parameters                                                    | `add /name NAME [phone]` → Can omit phone                               |
| `...` (ellipsis)       | Parameter can be repeated multiple times                               | `/note NOTE...` → Can add multiple notes                               |


**Command Syntax Rules**

| Rule                      | Description                                                                 | Example                                                                 |
|---------------------------|-----------------------------------------------------------------------------|-------------------------------------------------------------------------|
| Parameter Order           | Parameters can be in any order                                              | `add /id A123 /name John` = `add /name John /id A123`                  |
| Extraneous Parameters     | Extra parameters are ignored for simple commands                            | `help 123` → Treated as `help`                                         |
| PDF Copy-Paste Warning    | Watch for missing spaces when copying from PDFs                             | Ensure `/idA123` becomes `/id A123` when pasting                       |


**Recommended Best Practices for Command Syntax**

1. **Required Parameters**
   - Must always be provided for commands that require them
   - Example: `ID` in `delete /id ID`

2. **Optional Parameters**
   - Can be omitted if not needed
   - Example: Phone number in contact creation

3. **Repeated Parameters**
   - Can add multiple instances where supported
   - Example: Multiple notes for a student

4. **Format Preservation**
   - Maintain exact spacing when copying commands
   - Test commands after pasting from external sources

---

### Viewing Help: `help`

Shows a message explaining how to access the help page.

**Format**: `help`

> ![Help Message](images/helpMessage.png)

---

### Adding a Person: `add`

Adds a person to the address book.

**Format**:
`add /id ID /name NAME [/phone PHONE_NUMBER] [/email EMAIL] [/course COURSE]`

**Examples**:
- `add /id A4235352H /name John Doe /phone 98765432 /email johnd@example.com`
- `add /id A4045018Y /name Betsy Crowe /email betsycrowe@example.com /phone 1234567 /course CS4215`

---

### Listing All Persons: `list`

Shows a list of all persons in the address book.

**Format**:
`list`

---

### Editing a Person: `edit`

Edits an existing person in the address book.

**Format**:
`edit /id ID [/newid NEW_ID] [/name NAME] [/phone PHONE_NUMBER] [/email EMAIL] [/course COURSE]`

**Notes**:
- Edits the person with the specified `ID`.
- Only the specified properties will be updated. Other properties will remain as they were previously.

<div style="background-color: #fde68a; padding: 10px; border: 1px solid #000; border-radius: 5px; color: #000">
    <b>Warning</b>
    <ul>
      <li>At least one optional field must be provided</li>
      <li>Not more than one of each optional field should be provided</li>
      <li>The new ID must not already be used by a different person</li>
    </ul>
</div><br>

**Example**:
`edit /id A0123456N /phone 91234567 /email johndoe@example.com`
This will update the phone number and email of the person with ID `A0123456N` to `91234567` and `johndoe@example.com` respectively.

---

### Locating Persons by Name: `find`

Finds persons whose names contain any of the given keywords.

**Format**:
`find KEYWORD [MORE_KEYWORDS]`

<div style="background-color: #98fB98; padding: 10px; border: 1px solid #000; border-radius: 5px; color: #000">
    <b>Tips</b>
    <ul>
      <li>The search is case-insensitive, e.g <code>hans</code> will match <code>Hans</code></li>
      <li>The order of the keywords does not matter, e.g. <code>Hans Bo</code> will match <code>Bo Hans</code></li>
      <li>Persons matching at least one keyword will be returned (i.e. <code>OR</code> search),<br>
      e.g. <code>Hans Bo</code> will return <code>Hans Gruber</code> , <code>Bo Yang</code></li>
    </ul>
</div><br>

<div style="background-color: #fde68a; padding: 10px; border: 1px solid #000; border-radius: 5px; color: #000">
    <b>Warning</b>
    <ul>
      <li>Only the name is searched</li>
      <li>Only full words will be matched e.g. <code>Han</code> will not match <code>Hans</code></li>
    </ul>
</div><br>

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`

<br>

  >![result for 'find alex david'](images/findAlexDavidResult.png)

---

### Sorting Your List: `sort`

Sorts your current list by **one** of four parameters: name, grade, attendance or participation.

**Format**:
sort /by name | grade | attendance | participation

<div style="background-color: #98fB98; padding: 10px; border: 1px solid #000; border-radius: 5px; color: #000">
    <b>Tips</b>
    <ul>
      <li>The keywords are case-insensitive, e.g <code>grade</code> will match <code>Grade</code></li>
      <li>To sort by multiple parameters, you must sort them one by one, in any order.</li>
      <li>The sort order for each keyword is as follows (name: A to Z | grade: 100 to 0 | attendance: Present > Excused > Absent > Unmarked | Participation: Excellent to Unmarked).</li>
</ul>
</div><br>

Examples:
* `sort /by name` will arrange your list in alphabetical order.
* `sort /by grade` will arrange your students by their grade scores in descending order.

### Deleting a Person: `delete`

Deletes the specified person from the address book.

**Format**:
`delete /id ID`

* Deletes the person with the specified `ID`.
* The ID refers to the ID belonging to the person in the person list.

**Examples**:
- `delete /id A1234567N` deletes the person with ID `A1234567N`.
- `delete /id A7654321B` deletes the person with ID `A7654321B`.

---

### Saving Contact Data: `file /save`

BetterCallTA data is automatically saved to `addressbook.json` in `[JAR file location]/data/` by default unless the save file is modified with the `file /load` command.

Change the save file that the address book will store data to with `file /save`.

**Format**
`file /save SAVE_FILE`

* Modify the save file location as specified with `SAVE_FILE` in `[JAR file location]/data/` as `SAVE_FILE.json`.

**Examples**:
- `file /save CS2103T-T10-3`

<div style="background-color: #fde68a; padding: 10px; border: 1px solid #000; border-radius: 5px; color: #000">
    <b>Warning</b>
    <ul>
      <li>If <code>SAVE_FILE.json</code> already exists in the <code>data</code> directory, it will be overwritten.</li>
      <li>It is recommended NOT to modify the saved <code>SAVE_FILE.json</code> directly as it may introduce unintended behaviour in the application.</li>
    </ul>
</div><br>

---

### Loading Contact Data: `file /load`

BetterCallTA data will load the most recent save file that was used in the application by default. Save files can be loaded with the `file /load` command.

Change the `.json` save file that the address book will read/save from with `file /load`.

**Format**
`file /load SAVE_FILE`

* Load the specified save file `[JAR file location]/data/SAVE_FILE.json` to the current working application. Subsequent operations will be saved to the loaded `SAVE_FILE.json` file.

**Examples**:
- `file /load CS2103T-T10-3`

---

### Listing Save Files: `file /list`

BetterCallTA stores all save files in `[JAR file location]/data/` and the `file /list` command will list out all `.json` save files in that directory, as well as indicating the current save file that is being in use by the application as indicated by `(current save file)`.

**Format**
`file /list all`

* List out all the save files stored by the application.

**Examples**:
- `file /list all`

---

### Clearing All Entries: `clear`

Clears all entries from the address book.

**Format**:
`clear`

---

### Resetting All Attendance and Participation Records: `resetRecords`

Resets the attendance and participation status of all persons to `UNMARKED`.

**Format**:
`resetRecords`

---

### Exiting the Program: `exit`

Exits the program.

**Format**:
`exit`

---

**Warning for Advanced Users**:
You can update the data directly by editing the file, but be cautious.

#### **Caution**:
- Saving the application data with the `file /save FILE` command will overwrite the `FILE.json` save file if it already exists.


--------------------------------------------------------------------------------------------------------------------

## FAQ
This section covers common questions that BetterCallTA users face.

**Q**: How do I transfer my data to another Computer?<br>
**A**: Copy the save files stored in `[home folder]/data/` over to the new Computer's `[home folder]/data/` location.

--------------------------------------------------------------------------------------------------------------------

## Known issues
This section covers the current known issues with BetterCallTA.

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary
This section covers the summary of commands in BetterCallTA.

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add /id ID /name NAME [/phone PHONE_NUMBER] [/email EMAIL] [/course COURSE]…​` <br> e.g., `add /id A0123456E /name James Ho /phone 22224444 /email jamesho@example.com`
**Clear**  | `clear`
**Delete** | `delete /id ID`<br> e.g., `delete /id A3456712E`
**Edit**   | `edit /id ID [/newid NEW_ID] [/name NAME] [/phone PHONE_NUMBER] [/email EMAIL] [/course COURSE]…​`<br> e.g.,`edit /id A0123456N /phone 91234567 /email johndoe@example.com`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**Sort**   | `sort /by KEYWORD [name/grade/attendance/participation]` e.g., `sort /by name`
**List all users**   | `list`
**Show Usage**   | `help`
**Save Data**   | `file /save SAVE_FILE`
**Load Data**   | `file /load SAVE_FILE`
**Reset Attendence and Participation Records** | `resetRecords`
**List Save Files** | `file /list all`

--------------------------------------------------------------------------------------------------------------------

## Glossary
This section covers the glossary of key terms as discussed in this User Guide and in other BetterCallTA documentations.

| Term | Definition |
|------|------------|
| **TA** | Teaching Assistant - Supports the instructor in teaching a course by conducting tutorials, grading assignments, and assisting students |
| **CLI** | Command Line Interface - Text-based interface for interacting with software |
| **GUI** | Graphical User Interface - Visual interface with graphical elements |
| **NUS** | National University of Singapore - Institution where this system is primarily used |
| **ID** | Student Identification Number - Unique identifier following NUS format |
| **Home folder** | The folder in your file system that serves as your personal storage space |
| **Command terminal** | **Powershell** on Windows, **Terminal** on Mac and Linux
