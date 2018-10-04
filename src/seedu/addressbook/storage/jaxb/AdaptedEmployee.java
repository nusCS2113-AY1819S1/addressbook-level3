package seedu.addressbook.storage.jaxb;

import javax.xml.bind.annotation.XmlElement;

import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Address;
import seedu.addressbook.data.person.Email;
import seedu.addressbook.data.person.Employee;
import seedu.addressbook.data.person.Name;
import seedu.addressbook.data.person.Phone;
import seedu.addressbook.data.person.ReadOnlyPerson;




public class AdaptedEmployee {


    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;


    public AdaptedEmployee(){}

    public AdaptedEmployee(ReadOnlyPerson source) {

        name = source.getName().fullName;

        phone= source.getPhone().value;

        email = source.getEmail().value;

        address = source.getAddress().value;

    }


    // copy of above but for employee instead
    public Employee toModelType() throws IllegalValueException {
        final Name name = new Name(this.name);
        final Phone phone = new Phone(this.phone, false);
        final Email email = new Email(this.email, false);
        final Address address = new Address(this.address, false);
        return new Employee(name, phone, email, address);
    }
}
