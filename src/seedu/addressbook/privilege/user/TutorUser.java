package seedu.addressbook.privilege.user;

import java.util.Arrays;
import java.util.List;

import seedu.addressbook.commands.AddCommand;
import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.DeleteCommand;

/**
 * Represents a user with Tutor access
 * */
public class TutorUser extends BasicUser {
    private static List<Command> newAllowedCommand = Arrays.asList(
            new AddCommand(),
            new DeleteCommand()
    );

    public TutorUser() {
        super();
        addAllowedCommands(newAllowedCommand);
        setCurrentLevel(PrivilegeLevel.Tutor);
        sortCommands();
    }
}
