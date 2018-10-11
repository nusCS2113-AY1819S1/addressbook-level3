package seedu.addressbook.commands;

import seedu.addressbook.data.person.ReadOnlyMenus;
import seedu.addressbook.data.person.ReadOnlyPerson;

import java.util.List;
import java.util.Optional;

/**
 * Represents the result of a command execution.
 */
public class CommandResult_Menu extends CommandResult{

    /** The feedback message to be shown to the user. Contains a description of the execution result */
    //public final String feedbackToUser;

    /** The list of persons that was produced by the command */

    /*public CommandResult_Menu(String feedbackToUser) {
        this.feedbackToUser = feedbackToUser;
        relevantMenus = null;
    }

    public CommandResult_Menu(String feedbackToUser, List<? extends ReadOnlyMenus> relevantMenus) {
        this.feedbackToUser = feedbackToUser;
        this.relevantMenus = relevantMenus;
    }*/
    public CommandResult_Menu(String feedbackToUser) {
        super(feedbackToUser);
    }

    /**
     * Returns list of persons relevant to the command command result, if any.
     */
    public CommandResult_Menu(String feedbackToUser, List<? extends ReadOnlyMenus> relevantMenus){
        super(feedbackToUser, null, relevantMenus, null);
    }
}
