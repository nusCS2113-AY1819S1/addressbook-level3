package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.person.ReadOnlyPerson;

/**
 * Select the person to edit appointment using the last displayed index
 * and enters edit-appointment mode.
 */
public class EditAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "edit-appointment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Selects the person to edit appointment(s) and enters edit-appointment mode.\n\t"
            + "Parameters: INDEX\n\t"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_EDIT_APPOINTMENT_MODE = "Enters edit-appointment mode - "
            + "accessed to %1$s's appointment(s): ";


    public EditAppointmentCommand(int targetVisibleIndex) {
            super(targetVisibleIndex);
        }

    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyPerson target = getTargetPerson();
            if (!addressBook.containsPerson(target)) {
                return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
            }

            saveHistory(COMMAND_WORD + " " + getTargetIndex());
            setEditingAppointmentState(true);
            setEditingPersonIndex(getTargetIndex());
            return new CommandResult( String.format(MESSAGE_EDIT_APPOINTMENT_MODE, target.getName()) );

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }

}
