package seedu.addressbook.commands.menu;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.menu.ReadOnlyMenus;
import seedu.addressbook.data.menu.Type;

//@@author SalsabilTasnia
/**
 * Lists all food items of a certain type (or category) in the menu list to the user.
 */
public class MenuListByTypeCommand extends Command {

    public static final String COMMAND_WORD = "listmenutype";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays all food item of a specific category in the Rms system as a list with index numbers.\n\t"
            + "Example: " + COMMAND_WORD + " main";

    public static final String MESSAGE_ERROR = "Invalid menu type searched! " + "\n"
             + "Only the following types are available: main, sides, beverage, dessert, others, set meal." + "\n"
             + "Only one type search allowed at a time!";

    private final String itemword;
    private final List<ReadOnlyMenus> matchedFoodItems = new ArrayList<>();
    private final Set<String> typeSet = new HashSet<>();

    public MenuListByTypeCommand(String itemword) {
        this.itemword = itemword;
    }
    public String getItemword() {
        return itemword;
    }


    /**
     * Retrieve all menu items in the menu that are of the same type as itemType
     *
     * @param itemType type of menu items the user wishes to view
     * @return a list of menu items of type, 'itemType'
     */
    private List<ReadOnlyMenus> getFoodItems(String itemType) {
        for (ReadOnlyMenus menuItem : rms.getAllMenus()) {
            final String wordsInItemName = menuItem.getType().getValue();
            typeSet.add(wordsInItemName);
            if (wordsInItemName.equals(itemType)) {
                matchedFoodItems.add(menuItem);
            }
        }
        return matchedFoodItems;
    }

    @Override
    public CommandResult execute() {
        final List<ReadOnlyMenus> itemsFound = getFoodItems(itemword);
        if (Type.isValidTypeName(itemword) == false) {
            return new MenuCommandResult(MESSAGE_ERROR);
        }

        return new MenuCommandResult(getMessageForMenuListShownSummary(itemsFound), itemsFound);
    }
}
