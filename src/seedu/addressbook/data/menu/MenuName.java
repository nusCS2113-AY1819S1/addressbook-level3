package seedu.addressbook.data.menu;

import java.util.Arrays;
import java.util.List;

import seedu.addressbook.data.exception.IllegalValueException;

//@@author SalsabilTasnia
/**
 * Represents a Menu's name in the Menu list.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class MenuName {

    public static final String EXAMPLE = "Cheese Burger";
    public static final String MESSAGE_NAME_CONSTRAINTS = "Menu Item names should be spaces or alphanumeric characters."
            + " It should contain minimum one character and must not be longer than"
            + " 30 alphanumeric characters and spaces";
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum} ]{1,30}+";

    public final String fullName;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public MenuName(String name) throws IllegalValueException {
        String trimmedName = name.trim();
        if (!isValidName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.fullName = trimmedName;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

    /**
     * Retrieves a listing of every word in the name, in order.
     */
    public List<String> getWordsInName() {
        return Arrays.asList(fullName.toLowerCase().split("\\s+"));
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MenuName // instanceof handles nulls
                && this.fullName.equals(((MenuName) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
