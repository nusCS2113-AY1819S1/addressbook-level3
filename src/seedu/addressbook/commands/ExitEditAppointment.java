package seedu.addressbook.commands;

import seedu.addressbook.data.person.ReadOnlyPerson;

/**
 * Exit edit-appointment mode and return to processing standard commands.
 */
public class ExitEditAppointment extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Exits edit-appointment mode.\n\t"
            + "Example: " + COMMAND_WORD ;

    public static final String MESSAGE_FINISH_EDIT_APPOINTMENT = "Finish editing appointments for %1$s.";


    @Override
    public CommandResult execute() {
        setEditingAppointmentState(false);
        this.setTargetIndex(checkEditingPersonIndex());
        final ReadOnlyPerson target = getTargetPerson();
        return new CommandResult( String.format(MESSAGE_FINISH_EDIT_APPOINTMENT, target.getName()) );
    }

}
