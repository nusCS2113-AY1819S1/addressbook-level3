package seedu.addressbook.commands.menu;

import java.util.List;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.menu.ReadOnlyMenus;

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


    /*
    private final Set<String> typeSet = new HashSet<>();
    final List<ReadOnlyMenus> typeList = new ArrayList<>();
    private List<ReadOnlyMenus> getFoodItemsBurger() {
        for (ReadOnlyMenus menuItem : rms.getAllMenus()) {
            //final Set<String> wordsInName = new HashSet<>(burger.getType().getWordsInTypeName());
            final String wordsInItemName = menuItem.getType().value;
            typeSet.add(wordsInItemName);
            typeList.add(menuItem);
            //System.out.println(wordsInItemName);
            //boolean exist = wordsInName.contains(itemword);

        }

        for(String type: typeSet){
            System.out.println(type);
        }
        return typeList;
    }

    private List<ReadOnlyMenus> getMenuItemsWithNameContainingAnyKeyword() {
        final List<ReadOnlyMenus> matchedMenuItems = new ArrayList<>();
        for (ReadOnlyMenus menu : rms.getAllMenus()){
            typeSet.add(menu.getType().value);
            System.out.println(menu.getType().value);
        }
        return matchedMenuItems;
    }

    private String ConstructDisplayMessage(){
        String MainMenuDisplay = "Main Menu";
        for (String typeName : typeSet){
          //  MainMenuDisplay += "\n" + typeName.toUpperCase() + ":  key in listmenutype"
          + typeName + "to view all" + typeName + "items";
            //System.out.println(typeName);
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
