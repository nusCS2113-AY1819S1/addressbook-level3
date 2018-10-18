package seedu.addressbook.commands.order;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.order.ReadOnlyOrder;


/**
 * Display the current draft and the list of order draft commands used for adding a new order
 */
public class OrderAddCommand extends Command {

    public static final String COMMAND_WORD = "addorder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Shows the details of the current draft order and the new order drafting instructions.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_ALL_ORDER_DRAFT_COMMANDS = "List of commands used for drafting a new order:";

    public static final String MESSAGE_ALL_ORDER_DRAFT_COMMANDS_USAGES = OrderAddCommand.MESSAGE_USAGE
            + "\n" + DraftOrderEditCustomerCommand.MESSAGE_USAGE
            + "\n" + DraftOrderEditDishCommand.MESSAGE_USAGE
            + "\n" + DraftOrderClearCommand.MESSAGE_USAGE;

    @Override
    public CommandResult execute() {
        final ReadOnlyOrder draftOrder = rms.getDraftOrder();
        String message = getDraftOrderAsString()
                        + "\n\n" + MESSAGE_ALL_ORDER_DRAFT_COMMANDS
                        + "\n" + MESSAGE_ALL_ORDER_DRAFT_COMMANDS_USAGES;
        return new CommandResult(message);
    }

}
