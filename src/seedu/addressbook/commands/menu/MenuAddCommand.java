package seedu.addressbook.commands.menu;

import java.util.HashSet;
import java.util.Set;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.menu.Menu;
import seedu.addressbook.data.menu.MenuName;
import seedu.addressbook.data.menu.Price;
import seedu.addressbook.data.menu.ReadOnlyMenus;
import seedu.addressbook.data.menu.Type;
import seedu.addressbook.data.menu.UniqueMenuList;
import seedu.addressbook.data.tag.Tag;

/**
 * Adds a menu item to the Rms.
 */
public class MenuAddCommand extends Command {

    public static final String COMMAND_WORD = "addmenu";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Adds a food item to the Rms.\n\n"
            + "Parameters: NAME p/PRICE type/TYPE [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " Cheese Burger p/$5.00 type/main t/newAddition t/hotSeller\n"
            + "Additional Notes:"
            + "\ni. PRICE must start with a $ sign and must be integer or float in value of 2 decimal places"
            + "\nii. TYPE should only be one of the following categories:"
            + "\n" + "   -  main"
            + "\n" + "   -  sides"
            + "\n" + "   -  beverage"
            + "\n" + "   -  dessert"
            + "\n" + "   -  others"
            + "\n" + "   -  set meal";

    public static final String MESSAGE_SUCCESS = "New food item added: %1$s";
    public static final String MESSAGE_DUPLICATE_MENU_ITEM = "This food item already exists in the Rms";

    private final Menu toAddFoodItem;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public MenuAddCommand(String name,
                          String price, /*boolean isPricePrivate,*/
                          String type,
                          Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAddFoodItem = new Menu(
                new MenuName(name),
                new Price(price),
                new Type(type),
                tagSet
        );
    }

    public MenuAddCommand(Menu toAddFoodItem) {
        this.toAddFoodItem = toAddFoodItem;
    }

    public ReadOnlyMenus getMenu() {
        return toAddFoodItem;
    }

    @Override
    public CommandResult execute() {
        try {
            rms.addMenu(toAddFoodItem);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAddFoodItem));
        } catch (UniqueMenuList.DuplicateMenuException dpe) {
            return new CommandResult(MESSAGE_DUPLICATE_MENU_ITEM);
        }
    }

}

