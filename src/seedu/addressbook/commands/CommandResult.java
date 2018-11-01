package seedu.addressbook.commands;

import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.Schedule;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    /** The feedback message to be shown to the user. Contains a description of the execution result */
    public final String feedbackToUser;

    /** The list of persons that was produced by the command */
    private final List<? extends ReadOnlyPerson> relevantPersons;

    /** The list schedule of the selected persons that was produced by the command */
    private final Set<? extends Schedule> schedulesOfPerson;

    public CommandResult(String feedbackToUser) {
        this.feedbackToUser = feedbackToUser;
        relevantPersons = null;
        schedulesOfPerson = null;
    }

    public CommandResult(String feedbackToUser, List<? extends ReadOnlyPerson> relevantPersons) {
        this.feedbackToUser = feedbackToUser;
        this.relevantPersons = relevantPersons;
        schedulesOfPerson = null;
    }

    public CommandResult(String feedbackToUser, Set<? extends Schedule> schedulesOfPerson) {
        this.feedbackToUser = feedbackToUser;
        this.schedulesOfPerson = schedulesOfPerson;
        relevantPersons = null;
    }

    /**
     * Returns list of persons relevant to the command command result, if any.
     */
    public Optional<List<? extends ReadOnlyPerson>> getRelevantPersons() {
        return Optional.ofNullable(relevantPersons);
    }

    /**
     * Returns the set of appointments persons of the chosen person relevant to the command command result, if any.
     */
    public Optional<Set<? extends Schedule>> getRelevantAppointments() {
        return Optional.ofNullable(schedulesOfPerson);
    }

}
