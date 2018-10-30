package seedu.addressbook.storage.jaxb;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import seedu.addressbook.data.employee.Attendance;
import seedu.addressbook.data.employee.Timing;
import seedu.addressbook.data.exception.IllegalValueException;

import seedu.addressbook.common.Utils;


/**
 * JAXB-friendly adapted tag data holder class.
 */
public class AdaptedAttendance {

    @XmlElement(required = true)
    private String name;
    @XmlAttribute (required = true)
    private boolean isClockedIn;

    @XmlElement
    private List<AdaptedTiming> timings = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public AdaptedAttendance() {}

    /**
     * Converts a given Attendance into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created AdaptedAttendance
     */
    public AdaptedAttendance(Attendance source) {
        name = source.getName();
        isClockedIn = source.getClockedIn();

        timings = new ArrayList<>();
        for (Timing timing : source.getTimings()) {
            timings.add(new AdaptedTiming(timing));
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
        for (AdaptedTiming timing : timings) {
            if (timing.isAnyRequiredFieldMissing()) {
                return true;
            }
        }
        return Utils.isAnyNull(name);
    }

    /**
     * Converts this jaxb-friendly adapted attendance object into the Attendance object.
     */
    public Attendance toModelType() throws IllegalValueException {
        final String name = this.name;
        final boolean isClockedIn = this.isClockedIn;

        final Set<Timing> timingSet = new LinkedHashSet<>();
        for (AdaptedTiming timing : timings) {
            timingSet.add(timing.toModelType());
        }

        return new Attendance(name, isClockedIn, timingSet);
    }
}
