package seedu.addressbook.data;

import seedu.addressbook.data.person.*;
import seedu.addressbook.data.person.UniqueMenuList.DuplicateMenuException;
import seedu.addressbook.data.person.UniqueMenuList.MenuNotFoundException;

/**
 * Represents the entire address book. Contains the data of the address book.
 */
public class RMS_menu {

    private final UniqueMenuList allMenus;

    public static RMS_menu empty() {
        return new RMS_menu();
    }

    /**
     * Creates an empty address book.
     */
    public RMS_menu() {
        allMenus = new UniqueMenuList();
    }

    /**
     * Constructs an address book with the given data.
     *
     * @param menus external changes to this will not affect this address book
     */
    public RMS_menu(UniqueMenuList menus) {
        this.allMenus = new UniqueMenuList(menus);
    }

    /**
     * Adds a person to the address book.
     *
     * @throws DuplicateMenuException if an equivalent person already exists.
     */
    public void addMenuItem(Menu toAdd) throws DuplicateMenuException {
        allMenus.add(toAdd);
    }

    /**
     * Checks if an equivalent person exists in the address book.
     */
    public boolean containsMenu(ReadOnlyMenus key) {
        return allMenus.contains(key);
    }

    /**
     * Removes the equivalent person from the address book.
     *
     * @throws MenuNotFoundException if no such Person could be found.
     */
    public void removeMenuItem(ReadOnlyMenus toRemove) throws MenuNotFoundException {
        allMenus.remove(toRemove);
    }

    /**
     * Clears all persons from the address book.
     */
    public void clear() {
        allMenus.clear();
    }

    /**
     * Defensively copied UniquePersonList of all persons in the address book at the time of the call.
     */
    public UniqueMenuList getAllMenus() {
        return new UniqueMenuList(allMenus);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RMS_menu // instanceof handles nulls
                && this.allMenus.equals(((RMS_menu) other).allMenus));
    }

    @Override
    public int hashCode() {
        return allMenus.hashCode();
    }
}
