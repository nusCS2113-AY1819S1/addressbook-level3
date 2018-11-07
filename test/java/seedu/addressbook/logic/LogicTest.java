package seedu.addressbook.logic;

import static junit.framework.TestCase.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.commands.ExitCommand;
import seedu.addressbook.commands.HelpCommand;
import seedu.addressbook.commands.employee.EmployeeAddCommand;
import seedu.addressbook.commands.employee.EmployeeClockInCommand;
import seedu.addressbook.commands.employee.EmployeeClockOutCommand;
import seedu.addressbook.commands.employee.EmployeeDeleteCommand;
import seedu.addressbook.commands.employee.EmployeeEditCommand;
import seedu.addressbook.commands.member.MemberAddCommand;
import seedu.addressbook.commands.member.MemberDeleteCommand;
import seedu.addressbook.commands.menu.MenuAddCommand;
import seedu.addressbook.commands.menu.MenuDeleteCommand;
import seedu.addressbook.commands.menu.MenuFindCommand;
import seedu.addressbook.commands.menu.MenuListByTypeCommand;
import seedu.addressbook.commands.menu.MenuViewAllCommand;
import seedu.addressbook.commands.order.DraftOrderClearCommand;
import seedu.addressbook.commands.order.DraftOrderConfirmCommand;
import seedu.addressbook.commands.order.DraftOrderEditCustomerCommand;
import seedu.addressbook.commands.order.DraftOrderEditDishCommand;
import seedu.addressbook.commands.order.OrderAddCommand;
import seedu.addressbook.commands.order.OrderClearCommand;
import seedu.addressbook.commands.order.OrderDeleteCommand;
import seedu.addressbook.commands.statistics.StatsMenuCommand;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.Rms;
import seedu.addressbook.data.employee.Attendance;
import seedu.addressbook.data.employee.Employee;
import seedu.addressbook.data.employee.EmployeeEmail;
import seedu.addressbook.data.employee.EmployeeName;
import seedu.addressbook.data.employee.EmployeePhone;
import seedu.addressbook.data.employee.EmployeePosition;
import seedu.addressbook.data.employee.ReadOnlyEmployee;
import seedu.addressbook.data.member.Member;
import seedu.addressbook.data.member.MemberName;
import seedu.addressbook.data.member.Points;
import seedu.addressbook.data.member.ReadOnlyMember;
import seedu.addressbook.data.menu.Menu;
import seedu.addressbook.data.menu.MenuName;
import seedu.addressbook.data.menu.Price;
import seedu.addressbook.data.menu.ReadOnlyMenus;
import seedu.addressbook.data.menu.Type;
import seedu.addressbook.data.order.Order;
import seedu.addressbook.data.order.ReadOnlyOrder;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.tag.Tag;
import seedu.addressbook.storage.StorageFile;

public class LogicTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private StorageFile saveFile;
    private Rms rms;
    private Logic logic;

    @Before
    public void setup() throws Exception {
        saveFile = new StorageFile(saveFolder.newFile("testSaveFile.txt").getPath());
        rms = new Rms();
        saveFile.save(rms);
        logic = new Logic(saveFile, rms);
    }

    @Test
    public void constructor() {
        //Constructor is called in the setup() method which executes before every test, no need to call it here again.

        //Confirm the last shown list is empty
        assertEquals(Collections.emptyList(), logic.getLastShownList());
        assertEquals(Collections.emptyList(), logic.getLastShownMenuList());
        assertEquals(Collections.emptyList(), logic.getLastShownEmployeeList());
        assertEquals(Collections.emptyList(), logic.getLastShownAttendanceList());
        assertEquals(Collections.emptyList(), logic.getLastShownMemberList());
        assertEquals(Collections.emptyList(), logic.getLastShownOrderList());
    }

    @Test
    public void execute_invalid() throws Exception {
        String invalidCommand = "       ";
        assertCommandBehavior(invalidCommand,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the command and confirms that the result message is correct.
     * Both the 'address book' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, Rms, boolean, List)
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, Rms.empty(), false, Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the Logic object's state are as expected:<br>
     *      - the internal address book data are same as those in the {@code expectedRms} <br>
     *      - the internal 'last shown list' matches the {@code expectedLastList} <br>
     *      - the storage file content matches data in {@code expectedRms} <br>
     */
    private void assertCommandBehavior(String inputCommand,
                                       String expectedMessage,
                                       Rms expectedRms,
                                       boolean isRelevantPersonsExpected,
                                       List<? extends ReadOnlyPerson> lastShownList) throws Exception {

        //Execute the command
        CommandResult r = logic.execute(inputCommand);

        //Confirm the result contains the right data
        assertEquals(expectedMessage, r.feedbackToUser);
        assertEquals(r.getRelevantPersons().isPresent(), isRelevantPersonsExpected);
        if (isRelevantPersonsExpected) {
            assertEquals(lastShownList, r.getRelevantPersons().get());
        }

        //Confirm the state of data is as expected
        assertEquals(expectedRms, rms);
        assertEquals(lastShownList, logic.getLastShownList());
        assertEquals(rms, saveFile.load());
    }

    /**
     * Executes the Employee command and confirms that the result message is correct.
     * Both the 'rms' and the 'last shown list' are expected to be empty.
     * @see #assertEmployeeCommandBehavior(String, String, Rms, boolean, List)
     */
    private void assertEmployeeCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertEmployeeCommandBehavior(inputCommand, expectedMessage, Rms.empty(), false, Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the Logic object's state are as expected:<br>
     *      - the internal Rms data are same as those in the {@code expectedRms} <br>
     *      - the internal 'last shown list' matches the {@code expectedLastList} <br>
     *      - the storage file content matches data in {@code expectedRms} <br>
     */
    private void assertEmployeeCommandBehavior(String inputCommand,
                                               String expectedMessage,
                                               Rms expectedRms,
                                               boolean isRelevantEmployeesExpected,
                                               List<? extends ReadOnlyEmployee> lastShownList) throws Exception {

        //Execute the command
        CommandResult r = logic.execute(inputCommand);

        //Confirm the result contains the right data
        assertEquals(expectedMessage, r.feedbackToUser);
        assertEquals(r.getRelevantEmployee().isPresent(), isRelevantEmployeesExpected);
        if (isRelevantEmployeesExpected) {
            assertEquals(lastShownList, r.getRelevantEmployee().get());
        }

        //Confirm the state of data is as expected
        assertEquals(expectedRms, rms);
        assertEquals(lastShownList, logic.getLastShownEmployeeList());
        assertEquals(rms, saveFile.load());
    }

    /**
     * Executes the command and confirms that the result message is correct and assert
     * also confirms that the following three parts of the Logic object's state are as expected:<br>
     *      - the internal Rms data are same as those in the {@code expectedRms} <br>
     *      - the internal 'last shown list' matches the {@code expectedLastList} <br>
     *      - the storage file content matches data in {@code expectedRms} <br>
     */
    private void assertEmployeeAttendanceCommandBehavior(String inputCommand,
                                               String expectedMessage,
                                               Rms expectedRms,
                                               boolean isRelevantEmployeesExpected,
                                               boolean isRelevantAttendancesExpected,
                                               List<? extends ReadOnlyEmployee> lastShownEmployeeList,
                                               List<? extends Attendance> lastShownAttendanceList) throws Exception {

        //Execute the command
        CommandResult r = logic.execute(inputCommand);

        //Confirm the result contains the right data
        assertEquals(expectedMessage, r.feedbackToUser);
        assertEquals(r.getRelevantEmployee().isPresent(), isRelevantEmployeesExpected);
        assertEquals(r.getRelevantAttendance().isPresent(), isRelevantAttendancesExpected);
        if (isRelevantEmployeesExpected) {
            assertEquals(lastShownEmployeeList, r.getRelevantEmployee().get());
        }
        if (isRelevantAttendancesExpected) {
            assertEquals(lastShownAttendanceList, r.getRelevantAttendance().get());
        }

        //Confirm the state of data is as expected
        assertEquals(expectedRms, rms);
        assertEquals(lastShownEmployeeList, logic.getLastShownEmployeeList());
        assertEquals(lastShownAttendanceList, logic.getLastShownAttendanceList());
        assertEquals(rms, saveFile.load());
    }

    /**
     * Executes the Member command and confirms that the result message is correct.
     * Both the 'rms' and the 'last shown list' are expected to be empty.
     * @see #assertMemberCommandBehavior(String, String, Rms, boolean, List)
     */
    private void assertMemberCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertMemberCommandBehavior(inputCommand, expectedMessage, Rms.empty(), false, Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the Logic object's state are as expected:<br>
     *      - the internal address book data are same as those in the {@code expectedRms} <br>
     *      - the internal 'last shown list' matches the {@code expectedLastList} <br>
     *      - the storage file content matches data in {@code expectedRms} <br>
     */
    private void assertMemberCommandBehavior(String inputCommand,
                                             String expectedMessage,
                                             Rms expectedRms,
                                             boolean isRelevantMemberExpected,
                                             List<? extends ReadOnlyMember> lastShownList) throws Exception {

        //Execute the command
        CommandResult r = logic.execute(inputCommand);

        //Confirm the result contains the right data
        assertEquals(expectedMessage, r.feedbackToUser);
        assertEquals(r.getRelevantMember().isPresent(), isRelevantMemberExpected);
        if (isRelevantMemberExpected) {
            assertEquals(lastShownList, r.getRelevantMember().get());
        }

        //Confirm the state of data is as expected
        assertEquals(expectedRms, rms);
        assertEquals(lastShownList, logic.getLastShownMemberList());
        assertEquals(rms, saveFile.load());
    }

    /**
     * Executes the Order command and confirms that the result message is correct.
     * Both the 'rms' and the 'last shown list' are expected to be empty.
     * @see #assertOrderCommandBehavior(String, String, Rms, boolean, List)
     */
    private void assertOrderCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertOrderCommandBehavior(inputCommand, expectedMessage, Rms.empty(), false, Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the Logic object's state are as expected:<br>
     *      - the internal rms data are same as those in the {@code expectedRms} <br>
     *      - the internal 'last shown list' matches the {@code expectedLastList} <br>
     *      - the storage file content matches data in {@code expectedRms} <br>
     */
    private void assertOrderCommandBehavior(String inputCommand,
                                       String expectedMessage,
                                       Rms expectedRms,
                                       boolean isRelevantOrdersExpected,
                                       List<? extends ReadOnlyOrder> lastShownList) throws Exception {

        //Execute the command
        CommandResult r = logic.execute(inputCommand);

        //Confirm the result contains the right data
        assertEquals(expectedMessage, r.feedbackToUser);
        assertEquals(r.getRelevantOrders().isPresent(), isRelevantOrdersExpected);
        if (isRelevantOrdersExpected) {
            assertEquals(lastShownList, r.getRelevantOrders().get());
        }

        //Confirm the state of data is as expected
        assertEquals(expectedRms, rms);
        assertEquals(lastShownList, logic.getLastShownOrderList());
        assertEquals(rms, saveFile.load());
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single member in the last shown list, using visible index.
     * @param commandWord to test assuming it targets a single employee in the last shown list based on visible index.
     */
    private void assertInvalidIndexBehaviorForMemberCommand(String commandWord) throws Exception {
        String expectedMessage = Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();

        Member m1 = helper.generateMember(1);
        Member m2 = helper.generateMember(2);
        List<Member> lastShownList = helper.generateMemberList(m1, m2);

        logic.setLastShownMemberList(lastShownList);

        assertMemberCommandBehavior(commandWord + " -1", expectedMessage, Rms.empty(), false, lastShownList);
        assertMemberCommandBehavior(commandWord + " 0", expectedMessage, Rms.empty(), false, lastShownList);
        assertMemberCommandBehavior(commandWord + " 3", expectedMessage, Rms.empty(), false, lastShownList);
    }

    /**
     * Executes the menu command and confirms that the result message is correct.
     * Both the 'address book' and the 'last shown menu list' are expected to be empty.
     * @see #assertMenuCommandBehavior(String, String, Rms, boolean, List)
     */
    private void assertMenuCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertMenuCommandBehavior(inputCommand, expectedMessage, Rms.empty(), false, Collections.emptyList());
    }

    /**
     * Executes the menu command and confirms that the result message is correct and
     * also confirms that the following three parts of the Logic object's state are as expected:<br>
     *      - the internal address book data are same as those in the {@code expectedRms} <br>
     *      - the internal 'last shown menu list' matches the {@code expectedLastList} <br>
     *      - the storage file content matches data in {@code expectedRms} <br>
     */
    private void assertMenuCommandBehavior(String inputCommand,
                                           String expectedMessage,
                                           Rms expectedRms,
                                           boolean isRelevantMenuItemsExpected,
                                           List<? extends ReadOnlyMenus> lastShownMenuList) throws Exception {

        //Execute the command
        CommandResult r = logic.execute(inputCommand);

        //Confirm the result contains the right data
        assertEquals(expectedMessage, r.feedbackToUser);
        assertEquals(r.getRelevantMenus().isPresent(), isRelevantMenuItemsExpected);
        if (isRelevantMenuItemsExpected) {
            assertEquals(lastShownMenuList, r.getRelevantMenus().get());
        }


        //Confirm the state of data is as expected
        assertEquals(expectedRms, rms);
        assertEquals(lastShownMenuList, logic.getLastShownMenuList());
        assertEquals(rms, saveFile.load());
    }

    /**
     * Executes the stats command and confirms that the result message is correct<br>
     */
    private void assertStatsCommandBehavior(String inputCommand,
                                            String expectedMessage,
                                            Rms expectedRms) throws Exception {

        //Execute the command
        CommandResult r = logic.execute(inputCommand);

        //Confirm the result contains the right data
        assertEquals(expectedMessage, r.feedbackToUser);

        //Confirm the state of data is as expected
        assertEquals(expectedRms, rms);
        assertEquals(rms, saveFile.load());
    }

    @Test
    public void execute_unknownCommandWord() throws Exception {
        String unknownCommand = "uicfhmowqewca";
        assertCommandBehavior(unknownCommand, HelpCommand.MESSAGE_ALL_USAGES);
    }

    @Test
    public void execute_help() throws Exception {
        assertCommandBehavior("help", HelpCommand.MESSAGE_ALL_USAGES);
    }

    @Test
    public void execute_exit() throws Exception {
        assertCommandBehavior("exit", ExitCommand.MESSAGE_EXIT_ACKNOWEDGEMENT);
    }

    @Test
    public void execute_addemp_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                EmployeeAddCommand.MESSAGE_USAGE);
        assertEmployeeCommandBehavior("addemp wrong args wrong args", expectedMessage);
        assertEmployeeCommandBehavior(
                "addemp Valid Name 12345 e/valid@email.butNoPhonePrefix a/valid, address pos/validPos",
                expectedMessage);
        assertEmployeeCommandBehavior(
                "addemp Valid Name p/12345 valid@email.butNoPrefix a/valid, address pos/validPos",
                expectedMessage);
        assertEmployeeCommandBehavior(
                "addemp Valid Name p/12345 e/valid@email.butNoAddressPrefix valid, address pos/validPos",
                expectedMessage);
        assertCommandBehavior(
                "addemp Valid Name p/12345 e/valid@email a/butNoAddressPrefix valid, address butNoPositionPrefix",
                expectedMessage);
    }

    @Test
    public void execute_addemp_invalidPersonData() throws Exception {
        assertEmployeeCommandBehavior(
                "addemp []\\[;] p/12345 e/valid@e.mail a/valid, address pos/validPos",
                EmployeeName.MESSAGE_NAME_CONSTRAINTS);
        assertEmployeeCommandBehavior(
                "addemp Valid Name p/not_numbers e/valid@e.mail a/valid, address pos/validPos",
                EmployeePhone.MESSAGE_PHONE_CONSTRAINTS);
        assertEmployeeCommandBehavior(
                "addemp Valid Name p/12345 e/notAnEmail a/valid, address pos/validPos",
                EmployeeEmail.MESSAGE_EMAIL_CONSTRAINTS);
        assertEmployeeCommandBehavior(
                "addemp Valid Name p/12345 e/valid@e.mail a/valid, address pos/@#%&%",
                EmployeePosition.MESSAGE_POSITION_CONSTRAINTS);

    }

    @Test
    public void execute_addemp_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Employee toBeAdded = helper.peter();
        Rms expectedRms = new Rms();
        expectedRms.addEmployee(toBeAdded);

        // execute command and verify result
        assertEmployeeCommandBehavior(helper.generateAddEmpCommand(toBeAdded),
                String.format(EmployeeAddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedRms,
                false,
                Collections.emptyList());

    }

    @Test
    public void execute_statsmenu_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, StatsMenuCommand.MESSAGE_USAGE);
        assertMenuCommandBehavior(
                "statsmenu InvalidDate", expectedMessage);
        assertMenuCommandBehavior(
                "statsmenu f/00192048 t/99022018", expectedMessage);
        assertMenuCommandBehavior(
                "statsmenu f/062017 t/2018", expectedMessage);
    }

    /*
    @Test
    public void invalidMemberInOrder() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Member m1 = helper.generateMember(1);
        Member toBeAdded = helper.eve();
        Rms expectedRms = new Rms();
        expectedRms.addMember(toBeAdded);
        expectedRms.findMemberInOrder(m1);
    }

    @Test
    public void validMemberInOrder() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Member m1 = helper.generateMember(1);
        Rms expectedRms = new Rms();
        expectedRms.addMember(m1);
        expectedRms.findMemberInOrder(m1);
    }
    */

    @Test
    public void execute_addempDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Employee toBeAdded = helper.peter();
        Rms expectedRms = new Rms();
        expectedRms.addEmployee(toBeAdded);

        // setup starting state
        logic.execute(helper.generateAddEmpCommand(toBeAdded)); //employee already in Rms

        // execute command and verify result
        assertEmployeeCommandBehavior(
                helper.generateAddEmpCommand(toBeAdded),
                EmployeeAddCommand.MESSAGE_DUPLICATE_EMPLOYEE,
                expectedRms,
                false,
                Collections.emptyList());
    }

    @Test
    public void execute_listemp_successful() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();

        Employee e1 = helper.generateEmployee(1);
        Employee e2 = helper.generateEmployee(2);
        List<Employee> lastShownList = helper.generateEmployeeList(e1, e2);

        Rms expectedRms = helper.generateRmsEmployees(lastShownList);
        List<? extends ReadOnlyEmployee> expectedList = expectedRms.getAllEmployees().immutableListView();

        // prepare address book state
        helper.addEmployeesToRms(rms, lastShownList);

        assertEmployeeCommandBehavior("listemp",
                Command.getMessageForEmployeeListShownSummary(expectedList),
                expectedRms,
                true,
                expectedList);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single employee in the last shown list, using visible index.
     * @param commandWord to test assuming it targets a single employee in the last shown list based on visible index.
     */
    private void assertInvalidIndexBehaviorForEmployeeDeleteCommand(String commandWord) throws Exception {
        String expectedMessage = Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();

        Employee e1 = helper.generateEmployee(1);
        Employee e2 = helper.generateEmployee(2);
        List<Employee> lastShownList = helper.generateEmployeeList(e1, e2);

        logic.setLastShownEmployeeList(lastShownList);

        assertEmployeeCommandBehavior(commandWord + " -1", expectedMessage, Rms.empty(), false, lastShownList);
        assertEmployeeCommandBehavior(commandWord + " 0", expectedMessage, Rms.empty(), false, lastShownList);
        assertEmployeeCommandBehavior(commandWord + " 3", expectedMessage, Rms.empty(), false, lastShownList);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single employee in the last shown list, using visible index.
     * @param commandWord to test assuming it targets a single employee in the last shown list based on visible index.
     */
    private void assertInvalidIndexBehaviorForEmployeeEditCommand(String commandWord) throws Exception {
        String invalidFormat = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                EmployeeEditCommand.MESSAGE_USAGE);
        String invalidIndexMessage = Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();

        Employee e1 = helper.generateEmployee(1);
        Employee e2 = helper.generateEmployee(2);
        List<Employee> lastShownList = helper.generateEmployeeList(e1, e2);
        String arbitraryParameter = "p/98765432";

        logic.setLastShownEmployeeList(lastShownList);

        assertEmployeeCommandBehavior(commandWord + " -1 " + arbitraryParameter, invalidFormat,
                Rms.empty(), false, lastShownList);
        assertEmployeeCommandBehavior(commandWord + " 0 " + arbitraryParameter, invalidIndexMessage,
                Rms.empty(), false, lastShownList);
        assertEmployeeCommandBehavior(commandWord + " 3 " + arbitraryParameter, invalidIndexMessage,
                Rms.empty(), false, lastShownList);
    }

    @Test
    public void execute_delemp_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                EmployeeDeleteCommand.MESSAGE_USAGE);
        assertEmployeeCommandBehavior("delemp ", expectedMessage);
        assertEmployeeCommandBehavior("delemp arg not number", expectedMessage);
    }

    @Test
    public void execute_delemp_invalidIndex() throws Exception {
        assertInvalidIndexBehaviorForEmployeeDeleteCommand("delemp");
    }

    @Test
    public void execute_delemp_removesCorrectEmployee() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Employee e1 = helper.generateEmployee(1);
        Employee e2 = helper.generateEmployee(2);
        Employee e3 = helper.generateEmployee(3);
        Attendance a1 = helper.generateAttendance(1);
        Attendance a2 = helper.generateAttendance(2);
        Attendance a3 = helper.generateAttendance(3);

        List<Employee> lastShownEmployeeList = helper.generateEmployeeList(e1, e2, e3);
        List<Attendance> lastShownAttendanceList = helper.generateAttendanceList(a1, a2, a3);

        Rms expectedRms = helper.generateRmsEmployeesAndAttendances(lastShownEmployeeList, lastShownAttendanceList);
        expectedRms.removeEmployee(e2);


        helper.addEmployeesToRms(rms, lastShownEmployeeList);
        helper.addAttendancesToRms(rms, lastShownAttendanceList);
        logic.setLastShownEmployeeList(lastShownEmployeeList);
        logic.setLastShownAttendanceList(lastShownAttendanceList);

        assertEmployeeAttendanceCommandBehavior("delemp 2",
                String.format(EmployeeDeleteCommand.MESSAGE_DELETE_EMPLOYEE_SUCCESS, e2),
                expectedRms,
                false,
                false,
                lastShownEmployeeList,
                lastShownAttendanceList);
    }

    @Test
    public void execute_delemp_missingInRms() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Employee e1 = helper.generateEmployee(1);
        Employee e2 = helper.generateEmployee(2);
        Employee e3 = helper.generateEmployee(3);

        List<Employee> threeEmployees = helper.generateEmployeeList(e1, e2, e3);

        Rms expectedRms = helper.generateRmsEmployees(threeEmployees);
        expectedRms.removeEmployee(e2);


        helper.addEmployeesToRms(rms, threeEmployees);
        rms.removeEmployee(e2);
        logic.setLastShownEmployeeList(threeEmployees);

        assertEmployeeCommandBehavior("delemp 2",
                Messages.MESSAGE_EMPLOYEE_NOT_IN_RMS,
                expectedRms,
                false,
                threeEmployees);
    }

    @Test
    public void execute_clockinEmployee_success() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Employee e1 = helper.generateEmployee(1);
        Employee e2 = helper.generateEmployee(2);
        Employee e3 = helper.generateEmployee(3);
        Attendance a1 = helper.generateAttendance(1);
        Attendance a2 = helper.generateAttendance(2);
        Attendance a3 = helper.generateAttendance(3);

        List<Employee> lastShownEmployeeList = helper.generateEmployeeList(e1, e2, e3);
        List<Attendance> lastShownAttendanceList = helper.generateAttendanceList(a1, a2, a3);

        Rms expectedRms = helper.generateRmsEmployeesAndAttendances(lastShownEmployeeList, lastShownAttendanceList);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String currentTime = timeFormatter.format(date);
        String currentDate = dateFormatter.format(date);

        helper.addEmployeesToRms(rms, lastShownEmployeeList);
        helper.addAttendancesToRms(rms, lastShownAttendanceList);
        logic.setLastShownEmployeeList(lastShownEmployeeList);
        logic.setLastShownAttendanceList(lastShownAttendanceList);

        assertEmployeeAttendanceCommandBehavior("clockin Employee 2",
                String.format(EmployeeClockInCommand.MESSAGE_SUCCESS, e2.getName(), currentDate, currentTime),
                expectedRms,
                false,
                false,
                lastShownEmployeeList,
                lastShownAttendanceList);
    }

    @Test
    public void execute_clockinEmployee_invalidEmployee() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Employee e1 = helper.generateEmployee(1);
        Employee e2 = helper.generateEmployee(2);
        Employee e3 = helper.generateEmployee(3);
        Attendance a1 = helper.generateAttendance(1);
        Attendance a2 = helper.generateAttendance(2);
        Attendance a3 = helper.generateAttendance(3);

        List<Employee> lastShownEmployeeList = helper.generateEmployeeList(e1, e2, e3);
        List<Attendance> lastShownAttendanceList = helper.generateAttendanceList(a1, a2, a3);

        Rms expectedRms = helper.generateRmsEmployeesAndAttendances(lastShownEmployeeList, lastShownAttendanceList);
        helper.addEmployeesToRms(rms, lastShownEmployeeList);
        helper.addAttendancesToRms(rms, lastShownAttendanceList);
        logic.setLastShownEmployeeList(lastShownEmployeeList);
        logic.setLastShownAttendanceList(lastShownAttendanceList);

        assertEmployeeAttendanceCommandBehavior("clockin Employee 5",
                String.format(Messages.MESSAGE_EMPLOYEE_NOT_IN_RMS),
                expectedRms,
                false,
                false,
                lastShownEmployeeList,
                lastShownAttendanceList);
    }

    @Test
    public void execute_clockoutEmployee_success() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Employee e1 = helper.generateEmployee(1);
        Employee e2 = helper.generateEmployee(2);
        Employee e3 = helper.generateEmployee(3);
        Attendance a1 = helper.generateAttendance(1);
        Attendance a2 = helper.generateAttendance(2);
        Attendance a3 = helper.generateAttendance(3);

        List<Employee> lastShownEmployeeList = helper.generateEmployeeList(e1, e2, e3);
        List<Attendance> lastShownAttendanceList = helper.generateAttendanceList(a1, a2, a3);

        Rms expectedRms = helper.generateRmsEmployeesAndAttendances(lastShownEmployeeList, lastShownAttendanceList);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String currentTime = timeFormatter.format(date);
        String currentDate = dateFormatter.format(date);

        helper.addEmployeesToRms(rms, lastShownEmployeeList);
        helper.addAttendancesToRms(rms, lastShownAttendanceList);
        logic.setLastShownEmployeeList(lastShownEmployeeList);
        logic.setLastShownAttendanceList(lastShownAttendanceList);
        logic.execute("clockin Employee 2");

        assertEmployeeAttendanceCommandBehavior("clockout Employee 2",
                String.format(EmployeeClockOutCommand.MESSAGE_SUCCESS, e2.getName(), currentDate, currentTime),
                expectedRms,
                false,
                false,
                lastShownEmployeeList,
                lastShownAttendanceList);
    }

    @Test
    public void execute_clockoutEmployee_invalidEmployee() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Employee e1 = helper.generateEmployee(1);
        Employee e2 = helper.generateEmployee(2);
        Employee e3 = helper.generateEmployee(3);
        Attendance a1 = helper.generateAttendance(1);
        Attendance a2 = helper.generateAttendance(2);
        Attendance a3 = helper.generateAttendance(3);

        List<Employee> lastShownEmployeeList = helper.generateEmployeeList(e1, e2, e3);
        List<Attendance> lastShownAttendanceList = helper.generateAttendanceList(a1, a2, a3);

        Rms expectedRms = helper.generateRmsEmployeesAndAttendances(lastShownEmployeeList, lastShownAttendanceList);

        helper.addEmployeesToRms(rms, lastShownEmployeeList);
        helper.addAttendancesToRms(rms, lastShownAttendanceList);
        logic.setLastShownEmployeeList(lastShownEmployeeList);
        logic.setLastShownAttendanceList(lastShownAttendanceList);

        assertEmployeeAttendanceCommandBehavior("clockout Employee 5",
                String.format(Messages.MESSAGE_EMPLOYEE_NOT_IN_RMS),
                expectedRms,
                false,
                false,
                lastShownEmployeeList,
                lastShownAttendanceList);
    }

    @Test
    public void execute_editemp_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Employee e1 = helper.generateEmployee(1);
        Employee e2 = helper.generateEmployee(2);
        Employee e3 = helper.generateEmployee(3);
        Employee editedEmployee = helper.generateEditEmployee(e2, "phone", "91234567");

        List<Employee> lastShownEmployeeList = helper.generateEmployeeList(e1, e2, e3);

        Rms expectedRms = helper.generateRmsEmployees(lastShownEmployeeList);
        expectedRms.editEmployee(e2, editedEmployee);


        helper.addEmployeesToRms(rms, lastShownEmployeeList);
        logic.setLastShownEmployeeList(lastShownEmployeeList);


        assertEmployeeCommandBehavior(helper.generateEditEmpCommand("2", "phone", "91234567"),
                String.format(EmployeeEditCommand.MESSAGE_EDIT_EMPLOYEE_SUCCESS, editedEmployee),
                expectedRms,
                false,
                lastShownEmployeeList);

    }

    @Test
    public void execute_editemp_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                EmployeeEditCommand.MESSAGE_USAGE);
        assertEmployeeCommandBehavior("editemp ", expectedMessage);
        assertEmployeeCommandBehavior("editemp arg not number", expectedMessage);
    }

    @Test
    public void execute_editemp_noArgs() throws Exception {
        TestDataHelper helper = new TestDataHelper();

        Employee e1 = helper.generateEmployee(1);
        Employee e2 = helper.generateEmployee(2);
        Employee e3 = helper.generateEmployee(3);
        List<Employee> lastShownList = helper.generateEmployeeList(e1, e2, e3);

        logic.setLastShownEmployeeList(lastShownList);

        assertEmployeeCommandBehavior(helper.generateEditEmpCommand("2", null, null),
                String.format(EmployeeEditCommand.MESSAGE_NOARGS),
                Rms.empty(),
                false,
                lastShownList);
    }

    @Test
    public void execute_editemp_invalidIndex() throws Exception {
        assertInvalidIndexBehaviorForEmployeeEditCommand("editemp");
    }

    @Test
    public void execute_addmember_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MemberAddCommand.MESSAGE_USAGE);
        assertMemberCommandBehavior(
                "addmember Valid Name p/", expectedMessage);
    }

    @Test
    public void execute_addmember_invalidMemberData() throws Exception {
        assertMemberCommandBehavior(
                "addmember []\\[;]", MemberName.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void execute_addmember_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Member toAdd = helper.eve();
        Rms expectedRms = new Rms();
        expectedRms.addMember(toAdd);

        // execute command and verify result
        assertMemberCommandBehavior(helper.generateAddMemberCommand(toAdd),
                String.format(MemberAddCommand.MESSAGE_SUCCESS, toAdd),
                expectedRms,
                false,
                Collections.emptyList());

    }

    @Test
    public void execute_addmemberDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Member toBeAdded = helper.eve();
        Rms expectedRms = new Rms();
        expectedRms.addMember(toBeAdded);

        // setup starting state
        logic.execute(helper.generateAddMemberCommand(toBeAdded)); //member already in Rms

        // execute command and verify result
        assertMemberCommandBehavior(
                helper.generateAddMemberCommand(toBeAdded),
                MemberAddCommand.MESSAGE_DUPLICATE_MEMBER,
                expectedRms,
                false,
                Collections.emptyList());
    }

    @Test
    public void execute_listmember_successful() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();

        Member m1 = helper.generateMember(1);
        Member m2 = helper.generateMember(2);
        List<Member> lastShownList = helper.generateMemberList(m1, m2);

        Rms expectedRms = helper.generateRmsMember(lastShownList);
        List<? extends ReadOnlyMember> expectedList = expectedRms.getAllMembers().immutableListView();

        // prepare address book state
        helper.addMembersToRms(rms, lastShownList);

        assertMemberCommandBehavior("listmembers",
                Command.getMessageForMemberListShownSummary(expectedList),
                expectedRms,
                true,
                expectedList);
    }

    @Test
    public void execute_delmember_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                MemberDeleteCommand.MESSAGE_USAGE);
        assertMemberCommandBehavior("delmember ", expectedMessage);
        assertMemberCommandBehavior("delmember arg not number", expectedMessage);
    }

    @Test
    public void execute_delmember_invalidIndex() throws Exception {
        assertInvalidIndexBehaviorForMemberCommand("delmember");
    }

    @Test
    public void execute_delmember_removesCorrectMember() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Member m1 = helper.generateMember(1);
        Member m2 = helper.generateMember(2);
        Member m3 = helper.generateMember(3);

        List<Member> threeMembers = helper.generateMemberList(m1, m2, m3);

        Rms expectedRms = helper.generateRmsMember(threeMembers);
        expectedRms.removeMember(m2);


        helper.addMembersToRms(rms, threeMembers);
        logic.setLastShownMemberList(threeMembers);

        assertMemberCommandBehavior("delmember 2",
                String.format(MemberDeleteCommand.MESSAGE_DELETE_MEMBER_SUCCESS, m2),
                expectedRms,
                false,
                threeMembers);
    }

    @Test
    public void execute_delmember_missingInRms() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Member m1 = helper.generateMember(1);
        Member m2 = helper.generateMember(2);
        Member m3 = helper.generateMember(3);

        List<Member> threeMembers = helper.generateMemberList(m1, m2, m3);

        Rms expectedRms = helper.generateRmsMember(threeMembers);
        expectedRms.removeMember(m2);


        helper.addMembersToRms(rms, threeMembers);
        rms.removeMember(m2);
        logic.setLastShownMemberList(threeMembers);

        assertMemberCommandBehavior("delmember 2",
                Messages.MESSAGE_MEMBER_NOT_IN_RMS,
                expectedRms,
                false,
                threeMembers);
    }

    @Test
    public void updateMemberPoints() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Points expectedPoints = new Points();

        Member m1 = helper.eve();
        m1.updatePoints(-50, 0);
        Points actualPoints = m1.getPoints();

        assertEquals(expectedPoints.getPoints(), actualPoints.getPoints());
    }

    @Test
    public void execute_addmenu_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MenuAddCommand.MESSAGE_USAGE);
        assertMenuCommandBehavior(
                "addmenu wrong args wrong args", expectedMessage);
        assertMenuCommandBehavior(
                "addmenu Valid Name $12345", expectedMessage);
        assertMenuCommandBehavior(
                "addmenu Valid Name p/$12345 butNoTypePrefix", expectedMessage);
    }

    @Test
    public void execute_addmenu_invalidMenuData() throws Exception {
        assertMenuCommandBehavior(
                "addmenu []\\[;] p/$12345 type/valid, type", MenuName.MESSAGE_NAME_CONSTRAINTS);
        assertMenuCommandBehavior(
                "addmenu Valid Name p/not_numbers type/valid, type", Price.MESSAGE_PRICE_CONSTRAINTS);
        assertMenuCommandBehavior(
                "addmenu Valid Name p/$12345 type/@#%&", Type.MESSAGE_TYPE_CONSTRAINTS);
        assertMenuCommandBehavior(
                "addmenu Valid Name p/$12345 type/valid, type t/invalid_-[.tag", Tag.MESSAGE_TAG_CONSTRAINTS);

    }

    @Test
    public void execute_addmenu_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Menu toBeAdded = helper.burger();
        Rms expectedRms = new Rms();
        expectedRms.addMenu(toBeAdded);

        // execute command and verify result
        assertMenuCommandBehavior(helper.generateMenuAddCommand(toBeAdded),
                String.format(MenuAddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedRms,
                false,
                Collections.emptyList());

    }

    @Test
    public void execute_addmenuDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Menu toBeAdded = helper.burger();
        Rms expectedRms = new Rms();
        expectedRms.addMenu(toBeAdded);

        // setup starting state
        rms.addMenu(toBeAdded); // menu already in internal RMS

        // execute command and verify result
        assertMenuCommandBehavior(
                helper.generateMenuAddCommand(toBeAdded),
                MenuAddCommand.MESSAGE_DUPLICATE_MENU_ITEM,
                expectedRms,
                false,
                Collections.emptyList());

    }

    //test for MenuListCommand
    @Test
    public void execute_listmenu_showsAllMenuItems() throws Exception {
        // prepare expectations
        // TestDataHelper helper = new TestDataHelper();
        Rms expectedRms = new Rms();
        List<? extends ReadOnlyMenus> expectedMenuList = expectedRms.getAllMenus().immutableListView();

        // prepare address book state
        //helper.addToRMS(rms, expectedMenuList);

        assertMenuCommandBehavior("listmenu",
                Command.getMessageForMenuListShownSummary(expectedMenuList),
                expectedRms,
                true,
                expectedMenuList);
    }

    @Test
    public void execute_menulistByTpe_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                MenuListByTypeCommand.MESSAGE_USAGE);
        assertMenuCommandBehavior("listmenutype ", expectedMessage);
    }

    @Test
    public void execute_menulistByType_successfulMatchesTheSpecifiedCategory() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Menu mTarget1 = helper.generateMenuWithGivenNameAndType("Cheese Burger", "main");
        Menu mTarget2 = helper.generateMenuWithGivenNameAndType("Chicken Burger", "main");
        Menu m1 = helper.generateMenuWithGivenNameAndType("Salad", "sides");
        Menu m2 = helper.generateMenuWithGivenNameAndType("Sprite", "beverage");

        List<Menu> fourMenus = helper.generateMenuList(m1, mTarget1, m2, mTarget2);
        Rms expectedRms = helper.generateRmsMenu(fourMenus);
        List<Menu> expectedList = helper.generateMenuList(mTarget1, mTarget2);
        helper.addToRmsMenu(rms, fourMenus);
        assertMenuCommandBehavior("listmenutype main",
                Command.getMessageForMenuListShownSummary(expectedList),
                expectedRms,
                true,
                expectedList);
    }

    @Test
    public void execute_menulistByType_moreThanOneTypeSearchNotAllowed() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Menu mTarget1 = helper.generateMenuWithGivenNameAndType("Cheese Burger", "main");
        Menu mTarget2 = helper.generateMenuWithGivenNameAndType("Chicken Burger", "main");
        Menu m1 = helper.generateMenuWithGivenNameAndType("Salad", "sides");
        Menu m2 = helper.generateMenuWithGivenNameAndType("Sprite", "beverage");

        List<Menu> fourMenus = helper.generateMenuList(m1, mTarget1, m2, mTarget2);
        Rms expectedRms = helper.generateRmsMenu(fourMenus);
        List<Menu> expectedList = helper.generateMenuList();
        helper.addToRmsMenu(rms, fourMenus);
        assertMenuCommandBehavior("listmenutype main sides",
                MenuListByTypeCommand.MESSAGE_ERROR,
                expectedRms,
                false,
                expectedList);
    }
    /*
     * Test case to check if the argument entered is one of the following or not:
     *     main, sides, beverage, dessert, others, set meals
     * If the arguments are not one of the following, then the argument is Invalid
     */

    @Test
    public void execute_menulistByType_invalidArgs() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Menu mTarget1 = helper.generateMenuWithGivenNameAndType("Cheese Burger", "main");
        Menu mTarget2 = helper.generateMenuWithGivenNameAndType("Chicken Burger", "main");
        Menu m1 = helper.generateMenuWithGivenNameAndType("Salad", "sides");
        Menu m2 = helper.generateMenuWithGivenNameAndType("Sprite", "beverage");

        List<Menu> fourMenus = helper.generateMenuList(m1, mTarget1, m2, mTarget2);
        Rms expectedRms = helper.generateRmsMenu(fourMenus);
        List<Menu> expectedList = helper.generateMenuList();
        helper.addToRmsMenu(rms, fourMenus);
        assertMenuCommandBehavior("listmenutype burger",
                MenuListByTypeCommand.MESSAGE_ERROR,
                expectedRms,
                false,
                expectedList);
    }


    /**
      * Confirms the 'invalid argument index number behaviour' for the given command
      * targeting a single menu item in the last shown menu list, using visible index.
      * @param commandWord to test
      *     assuming it targets a single menu item in the last shown menu list based on visible index.
      */

    private void assertInvalidIndexBehaviorForMenuCommand(String commandWord) throws Exception {
        String expectedMessage = Messages.MESSAGE_INVALID_MENU_ITEM_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();

        Menu e1 = helper.generateMenuItem(1);
        Menu e2 = helper.generateMenuItem(2);
        List<Menu> lastShownMenuList = helper.generateMenuList(e1, e2);

        logic.setLastShownMenuList(lastShownMenuList);

        assertMenuCommandBehavior(commandWord + " -1", expectedMessage, Rms.empty(), false, lastShownMenuList);
        assertMenuCommandBehavior(commandWord + " 0", expectedMessage, Rms.empty(), false, lastShownMenuList);
        assertMenuCommandBehavior(commandWord + " 3", expectedMessage, Rms.empty(), false, lastShownMenuList);

    }


    //test for MenuViewAll Command testing for valid arguments
    @Test
    public void execute_menuviewall_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                MenuViewAllCommand.MESSAGE_USAGE);
        assertMenuCommandBehavior("viewallmenu ", expectedMessage);
        assertMenuCommandBehavior("viewallmenu arg not number", expectedMessage);
    }

    @Test
    public void execute_deletemenu_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                MenuDeleteCommand.MESSAGE_USAGE);
        assertMenuCommandBehavior("deletemenu ", expectedMessage);
        assertMenuCommandBehavior("deletemenu arg not number", expectedMessage);
    }

    @Test
    public void execute_deletemenu_invalidIndex() throws Exception {
        assertInvalidIndexBehaviorForMenuCommand("deletemenu");
    }

    @Test
    public void execute_deletemenu_removesCorrectMenu() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Menu m1 = helper.generateMenuItem(1);
        Menu m2 = helper.generateMenuItem(2);
        Menu m3 = helper.generateMenuItem(3);
        List<Menu> threeMenus = helper.generateMenuList(m1, m2, m3);
        Rms expectedRms = helper.generateRmsMenu(threeMenus);
        expectedRms.removeMenuItem(m2);
        helper.addToRmsMenu(rms, threeMenus);
        logic.setLastShownMenuList(threeMenus);
        assertMenuCommandBehavior("deletemenu 2",
                String.format(MenuDeleteCommand.MESSAGE_DELETE_MENU_ITEM_SUCCESS, m2),
                expectedRms,
                false,
                threeMenus);
    }

    @Test
    public void execute_deletemenu_missingInRms() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Menu m1 = helper.generateMenuItem(1);
        Menu m2 = helper.generateMenuItem(2);
        Menu m3 = helper.generateMenuItem(3);
        List<Menu> threeMenus = helper.generateMenuList(m1, m2, m3);
        Rms expectedRms = helper.generateRmsMenu(threeMenus);
        expectedRms.removeMenuItem(m2);
        helper.addToRmsMenu(rms, threeMenus);
        rms.removeMenuItem(m2);
        logic.setLastShownMenuList(threeMenus);
        assertMenuCommandBehavior("deletemenu 2",
                Messages.MESSAGE_MENU_ITEM_NOT_IN_ADDRESSBOOK,
                expectedRms,
                false,
                threeMenus);
    }

    @Test
    public void execute_findmenu_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MenuFindCommand.MESSAGE_USAGE);
        assertMenuCommandBehavior("findmenu ", expectedMessage);
    }

    @Test
    public void execute_findmenu_onlyMatchesFullWordsInMenuItems() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Menu mTarget1 = helper.generateMenuWithName("Double Cheese Burger");
        Menu mTarget2 = helper.generateMenuWithName("Mac and Cheese");
        Menu m1 = helper.generateMenuWithName("cheeeeseeeeeee");
        Menu m2 = helper.generateMenuWithName("che ese");
        List<Menu> fourMenus = helper.generateMenuList(m1, mTarget1, m2, mTarget2);
        Rms expectedRms = helper.generateRmsMenu(fourMenus);
        List<Menu> expectedList = helper.generateMenuList(mTarget1, mTarget2);
        helper.addToRmsMenu(rms, fourMenus);
        assertMenuCommandBehavior("findmenu Cheese",
                Command.getMessageForMenuListShownSummary(expectedList),
                expectedRms,
                true,
                expectedList);
    }

    @Test
    public void execute_findmenu_isInCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Menu mTarget1 = helper.generateMenuWithName("bla bla KEY bla");
        Menu mTarget2 = helper.generateMenuWithName("bla KeY bla bceofeia");
        Menu mTarget3 = helper.generateMenuWithName("key key");
        Menu m2 = helper.generateMenuWithName("sduauo");

        List<Menu> fourMenus = helper.generateMenuList(mTarget1, m2, mTarget2, mTarget3);
        Rms expectedRms = helper.generateRmsMenu(fourMenus);
        List<Menu> expectedList = helper.generateMenuList(mTarget1, mTarget2, mTarget3);
        helper.addToRmsMenu(rms, fourMenus);

        assertMenuCommandBehavior("findmenu KEY",
                Command.getMessageForMenuListShownSummary(expectedList),
                expectedRms,
                true,
                expectedList);
    }

    @Test
    public void execute_findmenu_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Menu mTarget1 = helper.generateMenuWithName("Cheese Taco");
        Menu mTarget2 = helper.generateMenuWithName("Cheese Burger");
        Menu m1 = helper.generateMenuWithName("Cheessse wrap");
        Menu m2 = helper.generateMenuWithName("Grilled cheeeese sandwiches");
        List<Menu> fourMenus = helper.generateMenuList(m1, mTarget1, m2, mTarget2);
        Rms expectedRms = helper.generateRmsMenu(fourMenus);
        List<Menu> expectedList = helper.generateMenuList(mTarget1, mTarget2);
        helper.addToRmsMenu(rms, fourMenus);
        assertMenuCommandBehavior("findmenu Cheese Taco",
                Command.getMessageForMenuListShownSummary(expectedList),
                expectedRms,
                true,
                expectedList);
    }

    @Test
    public void execute_clearorder() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        rms.addOrder(helper.generateOrder(1));
        rms.addOrder(helper.generateOrder(2));
        rms.addOrder(helper.generateOrder(3));

        assertOrderCommandBehavior(
                "clearorder",
                OrderClearCommand.MESSAGE_SUCCESS,
                Rms.empty(),
                false,
                Collections.emptyList());
    }

    @Test
    public void execute_listorder_showsAllOrders() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Rms expectedRms = helper.generateRmsOrder(1, 2, 3, 4);
        List<? extends ReadOnlyOrder> expectedList = expectedRms.getAllOrders().immutableListView();

        // prepare address book state
        helper.addOrdersToRms(rms, 1, 2, 3, 4);

        assertOrderCommandBehavior("listorder",
                Command.getMessageForOrderListShownSummary(expectedList),
                expectedRms,
                true,
                expectedList);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single person in the last shown list, using visible index.
     * @param commandWord to test assuming it targets a single person in the last shown list based on visible index.
     */
    private void assertInvalidIndexBehaviorForOrderCommand(String commandWord) throws Exception {
        String expectedMessage = Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Order> lastShownList = helper.generateOrderList(1, 2);

        logic.setLastShownOrderList(lastShownList);

        assertOrderCommandBehavior(commandWord + " -1", expectedMessage, Rms.empty(), false, lastShownList);
        assertOrderCommandBehavior(commandWord + " 0", expectedMessage, Rms.empty(), false, lastShownList);
        assertOrderCommandBehavior(commandWord + " 3", expectedMessage, Rms.empty(), false, lastShownList);

    }

    @Test
    public void execute_deleteorder_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                OrderDeleteCommand.MESSAGE_USAGE);
        assertOrderCommandBehavior("deleteorder ", expectedMessage);
        assertOrderCommandBehavior("deleteorder arg not number", expectedMessage);
    }

    @Test
    public void execute_deleteorder_invalidIndex() throws Exception {
        assertInvalidIndexBehaviorForOrderCommand("deleteorder");
    }

    @Test
    public void execute_deleteorder_removesCorrectOrder() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Order o1 = helper.generateOrder(1);
        Order o2 = helper.generateOrder(2);
        Order o3 = helper.generateOrder(3);

        List<Order> threeOrders = helper.generateOrderList(o1, o2, o3);

        Rms expectedRms = helper.generateRmsOrder(threeOrders);
        expectedRms.removeOrder(o2);


        helper.addOrdersToRms(rms, threeOrders);
        logic.setLastShownOrderList(threeOrders);

        assertOrderCommandBehavior("deleteorder 2",
                String.format(OrderDeleteCommand.MESSAGE_DELETE_ORDER_SUCCESS, o2),
                expectedRms,
                false,
                threeOrders);
    }

    @Test
    public void execute_deleteorder_missingInRms() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Order o1 = helper.generateOrder(1);
        Order o2 = helper.generateOrder(2);
        Order o3 = helper.generateOrder(3);

        List<Order> threeOrders = helper.generateOrderList(o1, o2, o3);

        Rms expectedRms = helper.generateRmsOrder(threeOrders);
        expectedRms.removeOrder(o2);

        helper.addOrdersToRms(rms, threeOrders);
        rms.removeOrder(o2);
        logic.setLastShownOrderList(threeOrders);

        assertOrderCommandBehavior("deleteorder 2",
                Messages.MESSAGE_ORDER_NOT_IN_ORDER_LIST,
                expectedRms,
                false,
                threeOrders);
    }

    @Test
    public void execute_draftcustomer_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                DraftOrderEditCustomerCommand.MESSAGE_USAGE);
        assertOrderCommandBehavior("draftcustomer ", expectedMessage);
        assertOrderCommandBehavior("draftcustomer arg not number", expectedMessage);
    }

    @Test
    public void execute_draftcustomer_invalidIndex() throws Exception {
        assertInvalidIndexBehaviorForMemberCommand("draftcustomer");
    }

    @Test
    public void execute_draftcustomer_retrievesCorrectMember() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Member m1 = helper.generateMember(1);
        Member m2 = helper.eve();
        Member m3 = helper.generateMember(3);

        List<Member> threeMembers = helper.generateMemberList(m1, m2, m3);

        Rms expectedRms = helper.generateRmsMember(threeMembers);
        Order expectedDraftOrder = helper.foodOrderWithoutDishes();

        helper.addMembersToRms(rms, threeMembers);
        logic.setLastShownMemberList(threeMembers);

        String expectedMessage = DraftOrderEditCustomerCommand.MESSAGE_SUCCESS
                + "\n" + Messages.MESSAGE_DRAFT_ORDER_DETAILS
                + "\n" + expectedDraftOrder.getDraftDetailsAsText();

        assertMemberCommandBehavior("draftcustomer 2",
                expectedMessage,
                expectedRms,
                false,
                threeMembers);
    }

    @Test
    public void execute_draftcustomer_missingInRms() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Member m1 = helper.generateMember(1);
        Member m2 = helper.generateMember(2);
        Member m3 = helper.generateMember(3);

        List<Member> threeMembers = helper.generateMemberList(m1, m2, m3);

        Rms expectedRms = helper.generateRmsMember(threeMembers);
        expectedRms.removeMember(m2);

        helper.addMembersToRms(rms, threeMembers);
        rms.removeMember(m2);
        logic.setLastShownMemberList(threeMembers);

        assertMemberCommandBehavior("draftcustomer 2",
                Messages.MESSAGE_MEMBER_NOT_IN_RMS,
                expectedRms,
                false,
                threeMembers);
    }

    @Test
    public void execute_draftdish_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                DraftOrderEditDishCommand.MESSAGE_USAGE);
        assertOrderCommandBehavior("draftdish", expectedMessage);
        assertOrderCommandBehavior("draftdish ", expectedMessage);
        assertOrderCommandBehavior("draftdish wrong args wrong args", expectedMessage);
        assertOrderCommandBehavior("draftdish 1", expectedMessage);
        assertOrderCommandBehavior("draftdish 1 2", expectedMessage);
        assertOrderCommandBehavior("draftdish 1 q/", expectedMessage);
        assertOrderCommandBehavior("draftdish q/2", expectedMessage);
        assertOrderCommandBehavior("draftdish a q/2", expectedMessage);
        assertOrderCommandBehavior("draftdish 1 q/b", expectedMessage);
    }

    @Test
    public void execute_draftdish_invalidIndex() throws Exception {
        String expectedMessage = Messages.MESSAGE_INVALID_MENU_ITEM_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        Menu m1 = helper.generateMenuItem(1);
        Menu m2 = helper.generateMenuItem(2);
        List<Menu> lastShownMenuList = helper.generateMenuList(m1, m2);

        logic.setLastShownMenuList(lastShownMenuList);

        assertMenuCommandBehavior("draftdish" + " -1 " + "q/1", expectedMessage, Rms.empty(), false, lastShownMenuList);
        assertMenuCommandBehavior("draftdish" + " 0 " + "q/1", expectedMessage, Rms.empty(), false, lastShownMenuList);
        assertMenuCommandBehavior("draftdish" + " 3 " + "q/1", expectedMessage, Rms.empty(), false, lastShownMenuList);
    }

    @Test
    public void execute_draftdish_retrievesCorrectMenuItem() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Menu m1 = helper.generateMenuItem(1);
        Menu m2 = helper.burger();
        Menu m3 = helper.generateMenuItem(3);

        List<Menu> threeMenus = helper.generateMenuList(m1, m2, m3);

        Rms expectedRms = helper.generateRmsMenu(threeMenus);
        Order expectedDraftOrder = helper.foodOrderWithoutCustomer();

        helper.addToRmsMenu(rms, threeMenus);
        logic.setLastShownMenuList(threeMenus);

        String expectedMessage = DraftOrderEditDishCommand.MESSAGE_SUCCESS
                + "\n" + Messages.MESSAGE_DRAFT_ORDER_DETAILS
                + "\n" + expectedDraftOrder.getDraftDetailsAsText();

        assertMenuCommandBehavior("draftdish 2 q/" + helper.FOOD_QUANTITY,
                expectedMessage,
                expectedRms,
                false,
                threeMenus);
    }

    @Test
    public void execute_draftdish_missingInRms() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Menu m1 = helper.generateMenuItem(1);
        Menu m2 = helper.generateMenuItem(2);
        Menu m3 = helper.generateMenuItem(3);

        List<Menu> threeMenus = helper.generateMenuList(m1, m2, m3);

        Rms expectedRms = helper.generateRmsMenu(threeMenus);
        expectedRms.removeMenuItem(m2);

        helper.addToRmsMenu(rms, threeMenus);
        rms.removeMenuItem(m2);
        logic.setLastShownMenuList(threeMenus);

        assertMenuCommandBehavior("draftdish 2 q/1",
                Messages.MESSAGE_MENU_ITEM_NOT_IN_ADDRESSBOOK,
                expectedRms,
                false,
                threeMenus);
    }

    @Test
    public void execute_cleardraft() throws Exception {
        TestDataHelper helper = new TestDataHelper();

        Order expectedDraftOrder = new Order();
        rms.editDraftOrderCustomer(helper.eve());
        rms.editDraftOrderDishItem(helper.burger(), helper.FOOD_QUANTITY);

        String expectedMessage = DraftOrderClearCommand.MESSAGE_SUCCESS
                + "\n" + Messages.MESSAGE_DRAFT_ORDER_DETAILS
                + "\n" + expectedDraftOrder.getDraftDetailsAsText();

        assertOrderCommandBehavior("cleardraft", expectedMessage);
    }

    @Test
    public void execute_addorder() throws Exception {
        TestDataHelper helper = new TestDataHelper();

        Order expectedDraftOrder = helper.foodOrder();
        rms.editDraftOrderCustomer(helper.eve());
        rms.editDraftOrderDishItem(helper.burger(), helper.FOOD_QUANTITY);

        String expectedMessage = Messages.MESSAGE_DRAFT_ORDER_DETAILS
                + "\n" + expectedDraftOrder.getDraftDetailsAsText()
                + "\n\n" + OrderAddCommand.MESSAGE_ADD_ORDER_INSTRUCTION
                + "\n\n" + OrderAddCommand.MESSAGE_ALL_ORDER_DRAFT_COMMANDS
                + "\n\n" + OrderAddCommand.MESSAGE_ALL_ORDER_DRAFT_COMMANDS_USAGES;

        assertOrderCommandBehavior("addorder", expectedMessage);
    }

    @Test
    public void execute_addorder_missingCustomer() throws Exception {
        TestDataHelper helper = new TestDataHelper();

        Order expectedDraftOrder = helper.foodOrderWithoutCustomer();
        rms.editDraftOrderDishItem(helper.burger(), helper.FOOD_QUANTITY);

        String expectedMessage = Messages.MESSAGE_DRAFT_ORDER_DETAILS
                + "\n" + expectedDraftOrder.getDraftDetailsAsText()
                + "\n\n" + OrderAddCommand.MESSAGE_ADD_ORDER_INSTRUCTION
                + "\n\n" + OrderAddCommand.MESSAGE_ALL_ORDER_DRAFT_COMMANDS
                + "\n\n" + OrderAddCommand.MESSAGE_ALL_ORDER_DRAFT_COMMANDS_USAGES;

        assertOrderCommandBehavior("addorder", expectedMessage);
    }

    @Test
    public void execute_addorder_missingDishes() throws Exception {
        TestDataHelper helper = new TestDataHelper();

        Order expectedDraftOrder = helper.foodOrderWithoutDishes();
        rms.editDraftOrderCustomer(helper.eve());

        String expectedMessage = Messages.MESSAGE_DRAFT_ORDER_DETAILS
                + "\n" + expectedDraftOrder.getDraftDetailsAsText()
                + "\n\n" + OrderAddCommand.MESSAGE_ADD_ORDER_INSTRUCTION
                + "\n\n" + OrderAddCommand.MESSAGE_ALL_ORDER_DRAFT_COMMANDS
                + "\n\n" + OrderAddCommand.MESSAGE_ALL_ORDER_DRAFT_COMMANDS_USAGES;

        assertOrderCommandBehavior("addorder", expectedMessage);
    }

    @Test
    public void execute_addorder_missingCustomerAndDishes() throws Exception {
        TestDataHelper helper = new TestDataHelper();

        Order expectedDraftOrder = new Order();

        String expectedMessage = Messages.MESSAGE_DRAFT_ORDER_DETAILS
                + "\n" + expectedDraftOrder.getDraftDetailsAsText()
                + "\n\n" + OrderAddCommand.MESSAGE_ADD_ORDER_INSTRUCTION
                + "\n\n" + OrderAddCommand.MESSAGE_ALL_ORDER_DRAFT_COMMANDS
                + "\n\n" + OrderAddCommand.MESSAGE_ALL_ORDER_DRAFT_COMMANDS_USAGES;

        assertOrderCommandBehavior("addorder", expectedMessage);
    }

    @Test
    public void execute_confirmorder_missingDishes() throws Exception {
        TestDataHelper helper = new TestDataHelper();

        Rms expectedRms = helper.generateRms();
        Order expectedDraftOrder = helper.foodOrderWithoutDishes();
        expectedRms.addOrder(expectedDraftOrder);

        rms.editDraftOrderCustomer(helper.eve());

        String expectedMessage = DraftOrderConfirmCommand.MESSAGE_DRAFT_INCOMPLETE
                + "\n" + Messages.MESSAGE_DRAFT_ORDER_DETAILS
                + "\n" + expectedDraftOrder.getDraftDetailsAsText();

        assertOrderCommandBehavior("confirmdraft", expectedMessage);
    }

    /*
    @Test
    public void execute_confirmorder_missingCustomer() throws Exception {
        TestDataHelper helper = new TestDataHelper();

        Rms expectedRms = helper.generateRms();
        Order expectedDraftOrder = helper.foodOrderWithoutCustomer();
        expectedRms.addOrder(expectedDraftOrder);

        rms.editDraftOrderDishItem(helper.burger(), helper.FOOD_QUANTITY);

        String expectedMessage = DraftOrderConfirmCommand.MESSAGE_SUCCESS
                + "\n" + Command.getMessageForOrderListShownSummary(expectedRms.getAllOrders().immutableListView());

        assertOrderCommandBehavior("confirmdraft",
                expectedMessage,
                expectedRms,
                true,
                threeOrders);
    }
    */

}

