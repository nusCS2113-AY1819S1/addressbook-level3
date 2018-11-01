package seedu.addressbook.commands;

import seedu.addressbook.commands.employee.EmployeeAddCommand;
import seedu.addressbook.commands.employee.EmployeeListCommand;
import seedu.addressbook.commands.member.MemberAddCommand;
import seedu.addressbook.commands.member.MemberListCommand;
import seedu.addressbook.commands.menu.MenuAddCommand;
import seedu.addressbook.commands.menu.MenuClearCommand;
import seedu.addressbook.commands.menu.MenuDeleteCommand;
import seedu.addressbook.commands.menu.MenuFindCommand;
import seedu.addressbook.commands.menu.MenuListByTypeCommand;
import seedu.addressbook.commands.menu.MenuListCommand;
import seedu.addressbook.commands.menu.MenuShowMainMenuCommand;
import seedu.addressbook.commands.menu.MenuViewAllCommand;
import seedu.addressbook.commands.order.DraftOrderClearCommand;
import seedu.addressbook.commands.order.DraftOrderConfirmCommand;
import seedu.addressbook.commands.order.DraftOrderEditCustomerCommand;
import seedu.addressbook.commands.order.DraftOrderEditDishCommand;
import seedu.addressbook.commands.order.OrderAddCommand;
import seedu.addressbook.commands.order.OrderClearCommand;
import seedu.addressbook.commands.order.OrderDeleteCommand;
import seedu.addressbook.commands.order.OrderListCommand;
import seedu.addressbook.commands.statistics.StatsEmployeeCommand;
import seedu.addressbook.commands.statistics.StatsMemberCommand;
import seedu.addressbook.commands.statistics.StatsMenuCommand;
import seedu.addressbook.commands.statistics.StatsOrderCommand;


/**
 * Shows help instructions.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Shows program usage instructions.\n\t"
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
            + "\n" + MemberAddCommand.MESSAGE_USAGE

            + "\n" + MenuAddCommand.MESSAGE_USAGE
            + "\n" + MenuDeleteCommand.MESSAGE_USAGE
            + "\n" + MenuFindCommand.MESSAGE_USAGE
            + "\n" + MenuListCommand.MESSAGE_USAGE
            + "\n" + MenuShowMainMenuCommand.MESSAGE_USAGE
            + "\n" + MenuListByTypeCommand.MESSAGE_USAGE
            + "\n" + MenuViewAllCommand.MESSAGE_USAGE
            + "\n" + MenuClearCommand.MESSAGE_USAGE

            + "\n" + OrderAddCommand.MESSAGE_USAGE
            + "\n" + OrderDeleteCommand.MESSAGE_USAGE
            + "\n" + OrderClearCommand.MESSAGE_USAGE
            + "\n" + OrderListCommand.MESSAGE_USAGE
            + "\n" + DraftOrderEditCustomerCommand.MESSAGE_USAGE
            + "\n" + DraftOrderEditDishCommand.MESSAGE_USAGE
            + "\n" + DraftOrderClearCommand.MESSAGE_USAGE
            + "\n" + DraftOrderConfirmCommand.MESSAGE_USAGE

            + "\n" + StatsEmployeeCommand.MESSAGE_USAGE
            + "\n" + StatsMenuCommand.MESSAGE_USAGE
            + "\n" + StatsMemberCommand.MESSAGE_USAGE
            + "\n" + StatsOrderCommand.MESSAGE_USAGE

            + "\n" + ExitCommand.MESSAGE_USAGE;

    @Override
    public CommandResult execute() {
        return new CommandResult(MESSAGE_ALL_USAGES);
    }
}
