package seedu.addressbook.stubs;

import seedu.addressbook.data.AddressBook;
import seedu.addressbook.storage.Storage;

public class StorageStub extends Storage {
    private String path;
    public StorageStub(String filePath) {
        path = filePath;
    }
    public void save(AddressBook addressBook){}
    public AddressBook load(){return new AddressBook();}
    public String getPath() {return path;}
}
