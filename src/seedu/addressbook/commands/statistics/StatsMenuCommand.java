package seedu.addressbook.commands.statistics;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.common.Utils;
import seedu.addressbook.data.menu.ReadOnlyMenus;
import seedu.addressbook.data.order.ReadOnlyOrder;
import seedu.addressbook.data.statistics.AsciiTable;
import seedu.addressbook.data.statistics.QuantityRevenuePair;

/**
 * Lists all menu statistics in the Rms to the user.
 */
public class StatsMenuCommand extends Command {

    public static final String COMMAND_WORD = "statsmenu";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays statistics information for menu items.\n"
            + "Select date range from ddmmyyyy to ddmmyyyy with f/ddmmyyyy and t/ddmmyyyy\n\t"
            + "Format: " + COMMAND_WORD + " [f/ddmmyyyy] [t/ddmmyyyy]\n\t"
            + "Example: " + COMMAND_WORD + " f/12122017 t/11112018\n\t"
            + "         " + COMMAND_WORD + " f/01012018";

    public static final String MESSAGE_NO_ORDER = "There are no orders in the system to calculate menu stats.";

    private Date dateFrom;
    private Date dateTo;
    private String heading;

    public StatsMenuCommand(String dateFrom, String dateTo) {
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        StringBuilder sb = new StringBuilder();
        sb.append("Displaying menu statistics ");
        if (dateFrom != null) {
            this.dateFrom = stringToDate(dateFrom);
            sb.append("from " + dateFormat.format(this.dateFrom) + " ");
        } else {
            this.dateFrom = new Date(0);
        }
        if (dateTo != null) {
            this.dateTo = stringToDate(dateTo);
            sb.append("until " + dateFormat.format(this.dateTo));
        } else {
            this.dateTo = new Date();
        }
        sb.append("\n================\n\n");
        this.heading = sb.toString();
    }

    @Override
    public CommandResult execute() {
        if (getMenuStats().equalsIgnoreCase(MESSAGE_NO_ORDER)) {
            return new StatsCommandResult(MESSAGE_NO_ORDER);
        } else {
            return new StatsCommandResult(heading + getMenuStats());
        }
    }

    private String getMenuStats() {
        StringBuilder sb = new StringBuilder();
        List<ReadOnlyOrder> allOrders = rms.getAllOrders().immutableListView();
        if (allOrders.isEmpty()) {
            return MESSAGE_NO_ORDER;
        }
        List<ReadOnlyMenus> allMenu = rms.getAllMenus().immutableListView();
        Map<ReadOnlyMenus, QuantityRevenuePair> allMenuSales = new TreeMap<>();
        Map<String, ReadOnlyMenus> bestsellers = new HashMap<>();
        Map<String, ReadOnlyMenus> worstsellers = new HashMap<>();

        // For every menu in every order, add the menu and quantity sold into allMenuSales
        for (ReadOnlyOrder order : allOrders) {
            Date orderDate = order.getDate();
            if (orderDate.compareTo(dateFrom) < 0 || orderDate.compareTo(dateTo) > 0) {
                continue;
            }
            Map<ReadOnlyMenus, Integer> dishItems = order.getDishItems();
            // ==========================================
            for (Map.Entry<ReadOnlyMenus, Integer> entry : dishItems.entrySet()) {
                if (!allMenuSales.containsKey(entry.getKey())) {
                    allMenuSales.put(entry.getKey(),
                            new QuantityRevenuePair(entry.getValue(),
                                    entry.getKey().getPrice().convertValueOfPricetoDouble()));
                } else {
                    allMenuSales.put(entry.getKey(),
                            allMenuSales.get(entry.getKey()).addData(entry.getValue(),
                                    entry.getKey().getPrice().convertValueOfPricetoDouble()));
                }
            }
        }

        // Check for menu items with no sales and insert into allMenuSales
        for (ReadOnlyMenus menu: allMenu) {
            if (!allMenuSales.containsKey(menu)) {
                allMenuSales.put(menu, new QuantityRevenuePair());
            }
        }

        // Sort allMenuSales by quantity sold
        List<Map.Entry<ReadOnlyMenus, QuantityRevenuePair>> sortedMenu = Utils.sortByValue(allMenuSales);
        for (int i = sortedMenu.size() - 1; i >= 0; i--) {
            ReadOnlyMenus menu = sortedMenu.get(i).getKey();
            int quantity = sortedMenu.get(i).getValue().getQuantity();
            sb.append(menu.getName());
            sb.append(" sold " + quantity + "\n");

            // Replace with menu.type during merge
            String type = menu.getType().value;
            // ==========================================
            if (!bestsellers.containsKey(type) && quantity > 0) {
                bestsellers.put(type, menu);
            } else if (quantity < 100) {
                worstsellers.put(type, menu);
            }
        }

        sb.append("\n\nBest Sellers\n");
        sb.append(toTable(bestsellers, allMenuSales));

        sb.append("Unpopular Items\n");
        sb.append(toTable(worstsellers, allMenuSales));

        return sb.toString();
    }

    /**
     * Parse the data into a table and return the table as a String
     */
    private String toTable(Map<String, ReadOnlyMenus> in, Map<ReadOnlyMenus, QuantityRevenuePair> allMenuSales) {
        String[] tableHeadings = {"Type", "Name", "Quantity Sold", "Sales Revenue"};
        AsciiTable table = new AsciiTable(tableHeadings);
        for (Map.Entry<String, ReadOnlyMenus> worstEntry : in.entrySet()) {
            String type = worstEntry.getKey();
            String menuName = worstEntry.getValue().getName().toString();
            int quantity = allMenuSales.get(worstEntry.getValue()).getQuantity();
            String revenue = Utils.formatCurrency(allMenuSales.get(worstEntry.getValue()).getRevenue());
            String[] rowData = {type, menuName, Integer.toString(quantity), "$" + revenue};
            table.addRow(rowData);
        }
        return table.toString();
    }

    /**
     * Convert a date String into a Date object
     */
    private Date stringToDate(String input) {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Integer.parseInt(input.substring(4)),
                Integer.parseInt(input.substring(2, 4)) - 1,
                Integer.parseInt(input.substring(0, 2)));
        return calendar.getTime();
    }

    public static Map<String, ReadOnlyMenus> getBs(List<ReadOnlyOrder> allOrders, List<ReadOnlyMenus> allMenu) {
        if (allOrders.isEmpty()) {
            return null;
        }
        Map<ReadOnlyMenus, QuantityRevenuePair> allMenuSales = new TreeMap<>();
        Map<String, ReadOnlyMenus> bestsellers = new HashMap<>();

        // For every menu in every order, add the menu and quantity sold into allMenuSales
        for (ReadOnlyOrder order : allOrders) {
            Map<ReadOnlyMenus, Integer> dishItems = order.getDishItems();
            // ==========================================
            for (Map.Entry<ReadOnlyMenus, Integer> entry : dishItems.entrySet()) {
                if (!allMenuSales.containsKey(entry.getKey())) {
                    int quantity = entry.getValue();
                    double revenue = entry.getKey().getPrice().convertValueOfPricetoDouble();
                    QuantityRevenuePair qr = new QuantityRevenuePair(quantity, revenue);
                    allMenuSales.put(entry.getKey(), qr);
                } else {
                    int quantity = entry.getValue();
                    double revenue = entry.getKey().getPrice().convertValueOfPricetoDouble();
                    QuantityRevenuePair qr = allMenuSales.get(entry.getKey());
                    qr.addData(quantity, revenue);
                    allMenuSales.put(entry.getKey(), qr);
                }
            }
        }

        // Check for menu items with no sales and insert into allMenuSales
        for (ReadOnlyMenus menu: allMenu) {
            if (!allMenuSales.containsKey(menu)) {
                allMenuSales.put(menu, new QuantityRevenuePair());
            }
        }

        // Sort allMenuSales by quantity sold
        List<Map.Entry<ReadOnlyMenus, QuantityRevenuePair>> sortedMenu = Utils.sortByValue(allMenuSales);
        for (int i = sortedMenu.size() - 1; i >= 0; i--) {
            ReadOnlyMenus menu = sortedMenu.get(i).getKey();
            int quantity = sortedMenu.get(i).getValue().getQuantity();
            String type = menu.getType().value;
            // ==========================================
            if (!bestsellers.containsKey(type) && quantity > 0) {
                bestsellers.put(type, menu);
            }
        }

        return bestsellers;
    }
}
