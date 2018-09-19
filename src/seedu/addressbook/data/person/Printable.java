package seedu.addressbook.data.person;

/**
 * A read-only immutable interface for any field that can be printed in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface Printable {

    /**
     * Returns a concatenated version of the printable strings of each object.
     */
    String getPrintableString();
}
