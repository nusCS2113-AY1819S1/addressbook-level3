package seedu.addressbook.commands.order;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.member.ReadOnlyMember;

/**
 * Edit the customer field of the draft order.
 * The customer is retrieved with the index of last displayed member list.
 */
public class DraftOrderEditCustomerCommand extends Command {

    public static final String COMMAND_WORD = "draftcustomer";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Add a customer to the draft order. "
            + "The customer is identified using the index from the last shown menu list. \n\t"
            + "Parameters: INDEX\n\t"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Customer is edited in the draft order.";

    public DraftOrderEditCustomerCommand(int targetVisibleIndex) {
        super(targetVisibleIndex);
    }


    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyMember target = getTargetMember();
            if (!rms.containsMember(target)) {
                return new CommandResult(Messages.MESSAGE_MEMBER_NOT_IN_RMS);
            }
            rms.editDraftOrderPoints(0);
            rms.editDraftOrderCustomer(target);
            String message = MESSAGE_SUCCESS + "\n" + getDraftOrderAsString();
            return new CommandResult(message);
        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);
        }
    }

}
