package seedu.addressbook.commands;

/**
 * Clears the exam book.
 */
public class ClearExamsCommand extends Command {

    public static final String COMMAND_WORD = "clearexams";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Clears exam book permanently.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Exam book has been cleared!";

    @Override
    public CommandResult execute() {
        examBook.clear();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean isMutating() {
        return true;
    }

    @Override
    public Category getCategory() {
        return Category.EXAM;
    }

    @Override
    public String getCommandUsageMessage() {
        return MESSAGE_USAGE;
    }
}
