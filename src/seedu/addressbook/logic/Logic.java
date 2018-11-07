package seedu.addressbook.logic;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.Schedule;
import seedu.addressbook.data.person.UniquePersonList;
import seedu.addressbook.parser.Parser;
import seedu.addressbook.storage.StorageFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Represents the main Logic of the AddressBook.
 */
public class Logic {


    private StorageFile storage;
    private AddressBook addressBook;

    /** The list of person shown to the user most recently.  */
    private List<? extends ReadOnlyPerson> lastShownList = Collections.emptyList();

    /** The list of editable person shown to the user most recently.  */
    private List<ReadOnlyPerson> editableLastShownList = Collections.emptyList();

    /** The set of appointments the selected person has that is shown to the user most recently. */
    //private Set<? extends Schedule> appointmentOfPerson = Collections.emptySet();

    public Logic() throws Exception{
        setStorage(initializeStorage());
        setAddressBook(storage.load());
    }

    Logic(StorageFile storageFile, AddressBook addressBook){
        setStorage(storageFile);
        setAddressBook(addressBook);
    }

    void setStorage(StorageFile storage){
        this.storage = storage;
    }

    void setAddressBook(AddressBook addressBook){
        this.addressBook = addressBook;
    }

    /**
     * Creates the StorageFile object based on the user specified path (if any) or the default storage path.
     * @throws StorageFile.InvalidStorageFilePathException if the target file path is incorrect.
     */
    private StorageFile initializeStorage() throws StorageFile.InvalidStorageFilePathException {
        return new StorageFile();
    }

    public String getStorageFilePath() {
        return storage.getPath();
    }

    /**
     * Unmodifiable view of the current last shown list.
     */
    public List<ReadOnlyPerson> getLastShownList() {
        return Collections.unmodifiableList(lastShownList);
    }

    /**
     * modifiable list of the current last shown list.
     */
    public List<ReadOnlyPerson> getEditableLastShownList() {
        return editableLastShownList;
    }

    protected void setLastShownList(List<? extends ReadOnlyPerson> newList) {
        lastShownList = newList;
    }

    protected void setEditableLastShownList(List<ReadOnlyPerson> newList) {
        editableLastShownList = newList;
    }

    /**
     * Parses the user command, executes it, and returns the result.
     * @throws Exception if there was any problem during command execution.
     */
    public CommandResult execute(String userCommandText) throws Exception {
        Command command = new Parser().parseCommand(userCommandText);
        CommandResult result = execute(command);
        recordResult(result);
        return result;
    }

    /**
     * Executes the command, updates storage, and returns the result.
     *
     * @param command user command
     * @return result of the command
     * @throws Exception if there was any problem during command execution.
     */
    private CommandResult execute(Command command) throws Exception {
        command.setData(addressBook, lastShownList, editableLastShownList);
        CommandResult result = command.execute();
        storage.save(addressBook);
        return result;
    }

    /** Updates the {@link #lastShownList} and {@link #editableLastShownList}if the result contains a list of Persons.
     * as well as the set of appointment for the selected person*/
    private void recordResult(CommandResult result) {
        final Optional<List<? extends ReadOnlyPerson>> personList = result.getRelevantPersons();
        //final Optional<Set<? extends Schedule>> scheduleList = result.getRelevantAppointments();
        final Optional<List<ReadOnlyPerson>> editablePersonList = result.getEditableRelevantPersons();
        if (personList.isPresent()) {
            lastShownList = personList.get();
        }
        if (editablePersonList.isPresent()) {
            editableLastShownList = editablePersonList.get();
        }

        //else if(scheduleList.isPresent()) {
        //    //appointmentOfPerson = scheduleList.get();
        //}
        /*final Optional<Set<? extends Schedule>> appointmentSet = result.getRelevantAppointments();
        if (appointmentSet.isPresent()) {
            lastShownList = personList.get();
        }

    /**
            * Obtain the latest detail the target person in the last shown list from the given arguments.
            *
            * @throws IndexOutOfBoundsException if the target index is out of bounds of the last viewed listing
     */
    }
}
