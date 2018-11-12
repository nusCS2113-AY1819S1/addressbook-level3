package seedu.addressbook.commands.order;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.commands.member.MemberListCommand;
import seedu.addressbook.commands.menu.MenuFindCommand;
import seedu.addressbook.commands.menu.MenuListByTypeCommand;
import seedu.addressbook.commands.menu.MenuListCommand;


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
            + "\n" + DraftOrderEditPointsCommand.MESSAGE_USAGE
            + "\n" + DraftOrderClearCommand.MESSAGE_USAGE
            + "\n" + DraftOrderConfirmCommand.MESSAGE_USAGE
            + "\n" + MemberListCommand.MESSAGE_USAGE
            + "\n" + MenuListCommand.MESSAGE_USAGE
            + "\n" + MenuFindCommand.MESSAGE_USAGE
            + "\n" + MenuListByTypeCommand.MESSAGE_USAGE;

    public static final String MESSAGE_ADD_ORDER_INSTRUCTION = "Adding new order instructions:"

            + "\n\t" + "Step 1: " + "Pick the food to order"
            + "\n\t\t" + "* " + "View menu list: "
            + MenuListCommand.COMMAND_WORD + "/"
            + MenuFindCommand.COMMAND_WORD + "/"
            + MenuListByTypeCommand.COMMAND_WORD
            + "\n\t\t" + "* " + "Pick a dish item from the list and the quantity of it: "
            + DraftOrderEditDishCommand.COMMAND_WORD
            + "\n\t\t" + "* " + "Repeat step 1 until the order are completed"

            + "\n\t" + "Step 2: " + "(Optional) Pick a member as the customer and redeem points for discount"
            + "\n\t\t" + "* " + "View the member list: "
            + MemberListCommand.COMMAND_WORD
            + "\n\t\t" + "* " + "Pick a member from the list: "
            + DraftOrderEditCustomerCommand.COMMAND_WORD
            + "\n\t\t" + "* " + "Assign member points to be used for discount: "
            + DraftOrderEditPointsCommand.COMMAND_WORD

            + "\n\t" + "Step 3: " + "Confirm and add the order to the order list: "
            + DraftOrderConfirmCommand.COMMAND_WORD;

    @Override
    public CommandResult execute() {
        String message = getDraftOrderAsString()
                + "\n\n" + MESSAGE_ADD_ORDER_INSTRUCTION
                + "\n\n" + MESSAGE_ALL_ORDER_DRAFT_COMMANDS
                + "\n\n" + MESSAGE_ALL_ORDER_DRAFT_COMMANDS_USAGES;
        return new CommandResult(message);
    }

}
