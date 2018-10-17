package seedu.addressbook.data.person;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.addressbook.data.account.Account;
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
    private Optional<Account> account = Optional.empty();
    private Fees fees;
    private final Set<Tag> tags = new HashSet<>();
    private Attendance attendance;

    /**
     * Assumption: Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.attendance = new Attendance();
        this.fees = new Fees();
    }

    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Account account) {
        this(name, phone, email, address, tags);
        this.account = Optional.ofNullable(account);
    }

    /**
     * Copy constructor.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(), source.getTags());
    }

    /**
     * Only update the fees when called in AddFeesCommand
     * @param fees
     */
    public void setFees(Fees fees) {
        this.fees = fees;
    }

    /**
     * Replaces this person's tags with the tags in {@code replacement}.
     */
    public void setTags(Set<Tag> replacement) {
        tags.clear();
        tags.addAll(replacement);
    }

    public void setAccount(Account account) {
        this.account = Optional.ofNullable(account);
    }

    public void removeAccount() {
        this.account = Optional.empty();
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
    public Fees getFees() {
        return fees;
    }

    @Override
    public Set<Tag> getTags() {
        return new HashSet<>(tags);
    }

    @Override
    public Optional<Account> getAccount() {
        return account;
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

    /** Has a boolean to check if the date is a duplicate **/
    public boolean updateAttendanceMethod(String date, Boolean isPresent, Boolean overWrite) {
        boolean duplicateDate = attendance.addAttendance(date, isPresent, overWrite);
        return duplicateDate;
    }

    public String viewAttendanceMethod() {
        return attendance.viewAttendance();
    }

    public Attendance getAttendance() {
        return attendance;
    }

    /** Replaces the attendance if there is already a duplicate **/
    public boolean replaceAttendanceMethod(String date, Boolean isPresent, Boolean overWrite) {
        boolean noDuplicateDate = !attendance.addAttendance(date, isPresent, overWrite);
        return noDuplicateDate;
    }

}
