package seedu.addressbook.data.member;

import seedu.addressbook.data.person.Name;

import java.util.Objects;

public class Member implements ReadOnlyMember {
    private Name name;

    public Member() {};

    public Member(Name name) {
        this.name = name;
    }

    /**
     * Copy constructor.
     */
    public Member(ReadOnlyMember source) {
        this(source.getName());
    }

    @Override
    public Name getName() {
        return name;
    }


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
