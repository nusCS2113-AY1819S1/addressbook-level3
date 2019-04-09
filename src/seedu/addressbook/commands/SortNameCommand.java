package seedu.addressbook.commands;

import seedu.addressbook.data.person.ReadOnlyPerson;

import java.util.List;

public class SortNameCommand extends Command{
    public static final String COMMAND_WORD = "sortname";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Sorts according to name and displays all persons in MediBook as a list with index numbers.\n\t"
            + "Example: " + COMMAND_WORD;


    @Override
    public CommandResult execute() {
        saveHistory(COMMAND_WORD);
        List<ReadOnlyPerson> sortedPersons = addressBook.getAllPersons().sortNameView();
        List<ReadOnlyPerson> allMutablePersons = addressBook.getAllPersons().mutableListView();
        return new CommandResult(getMessageForPersonListShownSummary(sortedPersons), sortedPersons, allMutablePersons);
    }
}

