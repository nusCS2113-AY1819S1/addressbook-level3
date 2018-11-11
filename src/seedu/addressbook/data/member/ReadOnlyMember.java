package seedu.addressbook.data.member;

import java.util.Date;


/**
 * A read-only immutable interface for a Member in the Restaurant Management System.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyMember {

    MemberName getName();
    MemberEmail getEmail();
    Points getCurrentPoints();
    Date getDate();
    MemberTier getMemberTier();
    int getCurrentPointsValue();
    int getTotalPointsValue();


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
                && other.getName().equals(this.getName())
                && other.getEmail().equals(this.getEmail())); // state checks here onwards
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsTextShowAll() {
        final StringBuilder builder = new StringBuilder();
        final String detailIsPrivate = "(private) ";
        builder.append(getName())
                .append(" | Email: ");
        builder.append(getEmail())
                .append(" | Available Points: ");
        builder.append(getCurrentPoints())
                .append(" | Total Points: ");
        builder.append(getTotalPointsValue())
                .append(" | Tier: ");
        builder.append(getMemberTier().toString())
                .append(" | Date: ");
        builder.append(getDate());
        return builder.toString();
    }

    /**
     * Formats a person as text, showing only non-private contact details.
     */
    default String getAsTextHidePrivate() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        builder.append(" | Email: ").append(getEmail());
        builder.append(" | Available Points: ").append(getCurrentPoints());
        builder.append(" | Total Points: ").append(getTotalPointsValue());
        builder.append(" | Tier: ").append(getMemberTier().toString());
        builder.append(" | Date: ").append(getDate());
        builder.append("\n");
        return builder.toString();
    }

    /**
     * Formats a person as text, showing only non-private contact details and hide membership date.
     */
    default String getAsTextInOrderList() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        builder.append(" | Tier: ").append(getMemberTier().toString());
        return builder.toString();
    }
}
