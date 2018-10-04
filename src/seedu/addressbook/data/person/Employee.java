package seedu.addressbook.data.person;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents an Employee in the system.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Employee extends Person {


    /**
     * Assumption: Every field must be present and not null.
     */
    public Employee(Name name, Phone phone, Email email, Address address){
        setName(name);
        setPhone(phone);
        setEmail(email);
        setAddress(address);
    }
}
