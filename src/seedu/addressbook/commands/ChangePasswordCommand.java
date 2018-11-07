package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.login.*;

import javax.security.auth.login.CredentialNotFoundException;
import java.io.IOException;

public class ChangePasswordCommand extends Command {
    public static final String COMMAND_WORD = "change-pass";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Changes password of current user.\n"
            + "Parameters: CURRENT_PASSWORD NEW_PASSWORD CONFIRM_NEW_PASSWORD\n"
            + "Example: " + COMMAND_WORD + "0ldPassword! n3wPassword! n3wPassword!";

    public static final String MESSAGE_SUCCESS = "changed password";
    public static final String MESSAGE_FAILED = "failed to change password";
    public static final String MESSAGE_DIFF_PASSWORD = "entered passwords must be the same";

    private final String current, next, confirm;

    public ChangePasswordCommand(String current, String next, String confirm) {
        this.current = current.trim();
        this.next = next.trim();
        this.confirm = confirm.trim();
    }

    @Override
    public CommandResult execute() {
        String username = login.getUsernameF();
        int accesslevel = login.getAccesslevelF();
        if(!next.equals(confirm)){
            return new CommandResult(MESSAGE_DIFF_PASSWORD + "abc" + current + "abc" + next + "abc" + confirm + "abc");
        }else if(WorkWithLoginStorage.compareCredentials(username, current)){
            Credentials credentials = new Credentials(username, next, accesslevel);
            WorkWithLoginStorage.editLogin(credentials);
            if(credentials.validateCredentials()){
                return new CommandResult(MESSAGE_SUCCESS);
            }else{
                return new CommandResult(MESSAGE_FAILED);
            }
        }
    return new CommandResult(MESSAGE_FAILED);
    }
}
