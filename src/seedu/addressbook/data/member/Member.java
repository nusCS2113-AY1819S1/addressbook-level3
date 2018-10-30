package seedu.addressbook.data.member;

import java.util.Date;
import java.util.Objects;

import seedu.addressbook.data.exception.IllegalValueException;


/**
 * Represents a Member in the member list.
 */
public class Member implements ReadOnlyMember {

    public static final String EMPTY_NAME_STRING = "EMPTY";

    private MemberName name;
    private Points points;
    private Date date;
    private MemberTier tier;

    public Member() {
        try {
            this.name = new MemberName(EMPTY_NAME_STRING);
        } catch (IllegalValueException ie) {
            this.name = null;
        }
        this.points = new Points();
        this.date = new Date();
        this.tier = new MemberTier();
    }

    public Member(MemberName name) {
        this.name = name;
        this.points = new Points();
        this.date = new Date();
        this.tier = new MemberTier();
    }

    public Member(MemberName name, Points points, Date date, MemberTier tier) {
        this.name = name;
        this.points = points;
        this.date = date;
        this.tier = tier;
    }
    /**
     * Copy constructor.
     */
    public Member(ReadOnlyMember source) {
        this(source.getName(), source.getPoints(), source.getDate(), source.getMemberTier());
    }

    @Override
    public MemberName getName() {
        return name;
    }

    @Override
    public Points getPoints() {
        return points;
    }

    public Points updatePoints(double price) {
        return this.points.updatePoints(price);
    }

    public Date getDate() {
        return date;
    }

    public MemberTier getMemberTier() {return tier; }

    public MemberTier updateTier(Points points) {return tier.updateTier(points); }

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
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return getAsTextShowAll();
    }
}
