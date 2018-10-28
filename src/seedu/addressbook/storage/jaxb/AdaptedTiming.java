package seedu.addressbook.storage.jaxb;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

import seedu.addressbook.data.employee.Timing;
import seedu.addressbook.data.exception.IllegalValueException;

import seedu.addressbook.common.Utils;

/**
 * JAXB-friendly adapted tag data holder class.
 */
public class AdaptedTiming {

    @XmlValue
    public String time;
    @XmlAttribute
    public String date;
    @XmlAttribute
    public boolean isCheckIn;


    /**
     * No-arg constructor for JAXB use.
     */
    public AdaptedTiming() {}

    /**
     * Converts a given Timing into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created AdaptedTag
     */
    public AdaptedTiming(Timing source) {
        time = source.time;
        date = source.date;
        isCheckIn = source.isCheckIn;
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
        return Utils.isAnyNull(time, date, isCheckIn);
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Timing toModelType() throws IllegalValueException {
        return new Timing(time, date, isCheckIn);
    }
}
