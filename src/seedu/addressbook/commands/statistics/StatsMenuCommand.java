package seedu.addressbook.commands.statistics;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.menu.ReadOnlyMenus;
import seedu.addressbook.data.order.ReadOnlyOrder;

import java.util.List;

/**
 * Lists all food items in the address book to the user.
 */
public class StatsMenuCommand extends Command {

    public static final String COMMAND_WORD = "statsmenu";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays statistics information for menu items.\n\t"
            + "Example: " + COMMAND_WORD;


    @Override
    public CommandResult execute() {
        return new StatsCommandResult(getMenuStats());
    }

    private String getMenuStats() {
        List<ReadOnlyOrder> allOrders = rms.getAllOrders().immutableListView();
        for (ReadOnlyOrder order : allOrders) {

        }
        return "Work In Progress\n";
    }
}
