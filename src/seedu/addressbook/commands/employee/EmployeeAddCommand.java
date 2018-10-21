package seedu.addressbook.commands.employee;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.employee.Employee;
import seedu.addressbook.data.employee.EmployeeAddress;
import seedu.addressbook.data.employee.EmployeeEmail;
import seedu.addressbook.data.employee.EmployeeName;
import seedu.addressbook.data.employee.EmployeePhone;
import seedu.addressbook.data.employee.EmployeePosition;
import seedu.addressbook.data.employee.ReadOnlyEmployee;
import seedu.addressbook.data.employee.UniqueEmployeeList;
import seedu.addressbook.data.exception.IllegalValueException;

import seedu.addressbook.common.Messages;

/**
 * Adds a new employee to the Rms.
 */

public class EmployeeAddCommand extends Command {

    public static final String COMMAND_WORD = "addemp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Adds an employee to the Rms. "
            + "Parameters: NAME p/PHONE e/EMAIL a/ADDRESS pos/POSITION\n\t"
            + "Example: " + COMMAND_WORD
            + " Peter Lee p/91234567 e/PeterLee89@rms.com a/Clementi Ave 2, Blk 543 #13-12 pos/Cashier";

    public static final String MESSAGE_SUCCESS = "New employee added: %1$s";

    private final Employee toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public EmployeeAddCommand(String name,
                              String phone,
                              String email,
                              String address,
                              String position) throws IllegalValueException {
        this.toAdd = new Employee(
                new EmployeeName(name),
                new EmployeePhone(phone),
                new EmployeeEmail(email),
                new EmployeeAddress(address),
                new EmployeePosition(position)
        );
    }

    public EmployeeAddCommand(Employee toAdd) {
        this.toAdd = toAdd;
    }

    public ReadOnlyEmployee getEmployee() {
        return toAdd;
    }

    @Override
    public CommandResult execute() {
        try {
            rms.addEmployee(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueEmployeeList.DuplicateEmployeeException dee) {
            return new CommandResult(Messages.MESSAGE_DUPLICATE_EMPLOYEE);
        }
    }
}
