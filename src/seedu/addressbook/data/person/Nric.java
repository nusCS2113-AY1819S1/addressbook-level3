package seedu.addressbook.data.person;

import seedu.addressbook.data.exception.IllegalValueException;

import java.util.Arrays;
import java.util.List;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Nric {

    public static final String EXAMPLE = "S1234567A";
    public static final String MESSAGE_NRIC_CONSTRAINTS = "Person NRIC should be either S or T followed by 7 digits and a letter";
    public static final String NRIC_VALIDATION_REGEX = "[stST]\\d{7}\\w";
    private static final String ALrequired = "5"; //Minimum Access Level to view such information

    public final String NRIC;
    private boolean isPrivate;
    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public Nric(String nric, boolean isPrivate ) throws IllegalValueException {
        this.isPrivate = isPrivate;
        nric = nric.trim();
        if (!isValidName(nric)) {
            throw new IllegalValueException(MESSAGE_NRIC_CONSTRAINTS);
        }
        this.NRIC = nric;
    }

    /**
     * Returns true if a given string is a valid person NRIC.
     */
    public static boolean isValidName(String test) {
        return test.matches(NRIC_VALIDATION_REGEX);
    }

//    /**
//     * Retrieves a listing of every word in the name, in order.
//     */
//    public List<String> getWordsInName() {
//        return Arrays.asList(NRIC.split("\\s+"));
//    }

    @Override
    public String toString() {
        return NRIC;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Nric // instanceof handles nulls
                && this.NRIC.equals(((Nric) other).NRIC)); // state check
    }

    @Override
    public int hashCode() {
        return NRIC.hashCode();
    }

    public boolean isPrivate() {
        return isPrivate;
    }

}
