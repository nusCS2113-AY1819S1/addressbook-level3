package seedu.addressbook.data.member;

import static seedu.addressbook.common.Messages.MESSAGE_NEGATIVE_POINTS;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents the number of membership point of a Member in the member list.
 */
public class Points {

    public static final int EARNED_POINTS_PER_DOLLAR = 10;
    public static final int REDEEMED_POINTS_PER_DOLLAR = 100;

    private int value;

    public Points() {
        this.value = 0;
    }

    public Points(int points) {
        this.value = points;
    }

    // public final String MESSAGE_NEGATIVE_POINTS = "Update points cannot result in negative points.";
    /**
     * Converts the price into points and adds in to the existing points for the member
     * @param price of the order being made
     * @return updated points
     */
    protected Points updatePoints(double price, int pointsToRedeem) {
        try {
            if (this.value < pointsToRedeem) {
                throw new IllegalValueException(MESSAGE_NEGATIVE_POINTS);
            }
            this.value -= pointsToRedeem;
            this.value += getEarnedPointsValue(price);
            return this;
        } catch (IllegalValueException e) {
            return this;
        }

    }

    public int getPoints() {
        return this.value;
    }

    public void setPoints(int points) {
        this.value = points;
    }

    public double getRedeemedDiscount() {
        return (((double) value) / REDEEMED_POINTS_PER_DOLLAR);
    }

    public static int getEarnedPointsValue(double price) {
        return (int) (price * EARNED_POINTS_PER_DOLLAR);
    }

    public static int getRedeemedPointsValue(double price) {
        return (int) (price * REDEEMED_POINTS_PER_DOLLAR);
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
    /*
    public boolean isPrivate() {
        return isPrivate;
    }
    */
}
