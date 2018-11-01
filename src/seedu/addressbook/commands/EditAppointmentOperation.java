package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.person.ReadOnlyPerson;


public class EditAppointmentOperation extends Command {

    public static final String COMMAND_WORD = "yea";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "\n"
            + "Hmmm.\n\t"
            + "Parameters: INDEX\n\t"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_FINISH_EDIT_APPOINTMENT = "Hmmm Finish editing appointments for %1$s.";

    //private int value;

    public EditAppointmentOperation(int targetVisibleIndex) {
        super(targetVisibleIndex);
        //value = targetVisibleIndex;
    }

    @Override
    public CommandResult execute() {
        final ReadOnlyPerson target = getTargetPerson();
        return new CommandResult( String.format(MESSAGE_FINISH_EDIT_APPOINTMENT, target.getName()) );
        //String.format("Finish edit appointments for ", target.getName()));
    }

}
