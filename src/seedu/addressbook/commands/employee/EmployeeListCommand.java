package seedu.addressbook.commands.employee;

import java.util.List;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.employee.ReadOnlyEmployee;

//@@author kianhong95
/**
 * List all employees.
 */
public class EmployeeListCommand extends Command {

    public static final String COMMAND_WORD = "listemp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "List all employees. \n\n"
            + "Parameters: NIL\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute() {
        List<ReadOnlyEmployee> allEmployees = rms.getAllEmployees().immutableListView();
        return new EmployeeCommandResult(getMessageForEmployeeListShownSummary(allEmployees), allEmployees);
    }
}
