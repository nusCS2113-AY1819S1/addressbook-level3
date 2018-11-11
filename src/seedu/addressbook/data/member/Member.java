package seedu.addressbook.data.member;

import java.util.Date;
import java.util.Objects;

import seedu.addressbook.data.exception.IllegalValueException;


/**
 * Represents a Member in the member list.
 */
public class Member implements ReadOnlyMember {

    public static final String EMPTY_NAME_STRING = "baLpcbImfjsHuIhCnEKM";
    public static final String EMPTY_EMAIL_STRING = "Example2018@rms.com";

    private MemberName name;
    private MemberEmail email;
    private Points points;
    private Date date;
    private MemberTier tier;

    public Member() {
        try {
            this.name = new MemberName(EMPTY_NAME_STRING);
            this.email = new MemberEmail(EMPTY_EMAIL_STRING);
        } catch (IllegalValueException ie) {
            this.name = null;
            this.email = null;
        }
        this.points = new Points();
        this.date = new Date();
        this.tier = new MemberTier();
    }

    public Member(MemberName name, MemberEmail email) {
        this.name = name;
        this.email = email;
        this.points = new Points();
        this.date = new Date();
        this.tier = new MemberTier();
    }

    public Member(MemberName name, MemberEmail email, Points points, Date date, MemberTier tier) {
        this.name = name;
        this.email = email;
        this.points = points;
        this.date = date;
        this.tier = tier;
    }
    /**
     * Copy constructor.
     */
    public Member(ReadOnlyMember source) {
        this(source.getName(), source.getEmail(), source.getCurrentPoints(), source.getDate(), source.getMemberTier());
    }

    @Override
    public MemberName getName() {
        return name;
    }

    public MemberEmail getEmail() {
        return email;
    }

    @Override
    public Points getCurrentPoints() {
        return points;
    }

    @Override
    public int getCurrentPointsValue() {
        return points.getCurrentPoints();
    }

    @Override
    public int getTotalPointsValue() {
        return points.getTotalPoints();
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public MemberTier getMemberTier() {
        return tier;
    }

    public void setPoints(int value) {
        points.setCurrentPoints(value);
    }

    public Points updatePoints(double price, int pointsToRedeem) {
        return this.points.updatePoints(price, pointsToRedeem);
    }

    public MemberTier updateTier(Points points) {
        return tier.updateTier(points);
    }



    /**
     * Updates the points and membership tier of the member
     * @param price
     */
    public void updatePointsAndTier(double price, int pointsToRedeem) {
        Points newPoints = updatePoints(price, pointsToRedeem);
        updateTier(newPoints);
    }

    protected void setName(MemberName name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyMember // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyMember) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, email);
    }

    @Override
    public String toString() {
        return getAsText();
    }
}
