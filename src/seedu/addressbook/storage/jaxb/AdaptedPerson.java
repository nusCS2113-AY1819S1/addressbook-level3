package seedu.addressbook.storage.jaxb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;

import seedu.addressbook.common.Utils;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Address;
import seedu.addressbook.data.person.Email;
import seedu.addressbook.data.person.Name;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.Phone;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.tag.Tag;


/**
 * JAXB-friendly adapted person data holder class.
 */
public class AdaptedPerson {

    /**
     * JAXB-friendly adapted contact detail data holder class.
     */
    private static class AdaptedContactDetail {
        private String value;
        private boolean isPrivate;

        @XmlValue
        public String getValue() {
            return value;
        }

        @XmlAttribute(name = "isPrivate", required = true)
        public boolean isPrivate() {
            return isPrivate;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public void setPrivate(boolean aPrivate) {
            isPrivate = aPrivate;
        }
    }

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private AdaptedContactDetail phone;
    @XmlElement(required = true)
    private AdaptedContactDetail email;
    @XmlElement(required = true)
    private AdaptedContactDetail address;

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

        phone = new AdaptedContactDetail();
        phone.setPrivate(source.getPhone().isPrivate());
        phone.setValue(source.getPhone().value);

        email = new AdaptedContactDetail();
        email.setPrivate(source.getEmail().isPrivate());
        email.setValue(source.getEmail().value);

        address = new AdaptedContactDetail();
        address.setPrivate(source.getAddress().isPrivate());
        address.setValue(source.getAddress().value);

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
        for (AdaptedTag tag : tagged) {
            if (tag.isAnyRequiredFieldMissing()) {
                return true;
            }
        }
        // second call only happens if phone/email/address are all not null
        return Utils.isAnyNull(name, phone, email, address)
                || Utils.isAnyNull(phone.getValue(), email.getValue(), address.getValue());
    }

    /**
     * Converts this jaxb-friendly adapted person object into the Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
        final Set<Tag> tags = new HashSet<>();
        for (AdaptedTag tag : tagged) {
            tags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final Phone phone = new Phone(this.phone.getValue(), this.phone.isPrivate());
        final Email email = new Email(this.email.getValue(), this.email.isPrivate());
        final Address address = new Address(this.address.getValue(), this.address.isPrivate());
        return new Person(name, phone, email, address, tags);
    }
}
