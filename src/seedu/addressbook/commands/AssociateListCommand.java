package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.person.Associated;
import seedu.addressbook.data.person.ReadOnlyPerson;

import java.util.HashSet;
import java.util.Set;

public class AssociateListCommand extends Command {
    public static final String COMMAND_WORD = "associatelist";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "List the name of the persons associated with the person at the target index of most recent list.\n\t"
            + "Parameters: INDEX\n\t"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Associates: ";

    public static final String MESSAGE_NO_ASSOCIATES = "No associates!";

    public AssociateListCommand(int targetVisibleIndex) {
        super(targetVisibleIndex);
    }


    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyPerson target = getTargetPerson();
             Set<Associated> associateList= target.getAssociateList();
             saveHistory(COMMAND_WORD);
            return new CommandResult(MESSAGE_SUCCESS + associateList.toString());
        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }
}
