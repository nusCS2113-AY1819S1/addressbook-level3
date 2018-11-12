package seedu.addressbook.data;

import java.util.Objects;

import seedu.addressbook.data.employee.Attendance;
import seedu.addressbook.data.employee.Employee;
import seedu.addressbook.data.employee.ReadOnlyEmployee;
import seedu.addressbook.data.employee.UniqueAttendanceList;
import seedu.addressbook.data.employee.UniqueEmployeeList;
import seedu.addressbook.data.employee.UniqueEmployeeList.DuplicateEmployeeException;
import seedu.addressbook.data.employee.UniqueEmployeeList.EmployeeNotFoundException;
import seedu.addressbook.data.member.Member;
import seedu.addressbook.data.member.ReadOnlyMember;
import seedu.addressbook.data.member.UniqueMemberList;
import seedu.addressbook.data.member.UniqueMemberList.DuplicateMemberException;
import seedu.addressbook.data.member.UniqueMemberList.MemberNotFoundException;
import seedu.addressbook.data.menu.Menu;
import seedu.addressbook.data.menu.ReadOnlyMenus;
import seedu.addressbook.data.menu.UniqueMenuList;
import seedu.addressbook.data.menu.UniqueMenuList.DuplicateMenuException;
import seedu.addressbook.data.menu.UniqueMenuList.MenuNotFoundException;
import seedu.addressbook.data.order.Order;
import seedu.addressbook.data.order.ReadOnlyOrder;
import seedu.addressbook.data.order.UniqueOrderList;
import seedu.addressbook.data.order.UniqueOrderList.DuplicateOrderException;
import seedu.addressbook.data.order.UniqueOrderList.OrderNotFoundException;

/**
 * Represents the entire Rms. Contains the data of the Rms.
 */
public class Rms {

    //@@author kianhong95
    private final UniqueEmployeeList allEmployees;
    //@@author kangmingtay
    private final UniqueMemberList allMembers;
    //@@author SalsabilTasnia
    private final UniqueMenuList allFoodItems;
    //@@author px1099
    private final UniqueOrderList allOrders;
    //@@author kianhong95
    private final UniqueAttendanceList allAttendance;

    //@@author px1099
    private Order draftOrder = new Order();

    //@@author AngWM
    /**
     * Creates an empty Rms.
     */
    public Rms() {
        allEmployees = new UniqueEmployeeList();
        allMembers = new UniqueMemberList();
        allFoodItems = new UniqueMenuList();
        allOrders = new UniqueOrderList();
        allAttendance = new UniqueAttendanceList();
    }

    /**
     * Constructs an Rms with the given data.
     */
    public Rms(UniqueMenuList menus,
               UniqueEmployeeList employees,
               UniqueOrderList orders,
               UniqueMemberList members,
               UniqueAttendanceList attendances) {
        this.allEmployees = new UniqueEmployeeList(employees);
        this.allMembers = new UniqueMemberList(members);
        this.allFoodItems = new UniqueMenuList(menus);
        this.allOrders = new UniqueOrderList(orders);
        this.allAttendance = new UniqueAttendanceList(attendances);
    }

    public static Rms empty() {
        return new Rms();
    }

    //@@author kianhong95
    /**
     * Adds an employee to the Rms.
     *
     * @throws DuplicateEmployeeException if an equivalent employee already exists.
     */
    public void addEmployee(Employee toAdd) throws DuplicateEmployeeException {
        allEmployees.add(toAdd);
    }

    /**
     * Adds an attendance list with the specified employee to the Rms.
     */
    public void addAttendance(Attendance toAdd) {
        allAttendance.add(toAdd);
    }

    /**
     * Gets index of the specified Attendance object.
     */
    public int findAttendanceIndex(String toFind) {
        return allAttendance.getAttendanceIndex(toFind);
    }


    /**
     * Gets index of the specified Attendance object.
     */
    public Attendance findAttendance(int toFind) {
        return allAttendance.getAttendance(toFind);
    }

    /**
     * Adds an attendance list with the specified employee to the Rms.
     */
    public void updateAttendance(Attendance oldAttendance, Attendance newAttendance) {
        allAttendance.setAttendance(oldAttendance, newAttendance); }

    //@@author kangmingtay
    /**
     * Adds a member to the Member list.
     *
     * @throws DuplicateMemberException if an equivalent member already exists.
     */

    public void addMember(Member toAdd) throws DuplicateMemberException {
        allMembers.add(toAdd);
    }

    //@@author SalsabilTasnia
    /**
     * Adds a menu item to the menu list.
     *
     * @throws DuplicateMenuException if an equivalent menu item already exists.
     */
    public void addMenu(Menu toAdd) throws DuplicateMenuException {
        allFoodItems.add(toAdd);
    }

    //@@author px1099
    /**
     * Adds an order to the order list.
     *
     * @throws DuplicateOrderException if an equivalent order already exists.
     */
    public void addOrder(Order toAdd) throws DuplicateOrderException {
        allOrders.add(toAdd);
    }

    //@@author SalsabilTasnia
    /**
     * Checks if an equivalent menu item exists in the menu list.
     */
    public boolean containsMenus(ReadOnlyMenus key) {
        return allFoodItems.contains(key);
    }

    //@@author kangmingtay
    /**
     * Checks if an equivalent member exists in the member list.
     */
    public boolean containsMember(ReadOnlyMember key) {
        return allMembers.contains(key);
    }

    //@@author SalsabilTasnia
    /**
     * Removes the equivalent menu item from the menu.
     *
     * @throws MenuNotFoundException if no such Order could be found.
     */
    public void removeMenuItem(ReadOnlyMenus toRemove) throws MenuNotFoundException {
        allFoodItems.remove(toRemove);
    }

    //@@author px1099
    /**
     * Removes the equivalent order from the order list.
     *
     * @throws OrderNotFoundException if no such Order could be found.
     */
    public void removeOrder(ReadOnlyOrder toRemove) throws OrderNotFoundException {
        allOrders.remove(toRemove);
    }

    //@@author kangmingtay
    /**
     * Removes the equivalent member from the member list.
     *
     * @throws MemberNotFoundException if no such Member could be found.
     */
    public void removeMember(ReadOnlyMember toRemove) throws MemberNotFoundException {
        allMembers.remove(toRemove);
    }

    //@@author kianhong95
    /**
     * Removes the equivalent employee from the Rms.
     *
     * @throws EmployeeNotFoundException if no such Employee could be found.
     */
    public void removeEmployee(ReadOnlyEmployee toRemove) throws EmployeeNotFoundException {
        allEmployees.remove(toRemove);
    }

    /**
     * Removes an attendance list with the specified employee from the Rms.
     */
    public void removeAttendance(Attendance toRemove) {
        allAttendance.remove(toRemove);
    }

    /**
     * Edits the equivalent employee from Rms
     *
     * @throws EmployeeNotFoundException if no such Employee could be found.
     */
    public void editEmployee(ReadOnlyEmployee toRemove, Employee toReplace) throws EmployeeNotFoundException {
        allEmployees.edit(toRemove, toReplace);
    }

    //@@author SalsabilTasnia
    /**
     * Clears all menu items from the menu.
     */
    public void clearMenu() {
        allFoodItems.clear();
    }

    //@@author px1099
    /**
     * Clears all orders from the order list.
     */
    public void clearOrderList() {
        allOrders.clear();
    }

    //@@author kangmingtay
    /**
     * Defensively copied UniqueMemberList of all members in the member list at the time of the call.
     */
    public UniqueMemberList getAllMembers() {
        return new UniqueMemberList(allMembers);
    }

    //@@author kianhong95
    /**
     * Defensively copied UniqueEmployeeList of all employees in the employee list at the time of the call.
     */
    public UniqueEmployeeList getAllEmployees() {
        return new UniqueEmployeeList(allEmployees);
    }

    /**
     * Defensively copied UniqueEmployeeList of all employees in the employee list at the time of the call.
     */
    public UniqueAttendanceList getAllAttendance() {
        return new UniqueAttendanceList(allAttendance);
    }

    //@@author SalsabilTasnia
    /**
     * Defensively copied UniqueMenuList of all menu items in the menu at the time of the call.
     */
    public UniqueMenuList getAllMenus() {
        return new UniqueMenuList(allFoodItems);
    }

    //@@author px1099
    /**
     * Defensively copied UniqueOrderList of all orders in the employee list at the time of the call.
     */
    public UniqueOrderList getAllOrders() {
        return new UniqueOrderList(allOrders);
    }

    public ReadOnlyOrder getDraftOrder() {
        return draftOrder;
    }

    public String getDraftOrderAsText() {
        return draftOrder.getDraftDetailsAsText();
    }

    /**
     * Set a member to be the customer of the draft order
     */
    public void editDraftOrderCustomer(ReadOnlyMember customer) {
        draftOrder.setCustomer(customer);
    }

    /**
     * Adjust the dish and its quantity in the draft order to add, remove or edit dish items in the draft.
     */
    public void editDraftOrderDishItem(ReadOnlyMenus dish, int quantity) {
        draftOrder.changeDishQuantity(dish, quantity);
    }

    //@@author kangmingtay
    /**
     * Edit the number of points to be redeemed
     */
    public void editDraftOrderPoints(int points) {
        draftOrder.setPoints(points);
    }

    //@@author px1099
    /**
     * Update the member points of a customer
     * @param customer the ReadOnlyMember interface of the Member object to update points
     * @param price the price of the added order made by the customer
     * @param points the redeemed points the customer used in the order
     */
    public void updatePointsOfCustomer(ReadOnlyMember customer, double price, int points) {
        allMembers.updatePointsOfCustomer(customer, price, points);
    }

    public void clearDraftOrder() {
        draftOrder = new Order();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Rms // instanceof handles nulls
                && this.allAttendance.equals(((Rms) other).allAttendance)
                && this.allEmployees.equals(((Rms) other).allEmployees)
                && this.allFoodItems.equals(((Rms) other).allFoodItems)
                && this.allMembers.equals(((Rms) other).allMembers)
                && this.allOrders.equals(((Rms) other).allOrders));
    }

    @Override
    public int hashCode() {
        return Objects.hash(allAttendance, allEmployees, allFoodItems, allMembers, allOrders);
    }

    //@@author
}
