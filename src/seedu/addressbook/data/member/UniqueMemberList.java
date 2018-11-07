package seedu.addressbook.data.member;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import seedu.addressbook.common.Utils;
import seedu.addressbook.data.exception.DuplicateDataException;

/**
 * A list of members. Does not allow null elements or duplicates.
 *
 * @see Member#equals(Object)
 * @see Utils#elementsAreUnique(Collection)
 */
public class UniqueMemberList implements Iterable<Member> {

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateMemberException extends DuplicateDataException {
        protected DuplicateMemberException() {
            super("Operation would result in duplicate members");
        }
    }

    /**
     * Signals that an operation targeting a specified member in the list would fail because
     * there is no such matching member in the list.
     */
    public static class MemberNotFoundException extends Exception {}

    private final List<Member> internalList = new ArrayList<>();

    /**
     * Constructs empty person list.
     */
    public UniqueMemberList() {}

    /**
     * Constructs a person list with the given persons.
     */
    public UniqueMemberList(Member... members) throws DuplicateMemberException {
        final List<Member> initialTags = Arrays.asList(members);
        if (!Utils.elementsAreUnique(initialTags)) {
            throw new DuplicateMemberException();
        }
        internalList.addAll(initialTags);
    }

    /**
     * Constructs a list from the items in the given collection.
     * @param members a collection of persons
     * @throws DuplicateMemberException if the {@code persons} contains duplicate persons
     */
    public UniqueMemberList(Collection<Member> members) throws DuplicateMemberException {
        if (!Utils.elementsAreUnique(members)) {
            throw new DuplicateMemberException();
        }
        internalList.addAll(members);
    }

    /**
     * Constructs a shallow copy of the list.
     */
    public UniqueMemberList(UniqueMemberList source) {
        internalList.addAll(source.internalList);
    }

    /**
     * Unmodifiable java List view with elements cast as immutable {@link ReadOnlyMember}s.
     * For use with other methods/libraries.
     * Any changes to the internal list/elements are immediately visible in the returned list.
     */
    public List<ReadOnlyMember> immutableListView() {
        return Collections.unmodifiableList(internalList);
    }


    /**
     * Checks if the list contains an equivalent member as the given argument.
     */
    public boolean contains(ReadOnlyMember toCheck) {
        return internalList.contains(toCheck);
    }

    /**
     * Adds a member to the list.
     *
     * @throws DuplicateMemberException if the member to add is a duplicate of an existing person in the list.
     */
    public void add(Member toAdd) throws DuplicateMemberException {
        if (contains(toAdd)) {
            throw new DuplicateMemberException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent member from the list.
     *
     * @throws MemberNotFoundException if no such member could be found in the list.
     */
    public void remove(ReadOnlyMember toRemove) throws MemberNotFoundException {
        final boolean memberFoundAndDeleted = internalList.remove(toRemove);
        if (!memberFoundAndDeleted) {
            throw new MemberNotFoundException();
        }
    }

    /**
     *  Checks if a member in another feature is in a list of members
     *  Returns the member if found, else create a new Member using the data from the member in the order
     */
    public static Member retrieveMember(ReadOnlyMember target, List<Member> memberList) {
        for (Member member : memberList) {
            if (target.isSameStateAs(member)) {
                return member;
            }
        }
        return new Member(target);
    }

    /**
     * Update the member points of a customer
     * @param target the ReadOnlyMember interface of the Member object to update points
     * @param price the price of the order made by the customer
     * @param usedPoints the redeemed points the customer used in the order
     */
    public void updatePointsOfCustomer(ReadOnlyMember target, double price, int usedPoints) {
        final Member customerToUpdatePoints = retrieveMember(target, internalList);
        customerToUpdatePoints.updatePointsAndTier(price, usedPoints);
    }

    /**
     * Clears all members in list.
     */
    public void clear() {
        internalList.clear();
    }

    @Override
    public Iterator<Member> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueMemberList // instanceof handles nulls
                && this.internalList.equals(((UniqueMemberList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
