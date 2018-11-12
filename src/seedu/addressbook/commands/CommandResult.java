package seedu.addressbook.commands;

import java.util.List;
import java.util.Optional;

import seedu.addressbook.data.employee.Attendance;
import seedu.addressbook.data.employee.ReadOnlyEmployee;
import seedu.addressbook.data.member.ReadOnlyMember;
import seedu.addressbook.data.menu.ReadOnlyMenus;
import seedu.addressbook.data.order.ReadOnlyOrder;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    /** The feedback message to be shown to the user. Contains a description of the execution result */
    public final String feedbackToUser;

    //@@author kianhong95
    /** The list of employees that was produced by the command */
    private final List<? extends ReadOnlyEmployee> relevantEmployees;

    /** The list of attendances that was produced by the command */
    private final List<? extends Attendance> relevantAttendances;

    //@@author SalsabilTasnia
    /** The menu list produced by the menu command*/
    private final List<? extends ReadOnlyMenus> relevantMenus;

    //@@author kangmingtay
    /** The list of members that was produced by the command */
    private final List<? extends ReadOnlyMember> relevantMembers;

    //@@author px1099
    /** The list of orders that was produced by the order command */
    private final List<? extends ReadOnlyOrder> relevantOrders;

    /** Constructor for result which do not return any list*/
    public CommandResult(String feedbackToUser) {
        this.feedbackToUser = feedbackToUser;
        relevantMenus = null;
        relevantOrders = null;
        relevantMembers = null;
        relevantEmployees = null;
        relevantAttendances = null;
    }

    /** Command result constructor used by child classes for Rms commands*/
    public CommandResult(String feedbackToUser,
                         List<? extends ReadOnlyMenus> relevantMenus,
                         List<? extends ReadOnlyOrder> relevantOrders,
                         List<? extends ReadOnlyMember> relevantMembers,
                         List<? extends ReadOnlyEmployee> relevantEmployees,
                         List<? extends Attendance> relevantAttendances) {
        this.feedbackToUser = feedbackToUser;
        this.relevantMenus = relevantMenus;
        this.relevantOrders = relevantOrders;
        this.relevantMembers = relevantMembers;
        this.relevantEmployees = relevantEmployees;
        this.relevantAttendances = relevantAttendances;
    }

    //@@author SalsabilTasnia
    /**
     * Returns list of menu items relevant to the command result, if any.
     */
    public Optional<List<? extends ReadOnlyMenus>> getRelevantMenus() {
        return Optional.ofNullable(relevantMenus);
    }

    //@@author px1099
    /**
     * Returns list of orders relevant to the command result, if any.
     */
    public Optional<List<? extends ReadOnlyOrder>> getRelevantOrders() {
        return Optional.ofNullable(relevantOrders);
    }

    //@@author kangmingtay
    /**
     * Returns list of members relevant to the command result, if any.
     */
    public Optional<List<? extends ReadOnlyMember>> getRelevantMember() {
        return Optional.ofNullable(relevantMembers);
    }

    //@@author kianhong95
    /**
     * Returns list of employees relevant to the command result, if any.
     */
    public Optional<List<? extends ReadOnlyEmployee>> getRelevantEmployee() {
        return Optional.ofNullable(relevantEmployees);
    }

    /**
     * Returns list of attendances relevant to the command result, if any.
     */
    public Optional<List<? extends Attendance>> getRelevantAttendance() {
        return Optional.ofNullable(relevantAttendances);
    }

    //@@author
}
