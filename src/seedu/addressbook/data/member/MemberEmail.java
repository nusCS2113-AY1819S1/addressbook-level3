package seedu.addressbook.data.member;

import seedu.addressbook.common.Email;
import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents a member's email in the RMS
 */
public class MemberEmail extends Email {

    public MemberEmail(String email) throws IllegalValueException {
        super(email);
    }
}
