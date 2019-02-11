package seedu.addressbook.commands.order;

import java.util.List;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.order.ReadOnlyOrder;

//@@author px1099
/**
 * Lists all orders in the order list to the user.
 */
public class OrderListCommand extends Command {

    public static final String COMMAND_WORD = "listorder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays all orders in the order list as a list with index numbers.\n\t"
            + "Example: " + COMMAND_WORD;


    @Override
    public CommandResult execute() {
        List<ReadOnlyOrder> allOrders = rms.getAllOrders().immutableListView();
        return new OrderCommandResult(getMessageForOrderListShownSummary(allOrders), allOrders);
    }
}
