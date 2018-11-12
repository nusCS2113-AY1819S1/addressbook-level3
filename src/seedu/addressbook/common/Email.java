package seedu.addressbook.common;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents a Person's email in the Rms.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email {
    public static final String EXAMPLE = "Example2018@rms.com";
    public static final String MESSAGE_EMAIL_CONSTRAINTS =
            "Emails should be 2 alphanumeric/period strings that are no longer than 20 characters separated by '@'";
    public static final String EMAIL_VALIDATION_REGEX = "[\\w.]{1,20}+@[\\w.]{1,20}+";

    public final String value;

    /**
     * Validates given email.
     *
     * @throws IllegalValueException if given email address string is invalid.
     */
    public Email(String email) throws IllegalValueException {
        String trimmedEmail = email.trim();
        if (!isValidEmail(trimmedEmail)) {
            throw new IllegalValueException(MESSAGE_EMAIL_CONSTRAINTS);
        }
        this.value = trimmedEmail;
    }

    /**
     * Checks if a given string is a valid email.
     */
    public static boolean isValidEmail(String test) {
        return test.matches(EMAIL_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Email // instanceof handles nulls
                && this.value.equals(((Email) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
