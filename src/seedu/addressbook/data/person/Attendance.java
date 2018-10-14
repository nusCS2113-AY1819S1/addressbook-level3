package seedu.addressbook.data.person;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Attendance implements Printable {

    /** Represents a map that links dates(kay) to attendance(value)*/
    private Map<String, Boolean> attendance_map = new HashMap<>();

    /** Method to add attendance */
    public void addAttendance(String date, Boolean isPresent) {
        if (date.equals("0")){
            date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        }
        // TODO: v1.3 Add in feature such that if(date == found){print MESSAGE_DUPLICATE_ATTENDANCE from update attendance command
        // TODO: and ask user if want to replace attendance};
        attendance_map.put(date, isPresent);
    }

    /** Method to reiterate person's attendance */
    public String viewAttendance() {
        String output = "";
        for (Map.Entry entry : attendance_map.entrySet()) {
            output += entry.getKey() + " -> " + entry.getValue() + "\n";
        }
        return output;
    }

    @Override
    public String getPrintableString(boolean showPrivate) {
        return "{}";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Attendance // instanceof handles nulls
                && this.attendance_map.equals(((Attendance) other).attendance_map)); // state check
    }
}

