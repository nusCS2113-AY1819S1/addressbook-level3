package seedu.addressbook.data.employee;

import java.util.Objects;

//@@author kianhong95
/**
 * Represents an Employee in the system.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Employee implements ReadOnlyEmployee {

    private EmployeeName name;
    private EmployeePhone phone;
    private EmployeeEmail email;
    private EmployeeAddress address;
    private EmployeePosition position;

    /**
     * Assumption: Every field must be present and not null.
     */
    public Employee(EmployeeName name,
                    EmployeePhone phone,
                    EmployeeEmail email,
                    EmployeeAddress address,
                    EmployeePosition position) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.position = position;
    }

    /**
     * Copy constructor.
     */
    public Employee(ReadOnlyEmployee source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(), source.getPosition());
    }

    @Override
    public EmployeeName getName() {
        return name;
    }

    @Override
    public EmployeePhone getPhone() {
        return phone;
    }

    @Override
    public EmployeeEmail getEmail() {
        return email;
    }

    @Override
    public EmployeeAddress getAddress() {
        return address;
    }

    @Override
    public EmployeePosition getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyEmployee // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyEmployee) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, position);
    }

    @Override
    public String toString() {
        return getAsTextShowDetails();
    }
}
