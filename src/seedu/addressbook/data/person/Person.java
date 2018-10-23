package seedu.addressbook.data.person;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.addressbook.data.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Person implements ReadOnlyPerson {

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Title title;
    private Associated associated;

    private Set<Schedule> schedules = new HashSet<>();

    private final Set<Tag> tags = new HashSet<>();
    /**
     * Assumption: Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Title title, Set<Schedule> schedules, Set<Tag> tags) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.title = title;
        this.schedules.addAll(schedules);
        this.tags.addAll(tags);
        this.associated = new Associated();
    }

    /**
     * Copy constructor.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(), source.getTitle(), source.getSchedules(), source.getTags());
    }
    @Override
    public Person getPerson() {return this;}

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Phone getPhone() {
        return phone;
    }

    @Override
    public Email getEmail() {
        return email;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public Title getTitle() { return title; }

    @Override
    public Set<Schedule> getSchedules() { return new HashSet<>(schedules); }

    @Override
    public Set<Tag> getTags() {
        return new HashSet<>(tags);
    }

    @Override
    public String getAssociateList() throws Associated.NoAssociatesException {
        return associated.getAssociates();
    }

    /**
     * Replaces this person's schedule with the schedule in {@code replacement}.
     */
    public void setSchedule(Set<Schedule> replacement) {
        schedules.clear();
        schedules.addAll(replacement);
    }

    /**
     * Replaces this person's tags with the tags in {@code replacement}.
     */
    public void setTags(Set<Tag> replacement) {
        tags.clear();
        tags.addAll(replacement);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyPerson // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyPerson) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, title, schedules, tags);
    }

    @Override
    public String toString() {
        return getAsTextShowAll();
    }

    public void addAnAssociate(ReadOnlyPerson target) throws Associated.DuplicateAssociationException, Associated.SameTitleException {
        if(this.getTitle().equals(target.getTitle())) throw new Associated.SameTitleException();
        else associated.addToAssociated(target);
    }
}
