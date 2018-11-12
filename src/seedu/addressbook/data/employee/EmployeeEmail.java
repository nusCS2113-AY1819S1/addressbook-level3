package seedu.addressbook.data.employee;

import seedu.addressbook.common.Email;
import seedu.addressbook.data.exception.IllegalValueException;

//@@author kianhong95
/**
 * Represents an Employee's email in the Rms.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class EmployeeEmail extends Email {

    /**
     * Empty constructor
     */
    public EmployeeEmail() {
        super();
    }

    /**
     * Validates given email.
     *
     * @throws IllegalValueException if given email address string is invalid.
     */
    public EmployeeEmail(String email) throws IllegalValueException {
        super(email);
    }
}
