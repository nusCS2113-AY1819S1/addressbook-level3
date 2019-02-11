package seedu.addressbook.commands.order;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;

//@@author px1099
/**
 * Delete all the fields of the draft order.
 */
public class DraftOrderClearCommand extends Command {

    public static final String COMMAND_WORD = "cleardraft";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Delete all the fields of the draft order.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "The draft order is cleared.\n%1$s";

    @Override
    public CommandResult execute() {
        rms.clearDraftOrder();
        String message = String.format(MESSAGE_SUCCESS, getDraftOrderAsString());
        return new CommandResult(message);
    }
}
