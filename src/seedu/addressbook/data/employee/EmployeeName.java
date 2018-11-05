package seedu.addressbook.data.employee;


import java.util.Arrays;
import java.util.List;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents an Employee's name in the Rms.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class EmployeeName {


    public static final String EXAMPLE = "Peter Lee";
    public static final String MESSAGE_NAME_CONSTRAINTS = "Employee names should be spaces or alphanumeric characters";
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public final String value;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public EmployeeName(String name) throws IllegalValueException {
        name = name.trim();
        if (!isValidName(name)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.value = name;
    }

    /**
     * Returns true if a given string is a valid employee name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

    /**
     * Retrieves a listing of every word in the name, in order.
     */
    public List<String> getWordsInName() {
        return Arrays.asList(value.split("\\s+"));
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmployeeName // instanceof handles nulls
                && this.value.equals(((EmployeeName) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
