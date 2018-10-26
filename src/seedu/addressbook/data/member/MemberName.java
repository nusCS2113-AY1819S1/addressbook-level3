package seedu.addressbook.data.member;


import seedu.addressbook.common.Name;
import seedu.addressbook.data.exception.IllegalValueException;

import java.util.Arrays;
import java.util.List;

/**
 * Represents an Employee's name in the Rms.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class MemberName extends Name {

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public MemberName(String name) throws IllegalValueException {
        super(name);
    }
}
