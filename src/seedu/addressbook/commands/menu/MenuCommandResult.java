package seedu.addressbook.commands.menu;

import java.util.List;

import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.menu.ReadOnlyMenus;

/**
 * Represents the result of a command execution.
 */
public class MenuCommandResult extends CommandResult {


    /** The feedback message to be shown to the user. Contains a description of the execution result */
    //public final String feedbackToUser;

    public MenuCommandResult(String feedbackToUser) {
        super(feedbackToUser);
    }

    /**
     * Returns list of menu items relevant to the command command result_menu, if any.
     */

    public MenuCommandResult(String feedbackToUser, List<? extends ReadOnlyMenus> relevantMenus) {
        super(feedbackToUser, relevantMenus, null, null, null, null);
    }
}
