package seedu.addressbook.data.employee;


import java.util.Objects;

/**
 * Represents a Timing field in the Rms.
 */
public class Timing {

    public final String time;
    public final String date;
    public final boolean isClockIn;


    public Timing(String time, String date, boolean isClockIn){
        this.time = time;
        this.date = date;
        this.isClockIn = isClockIn;
    }

    public String getDate() { return date; }

    public boolean isClockIn() {
        return isClockIn;
    }

    @Override
    public int hashCode(){ return Objects.hash(time, date, isClockIn); }

    @Override
    public String toString() {
        return "Date = " + date + " Time = " + time + " isClockIn = " + isClockIn;
    }
}
