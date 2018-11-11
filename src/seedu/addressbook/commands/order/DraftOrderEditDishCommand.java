package seedu.addressbook.commands.order;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.menu.ReadOnlyMenus;

/**
 * Edit the quantity of a dish item of the draft order.
 * The dish item is retrieved with the index of last displayed menu.
 */
public class DraftOrderEditDishCommand extends Command {

    public static final String COMMAND_WORD = "draftdish";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Add a dish to the draft order. "
            + "The dish is identified using the index from the last shown menu list. \n\t"
            + "Parameters: INDEX q/QUANTITY\n\t"
            + "Example: " + COMMAND_WORD + " 3 q/4";

    public static final String MESSAGE_SUCCESS = "The dish is edited in the draft order.";

    public static final String MESSAGE_INVALID_FORMAT = "The entered command does not follow the format\n"
            + "INDEX must be a non-negative integer\n"
            + "QUANTITY must be a non-negative integer of 1-3 digits\n"
            + MESSAGE_USAGE;

    private int quantity;

    public DraftOrderEditDishCommand(int targetVisibleIndex, int quantity) {
        super(targetVisibleIndex);
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }


    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyMenus target = getTargetMenu();
            if (!rms.containsMenus(target)) {
                return new CommandResult(Messages.MESSAGE_MENU_ITEM_NOT_IN_ADDRESSBOOK);
            }
            rms.editDraftOrderDishItem(target, quantity);
            String message = MESSAGE_SUCCESS + "\n" + getDraftOrderAsString();
            return new CommandResult(message);
        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_MENU_ITEM_DISPLAYED_INDEX);
        }
    }

}
