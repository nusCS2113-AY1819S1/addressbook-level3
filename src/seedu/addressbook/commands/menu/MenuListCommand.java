package seedu.addressbook.commands.menu;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private static boolean executeMenu;

    private final Set<String> typeSet = new HashSet<>();



    @Override
    public CommandResult execute() {
        executeMenu = true;
        List<ReadOnlyMenus> allMenus = rms.getAllMenus().immutableListView();
        //return new MenuCommandResult(MAIN_MENU_DISPLAY);
        return new MenuCommandResult(getMessageForMenuListShownSummary(allMenus), allMenus);
    }
}
