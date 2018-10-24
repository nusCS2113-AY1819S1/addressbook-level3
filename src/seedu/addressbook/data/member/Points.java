package seedu.addressbook.data.member;

import seedu.addressbook.data.exception.IllegalValueException;

import java.awt.*;

import static seedu.addressbook.common.Messages.MESSAGE_NEGATIVE_POINTS;

public class Points {

    private int value;

    public Points(){
        this.value = 0;
    }

    public Points(int points){
        this.value = points;
    }

//    public final String MESSAGE_NEGATIVE_POINTS = "Update points cannot result in negative points.";
    /**
     * Converts the price into points and adds in to the existing points for the member
     * @param price of the order being made
     * @return updated points
     */
    protected Points updatePoints(double price) {
        try {
            this.value += ((int) price) / 10;
            if(this.value < 0) {
                throw new IllegalValueException(MESSAGE_NEGATIVE_POINTS);
            }
            return this;
        } catch (IllegalValueException e) {
            this.value = 0;
            return this;
        }

    }

    public int getPoints() {
        return this.value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Points // instanceof handles nulls
                && this.toString().equals(((Points) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

//    public boolean isPrivate() {
//        return isPrivate;
//    }
}
