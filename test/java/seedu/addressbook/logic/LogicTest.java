package seedu.addressbook.logic;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.commands.*;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.person.*;
import seedu.addressbook.data.tag.Tag;
import seedu.addressbook.storage.StorageFile;

import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static seedu.addressbook.common.Messages.*;


public class LogicTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private StorageFile saveFile;
    private AddressBook addressBook;
    private Logic logic;


    @Before
    public void setup() throws Exception {
        saveFile = new StorageFile(saveFolder.newFile("testSaveFile.txt").getPath());
        addressBook = new AddressBook();
        saveFile.save(addressBook);
        logic = new Logic(saveFile, addressBook);
    }

    @Test
    public void constructor() {
        //Constructor is called in the setup() method which executes before every test, no need to call it here again.

        //Confirm the last shown list is empty
        assertEquals(Collections.emptyList(), logic.getLastShownList());
    }

    @Test
    public void executeInvalid() throws Exception {
        String invalidCommand = "       ";
        assertCommandBehavior(invalidCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the command and confirms that the result message is correct.
     * Both the 'address book' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, AddressBook, boolean, List)
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, AddressBook.empty(),false, Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the Logic object's state are as expected:<br>
     *      - the internal address book data are same as those in the {@code expectedAddressBook} <br>
     *      - the internal 'last shown list' matches the {@code expectedLastList} <br>
     *      - the storage file content matches data in {@code expectedAddressBook} <br>
     */
    private void assertCommandBehavior(String inputCommand,
                                      String expectedMessage,
                                      AddressBook expectedAddressBook,
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
        assertEquals(expectedAddressBook, addressBook);
        assertEquals(lastShownList, logic.getLastShownList());
        assertEquals(addressBook, saveFile.load());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the Logic object's state are as expected:<br>
     *      - the internal address book data are same as those in the {@code expectedAddressBook} <br>
     *      - the internal 'last shown list' matches the {@code expectedLastList} <br>
     *      - the storage file content matches data in {@code expectedAddressBook} <br>
     * This assertCommandBehavior check of scheduleList input
     */
    private void assertCommandBehavior(String inputCommand,
                                       String expectedMessage,
                                       AddressBook expectedAddressBook,
                                       boolean isRelevantPersonsExpected,
                                       List<? extends ReadOnlyPerson> lastShownList,
                                       Set<? extends Schedule> scheduleList) throws Exception {

        //Execute the command
        CommandResult r = logic.execute(inputCommand);

        //Confirm the result contains the right data
        assertEquals(expectedMessage, r.feedbackToUser);
        assertEquals(r.getRelevantPersons().isPresent(), isRelevantPersonsExpected);
        if(isRelevantPersonsExpected ){
            assertEquals(lastShownList, r.getRelevantPersons().get());
        }
        assertEquals(scheduleList, r.getRelevantAppointments().get());

        //Confirm the state of data is as expected
        assertEquals(expectedAddressBook, addressBook);
        assertEquals(lastShownList, logic.getLastShownList());
        assertEquals(addressBook, saveFile.load());
    }


    @Test
    public void executeUnknownCommandWord() throws Exception {
        String unknownCommand = "uicfhmowqewca";
        assertCommandBehavior(unknownCommand, HelpCommand.MESSAGE_ALL_USAGES);
    }

    @Test
    public void execute_help() throws Exception {
        assertCommandBehavior("help", HelpCommand.MESSAGE_ALL_USAGES);
    }

    @Test
    public void execute_exit() throws Exception {
        assertCommandBehavior("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

    @Test
    public void execute_clear() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        addressBook.addPerson(helper.generatePerson(1, true));
        addressBook.addPerson(helper.generatePerson(2, true));
        addressBook.addPerson(helper.generatePerson(3, true));

        assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, AddressBook.empty(), false, Collections.emptyList());
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
                "add []\\[;] n/S7778889T p/12345 e/valid@e.mail a/valid, address s/Doctor d/01-01-2001-11:00", Name.MESSAGE_NAME_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name n/S7778889T p/not_numbers e/valid@e.mail a/valid, address s/Doctor d/01-01-2001-11:00", Phone.MESSAGE_PHONE_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name n/S7778889T p/12345 e/notAnEmail a/valid, address s/Doctor d/01-01-2001-11:00", Email.MESSAGE_EMAIL_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name n/S7778889T p/12345 e/valid@e.mail a/valid, address s/Doctor d/01-01-2009", Schedule.MESSAGE_SCHEDULE_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name n/S7778889T p/12345 e/valid@e.mail a/valid, address s/Doctor d/11:00", Schedule.MESSAGE_SCHEDULE_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name n/S7778889T p/12345 e/valid@e.mail a/valid, address s/Doctor d/01-01-2001-11:00 t/invalid_-[.tag", Tag.MESSAGE_TAG_CONSTRAINTS);

    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Person toBeAdded = helper.adam();
        AddressBook expectedAB = new AddressBook();
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
        AddressBook expectedAB = new AddressBook();
        expectedAB.addPerson(toBeAdded);

        // setup starting state
        addressBook.addPerson(toBeAdded); // person already in internal address book

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
        AddressBook expectedAB = helper.generateAddressBook(false, true);
        List<? extends ReadOnlyPerson> expectedList = expectedAB.getAllPersons().immutableListView();

        // prepare address book state
        helper.addToAddressBook(addressBook, false, true);

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

        assertCommandBehavior(commandWord + " -1", expectedMessage, AddressBook.empty(), false, lastShownList);
        assertCommandBehavior(commandWord + " 0", expectedMessage, AddressBook.empty(), false, lastShownList);
        assertCommandBehavior(commandWord + " 3", expectedMessage, AddressBook.empty(), false, lastShownList);

    }

    @Test
    public void executeViewOnlyShowsNonPrivate() throws Exception {

        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, true);
        Person p2 = helper.generatePerson(2, false);
        List<Person> lastShownList = helper.generatePersonList(p1, p2);
        AddressBook expectedAB = helper.generateAddressBook(lastShownList);
        helper.addToAddressBook(addressBook, lastShownList);

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

        AddressBook expectedAB = new AddressBook();
        expectedAB.addPerson(p2);

        addressBook.addPerson(p2);
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
        AddressBook expectedAB = helper.generateAddressBook(lastShownList);
        helper.addToAddressBook(addressBook, lastShownList);

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

        AddressBook expectedAB = new AddressBook();
        expectedAB.addPerson(p1);

        addressBook.addPerson(p1);
        logic.setLastShownList(lastShownList);

        assertCommandBehavior("viewall 2",
                                Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK,
                                expectedAB,
                                false,
                                lastShownList);
    }

    @Test
    public void execute_editAppointment_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditAppointmentCommand.MESSAGE_USAGE);
        assertCommandBehavior("edit-appointment ", expectedMessage);
        assertCommandBehavior("edit-appointment arg not number", expectedMessage);
    }

    @Test
    public void execute_editAppointment_invalidIndex() throws Exception {
        assertInvalidIndexBehaviorForCommand("edit-appointment");
    }

    @Test
    public void execute_editAppointment_enterEditAppointmentMode() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, true);
        Person p2 = helper.generatePerson(2, false);
        List<Person> lastShownList = helper.generatePersonList(p1, p2);
        AddressBook expectedAB = helper.generateAddressBook(lastShownList);
        helper.addToAddressBook(addressBook, lastShownList);

        logic.setLastShownList(lastShownList);

        assertCommandBehavior("edit-appointment 1",
                String.format(EditAppointmentCommand.MESSAGE_EDIT_APPOINTMENT_MODE, p1.getName().toString()),
                expectedAB,
                false,
                lastShownList);
        EditAppointmentCommand.setEditingAppointmentState(false);

        assertCommandBehavior("edit-appointment 2",
                String.format(EditAppointmentCommand.MESSAGE_EDIT_APPOINTMENT_MODE, p2.getName().toString()),
                expectedAB,
                false,
                lastShownList);
        EditAppointmentCommand.setEditingAppointmentState(false);
    }

    @Test
    public void execute_tryToEditAppointmentPersonMissingInAddressBook_errorMessage() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);
        Person p2 = helper.generatePerson(2, false);
        List<Person> lastShownList = helper.generatePersonList(p1, p2);

        AddressBook expectedAB = new AddressBook();
        expectedAB.addPerson(p1);

        addressBook.addPerson(p1);
        logic.setLastShownList(lastShownList);

        assertCommandBehavior("edit-appointment 2",
                Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK,
                expectedAB,
                false,
                lastShownList);
    }

    @Test
    public void executeInEditAppointmentMode_unknownCommandWord() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, true);
        Person p2 = helper.generatePerson(2, false);
        List<Person> lastShownList = helper.generatePersonList(p1, p2);
        AddressBook expectedAB = helper.generateAddressBook(lastShownList);
        helper.addToAddressBook(addressBook, lastShownList);

        logic.setLastShownList(lastShownList);
        EditAppointmentCommand.setEditingAppointmentState(true);
        EditAppointmentCommand.setEditingPersonIndex(1);

        String unknownCommand = "uicfhmowqewca";
        assertCommandBehavior(unknownCommand,
                String.format(HelpEditAppointment.MESSAGE_ALL_USAGES, p1.getName().toString()),
                expectedAB,
                false,
                lastShownList);
        EditAppointmentCommand.setEditingAppointmentState(false);
    }

    @Test
    public void executeInEditAppointmentMode_help() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, true);
        Person p2 = helper.generatePerson(2, false);
        List<Person> lastShownList = helper.generatePersonList(p1, p2);
        AddressBook expectedAB = helper.generateAddressBook(lastShownList);
        helper.addToAddressBook(addressBook, lastShownList);

        logic.setLastShownList(lastShownList);
        EditAppointmentCommand.setEditingAppointmentState(true);
        EditAppointmentCommand.setEditingPersonIndex(1);

        assertCommandBehavior("help",
                String.format(HelpEditAppointment.MESSAGE_ALL_USAGES, p1.getName().toString()),
                expectedAB,
                false,
                lastShownList);
        EditAppointmentCommand.setEditingAppointmentState(false);
    }

    @Test
    public void executeInEditAppointmentMode_done() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, true);
        Person p2 = helper.generatePerson(2, false);
        List<Person> lastShownList = helper.generatePersonList(p1, p2);
        AddressBook expectedAB = helper.generateAddressBook(lastShownList);
        helper.addToAddressBook(addressBook, lastShownList);

        logic.setLastShownList(lastShownList);
        EditAppointmentCommand.setEditingAppointmentState(true);
        EditAppointmentCommand.setEditingPersonIndex(1);

        assertCommandBehavior("done",
                String.format(ExitEditAppointment.MESSAGE_FINISH_EDIT_APPOINTMENT, p1.getName().toString()),
                expectedAB,
                false,
                lastShownList);
        EditAppointmentCommand.setEditingAppointmentState(false);
    }

    @Test
    public void executeInEditAppointmentMode_list_showsAllSchedules() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, true);
        Person p2 = helper.generatePerson(2, false);
        List<Person> lastShownList = helper.generatePersonList(p1, p2);
        AddressBook expectedAB = helper.generateAddressBook(lastShownList);
        helper.addToAddressBook(addressBook, lastShownList);

        logic.setLastShownList(lastShownList);
        EditAppointmentCommand.setEditingAppointmentState(true);
        EditAppointmentCommand.setEditingPersonIndex(1);
        Set<Schedule> appointments = p1.getSchedules();

        assertCommandBehavior("list",
                ListAppointment.getMessageForAppointmentMadeByPerson(appointments, p1.getName()),
                expectedAB,
                false,
                lastShownList,
                appointments);
        EditAppointmentCommand.setEditingAppointmentState(false);
    }

    @Test
    public void executeInEditAppointmentMode_add_invalidArgsFormat() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, true);
        Person p2 = helper.generatePerson(2, false);
        List<Person> lastShownList = helper.generatePersonList(p1, p2);
        AddressBook expectedAB = helper.generateAddressBook(lastShownList);
        helper.addToAddressBook(addressBook, lastShownList);

        logic.setLastShownList(lastShownList);
        EditAppointmentCommand.setEditingAppointmentState(true);
        EditAppointmentCommand.setEditingPersonIndex(1);

        assertCommandBehavior("add",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppointment.MESSAGE_USAGE),
                expectedAB,
                false,
                lastShownList);
        EditAppointmentCommand.setEditingAppointmentState(false);
    }

    @Test
    public void executeInEditAppointmentMode_add_invalidScheduleFormat() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, true);
        Person p2 = helper.generatePerson(2, false);
        List<Person> lastShownList = helper.generatePersonList(p1, p2);
        AddressBook expectedAB = helper.generateAddressBook(lastShownList);
        helper.addToAddressBook(addressBook, lastShownList);

        logic.setLastShownList(lastShownList);
        EditAppointmentCommand.setEditingAppointmentState(true);
        EditAppointmentCommand.setEditingPersonIndex(1);

        String invalidScheduleFormat1 = "not DD-MM-YYYY-HH:MM";
        String invalidScheduleFormat2 = "01-01-2019";
        String invalidScheduleFormat3 = "23:59";
        String invalidScheduleFormat4 = "01-01-2019-2359";
        assertCommandBehavior("add " + invalidScheduleFormat1,
                Schedule.MESSAGE_SCHEDULE_CONSTRAINTS,
                expectedAB,
                false,
                lastShownList);
        assertCommandBehavior("add " + invalidScheduleFormat2,
                Schedule.MESSAGE_SCHEDULE_CONSTRAINTS,
                expectedAB,
                false,
                lastShownList);
        assertCommandBehavior("add " + invalidScheduleFormat3,
                Schedule.MESSAGE_SCHEDULE_CONSTRAINTS,
                expectedAB,
                false,
                lastShownList);
        assertCommandBehavior("add " + invalidScheduleFormat4,
                Schedule.MESSAGE_SCHEDULE_CONSTRAINTS,
                expectedAB,
                false,
                lastShownList);
        EditAppointmentCommand.setEditingAppointmentState(false);
    }

    private String scheduleSetToStringWithoutSpaceAtTheEnd(Set<Schedule> appointments) {
        final StringBuilder scheduleList = new StringBuilder();
        for (Schedule schedule : appointments) {
            scheduleList.append(schedule.toString());
        }
        return scheduleList.toString().trim();
    }

    private String scheduleSetToString(Set<Schedule> appointments) {
        final StringBuilder scheduleList = new StringBuilder();
        for (Schedule schedule : appointments) {
            scheduleList.append(schedule.toString());
        }
        return scheduleList.toString();
    }

    @Test
    public void executeInEditAppointmentMode_addDuplicate_notAllowed() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, true);
        Person p2 = helper.generatePerson(2, false);
        List<Person> lastShownList = helper.generatePersonList(p1, p2);
        AddressBook expectedAB = helper.generateAddressBook(lastShownList);
        helper.addToAddressBook(addressBook, lastShownList);

        logic.setLastShownList(lastShownList);
        EditAppointmentCommand.setEditingAppointmentState(true);
        EditAppointmentCommand.setEditingPersonIndex(1);
        Set<Schedule> appointments = p1.getSchedules();

        String scheduleString = scheduleSetToStringWithoutSpaceAtTheEnd(appointments);

        assertCommandBehavior("add " + scheduleString,
                String.format(AddAppointment.MESSAGE_NO_CHANGE_MADE, p1.getName().toString(), scheduleString),
                expectedAB,
                false,
                lastShownList);
        EditAppointmentCommand.setEditingAppointmentState(false);
    }

    @Test
    public void executeInEditAppointmentMode_add_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, true);
        Person p2 = helper.generatePerson(2, false);
        List<Person> lastShownList = helper.generatePersonList(p1, p2);
        AddressBook expectedAB = helper.generateAddressBook(lastShownList);
        helper.addToAddressBook(addressBook, lastShownList);

        List<ReadOnlyPerson> lastShownEditableList = helper.generateEditablePersonList(p1, p2);

        logic.setLastShownList(lastShownList);
        logic.setEditableLastShownList(lastShownEditableList);

        Set<Schedule> appointmentsToBeAdd = new HashSet<>();
        appointmentsToBeAdd.add(helper.generateSchedule(1));
        appointmentsToBeAdd.add(helper.generateSchedule(2));
        appointmentsToBeAdd.add(helper.generateSchedule(3));
        appointmentsToBeAdd.removeAll(p1.getSchedules());

        String scheduleString = scheduleSetToString(appointmentsToBeAdd);

        EditAppointmentCommand.setEditingAppointmentState(true);
        EditAppointmentCommand.setEditingPersonIndex(1);


        assertCommandBehavior("add " + scheduleString,
                String.format(AddAppointment.MESSAGE_ADDED_PERSON_APPOINTMENT, p1.getName())
                + String.format(AddAppointment.MESSAGE_FOR_ADDED_APPOINTMENTS, scheduleString),
                expectedAB,
                true,
                lastShownList);
        EditAppointmentCommand.setEditingAppointmentState(false);
    }

    @Test
    public void executeInEditAppointmentMode_addDuplicateAndSuccessful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, true);
        Person p2 = helper.generatePerson(2, false);
        List<Person> lastShownList = helper.generatePersonList(p1, p2);
        AddressBook expectedAB = helper.generateAddressBook(lastShownList);
        helper.addToAddressBook(addressBook, lastShownList);

        List<ReadOnlyPerson> lastShownEditableList = helper.generateEditablePersonList(p1, p2);

        logic.setLastShownList(lastShownList);
        logic.setEditableLastShownList(lastShownEditableList);

        Set<Schedule> appointmentsToBeAdd = new HashSet<>();
        appointmentsToBeAdd.add(helper.generateSchedule(1));
        appointmentsToBeAdd.add(helper.generateSchedule(2));
        appointmentsToBeAdd.add(helper.generateSchedule(3));
        appointmentsToBeAdd.removeAll(p1.getSchedules());

        String scheduleStringAdd = scheduleSetToString(appointmentsToBeAdd);
        String scheduleStringDuplicate = scheduleSetToString(p1.getSchedules());

        appointmentsToBeAdd.addAll(p1.getSchedules());
        String scheduleStringInput = scheduleSetToString(appointmentsToBeAdd);

        EditAppointmentCommand.setEditingAppointmentState(true);
        EditAppointmentCommand.setEditingPersonIndex(1);


        assertCommandBehavior("add " + scheduleStringInput,
                String.format(AddAppointment.MESSAGE_ADDED_PERSON_APPOINTMENT, p1.getName())
                + String.format(AddAppointment.MESSAGE_FOR_ADDED_APPOINTMENTS, scheduleStringAdd)
                        + String.format(AddAppointment.MESSAGE_FOR_DUPLICATE_APPOINTMENTS, scheduleStringDuplicate),
                expectedAB,
                true,
                lastShownList);
        EditAppointmentCommand.setEditingAppointmentState(false);
    }

    @Test
    public void executeInEditAppointmentMode_delete_invalidArgsFormat() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, true);
        Person p2 = helper.generatePerson(2, false);
        List<Person> lastShownList = helper.generatePersonList(p1, p2);
        AddressBook expectedAB = helper.generateAddressBook(lastShownList);
        helper.addToAddressBook(addressBook, lastShownList);

        logic.setLastShownList(lastShownList);
        EditAppointmentCommand.setEditingAppointmentState(true);
        EditAppointmentCommand.setEditingPersonIndex(1);

        assertCommandBehavior("delete",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAppointment.MESSAGE_USAGE),
                expectedAB,
                false,
                lastShownList);
        EditAppointmentCommand.setEditingAppointmentState(false);
    }

    @Test
    public void executeInEditAppointmentMode_delete_invalidScheduleFormat() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, true);
        Person p2 = helper.generatePerson(2, false);
        List<Person> lastShownList = helper.generatePersonList(p1, p2);
        AddressBook expectedAB = helper.generateAddressBook(lastShownList);
        helper.addToAddressBook(addressBook, lastShownList);

        logic.setLastShownList(lastShownList);
        EditAppointmentCommand.setEditingAppointmentState(true);
        EditAppointmentCommand.setEditingPersonIndex(1);

        String invalidScheduleFormat1 = "not DD-MM-YYYY-HH:MM";
        String invalidScheduleFormat2 = "01-01-2019";
        String invalidScheduleFormat3 = "23:59";
        String invalidScheduleFormat4 = "01-01-2019-2359";
        assertCommandBehavior("delete " + invalidScheduleFormat1,
                Schedule.MESSAGE_SCHEDULE_CONSTRAINTS,
                expectedAB,
                false,
                lastShownList);
        assertCommandBehavior("delete " + invalidScheduleFormat2,
                Schedule.MESSAGE_SCHEDULE_CONSTRAINTS,
                expectedAB,
                false,
                lastShownList);
        assertCommandBehavior("delete " + invalidScheduleFormat3,
                Schedule.MESSAGE_SCHEDULE_CONSTRAINTS,
                expectedAB,
                false,
                lastShownList);
        assertCommandBehavior("delete " + invalidScheduleFormat4,
                Schedule.MESSAGE_SCHEDULE_CONSTRAINTS,
                expectedAB,
                false,
                lastShownList);
        EditAppointmentCommand.setEditingAppointmentState(false);
    }

    @Test
    public void executeInEditAppointmentMode_delete_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, true);
        Person p2 = helper.generatePerson(2, false);
        List<Person> lastShownList = helper.generatePersonList(p1, p2);
        AddressBook expectedAB = helper.generateAddressBook(lastShownList);
        helper.addToAddressBook(addressBook, lastShownList);

        logic.setLastShownList(lastShownList);

        List<ReadOnlyPerson> lastShownEditableList = helper.generateEditablePersonList(p1, p2);
        logic.setEditableLastShownList(lastShownEditableList);

        EditAppointmentCommand.setEditingAppointmentState(true);
        EditAppointmentCommand.setEditingPersonIndex(1);
        Set<Schedule> appointments = p1.getSchedules();

        String scheduleString = scheduleSetToString(appointments);

        assertCommandBehavior("delete " + scheduleString,
                String.format(DeleteAppointment.MESSAGE_DELETE_PERSON_APPOINTMENT, p1.getName())
                        + String.format(DeleteAppointment.MESSAGE_FOR_DELETED_APPOINTMENTS, scheduleString),
                expectedAB,
                true,
                lastShownList);
        EditAppointmentCommand.setEditingAppointmentState(false);
    }

    @Test
    public void executeInEditAppointmentMode_deleteNoneExist() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, true);
        Person p2 = helper.generatePerson(2, false);
        List<Person> lastShownList = helper.generatePersonList(p1, p2);
        AddressBook expectedAB = helper.generateAddressBook(lastShownList);
        helper.addToAddressBook(addressBook, lastShownList);

        logic.setLastShownList(lastShownList);

        Set<Schedule> appointments = new HashSet<>();
        appointments.add(helper.generateSchedule(1));
        appointments.add(helper.generateSchedule(2));
        appointments.add(helper.generateSchedule(3));
        appointments.removeAll(p1.getSchedules());

        String scheduleString = scheduleSetToStringWithoutSpaceAtTheEnd(appointments);

        EditAppointmentCommand.setEditingAppointmentState(true);
        EditAppointmentCommand.setEditingPersonIndex(1);


        assertCommandBehavior("delete " + scheduleString,
                String.format(DeleteAppointment.MESSAGE_NO_CHANGE_MADE, p1.getName().toString(), scheduleString),
                expectedAB,
                false,
                lastShownList);
        EditAppointmentCommand.setEditingAppointmentState(false);
    }

    @Test
    public void executeInEditAppointmentMode_DeleteNonExistentAndSuccessful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, true);
        Person p2 = helper.generatePerson(2, false);
        List<Person> lastShownList = helper.generatePersonList(p1, p2);
        AddressBook expectedAB = helper.generateAddressBook(lastShownList);
        helper.addToAddressBook(addressBook, lastShownList);

        List<ReadOnlyPerson> lastShownEditableList = helper.generateEditablePersonList(p1, p2);

        logic.setLastShownList(lastShownList);
        logic.setEditableLastShownList(lastShownEditableList);

        Set<Schedule> appointmentsToBeAdd = new HashSet<>();
        appointmentsToBeAdd.add(helper.generateSchedule(1));
        appointmentsToBeAdd.add(helper.generateSchedule(2));
        appointmentsToBeAdd.add(helper.generateSchedule(3));
        appointmentsToBeAdd.removeAll(p1.getSchedules());

        String scheduleStringNoneExistent = scheduleSetToString(appointmentsToBeAdd);
        String scheduleStringDelete = scheduleSetToString(p1.getSchedules());

        appointmentsToBeAdd.addAll(p1.getSchedules());
        String scheduleStringInput = scheduleSetToString(appointmentsToBeAdd);

        EditAppointmentCommand.setEditingAppointmentState(true);
        EditAppointmentCommand.setEditingPersonIndex(1);


        assertCommandBehavior("delete " + scheduleStringInput,
                String.format(DeleteAppointment.MESSAGE_DELETE_PERSON_APPOINTMENT, p1.getName())
                        + String.format(DeleteAppointment.MESSAGE_FOR_DELETED_APPOINTMENTS, scheduleStringDelete)
                        + String.format(DeleteAppointment.MESSAGE_FOR_MISSING_APPOINTMENTS, scheduleStringNoneExistent),
                expectedAB,
                true,
                lastShownList);
        EditAppointmentCommand.setEditingAppointmentState(false);
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

        AddressBook expectedAB = helper.generateAddressBook(threePersons);
        expectedAB.removePerson(p2);


        helper.addToAddressBook(addressBook, threePersons);
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

        AddressBook expectedAB = helper.generateAddressBook(threePersons);
        expectedAB.removePerson(p2);

        helper.addToAddressBook(addressBook, threePersons);
        addressBook.removePerson(p2);
        logic.setLastShownList(threePersons);

        assertCommandBehavior("delete 2",
                                Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK,
                                expectedAB,
                                false,
                                threePersons);
    }

    @Test
    public void execute_link_success() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePersonWithTitle(1, "Doctor");
        Person p2 = helper.generatePersonWithTitle(2, "Patient");

        List<Person> twoPersons = helper.generatePersonList(p1, p2);

        helper.addToAddressBook(addressBook, twoPersons);
        logic.setLastShownList(twoPersons);

        AddressBook expectedAB = helper.generateAddressBook(twoPersons);
        String expectedMessage = String.format(LinkCommand.MESSAGE_SUCCESS, p1.getName(), p2.getName());
        assertCommandBehavior("link 1 2", expectedMessage, expectedAB, false, twoPersons);
        assertEquals(true, p1.getAssociateList().contains(new Associated(p2.getName()+" "+p2.getNric(), p2.getTitle(), p1.getTitle())));
        assertEquals(true, p2.getAssociateList().contains(new Associated(p1.getName()+" "+p1.getNric(), p1.getTitle(), p2.getTitle())));
    }

    @Test
    public void execute_link_duplicateAssociation() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePersonWithTitle(1, "Doctor");
        Person p2 = helper.generatePersonWithTitle(2, "Patient");

        List<Person> twoPersons = helper.generatePersonList(p1, p2);

        helper.addToAddressBook(addressBook, twoPersons);
        logic.setLastShownList(twoPersons);
        addressBook.linkTwoPerson(p1, p2);
        AddressBook expectedAB = helper.generateAddressBook(twoPersons);
        String expectedMessage = String.format(LinkCommand.MESSAGE_DUPLICATE_ASSOCIATION);
        assertCommandBehavior("link 1 2", expectedMessage, expectedAB, false, twoPersons);
    }

    @Test
    public void execute_link_sameTitle() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePersonWithTitle(1, "Doctor");
        Person p2 = helper.generatePersonWithTitle(2, "Doctor");

        List<Person> twoPersons = helper.generatePersonList(p1, p2);

        helper.addToAddressBook(addressBook, twoPersons);
        logic.setLastShownList(twoPersons);
        AddressBook expectedAB = helper.generateAddressBook(twoPersons);
        String expectedMessage = String.format(LinkCommand.MESSAGE_SAME_TITLE_FAILURE);
        assertCommandBehavior("link 1 2", expectedMessage, expectedAB, false, twoPersons);
    }

    @Test
    public void execute_unlink_success() throws Exception{
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePersonWithTitle(1, "Doctor");
        Person p2 = helper.generatePersonWithTitle(2, "Patient");

        List<Person> twoPersons = helper.generatePersonList(p1, p2);

        helper.addToAddressBook(addressBook, twoPersons);
        logic.setLastShownList(twoPersons);

        addressBook.linkTwoPerson(p1, p2);
        AddressBook expectedAB = helper.generateAddressBook(twoPersons);
        String expectedMessage = String.format(UnLinkCommand.MESSAGE_SUCCESS, p1.getName(), p2.getName());
        assertCommandBehavior("unlink 1 2", expectedMessage, expectedAB, false, twoPersons);
        assertEquals(false, p1.getAssociateList().contains(new Associated(p2.getName()+" "+p2.getNric(), p2.getTitle(), p1.getTitle())));
        assertEquals(false, p2.getAssociateList().contains(new Associated(p1.getName()+" "+p1.getNric(), p1.getTitle(), p2.getTitle())));
    }

    @Test
    public void execute_unlink_noAssociation() throws Exception{
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePersonWithTitle(1, "Doctor");
        Person p2 = helper.generatePersonWithTitle(2, "Patient");

        List<Person> twoPersons = helper.generatePersonList(p1, p2);

        helper.addToAddressBook(addressBook, twoPersons);
        logic.setLastShownList(twoPersons);

        AddressBook expectedAB = helper.generateAddressBook(twoPersons);
        String expectedMessage = String.format(UnLinkCommand.MESSAGE_NO_ASSOCIATION);
        assertCommandBehavior("unlink 1 2", expectedMessage, expectedAB, false, twoPersons);
    }

    @Test
    public void execute_undo_nothingToUndo() throws Exception {
        String expectedMessage = String.format(UndoCommand.MESSAGE_NO_COMMAND);
        assertCommandBehavior("undo", expectedMessage);
    }

    @Test
    public void execute_undo_undoAddCommand() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);
        Person p2 = helper.generatePerson(2, false);
        Person p3 = helper.generatePerson(3, false);

        List<Person> threePersons = helper.generatePersonList(p1, p2, p3);
        List<Person> twoPersons = helper.generatePersonList(p1, p2);


        helper.addToAddressBook(addressBook, threePersons);
        AddCommand testAdd = new AddCommand(p3);
        testAdd.setData(addressBook,null,null);
        testAdd.saveUndoableToHistory("test");
        AddressBook expectedAB = helper.generateAddressBook(twoPersons);
        logic.setLastShownList(twoPersons);
        String expectedMessage = String.format(UndoCommand.MESSAGE_SUCCESS);
        assertCommandBehavior("undo", expectedMessage, expectedAB, true, twoPersons);
        assertEquals(false, addressBook.containsPerson(p3));
    }

    @Test
    public void execute_undo_undoClearCommand() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);
        List<Person> onePerson = helper.generatePersonList(p1);

        helper.addToAddressBook(addressBook, onePerson);
        ClearCommand testClear = new ClearCommand();
        testClear.setData(addressBook, null, null);
        testClear.setCopied(addressBook.getAllPersons());
        testClear.saveUndoableToHistory("test");
        addressBook.clear();
        AddressBook expectedAB = helper.generateAddressBook(onePerson);
        String expectedMessage = String.format(UndoCommand.MESSAGE_SUCCESS);
        assertCommandBehavior("undo", expectedMessage, expectedAB, true, onePerson);
        assertEquals(true, addressBook.containsPerson(p1));
    }

    @Test
    public void execute_undo_undoDelete() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);

        List<Person> onePerson = helper.generatePersonList(p1);

        AddressBook expectedAB = helper.generateAddressBook(onePerson);
        DeleteCommand testDelete = new DeleteCommand(-1);
        testDelete.setData(addressBook, null, null);
        testDelete.setBackup(p1);
        testDelete.saveUndoableToHistory("test");
        String expectedMessage = String.format(UndoCommand.MESSAGE_SUCCESS);
        assertCommandBehavior("undo", expectedMessage, expectedAB, true, onePerson);
        assertEquals(true, addressBook.containsPerson(p1));
    }

    @Test
    public void execute_undo_undoLink() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePersonWithTitle(1, "Doctor");
        Person p2 = helper.generatePersonWithTitle(2, "Patient");

        List<Person> twoPersons = helper.generatePersonList(p1, p2);

        helper.addToAddressBook(addressBook, twoPersons);
        logic.setLastShownList(twoPersons);

        addressBook.linkTwoPerson(p1, p2);
        LinkCommand testLink = new LinkCommand(-1,-1);
        testLink.setData(addressBook, null, null);
        testLink.setTargets(p1, p2);
        testLink.saveUndoableToHistory("test");
        String expectedMessage = String.format(UndoCommand.MESSAGE_SUCCESS);
        AddressBook expectedAB = helper.generateAddressBook(twoPersons);
        assertCommandBehavior("undo", expectedMessage, expectedAB, true, twoPersons);
        assertEquals(false, p1.getAssociateList().contains(new Associated(p2.getName()+" "+p2.getNric(), p2.getTitle(), p1.getTitle())));
        assertEquals(false, p2.getAssociateList().contains(new Associated(p1.getName()+" "+p1.getNric(), p1.getTitle(), p2.getTitle())));
    }

    @Test
    public void execute_undo_undoUnLink() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePersonWithTitle(1, "Doctor");
        Person p2 = helper.generatePersonWithTitle(2, "Patient");

        List<Person> twoPersons = helper.generatePersonList(p1, p2);

        helper.addToAddressBook(addressBook, twoPersons);
        logic.setLastShownList(twoPersons);
        UnLinkCommand testUnLink = new UnLinkCommand(-1, -1);
        testUnLink.setData(addressBook, null, null);
        testUnLink.setTargets(p1, p2);
        testUnLink.saveUndoableToHistory("test");
        String expectedMessage = String.format(UndoCommand.MESSAGE_SUCCESS);
        AddressBook expectedAB = helper.generateAddressBook(twoPersons);
        assertCommandBehavior("undo", expectedMessage, expectedAB, true, twoPersons);
        assertEquals(true, p1.getAssociateList().contains(new Associated(p2.getName()+" "+p2.getNric(), p2.getTitle(), p1.getTitle())));
        assertEquals(true, p2.getAssociateList().contains(new Associated(p1.getName()+" "+p1.getNric(), p1.getTitle(), p2.getTitle())));
    }

    @Test
    public void execute_redo_nothingToRedo() throws Exception {
        String expectedMessage = String.format(RedoCommand.MESSAGE_FAILURE);
        assertCommandBehavior("redo", expectedMessage);
    }

    @Test
    public void execute_history_noHistory() throws Exception {
        String expectedMessage = String.format(HistoryCommand.MESSAGE_NO_HISTORY);
        assertCommandBehavior("history", expectedMessage);
    }

    @Test
    public void execute_history_showHistory() throws Exception {
        ListCommand test = new ListCommand();
        test.setData(addressBook, null, null);
        test.saveHistory(ListCommand.COMMAND_WORD);
        String expectedMessage = String.format(ListCommand.COMMAND_WORD + "\n");
        assertCommandBehavior("history", expectedMessage);
    }

    @Test
    public void execute_link_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE);
        assertCommandBehavior("link ", expectedMessage);
    }

    @Test
    public void execute_find_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("find ", expectedMessage);
    }

    @Test
    public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person pTarget1 = helper.generatePersonWithName("bla bla KEY bla", 1);
        Person pTarget2 = helper.generatePersonWithName("bla KEY bla bceofeia", 2);
        Person p1 = helper.generatePersonWithName("KE Y", 3);
        Person p2 = helper.generatePersonWithName("KEYKEYKEY sduauo", 4);

        List<Person> fourPersons = helper.generatePersonList(p1, pTarget1, p2, pTarget2);
        AddressBook expectedAB = helper.generateAddressBook(fourPersons);
        List<Person> expectedList = helper.generatePersonList(pTarget1, pTarget2);
        helper.addToAddressBook(addressBook, fourPersons);

        assertCommandBehavior("find KEY",
                                Command.getMessageForPersonListShownSummary(expectedList),
                                expectedAB,
                                true,
                                expectedList);
    }

    @Test
    public void execute_find_isCaseInsensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person pTarget1 = helper.generatePersonWithName("bla bla KEY bla", 1);
        Person pTarget2 = helper.generatePersonWithName("bla KEY bla bceofeia", 2);
        Person p1 = helper.generatePersonWithName("key key", 3);
        Person p2 = helper.generatePersonWithName("KEy sduauo", 4);

        List<Person> fourPersons = helper.generatePersonList(p1, pTarget1, p2, pTarget2);
        AddressBook expectedAB = helper.generateAddressBook(fourPersons);
        List<Person> expectedList = helper.generatePersonList(p1, pTarget1, p2, pTarget2);
        helper.addToAddressBook(addressBook, fourPersons);

        assertCommandBehavior("find KEY",
                                Command.getMessageForPersonListShownSummary(expectedList),
                                expectedAB,
                                true,
                                expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person pTarget1 = helper.generatePersonWithName("bla bla KEY bla", 1);
        Person pTarget2 = helper.generatePersonWithName("bla rAnDoM bla bceofeia", 2);
        Person p1 = helper.generatePersonWithName("key key", 3);
        Person p2 = helper.generatePersonWithName("KEy sduauo", 4);

        List<Person> fourPersons = helper.generatePersonList(p1, pTarget1, p2, pTarget2);
        AddressBook expectedAB = helper.generateAddressBook(fourPersons);
        List<Person> expectedList = helper.generatePersonList(p1, pTarget1, p2, pTarget2);
        helper.addToAddressBook(addressBook, fourPersons);

        assertCommandBehavior("find KEY rAnDoM",
                                Command.getMessageForPersonListShownSummary(expectedList),
                                expectedAB,
                                true,
                                expectedList);
    }

    /**
     * A utility class to generate test data.
     */
    class TestDataHelper{

        Person adam() throws Exception {
            Name name = new Name("Adam Brown");
            Nric nric = new Nric("S4567891T", true);
            Phone privatePhone = new Phone("111111", true);
            Email email = new Email("adam@gmail.com", false);
            Address privateAddress = new Address("111, alpha street", true);
            Title title = new Title("Patient");
            Schedule schedule1 = new Schedule("25-12-2019-11:00");
            Schedule schedule2 = new Schedule("25-03-2018-23:00");
            Set<Schedule> schedules = new HashSet<>(Arrays.asList(schedule1, schedule2));
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("tag2");
            Set<Tag> tags = new HashSet<>(Arrays.asList(tag1, tag2));
            Set<Associated> associated= new HashSet<>();
            return new Person(name, nric, privatePhone, email, privateAddress, title, schedules, tags, associated);
        }

        /**
         * Generates a valid person using the given seed.
         * Running this function with the same parameter values guarantees the returned person will have the same state.
         * Each unique seed will generate a unique Person object.
         *
         * @param seed used to generate the person data field values
         * @param isAllFieldsPrivate determines if private-able fields (phone, email, address) will be private
         */
        Person generatePerson(int seed, boolean isAllFieldsPrivate) throws Exception {
            return new Person(
                    new Name("Person " + seed),
                    new Nric("S12343" + seed + seed + "Y", isAllFieldsPrivate),
                    new Phone("" + Math.abs(seed), isAllFieldsPrivate),
                    new Email(seed + "@email", isAllFieldsPrivate),
                    new Address("House of " + seed, isAllFieldsPrivate),
                    new Title("Doctor"),
                    new HashSet<>(Arrays.asList(new Schedule("26-01-2019-11:00"), new Schedule("19-02-2019-23:00"))),
                    new HashSet<>(Arrays.asList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1)))),
                    new HashSet<>(Arrays.asList(new Associated("associate1")))
            );
        }
        /**
         * Generates a valid schedule using the given seed.
         * Each unique seed will generate a unique schedule in String.
         *
         * @param seed used to generate the schedule string
         */
        Schedule generateSchedule(int seed) throws Exception {
            int num1 = seed%28;
            int num2 = (seed*7)%100;
            int num3 = (seed*7)%24;
            return new Schedule((num1 < 10 ? "0" : "") + num1 + "-12-20" + (num2 < 10 ? "0" : "") + num2 + "-" + (num3 < 10 ? "0" : "") + num3 + ":00");
        }
        /**
         * Generates a valid person using the given seed.
         * Running this function with the same parameter values guarantees the returned person will have the same state.
         * Each unique seed will generate a unique Person object.
         *
         * @param seed used to generate the person data field values
         * @param title determines if the person is created as a doctor or patient
         */
        Person generatePersonWithTitle(int seed, String title) throws Exception {
            return new Person(
                    new Name("Person " + seed),
                    new Nric("S12343" + seed + seed + "Y", false),
                    new Phone("" + Math.abs(seed), false),
                    new Email(seed + "@email", false),
                    new Address("House of " + seed, false),
                    new Title(title),
                    new HashSet<>(Arrays.asList(new Schedule("26-01-2019-11:00"), new Schedule("19-02-2019-23:00"))),
                    new HashSet<>(Arrays.asList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1)))),
                    new HashSet<>(Arrays.asList(new Associated("associate1")))
            );
        }

        /** Generates the correct add command based on the person given */
        String generateAddCommand(Person p) {
            StringJoiner cmd = new StringJoiner(" ");

            cmd.add("add");

            cmd.add(p.getName().toString());
            cmd.add((p.getNric().isPrivate() ? "pn/" : "p/") + p.getNric());
            cmd.add((p.getPhone().isPrivate() ? "pp/" : "p/") + p.getPhone());
            cmd.add((p.getEmail().isPrivate() ? "pe/" : "e/") + p.getEmail());
            cmd.add((p.getAddress().isPrivate() ? "pa/" : "a/") + p.getAddress());
            cmd.add(("s/") + p.getTitle());

            Set<Schedule> schedules = p.getSchedules();
            for(Schedule t: schedules){
                cmd.add("d/" + t.value );
            }
            Set<Tag> tags = p.getTags();
            for(Tag t: tags){
                cmd.add("t/" + t.tagName);
            }

            return cmd.toString();
        }

        /**
         * Generates an AddressBook with auto-generated persons.
         * @param isPrivateStatuses flags to indicate if all contact details of respective persons should be set to
         *                          private.
         */
        AddressBook generateAddressBook(Boolean... isPrivateStatuses) throws Exception{
            AddressBook addressBook = new AddressBook();
            addToAddressBook(addressBook, isPrivateStatuses);
            return addressBook;
        }

        /**
         * Generates an AddressBook based on the list of Persons given.
         */
        AddressBook generateAddressBook(List<Person> persons) throws Exception{
            AddressBook addressBook = new AddressBook();
            addToAddressBook(addressBook, persons);
            return addressBook;
        }

        /**
         * Adds auto-generated Person objects to the given AddressBook
         * @param addressBook The AddressBook to which the Persons will be added
         * @param isPrivateStatuses flags to indicate if all contact details of generated persons should be set to
         *                          private.
         */
        void addToAddressBook(AddressBook addressBook, Boolean... isPrivateStatuses) throws Exception{
            addToAddressBook(addressBook, generatePersonList(isPrivateStatuses));
        }

        /**
         * Adds the given list of Persons to the given AddressBook
         */
        void addToAddressBook(AddressBook addressBook, List<Person> personsToAdd) throws Exception{
            for(Person p: personsToAdd){
                addressBook.addPerson(p);
            }
        }

        /**
         * Creates a list of ReadOnlyPersons based on the give Person objects.
         */
        List<ReadOnlyPerson> generateEditablePersonList(Person... persons) throws Exception{
            List<ReadOnlyPerson> personList = new ArrayList<>();
            for(Person p: persons){
                personList.add(p);
            }
            return personList;
        }

        /**
         * Creates a list of Persons based on the give Person objects.
         */
        List<Person> generatePersonList(Person... persons) throws Exception{
            List<Person> personList = new ArrayList<>();
            for(Person p: persons){
                personList.add(p);
            }
            return personList;
        }

        /**
         * Generates a list of Persons based on the flags.
         * @param isPrivateStatuses flags to indicate if all contact details of respective persons should be set to
         *                          private.
         */
        List<Person> generatePersonList(Boolean... isPrivateStatuses) throws Exception{
            List<Person> persons = new ArrayList<>();
            int i = 1;
            for(Boolean p: isPrivateStatuses){
                persons.add(generatePerson(i++, p));
            }
            return persons;
        }

        /**
         * Generates a Person object with given name. Other fields will have some dummy values.
         */
         Person generatePersonWithName(String name, int seed) throws Exception {
            return new Person(
                    new Name(name),
                    new Nric("S98765" + seed + seed + "P", false),
                    new Phone("1", false),
                    new Email("1@email", false),
                    new Address("House of 1", false),
                    new Title("Doctor"),
                    Collections.singleton(new Schedule( "27-01-2019-23:00")),
                    Collections.singleton(new Tag("tag")),
                    Collections.singleton(new Associated("associate1"))
            );
        }
    }

}
