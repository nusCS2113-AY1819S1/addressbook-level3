package seedu.addressbook.data.member;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents the number of membership point of a Member in the member list.
 */
public class Points {

    public static final int EARNED_POINTS_PER_DOLLAR = 10;
    public static final int REDEEMED_POINTS_PER_DOLLAR = 100;
    public static final String MESSAGE_NEGATIVE_POINTS = "Update points cannot result in negative points.";

    private int currentPoints;
    private int totalPoints;



    public Points() {
        this.currentPoints = 0;
        this.totalPoints = 0;
    }

    public Points (int pointsToRedeem) {
        this.currentPoints = pointsToRedeem;
        this.totalPoints = pointsToRedeem;
    }
    public Points(int currentPoints, int totalPoints) {
        this.currentPoints = currentPoints;
        this.totalPoints = totalPoints;
    }

    /**
     * Converts the price into points and adds in to the existing points for the member
     * @param price of the order being made
     * @return updated points
     */
    protected Points updatePoints(double price, int pointsToRedeem) {
        try {
            if (this.currentPoints < pointsToRedeem) {
                throw new IllegalValueException(MESSAGE_NEGATIVE_POINTS);
            }
            final int pointsEarned = getEarnedPointsValue(price);
            this.currentPoints -= pointsToRedeem;
            this.currentPoints += pointsEarned;
            this.totalPoints += pointsEarned;
            return this;
        } catch (IllegalValueException e) {
            return this;
        }

    }

    public int getCurrentPoints() {
        return this.currentPoints;
    }

    public int getTotalPoints() {
        return this.totalPoints;
    }

    public void setCurrentPoints(int points) {
        this.currentPoints = points;
    }

    public double getRedeemedDiscount() {
        return (((double) currentPoints) / REDEEMED_POINTS_PER_DOLLAR);
    }

    public static int getEarnedPointsValue(double price) {
        return (int) (price * EARNED_POINTS_PER_DOLLAR);
    }

    public static int getRedeemedPointsValue(double price) {
        return (int) (price * REDEEMED_POINTS_PER_DOLLAR);
    }

    @Override
    public String toString() {
        return Integer.toString(currentPoints) + Integer.toString(totalPoints);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Points // instanceof handles nulls
                && this.currentPoints == ((Points) other).currentPoints
                && this.totalPoints == ((Points) other).totalPoints); // state check
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

}
