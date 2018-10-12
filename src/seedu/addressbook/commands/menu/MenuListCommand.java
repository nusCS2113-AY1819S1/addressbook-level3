package seedu.addressbook.commands.menu;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.menu.ReadOnlyMenus;

import java.util.List;

/**
 * Lists all food items in the address book to the user.
 */
public class MenuListCommand extends Command {

    public static final String COMMAND_WORD = "listmenu";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" 
            + "Displays all menu items in the RMS system as a list with index numbers.\n\t"
            + "Example: " + COMMAND_WORD;


    @Override
    public CommandResult execute() {
        List<ReadOnlyMenus> allMenus = rms.getAllMenus().immutableListView();
        return new MenuCommandResult(getMessageForMenuListShownSummary(allMenus), allMenus);
    }
}
