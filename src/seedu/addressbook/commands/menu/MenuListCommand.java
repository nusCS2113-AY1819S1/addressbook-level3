package seedu.addressbook.commands.menu;

import java.util.List;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.menu.ReadOnlyMenus;

/**
 * Lists all food items in the Rms to the user.
 */
public class MenuListCommand extends Command {

    public static final String COMMAND_WORD = "listmenu";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays all menu items in the Rms system as a list with index numbers.\n\t"
            + "Example: " + COMMAND_WORD;



    @Override
    public CommandResult execute() {
        List<ReadOnlyMenus> allMenus = rms.getAllMenus().immutableListView();
        return new MenuCommandResult(getMessageForMenuListShownSummary(allMenus), allMenus);
    }
}
