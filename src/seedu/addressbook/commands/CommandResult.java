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

    /** The editable list of persons that was produced by the command */
    private final List<ReadOnlyPerson> editableRelevantPersons;

    /** A boolean that determines if the list of person present get printed */
    private boolean toPrint = true;


    public CommandResult(String feedbackToUser) {
        this.feedbackToUser = feedbackToUser;
        relevantPersons = null;
        schedulesOfPerson = null;
        editableRelevantPersons = null;
    }

    public CommandResult(String feedbackToUser, Set<? extends Schedule> schedulesOfPerson) {
        this.feedbackToUser = feedbackToUser;
        this.schedulesOfPerson = schedulesOfPerson;
        relevantPersons = null;
        editableRelevantPersons = null;
    }

    public CommandResult(String feedbackToUser, List<ReadOnlyPerson> relevantPersons, List<ReadOnlyPerson> editableRelevantPersons) {
        this.feedbackToUser = feedbackToUser;
        this.relevantPersons = relevantPersons;
        this.editableRelevantPersons = editableRelevantPersons;
        schedulesOfPerson = null;
    }

    public CommandResult(String feedbackToUser, List<ReadOnlyPerson> relevantPersons, List<ReadOnlyPerson> editableRelevantPersons, boolean toPrint) {
        this.feedbackToUser = feedbackToUser;
        this.relevantPersons = relevantPersons;
        this.editableRelevantPersons = editableRelevantPersons;
        schedulesOfPerson = null;
        this.toPrint = toPrint;
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

    /**
     * Returns list of editable persons relevant to the command command result, if any.
     */
    public Optional<List<ReadOnlyPerson>> getEditableRelevantPersons() {
        return Optional.ofNullable(editableRelevantPersons);
    }

    /**
     * Returns a boolean that determines if the list of persons relevant to the command gets printed.
     */
    public boolean canPrint() {
        return toPrint;
    }

}
