package seedu.addressbook.commands.order;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.order.Order;
import seedu.addressbook.data.order.ReadOnlyOrder;
import seedu.addressbook.data.order.UniqueOrderList;

/**
 * Confirm the order and put it into the order list. Clear the draft order afterward.
 */
public class DraftOrderConfirmCommand extends Command {

    public static final String COMMAND_WORD = "confirmdraft";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Confirm the order and put it into the order list. Clear the draft order afterward.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "The order has been added:\n%1$s";
    public static final String MESSAGE_DRAFT_INCOMPLETE = "The draft needs to be completed before confirming.";
    public static final String MESSAGE_DUPLICATE_ORDER = "This order already exists in the order list";

    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyOrder draftOrder = rms.getDraftOrder();
            String message;
            if (draftOrder.hasDishItems()) {
                final Order toAdd = new Order(
                        draftOrder.getCustomer(),
                        draftOrder.getDishItems(),
                        draftOrder.getPoints());
                rms.addOrder(toAdd);
                if (draftOrder.hasCustomerField()) {
                    rms.updatePointsOfCustomer(toAdd.getCustomer(), toAdd.getPrice(), toAdd.getPoints());
                }
                rms.clearDraftOrder();
                return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getAsTextAfterAdd()));
            } else {
                message = MESSAGE_DRAFT_INCOMPLETE + "\n" + getDraftOrderAsString();
                return new CommandResult(message);
            }
        } catch (UniqueOrderList.DuplicateOrderException doe) {
            return new CommandResult(MESSAGE_DUPLICATE_ORDER);
        }
    }
}
