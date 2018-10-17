package seedu.addressbook.data.person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import seedu.addressbook.common.Utils;
import seedu.addressbook.data.exception.DuplicateDataException;

/**
 * A list of statistics per assignment. Does not allow null elements or duplicates.
 *
 * @see AssignmentStatistics#equals(Object)
 * @see Utils#elementsAreUnique(Collection)
 */
public class UniqueStatisticsList implements Iterable<AssignmentStatistics> {

    private final List<AssignmentStatistics> internalList = new ArrayList<>();

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateStatisticsException extends DuplicateDataException {
        protected DuplicateStatisticsException() {
            super("Operation would result in duplicate assignment statistics");
        }
    }

    /**
     * Signals that an operation targeting a specified result in the list would fail because
     * there is no such matching result in the list.
     */
    public static class StatisticsNotFoundException extends Exception {}

    /**
     * Constructs empty result list.
     */
    public UniqueStatisticsList() {}

    /**
     * Constructs a result list with the given statistics.
     */
    public UniqueStatisticsList(AssignmentStatistics... statistics) throws DuplicateStatisticsException {
        final List<AssignmentStatistics> initialTags = Arrays.asList(statistics);
        if (!Utils.elementsAreUnique(initialTags)) {
            throw new DuplicateStatisticsException();
        }
        internalList.addAll(initialTags);
    }

    /**
     * Constructs a list from the items in the given collection.
     * @param statistics a collection of statistics
     * @throws DuplicateStatisticsException if the {@code statistics} contains duplicate statistics
     */
    public UniqueStatisticsList(Collection<AssignmentStatistics> statistics) throws DuplicateStatisticsException {
        if (!Utils.elementsAreUnique(statistics)) {
            throw new DuplicateStatisticsException();
        }
        internalList.addAll(statistics);
    }

    /**
     * Constructs a shallow copy of the list.
     */
    public UniqueStatisticsList(UniqueStatisticsList source) {
        internalList.addAll(source.internalList);
    }

    /**
     * Checks if the list contains an equivalent statistics as the given argument.
     */
    public boolean contains(AssignmentStatistics toCheck) {
        return internalList.contains(toCheck);
    }

    /**
     * Adds a result to the list.
     *
     * @throws DuplicateStatisticsException if the person to add is a duplicate of an existing stastic in the list.
     */
    public void add(AssignmentStatistics toAdd) throws DuplicateStatisticsException {
        if (contains(toAdd)) {
            throw new DuplicateStatisticsException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent statistic from the list.
     *
     * @throws StatisticsNotFoundException if no such person could be found in the list.
     */
    public void remove(AssignmentStatistics toRemove) throws StatisticsNotFoundException {
        final boolean statisticsFoundAndDeleted = internalList.remove(toRemove);
        if (!statisticsFoundAndDeleted) {
            throw new StatisticsNotFoundException();
        }
    }

    /**
     * Clears all statistics in list.
     */
    public void clear() {
        internalList.clear();
    }

    @Override
    public Iterator<AssignmentStatistics> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueStatisticsList // instanceof handles nulls
                && this.internalList.equals((
                (UniqueStatisticsList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
