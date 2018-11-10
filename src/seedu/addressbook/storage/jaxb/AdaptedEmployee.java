package seedu.addressbook.storage.jaxb;

import javax.xml.bind.annotation.XmlElement;

import seedu.addressbook.common.Utils;
import seedu.addressbook.data.employee.Employee;
import seedu.addressbook.data.employee.EmployeeAddress;
import seedu.addressbook.data.employee.EmployeeEmail;
import seedu.addressbook.data.employee.EmployeeName;
import seedu.addressbook.data.employee.EmployeePhone;
import seedu.addressbook.data.employee.EmployeePosition;
import seedu.addressbook.data.employee.ReadOnlyEmployee;
import seedu.addressbook.data.exception.IllegalValueException;

/**
 * JAXB-friendly adapted employee data holder class.
 */
public class AdaptedEmployee {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String position;

    /**
     * No-arg constructor for JAXB use.
     */
    public AdaptedEmployee() {}

    public AdaptedEmployee(ReadOnlyEmployee source) {

        name = source.getName().fullName;

        phone = source.getPhone().value;

        email = source.getEmail().value;

        address = source.getAddress().value;

        position = source.getPosition().value;
    }

    /**
     * Returns true if any required field is missing.
     *
     * JAXB does not enforce (required = true) without a given XML schema.
     * Since we do most of our validation using the data class constructors, the only extra logic we need
     * is to ensure that every xml element in the document is present. JAXB sets missing elements as null,
     * so we check for that.
     */
    public boolean isAnyRequiredFieldMissing() {
        return Utils.isAnyNull(name, phone, email, address, position);
    }

    /**
     * Converts this jaxb-friendly adapted employee object into the Employee object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted employee
     */
    public Employee toModelType() throws IllegalValueException {
        final EmployeeName name = new EmployeeName(this.name);
        final EmployeePhone phone = new EmployeePhone(this.phone);
        final EmployeeEmail email = new EmployeeEmail(this.email);
        final EmployeeAddress address = new EmployeeAddress(this.address);
        final EmployeePosition position = new EmployeePosition(this.position);
        return new Employee(name, phone, email, address, position);
    }
}
