package seedu.addressbook.data.person;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private final Set<Tag> tags = new HashSet<>();
    /**
     * Assumption: Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
    }

    /**
     * Copy constructor.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(), source.getTags());
    }

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
    public Set<Tag> getTags() {
        return new HashSet<>(tags);
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
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return getAsTextShowAll();
    }

    /**
     * Formats a person as text, showing only non-private contact details.
     */
    @Override
    public String getAsTextHidePrivate() {
        final StringBuilder builder = new StringBuilder();

        List<Printable> printables = new ArrayList<>();
        printables.add(name);
        if(!phone.isPrivate()){
            printables.add(phone);
        }
        if(!email.isPrivate()){
            printables.add(phone);
        }
        if(!address.isPrivate()){
            printables.add(phone);
        }
        builder.append(getPrintableString(
                printables.toArray(new Printable[printables.size()])));

        builder.append(" Tags: ");
        for (Tag tag : getTags()) {
            builder.append(tag);
        }
        return builder.toString();
    }

    /**
     * Returns a concatenated version of the printable strings of each object.
     */
    private String getPrintableString(Printable... printables) {
        String result = Arrays.stream(printables)
                .map(printable -> printable.getPrintableString())
                .collect(Collectors.joining(" "));
        return result;
    }
}
