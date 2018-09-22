package seedu.addressbook.data;

import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.UniquePersonList;
import seedu.addressbook.data.person.UniquePersonList.DuplicatePersonException;
import seedu.addressbook.data.person.UniquePersonList.PersonNotFoundException;

import java.util.ArrayList;

/**
 * Represents the entire address book. Contains the data of the address book.
 */
public class AddressBook {

    private UniquePersonList allPersons;
    private ArrayList<UniquePersonList> addressBookStates = new ArrayList<>();
    private int STATEITERATOR = 0;
    private int ITERATOROFFSET = 1;

    public static AddressBook empty() {
        return new AddressBook();
    }

    /**
     * Creates an empty address book.
     */
    public AddressBook() {
        allPersons = new UniquePersonList();
    }

    /**
     * Constructs an address book with the given data.
     *
     * @param persons external changes to this will not affect this address book
     */
    public AddressBook(UniquePersonList persons) {
        this.allPersons = new UniquePersonList(persons);
        saveInitialState();
    }

    public void switchAddressBook(UniquePersonList persons){
        allPersons = new UniquePersonList(persons);
    }

    /**
     * Adds a person to the address book.
     *
     * @throws DuplicatePersonException if an equivalent person already exists.
     */
    public void addPerson(Person toAdd) throws DuplicatePersonException {
        allPersons.add(toAdd);
    }

    /**
     * Checks if an equivalent person exists in the address book.
     */
    public boolean containsPerson(ReadOnlyPerson key) {
        return allPersons.contains(key);
    }

    /**
     * Removes the equivalent person from the address book.
     *
     * @throws PersonNotFoundException if no such Person could be found.
     */
    public void removePerson(ReadOnlyPerson toRemove) throws PersonNotFoundException {
        allPersons.remove(toRemove);
    }

    /**
     * Clears all persons from the address book.
     */
    public void clear() {
        allPersons.clear();
    }

    /**
     * Defensively copied UniquePersonList of all persons in the address book at the time of the call.
     */
    public UniquePersonList getAllPersons() {
        return new UniquePersonList(allPersons);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.allPersons.equals(((AddressBook) other).allPersons));
    }

    @Override
    public int hashCode() {
        return allPersons.hashCode();
    }

    public static class HistoryOutOfBoundException extends Exception {}
    /**
     * Save initial state of the address book
     */
    public void saveInitialState() {
        this.addressBookStates.add(getAllPersons());
    }

    /**
     * add the current state of the address book into the history
     */
    public void addCurrentState() {
        moveIteratorForward();
        addressBookStates.add(getAllPersons());
    }

    public void moveIteratorForward() {
        STATEITERATOR++;
    }

    public void moveIteratorBackward() {
        STATEITERATOR--;
    }

    /**
     * truncate old history when new undo-able command is executed after undo-ing
     */
    public void truncateOldPath() {
        addressBookStates.subList(STATEITERATOR, addressBookStates.size()).clear();
    }

    /**
     * check whether to execute truncateOldPath
     */
    public void checkForAction() {
        if(!nextBoxIsEmpty()) truncateOldPath();
        addCurrentState();
    }

    public boolean nextBoxIsEmpty() {
        if((STATEITERATOR + ITERATOROFFSET) >= addressBookStates.size()) return false;
        else return true;
    }

    public void undoLast() throws HistoryOutOfBoundException {
        moveIteratorBackward();
        if(STATEITERATOR < 0){
            moveIteratorForward();
            throw new HistoryOutOfBoundException();
        }
        switchAddressBook(addressBookStates.get(STATEITERATOR));
    }

    public void redoLast() throws HistoryOutOfBoundException {
        moveIteratorForward();
        if(STATEITERATOR + ITERATOROFFSET> addressBookStates.size()){
            moveIteratorBackward();
            throw new HistoryOutOfBoundException();
        }
        switchAddressBook(addressBookStates.get(STATEITERATOR));
    }
}
