package seedu.addressbook.data.member;

import static seedu.addressbook.common.Messages.MESSAGE_MAXIMUM_POINTS_EXCEEDED;
import static seedu.addressbook.common.Messages.MESSAGE_MAXIMUM_TOTAL_POINTS_EXCEEDED;
import static seedu.addressbook.common.Messages.MESSAGE_NEGATIVE_POINTS;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents the number of membership point of a Member in the member list.
 */
public class Points {

    public static final int EARNED_POINTS_PER_DOLLAR = 10;
    public static final int REDEEMED_POINTS_PER_DOLLAR = 100;
    public static final int MAX_CURRENT_POINTS = 2000000000;
    public static final int MAX_TOTAL_POINTS = 2000000000;

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
            if (currentPoints > MAX_CURRENT_POINTS) {
                throw new IllegalValueException(MESSAGE_MAXIMUM_POINTS_EXCEEDED);
            } else if (totalPoints > MAX_TOTAL_POINTS) {
                throw new IllegalValueException(MESSAGE_MAXIMUM_TOTAL_POINTS_EXCEEDED);
            }
            return this;
        } catch (IllegalValueException e) {
            if (e.getMessage() == MESSAGE_NEGATIVE_POINTS) {
                return this;
            } else if (e.getMessage() == MESSAGE_MAXIMUM_POINTS_EXCEEDED) {
                this.currentPoints = MAX_TOTAL_POINTS;
                this.totalPoints = MAX_TOTAL_POINTS;
                return this;
            } else if (e.getMessage() == MESSAGE_MAXIMUM_TOTAL_POINTS_EXCEEDED) {
                this.totalPoints = MAX_TOTAL_POINTS;
                return this;
            }
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

    /**
     * checks the points are valid
     * @param points
     * @return false if points are negative, true if points are positive or zero
     */
    public boolean isValidPoints(int points) {
        if (points >= 0) {
            return true;
        }
        return false;
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
        return Integer.toString(currentPoints);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Points // instanceof handles nulls
                && this.toString().equals(((Points) other).currentPoints)); // state check
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
