package seedu.addressbook.data;

import seedu.addressbook.data.menu.Menu;
import seedu.addressbook.data.menu.ReadOnlyMenus;
import seedu.addressbook.data.menu.UniqueMenuList;
import seedu.addressbook.data.menu.UniqueMenuList.DuplicateMenuException;
import seedu.addressbook.data.order.Order;
import seedu.addressbook.data.order.ReadOnlyOrder;
import seedu.addressbook.data.order.UniqueOrderList;
import seedu.addressbook.data.order.UniqueOrderList.OrderNotFoundException;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.UniqueEmployeeList;
import seedu.addressbook.data.person.UniquePersonList;
import seedu.addressbook.data.person.UniquePersonList.DuplicatePersonException;
import seedu.addressbook.data.person.UniquePersonList.PersonNotFoundException;

/**
 * Represents the entire address book. Contains the data of the address book.
 */
public class RMS {

    private final UniquePersonList allPersons;
    private final UniqueEmployeeList allEmployees;
    private final UniqueMenuList allFoodItems;
    private final UniqueOrderList allOrders;

    public static RMS empty() {
        return new RMS();
    }

    /**
     * Creates an empty address book.
     */
    // added allEmployees = new UniqueEmployeeList();
    public RMS() {

        allPersons = new UniquePersonList();
        allEmployees = new UniqueEmployeeList();
        allFoodItems = new UniqueMenuList();
        allOrders = new UniqueOrderList();
    }

    /**
     * Constructs an address book with the given data.
     *
     * @param persons external changes to this will not affect this address book
     */
    // Construct address book with persons and employees
    public RMS(UniquePersonList persons, UniqueMenuList menus, UniqueEmployeeList employees, UniqueOrderList orders) {
        this.allPersons = new UniquePersonList(persons);
        this.allEmployees = new UniqueEmployeeList(employees);
        this.allFoodItems = new UniqueMenuList(menus);
        this.allOrders = new UniqueOrderList(orders);
    }

    /**
     * Adds a person to the address book.
     *
     * @throws DuplicatePersonException if an equivalent person already exists.
     */
    public void addPerson(Person toAdd) throws DuplicatePersonException { allPersons.add(toAdd); }

    public void addMenu(Menu toAdd) throws DuplicateMenuException { allFoodItems.add(toAdd); }

    /**
     * Adds an order to the order list.
     */
    public void addOrder(Order toAdd) {
        allOrders.add(toAdd);
    }


    /**
     * Checks if an equivalent person exists in the address book.
     */
    public boolean containsPerson(ReadOnlyPerson key) {
        return allPersons.contains(key);
    }

    public boolean containsMenus(ReadOnlyMenus key) {
        return allFoodItems.contains(key);
    }


    /**
     * Checks if an equivalent order exists in the order list.
     */
    public boolean containsOrder(ReadOnlyOrder key) {
        return allOrders.contains(key);
    }

    /**
     * Removes the equivalent person from the address book.
     *
     * @throws PersonNotFoundException if no such Person could be found.
     */
    public void removePerson(ReadOnlyPerson toRemove) throws PersonNotFoundException {
        allPersons.remove(toRemove);
    }

    public void removeMenuItem(ReadOnlyMenus toRemove) throws UniqueMenuList.MenuNotFoundException {
        allFoodItems.remove(toRemove);
    }

    /**
     * Removes the equivalent order from the order list.
     *
     * @throws OrderNotFoundException if no such Order could be found.
     */
    public void removeOrder(ReadOnlyOrder toRemove) throws OrderNotFoundException {
        allOrders.remove(toRemove);
    }

    /**
     * Clears all persons from the address book.
     */
    public void clear() {
        allPersons.clear();
    }

    public void clearMenu() {
        allFoodItems.clear();
    }

    /**
     * Clears all orders from the order list.
     */
    public void clearOrderList() {
        allOrders.clear();
    }

    /**
     * Defensively copied UniquePersonList of all persons in the address book at the time of the call.
     */
    public UniquePersonList getAllPersons() {
        return new UniquePersonList(allPersons);
    }

    // this is a copy of getAllPersons for employees
    public UniqueEmployeeList getAllEmployees() {
        return new UniqueEmployeeList(allEmployees);
    }

    public UniqueMenuList getAllMenus() {
        return new UniqueMenuList(allFoodItems);
    }

    public UniqueOrderList getAllOrders() { return new UniqueOrderList(allOrders); }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RMS // instanceof handles nulls
                && this.allPersons.equals(((RMS) other).allPersons));
    }

    @Override
    public int hashCode() {
        return allPersons.hashCode();
    }
}

