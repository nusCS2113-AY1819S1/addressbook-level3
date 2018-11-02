package seedu.addressbook.data;

import java.util.ArrayList;

public class CommandHistory {
    private ArrayList<String> history;
//    private AddressBook addressBook;
//    private ArrayList<UniquePersonList> addressBookStates = new ArrayList<>()
//    private static final int ITERATOROFFSET = 1;
//    private int stateIterator = 0;

    public CommandHistory(){
        history = new ArrayList<>();
//        this.addressBook = addressBook;
//        saveInitialState();
    }

//    public static class HistoryOutOfBoundException extends Exception {}

    public static class EmptyHistoryException extends Exception {}

    public void addHistory(String Arg) {
        history.add(Arg);
    }

    public String getHistory() throws EmptyHistoryException {
        if(history.size() == 0) throw new EmptyHistoryException();
        return historyToString();
    }

    public String historyToString() {
        final StringBuilder builder = new StringBuilder();
        for (String command:history) {
            builder.append(command);
            builder.append("\n");
        }
        return builder.toString();
    }
//    /**
//     * Save initial state of the address book
//     */
//    public void saveInitialState() {
//        this.addressBookStates.add(addressBook.getAllPersons());
//    }
//
//    /**
//     * add the current state of the address book into the history
//     */
//    public void addCurrentState() {
//        moveIteratorForward();
//        addressBookStates.add(addressBook.getAllPersons());
//    }
//
//    public void moveIteratorForward() {
//        stateIterator++;
//    }
//
//    public void moveIteratorBackward() {
//        stateIterator--;
//    }
//
//    /**
//     * truncate old history when new undo-able command is executed after undo-ing
//     */
//    public void truncateOldPath() {
//        addressBookStates.subList(stateIterator + ITERATOROFFSET, addressBookStates.size()).clear();
//    }
//
//    /**
//     * check whether to execute truncateOldPath
//     */
//    public void checkForAction() {
//        if(!nextBoxIsEmpty()) truncateOldPath();
//        addCurrentState();
//    }
//
//    public boolean nextBoxIsEmpty() {
//        if((stateIterator + ITERATOROFFSET) >= addressBookStates.size()) return true;
//        else return false;
//    }
//
//    public void undoLast() throws HistoryOutOfBoundException {
//        moveIteratorBackward();
//        if(stateIterator < 0){
//            moveIteratorForward();
//            throw new HistoryOutOfBoundException();
//        }
//        addressBook.switchAddressBook(addressBookStates.get(stateIterator));
//    }
//
//    public void redoLast() throws HistoryOutOfBoundException {
//        moveIteratorForward();
//        if(stateIterator + ITERATOROFFSET> addressBookStates.size()){
//            moveIteratorBackward();
//            throw new HistoryOutOfBoundException();
//        }
//        addressBook.switchAddressBook(addressBookStates.get(stateIterator));
//    }
}
