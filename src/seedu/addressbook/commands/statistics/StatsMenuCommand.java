package seedu.addressbook.commands.statistics;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.common.Utils;
import seedu.addressbook.data.menu.ReadOnlyMenus;
import seedu.addressbook.data.order.ReadOnlyOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.sun.xml.bind.Util;

/**
 * Lists all food items in the address book to the user.
 */
public class StatsMenuCommand extends Command {

    public static final String COMMAND_WORD = "statsmenu";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays statistics information for menu items.\n\t"
            + "Example: " + COMMAND_WORD;


    @Override
    public CommandResult execute() {
        return new StatsCommandResult(getMenuStats());
    }

    private String getMenuStats() {
        StringBuilder sb = new StringBuilder();
        List<ReadOnlyOrder> allOrders = rms.getAllOrders().immutableListView();
        Map<ReadOnlyMenus, Integer> allMenu = new TreeMap<>();

        for (ReadOnlyOrder order : allOrders) {
            // Replace with order.getDishItems() during merge
            Map<ReadOnlyMenus, Integer> dishItems = new HashMap<>();
            dishItems.put(rms.getAllMenus().immutableListView().get(0), 2);
            dishItems.put(rms.getAllMenus().immutableListView().get(1), 3);
            dishItems.put(rms.getAllMenus().immutableListView().get(3), 1);
            // ==========================================
            for (Map.Entry<ReadOnlyMenus, Integer> entry : dishItems.entrySet()) {
                if (!allMenu.containsKey(entry.getKey()))
                    allMenu.put(entry.getKey(), entry.getValue());
                else
                    allMenu.put(entry.getKey(), allMenu.get(entry.getKey()) + entry.getValue());
            }
        }
        List<Map.Entry<ReadOnlyMenus, Integer>> sortedMenu = Utils.sortByValue(allMenu);
        for (int i = sortedMenu.size() - 1; i >= 0; i--) {
            ReadOnlyMenus menu = sortedMenu.get(i).getKey();
            Integer quantity = sortedMenu.get(i).getValue();
            sb.append(menu.getName());
            sb.append(" sold " + quantity);
            Utils.appendNewLine(sb);
        }
        return sb.toString();
    }
}
