package seedu.addressbook.commands.order;

import java.util.List;

import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.order.ReadOnlyOrder;

/**
 * Represents the result of an order command execution.
 */
public class OrderCommandResult extends CommandResult {

    public OrderCommandResult(String feedbackToUser, List<? extends ReadOnlyOrder> relevantOrders) {
        super(feedbackToUser, null, relevantOrders, null, null, null);
    }
}
