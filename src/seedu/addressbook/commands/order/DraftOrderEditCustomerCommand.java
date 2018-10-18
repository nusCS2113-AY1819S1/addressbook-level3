package seedu.addressbook.commands.order;


import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.member.ReadOnlyMember;
import seedu.addressbook.data.menu.ReadOnlyMenus;

/**
 * Add a customer to the draft order using the index of last displayed menu.
 */
public class DraftOrderEditCustomerCommand extends Command {

    public static final String COMMAND_WORD = "editdraftcustomer";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Add a customer to the draft order. "
            + "The customer is identified using the index from the last shown menu list. \n\t"
            + "Parameters: INDEX\n\t"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_EDIT_CUSTOMER_SUCCESS = "Customer successfully edited in the draft order.";

    public DraftOrderEditCustomerCommand(int targetVisibleIndex) {
        super(targetVisibleIndex);
    }


    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyMember target = getTargetMember();
            if (!rms.containsMember(target)) {
                return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
            }
            rms.editDraftOrderCustomer(target);
            String message = MESSAGE_EDIT_CUSTOMER_SUCCESS + "\n" + getDraftOrderAsString();
            return new CommandResult(message);
        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }

}
