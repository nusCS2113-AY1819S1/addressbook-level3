package seedu.addressbook.commands.statistics;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.common.Utils;
import seedu.addressbook.data.menu.ReadOnlyMenus;
import seedu.addressbook.data.order.ReadOnlyOrder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.sun.xml.bind.Util;

/**
 * Lists all food items in the address book to the user.
 */
public class StatsMenuCommand extends Command {
    private Date dateFrom, dateTo;
    private String heading;

    public static final String COMMAND_WORD = "statsmenu";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays statistics information for menu items.\n Select date range from ddmmyyyy to ddmmyyyy with f/ddmmyyyy and t/ddmmyyyy\t"
            + "Example: " + COMMAND_WORD + " [f/24102018] [t/26102018]";


    public StatsMenuCommand(String dateFrom, String dateTo) {
        StringBuilder sb = new StringBuilder();
        sb.append("Displaying menu statistics ");
        if (dateFrom != null) {
            sb.append("from " + dateFrom + " ");
            this.dateFrom = stringToDate(dateFrom);
        }
        else
            this.dateFrom = new Date(0);
        if (dateTo != null) {
            sb.append("until " + dateTo);
            this.dateTo = stringToDate(dateTo);
        }
        else
            this.dateTo = new Date();
        sb.append("\n================\n\n");
        this.heading = sb.toString();
    }

    @Override
    public CommandResult execute() {
        return new StatsCommandResult(heading + getMenuStats());
    }

    private String getMenuStats() {
        StringBuilder sb = new StringBuilder();
        List<ReadOnlyOrder> allOrders = rms.getAllOrders().immutableListView();
        List<ReadOnlyMenus> allMenu = rms.getAllMenus().immutableListView();
        Map<ReadOnlyMenus, Integer> allMenuSales = new TreeMap<>();
        Map<String, ReadOnlyMenus> bestsellers = new HashMap<>();
        Map<String, ReadOnlyMenus> worstsellers = new HashMap<>();

        // For every menu in every order, add the menu and quantity sold into allMenuSales
        for (ReadOnlyOrder order : allOrders) {
            Date orderDate = order.getDate();
            if (orderDate.compareTo(dateFrom) < 0 || orderDate.compareTo(dateTo) > 0) {
                continue;
            }
            // Replace with order.getDishItems() during merge
            Map<ReadOnlyMenus, Integer> dishItems = new HashMap<>();
            dishItems.put(rms.getAllMenus().immutableListView().get(0), 2);
            dishItems.put(rms.getAllMenus().immutableListView().get(1), 3);
            dishItems.put(rms.getAllMenus().immutableListView().get(3), 1);
            // ==========================================
            for (Map.Entry<ReadOnlyMenus, Integer> entry : dishItems.entrySet()) {
                if (!allMenuSales.containsKey(entry.getKey()))
                    allMenuSales.put(entry.getKey(), entry.getValue());
                else
                    allMenuSales.put(entry.getKey(), allMenuSales.get(entry.getKey()) + entry.getValue());
            }
        }

        // Check for menu items with no sales and insert into allMenuSales
        for (ReadOnlyMenus menu: allMenu) {
            if (!allMenuSales.containsKey(menu))
                allMenuSales.put(menu, 0);
        }

        // Sort allMenuSales by quantity sold
        List<Map.Entry<ReadOnlyMenus, Integer>> sortedMenu = Utils.sortByValue(allMenuSales);
        for (int i = sortedMenu.size() - 1; i >= 0; i--) {
            ReadOnlyMenus menu = sortedMenu.get(i).getKey();
            Integer quantity = sortedMenu.get(i).getValue();
            sb.append(menu.getName());
            sb.append(" sold " + quantity + "\n");

            // Replace with menu.type during merge
            String type = "Mains";
            // ==========================================
            if (!bestsellers.containsKey(type) && quantity > 1)
                bestsellers.put(type, menu);
            else
                worstsellers.put(type, menu);
        }

        sb.append("\n\nBest Sellers\n");
        sb.append("=============\n");
        for (Map.Entry<String, ReadOnlyMenus> bestEntry : bestsellers.entrySet()) {
            sb.append(bestEntry.getKey() + ": " + bestEntry.getValue().getName() + "\n");
            sb.append("Total quantity sold: " + allMenuSales.get(bestEntry.getValue()) + "\n");
        }

        sb.append("\n\nUnpopular Items\n");
        sb.append("================\n");
        for (Map.Entry<String, ReadOnlyMenus> worstEntry : worstsellers.entrySet()) {
            sb.append(worstEntry.getKey() + ": " + worstEntry.getValue().getName() + "\n");
            sb.append("Total quantity sold: " + allMenuSales.get(worstEntry.getValue()) + "\n");
        }

        return sb.toString();
    }

    private Date stringToDate(String input) {
        return new Date(Integer.parseInt(input.substring(4)) - 1900, Integer.parseInt(input.substring(2,4)) - 1, Integer.parseInt(input.substring(0,2)));
    }
}
