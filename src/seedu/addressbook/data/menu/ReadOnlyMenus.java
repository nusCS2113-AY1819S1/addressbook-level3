package seedu.addressbook.data.menu;

import java.util.Set;

import seedu.addressbook.data.tag.Tag;

/**
 * A read-only immutable interface for a Menu Item in the Rms.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyMenus {

    MenuName getName();
    Price getPrice();
    Type getType();

    /**
     * The returned {@code Set} is a deep copy of the internal {@code Set},
     * changes on the returned list will not affect the menu item's internal tags.
     */
    Set<Tag> getTags();

    /**
     * Returns true if the values inside this object is same as those of the other
     * (Note: interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyMenus other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()));
    }

    /**
     * Formats a menu item as text, showing all details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName()).append(" | Price ").append(getPrice()).append(" | Type: ").append(getType());
        builder.append(" | Tags: ");
        for (Tag tag : getTags()) {
            builder.append(tag);
        }
        return builder.toString();
    }
}
