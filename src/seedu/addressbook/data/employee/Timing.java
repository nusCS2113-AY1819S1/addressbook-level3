package seedu.addressbook.data.employee;


import java.util.Objects;

/**
 * Represents a Timing field in the Rms.
 */
public class Timing {

    public final String time;
    public final String date;
    public final boolean isCheckIn;


    public Timing(String time, String date, boolean isCheckIn){
        this.time = time;
        this.date = date;
        this.isCheckIn = isCheckIn;
    }

    public String getDate() { return date; }

    public boolean isCheckIn() {
        return isCheckIn;
    }

    @Override
    public int hashCode(){ return Objects.hash(time, date, isCheckIn); }

    @Override
    public String toString() {
        return "Date = " + date + " Time = " + time + " isCheckIn = " + isCheckIn;
    }
}
