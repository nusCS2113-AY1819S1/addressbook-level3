package seedu.addressbook.data.menu;

import seedu.addressbook.data.exception.IllegalValueException;

//@@author SalsabilTasnia
/**
 * Price of a particular menu item in the Restaurant Management System.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(String)}
 */
public class Price {

    public static final String EXAMPLE = "$4.40";
    public static final String MESSAGE_PRICE_CONSTRAINTS = "Price must follow "
            + "the format $A.BC or $A, "
            + "where A is a number of 1-3 digits and B and C are 1 digit each";

    public static final String PRICE_VALIDATION_REGEX = "\\$[1-9][0-9]{0,2}(\\.[0-9]{2})?|\\$0\\.[0-9]{2}|\\$0";

    public final String value;

    /**
     * Validates given phone number.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public Price(String price) throws IllegalValueException {
        String trimmedPrice = price.trim();
        if (!isValidPrice(trimmedPrice)) {
            throw new IllegalValueException(MESSAGE_PRICE_CONSTRAINTS);
        }
        this.value = trimmedPrice;
    }

    /**
     * Convert price value from String to double
     */
    public double convertValueOfPriceToDouble() {
        String doubleValue = this.value.substring(1);
        return Double.parseDouble(doubleValue);
    }

    /**
     * Convert any double into a currency String format
     */
    public static String convertPriceToString(double priceInDouble) {
        String valueAsString = Double.toString(priceInDouble);
        String valueAsPrice = "$" + valueAsString;
        //ensuring the final answer is always returned in 2 decimal places
        int decimalIndex = valueAsPrice.indexOf(".");
        if ((valueAsPrice.substring(decimalIndex)).length() < 3) {
            valueAsPrice = valueAsPrice + "0";
        } else if ((valueAsPrice.substring(decimalIndex)).length() >= 3) {
            valueAsPrice = valueAsPrice.substring(0, decimalIndex + 3);

        }

        return valueAsPrice;
    }


    /**
     * Checks if a given string is a valid menu item price.
     */
    public static boolean isValidPrice(String test) {
        return test.matches(PRICE_VALIDATION_REGEX);
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
