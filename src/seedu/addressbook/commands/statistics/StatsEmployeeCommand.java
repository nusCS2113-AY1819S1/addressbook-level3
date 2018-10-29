package seedu.addressbook.commands.statistics;

import java.util.List;
import java.util.Set;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.employee.Attendance;
import seedu.addressbook.data.employee.ReadOnlyEmployee;
import seedu.addressbook.data.employee.Timing;
import seedu.addressbook.data.employee.UniqueAttendanceList;
import seedu.addressbook.data.menu.ReadOnlyMenus;
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
        return new StatsCommandResult(getEmployeeStats());
    }

    private String getEmployeeStats() {
        StringBuilder res = new StringBuilder();
        List<ReadOnlyEmployee> allEmployees = rms.getAllEmployees().immutableListView();
        UniqueAttendanceList allAttendance = rms.getAllAttendance();
        if (allEmployees.isEmpty())
            return "There are no employees in the system.";
        res.append("Number of employees: " + allEmployees.size() + "\n\n");
        res.append("Currently on duty employees: ");
        String[] headings = new String[]{"Name", "Position", "Clocked in"};
        AsciiTable onduty = new AsciiTable(headings);
        int count = 0;
        for (ReadOnlyEmployee emp : allEmployees) {
            String name = emp.getName().fullName;
            Attendance attendance = allAttendance.getAttendance(allAttendance.getAttendanceIndex(name));
            if (attendance.getClockedIn()) {
                Set<Timing> timing = attendance.getTimings();
                String[] data = new String[]{name, emp.getPosition().value, ((Timing) timing.toArray()[ timing.size()-1 ]).time};
                onduty.addRow(data);
                count++;
            }
        }
        res.append(count + "\n");
        if (count != 0)
            res.append(onduty.toString());
        res.append("\n");

        return res.toString();
    }
}
