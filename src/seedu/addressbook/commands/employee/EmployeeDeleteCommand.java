package seedu.addressbook.commands.employee;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.employee.ReadOnlyEmployee;
import seedu.addressbook.data.employee.UniqueEmployeeList.EmployeeNotFoundException;
/**
 * Deletes an employee identified using it's last displayed index from the address book.
 */
public class EmployeeDeleteCommand extends Command{

    public static final String COMMAND_WORD = "delemp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Deletes the employee identified by the index number used in the last employee listing.\n\t"
            + "Parameters: INDEX\n\t"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_EMPLOYEE_SUCCESS = "Deleted Employee: %1$s";


    public EmployeeDeleteCommand(int targetVisibleIndex) {
        super(targetVisibleIndex);
    }


    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyEmployee target = getTargetEmployee();
            rms.removeEmployee(target);
            return new CommandResult(String.format(MESSAGE_DELETE_EMPLOYEE_SUCCESS, target));

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
        } catch (EmployeeNotFoundException enfe) {
            return new CommandResult(Messages.MESSAGE_EMPLOYEE_NOT_IN_RMS);
        }
    }

}
