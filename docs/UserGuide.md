---
layout: page
title: User Guide
---

ContaX is a **desktop application for managing your Contacts and Schedule**. It is a powerful tool *optimized for use via a Command Line Interface* (CLI), while incorporating Graphical User Interface (GUI) elements to make it user-friendly. If you are able to type fast, ContaX is capable of helping you manage your contacts and schedule more efficiently than traditional GUI applications, allowing you to shift your focus to other things.

Broadly speaking, ContaX consists of an *Address Book* for managing Contacts, and a *Schedule* for managing Appointments.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `ContaX.jar` from [here](https://github.com/AY2122S2-CS2103-W17-1/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for ContaX.

1. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * **`list`** : Lists all contacts.

   * **`add`**`n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.

   * **`delete`**`3` : Deletes the 3rd contact shown in the current list.

   * **`clear`** : Deletes all contacts.

   * **`exit`** : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* If a parameter is expected only once in the command but you specified it multiple times, only the last occurrence of the parameter will be taken.<br>
  e.g. if you specify `p/12341234 p/56785678`, only `p/56785678` will be taken.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

</div>

### Common Date and Time Syntax

<div markdown="block" class="alert alert-secondary">

**:information_source: This section details the format(s) that date and time inputs are expected to be in.**<br>

* All date inputs must conform to the following format: `dd-mm-yyyy`.

| Field | Description |
| - | - |
| `dd` | Day of the month. |
| `mm` | Numerical representation of the month, from `1` to `12`. |
| `yyyy` | Year in the full 4-digit format. |

* All time inputs must conform to the following format: `hh:mm`

| Field | Description |
| - | - |
| `hh` | Hour of the day, in 24-hour format. |
| `mm` | Minute of the hour, from `00` to `59`. |

</div>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Onboarding guide
When you run the program for the first time, you will be prompted to take a quick tour of the ContaX's basic functions guided through a series of step-by-step instructions.

Example of instructions:
* Input command
![](images/OnboardingCommand.png) 
* List contacts
![](images/OnboardingList.png)

### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

#### Extension on Find contact by type : `find` `t/`

Locates persons by different search types

Format: `find KEYWORD [MORE_KEYWORDS] [t/SEARCH_TYPE]`

* The search is case-insensitive. e.g. hans will match Hans
* The order of the keywords does not matter. e.g. Hans Bo will match Bo Hans
* `SEARCH_TYPE` must match one of the following:
  * Name
  * Address
  * Phone
  * Email
* If `SEARCH_TYPE` is not specified, default search type is by `Name`.
* Persons matching at least one keyword will be returned

Examples:
* `find t/Address k/Garden`
  * Lists all contact with address contains keyword "Garden"
* `find t/Phone k/87438807`
  * Lists all contact with phone matches keyword "87438807"


### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Creating a Tag : `addtag`

Creates a new tag in ContaX.

Format: `addtag n/TAGNAME`

* The `TAGNAME` parameter **must** be specified.
* If the `TAGNAME` already exists (case-insensitive), the tag will not be created.

Examples:
* `addtag n/Potential Clients` Creates a tag named *Potential Clients* in the address book (if it does not exist).

### Editing a Tag : `edittag`

Edits an existing tag in ContaX.

Format: `edittag f/OLD_TAGNAME t/NEW_TAGNAME`

* All parameters **must** be specified.
* Changes the name of a tag`OLD_TAGNAME` to `NEW_TAGNAME`.
* `OLD_TAGNAME` and `NEW_TAGNAME` are case-insensitive.
* An error will be thrown if either `OLD_TAGNAME` cannot be found or `NEW_TAGNAME` already exists in ContaX.

Examples:
* `edittag f/New Clients t/Prospective Clients` Changes the name of the tag *New Clients* to *Prospective Clients*. Command will be ignored if the tag *New Clients* does not exist in the first place or *Prospective Clients* already exists in Contax.

### Listing All Tags : `listtags`

Shows a list of all tags in ContaX.

Format: `listtags`

**Example output**

![List Tags](images/ListTag.png)

### Deleting a Tag : `deletetag`

Deletes the specified tag in ContaX.

Format: `deletetag n/TAGNAME`

* The `TAGNAME` parameter **must** be specified.
* Deletion is case-insensitive e.g. `students` is the same as `Students`.
* When the tag is deleted, contacts that contain this tag will have the tag removed.
* An error will be thrown if the specified `TAGNAME` cannot be found.

Examples:
* `deletetag n/friends` Deletes the tag named *friends* and disassociates any contacts that have the *friends* tag.

### Finding Contacts by Tag : `findByTag`

Finds persons whose tags match the given tag names.

Format: `findByTag t/TAGNAME`

* The `TAGNAME` parameter **must** be specified.
* Search is case-insensitive e.g. `clients` is the same as `Clients`.
* An error will be thrown if the specified `TAGNAME` does not exist.

Examples:
* `findByTag t/friends` Displays the contact information of contacts who have the *friends* tag.
 
![Find Tags](images/FindTag.png)

### Creating an Appointment : `addAppointment`

Creates an appointment in the schedule.

Format: `addAppointment n/NAME d/DATE t/TIME l/DURATION [p/PERSON]`

* Creates a new appointment with the specified parameters.
* All parameters except `PERSON` **must** be specified.
* The `NAME` parameter must be **non-empty**.
* The `DATE` parameter denotes the starting date, and **must conform to the [Common Date Formats](#common-date-and-time-syntax)**.
* The `TIME` parameter denotes the starting time, and **must conform to the [Common Time Formats](#common-date-and-time-syntax)**.
* The `DURATION` parameter is the duration of the appointment in *minutes*, and **must be a positive number**.
* The `PERSON` parameter, if specified, **must be a positive integer**, and refers to the index number shown in the displayed person list.
<div markdown="span" class="alert alert-warning">:rotating_light: **Important Note:**
The operation will fail if the appointment **overlaps** with another appointment.
</div>

Examples:
* `addAppointment n/Bi-Weekly Meeting d/14-02-2022 t/11:00 l/60` Creates a *one-hour* appointment named *"Bi-Weekly Meeting"* on *14th Feb 2022* at *11:00 AM*, associated with nobody in the contact list.
* `addAppointment n/Contract Signing With Charlie d/22-10-2022 t/16:30 p/1 l/300` Creates a *5-hour* appointment named *"Contract Signing With Charlie"* on *22nd Oct 2022* at *4:30 PM*, associated with the *first* person in the contact list.

### Listing All Appointments : `listAppointments`

Shows a list of all appointments in the schedule.

Format: `listAppointments`

**Example output**

![List Appointments Wireframe](images/wireframes/ListAppointments.png)

### Deleting an Appointment : `deleteAppointment`

Deletes an appointment previously created in the schedule.

Format: `deleteAppointment INDEX`

* Deletes the appointment that is at `INDEX` in the appointment list.
* The `INDEX` parameter **must be a positive integer**, and refers to the index number shown in the **displayed appointment list**.

Examples:
* `deleteAppointment 2` Deletes the *second* appointment in the list of appointments.

### Editing an Appointment : `editAppointment`

Edits an appointment previously created in the schedule.

Format: `editAppointment INDEX [n/NAME] [d/DATE] [t/TIME] [p/PERSON] [l/DURATION]`

* Edits the appointment that is at `INDEX` in the appointment list, setting the supplied parameter(s) to the supplied value(s).
* The `INDEX` parameter **must be a positive integer**, and refers to the index number shown in the **displayed appointment list**.
* At least one of the optional parameters must be supplied, otherwise the command will be ignored.
* If supplied, the optional parameters must conform to the following rules:
    * The `NAME` parameter must be non-empty.
    * The `DATE` parameter denotes the starting date, and **must conform to the [Common Date Formats](#common-date-and-time-syntax)**.
    * The `TIME` parameter denotes the starting time, and **must conform to the [Common Time Formats](#common-date-and-time-syntax)**.
    * The `PERSON` parameter must be a positive integer, and refers to the index number shown in the displayed person list.
    * The `DURATION` parameter is the duration of the appointment in *minutes*, and **must be a positive number**.
<div markdown="span" class="alert alert-warning">:rotating_light: **Important Note:**
The operation will fail if the modified appointment **overlaps** with another appointment.
</div>

Examples:
* `editAppointment 6 l/300` Edits the *6th* appointment in the list of appointments to have a duration of *5 hours*. No other properties are changed.
* `editAppointment 2 n/Call Juliet t/13:45` Edits the *second* appointment in the list of appointments to have the name *"Call Juliet"* and changes the time to *1:45 PM*. No other properties are changed.

### Listing Appointments Within A Period : `appointmentsBetween`

Lists all the appointments between a starting time and ending time.

Format: `appointmentsBetween sd/STARTDATE st/STARTTIME ed/ENDDATE et/ENDTIME`

* The starting time **must be before** the ending time.
* The `STARTDATE` parameter denotes the *starting date* of the period.
* The `STARTTIME` parameter denotes the *starting time* on the starting date for the period.
* The `ENDDATE` parameter denotes the *ending date* of the period.
* The `ENDTIME` parameter denotes the *ending time* on the ending date for the period.
* Both `STARTDATE` and `ENDDATE` **must conform to the [Common Date Formats](#common-date-and-time-syntax)**.
* Both `STARTTIME` and `ENDTIME` **must conform to the [Common Time Formats](#common-date-and-time-syntax)**.

Example:
* `appointmentsBetween sd/21-10-2022 st/12:00 ed/23-10-2022 et/17:00` Lists all appointments between *21 October 2022, 12 PM* and *23 October 2022, 5PM*.

**Example Output:**

![Appointments Between Wireframe](images/wireframes/AppointmentsBetween.png)

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

ContaX data are saved in the hard disk automatically after any command that changes the data in JSON format at `[JAR file location]/data/contax.json`.

### Exporting the data: `exportcsv`

Exports the current list of contacts as a CSV file that can be imported through ContaX or other contact software such as Google/Outlook

Format: `exportcsv [EXPORTTYPE]`
* Exports CSV as per the specified format.
* If `EXPORTTYPE` is left empty, it will default to `EXPORTTYPE = 1` and export in custom ContaX format
* `EXPORTTYPE` options are
   * `EXPORTTYPE=1` for ContaX format
   * `EXPORTTYPE=2` for Google Contacts format
   * `EXPORTTYPE=3` for Microsoft Outlook format
* File will be saved on the directory `[JAR file location]/data/addressbook.csv`

Examples:
* `exportcsv 2`: Exports the current address book as a CSV file ready to be uploaded onto Google Contacts 
### Editing the data file

ContaX data are saved as a JSON file `[JAR file location]/data/contax.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, ContaX will discard all data and start with an empty data file at the next run.
</div>

### Importing data

In order to import data from external sources into ContaX, there are the following options:

* Replacing `contax.json` file with a valid ContaX formatted generated by another system with ContaX
* [Simple CSV import (GUI-based option)](#simple-csv-import)
* [Advanced CSV import (Command based)](#advanced-csv-import--importcsv)

### Simple CSV Import

Select the `Import` button under `File->Import` on the top bar.

!['basic import UI'](images/BasicImportUI.png)

Upon clicking, you will be prompted to select .csv or .json files. Accessing import via the top menu will allow users to import their task and appointments list as JSON/CSV files that were generated by another instance of ContaX.

### Advanced CSV Import : `importcsv`

Format: `importcsv [n/COLUMNNUM] [p/COLUMN_PERSON] [e/COLUMN_EMAIL] [a/COLUMN_ADDRESS] [t/COLUMN_TAGS]`

* Each argument represents the corresponding attribute for a person. 
  * n = Name
  * p = Phone
  * e = Email
  * a = Address
  * t = Tagged
* If any argument is omitted, it will follow the next available column based on the default sequence of
  Name - Phone - Email - Address - Tagged
* This user guide will be updated later on with templates for importing for the following contact csv formats:
  * Google Contacts
  * Outlook Contacts

Examples:
* `importcsv n/2 p/3 e/5 a/6 t/4`
  * Reads from a CSV file treating the 2nd row as names, 3rd row as phone numbers, 5th row as email addresses, 4th row as tagged.
* `importcsv n/2`
  * Reads from CSV treating the 2nd row as names, 3rd row as phone numbers, 4th row as email address, 5th row as tagged.

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._


### Batch and Range Commands

### Operate on Contacts by Conditional Clause : `batch`

If the given condition is satisfied, it returns a group of contacts

Format: `batch COMMAND where/CONDITION`

* The commands include list, edit, delete
* The `CONDITION` field must conform to the following syntax: `TERM OP TERM`
  * Valid operators for the `OP` field are `>`, `<`, `=`, `!=`, `LIKE`
  * A `TERM` may be an attribute of a person or a constant value
Examples:
* `batch delete where/name LIKE A%` 
  * Deletes all persons whose name start with A (case-sensitive)
* `batch Edit p/87438806 where/ p/Phone = 87438807 `
    * edit contact with phone matches keyword 87438807 change to 87438806

### Operate on Contacts within Range : `range`

Perform actions on a group of contacts

Format: `range COMMAND from/INDEX_FROM to/INDEX_TO`

* Performs the specified COMMAND on all contacts between the specified range of `INDEX_FROM` to `INDEX_TO`
* The allowed operations in `COMMAND` are
  * list
  * edit
  * delete
* The `INDEX_FROM` and `INDEX_TO` parameters must be **positive integers**, and refer to the index number shown in the **displayed contact list**
* `INDEX_FROM` must be less than `INDEX_TO` must be supplied, otherwise the command will perform no operation
* `COMMAND` must be a valid command
* The resultant effect of the command is dependent on the performed action

Examples:
* `range edit e/johndoe@example.com from/6 to/10`
  * Sets the email address of the 6th to 10th contacts in the address book to `johndoe@example.com`
* `range delete from/2 to/3`
  * Deletes the 2nd and 3rd contacts in the Address Book

### Chaining Commands: `&&`

Perform actions on a group of contacts

Format: `COMMAND_A && COMMAND_B`

* Call multiple commands
* The syntax of `COMMAND_A` and `COMMAND_B` must be correct
* A valid command must be supplied before and after the `&&` operator, otherwise the command will fail
Examples:
* `editAppointment 6 l/360 && listAppointments`
    * Edits the 6th to 10th appointment in the list of appointments to have a duration of 6 hours. Then list all appointments in the Schedule
* `deleteAppointment 2 && addAppointment n/Contract Signing With Charlie d/22-10-2022 t/16:30 p/1 l/300`
    * Deletes the 2nd appointment in the list of appointments.
      Creates a 5-hour appointment named "Contract Signing With Charlie" on 22nd Oct 2022 at 4:30 PM, associated with the first person in the contact list



--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List** | `list`
**Add Tag** | `addtag n/TAGNAME` <br> e.g., `addtag n/Potential Clients`
**Edit Tag** | `edittag f/OLD_TAGNAME t/NEW_TAGNAME` <br> e.g., `edittag f/New Clients t/Prospective Clients`
**List Tags** | `listtags`
**Delete Tag** | `deletetag n/TAGNAME` <br> e.g., `deletetag n/friends`
**Find Contacts By Tag** | `findByTag t/TAGNAME` <br> e.g., `findByTag t/friends`
**Add Appointment** | `addAppointment n/NAME d/DATE t/TIME l/DURATION p/PERSON`<br> e.g., `addAppointment n/Call Bob d/14-02-2022 t/11:00 p/2 l/60`
**List Appointments** | `listAppointments`
**Delete Appointment** | `deleteAppointment INDEX`<br> e.g., `deleteAppointment 2`
**Edit Appointment** | `editAppointment INDEX [n/NAME] [d/DATE] [t/TIME] [p/PERSON] [l/DURATION]`<br> e.g., `editAppointment 2 n/Call Juliet t/13:45`
**List Appointments Within Period** | `appointmentsBetween sd/STARTDATE st/STARTTIME ed/ENDDATE et/ENDTIME` <br> e.g. `appointmentsBetween sd/21-10-2022 st/12:00 ed/23-10-2022 et/17:00`
**Help** | `help`
**Export CSV** | `exportcsv [EXPORTTYPE]`<br> e.g., `exportcsv 2`
**Import CSV** | `importcsv [n/COLUMNNUM] [p/COLUMN_PERSON] [e/COLUMN_EMAIL] [a/COLUMN_ADDRESS] [t/COLUMN_TAGS]` <br> e.g., `importCSV n/2 p/3 e/5 a/6 t/4`
**Operate on Contacts by Conditional Clause** | `batch COMMAND where/CONDITION` <br> e.g., `batch Edit p/87438806 where/ p/Phone = 87438807`
**Operate on Contacts within Range** | `range COMMAND from/INDEX to/INDEX` <br> e.g., `range edit e/johndoe@example.com from/6 to/10`
**Chaining Commands** | `COMMAND_A && COMMAND_B` <br> e.g., `editAppointment 6 l/360 && listAppointments`
