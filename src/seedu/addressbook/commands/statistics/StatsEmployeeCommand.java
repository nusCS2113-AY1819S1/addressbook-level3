package seedu.addressbook.commands.statistics;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;

/**
 * Lists all food items in the address book to the user.
 */
public class StatsEmployeeCommand extends Command {

    public static final String COMMAND_WORD = "statsemp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays statistics information for employees.\n\t"
            + "Example: " + COMMAND_WORD;


    @Override
    public CommandResult execute() {
        return new StatsCommandResult(getEmployeeStats());
    }

    private String getEmployeeStats() {

        return "Work In Progress\n";
    }
}
