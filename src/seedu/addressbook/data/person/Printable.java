package seedu.addressbook.data.person;

/**
 * A printable interface for a Person.
 */
public interface Printable {

    /**
     * Returns a concatenated version of the printable strings of each object.
     */
    String getPrintableString(Printable... printables);
}
