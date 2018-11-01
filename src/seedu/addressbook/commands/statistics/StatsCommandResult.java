package seedu.addressbook.commands.statistics;

import seedu.addressbook.commands.CommandResult;

/**
 * Represents the result of a command execution.
 */
public class StatsCommandResult extends CommandResult {

    /** The feedback message to be shown to the user. Contains a description of the execution result */
    //public final String feedbackToUser;


    public StatsCommandResult(String feedbackToUser) {
        super(feedbackToUser);
    }

    /*public MenuCommandResult(String feedbackToUser, List<? extends ReadOnlyMenus> relevantMenus) {
        this.feedbackToUser = feedbackToUser;
        this.relevantMenus = relevantMenus;
    }*/
    /**
     * Returns list of menu items relevant to the command command result_menu, if any.
     */
//    public StatsCommandResult(String feedbackToUser, List<? extends ReadOnlyMenus> relevantMenus){
//        super(feedbackToUser, null, relevantMenus, null, null, null);
//    }
}
