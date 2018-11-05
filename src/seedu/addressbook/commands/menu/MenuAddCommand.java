package seedu.addressbook.commands.menu;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.menu.*;
import seedu.addressbook.data.tag.Tag;

import java.util.HashSet;
import java.util.Set;

/**
 * Adds a menu item to the address book.
 */
public class MenuAddCommand extends Command {

    public static final String COMMAND_WORD = "addmenu";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Adds a food item to the Rms. "
            + "Parameters: NAME p/PRICE type/TYPE [t/TAG]...\n\t"
            + "Example: " + COMMAND_WORD
            + " Cheese Burger p/5 type/burger t/newAddition t/hotSeller";

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
//test command