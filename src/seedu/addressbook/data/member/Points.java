package seedu.addressbook.data.member;

import java.awt.*;

public class Points {
    private String points;

    public String value;
    public Points(){
        this.points = "0";
        this.value = this.points;
    }

    /**
     * Converts the price into points and adds in to the existing points for the member
     * @param price of the order being made
     * @return updated points
     */
    protected Points updatePoints(double price) {
        double value = Double.parseDouble(this.value);
        double result = value + price;
        this.value = Double.toString(result);
        return this;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Points // instanceof handles nulls
                && this.value.equals(((Points) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

//    public boolean isPrivate() {
//        return isPrivate;
//    }
}
