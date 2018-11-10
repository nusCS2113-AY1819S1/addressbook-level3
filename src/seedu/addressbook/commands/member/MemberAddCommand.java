package seedu.addressbook.commands.member;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.member.Member;
import seedu.addressbook.data.member.MemberEmail;
import seedu.addressbook.data.member.MemberName;
import seedu.addressbook.data.member.ReadOnlyMember;
import seedu.addressbook.data.member.UniqueMemberList;


/**
 * Adds a member to the member list.
 */
public class MemberAddCommand extends Command {

    public static final String COMMAND_WORD = "addmember";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Adds a member to the Member list. "
            + "Contact details can be marked private by prepending 'p' to the prefix.\n\n"
            + "Parameters: NAME e/EMAIL \n"
            + "Example: " + COMMAND_WORD
            + " John Doe e/Example123@gmail.com";

    public static final String MESSAGE_SUCCESS = "New member added: %1$s";
    public static final String MESSAGE_DUPLICATE_MEMBER = "This member already exists in the member list";

    private final Member toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public MemberAddCommand(String name, String email) throws IllegalValueException {
        this.toAdd = new Member(
                new MemberName(name),
                new MemberEmail(email)
        );
    }

    public MemberAddCommand(Member toAdd) {
        this.toAdd = toAdd;
    }

    public ReadOnlyMember getMember() {
        return toAdd;
    }

    @Override
    public CommandResult execute() {
        try {
            rms.addMember(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueMemberList.DuplicateMemberException dpe) {
            return new CommandResult(MESSAGE_DUPLICATE_MEMBER);
        }
    }

}
