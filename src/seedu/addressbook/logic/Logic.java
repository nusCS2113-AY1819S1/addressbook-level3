package seedu.addressbook.logic;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.Rms;
import seedu.addressbook.data.employee.Attendance;
import seedu.addressbook.data.employee.ReadOnlyEmployee;
import seedu.addressbook.data.member.ReadOnlyMember;
import seedu.addressbook.data.menu.ReadOnlyMenus;
import seedu.addressbook.data.order.ReadOnlyOrder;
import seedu.addressbook.parser.Parser;
import seedu.addressbook.storage.StorageFile;

/**
 * Represents the main Logic of the Rms.
 */
public class Logic {

    private StorageFile storage;
    //@@author px1099
    private Rms rms;

    //@@author kangmingtay
    /**
     * The list of member shown to the user most recently.
     */
    private List<? extends ReadOnlyMember> lastShownMemberList = Collections.emptyList();

    //@@author SalsabilTasnia
    /**
     * The list of menu shown to the user most recently.
     */
    private List<? extends ReadOnlyMenus> lastShownMenuList = Collections.emptyList();

    //@@author px1099
    /**
     *  The list of order shown to the user most recently.
     */
    private List<? extends ReadOnlyOrder> lastShownOrderList = Collections.emptyList();

    //@@author kianhong95
    /**
     * The list of employee shown to the user most recently.
     */
    private List<? extends ReadOnlyEmployee> lastShownEmployeeList = Collections.emptyList();

    /**
     * The list of employee shown to the user most recently.
     */
    private List<? extends Attendance> lastShownAttendanceList = Collections.emptyList();

    //@@author
    public Logic() throws Exception {
        setStorage(initializeStorage());
        setRms(storage.load());
    }

    Logic(StorageFile storageFile, Rms rms) {
        setStorage(storageFile);
        setRms(rms);
    }

    public void setStorage(StorageFile storage) {
        this.storage = storage;
    }

    public void setRms(Rms rms) {
        this.rms = rms;
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

    //@@author kangmingtay
    /**
     * Unmodifiable view of the current last shown member list.
     */
    public List<ReadOnlyMember> getLastShownMemberList() {
        return Collections.unmodifiableList(lastShownMemberList);
    }

    //@@author kianhong95
    /**
     * Unmodifiable view of the current last shown order list.
     */
    public List<ReadOnlyEmployee> getLastShownEmployeeList() {
        return Collections.unmodifiableList(lastShownEmployeeList);
    }

    /**
     * Unmodifiable view of the current last shown order list.
     */
    public List<Attendance> getLastShownAttendanceList() {
        return Collections.unmodifiableList(lastShownAttendanceList);
    }

    //@@author SalsabilTasnia
    /**
     * Unmodifiable view of the current last shown menu list.
     */
    public List<ReadOnlyMenus> getLastShownMenuList() {
        return Collections.unmodifiableList(lastShownMenuList);
    }

    //@@author px1099
    /**
     * Unmodifiable view of the current last shown order list.
     */
    public List<ReadOnlyOrder> getLastShownOrderList() {
        return Collections.unmodifiableList(lastShownOrderList);
    }

    //@@author SalsabilTasnia
    protected void setLastShownMenuList(List<? extends ReadOnlyMenus> newList) {
        lastShownMenuList = newList;
    }

    //@@author px1099
    protected void setLastShownOrderList(List<? extends ReadOnlyOrder> newList) {
        lastShownOrderList = newList;
    }

    //@@author kangmingtay
    protected void setLastShownMemberList(List<? extends ReadOnlyMember> newList) {
        lastShownMemberList = newList;
    }

    //@@author kianhong95
    protected void setLastShownEmployeeList(List<? extends ReadOnlyEmployee> newList) {
        lastShownEmployeeList = newList;
    }

    protected void setLastShownAttendanceList(List<? extends Attendance> newList) {
        lastShownAttendanceList = newList;
    }

    //@@author
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
        command.setData(rms,
                lastShownMenuList,
                lastShownOrderList,
                lastShownMemberList,
                lastShownEmployeeList);
        CommandResult result = command.execute();
        storage.save(rms);
        return result;
    }

    //@@author px1099
    /**
     * Updates the last shown lists if the result contains a list of result Objects.
     */
    private void recordResult(CommandResult result) {
        final Optional<List<? extends ReadOnlyMenus>> menuList = result.getRelevantMenus();
        final Optional<List<? extends ReadOnlyOrder>> orderList = result.getRelevantOrders();
        final Optional<List<? extends ReadOnlyMember>> memberList = result.getRelevantMember();
        final Optional<List<? extends ReadOnlyEmployee>> employeeList = result.getRelevantEmployee();
        if (menuList.isPresent()) {
            lastShownMenuList = menuList.get();
        } else if (orderList.isPresent()) {
            lastShownOrderList = orderList.get();
        } else if (memberList.isPresent()) {
            lastShownMemberList = memberList.get();
        } else if (employeeList.isPresent()) {
            lastShownEmployeeList = employeeList.get();
        }
    }

    //@@author
}

