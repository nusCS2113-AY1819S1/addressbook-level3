package seedu.addressbook.commands.statistics;

import java.util.List;
import java.util.Set;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.employee.Attendance;
import seedu.addressbook.data.employee.ReadOnlyEmployee;
import seedu.addressbook.data.employee.Timing;
import seedu.addressbook.data.employee.UniqueAttendanceList;
import seedu.addressbook.data.statistics.AsciiTable;

/**
 * Lists all food items in the address book to the user.
 */
public class StatsEmployeeCommand extends Command {

    public static final String COMMAND_WORD = "statsemp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays statistics information for employees.\n\t"
            + "Example: " + COMMAND_WORD;


    @Override
    public CommandResult execute() {
        return new StatsCommandResult(getEmployeeOverviewStats());
    }

    private String getEmployeeOverviewStats() {
        StringBuilder res = new StringBuilder();
        List<ReadOnlyEmployee> allEmployees = rms.getAllEmployees().immutableListView();
        UniqueAttendanceList allAttendance = rms.getAllAttendance();
        if (allEmployees.isEmpty()) {
            return "There are no employees in the system.";
        }
        res.append("Number of employees: " + allEmployees.size() + "\n\n");
        res.append("Currently on duty employees: ");
        String[] headings = new String[]{"Name", "Position", "Clocked in"};
        AsciiTable onDuty = new AsciiTable(headings);
        headings = new String[]{"Name", "Position", "Activity"};
        AsciiTable recentAttendance = new AsciiTable(headings);
        int count = 0;
        for (ReadOnlyEmployee emp : allEmployees) {
            String name = emp.getName().fullName;
            Attendance attendance = allAttendance.getAttendance(allAttendance.getAttendanceIndex(name));
            Set<Timing> timings = attendance.getTimings();
            Object[] timingArray = timings.toArray();
            int offset = 0;
            if (attendance.getClockedIn()) {
                offset = 1;
                String[] data;
                data = new String[]{name, emp.getPosition().value, ((Timing) timingArray[timingArray.length - 1]).time};
                onDuty.addRow(data);
                count++;
            }

            int j = 0;
            for (int i = timingArray.length - 1 - offset; i >= 1 && j < 3; i -= 2) {
                String[] data = new String[]{" ", " ", "  "};

                if (j == 0) {
                    data[0] = emp.getName().fullName;
                    data[1] = emp.getPosition().value;
                }
                Timing outTiming = (Timing) timingArray[i];
                Timing inTiming = (Timing) timingArray[i - 1];
                data[2] = inTiming.date + " " + inTiming.time + " - " + outTiming.date + " " + outTiming.time;
                j++;
                recentAttendance.addRow(data);
            }

        }
        res.append(count + "\n");
        if (count != 0) {
            res.append(onDuty.toString());
        }
        res.append("\n\n");
        res.append("All employees recent attendance\n");
        res.append(recentAttendance.toString());
        res.append("\n\n");

        return res.toString();
    }
}
