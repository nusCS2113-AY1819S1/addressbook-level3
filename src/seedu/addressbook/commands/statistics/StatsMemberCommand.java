package seedu.addressbook.commands.statistics;

import java.util.List;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.member.ReadOnlyMember;

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
        for (ReadOnlyMember member : allMembers) {

        }
        res.append("Number of members: " + allMembers.size());
        appendNewLine(res);
        return res.toString();
    }

    private StringBuilder appendNewLine(StringBuilder sb) {
        sb.append("\n");
        return sb;
    }
}
