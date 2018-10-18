package seedu.addressbook.commands;


import seedu.addressbook.commands.member.*;
import seedu.addressbook.commands.menu.*;
import seedu.addressbook.commands.order.*;
import seedu.addressbook.commands.employee.*;


/**
 * Shows help instructions.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" +"Shows program usage instructions.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_ALL_USAGES = AddCommand.MESSAGE_USAGE
            + "\n" + DeleteCommand.MESSAGE_USAGE
            + "\n" + ClearCommand.MESSAGE_USAGE
            + "\n" + FindCommand.MESSAGE_USAGE
            + "\n" + ListCommand.MESSAGE_USAGE
            + "\n" + ViewCommand.MESSAGE_USAGE
            + "\n" + ViewAllCommand.MESSAGE_USAGE
            + "\n" + HelpCommand.MESSAGE_USAGE

            + "\n" + EmployeeListCommand.MESSAGE_USAGE
            + "\n" + EmployeeAddCommand.MESSAGE_USAGE

            + "\n" + MemberListCommand.MESSAGE_USAGE

            + "\n" + MenuAddCommand.MESSAGE_USAGE
            + "\n" + MenuDeleteCommand.MESSAGE_USAGE
            + "\n" + MenuFindCommand.MESSAGE_USAGE
            + "\n" + MenuListCommand.MESSAGE_USAGE
            + "\n" + MenuViewAllCommand.MESSAGE_USAGE

            + "\n" + OrderAddCommand.MESSAGE_USAGE
            + "\n" + OrderDeleteCommand.MESSAGE_USAGE
            + "\n" + OrderClearCommand.MESSAGE_USAGE
            + "\n" + OrderListCommand.MESSAGE_USAGE
            + "\n" + DraftOrderEditCustomerCommand.MESSAGE_USAGE
            + "\n" + DraftOrderEditDishCommand.MESSAGE_USAGE

            + "\n" + ExitCommand.MESSAGE_USAGE;

    @Override
    public CommandResult execute() {
        return new CommandResult(MESSAGE_ALL_USAGES);
    }
}
