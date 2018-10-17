package seedu.addressbook.commands.statistics;

import java.util.List;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.order.ReadOnlyOrder;

/**
 * Lists all food items in the address book to the user.
 */
public class StatsOrderCommand extends Command {

    public static final String COMMAND_WORD = "statsorder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays statistics information for orders.\n\t"
            + "Example: " + COMMAND_WORD;


    @Override
    public CommandResult execute() {
        return new StatsCommandResult(getOrderStats());
    }

    private String getOrderStats() {

        return "Work In Progress\n";
    }
}
