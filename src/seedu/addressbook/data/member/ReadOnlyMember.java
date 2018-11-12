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
     * Formats a member as text, showing all details.
     */
    default String getAsText() {
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
     * Formats a member as text, showing only contact details and member tier.
     */
    default String getAsTextInOrderList() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        builder.append(" | Email: ").append(getEmail());
        builder.append(" | Tier: ").append(getMemberTier().toString());
        return builder.toString();
    }
}
