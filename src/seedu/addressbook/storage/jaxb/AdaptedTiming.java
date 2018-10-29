package seedu.addressbook.storage.jaxb;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

import seedu.addressbook.common.Utils;
import seedu.addressbook.data.employee.Timing;
import seedu.addressbook.data.exception.IllegalValueException;

/**
 * JAXB-friendly adapted tag data holder class.
 */
public class AdaptedTiming {

    private String time;
    private String date;
    private boolean isClockIn;


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
        setTime(source.time);
        setDate(source.date);
        setClockIn(source.isClockIn);
    }

    @XmlValue
    public String getTime() {
        return time;
    }

    @XmlAttribute
    public String getDate() {
        return date;
    }

    @XmlAttribute
    public boolean isClockIn() {
        return isClockIn;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setClockIn(boolean clockIn) {
        isClockIn = clockIn;
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
        return Utils.isAnyNull(getTime(), getDate(), isClockIn());
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Timing toModelType() throws IllegalValueException {
        return new Timing(getTime(), getDate(), isClockIn());
    }

}
