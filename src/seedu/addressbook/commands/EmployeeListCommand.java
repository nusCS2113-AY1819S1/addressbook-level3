package seedu.addressbook.commands;

import java.util.List;

import seedu.addressbook.data.person.ReadOnlyPerson;


/**
 * List all employees.
 */
public class EmployeeListCommand extends Command{

    public static final String COMMAND_WORD = "listemp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "List all employees. \n\t"
            + "Parameters: NIL\n\t"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute() {
        List<ReadOnlyPerson> allEmployees = rms.getAllEmployees().immutableListView();
        return new CommandResult(getMessageForPersonListShownSummary(allEmployees), allEmployees);
    }
}
