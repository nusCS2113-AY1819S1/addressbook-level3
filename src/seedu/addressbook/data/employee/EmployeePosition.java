package seedu.addressbook.data.employee;

import java.util.Arrays;
import java.util.List;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents an Employee's position in the Rms.
 * Guarantees: immutable; is valid as declared in {@link #isValidPosition(String)}
 */
public class EmployeePosition {


    public static final String EXAMPLE = "Cashier";
    public static final String MESSAGE_NAME_CONSTRAINTS = "Employee position should be spaces or alphanumeric characters";
    public static final String POSITION_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public final String position;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public EmployeePosition(String position) throws IllegalValueException {
        position = position.trim();
        if (!isValidPosition(position)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.position = position;
    }

    /**
     * Returns true if a given string is a valid employee name.
     */
    public static boolean isValidPosition(String test) {
        return test.matches(POSITION_VALIDATION_REGEX);
    }

    /**
     * Retrieves a listing of every word in the name, in order.
     */
    public List<String> getWordsInName() {
        return Arrays.asList(position.split("\\s+"));
    }

    @Override
    public String toString() {
        return position;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmployeePosition // instanceof handles nulls
                && this.position.equals(((EmployeePosition) other).position)); // state check
    }

    @Override
    public int hashCode() { return position.hashCode();
    }
}
