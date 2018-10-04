package seedu.addressbook.commands;

/**
 * Terminates the program.
 */
public class ExitCommand_Menu extends Command_Menu {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Exits the program.\n\t"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_EXIT_ACKNOWEDGEMENT = "Exiting RMS as requested ...";

    @Override
    public CommandResult_Menu execute() {
        return new CommandResult_Menu(MESSAGE_EXIT_ACKNOWEDGEMENT);
    }

}
