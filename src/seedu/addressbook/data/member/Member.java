package seedu.addressbook.data.member;

import seedu.addressbook.data.person.Name;

import java.util.Objects;

public class Member implements ReadOnlyMember {
    private Name name;
    private Points points;

    public Member() {}

    public Member(Name name, Points points) {
        this.name = name;
        this.points = points;
    }

    /**
     * Copy constructor.
     */
    public Member(ReadOnlyMember source) {
        this(source.getName(), source.getPoints());
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Points getPoints() { return points; }

    protected void setName(Name name) {
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
