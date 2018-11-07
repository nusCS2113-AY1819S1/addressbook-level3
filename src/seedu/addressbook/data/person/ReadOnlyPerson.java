package seedu.addressbook.data.person;

import java.util.Set;

import seedu.addressbook.data.tag.Tag;
import seedu.addressbook.login.login;

/**
 * A read-only immutable interface for a Person in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyPerson {
    Person getPerson();
    Name getName();
    Nric getNric();
    Phone getPhone();
    Email getEmail();
    Address getAddress();
    Title getTitle();
    Set<Schedule> getSchedules();
    Set<Associated> getAssociateList();
    /**
     * The returned {@code Set} is a deep copy of the internal {@code Set},
     * changes on the returned list will not affect the person's internal tags.
     */
    Set<Tag> getTags();

    /**
     * Returns true if the values inside this object is same as those of the other (Note: interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyPerson other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getNric().equals(this.getNric())
                && other.getPhone().equals(this.getPhone())
                && other.getEmail().equals(this.getEmail())
                && other.getAddress().equals(this.getAddress())
                && other.getTitle().equals(this.getTitle()) );
                //&& other.getSchedule().equals(this.getSchedule()) );
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsTextShowAll() {
        final StringBuilder builder = new StringBuilder();
        final String detailIsPrivate = "(private) ";
        builder.append(getName()).append(" NRIC: ");
        if (getNric().isPrivate()){
            builder.append(detailIsPrivate);
        }
        builder.append(getNric())
                .append(" Phone: ");
        if (getPhone().isPrivate()) {
            builder.append(detailIsPrivate);
        }
        builder.append(getPhone())
                .append(" Email: ");
        if (getEmail().isPrivate()) {
            builder.append(detailIsPrivate);
        }
        builder.append(getEmail())
                .append(" Address: ");
        if (getAddress().isPrivate()) {
            builder.append(detailIsPrivate);
        }
        if(login.getAccesslevelF() <= getAddress().getAccessLevel()) {
            System.out.println("hide getAsTextShowAll");
            builder.append(getAddress())
                    .append(" Title: ");
        }else{
            builder.append(" HIDDEN").append(" Title: ");
        }
        if (getTitle().isPrivate()) {
            builder.append(detailIsPrivate);
        }
        builder.append(getTitle())
                .append(" Schedule: ");
        /*if (getSchedule().isPrivate()) {
            builder.append(detailIsPrivate);
        }
        builder.append(getSchedule())*/
        for(Schedule schedule : getSchedules()){
            builder.append(schedule);
        }
        builder.append(" Tags: ");
        for (Tag tag : getTags()) {
            builder.append(tag);
        }
        return builder.toString();
    }

    /**
     * Formats a person as text, showing only non-private contact details.
     */
    default String getAsTextHidePrivate() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        if(!getNric().isPrivate()) {
            builder.append(" NRIC: ").append(getNric());
        }
        if (!getPhone().isPrivate()) {
            builder.append(" Phone: ").append(getPhone());
        }
        if (!getEmail().isPrivate()) {
            builder.append(" Email: ").append(getEmail());
        }
        if (!getAddress().isPrivate()) {
            if(login.getAccesslevelF() <= getAddress().getAccessLevel()){
                System.out.println("hide getAsTextHidePrivate");
                builder.append(" Address: ").append(getAddress());
            }else{
                builder.append(" Address: ").append(" HIDDEN");
            }
        }
        if (!getTitle().isPrivate()) {
            builder.append(" Title: ").append(getTitle());
        }
        /*if (!getSchedule().isPrivate()) {
            builder.append(" Schedule: ").append(getSchedule());
        }*/
        builder.append(" Schedule: ");
        for(Schedule schedule : getSchedules()){
            builder.append(schedule);
        }
        builder.append(" Tags: ");
        for (Tag tag : getTags()) {
            builder.append(tag);
        }
        return builder.toString();
    }
}


