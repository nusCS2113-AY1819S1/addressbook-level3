package seedu.addressbook.commands.statistics;

import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.member.ReadOnlyMember;
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
        return new StatsCommandResult(getMemberStats());
    }

    private String getMemberStats() {
        StringBuilder res = new StringBuilder();
        List<ReadOnlyMember> allMembers = rms.getAllMembers().immutableListView();

        MemberDateTable dateTable = new MemberDateTable();

        for (ReadOnlyMember member : allMembers) {
            // Replace with member.getDate() during merge
            Date temp = new Date();
            // ==========================================
            dateTable.addData(temp);
        }
        res.append("Number of members: " + allMembers.size());
        appendNewLine(res);
        appendNewLine(res);
        res.append("New members this year: " + dateTable.getYearCount(new Date()));
        appendNewLine(res);
        appendNewLine(res);
        res.append("New members this month: " + dateTable.getMonthCount(new Date()));
        appendNewLine(res);
        appendNewLine(res);
        res.append("New members today: " + dateTable.getDayCount(new Date()));
        return res.toString();
    }

    private StringBuilder appendNewLine(StringBuilder sb) {
        sb.append("\n");
        return sb;
    }
}
