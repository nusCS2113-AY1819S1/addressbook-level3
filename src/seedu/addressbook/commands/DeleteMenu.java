package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.person.ReadOnlyMenus;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.UniquePersonList.PersonNotFoundException;


/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteMenu extends Command_Menu {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Deletes the menu item identified by the index number used in the last menu listing.\n\t"
            + "Parameters: INDEX\n\t"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_MENU_SUCCESS = "Deleted Menu Item: %1$s";


    public DeleteMenu(int targetVisibleIndex) {
        super(targetVisibleIndex);
    }


    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyMenus target = getTargetMenu();
            menuBook.removeMenuItem(target);
            return new CommandResult(String.format(MESSAGE_DELETE_MENU_SUCCESS, target));

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_MENU_ITEM_DISPLAYED_INDEX);
        } catch (PersonNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_MENU_ITEM_NOT_IN_ADDRESSBOOK);
        }
    }

}
