package seedu.addressbook.data.menu;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.addressbook.data.tag.Tag;

/**
 * Represents a Menu in the Rms.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Menu implements ReadOnlyMenus, Comparable<Menu> {

    private MenuName name;
    private Price price;
    private Type type;

    private final Set<Tag> tags = new HashSet<>();
    /**
     * Assumption: Every field must be present and not null.
     */
    public Menu(MenuName name, Price price, Type type, Set<Tag> tags) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.tags.addAll(tags);
    }

    /**
     * Copy constructor.
     */
    public Menu(ReadOnlyMenus source) {
        this(source.getName(), source.getPrice(), source.getType(), source.getTags());
    }

    @Override
    public MenuName getName() {
        return name;
    }

    @Override
    public Price getPrice() {
        return price;
    }

    @Override
    public Type getType() {
        return type;
    }


    @Override
    public Set<Tag> getTags() {
        return new HashSet<>(tags);
    }

    /**
     * Replaces this menu item's tags with the tags in {@code replacement}.
     */
    public void setTags(Set<Tag> replacement) {
        tags.clear();
        tags.addAll(replacement);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyMenus // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyMenus) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    @Override
    public int compareTo(Menu target) {
        return this.name.fullName.compareTo(target.name.fullName);
    }
}
