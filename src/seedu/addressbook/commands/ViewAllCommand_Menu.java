package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.person.ReadOnlyMenus;
import seedu.addressbook.data.person.ReadOnlyPerson;


/**
 * Shows all details of the person identified using the last displayed index.
 * Private contact details are shown.
 */
public class ViewAllCommand_Menu extends Command_Menu {

    public static final String COMMAND_WORD = "viewall menu";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Shows all details of the person "
            + "identified by the index number in the last shown person listing.\n\t"
            + "Parameters: INDEX\n\t"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_VIEW_MENU_ITEM_DETAILS = "Viewing menu: %1$s";


    public ViewAllCommand_Menu(int targetVisibleIndex) {
        super(targetVisibleIndex);
    }


    @Override
    public CommandResult_Menu execute() {
        try {
            final ReadOnlyMenus target = getTargetMenu();
            if (!menuBook.containsMenu(target)) {
                return new CommandResult_Menu(Messages.MESSAGE_MENU_ITEM_NOT_IN_ADDRESSBOOK);
            }
            return new CommandResult_Menu(String.format(MESSAGE_VIEW_MENU_ITEM_DETAILS, target.getAsTextShowAll()));
        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult_Menu(Messages.MESSAGE_INVALID_MENU_ITEM_DISPLAYED_INDEX);
        }
    }
}
