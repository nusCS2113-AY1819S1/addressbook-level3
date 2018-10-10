package seedu.addressbook.commands;

import seedu.addressbook.data.order.ReadOnlyOrder;

import java.util.List;

/**
 * Represents the result of an order command execution.
 */
public class OrderCommandResult extends CommandResult {

    public OrderCommandResult(String feedbackToUser, List<? extends ReadOnlyOrder> relevantOrders) {
        super(feedbackToUser, null, null, relevantOrders, null);
    }
}
