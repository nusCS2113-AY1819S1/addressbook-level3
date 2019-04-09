package seedu.addressbook.commands;

import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.Schedule;

import java.util.Set;

/**
 * Lists all appointment of the selected person to the user.
 */
public class ListAppointment extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays all appointment of the person selected as a list with index numbers.\n\t"
            + "Example: " + COMMAND_WORD;


    @Override
    public CommandResult execute() {
        this.setTargetIndex(checkEditingPersonIndex());
        final ReadOnlyPerson target = getTargetPerson();
        Set<Schedule> appointments = target.getSchedules();
        return new CommandResult(getMessageForAppointmentMadeByPerson(appointments, target.getName()), appointments);
    }

}
