package seedu.addressbook.commands.statistics;

import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.common.Utils;
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
        return new StatsCommandResult(getOverviewStats());
    }

    private String getOverviewStats() {
        StringBuilder res = new StringBuilder();
        List<ReadOnlyMember> allMembers = rms.getAllMembers().immutableListView();

        MemberDateTable dateTable = new MemberDateTable();

        for (ReadOnlyMember member : allMembers) {
            // Replace with member.getDate() during merge
            Date temp = new Date();
            // ==========================================
            dateTable.addData(temp);
        }
        res.append("Number of members: " + allMembers.size() + "\n\n");
        res.append("New members this year: " + dateTable.getYearCount(new Date()) + "\n\n");
        res.append("New members this month: " + dateTable.getMonthCount(new Date()) + "\n\n");
        res.append("New members today: " + dateTable.getDayCount(new Date()));
        return res.toString();
    }

}
