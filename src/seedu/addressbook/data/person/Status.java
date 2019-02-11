package seedu.addressbook.data.person;
//@@author muhdharun

import java.util.Arrays;

import seedu.addressbook.data.exception.IllegalValueException;


/**
 * Represents a Person's criminal status (if any).
 * Guarantees: Can be edited using 'edit' command only for "clear" and "wanted";
 * is valid as declared in {@link #isValidStatus(String)}
 */

public class Status {

    public static final String EXAMPLE = "wanted";
    public static final String MESSAGE_NAME_CONSTRAINTS = "Status should be one of the 3: wanted/xc/clear";

    static final String WANTED_KEYWORD = "wanted";
    private static final String EXCONVICT_KEYWORD = "xc"; //ex-convict
    private static final String CLEAR_KEYWORD = "clear";

    private static final String[] STATUS_VALIDATION = {WANTED_KEYWORD, EXCONVICT_KEYWORD, CLEAR_KEYWORD};

    private final String currentStatus;

    /**
     * Validates given Status.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public Status(String status) throws IllegalValueException {
        status = status.trim();
        if (!isValidStatus(status)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.currentStatus = status;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    /**
     * Returns true if a given string is a valid Status.
     */

    private static boolean isValidStatus(String test) {

        return Arrays.asList(STATUS_VALIDATION).contains(test);
    }

    @Override
    public String toString() {
        return currentStatus;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Status // instanceof handles nulls
                && this.currentStatus.equals(((Status) other).currentStatus)); // state check
    }

    @Override
    public int hashCode() {
        return currentStatus.hashCode();
    }

}
