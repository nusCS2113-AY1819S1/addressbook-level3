package seedu.addressbook.data.member;

import java.util.Date;


/**
 * A read-only immutable interface for a Member in the Restaurant Management System.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyMember {

    MemberName getName();
    Points getPoints();
    Points updatePoints(double price);
    void updatePointsAndTier(double price);
    Date getDate();
    MemberTier getMemberTier();
    MemberTier updateTier(Points points);


    /**
     * The returned {@code Set} is a deep copy of the internal {@code Set},
     * changes on the returned list will not affect the person's internal tags.
     */
    // Set<Tag> getTags();

    /**
     * Returns true if the values inside this object is same as those of the other
     * (Note: interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyMember other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName())); // state checks here onwards
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsTextShowAll() {
        final StringBuilder builder = new StringBuilder();
        final String detailIsPrivate = "(private) ";
        builder.append(getName())
                .append(" Points: ");
        builder.append(getPoints())
                .append(" Date: ");
        builder.append(getDate())
                .append(" Tier: ");
        builder.append(getMemberTier().toString());
        return builder.toString();
    }

    /**
     * Formats a person as text, showing only non-private contact details.
     */
    default String getAsTextHidePrivate() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        builder.append(" Points: ").append(getPoints());
        builder.append(" Date: ").append(getDate());
        builder.append(" Tier: ").append(getMemberTier().toString());
        return builder.toString();
    }
}
