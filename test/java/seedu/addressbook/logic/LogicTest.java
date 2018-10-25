package seedu.addressbook.logic;


import static junit.framework.TestCase.assertEquals;
import static seedu.addressbook.common.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.addressbook.commands.employee.EmployeeAddCommand;
import seedu.addressbook.commands.employee.EmployeeDeleteCommand;
import seedu.addressbook.commands.member.MemberAddCommand;
import seedu.addressbook.commands.member.MemberDeleteCommand;
import seedu.addressbook.commands.menu.MenuAddCommand;
import seedu.addressbook.commands.menu.MenuDeleteCommand;
import seedu.addressbook.commands.menu.MenuListByTypeCommand;
import seedu.addressbook.commands.menu.MenuViewAllCommand;
import seedu.addressbook.commands.AddCommand;
import seedu.addressbook.commands.ClearCommand;
import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.commands.DeleteCommand;
import seedu.addressbook.commands.ExitCommand;
import seedu.addressbook.commands.FindCommand;
import seedu.addressbook.commands.HelpCommand;
import seedu.addressbook.commands.ViewAllCommand;
import seedu.addressbook.commands.ViewCommand;
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
import seedu.addressbook.data.Rms;
import seedu.addressbook.data.person.Email;
import seedu.addressbook.data.person.Name;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.Phone;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.tag.Tag;

import seedu.addressbook.common.Messages;
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
        assertEquals(Collections.emptyList(), logic.getLastShownMemberList());
    }

    @Test
    public void execute_invalid() throws Exception {
        String invalidCommand = "       ";
        assertCommandBehavior(invalidCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the command and confirms that the result message is correct.
     * Both the 'address book' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, Rms, boolean, List)
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, Rms.empty(),false, Collections.emptyList());
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
        if(isRelevantPersonsExpected){
            assertEquals(lastShownList, r.getRelevantPersons().get());
        }

        //Confirm the state of data is as expected
        assertEquals(expectedRms, rms);
        assertEquals(lastShownList, logic.getLastShownList());
        assertEquals(rms, saveFile.load());
    }

    /**
     * Executes the Employee command and confirms that the result message is correct.
     * Both the 'address book' and the 'last shown list' are expected to be empty.
     * @see #assertEmployeeCommandBehavior(String, String, Rms, boolean, List)
     */
    private void assertEmployeeCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertEmployeeCommandBehavior(inputCommand, expectedMessage, Rms.empty(),false, Collections.emptyList());
    }
    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the Logic object's state are as expected:<br>
     *      - the internal address book data are same as those in the {@code expectedRms} <br>
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
        if(isRelevantEmployeesExpected){
            assertEquals(lastShownList, r.getRelevantEmployee().get());
        }

        //Confirm the state of data is as expected
        assertEquals(expectedRms, rms);
        assertEquals(lastShownList, logic.getLastShownEmployeeList());
        assertEquals(rms, saveFile.load());
    }

    /**
     * Executes the Member command and confirms that the result message is correct.
     * Both the 'address book' and the 'last shown list' are expected to be empty.
     * @see #assertMemberCommandBehavior(String, String, Rms, boolean, List)
     */
    private void assertMemberCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertMemberCommandBehavior(inputCommand, expectedMessage, Rms.empty(),false, Collections.emptyList());
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
        if(isRelevantMemberExpected){
            assertEquals(lastShownList, r.getRelevantMember().get());
        }

        //Confirm the state of data is as expected
        assertEquals(expectedRms, rms);
        assertEquals(lastShownList, logic.getLastShownMemberList());
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
        if(isRelevantMenuItemsExpected){
            assertEquals(lastShownMenuList, r.getRelevantMenus().get());
        }


        //Confirm the state of data is as expected
        assertEquals(expectedRms, rms);
        assertEquals(lastShownMenuList, logic.getLastShownMenuList());
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
    public void execute_clear() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        rms.addPerson(helper.generatePerson(1, true));
        rms.addPerson(helper.generatePerson(2, true));
        rms.addPerson(helper.generatePerson(3, true));

        assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, Rms.empty(), false, Collections.emptyList());
    }

    @Test
    public void execute_add_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandBehavior(
                "add wrong args wrong args", expectedMessage);
        assertCommandBehavior(
                "add Valid Name 12345 e/valid@email.butNoPhonePrefix a/valid, address", expectedMessage);
        assertCommandBehavior(
                "add Valid Name p/12345 valid@email.butNoPrefix a/valid, address", expectedMessage);
        assertCommandBehavior(
                "add Valid Name p/12345 e/valid@email.butNoAddressPrefix valid, address", expectedMessage);
    }

    @Test
    public void execute_add_invalidPersonData() throws Exception {
        assertCommandBehavior(
                "add []\\[;] p/12345 e/valid@e.mail a/valid, address", Name.MESSAGE_NAME_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name p/not_numbers e/valid@e.mail a/valid, address", Phone.MESSAGE_PHONE_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name p/12345 e/notAnEmail a/valid, address", Email.MESSAGE_EMAIL_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name p/12345 e/valid@e.mail a/valid, address t/invalid_-[.tag", Tag.MESSAGE_TAG_CONSTRAINTS);

    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Person toBeAdded = helper.adam();
        Rms expectedAB = new Rms();
        expectedAB.addPerson(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                false,
                Collections.emptyList());

    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Person toBeAdded = helper.adam();
        Rms expectedAB = new Rms();
        expectedAB.addPerson(toBeAdded);

        // setup starting state
        rms.addPerson(toBeAdded); // person already in internal address book

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAdded),
                AddCommand.MESSAGE_DUPLICATE_PERSON,
                expectedAB,
                false,
                Collections.emptyList());

    }

    @Test
    public void execute_list_showsAllPersons() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Rms expectedAB = helper.generateRms(false, true);
        List<? extends ReadOnlyPerson> expectedList = expectedAB.getAllPersons().immutableListView();

        // prepare address book state
        helper.addToRms(rms, false, true);

        assertCommandBehavior("list",
                Command.getMessageForPersonListShownSummary(expectedList),
                expectedAB,
                true,
                expectedList);
    }

    @Test
    public void execute_view_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE);
        assertCommandBehavior("view ", expectedMessage);
        assertCommandBehavior("view arg not number", expectedMessage);
    }

    @Test
    public void execute_view_invalidIndex() throws Exception {
        assertInvalidIndexBehaviorForCommand("view");
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single person in the last shown list, using visible index.
     * @param commandWord to test assuming it targets a single person in the last shown list based on visible index.
     */
    private void assertInvalidIndexBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Person> lastShownList = helper.generatePersonList(false, true);

        logic.setLastShownList(lastShownList);

        assertCommandBehavior(commandWord + " -1", expectedMessage, Rms.empty(), false, lastShownList);
        assertCommandBehavior(commandWord + " 0", expectedMessage, Rms.empty(), false, lastShownList);
        assertCommandBehavior(commandWord + " 3", expectedMessage, Rms.empty(), false, lastShownList);

    }

    @Test
    public void execute_view_onlyShowsNonPrivate() throws Exception {

        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, true);
        Person p2 = helper.generatePerson(2, false);
        List<Person> lastShownList = helper.generatePersonList(p1, p2);
        Rms expectedAB = helper.generateRms(lastShownList);
        helper.addToRms(rms, lastShownList);

        logic.setLastShownList(lastShownList);

        assertCommandBehavior("view 1",
                String.format(ViewCommand.MESSAGE_VIEW_PERSON_DETAILS, p1.getAsTextHidePrivate()),
                expectedAB,
                false,
                lastShownList);

        assertCommandBehavior("view 2",
                String.format(ViewCommand.MESSAGE_VIEW_PERSON_DETAILS, p2.getAsTextHidePrivate()),
                expectedAB,
                false,
                lastShownList);
    }

    @Test
    public void execute_tryToViewMissingPerson_errorMessage() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);
        Person p2 = helper.generatePerson(2, false);
        List<Person> lastShownList = helper.generatePersonList(p1, p2);

        Rms expectedAB = new Rms();
        expectedAB.addPerson(p2);

        rms.addPerson(p2);
        logic.setLastShownList(lastShownList);

        assertCommandBehavior("view 1",
                Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK,
                expectedAB,
                false,
                lastShownList);
    }

    @Test
    public void execute_viewAll_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewAllCommand.MESSAGE_USAGE);
        assertCommandBehavior("viewall ", expectedMessage);
        assertCommandBehavior("viewall arg not number", expectedMessage);
    }

    @Test
    public void execute_viewAll_invalidIndex() throws Exception {
        assertInvalidIndexBehaviorForCommand("viewall");
    }

    @Test
    public void execute_viewAll_alsoShowsPrivate() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, true);
        Person p2 = helper.generatePerson(2, false);
        List<Person> lastShownList = helper.generatePersonList(p1, p2);
        Rms expectedAB = helper.generateRms(lastShownList);
        helper.addToRms(rms, lastShownList);

        logic.setLastShownList(lastShownList);

        assertCommandBehavior("viewall 1",
                String.format(ViewCommand.MESSAGE_VIEW_PERSON_DETAILS, p1.getAsTextShowAll()),
                expectedAB,
                false,
                lastShownList);

        assertCommandBehavior("viewall 2",
                String.format(ViewCommand.MESSAGE_VIEW_PERSON_DETAILS, p2.getAsTextShowAll()),
                expectedAB,
                false,
                lastShownList);
    }

    @Test
    public void execute_tryToViewAllPersonMissingInAddressBook_errorMessage() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);
        Person p2 = helper.generatePerson(2, false);
        List<Person> lastShownList = helper.generatePersonList(p1, p2);

        Rms expectedAB = new Rms();
        expectedAB.addPerson(p1);

        rms.addPerson(p1);
        logic.setLastShownList(lastShownList);

        assertCommandBehavior("viewall 2",
                Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK,
                expectedAB,
                false,
                lastShownList);
    }

    @Test
    public void execute_delete_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertCommandBehavior("delete ", expectedMessage);
        assertCommandBehavior("delete arg not number", expectedMessage);
    }

    @Test
    public void execute_delete_invalidIndex() throws Exception {
        assertInvalidIndexBehaviorForCommand("delete");
    }

    @Test
    public void execute_delete_removesCorrectPerson() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);
        Person p2 = helper.generatePerson(2, true);
        Person p3 = helper.generatePerson(3, true);

        List<Person> threePersons = helper.generatePersonList(p1, p2, p3);

        Rms expectedAB = helper.generateRms(threePersons);
        expectedAB.removePerson(p2);


        helper.addToRms(rms, threePersons);
        logic.setLastShownList(threePersons);

        assertCommandBehavior("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, p2),
                expectedAB,
                false,
                threePersons);
    }

    @Test
    public void execute_delete_missingInAddressBook() throws Exception {

        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);
        Person p2 = helper.generatePerson(2, true);
        Person p3 = helper.generatePerson(3, true);

        List<Person> threePersons = helper.generatePersonList(p1, p2, p3);

        Rms expectedAB = helper.generateRms(threePersons);
        expectedAB.removePerson(p2);

        helper.addToRms(rms, threePersons);
        rms.removePerson(p2);
        logic.setLastShownList(threePersons);

        assertCommandBehavior("delete 2",
                Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK,
                expectedAB,
                false,
                threePersons);
    }

    @Test
    public void execute_find_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("find ", expectedMessage);
    }


    @Test
    public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person pTarget1 = helper.generatePersonWithName("bla bla KEY bla");
        Person pTarget2 = helper.generatePersonWithName("bla KEY bla bceofeia");
        Person p1 = helper.generatePersonWithName("KE Y");
        Person p2 = helper.generatePersonWithName("KEYKEYKEY sduauo");

        List<Person> fourPersons = helper.generatePersonList(p1, pTarget1, p2, pTarget2);
        Rms expectedAB = helper.generateRms(fourPersons);
        List<Person> expectedList = helper.generatePersonList(pTarget1, pTarget2);
        helper.addToRms(rms, fourPersons);

        assertCommandBehavior("find KEY",
                Command.getMessageForPersonListShownSummary(expectedList),
                expectedAB,
                true,
                expectedList);
    }



    @Test
    public void execute_find_isCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person pTarget1 = helper.generatePersonWithName("bla bla KEY bla");
        Person pTarget2 = helper.generatePersonWithName("bla KEY bla bceofeia");
        Person p1 = helper.generatePersonWithName("key key");
        Person p2 = helper.generatePersonWithName("KEy sduauo");

        List<Person> fourPersons = helper.generatePersonList(p1, pTarget1, p2, pTarget2);
        Rms expectedAB = helper.generateRms(fourPersons);
        List<Person> expectedList = helper.generatePersonList(pTarget1, pTarget2);
        helper.addToRms(rms, fourPersons);

        assertCommandBehavior("find KEY",
                Command.getMessageForPersonListShownSummary(expectedList),
                expectedAB,
                true,
                expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person pTarget1 = helper.generatePersonWithName("bla bla KEY bla");
        Person pTarget2 = helper.generatePersonWithName("bla rAnDoM bla bceofeia");
        Person p1 = helper.generatePersonWithName("key key");
        Person p2 = helper.generatePersonWithName("KEy sduauo");

        List<Person> fourPersons = helper.generatePersonList(p1, pTarget1, p2, pTarget2);
        Rms expectedAB = helper.generateRms(fourPersons);
        List<Person> expectedList = helper.generatePersonList(pTarget1, pTarget2);
        helper.addToRms(rms, fourPersons);

        assertCommandBehavior("find KEY rAnDoM",
                Command.getMessageForPersonListShownSummary(expectedList),
                expectedAB,
                true,
                expectedList);
    }

    @Test
    public void execute_addemp_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmployeeAddCommand.MESSAGE_USAGE);
        assertEmployeeCommandBehavior(
                "addemp wrong args wrong args", expectedMessage);
        assertEmployeeCommandBehavior(
                "addemp Valid Name 12345 e/valid@email.butNoPhonePrefix a/valid, address pos/validPos", expectedMessage);
        assertEmployeeCommandBehavior(
                "addemp Valid Name p/12345 valid@email.butNoPrefix a/valid, address pos/validPos", expectedMessage);
        assertEmployeeCommandBehavior(
                "addemp Valid Name p/12345 e/valid@email.butNoAddressPrefix valid, address pos/validPos", expectedMessage);
        assertCommandBehavior(
                "addemp Valid Name p/12345 e/valid@email a/butNoAddressPrefix valid, address butNoPositionPrefix", expectedMessage);
    }

    @Test
    public void execute_addemp_invalidPersonData() throws Exception {
        assertEmployeeCommandBehavior(
                "addemp []\\[;] p/12345 e/valid@e.mail a/valid, address pos/validPos", EmployeeName.MESSAGE_NAME_CONSTRAINTS);
        assertEmployeeCommandBehavior(
                "addemp Valid Name p/not_numbers e/valid@e.mail a/valid, address pos/validPos", EmployeePhone.MESSAGE_PHONE_CONSTRAINTS);
        assertEmployeeCommandBehavior(
                "addemp Valid Name p/12345 e/notAnEmail a/valid, address pos/validPos", EmployeeEmail.MESSAGE_EMAIL_CONSTRAINTS);
        assertEmployeeCommandBehavior(
                "addemp Valid Name p/12345 e/valid@e.mail a/valid, address pos/@#%&%", EmployeePosition.MESSAGE_POSITION_CONSTRAINTS);

    }

    @Test
    public void execute_addemp_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Employee toBeAdded = helper.peter();
        Rms expectedAB = new Rms();
        expectedAB.addEmployee(toBeAdded);

        // execute command and verify result
        assertEmployeeCommandBehavior(helper.generateAddEmpCommand(toBeAdded),
                String.format(EmployeeAddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                false,
                Collections.emptyList());

    }

    @Test
    public void execute_addempDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Employee toBeAdded = helper.peter();
        Rms expectedAB = new Rms();
        expectedAB.addEmployee(toBeAdded);

        // setup starting state
        logic.execute(helper.generateAddEmpCommand(toBeAdded)); //employee already in Rms

        // execute command and verify result
        assertEmployeeCommandBehavior(
                helper.generateAddEmpCommand(toBeAdded),
                EmployeeAddCommand.MESSAGE_DUPLICATE_EMPLOYEE,
                expectedAB,
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
    private void assertInvalidIndexBehaviorForEmployeeCommand(String commandWord) throws Exception {
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

    @Test
    public void execute_delemp_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmployeeDeleteCommand.MESSAGE_USAGE);
        assertEmployeeCommandBehavior("delemp ", expectedMessage);
        assertEmployeeCommandBehavior("delemp arg not number", expectedMessage);
    }

    @Test
    public void execute_delemp_invalidIndex() throws Exception {
        assertInvalidIndexBehaviorForEmployeeCommand("delemp");
    }

    @Test
    public void execute_delemp_removesCorrectEmployee() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Employee e1 = helper.generateEmployee(1);
        Employee e2 = helper.generateEmployee(2);
        Employee e3 = helper.generateEmployee(3);

        List<Employee> threeEmployees = helper.generateEmployeeList(e1, e2, e3);

        Rms expectedRms = helper.generateRmsEmployees(threeEmployees);
        expectedRms.removeEmployee(e2);


        helper.addEmployeesToRms(rms, threeEmployees);
        logic.setLastShownEmployeeList(threeEmployees);

        assertEmployeeCommandBehavior("delemp 2",
                String.format(EmployeeDeleteCommand.MESSAGE_DELETE_EMPLOYEE_SUCCESS, e2),
                expectedRms,
                false,
                threeEmployees);
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
    public void execute_addmember_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MemberAddCommand.MESSAGE_USAGE);
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
        Rms expectedAB = new Rms();
        expectedAB.addMember(toAdd);

        // execute command and verify result
        assertMemberCommandBehavior(helper.generateAddMemberCommand(toAdd),
                String.format(MemberAddCommand.MESSAGE_SUCCESS, toAdd),
                expectedAB,
                false,
                Collections.emptyList());

    }

    @Test
    public void execute_addmemberDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Member toBeAdded = helper.eve();
        Rms expectedAB = new Rms();
        expectedAB.addMember(toBeAdded);

        // setup starting state
        logic.execute(helper.generateAddMemberCommand(toBeAdded)); //member already in Rms

        // execute command and verify result
        assertMemberCommandBehavior(
                helper.generateAddMemberCommand(toBeAdded),
                MemberAddCommand.MESSAGE_DUPLICATE_MEMBER,
                expectedAB,
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
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MemberDeleteCommand.MESSAGE_USAGE);
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
        m1.updatePoints(-50);
        Points actualPoints = m1.getPoints();

        assertEquals(expectedPoints.getPoints(), actualPoints.getPoints());
    }

    @Test
    public void execute_addmenu_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MenuAddCommand.MESSAGE_USAGE);
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
        Rms expectedAB = new Rms();
        expectedAB.addMenu(toBeAdded);

        // execute command and verify result
        assertMenuCommandBehavior(helper.generateMenuAddCommand(toBeAdded),
                String.format(MenuAddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                false,
                Collections.emptyList());

    }

    @Test
    public void execute_addmenuDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Menu toBeAdded = helper.burger();
        Rms expectedAB = new Rms();
        expectedAB.addMenu(toBeAdded);

        // setup starting state
        rms.addMenu(toBeAdded); // menu already in internal RMS

        // execute command and verify result
        assertMenuCommandBehavior(
                helper.generateMenuAddCommand(toBeAdded),
                MenuAddCommand.MESSAGE_DUPLICATE_MENU_ITEM,
                expectedAB,
                false,
                Collections.emptyList());

    }

    //test for MenuListCommand
    @Test
    public void execute_listmenu_showsAllMenuItems() throws Exception {
        // prepare expectations
        // TestDataHelper helper = new TestDataHelper();
        Rms expectedRMS = new Rms();
        List<? extends ReadOnlyMenus> expectedRMSList = expectedRMS.getAllMenus().immutableListView();

        // prepare address book state
        //helper.addToRMS(rms, expectedRMSList);

        assertMenuCommandBehavior("listmenu",
                Command.getMessageForMenuListShownSummary(expectedRMSList),
                expectedRMS,
                true,
                expectedRMSList);
    }

    @Test
    public void execute_menulistByTpe_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MenuListByTypeCommand.MESSAGE_USAGE);
        assertMenuCommandBehavior("listmenutype ", expectedMessage);
    }

    @Test
    public void execute_menulistByType_successful_MatchesTheSpecifiedCategory() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Menu mTarget1 = helper.generateMenuWithGivenNameAndType("Cheese Burger","main");
        Menu mTarget2 = helper.generateMenuWithGivenNameAndType("Chicken Burger", "main");
        Menu m1 = helper.generateMenuWithGivenNameAndType("Salad", "sides");
        Menu m2 = helper.generateMenuWithGivenNameAndType("Sprite", "beverage");

        List<Menu> fourMenus = helper.generateMenuList(m1, mTarget1, m2, mTarget2);
        Rms expectedAB = helper.generateRmsMenu(fourMenus);
        List<Menu> expectedList = helper.generateMenuList(mTarget1, mTarget2);
        helper.addToRmsMenu(rms, fourMenus);
        assertMenuCommandBehavior("listmenutype main",
                Command.getMessageForMenuListShownSummary(expectedList),
                expectedAB,
                true,
                expectedList);
    }

    @Test
    public void execute_menulistByType_moreThanOneTypeSearchNotAllowed() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Menu mTarget1 = helper.generateMenuWithGivenNameAndType("Cheese Burger","main");
        Menu mTarget2 = helper.generateMenuWithGivenNameAndType("Chicken Burger", "main");
        Menu m1 = helper.generateMenuWithGivenNameAndType("Salad", "sides");
        Menu m2 = helper.generateMenuWithGivenNameAndType("Sprite", "beverage");

        List<Menu> fourMenus = helper.generateMenuList(m1, mTarget1, m2, mTarget2);
        Rms expectedAB = helper.generateRmsMenu(fourMenus);
        List<Menu> expectedList = helper.generateMenuList();
        helper.addToRmsMenu(rms, fourMenus);
        assertMenuCommandBehavior("listmenutype main sides",
                MenuListByTypeCommand.MESSAGE_ERROR,
                expectedAB,
                false,
                expectedList);
    }
    /*
     * Test case to check if the argument entered is one of the following or not: main, sides, beverage, dessert, others, set meals
     * If the arguments are not one of the following, then the argument is Invalid
     */

    @Test
    public void execute_menulistByType_invalidArgs() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Menu mTarget1 = helper.generateMenuWithGivenNameAndType("Cheese Burger","main");
        Menu mTarget2 = helper.generateMenuWithGivenNameAndType("Chicken Burger", "main");
        Menu m1 = helper.generateMenuWithGivenNameAndType("Salad", "sides");
        Menu m2 = helper.generateMenuWithGivenNameAndType("Sprite", "beverage");

        List<Menu> fourMenus = helper.generateMenuList(m1, mTarget1, m2, mTarget2);
        Rms expectedAB = helper.generateRmsMenu(fourMenus);
        List<Menu> expectedList = helper.generateMenuList();
        helper.addToRmsMenu(rms, fourMenus);
        assertMenuCommandBehavior("listmenutype burger",
                MenuListByTypeCommand.MESSAGE_ERROR,
                expectedAB,
                false,
                expectedList);
    }


 //   /**
  //   * Confirms the 'invalid argument index number behaviour' for the given command
   //  * targeting a single menu item in the last shown menu list, using visible index.
    // * @param commandWord to test assuming it targets a single menu item in the last shown menu list based on visible index.
     //*/
    /*
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
    */

    //test for MenuViewAll Command testing for valid arguments
    @Test
    public void execute_MenuviewAll_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MenuViewAllCommand.MESSAGE_USAGE);
        assertMenuCommandBehavior("viewallmenu ", expectedMessage);
        assertMenuCommandBehavior("viewallmenu arg not number", expectedMessage);
    }

    @Test
    public void execute_deletemenu_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MenuDeleteCommand.MESSAGE_USAGE);
        assertMenuCommandBehavior("deletemenu ", expectedMessage);
        assertMenuCommandBehavior("deletemenu arg not number", expectedMessage);
    }

    /*@Test
    public void execute_deletemenu_invalidIndex() throws Exception {
        assertInvalidIndexBehaviorForMenuCommand("deletemenu");
    }*/

//
//    @Test
//    public void invalidMemberInOrder() throws Exception {
//        TestDataHelper helper = new TestDataHelper();
//        Member m1 = helper.generateMember(1);
//        Member toBeAdded = helper.eve();
//        Rms expectedAB = new Rms();
//        expectedAB.addMember(toBeAdded);
//        expectedAB.findMemberInOrder(m1);
//    }
//
//    @Test
//    public void validMemberInOrder() throws Exception {
//        TestDataHelper helper = new TestDataHelper();
//        Member m1 = helper.generateMember(1);
//        Rms expectedAB = new Rms();
//        expectedAB.addMember(m1);
//        expectedAB.findMemberInOrder(m1);
//    }

}

