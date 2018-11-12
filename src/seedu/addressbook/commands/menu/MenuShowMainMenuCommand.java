package seedu.addressbook.commands.menu;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;

//@@author SalsabilTasnia
/**
 * Lists all food items in the menu list to the user.
 */
public class MenuShowMainMenuCommand extends Command {

    public static final String COMMAND_WORD = "showmainmenu";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays all the categories of menu items in the Rms system.\n\t"
            + "Example: " + COMMAND_WORD;
    public static final String MAIN_MENU_DISPLAY = "RMS Main Menu" + "\n"
            + "==================================================================================="
            + "\n\n" + "Item Category:"
            + "\n\n" + " -Main : key in 'listmenutype main' to view all Main items"
            + "\n" + " -Sides : key in 'listmenutype sides' to view all Sides"
            + "\n" + " -Beverages : key in 'listmenutype beverage' to view all Beverage"
            + "\n" + " -Dessert : key in 'listmenutype dessert' to view all Dessert"
            + "\n" + " -Others : key in 'listmenutype others' to view all Others"
            + "\n" + " -Set Meals : key in 'listmenutype set meal' to view all Set Meal";

    @Override
    public CommandResult execute() {
        return new MenuCommandResult(MAIN_MENU_DISPLAY);
    }
}
