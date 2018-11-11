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
import seedu.addressbook.commands.menu.MenuRecommendationCommand;
import seedu.addressbook.commands.menu.MenuShowMainMenuCommand;
import seedu.addressbook.commands.menu.MenuViewAllCommand;
import seedu.addressbook.commands.order.DraftOrderClearCommand;
import seedu.addressbook.commands.order.DraftOrderConfirmCommand;
import seedu.addressbook.commands.order.DraftOrderEditCustomerCommand;
import seedu.addressbook.commands.order.DraftOrderEditDishCommand;
import seedu.addressbook.commands.order.DraftOrderEditPointsCommand;
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

    public static final String MESSAGE_ALL_USAGES = "1. " + HelpCommand.MESSAGE_USAGE

            + "\n\n2. " + EmployeeListCommand.MESSAGE_USAGE
            + "\n\n3. " + EmployeeAddCommand.MESSAGE_USAGE

            + "\n\n4. " + MemberListCommand.MESSAGE_USAGE
            + "\n\n5. " + MemberAddCommand.MESSAGE_USAGE

            + "\n\n6. " + MenuAddCommand.MESSAGE_USAGE
            + "\n\n7. " + MenuDeleteCommand.MESSAGE_USAGE
            + "\n\n8. " + MenuFindCommand.MESSAGE_USAGE
            + "\n\n9. " + MenuListCommand.MESSAGE_USAGE
            + "\n\n10. " + MenuShowMainMenuCommand.MESSAGE_USAGE
            + "\n\n11. " + MenuListByTypeCommand.MESSAGE_USAGE
            + "\n\n12. " + MenuRecommendationCommand.MESSAGE_USAGE
            + "\n\n13. " + MenuViewAllCommand.MESSAGE_USAGE
            + "\n\n14. " + MenuClearCommand.MESSAGE_USAGE

            + "\n\n15. " + OrderAddCommand.MESSAGE_USAGE
            + "\n\n16. " + OrderDeleteCommand.MESSAGE_USAGE
            + "\n\n17. " + OrderClearCommand.MESSAGE_USAGE
            + "\n\n18. " + OrderListCommand.MESSAGE_USAGE
            + "\n\n19. " + DraftOrderEditCustomerCommand.MESSAGE_USAGE
            + "\n\n20. " + DraftOrderEditDishCommand.MESSAGE_USAGE
            + "\n\n21. " + DraftOrderEditPointsCommand.MESSAGE_USAGE
            + "\n\n22. " + DraftOrderClearCommand.MESSAGE_USAGE
            + "\n\n23. " + DraftOrderConfirmCommand.MESSAGE_USAGE

            + "\n\n24. " + StatsEmployeeCommand.MESSAGE_USAGE
            + "\n\n25. " + StatsMenuCommand.MESSAGE_USAGE
            + "\n\n26. " + StatsMemberCommand.MESSAGE_USAGE
            + "\n\n27. " + StatsOrderCommand.MESSAGE_USAGE

            + "\n\n28. " + ExitCommand.MESSAGE_USAGE;

    @Override
    public CommandResult execute() {
        return new CommandResult(MESSAGE_ALL_USAGES);
    }
}
