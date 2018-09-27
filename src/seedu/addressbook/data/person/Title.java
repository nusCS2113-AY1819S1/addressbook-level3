package seedu.addressbook.data.person;

import seedu.addressbook.data.exception.IllegalValueException;

public class Title {
    public static final String EXAMPLE = "d";
    public static final String MESSAGE_TITLE_CONSTRAINTS = "Title should either be 'd' for Doctor or 'p' for Patient";
    public final String value;


    public Title(String title) throws IllegalValueException {
        title = title.trim();
        if (!isValidTitle(title)){
            throw new IllegalValueException(MESSAGE_TITLE_CONSTRAINTS);
        }
        this.value = title;
    }

    public static boolean isValidTitle(String test){
        if((test == "p") || (test == "d")) return true;
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
}
