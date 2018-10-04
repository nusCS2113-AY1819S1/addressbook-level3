package seedu.addressbook.commands;

import seedu.addressbook.data.CommandHistory;

/**
 * Lists previously executed commands to the user.
 */
public class HistoryCommand extends Command {

    public static final String COMMAND_WORD = "history";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays all previously executed commands other than ,'history' and 'help', starting from the oldest.\n\t"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_NO_HISTORY = "No commands had been executed";


    @Override
    public CommandResult execute() {
        try {
            return new CommandResult(commandHistory.getHistory());
        } catch (CommandHistory.EmptyHistoryException ehe) {
            return new CommandResult(MESSAGE_NO_HISTORY);
        }
    }
}
