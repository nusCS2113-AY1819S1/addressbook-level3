package seedu.addressbook.data.order;

import java.util.Date;
import java.util.Map;

import seedu.addressbook.common.Utils;
import seedu.addressbook.data.member.ReadOnlyMember;
import seedu.addressbook.data.menu.Price;
import seedu.addressbook.data.menu.ReadOnlyMenus;

/**
 * A read-only immutable interface for an Order in the ordering list.
 */
public interface ReadOnlyOrder {

    int MAX_DISH_NAME_DISPLAY_LENGTH = 24;
    int MAX_DISH_PRICE_DISPLAY_LENGTH = 12;
    int MAX_DISH_QUANTITY_DIGITS = 3;
    String MULTIPLY_SIGN = "x ";

    ReadOnlyMember getCustomer();
    Date getDate();
    double getPrice();
    double getOriginalPrice();
    int getPoints();
    int getMaxPointsRedeemable();
    int getEarnedPointsValue();
    Map<ReadOnlyMenus, Integer> getDishItems();

    boolean hasCustomerField();
    boolean hasDishItems();
    boolean hasPoints();


    /**
     * Returns true if the values inside this object is same as those of the other
     * (Note: interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyOrder other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getCustomer().equals(this.getCustomer()) // state checks here onwards
                && other.getDate().equals(this.getDate())
                && other.getDishItems().equals(this.getDishItems()));
    }

    /**
     * Formats an order as text, showing all details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        if (hasCustomerField()) {
            builder.append("Customer: ").append(getCustomer().getAsTextInOrderList());
            builder.append("\n\t").append("   ");
        }
        builder.append("Date: ").append(getDate());
        int i = 0;
        for (Map.Entry<ReadOnlyMenus, Integer> m: getDishItems().entrySet()) {
            i++;
            String dishName = m.getKey().getName().toString();
            String dishPrice = "(" + m.getKey().getPrice().toString() + ")";
            String dishQuantity = "" + m.getValue();
            dishQuantity = MULTIPLY_SIGN
                    + Utils.blankSpace(MAX_DISH_QUANTITY_DIGITS - dishQuantity.length())
                    + dishQuantity;
            builder.append("\n\t\t");
            builder.append(i).append(". ");
            builder.append(dishName);
            if (dishName.length() > MAX_DISH_NAME_DISPLAY_LENGTH) {
                builder.append("\n\t\t").append("   ");
                builder.append(Utils.blankSpace(MAX_DISH_NAME_DISPLAY_LENGTH));
            } else {
                builder.append(Utils.blankSpace(dishName, MAX_DISH_NAME_DISPLAY_LENGTH));
            }
            builder.append(dishPrice);
            builder.append(Utils.blankSpace(dishPrice, MAX_DISH_PRICE_DISPLAY_LENGTH));
            builder.append(dishQuantity);
        }
        if (hasCustomerField()) {
            builder.append("\n\t   Redeemed points:\t").append(getPoints());
        }
        builder.append("\n\t   Total price:\t\t");
        builder.append(Price.convertPricetoString(getPrice()));
        return builder.toString();
    }

    default String getAsTextAfterAdd() {
        final StringBuilder builder = new StringBuilder();
        if (hasCustomerField()) {
            builder.append("\tCustomer: ").append(getCustomer().getAsTextInOrderList()).append("\n");
        }
        builder.append("\tDate: ").append(getDate());
        int i = 0;
        for (Map.Entry<ReadOnlyMenus, Integer> m: getDishItems().entrySet()) {
            i++;
            String dishName = m.getKey().getName().toString();
            String dishPrice = "(" + m.getKey().getPrice().toString() + ")";
            String dishQuantity = "" + m.getValue();
            dishQuantity = MULTIPLY_SIGN
                    + Utils.blankSpace(MAX_DISH_QUANTITY_DIGITS - dishQuantity.length())
                    + dishQuantity;
            builder.append("\n\t\t");
            builder.append(i).append(". ");
            builder.append(dishName);
            if (dishName.length() > MAX_DISH_NAME_DISPLAY_LENGTH) {
                builder.append("\n\t\t").append("   ");
                builder.append(Utils.blankSpace(MAX_DISH_NAME_DISPLAY_LENGTH));
            } else {
                builder.append(Utils.blankSpace(dishName, MAX_DISH_NAME_DISPLAY_LENGTH));
            }
            builder.append(dishPrice);
            builder.append(Utils.blankSpace(dishPrice, MAX_DISH_PRICE_DISPLAY_LENGTH));
            builder.append(dishQuantity);
        }
        if (hasCustomerField()) {
            builder.append("\n\tRedeemed points:\t\t").append(getPoints());
        }
        builder.append("\n\tTotal price:\t\t");
        builder.append(Price.convertPricetoString(getPrice()));
        builder.append("\n\tEarned points:\t\t").append(getEarnedPointsValue());
        return builder.toString();
    }

    /**
     * Formats an draft order as text. Null fields are shown as empty.
     */
    default String getDraftDetailsAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append("\tCustomer: ");
        if (hasCustomerField()) {
            builder.append(getCustomer().getAsTextInOrderList());
        } else {
            builder.append("<empty>");
        }
        builder.append("\n\tDishes: ");
        if (hasDishItems()) {
            int i = 0;
            for (Map.Entry<ReadOnlyMenus, Integer> m: getDishItems().entrySet()) {
                i++;
                String dishName = m.getKey().getName().toString();
                String dishPrice = "(" + m.getKey().getPrice().toString() + ")";
                String dishQuantity = "" + m.getValue();
                dishQuantity = MULTIPLY_SIGN
                        + Utils.blankSpace(MAX_DISH_QUANTITY_DIGITS - dishQuantity.length())
                        + dishQuantity;
                if (i != 1) {
                    builder.append("\n\t\t");
                }
                builder.append(i).append(". ");
                builder.append(dishName);
                if (dishName.length() > MAX_DISH_NAME_DISPLAY_LENGTH) {
                    builder.append("\n\t\t").append("   ");
                    builder.append(Utils.blankSpace(MAX_DISH_NAME_DISPLAY_LENGTH));
                } else {
                    builder.append(Utils.blankSpace(dishName, MAX_DISH_NAME_DISPLAY_LENGTH));
                }
                builder.append(dishPrice);
                builder.append(Utils.blankSpace(dishPrice, MAX_DISH_PRICE_DISPLAY_LENGTH));
                builder.append(dishQuantity);
            }
        } else {
            builder.append("<empty>");
        }
        if (hasCustomerField() && getMaxPointsRedeemable() != 0) {
            builder.append("\n\tRedeemed points:\t\t").append(getPoints());
            builder.append(" / ").append(getMaxPointsRedeemable());
        }
        builder.append("\n\tTotal price:\t\t");
        builder.append(Price.convertPricetoString(getPrice()));
        return builder.toString();
    }
}
