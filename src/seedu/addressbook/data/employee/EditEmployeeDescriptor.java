package seedu.addressbook.data.employee;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Stores the details to edit the employee with. Each non-empty field value will replace the
 * corresponding field value of the employee.
 */
public class EditEmployeeDescriptor {
    private EmployeeEmail email;
    private EmployeePhone phone;
    private EmployeeAddress address;
    private EmployeePosition position;

    public EditEmployeeDescriptor(){}

    public EditEmployeeDescriptor(String phone,
                                  String email,
                                  String address,
                                  String position) throws IllegalValueException {
        if (phone == null) {
            this.phone = new EmployeePhone();
        } else {
            this.phone = new EmployeePhone(phone);
        }

        if (email == null) {
            this.email = new EmployeeEmail();
        } else {
            this.email = new EmployeeEmail(email);
        }
        if (address == null) {
            this.address = new EmployeeAddress();
        } else {
            this.address = new EmployeeAddress(address);
        }
        if (position == null) {
            this.position = new EmployeePosition();
        } else {
            this.position = new EmployeePosition(position);
        }
    }

    /**
     * Copy constructor.
     */
    public EditEmployeeDescriptor(EditEmployeeDescriptor toCopy) {
        setPhone(toCopy.phone);
        setEmail(toCopy.email);
        setAddress(toCopy.address);
        setPosition(toCopy.position);
    }

    public void setPhone(EmployeePhone phone) {
        this.phone = phone;
    }

    public EmployeePhone getPhone() {
        return phone;
    }

    public void setEmail(EmployeeEmail email) {
        this.email = email;
    }

    public EmployeeEmail getEmail() {
        return email;
    }

    public void setAddress(EmployeeAddress address) {
        this.address = address;
    }

    public EmployeeAddress getAddress() {
        return address;
    }

    public void setPosition(EmployeePosition position) {
        this.position = position;
    }

    public EmployeePosition getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditEmployeeDescriptor)) {
            return false;
        }

        // state check
        EditEmployeeDescriptor e = (EditEmployeeDescriptor) other;

        return getPhone().equals(e.getPhone())
                && getEmail().equals(e.getEmail())
                && getAddress().equals(e.getAddress())
                && getPosition().equals(e.getPosition());
    }
}
