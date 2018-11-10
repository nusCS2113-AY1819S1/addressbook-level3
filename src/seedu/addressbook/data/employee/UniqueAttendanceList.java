package seedu.addressbook.data.employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * A list of attendance timings. Does not allow null elements.
 */
public class UniqueAttendanceList implements Iterable<Attendance> {

    private final List<Attendance> attendanceInternalList = new ArrayList<>();

    /**
     * Constructs empty attendance list.
     */
    public UniqueAttendanceList() {}

    /**
     * Constructs an attendance list with the given attendance timings.
     */
    public UniqueAttendanceList(Attendance... attendances) {
        final List<Attendance> initialTags = Arrays.asList(attendances);
        attendanceInternalList.addAll(initialTags);
    }

    /**
     * Constructs a list from the items in the given collection.
     * @param attendances a collection of attendance
     */
    public UniqueAttendanceList(Collection<Attendance> attendances) {
        attendanceInternalList.addAll(attendances);
    }

    /**
     * Constructs a shallow copy of the list.
     */
    public UniqueAttendanceList(UniqueAttendanceList source) {
        attendanceInternalList.addAll(source.attendanceInternalList);
    }

    /**
     * Adds an attendance to the list.
     */
    public void add(Attendance toAdd) {
        attendanceInternalList.add(toAdd);
    }

    /**
     * Removes the equivalent employee from the list.
     */
    public void remove(Attendance toRemove) {
        attendanceInternalList.remove(toRemove);
    }

    /**
     * Gets index of the specified Attendance object.
     */
    public int getAttendanceIndex(String target) {
        for (Attendance attendance : attendanceInternalList) {
            if (attendance.getName().equals(target)) {
                int index = attendanceInternalList.indexOf(attendance);
                return index;
            }
        }
        return -1;
    }

    /**
     * Gets the Attendance object at target index.
     */
    public Attendance getAttendance(int target) {
        return attendanceInternalList.get(target);
    }

    /**
     * Adds a time field to the attendance for the specified employee in the list.
     */
    public void setAttendance(Attendance target, Attendance newAttendance) {
        int index = attendanceInternalList.indexOf(target);

        attendanceInternalList.set(index, newAttendance);
    }

    @Override
    public Iterator<Attendance> iterator() {
        return attendanceInternalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueAttendanceList // instanceof handles nulls
                && this.attendanceInternalList.equals(((UniqueAttendanceList) other).attendanceInternalList));
    }

    @Override
    public int hashCode() {
        return attendanceInternalList.hashCode();
    }
}
