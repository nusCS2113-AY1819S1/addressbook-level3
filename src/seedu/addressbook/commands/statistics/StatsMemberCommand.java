package seedu.addressbook.commands.statistics;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

    public static final String MESSAGE_NO_MEMBERS = "There are no members in the system.";


    @Override
    public CommandResult execute() {
        return new StatsCommandResult(getOverviewStats());
    }

    private String getOverviewStats() {
        StringBuilder res = new StringBuilder();
        List<ReadOnlyMember> allMembers = rms.getAllMembers().immutableListView();
        if (allMembers.isEmpty()) {
            return MESSAGE_NO_MEMBERS;
        }
        MemberDateTable dateTable = new MemberDateTable();
        int[] tierCount = new int[]{0, 0, 0};
        for (ReadOnlyMember member : allMembers) {
            Date signupDate = member.getDate();
            dateTable.addData(signupDate);
            String tier = getTier(member);
            if (tier.equalsIgnoreCase("Bronze")) {
                tierCount[0]++;
            } else if (tier.equalsIgnoreCase("Silver")) {
                tierCount[1]++;
            } else if (tier.equalsIgnoreCase("Gold")) {
                tierCount[2]++;
            }
        }
        res.append("Number of members: " + allMembers.size() + "\n\n");
        res.append("New members this year: " + dateTable.getYearCount(new Date()) + "\n\n");
        res.append("New members this month: " + dateTable.getMonthCount(new Date()) + "\n\n");
        res.append("New members today: " + dateTable.getDayCount(new Date()));
        res.append("\n\n\n");

        res.append("Tier Table\n");
        AsciiTable table = createTierTable();
        String[] values = convertIntArrToStrArr(tierCount);
        table.addRow(values);
        res.append(table.toString());

        return res.toString();
    }

    private AsciiTable createTierTable() {
        String[] headings = new String[]{"Bronze", "Silver", "Gold"};
        return new AsciiTable(headings);
    }

    private String[] convertIntArrToStrArr(int[] in) {
        String listString = Arrays.toString(in);
        return listString.replaceAll("[\\[\\]]", "").split("\\s*,\\s*");
    }

    private String getTier(ReadOnlyMember member) {
        return member.getMemberTier().toString();
    }

}
