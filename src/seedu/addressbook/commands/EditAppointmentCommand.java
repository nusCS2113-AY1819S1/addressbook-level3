package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.UniquePersonList;


/**
 * Select the person to edit their schedule using the last displayed index.
 */
public class EditAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "edit-appointment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Select the person to edit appointment.\n\t"
            + "Parameters: INDEX\n\t"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_EDIT_PERSON_APPOINTMENT = "Accessed to %1$s's appointment: ";


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
            commandHistory.addHistory(COMMAND_WORD + " " + getTargetIndex());
            //return new CommandResult(String.format(MESSAGE_EDIT_PERSON_APPOINTMENT, target.getAsTextHidePrivate()));
            //command.setdata(addressBook, lastShownList);
            //Command command = new EditAppointmentOperation(getTargetIndex());
            //CommandResult result = command.execute();
            //storage.save(addressBook);
            setEditingAppointmentState(true);
            setEditingPersonIndex( getTargetIndex() );
            return new CommandResult( String.format (MESSAGE_EDIT_PERSON_APPOINTMENT, target.getName()) );

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } //catch (UniquePersonList.PersonNotFoundException pnfe) {
           // return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
        //}
    }

}

