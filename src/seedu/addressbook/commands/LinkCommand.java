package seedu.addressbook.commands;

public class LinkCommand extends Command{
    public static final String COMMAND_WORD = "link";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "associates a person identified by the index number used in the last person listing with another person. One of them must a patient and the other a doctor\n\t"
            + "Parameters: INDEX1 INDEX2\n\t"
            + "Example: " + COMMAND_WORD + " 1" + " 2";
    public static final String MESSAGE_SUCCESS = "Associated %1$s and %2$s!\n";

    public LinkCommand(int targetVisibleIndex, int targetVisibleIndex2) {
        super(targetVisibleIndex, targetVisibleIndex2);
    }

    @Override
    public CommandResult execute() {
        return new CommandResult("Command under construction");
    }
}
