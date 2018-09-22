package seedu.addressbook.data;

import seedu.addressbook.data.person.UniquePersonList;
import java.util.ArrayList;

/**
 * Stores the different states of the address book
 */
public class CommandHistory {
    protected AddressBook addressBook;
    private ArrayList<UniquePersonList> addressBookStates = new ArrayList<>();
    private int STATEITERATOR = 0;
    private int ITERATOROFFSET = 1;
    /**
     * Save initial state of the address book
     */
    public void saveInitialState() {
        addressBookStates.add(addressBook.getAllPersons());
    }

    /**
     * add the current state of the address book into the history
     */
    public void addCurrentState() {
        STATEITERATOR++;
        addressBookStates.add(addressBook.getAllPersons());
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
        if(addressBookStates.get(STATEITERATOR + ITERATOROFFSET) != null) return false;
        else return true;
    }
}