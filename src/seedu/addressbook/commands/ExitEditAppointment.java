package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.person.ReadOnlyPerson;

public class ExitEditAppointment extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Conclude editing the person's appointments.\n\t"
            + "Example: " + COMMAND_WORD ;

    public static final String MESSAGE_FINISH_EDIT_APPOINTMENT = "Finish editing appointments for %1$s.";


    public ExitEditAppointment(int targetVisibleIndex) {
        super(targetVisibleIndex);
    }

    @Override
    public CommandResult execute() {
        Command.setEditingAppointmentState(false);
        final ReadOnlyPerson target = getTargetPerson();
        return new CommandResult( String.format(MESSAGE_FINISH_EDIT_APPOINTMENT, target.getName()) );
    }

}

