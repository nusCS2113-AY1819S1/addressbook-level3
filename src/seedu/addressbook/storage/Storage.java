package seedu.addressbook.storage;
import seedu.addressbook.data.AddressBook;

public abstract class Storage {
    /**
     * Signals that some error has occured while trying to convert and read/write data between the application
     * and the storage file.
     */
    public static class StorageOperationException extends Exception {
        public StorageOperationException(String message) {
            super(message);
        }
    }

    public abstract void save(AddressBook addressBook) throws StorageOperationException;

    public abstract AddressBook load() throws StorageFile.StorageOperationException;
    public abstract String getPath();
}
