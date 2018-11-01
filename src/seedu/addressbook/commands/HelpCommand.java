package seedu.addressbook.commands;


/**
 * Shows help instructions.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" +"Shows program usage instructions.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_ALL_USAGES = AddCommand.MESSAGE_USAGE + "\n"
            + "\n" + DeleteCommand.MESSAGE_USAGE + "\n"
            + "\n" + ClearCommand.MESSAGE_USAGE + "\n"
            + "\n" + FindCommand.MESSAGE_USAGE + "\n"
            + "\n" + ListCommand.MESSAGE_USAGE + "\n"
            + "\n" + ViewCommand.MESSAGE_USAGE + "\n"
            + "\n" + ViewAllCommand.MESSAGE_USAGE + "\n"
            + "\n" + HelpCommand.MESSAGE_USAGE + "\n"
            + "\n" + ExitCommand.MESSAGE_USAGE + "\n"
            + "\n" + UndoCommand.MESSAGE_USAGE + "\n"
            + "\n" + RedoCommand.MESSAGE_USAGE + "\n"
            + "\n" + EditAppointmentCommand.MESSAGE_USAGE + "\n"
            + "\n" + ChatCommand.MESSAGE_USAGE;

    @Override
    public CommandResult execute() {
        return new CommandResult(MESSAGE_ALL_USAGES);
    }
}
