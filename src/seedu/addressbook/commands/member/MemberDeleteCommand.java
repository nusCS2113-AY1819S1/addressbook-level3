package seedu.addressbook.commands.member;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.member.ReadOnlyMember;
import seedu.addressbook.data.member.UniqueMemberList.MemberNotFoundException;

/**
 * Deletes a member identified using it's last displayed index from the member list.
 */
public class MemberDeleteCommand extends Command {

    public static final String COMMAND_WORD = "delmember";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Deletes the member identified by the index number used in the last member listing.\n\t"
            + "Parameters: INDEX\n\t"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_MEMBER_SUCCESS = "Deleted member: %1$s";


    public MemberDeleteCommand(int targetVisibleIndex) {
        super(targetVisibleIndex);
    }


    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyMember target = getTargetMember();
            rms.removeMember(target);
            return new CommandResult(String.format(MESSAGE_DELETE_MEMBER_SUCCESS, target));

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);
        } catch (MemberNotFoundException enfe) {
            return new CommandResult(Messages.MESSAGE_MEMBER_NOT_IN_RMS);
        }
    }

}
