//@@author kianhong95
package seedu.addressbook.data.employee;

import seedu.addressbook.common.Name;
import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents an Employee's name in the Rms.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class EmployeeName extends Name {

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public EmployeeName(String name) throws IllegalValueException {
        super(name);
    }
}
