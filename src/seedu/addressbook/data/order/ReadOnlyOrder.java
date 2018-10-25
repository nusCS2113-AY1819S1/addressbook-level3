package seedu.addressbook.data.order;

import seedu.addressbook.data.member.ReadOnlyMember;
import seedu.addressbook.data.menu.*;

import java.util.Date;
import java.util.Map;

/**
 * A read-only immutable interface for an Order in the ordering list.
 */
public interface ReadOnlyOrder {

    ReadOnlyMember getCustomer();
    Date getDate();
    double getPrice();
    Map<ReadOnlyMenus, Integer> getDishItems();

    boolean hasCustomerField();
    boolean hasDishItems();

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
        if (hasCustomerField()) {
            builder.append("\tCustomer: ").append(getCustomer().getAsTextShowAll()).append("\n\t");
        }
        builder.append("\tDate: ").append(getDate());
        int i = 0;
        for (Map.Entry<ReadOnlyMenus, Integer> m: getDishItems().entrySet()) {
            i++;
            builder.append("\n");
            MenuName dishName = m.getKey().getName();
            Price dishPrice = m.getKey().getPrice();
            int quantity = m.getValue();
            builder.append("\t\t\t")
                    .append(i).append(". ")
                    .append(dishName.toString()).append("\t")
                    .append("($").append(dishPrice.toString()).append(") \t\t")
                    .append("x").append(quantity);
        }
        builder.append("\n\t\tPrice: $");
        builder.append(Price.convertPricetoString(getPrice()));
        return builder.toString();
    }

    /**
     * Formats an order as text, showing only non-private details of customer.
     */
    default String getAsTextHidePrivate() {
        final StringBuilder builder = new StringBuilder();
        if (hasCustomerField()) {
            builder.append("\tCustomer: ").append(getCustomer().getAsTextHidePrivate()).append("\n\t");
        }
        builder.append("\tDate: ").append(getDate());
        int i = 0;
        for (Map.Entry<ReadOnlyMenus, Integer> m: getDishItems().entrySet()) {
            i++;
            builder.append("\n");
            MenuName dishName = m.getKey().getName();
            Price dishPrice = m.getKey().getPrice();
            int quantity = m.getValue();
            builder.append("\t\t\t")
                    .append(i).append(". ")
                    .append(dishName.toString()).append("\t")
                    .append("($").append(dishPrice.toString()).append(") \t\t")
                    .append("x").append(quantity);
        }
        builder.append("\n\t\tPrice: $");
        builder.append(Price.convertPricetoString(getPrice()));
        return builder.toString();
    }

    /**
     * Formats an draft order as text. Null fields are shown as empty.
     */
    default String getDraftDetailsAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append("\t\tCustomer: ");
        if (hasCustomerField()) {
            builder.append(getCustomer().getAsTextShowAll());
        } else {
            builder.append("<empty>");
        }
        builder.append("\n\t\tDishes: ");
        if (hasDishItems()) {
            int i = 0;
            for (Map.Entry<ReadOnlyMenus, Integer> m: getDishItems().entrySet()) {
                i++;
                builder.append("\n");
                MenuName dishName = m.getKey().getName();
                Price dishPrice = m.getKey().getPrice();
                int quantity = m.getValue();
                builder.append("\t\t\t")
                        .append(i).append(". ")
                        .append(dishName.toString()).append("\t")
                        .append("($").append(dishPrice.toString()).append(") \t\t")
                        .append("x").append(quantity);
            }
        } else {
            builder.append("<empty>");
        }
        return builder.toString();
    }
}
