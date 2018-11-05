package seedu.addressbook.data.menu;

import seedu.addressbook.data.exception.IllegalValueException;


/**
 * Price of a particular menu item in the Restaurant Management System.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Price {

    public static final String EXAMPLE = "$4.40";
    public static final String MESSAGE_PRICE_CONSTRAINTS = "Price should be in SGD and in dollars and cents";
    public static final String PHONE_VALIDATION_REGEX = "\\$[1-9][0-9]*(\\.[0-9]{2})?|\\$0?\\.[0-9][0-9]";//"^\\$\\d+([.][0-9]+)?$";

    public final String value;
    //private boolean isPrivate;

    /**
     * Validates given phone number.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public Price(String price/*, boolean isPrivate*/) throws IllegalValueException {
        //this.isPrivate = isPrivate;
        price = price.trim();
        if (!isValidPhone(price)) {
            throw new IllegalValueException(MESSAGE_PRICE_CONSTRAINTS);
        }
        this.value = price;
    }

    /**
     * Checks if a given string is a valid menu item price.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(PHONE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Price // instanceof handles nulls
                && this.value.equals(((Price) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
