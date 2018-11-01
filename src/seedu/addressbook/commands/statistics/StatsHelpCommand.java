package seedu.addressbook.commands.statistics;

import seedu.addressbook.commands.Command;

/**
 * Shows help instructions.
 */
public class StatsHelpCommand extends Command {

    public static final String COMMAND_WORD = "statistics";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Shows statistics usage instructions.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_ALL_USAGES = StatsEmployeeCommand.MESSAGE_USAGE
            + "\n" + StatsMenuCommand.MESSAGE_USAGE
            + "\n" + StatsMemberCommand.MESSAGE_USAGE
            + "\n" + StatsOrderCommand.MESSAGE_USAGE;

    @Override
    public StatsCommandResult execute() {
        return new StatsCommandResult(MESSAGE_ALL_USAGES);
    }
}
