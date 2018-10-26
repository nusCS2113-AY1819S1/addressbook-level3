package seedu.addressbook.commands.statistics;

import java.util.List;
import java.util.Date;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.member.ReadOnlyMember;
import seedu.addressbook.data.statistics.AsciiTable;
import seedu.addressbook.data.statistics.MemberDateTable;

/**
 *
 */
public class StatsMemberCommand extends Command {

    public static final String COMMAND_WORD = "statsmember";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays statistics information for members.\n\t"
            + "Example: " + COMMAND_WORD;


    @Override
    public CommandResult execute() {
        return new StatsCommandResult(getOverviewStats());
    }

    private String getOverviewStats() {
        StringBuilder res = new StringBuilder();
        List<ReadOnlyMember> allMembers = rms.getAllMembers().immutableListView();
        if (allMembers.isEmpty())
            return "There are no members in the system.";
        MemberDateTable dateTable = new MemberDateTable();

        for (ReadOnlyMember member : allMembers) {
            // Replace with member.getDate() during merge
            Date signupDate = member.getDate();
            // ==========================================
            dateTable.addData(signupDate);
        }
        res.append("Number of members: " + allMembers.size() + "\n\n");
        res.append("New members this year: " + dateTable.getYearCount(new Date()) + "\n\n");
        res.append("New members this month: " + dateTable.getMonthCount(new Date()) + "\n\n");
        res.append("New members today: " + dateTable.getDayCount(new Date()));
        res.append("\n\n\n");

        // Replace with list of tiers during merge
        res.append("Tier Table");
        String[] headings = new String[]{"Bronze", "Silver", "Gold", "Platinum", "Diamond"};
        // =======================================
        AsciiTable table = new AsciiTable(headings);
        String[] values = new String[]{"12", "6", "4", "2", "1"};
        table.addRow(values);
        res.append(table.toString());

        return res.toString();
    }

}
