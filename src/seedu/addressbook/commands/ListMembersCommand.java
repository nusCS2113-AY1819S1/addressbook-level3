package seedu.addressbook.commands;

import seedu.addressbook.data.member.ReadOnlyMember;


import java.util.List;

public class ListMembersCommand extends Command{
    public static final String COMMAND_WORD = "listmembers";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays all members in the address book as a list with index numbers.\n\t"
            + "Example: " + COMMAND_WORD;


    @Override
    public CommandResult execute() {
        List<ReadOnlyMember> allMembers = rms.getAllMembers().immutableListView();
        return new MemberCommandResult(getMessageForMemberListShownSummary(allMembers), allMembers);
    }
}
