package seedu.addressbook.commands.statistics;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.common.Utils;
import seedu.addressbook.data.order.ReadOnlyOrder;
import seedu.addressbook.data.statistics.AsciiTable;
import seedu.addressbook.data.statistics.OrderDateTable;

/**
 * Lists all order statistics in the Rms to the user.
 */
public class StatsOrderCommand extends Command {

    public static final String COMMAND_WORD = "statsorder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays statistics information for orders.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_NO_ORDER = "There are no orders in the system.";


    @Override
    public CommandResult execute() {
        return new StatsCommandResult(getOrderStats());
    }

    private String getOrderStats() {
        StringBuilder sb = new StringBuilder();
        List<ReadOnlyOrder> allOrders = rms.getAllOrders().immutableListView();
        if (allOrders.isEmpty()) {
            return MESSAGE_NO_ORDER;
        }

        OrderDateTable dateTable = new OrderDateTable();

        for (ReadOnlyOrder order : allOrders) {
            dateTable.addData(order);
        }
        Date currentDate = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(currentDate);

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
        sb.append("\n\n\n");

        sb.append("Past 12 Months Sales\n");
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentYear = calendar.get(Calendar.YEAR);
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        months = rotateRight(months, 12 - currentMonth);
        AsciiTable table = new AsciiTable(months);
        String[] dataRow = new String[12];
        for (int i = 0; i < 12; i++) {
            calendar.set(Calendar.MONTH, i);
            if (currentMonth <= i) {
                calendar.set(Calendar.YEAR, currentYear - 1);
            }
            dataRow[i] = "$" + Utils.formatCurrency((dateTable.getMonthRevenue(calendar.getTime())));
        }
        dataRow = rotateRight(dataRow, 12 - currentMonth);
        table.addRow(dataRow);
        sb.append(table.toString());
        return sb.toString();
    }

    /**
     * Rotate the columns to the right to display the last 12 months in correct order
     */
    private String[] rotateRight(String[] in, int rotation) {
        String[] out = in.clone();
        for (int x = 0; x <= in.length - 1; x++) {
            out[(x + rotation) % in.length ] = in[x];
        }

        return out;
    }
}
