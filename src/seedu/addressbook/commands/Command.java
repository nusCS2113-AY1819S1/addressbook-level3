package seedu.addressbook.commands;

import static seedu.addressbook.ui.Gui.DISPLAYED_INDEX_OFFSET;

import java.util.List;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.Rms;
import seedu.addressbook.data.employee.ReadOnlyEmployee;
import seedu.addressbook.data.member.ReadOnlyMember;
import seedu.addressbook.data.menu.ReadOnlyMenus;
import seedu.addressbook.data.order.ReadOnlyOrder;

/**
 * Represents an executable command.
 */
public abstract class Command {

    //@@author AngWM
    protected Rms rms;
    //@@author SalsabilTasnia
    protected List<? extends ReadOnlyMenus> relevantMenus;
    //@@author kangmingtay
    protected List<? extends ReadOnlyMember> relevantMembers;
    //@@author px1099
    protected List<? extends ReadOnlyOrder> relevantOrders;
    //@@author kianhong95
    protected List<? extends ReadOnlyEmployee> relevantEmployees;
    //@@author

    private int targetIndex = -1;

    /**
     * @param targetIndex last visible listing index of the target object
     */
    public Command(int targetIndex) {
        this.setTargetIndex(targetIndex);
    }

    protected Command() {
    }

    //@@author kianhong95
    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of employees.
     *
     * @param employeesDisplayed used to generate summary
     * @return summary message for employees displayed
     */
    public static String getMessageForEmployeeListShownSummary(List<? extends ReadOnlyEmployee> employeesDisplayed) {
        if (employeesDisplayed.size() == 0) {
            return Messages.MESSAGE_NO_EMPLOYEES_IN_SYSTEM;
        }
        return String.format(Messages.MESSAGE_EMPLOYEES_LISTED_OVERVIEW, employeesDisplayed.size());
    }

    //@@author SalsabilTasnia
    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of menu.
     *
     * @param menusDisplayed used to generate summary
     * @return summary message for menus displayed
     */
    public static String getMessageForMenuListShownSummary(List<? extends ReadOnlyMenus> menusDisplayed) {
        return String.format(Messages.MESSAGE_MENUS_LISTED_OVERVIEW, menusDisplayed.size());
    }

    //@@author kangmingtay
    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of members.
     *
     * @param membersDisplayed used to generate summary
     * @return summary message for members displayed
     */
    public static String getMessageForMemberListShownSummary(List<? extends ReadOnlyMember> membersDisplayed) {
        return String.format(Messages.MESSAGE_MEMBERS_LISTED_OVERVIEW, membersDisplayed.size());
    }

    //@@author px1099
    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of orders.
     *
     * @param ordersDisplayed used to generate summary
     * @return summary message for orders displayed
     */
    public static String getMessageForOrderListShownSummary(List<? extends ReadOnlyOrder> ordersDisplayed) {
        return String.format(Messages.MESSAGE_ORDERS_LISTED_OVERVIEW, ordersDisplayed.size());
    }

    /**
     * Constructs a string from the current status of the draft order.
     *
     * @return draft order details
     */
    protected String getDraftOrderAsString() {
        final String draftOrder = rms.getDraftOrderAsText();
        return String.format(Messages.MESSAGE_DRAFT_ORDER_DETAILS, draftOrder);
    }

    //@@ author
    /**
     * Executes the command and returns the result.
     */
    public abstract CommandResult execute();

    //Note: it is better to make the execute() method abstract, by replacing the above method with the line below:
    //public abstract CommandResult execute();

    //@@author AngWM
    /**
     * Supplies the data the command will operate on.
     */
    public void setData(Rms rms,
                        List<? extends ReadOnlyMenus> relevantMenus,
                        List<? extends ReadOnlyOrder> relevantOrders,
                        List<? extends ReadOnlyMember> relevantMembers,
                        List<? extends ReadOnlyEmployee> relevantEmployees) {
        this.rms = rms;
        this.relevantMenus = relevantMenus;
        this.relevantOrders = relevantOrders;
        this.relevantMembers = relevantMembers;
        this.relevantEmployees = relevantEmployees;
    }

    //@@author SalsabilTasnia
    /**
     * Extracts the the target menu item in the last shown menu list from the given arguments.
     *
     * @throws IndexOutOfBoundsException if the target index is out of bounds of the last viewed listing
     */
    protected ReadOnlyMenus getTargetMenu() throws IndexOutOfBoundsException {
        return relevantMenus.get(getTargetIndex() - DISPLAYED_INDEX_OFFSET);
    }

    //@@author kangmingtay
    /**
     * Extracts the the target member in the last shown list from the given arguments.
     *
     * @throws IndexOutOfBoundsException if the target index is out of bounds of the last viewed listing
     */
    protected ReadOnlyMember getTargetMember() throws IndexOutOfBoundsException {
        return relevantMembers.get(getTargetIndex() - DISPLAYED_INDEX_OFFSET);
    }

    //@@author px1099
    /**
     * Extracts the the target order in the last shown order list from the given arguments.
     *
     * @throws IndexOutOfBoundsException if the target index is out of bounds of the last viewed listing
     */
    protected ReadOnlyOrder getTargetOrder() throws IndexOutOfBoundsException {
        return relevantOrders.get(getTargetIndex() - DISPLAYED_INDEX_OFFSET);
    }

    //@@author kianhong95
    /**
     * Extracts the target employee in the last shown employee list from the given arguments.
     *
     * @throws IndexOutOfBoundsException if the target index is out of bounds of the last viewed listing
     */
    protected ReadOnlyEmployee getTargetEmployee() throws IndexOutOfBoundsException {
        return relevantEmployees.get(getTargetIndex() - DISPLAYED_INDEX_OFFSET);
    }

    //@@author
    public int getTargetIndex() {
        return targetIndex;
    }

    public void setTargetIndex(int targetIndex) {
        this.targetIndex = targetIndex;
    }
}
