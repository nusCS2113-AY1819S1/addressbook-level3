package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.person.ReadOnlyPerson;

public class AddAppointment extends Command{

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "\n"
            + "Add in an appointment date for .\n\t"
            + "Parameters: DD-MM-YYYY\n\t"
            + "Example: " + COMMAND_WORD + " 01-01-2019";

    public static final String MESSAGE_EDIT_PERSON_APPOINTMENT = "%1$s has a new appointment date on %1$s";


    public AddAppointment(int targetVisibleIndex) {
        super(targetVisibleIndex);
    }

    @Override
    public CommandResult execute() {
        return new CommandResult("command under construct, tbc ");
        /*try {
            final ReadOnlyPerson target = getTargetPerson();
            if (!addressBook.containsPerson(target)) {
                return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
            }
            commandHistory.addHistory(COMMAND_WORD + " " + getTargetIndex());
            //return new CommandResult(String.format(MESSAGE_EDIT_PERSON_APPOINTMENT, target.getAsTextHidePrivate()));

            return new CommandResult(String.format("Finish edit appointments for ", target.getName()));
            //String.format (MESSAGE_EDIT_PERSON_APPOINTMENT, target.getName()));

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } //catch (UniquePersonList.PersonNotFoundException pnfe) {
        // return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
        //}
        */

    }

}

