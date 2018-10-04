package seedu.addressbook.commands;

import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.*;
import seedu.addressbook.data.tag.Tag;

import java.util.HashSet;
import java.util.Set;

/**
 * Adds a person to the address book.
 */
public class AddMenu extends Command_Menu {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Adds a menu to the existing menu list. "
            + "Contact details can be marked private by prepending 'p' to the prefix.\n\t"
            + "Parameters: NAME [p]p/PRICE [t/TAG]...\n\t"
            + "Example: " + COMMAND_WORD
            + "Cheese Burger p/$4.40 t/New arrival";

    public static final String MESSAGE_SUCCESS = "New food item added: %1$s";
    public static final String MESSAGE_DUPLICATE_MENUITEM = "This menu item already exists in RMS";

    private final Menu toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddMenu(String name,
                   String price, boolean isPricePrivate,
                   Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Menu(
                new Name(name),
                new Price(price, isPricePrivate),
                tagSet
        );
    }

    public AddMenu(Menu toAdd) {
        this.toAdd = toAdd;
    }

    public ReadOnlyMenus getMenu() {
        return toAdd;
    }

    @Override
    public CommandResult_Menu execute() {
        try {
            menuBook.addMenuItem(toAdd);
            return new CommandResult_Menu(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueMenuList.DuplicateMenuException dpe) {
            return new CommandResult_Menu(MESSAGE_DUPLICATE_MENUITEM);
        }
    }

}
