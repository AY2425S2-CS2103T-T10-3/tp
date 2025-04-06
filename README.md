[![CI Status](https://github.com/AY2425S2-CS2103T-T10-3/tp/workflows/Java%20CI/badge.svg)](https://github.com/AY2425S2-CS2103T-T10-3/tp/actions)
[![MarkBind Action](https://github.com/AY2425S2-CS2103T-T10-3/tp/actions/workflows/docs.yml/badge.svg)](https://github.com/AY2425S2-CS2103T-T10-3/tp/actions/workflows/docs.yml)
[![pages-build-deployment](https://github.com/AY2425S2-CS2103T-T10-3/tp/actions/workflows/pages/pages-build-deployment/badge.svg)](https://github.com/AY2425S2-CS2103T-T10-3/tp/actions/workflows/pages/pages-build-deployment)

# Better Call TA

**Better Call TA** is a contact management platform designed for Teaching Assistants (TAs) to efficiently manage student information. It combines the speed of a Command Line Interface (CLI) with the convenience of a Graphical User Interface (GUI).

## Quick Start

1.  **Install Java:** Ensure you have Java 17 or above.
2.  **Download:** Get the latest `.jar` file from [here](https://github.com/AY2425S2-CS2103T-T10-3/tp/releases).
3.  **Setup:** Copy the `.jar` to your desired home folder.
4.  **Run:** Open a command terminal (PowerShell, Terminal) and navigate to the folder. Execute `java -jar BetterCallTA.jar`.
5.  **Use:** Enter commands in the GUI's command box.

## Key Features

| Command             | Description                                          | Example                                                                                                 |
| ------------------- | ---------------------------------------------------- | ------------------------------------------------------------------------------------------------------- |
| `add`               | Adds a new student.                                  | `add /id A0123456E /name James Ho /phone 22224444 /email jamesho@example.com /course CS2103T`            |
| `list`              | Lists all students.                                 | `list`                                                                                                  |
| `edit`              | Edits an existing student's information.              | `edit /id A0123456N /phone 91234567 /email johndoe@example.com`                                        |
| `find`              | Finds students by name.                               | `find James Jake`                                                                                       |
| `delete`            | Deletes a student.                                  | `delete /id A3456712E`                                                                                 |
| `clear`             | Deletes all students.                                 | `clear`                                                                                                 |
| `exit`              | Exits the application.                               | `exit`                                                                                                  |
| `file /save`        | Saves data to a specified file.                         | `file /save CS2103T-T10-3`                                                                               |
| `file /load`        | Loads data from a specified file. (Without specifying the file extension)                        | `file /load CS2103T-T10-3`                                                                               |
| `file /list all`    | Lists all available save files. (Without displaying the file extension)                        | `file /list all`                                                                                       |
| `resetRecords`      | Resets attendance and participation records.            | `resetRecords`                                                                                        |
| `help`              | Shows help message.                                  | `help`                                                                                                  |

For more detailed documentation on the usage of commands in BetterCallTA, refer to our [User Guide](https://ay2425s2-cs2103t-t10-3.github.io/tp/UserGuide.html).

## Student Data

Each student's information includes:

| Field         | Description                                     |
| ------------- | ----------------------------------------------- |
| **ID** | Unique identifier (NUS format)                  |
| **Name** | Student's name                                  |
| **Phone** | Phone number                                    |
| **Email** | Email address                                   |
| **Course** | NUS course code                                 |
| **Attendance**| Class participation status                      |
| **Participation**| Engagement level                              |
| **Grade** | Predicted grade                                 |
| **Notes** | Additional details                              |

## Important Notes

* Commands are case-sensitive.
* Parameters can be entered in any order.
* See the full User Guide for detailed information and formatting rules.

## FAQ

* **Q:** How do I transfer my data to another computer?
    * **A:** Copy the save files from `[home folder]/data/`.

## Known Issues

* GUI may open off-screen with multiple displays.
* Minimized Help Window may not restore properly.

## Glossary

| Term | Definition                                                                |
| ---- | ------------------------------------------------------------------------- |
| TA   | Teaching Assistant                                                        |
| CLI  | Command Line Interface                                                    |
| GUI  | Graphical User Interface                                                    |
| NUS  | National University of Singapore                                          |

For the complete User Guide, refer to the original documentation.
