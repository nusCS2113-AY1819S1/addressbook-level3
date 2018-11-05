package seedu.addressbook.data.menu;

import seedu.addressbook.data.exception.IllegalValueException;

import java.util.Arrays;
import java.util.List;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTypeName(String)}
 */
public class Type {

    public static final String EXAMPLE = "Burger";
    public static final String MESSAGE_TYPE_CONSTRAINTS = "Item Type should only be in words";
    public static final String TYPE_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public final String value;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public Type(String name) throws IllegalValueException {
        name = name.trim();
        if (!isValidTypeName(name)) {
            throw new IllegalValueException(MESSAGE_TYPE_CONSTRAINTS);
        }
        this.value = name;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidTypeName(String test) {
        return test.matches(TYPE_VALIDATION_REGEX);
    }

    /**
     * Retrieves a listing of every word in the name, in order.
     */
    public List<String> getWordsInTypeName() {

        return Arrays.asList(value.split("\\s+"));
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Type // instanceof handles nulls
                && this.value.equals(((Type) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
