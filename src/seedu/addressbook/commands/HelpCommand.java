package seedu.addressbook.commands;

import seedu.addressbook.password.Password;

/**
 * Shows help instructions.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Shows program usage instructions.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_ALL_USAGES = AddCommand.MESSAGE_USAGE
            + "\n" + DeleteCommand.MESSAGE_USAGE
            + "\n" + ClearCommand.MESSAGE_USAGE
            + "\n" + EditCommand.MESSAGE_USAGE
            + "\n" + CheckCommand.MESSAGE_USAGE
            + "\n" + CheckPoStatusCommand.MESSAGE_USAGE
            + "\n" + UpdateStatusCommand.MESSAGE_USAGE
            + "\n" + FindCommand.MESSAGE_USAGE
            + "\n" + ShowUnreadCommand.MESSAGE_USAGE
            + "\n" + InboxCommand.MESSAGE_USAGE
            + "\n" + RequestHelpCommand.MESSAGE_USAGE
            + "\n" + DispatchCommand.MESSAGE_USAGE
            + "\n" + ReadCommand.MESSAGE_USAGE
            + "\n" + ListCommand.MESSAGE_USAGE
            + "\n" + ViewAllCommand.MESSAGE_USAGE
            + "\n" + Password.UPDATE_PASSWORD_MESSAGE_USAGE
            + "\n" + HelpCommand.MESSAGE_USAGE
            + "\n" + LogoutCommand.MESSAGE_USAGE
            + "\n" + ShutdownCommand.MESSAGE_USAGE;

    public static final String MESSAGE_PO_USAGES = FindCommand.MESSAGE_USAGE
            + "\n" + ListCommand.MESSAGE_USAGE
            + "\n" + ViewAllCommand.MESSAGE_USAGE
            + "\n" + HelpCommand.MESSAGE_USAGE
            + "\n" + RequestHelpCommand.MESSAGE_USAGE
            + "\n" + ShowUnreadCommand.MESSAGE_USAGE
            + "\n" + InboxCommand.MESSAGE_USAGE
            + "\n" + ReadCommand.MESSAGE_USAGE
            + "\n" + LogoutCommand.MESSAGE_USAGE
            + "\n" + ShutdownCommand.MESSAGE_USAGE;


    //@@author iamputradanish
    @Override
    public CommandResult execute() {
        boolean isHqpFlag = Password.isHqpUser();

        if (isHqpFlag) {
            return new CommandResult(MESSAGE_ALL_USAGES);
        } else {
            return new CommandResult(MESSAGE_PO_USAGES);
        }
    }
}
