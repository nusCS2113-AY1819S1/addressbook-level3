package seedu.addressbook.data;

import seedu.addressbook.data.order.Order;
import seedu.addressbook.data.order.OrderList;
import seedu.addressbook.data.order.OrderList.OrderNotFoundException;
import seedu.addressbook.data.order.ReadOnlyOrder;

/**
 * Represents the entire restaurant management system. Contains the data of the order list.
 */
public class RestaurantManagementSystem {

    private final OrderList allOrders;

    public static RestaurantManagementSystem empty() {
        return new RestaurantManagementSystem();
    }

    /**
     * Creates a empty order list.
     */
    public RestaurantManagementSystem() {
        allOrders = new OrderList();
    }

    /**
     * Constructs an order list with the given data.
     *
     * @param orders external changes to this will not affect this order list
     */
    public RestaurantManagementSystem(OrderList orders) {
        this.allOrders = new OrderList(orders);
    }

    /**
     * Adds an order to the order list.
     */
    public void addOrder(Order toAdd) {
        allOrders.add(toAdd);
    }

    /**
     * Checks if an equivalent order exists in the order list.
     */
    public boolean containsOrder(ReadOnlyOrder key) {
        return allOrders.contains(key);
    }

    /**
     * Removes the equivalent order from the address book.
     *
     * @throws OrderNotFoundException if no such Order could be found.
     */
    public void removeOrder(ReadOnlyOrder toRemove) throws OrderNotFoundException {
        allOrders.remove(toRemove);
    }

    /**
     * Clears all orders from the address book.
     */
    public void clearOrderList() {
        allOrders.clear();
    }

    /**
     * Defensively copied OrderList of all orders in the order list at the time of the call.
     */
    public OrderList getAllOrders() {
        return new OrderList(allOrders);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RestaurantManagementSystem // instanceof handles nulls
                && this.allOrders.equals(((RestaurantManagementSystem) other).allOrders));
    }

    @Override
    public int hashCode() {
        return allOrders.hashCode();
    }
}
