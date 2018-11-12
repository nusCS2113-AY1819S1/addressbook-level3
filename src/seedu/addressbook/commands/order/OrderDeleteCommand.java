package seedu.addressbook.commands.order;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.order.ReadOnlyOrder;
import seedu.addressbook.data.order.UniqueOrderList.OrderNotFoundException;

/**
 * Deletes an order identified using it's last displayed index from the order list.
 */
public class OrderDeleteCommand extends Command {

    public static final String COMMAND_WORD = "deleteorder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Deletes the order identified by the index number used in the last order listing.\n\t"
            + "Parameters: INDEX\n\t"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ORDER_SUCCESS = "Deleted Order: %1$s";


    public OrderDeleteCommand(int targetVisibleIndex) {
        super(targetVisibleIndex);
    }


    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyOrder target = getTargetOrder();
            rms.removeOrder(target);
            String message = String.format(MESSAGE_DELETE_ORDER_SUCCESS, target);
            return new CommandResult(message);

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        } catch (OrderNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_ORDER_NOT_IN_ORDER_LIST);
        }
    }

}
