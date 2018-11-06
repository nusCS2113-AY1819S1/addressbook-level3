package seedu.addressbook.storage.jaxb;

import seedu.addressbook.common.Utils;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.*;
import seedu.addressbook.data.tag.Tag;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * JAXB-friendly adapted person data holder class.
 */
public class AdaptedPerson {

    private static class AdaptedContactDetail {
        @XmlValue
        public String value;
        @XmlAttribute(required = true)
        public boolean isPrivate;
    }

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private AdaptedContactDetail nric;
    @XmlElement(required = true)
    private AdaptedContactDetail phone;
    @XmlElement(required = true)
    private AdaptedContactDetail email;
    @XmlElement(required = true)
    private AdaptedContactDetail address;
    @XmlElement(required = true)
    private AdaptedContactDetail title;

    @XmlElement
    private List<AdaptedSchedule> scheduled = new ArrayList<>();

    @XmlElement
    private List<AdaptedAssociate> associates = new ArrayList<>();

    @XmlElement
    private List<AdaptedTag> tagged = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public AdaptedPerson() {}


    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created AdaptedPerson
     */
    public AdaptedPerson(ReadOnlyPerson source) {
        name = source.getName().fullName;

        nric = new AdaptedContactDetail();
        nric.isPrivate = source.getNric().isPrivate();
        nric.value = source.getNric().NRIC;

        phone = new AdaptedContactDetail();
        phone.isPrivate = source.getPhone().isPrivate();
        phone.value = source.getPhone().value;

        email = new AdaptedContactDetail();
        email.isPrivate = source.getEmail().isPrivate();
        email.value = source.getEmail().value;

        address = new AdaptedContactDetail();
        address.isPrivate = source.getAddress().isPrivate();
        address.value = source.getAddress().value;

        title = new AdaptedContactDetail();
        title.isPrivate = source.getTitle().isPrivate();
        title.value = source.getTitle().value;

        scheduled = new ArrayList<>();
        for (Schedule schedule : source.getSchedules()) {
            scheduled.add(new AdaptedSchedule(schedule));
        }
        associates = new ArrayList<>();
        for (Associated associated : source.getAssociateList()) {
            associates.add((new AdaptedAssociate(associated)));
        }
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new AdaptedTag(tag));
        }
    }

    /**
     * Returns true if any required field is missing.
     *
     * JAXB does not enforce (required = true) without a given XML schema.
     * Since we do most of our validation using the data class constructors, the only extra logic we need
     * is to ensure that every xml element in the document is present. JAXB sets missing elements as null,
     * so we check for that.
     */
    public boolean isAnyRequiredFieldMissing() {
        for (AdaptedSchedule schedule : scheduled) {
            if (schedule.isAnyRequiredFieldMissing()) {
                return true;
            }
        }
        for (AdaptedTag tag : tagged) {
            if (tag.isAnyRequiredFieldMissing()) {
                return true;
            }
        }
        // second call only happens if phone/email/address are all not null
        return Utils.isAnyNull(name, phone, email, address, title)
                || Utils.isAnyNull(phone.value, email.value, address.value, title.value);
    }

    /**
     * Converts this jaxb-friendly adapted person object into the Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
        final Set<Schedule> schedules = new HashSet<>();
        for (AdaptedSchedule schedule : scheduled) {
            schedules.add(schedule.toModelType());
        }
        final Set<Tag> tags = new HashSet<>();
        for (AdaptedTag tag : tagged) {
            tags.add(tag.toModelType());
        }
        final Set<Associated> associateds = new HashSet<>();
        for (AdaptedAssociate associate : associates) {
            associateds.add(associate.toModelType());
        }
        final Name name = new Name(this.name);
        final Nric nric = new Nric(this.nric.value, this.nric.isPrivate);
        final Phone phone = new Phone(this.phone.value, this.phone.isPrivate);
        final Email email = new Email(this.email.value, this.email.isPrivate);
        final Address address = new Address(this.address.value, this.address.isPrivate);
        final Title title = new Title(this.title.value, this.title.isPrivate);
        return new Person(name, nric, phone, email, address, title, schedules, tags, associateds);
    }
}
