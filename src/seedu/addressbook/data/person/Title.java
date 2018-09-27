package seedu.addressbook.data.person;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents a Person's title in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTitle(String)}
 */
public class Title {
    public static final String EXAMPLE = "d";
    public static final String MESSAGE_TITLE_CONSTRAINTS = "Title should either be ''Doctor' or 'Patient'";

    public final String value;
    private boolean isPrivate;

    public Title(String title, boolean isPrivate) throws IllegalValueException {
        this.isPrivate = isPrivate;
        title = title.trim();
        if (!isValidTitle(title)){
            throw new IllegalValueException(MESSAGE_TITLE_CONSTRAINTS);
        }
        this.value = title;
    }

    public static boolean isValidTitle(String test){
        if(test.equals("Doctor") || test.equals("Patient")) return true;
        else return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Title // instanceof handles nulls
                && this.value.equals(((Title) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }

    public boolean isPrivate() { return isPrivate; }
}
