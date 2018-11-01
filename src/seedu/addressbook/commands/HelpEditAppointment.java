package seedu.addressbook.commands;

/**
 * Shows help instructions for editing appointment of a selected person.
 */
public class HelpEditAppointment extends Command{

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" +"Shows editing appointment instructions.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_ALL_USAGES = ListAppoinment.MESSAGE_USAGE + "\n"
            + "\n" + "Add and Delete Appointment Command still under construction. (Coming in V1.4) " + "\n"
            //+ "\n" + ClearCommand.MESSAGE_USAGE + "\n"
            //+ "\n" + FindCommand.MESSAGE_USAGE + "\n"
            + "\n" + ExitEditAppointment.MESSAGE_USAGE;

    @Override
    public CommandResult execute() {
        return new CommandResult(MESSAGE_ALL_USAGES);
    }

}
