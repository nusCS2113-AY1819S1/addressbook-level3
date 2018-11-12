package seedu.addressbook.commands.member;

import java.util.List;

import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.member.ReadOnlyMember;

/**
 * Represents the result of an order command execution.
 */
public class MemberCommandResult extends CommandResult {

    public MemberCommandResult(String feedbackToUser, List<? extends ReadOnlyMember> relevantMembers) {
        super(feedbackToUser, null, null, relevantMembers, null, null);
    }
}
