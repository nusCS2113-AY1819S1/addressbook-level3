package seedu.addressbook.commands.menu;

import java.util.List;
import java.util.Map;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.commands.statistics.StatsMenuCommand;
import seedu.addressbook.data.menu.ReadOnlyMenus;
import seedu.addressbook.data.order.ReadOnlyOrder;

/**
 * Lists recommended food items in the menu list to the user.
 */
public class MenuRecommendationCommand extends Command {

    public static final String COMMAND_WORD = "recommendations";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays the best seller items as the recommendations of the month.\n\t"
            + "Example: " + COMMAND_WORD;

    /**
     * Displays all the best selling items of each category in the menu, if the items in those categories are sold
     * The best selling items are obtained from the statistics that determine the best and worst selling items of
     * the month
     *
     * @return the best selling items of each category
     */
    private String displayRecommendedItems() {
        List<ReadOnlyMenus> allMenus = rms.getAllMenus().immutableListView();
        List<ReadOnlyOrder> allOrders = rms.getAllOrders().immutableListView();
        Map<String, ReadOnlyMenus> map = StatsMenuCommand.getBs(allOrders, allMenus);
        final StringBuilder builder = new StringBuilder();
        builder.append("Recommendations of the month are:\n\n");
        for (Map.Entry m: map.entrySet()) {
            builder.append(m.getKey()).append(" : \n\t").append(m.getValue()).append("\n\n");
        }

        return builder.toString();

    }

    @Override
    public CommandResult execute() {
        return new MenuCommandResult(displayRecommendedItems());
    }
}
