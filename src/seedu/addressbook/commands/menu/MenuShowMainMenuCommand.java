package seedu.addressbook.commands.menu;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.menu.ReadOnlyMenus;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Lists all food items in the address book to the user.
 */
public class MenuShowMainMenuCommand extends Command {

    public static final String COMMAND_WORD = "showMainMenu";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" 
            + "Displays all the categories of menu items in the Rms system.\n\t"
            + "Example: " + COMMAND_WORD;
    public static final String MAIN_MENU_DISPLAY = "RMS Main Menu" + "\n"
                                                   +"==================================================================================="
                                                   + "\n\n" + "Item Category:"
                                                   + "\n\n" + "-Main : key in 'listmenutype main' to view all Main items"
                                                   + "\n" + " -Sides : key in 'listmenutype sides' to view all Sides"
                                                   + "\n" + " -Beverages : key in 'listmenutype beverage' to view all Beverage"
                                                   + "\n" + " -Dessert : key in 'listmenutype dessert' to view all Dessert"
                                                   + "\n" + " -Others : key in 'listmenutype others' to view all Others"
                                                   + "\n" + " -Set Meals : key in 'listmenutype set meals' to view all Set Meals";


    private final Set<String> typeSet = new HashSet<>();

    private void ExistingMenuCategory() {
        List<ReadOnlyMenus> allMenus = rms.getAllMenus().immutableListView();
        for (ReadOnlyMenus menuItem : allMenus) {
            //final Set<String> typeSet = new HashSet<>(menuItem.getType().getWordsInTypeName());
            typeSet.add(menuItem.getType().value);
        }
    }

    /*private String ConstructDisplayMessage(){
        String MainMenuDisplay = "Main Menu";
        for (String typeName : typeSet){
            MainMenuDisplay += "\n" + typeName.toUpperCase() + ":  key in listmenutype" + typeName + "to view all" + typeName + "items";
        }
        return MainMenuDisplay;
    }*/




    @Override
    public CommandResult execute() {
        List<ReadOnlyMenus> allMenus = rms.getAllMenus().immutableListView();
        return new MenuCommandResult(MAIN_MENU_DISPLAY);
        //return new MenuCommandResult(getMessageForMenuListShownSummary(allMenus), allMenus);
    }
}
