package seedu.addressbook.commands.menu;

import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.menu.Menu;
import seedu.addressbook.data.menu.MenuName;
import seedu.addressbook.data.menu.ReadOnlyMenus;
import seedu.addressbook.data.menu.UniqueMenuList;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the result of a command execution.
 */
public class MenuCommandResult extends CommandResult {
   // private final List<Menu> internalList = new ArrayList<>();


    /** The feedback message to be shown to the user. Contains a description of the execution result */
    //public final String feedbackToUser;


    public MenuCommandResult(String feedbackToUser) {
        super(feedbackToUser);
    }



    /*public MenuCommandResult(String feedbackToUser, List<? extends ReadOnlyMenus> relevantMenus) {
        this.feedbackToUser = feedbackToUser;
        this.relevantMenus = relevantMenus;
    }*/
    /**
     * Returns list of menu items relevant to the command command result_menu, if any.
     */



    public MenuCommandResult(String feedbackToUser, List<? extends ReadOnlyMenus> relevantMenus){
        super(feedbackToUser, null, relevantMenus, null, null, null);
    }
}
