package seedu.addressbook.commands.menu;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;

/**
 * Clears the menu list.
 */
public class MenuClearCommand extends Command {

    public static final String COMMAND_WORD = "clearmenu";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Clears menu items permanently.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Menu has been cleared!";

    @Override
    public CommandResult execute() {
        rms.clearMenu();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
