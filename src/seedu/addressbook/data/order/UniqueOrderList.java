package seedu.addressbook.data.order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import seedu.addressbook.common.Utils;
import seedu.addressbook.data.exception.DuplicateDataException;

//@@author px1099
/**
 * A list of orders. Does not allow null element or duplicates.
 *
 * @see Order#equals(Object)
 * @see Utils#elementsAreUnique(Collection)
 */
public class UniqueOrderList implements Iterable<Order> {

    private final List<Order> internalList = new ArrayList<>();

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateOrderException extends DuplicateDataException {
        protected DuplicateOrderException() {
            super("Operation would result in duplicate orders");
        }
    }

    /**
     * Signals that an operation targeting a specified order in the list would fail because
     * there is no such matching order in the list.
     */
    public static class OrderNotFoundException extends Exception {}

    /**
     * Constructs empty order list.
     */
    public UniqueOrderList() {}

    /**
     * Constructs an order list with the given orders.
     */
    public UniqueOrderList(Order... orders) throws DuplicateOrderException {
        final List<Order> initialTags = Arrays.asList(orders);
        if (!Utils.elementsAreUnique(initialTags)) {
            throw new DuplicateOrderException();
        }
        internalList.addAll(initialTags);
    }

    /**
     * Constructs a list from the items in the given collection.
     * @param orders a collection of persons
     * @throws DuplicateOrderException if the {@code persons} contains duplicate persons
     */
    public UniqueOrderList(Collection<Order> orders) throws DuplicateOrderException {
        if (!Utils.elementsAreUnique(orders)) {
            throw new DuplicateOrderException();
        }
        internalList.addAll(orders);
    }

    /**
     * Constructs a shallow copy of the list.
     */
    public UniqueOrderList(UniqueOrderList source) {
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
     *
     * @throws DuplicateOrderException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(Order toAdd) throws DuplicateOrderException {
        if (contains(toAdd)) {
            throw new DuplicateOrderException();
        }
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
                || (other instanceof UniqueOrderList // instanceof handles nulls
                && this.internalList.equals(((UniqueOrderList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
