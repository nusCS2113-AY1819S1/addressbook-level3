package seedu.addressbook.commands;

import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.Schedule;

import java.util.List;
import java.util.Set;


/**
 * Lists all appointment of the person selected in the address book to the user.
 */
public class ListAppoinment extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays all appointment of the person selected as a list with index numbers.\n\t"
            + "Example: " + COMMAND_WORD;


    @Override
    public CommandResult execute() {
        commandHistory.addHistory(COMMAND_WORD);
        this.setTargetIndex(checkEditingPersonIndex());
        final ReadOnlyPerson target = getTargetPerson();
        Set<Schedule> appointments = target.getSchedules();
        return new CommandResult(getMessageForAppointmentMadeByPerson(appointments, target.getName()), appointments);
        //List<ReadOnlyPerson> allPersons = addressBook.getAllPersons().immutableListView();
        //return new CommandResult(getMessageForPersonListShownSummary(allPersons), allPersons);
    }

}
