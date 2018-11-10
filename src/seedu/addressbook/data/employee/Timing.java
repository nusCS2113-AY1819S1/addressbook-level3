package seedu.addressbook.data.employee;

import java.util.Objects;

/**
 * Represents a Timing field in the Rms.
 */
public class Timing {

    public final String time;
    public final String date;
    public final boolean isClockIn;


    public Timing(String time, String date, boolean isClockIn) {
        this.time = time;
        this.date = date;
        this.isClockIn = isClockIn;
    }

    public String getDate() {
        return date;
    }

    public boolean isClockIn() {
        return isClockIn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, date, isClockIn);
    }

    @Override
    public String toString() {
        return "Date = " + date + " Time = " + time + " isClockIn = " + isClockIn;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Timing // instanceof handles nulls
                && this.time.equals(((Timing) other).time)
                && this.date.equals(((Timing) other).date)
                && this.isClockIn == ((Timing) other).isClockIn); // state check
    }
}
