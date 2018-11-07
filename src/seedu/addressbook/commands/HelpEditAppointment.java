package seedu.addressbook.commands;

import seedu.addressbook.data.person.ReadOnlyPerson;

/**
 * Shows help instructions for editing appointment of a selected person.
 */
public class HelpEditAppointment extends Command{

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" +"Shows edit-appointment mode usage instructions.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_EDIT_APPOINTMENT_MODE = "In edit-appointment mode for %1$s!" + "\n";

    public static final String MESSAGE_ALL_USAGES = MESSAGE_EDIT_APPOINTMENT_MODE
            //+ "\n" + ListAppointment.MESSAGE_USAGE + "\n"
            //+ "\n" + "Add and Delete Appointment Command still under construction. (Coming in V1.4) " + "\n"
            + "\n" + AddAppointment.MESSAGE_USAGE + "\n"
            //+ "\n" + FindCommand.MESSAGE_USAGE + "\n"
            + "\n" + HelpEditAppointment.MESSAGE_USAGE + "\n"
            + "\n" + ExitEditAppointment.MESSAGE_USAGE;

    @Override
    public CommandResult execute() {
        this.setTargetIndex(checkEditingPersonIndex());
        final ReadOnlyPerson target = getTargetPerson();
        return new CommandResult(String.format(MESSAGE_ALL_USAGES, target.getName().toString()));
    }

}
