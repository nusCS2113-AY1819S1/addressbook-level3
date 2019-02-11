package seedu.addressbook.data.employee;

import seedu.addressbook.data.exception.IllegalValueException;

//@@author kianhong95
/**
 * Represents an Employee's position in the Rms.
 * Guarantees: immutable; is valid as declared in {@link #isValidPosition(String)}
 */
public class EmployeePosition {


    public static final String EXAMPLE = "Cashier";
    public static final String MESSAGE_POSITION_CONSTRAINTS = "Position cannot be longer than "
                    + "30 alphanumeric characters and spaces.";
    public static final String POSITION_VALIDATION_REGEX = "[\\p{Alnum} ]{1,30}+";

    public final String value;

    /**
     * Empty constructor
     */
    public EmployeePosition() {
        this.value = "";
    }

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public EmployeePosition(String value) throws IllegalValueException {
        String trimmedValue = value.trim();
        if (!isValidPosition(trimmedValue)) {
            throw new IllegalValueException(MESSAGE_POSITION_CONSTRAINTS);
        }
        this.value = trimmedValue;
    }

    /**
     * Returns true if a given string is a valid employee name.
     */
    public static boolean isValidPosition(String test) {
        return test.matches(POSITION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmployeePosition // instanceof handles nulls
                && this.value.equals(((EmployeePosition) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
