package seedu.addressbook.data.employee;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents an Attendance list in the Rms.
 * Guarantees: EmployeeName is present, not null and validated
 *             as this command only works when an employee has been properly created.
 */
public class Attendance {
    private String name;

    private final Set<Timing> timings = new LinkedHashSet<>();

    public Attendance(){}

    public Attendance(String name){
        this.name = name.trim();
    }

    public Attendance(String name, Set<Timing> timings){
        this.name = name;
        this.timings.addAll(timings);
    }

    /**
     * Copy constructor.
     */
    public Attendance(Attendance source) {
        this(source.getName(), source.getTimings());
    }

    public String getName() {
        return name;
    }

    public Set<Timing> getTimings() {
        return new LinkedHashSet<>(timings);
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, timings);
    }

    @Override
    public String toString() {
        return getAsTextShowAll();
    }


    /**
     * Formats the attendance as text, showing all check in and check out timings.
     */
    public String getAsTextShowAll() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("Timings: ");
        for (Timing timing : getTimings()) {
            builder.append(timing);
        }
        return builder.toString();
    }
}
