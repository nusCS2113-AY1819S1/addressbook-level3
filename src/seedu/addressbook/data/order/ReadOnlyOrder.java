package seedu.addressbook.data.order;

import seedu.addressbook.data.person.*;

import java.util.Date;
import java.util.Map;

/**
 * A read-only immutable interface for an Order in the ordering list.
 */
public interface ReadOnlyOrder {

    Person getCustomer();
    Date getDate();
    double getPrice();
    Map<Dish, Integer> getDishItems();

    /**
     * Returns true if the values inside this object is same as those of the other (Note: interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyOrder other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getCustomer().equals(this.getCustomer()) // state checks here onwards
                && other.getDate().equals(this.getDate())
                && other.getDishItems().equals(this.getDishItems()));
    }

    /**
     * Formats an order as text, showing all details of customer.
     */
    default String getAsTextShowAll() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Customer: \n\t")
                .append(getCustomer().getAsTextShowAll()).append("\n")
                .append("Date: ").append(getDate()).append("\n")
                .append("Price: ").append(getPrice()).append("\n")
                .append("Dishes: \n");
        int i = 0;
        for (Map.Entry<Dish, Integer> m: getDishItems().entrySet()) {
            i++;
            String dishName = m.getKey().getDishName();
            double dishPrice = m.getKey().getDishPrice();
            int quantity = m.getValue();
            builder.append("\t")
                    .append(i).append(". ")
                    .append(dishName)
                    .append(" (").append(dishPrice).append(" SGD) \t")
                    .append("x").append(quantity).append("\n");
        }
        return builder.toString();
    }

    /**
     * Formats an order as text, showing only non-private details of customer.
     */
    default String getAsTextHidePrivate() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Customer: \n\t")
                .append(getCustomer().getAsTextHidePrivate()).append("\n")
                .append("Date: ").append(getDate()).append("\n")
                .append("Price: ").append(getPrice()).append("\n")
                .append("Dishes: \n");
        int i = 0;
        for (Map.Entry<Dish, Integer> m: getDishItems().entrySet()) {
            i++;
            String dishName = m.getKey().getDishName();
            double dishPrice = m.getKey().getDishPrice();
            int quantity = m.getValue();
            builder.append("\t")
                    .append(i).append(". ")
                    .append(dishName)
                    .append(" (").append(dishPrice).append(" SGD) \t")
                    .append("x").append(quantity).append("\n");
        }
        return builder.toString();
    }
}
