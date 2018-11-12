//@@author kianhong95
package seedu.addressbook.commands.employee;

import java.util.List;

import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.employee.ReadOnlyEmployee;

//@@author kianhong95
/**
 * Represents the result of an employee command execution.
 */
public class EmployeeCommandResult extends CommandResult {

    public EmployeeCommandResult(String feedbackToUser, List<? extends ReadOnlyEmployee> relevantEmployees) {
        super(feedbackToUser, null, null, null, relevantEmployees, null);
    }
}
