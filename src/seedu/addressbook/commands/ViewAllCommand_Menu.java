package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.person.ReadOnlyMenus;
import seedu.addressbook.data.person.ReadOnlyPerson;


/**
 * Shows all details of the person identified using the last displayed index.
 * Private contact details are shown.
 */
public class ViewAllCommand_Menu extends Command {

    public static final String COMMAND_WORD = "viewallmenu";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Shows all details of the food items "
            + "identified by the index number in the last shown menu listing.\n\t"
            + "Parameters: INDEX\n\t"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_VIEW_MENU_ITEM_DETAILS = "Viewing menu: %1$s";


    public ViewAllCommand_Menu(int targetVisibleIndex) {
        super(targetVisibleIndex);
    }


    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyMenus target = getTargetMenu();
            if (!addressBook.containsMenus(target)) {
                return new CommandResult(Messages.MESSAGE_MENU_ITEM_NOT_IN_ADDRESSBOOK);
            }
            return new CommandResult(String.format(MESSAGE_VIEW_MENU_ITEM_DETAILS, target.getAsTextShowAll()));
        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_MENU_ITEM_DISPLAYED_INDEX);
        }
    }
}
