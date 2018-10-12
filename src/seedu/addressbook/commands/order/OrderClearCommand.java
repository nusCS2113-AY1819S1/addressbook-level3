package seedu.addressbook.commands.order;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;

/**
 * Clears the order list.
 */
public class OrderClearCommand extends Command {

    public static final String COMMAND_WORD = "clearorder";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Clears order list permanently.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Order list has been cleared!";

    @Override
    public CommandResult execute() {
        rms.clearOrderList();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
