package seedu.addressbook.commands.statistics;

import java.util.Date;
import java.util.List;


import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.common.Utils;
import seedu.addressbook.data.order.ReadOnlyOrder;
import seedu.addressbook.data.statistics.OrderDateTable;

/**
 * Lists all food items in the address book to the user.
 */
public class StatsOrderCommand extends Command {

    public static final String COMMAND_WORD = "statsorder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays statistics information for orders.\n\t"
            + "Example: " + COMMAND_WORD;


    @Override
    public CommandResult execute() {
        return new StatsCommandResult(getOrderStats());
    }

    private String getOrderStats() {
        StringBuilder sb = new StringBuilder();
        List<ReadOnlyOrder> allOrders = rms.getAllOrders().immutableListView();
        if (allOrders.isEmpty())
            return "There are no orders in the system.";

        OrderDateTable dateTable = new OrderDateTable();

        for (ReadOnlyOrder order : allOrders) {
            dateTable.addData(order);
        }
        Date currentDate = new Date();

        sb.append("This year's statistics\n");
        sb.append("========================\n");
        sb.append("Number of orders: " + Integer.toString(dateTable.getYearCount(currentDate)) + "\n");
        sb.append("Revenue: $" + Utils.formatCurrency(dateTable.getYearRevenue(currentDate)) + "\n\n");
        sb.append("This month's statistics\n");
        sb.append("========================\n");
        sb.append("Number of orders: " + Integer.toString(dateTable.getMonthCount(currentDate)) + "\n");
        sb.append("Revenue: $" + Utils.formatCurrency(dateTable.getMonthRevenue(currentDate)) + "\n\n");
        sb.append("Today's statistics\n");
        sb.append("========================\n");
        sb.append("Number of orders: " + Integer.toString(dateTable.getDayCount(currentDate)) + "\n");
        sb.append("Revenue: $" + Utils.formatCurrency(dateTable.getDayRevenue(currentDate)));

        return sb.toString();
    }
}
