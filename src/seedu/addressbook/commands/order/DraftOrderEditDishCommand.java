package seedu.addressbook.commands.order;

import java.util.HashMap;
import java.util.Map;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.menu.ReadOnlyMenus;

//@@author px1099
/**
 * Edit the quantity of a dish item of the draft order.
 * The dish item is retrieved with the index of last displayed menu.
 */
public class DraftOrderEditDishCommand extends Command {

    public static final String COMMAND_WORD = "draftdish";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Add dishes to the draft order. "
            + "The dishes are identified using the indexes from the last shown menu list. \n\t"
            + "Parameters: INDEX q/QUANTITY [INDEX q/QUANTITY]...\n\t"
            + "Example: " + COMMAND_WORD + " 1 q/4 3 q/2";

    public static final String MESSAGE_SUCCESS = "The dishes are edited in the draft order.\n%1$s";

    public static final String MESSAGE_INVALID_FORMAT = "The entered command does not follow the format\n"
            + "INDEX must be a non-negative integer\n"
            + "QUANTITY must be a non-negative integer of 1-3 digits\n"
            + MESSAGE_USAGE;

    public static final String MESSAGE_DUPLICATE_INDEX = "There are duplicate index in the input command";

    private Map<Integer, Integer> indexQuantityPairs = new HashMap<>();

    public DraftOrderEditDishCommand(Map<Integer, Integer> indexQuantityPairs) {
        super();
        this.indexQuantityPairs.putAll(indexQuantityPairs);
    }


    @Override
    public CommandResult execute() {
        try {
            for (Map.Entry<Integer, Integer> entry: indexQuantityPairs.entrySet()) {
                int index = entry.getKey();
                setTargetIndex(index);
                ReadOnlyMenus target = getTargetMenu();
                if (!rms.containsMenus(target)) {
                    return new CommandResult(Messages.MESSAGE_MENU_ITEM_NOT_IN_ADDRESSBOOK);
                }
            }
            for (Map.Entry<Integer, Integer> entry: indexQuantityPairs.entrySet()) {
                int index = entry.getKey();
                int quantity = entry.getValue();
                setTargetIndex(index);
                ReadOnlyMenus target = getTargetMenu();
                rms.editDraftOrderDishItem(target, quantity);
            }
            String message = String.format(MESSAGE_SUCCESS, getDraftOrderAsString());
            return new CommandResult(message);
        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_MENU_ITEM_DISPLAYED_INDEX);
        }
    }

    public Map<Integer, Integer> getIndexQuantityPairs() {
        return indexQuantityPairs;
    }
}
