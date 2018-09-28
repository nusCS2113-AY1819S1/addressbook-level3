package seedu.addressbook.data;

import seedu.addressbook.data.person.UniquePersonList;

import java.util.ArrayList;

public class CommandHistory {
    private AddressBook addressBook;
    private ArrayList<UniquePersonList> addressBookStates = new ArrayList<>();
    private int STATEITERATOR = 0;
    private int ITERATOROFFSET = 1;

    public CommandHistory(AddressBook addressBook){
        this.addressBook = addressBook;
        saveInitialState();
    }

    public static class HistoryOutOfBoundException extends Exception {}
    /**
     * Save initial state of the address book
     */
    public void saveInitialState() {
        this.addressBookStates.add(addressBook.getAllPersons());
    }

    /**
     * add the current state of the address book into the history
     */
    public void addCurrentState() {
        moveIteratorForward();
        addressBookStates.add(addressBook.getAllPersons());
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
        addressBookStates.subList(STATEITERATOR + ITERATOROFFSET, addressBookStates.size()).clear();
    }

    /**
     * check whether to execute truncateOldPath
     */
    public void checkForAction() {
        if(!nextBoxIsEmpty()) truncateOldPath();
        addCurrentState();
    }

    public boolean nextBoxIsEmpty() {
        if((STATEITERATOR + ITERATOROFFSET) >= addressBookStates.size()) return true;
        else return false;
    }

    public void undoLast() throws HistoryOutOfBoundException {
        moveIteratorBackward();
        if(STATEITERATOR < 0){
            moveIteratorForward();
            throw new HistoryOutOfBoundException();
        }
        addressBook.switchAddressBook(addressBookStates.get(STATEITERATOR));
    }

    public void redoLast() throws HistoryOutOfBoundException {
        moveIteratorForward();
        if(STATEITERATOR + ITERATOROFFSET> addressBookStates.size()){
            moveIteratorBackward();
            throw new HistoryOutOfBoundException();
        }
        addressBook.switchAddressBook(addressBookStates.get(STATEITERATOR));
    }
}
