package seedu.addressbook.data;

import seedu.addressbook.data.member.Member;
import seedu.addressbook.data.member.ReadOnlyMember;
import seedu.addressbook.data.member.UniqueMemberList;
import seedu.addressbook.data.person.Menu;
import seedu.addressbook.data.person.ReadOnlyMenus;
import seedu.addressbook.data.person.UniqueMenuList;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.UniqueEmployeeList;
import seedu.addressbook.data.person.UniquePersonList;
import seedu.addressbook.data.person.UniquePersonList.DuplicatePersonException;
import seedu.addressbook.data.person.UniquePersonList.PersonNotFoundException;
import seedu.addressbook.data.member.UniqueMemberList.DuplicateMemberException;
import seedu.addressbook.data.member.UniqueMemberList.MemberNotFoundException;

/**
 * Represents the entire address book. Contains the data of the address book.
 */
public class AddressBook {

    private final UniquePersonList allPersons;
    private final UniqueEmployeeList allEmployees;
    private final UniqueMenuList allFoodItems;
    private final UniqueMemberList allMembers;

    public static AddressBook empty() {
        return new AddressBook();
    }

    /**
     * Creates an empty address book.
     */
    // added allEmployees = new UniqueEmployeeList();
    public AddressBook() {

        allPersons = new UniquePersonList();
        allEmployees = new UniqueEmployeeList();
        allFoodItems = new UniqueMenuList();
        allMembers = new UniqueMemberList();
    }

    /**
     * Constructs an address book with the given data.
     *
     * @param persons external changes to this will not affect this address book
     */
    // Construct address book with persons and employees
    public AddressBook(UniquePersonList persons, UniqueMenuList menus, UniqueEmployeeList employees, UniqueMemberList members) {
        this.allPersons = new UniquePersonList(persons);
        this.allEmployees = new UniqueEmployeeList(employees);
        this.allFoodItems = new UniqueMenuList(menus);
        this.allMembers = new UniqueMemberList(members);
    }

    /**
     * Adds a person to the address book.
     *
     * @throws DuplicatePersonException if an equivalent person already exists.
     */
    public void addPerson(Person toAdd) throws DuplicatePersonException {
        allPersons.add(toAdd);
    }

    public void addMenu(Menu toAdd1) throws UniqueMenuList.DuplicateMenuException {
        allFoodItems.add(toAdd1);
    }

    /**
     * Adds a member to the address book.
     *
     * @throws DuplicateMemberException if an equivalent member already exists.
     */

    public void addMember(Member toAdd) throws DuplicateMemberException {
        allMembers.add(toAdd);
    }

    /**
     * Checks if an equivalent person exists in the address book.
     */
    public boolean containsPerson(ReadOnlyPerson key) {
        return allPersons.contains(key);
    }

    public boolean containsMenus(ReadOnlyMenus key1) {
        return allFoodItems.contains(key1);
    }

    /**
     * Checks if an equivalent member exists in the address book.
     */
    public boolean containsMember(ReadOnlyMember key) {
        return allMembers.contains(key);
    }

    /**
     * Removes the equivalent person from the address book.
     *
     * @throws PersonNotFoundException if no such Person could be found.
     */
    public void removePerson(ReadOnlyPerson toRemove) throws PersonNotFoundException {
        allPersons.remove(toRemove);
    }

    public void removeMenuItem(ReadOnlyMenus toRemove1) throws UniqueMenuList.MenuNotFoundException {
        allFoodItems.remove(toRemove1);
    }

    /**
     * Removes the equivalent member from the address book.
     *
     * @throws MemberNotFoundException if no such Member could be found.
     */
    public void removeMember(ReadOnlyMember toRemove) throws MemberNotFoundException {
        allMembers.remove(toRemove);
    }

    /**
     * Clears all persons from the address book.
     */
    public void clear() {
        allPersons.clear();
    }

    public void clearmenu() {
        allFoodItems.clear();
    }

    /**
     * Clears all members from the address book.
     */
    public void clearmembers() {
        allMembers.clear();
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

    /**
     * Defensively copied UniqueMemberList of all members in the address book at the time of the call.
     */
    public UniqueMemberList getAllMembers() {
        return new UniqueMemberList(allMembers);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.allPersons.equals(((AddressBook) other).allPersons));
    }

    @Override
    public int hashCode() {
        return allPersons.hashCode();
    }
}

