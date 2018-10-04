package seedu.addressbook.logic;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.commands.CommandResult_Menu;
import seedu.addressbook.commands.Command_Menu;
import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.RMS_menu;
import seedu.addressbook.data.person.ReadOnlyMenus;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.parser.Parser;
import seedu.addressbook.parser.Parser_RMSMenu;
import seedu.addressbook.storage.StorageFile;
import seedu.addressbook.storage.StorageFile_RMS;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Represents the main Logic of the AddressBook.
 */
public class Logic_RMS {


    private StorageFile_RMS Menu_storage;
    private RMS_menu menuBook;

    /** The list of person shown to the user most recently.  */
    private List<? extends ReadOnlyMenus> lastShownList = Collections.emptyList();

    public Logic_RMS() throws Exception{
        setStorage(initializeStorage());
        setRMS_menu(Menu_storage.load());
    }

    Logic_RMS(StorageFile_RMS storageFile_rms, RMS_menu menuBook){
        setStorage(storageFile_rms);
        setRMS_menu(menuBook);
    }

    void setStorage(StorageFile_RMS storage_rms){
        this.Menu_storage = Menu_storage;
    }

    void setRMS_menu(RMS_menu menuBook){
        this.menuBook = menuBook;
    }

    /**
     * Creates the StorageFile object based on the user specified path (if any) or the default storage path.
     * @throws StorageFile_RMS.InvalidStorageFilePathException if the target file path is incorrect.
     */
    private StorageFile_RMS initializeStorage() throws StorageFile_RMS.InvalidStorageFilePathException {
        return new StorageFile_RMS();
    }

    public String getStorageFilePath() {
        return Menu_storage.getPath();
    }

    /**
     * Unmodifiable view of the current last shown list.
     */
    public List<ReadOnlyMenus> getLastShownList() {
        return Collections.unmodifiableList(lastShownList);
    }

    protected void setLastShownList(List<? extends ReadOnlyMenus> newList) {
        lastShownList = newList;
    }

    /**
     * Parses the user command, executes it, and returns the result.
     * @throws Exception if there was any problem during command execution.
     */
    public CommandResult_Menu execute(String userCommandText) throws Exception {
        Command_Menu command = new Parser_RMSMenu().parseCommand_Menu(userCommandText);
        CommandResult_Menu result = execute(command);
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
    private CommandResult_Menu execute(Command_Menu command) throws Exception {
        command.setData(menuBook, lastShownList);
        CommandResult_Menu result = command.execute();
        Menu_storage.save(menuBook);
        return result;
    }

    /** Updates the {@link #lastShownList} if the result contains a list of Persons. */
    private void recordResult(CommandResult_Menu result) {
        final Optional<List<? extends ReadOnlyMenus>> menuList = result.getRelevantMenus();
        if (menuList.isPresent()) {
            lastShownList = menuList.get();
        }
    }
}
