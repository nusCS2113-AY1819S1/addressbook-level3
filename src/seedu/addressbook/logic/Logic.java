package seedu.addressbook.logic;

import static seedu.addressbook.common.Messages.MESSAGE_INSUFFICIENT_PRIVILEGE;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.commands.IncorrectCommand;
import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.ExamBook;
import seedu.addressbook.data.StatisticsBook;
import seedu.addressbook.data.person.ReadOnlyExam;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.parser.Parser;
import seedu.addressbook.privilege.Privilege;
import seedu.addressbook.storage.Storage;
import seedu.addressbook.storage.StorageFile;

/**
 * Represents the main Logic of the AddressBook.
 */
public class Logic {
    private Storage storage;
    private AddressBook addressBook;
    private Privilege privilege;
    private ExamBook examBook;
    private StatisticsBook statisticsBook;

    /** The list of person shown to the user most recently.  */
    private List<? extends ReadOnlyPerson> lastShownList = Collections.emptyList();

    /** The list of exam shown to the user most recently.  */
    private List<? extends ReadOnlyExam> lastShownExamList = Collections.emptyList();

    /**
     * Signals that an operation requiring password authentication failed.
     */
    public static class WrongPasswordEnteredException extends Exception {}

    public Logic() throws Exception {
        privilege = new Privilege();
        setStorage(initializeStorage());
        setAddressBook(storage.load());
        setExamBook(storage.loadExam());
        setStatisticsBook(storage.loadStatistics());
    }

    Logic(Storage storageFile, AddressBook addressBook, ExamBook examBook, StatisticsBook statisticsBook, Privilege
            privilege) {
        this(storageFile, addressBook, examBook, statisticsBook);
        setPrivilege(privilege);
    }

    Logic(Storage storageFile, AddressBook addressBook, ExamBook examBook, StatisticsBook statisticsBook) {
        setStorage(storageFile);
        setAddressBook(addressBook);
        setExamBook(examBook);
        setStatisticsBook(statisticsBook);
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public void setAddressBook(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }

    public void setExamBook(ExamBook examBook) {
        this.examBook = examBook;
    }

    public void setStatisticsBook(StatisticsBook statisticsBook) {
        this.statisticsBook = statisticsBook;
    }

    /**
     * Creates the StorageFile object based on the user specified path (if any) or the default storage path.
     * @throws StorageFile.InvalidStorageFilePathException if the target file path is incorrect.
     * @throws StorageFile.InvalidInitialisationException if the JAXB set up has error
     */
    private StorageFile initializeStorage()
            throws StorageFile.InvalidStorageFilePathException,
            StorageFile.InvalidInitialisationException {
        return new StorageFile();
    }

    public String getStorageFilePath() {
        return storage.getPath();
    }

    public String getStorageFilePathExam() {
        return storage.getPathExam();
    }

    public String getStorageFilePathStatistics() {
        return storage.getPathStatistics();
    }

    /**
     * Unmodifiable view of the current last shown list.
     */
    public List<ReadOnlyPerson> getLastShownList() {
        return Collections.unmodifiableList(lastShownList);
    }

    protected void setLastShownList(List<? extends ReadOnlyPerson> newList) {
        lastShownList = newList;
    }

    /**
     * Unmodifiable view of the current last shown exams list.
     */
    public List<ReadOnlyExam> getLastShownExamList() {
        return Collections.unmodifiableList(lastShownExamList);
    }

    protected void setLastShownExamList(List<? extends ReadOnlyExam> newList) {
        lastShownExamList = newList;
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
     * Executes the command, updates storage if the command can potentially mutate data,
     * and returns the result.
     *
     * @param command user command
     * @return result of the command
     * @throws Exception if there was any problem during command execution.
     */
    private CommandResult execute(Command command) throws Exception {
        final CommandResult result;

        command.setData(addressBook, lastShownList, lastShownExamList, privilege, examBook, statisticsBook);

        /** Checking instanceof IncorrectCommand to prevent overwriting the message of an incorrect command*/
        if (privilege.isAllowedCommand(command) || (command instanceof IncorrectCommand)) {
            result = command.execute();
        } else {
            result = new IncorrectCommand (String.format(MESSAGE_INSUFFICIENT_PRIVILEGE,
                            privilege.getRequiredPrivilegeAsString(command),
                            privilege.getLevelAsString())).execute();
        }

        if (command.isMutating()) {
            storage.save(addressBook);
            storage.saveExam(examBook);
            storage.saveStatistics(statisticsBook);
        }
        return result;
    }

    /** Updates the {@link #lastShownList} if the result contains a list of Persons.
     *  Updates the {@link #lastShownExamList} if the result contains a list of Exams.
     * */
    private void recordResult(CommandResult result) {
        final Optional<List<? extends ReadOnlyPerson>> personList = result.getRelevantPersons();
        final Optional<List<? extends ReadOnlyExam>> examList = result.getRelevantExams();
        if (personList.isPresent()) {
            lastShownList = personList.get();
        } else if (examList.isPresent()) {
            lastShownExamList = examList.get();
        }
    }
}
