package seedu.addressbook.privilege.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.ExitCommand;
import seedu.addressbook.commands.FindCommand;
import seedu.addressbook.commands.HelpCommand;
import seedu.addressbook.commands.ListCommand;
import seedu.addressbook.commands.LoginCommand;
import seedu.addressbook.commands.LogoutCommand;
import seedu.addressbook.commands.RaisePrivilegeCommand;
import seedu.addressbook.commands.ViewCommand;
import seedu.addressbook.commands.ViewPrivilegeCommand;
import seedu.addressbook.commands.ViewSelfCommand;

/**
 * Represents a user with Basic access
 * */
public class BasicUser implements User {
    private static List<Command> newAllowedCommands = Arrays.asList(
            new ViewCommand(),
            new ListCommand(),
            new RaisePrivilegeCommand(),
            new ViewPrivilegeCommand(),
            new FindCommand(),
            new HelpCommand(),
            new LoginCommand(),
            new LogoutCommand(),
            new ExitCommand(),
            new ViewSelfCommand()
    );
    private List<Command> allowedCommands;
    private PrivilegeLevel currentLevel;

    public BasicUser() {
        currentLevel = PrivilegeLevel.Basic;
        allowedCommands = new ArrayList<>();
        allowedCommands.addAll(newAllowedCommands);
        sortCommands();
    }

    protected void addAllowedCommands(List<Command> newAllowedCommands) {
        allowedCommands.addAll(newAllowedCommands);
    }

    protected void setCurrentLevel(PrivilegeLevel currentLevel) {
        this.currentLevel = currentLevel;
    }

    public String getPrivilegeLevelAsString() {
        return currentLevel.toString();
    }

    public PrivilegeLevel getPrivilegeLevel() {
        return currentLevel;
    }

    public List<Command> getAllowedCommands() {
        return allowedCommands;
    }

    protected void sortCommands() {
        allowedCommands.sort(new SortByCategory());
    }

    /** Checks if this privilege level have access to the given command*/
    public boolean isAllowedCommand(Command command) {
        for (Command allowedCommand: allowedCommands) {
            if (command.getClass().equals(allowedCommand.getClass())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Comparator used to sort the commands in allowedCommands
     */
    class SortByCategory implements Comparator<Command> {
        /**
         * Compare commands based on category, if they are the same, compare name of the command
         */
        public int compare(Command a, Command b) {
            String categoryA = a.getCategory().toString();
            String categoryB = b.getCategory().toString();
            if (!categoryA.equals(categoryB)) {
                return categoryA.compareTo(categoryB);
            } else {
                return a.toString().compareTo(b.toString());
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BasicUser // instanceof handles nulls
                && this.currentLevel.equals(((BasicUser) other).currentLevel)); // state check
    }
}
