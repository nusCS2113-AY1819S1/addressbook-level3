package seedu.addressbook.commands.menu;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.menu.ReadOnlyMenus;
import seedu.addressbook.data.menu.UniqueMenuList.MenuNotFoundException;


/**
 * Deletes a menu item identified using it's last displayed index from the Rms.
 */
public class MenuDeleteCommand extends Command {

    public static final String COMMAND_WORD = "delmenu";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Deletes the menu item identified by the index number used in the last menu listing.\n\t"
            + "Parameters: INDEX\n\t"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_MENU_ITEM_SUCCESS = "Deleted Menu Item: %1$s";


    public MenuDeleteCommand(int targetVisibleIndex) {
        super(targetVisibleIndex);
    }


    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyMenus menutarget = getTargetMenu();
            rms.removeMenuItem(menutarget);
            return new CommandResult(String.format(MESSAGE_DELETE_MENU_ITEM_SUCCESS, menutarget));

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_MENU_ITEM_DISPLAYED_INDEX);
        } catch (MenuNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_MENU_ITEM_NOT_IN_ADDRESSBOOK);
        }
    }

}
