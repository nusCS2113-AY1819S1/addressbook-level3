package seedu.addressbook.commands;

import seedu.addressbook.data.person.ReadOnlyPerson;

import java.util.List;

public class SortCommand extends Command{
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Displays all persons in the address book as a sorted list with index numbers.\n\t"
            + "Example: " + COMMAND_WORD;


    @Override
    public CommandResult execute() {
        saveHistory(COMMAND_WORD);
        List<ReadOnlyPerson> sortedPersons = addressBook.getAllPersons().sortView();
        List<ReadOnlyPerson> allMutablePersons = addressBook.getAllPersons().mutableListView();
        return new CommandResult(getMessageForPersonListShownSummary(sortedPersons), sortedPersons, allMutablePersons);
    }
}

