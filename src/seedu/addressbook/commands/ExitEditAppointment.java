package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.person.ReadOnlyPerson;

public class ExitEditAppointment extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Exits edit-appointment mode.\n\t"
            + "Example: " + COMMAND_WORD ;

    //public static final String MESSAGE_EDIT_APPOINTMENT_MODE = "\n" + "Exiting edit-appointment mode!";

    public static final String MESSAGE_FINISH_EDIT_APPOINTMENT = "Finish editing appointments for %1$s.";
            //+ "\n" + MESSAGE_EDIT_APPOINTMENT_MODE;


    public ExitEditAppointment(int targetVisibleIndex) {
        super(targetVisibleIndex);
    }

    @Override
    public CommandResult execute() {
        setEditingAppointmentState(false);
        //setEditingPersonIndex(0);
        final ReadOnlyPerson target = getTargetPerson();
        return new CommandResult( String.format(MESSAGE_FINISH_EDIT_APPOINTMENT, target.getName()) );
    }

}

