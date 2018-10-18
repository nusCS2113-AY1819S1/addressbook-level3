package seedu.addressbook.data.order;

import seedu.addressbook.data.member.Member;
import seedu.addressbook.data.menu.*;

import java.util.Date;
import java.util.Map;

/**
 * A read-only immutable interface for an Order in the ordering list.
 */
public interface ReadOnlyOrder {

    Member getCustomer();
    Date getDate();
    double getPrice();
    Map<Menu, Integer> getDishItems();

    boolean hasCustomerField();
    boolean hasDishItems();
    boolean hasAllRequiredField();

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
        builder.append("\tCustomer: ").append(getCustomer().getAsTextShowAll()).append("\n")
                .append("\t\tDate: ").append(getDate()).append("\n");
        int i = 0;
        for (Map.Entry<Menu, Integer> m: getDishItems().entrySet()) {
            i++;
            MenuName dishName = m.getKey().getName();
            Price dishPrice = m.getKey().getPrice();
            int quantity = m.getValue();
            builder.append("\t\t\t")
                    .append(i).append(". ")
                    .append(dishName.toString()).append("\t")
                    .append("(").append(dishPrice.toString()).append(" SGD) \t\t")
                    .append("x").append(quantity).append("\n");
        }
        builder.append("\t\tPrice: ").append(getPrice()).append(" SGD\n");
        return builder.toString();
    }

    /**
     * Formats an order as text, showing only non-private details of customer.
     */
    default String getAsTextHidePrivate() {
        final StringBuilder builder = new StringBuilder();
        builder.append("\tCustomer: ").append(getCustomer().getAsTextHidePrivate()).append("\n")
                .append("\t\tDate: ").append(getDate()).append("\n");
        int i = 0;
        for (Map.Entry<Menu, Integer> m: getDishItems().entrySet()) {
            i++;
            MenuName dishName = m.getKey().getName();
            Price dishPrice = m.getKey().getPrice();
            int quantity = m.getValue();
            builder.append("\t\t\t")
                    .append(i).append(". ")
                    .append(dishName.toString()).append("\t")
                    .append("(").append(dishPrice.toString()).append(" SGD) \t\t")
                    .append("x").append(quantity).append("\n");
        }
        builder.append("\t\tPrice: ").append(getPrice()).append(" SGD\n");
        return builder.toString();
    }
}
