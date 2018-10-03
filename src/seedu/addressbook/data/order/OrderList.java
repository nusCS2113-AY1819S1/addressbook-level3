package seedu.addressbook.data.order;

import java.util.*;

/**
 * A list of orders.
 */
public class OrderList implements Iterable<Order> {

    /**
     * Signals that an operation targeting a specified order in the list would fail because
     * there is no such matching order in the list.
     */
    public static class OrderNotFoundException extends Exception {}

    private final List<Order> internalList = new ArrayList<>();

    /**
     * Constructs empty order list.
     */
    public OrderList() {}

    /**
     * Constructs an order list with the given orders.
     */
    public OrderList(Order... orders) {
        final List<Order> initialTags = Arrays.asList(orders);
        internalList.addAll(initialTags);
    }

    /**
     * Constructs a list from the items in the given collection.
     */
    public OrderList(Collection<Order> orders) {
        internalList.addAll(orders);
    }

    /**
     * Constructs a shallow copy of the list.
     */
    public OrderList(OrderList source) {
        internalList.addAll(source.internalList);
    }

    /**
     * Unmodifiable java List view with elements cast as immutable {@link ReadOnlyOrder}s.
     * For use with other methods/libraries.
     * Any changes to the internal list/elements are immediately visible in the returned list.
     */
    public List<ReadOnlyOrder> immutableListView() {
        return Collections.unmodifiableList(internalList);
    }


    /**
     * Checks if the list contains an equivalent order as the given argument.
     */
    public boolean contains(ReadOnlyOrder toCheck) {
        return internalList.contains(toCheck);
    }

    /**
     * Adds an order to the list.
     */
    public void add(Order toAdd) {
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent order from the list.
     *
     * @throws OrderNotFoundException if no such order could be found in the list.
     */
    public void remove(ReadOnlyOrder toRemove) throws OrderNotFoundException {
        final boolean orderFoundAndDeleted = internalList.remove(toRemove);
        if (!orderFoundAndDeleted) {
            throw new OrderNotFoundException();
        }
    }

    /**
     * Clears all orders in list.
     */
    public void clear() {
        internalList.clear();
    }

    @Override
    public Iterator<Order> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrderList // instanceof handles nulls
                && this.internalList.equals(
                ((OrderList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
