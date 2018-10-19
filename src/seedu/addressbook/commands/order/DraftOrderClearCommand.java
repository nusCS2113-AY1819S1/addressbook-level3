package seedu.addressbook.commands.order;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;

/**
 * Delete all the fields of the draft order.
 */
public class DraftOrderClearCommand extends Command {

    public static final String COMMAND_WORD = "cleardraft";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" +"Delete all the fields of the draft order.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "The draft order is cleared.";

    @Override
    public CommandResult execute() {
        rms.clearDraftOrder();
        String message = MESSAGE_SUCCESS + "\n" + getDraftOrderAsString();
        return new CommandResult(message);
    }
}
