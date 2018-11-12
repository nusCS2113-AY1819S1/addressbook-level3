package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.login.*;

import javax.security.auth.login.CredentialNotFoundException;
import java.io.IOException;

public class ChangePasswordCommand extends UndoAbleCommand {
    public static final String COMMAND_WORD = "change-pass";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Changes password of current user.\n"
            + "Parameters: pw/CURRENT_PASSWORD npw/NEW_PASSWORD cpw/CONFIRM_NEW_PASSWORD\n"
            + "Example: " + COMMAND_WORD + "pw/0ldPassword! npw/n3wPassword! cpw/n3wPassword!";

    public static final String MESSAGE_SUCCESS = "Your password has been successfully changed!";
    public static final String MESSAGE_FAILED = "failed to change password!";
    public static final String MESSAGE_FAILED_DIFF_PASSWORD = "New passwords do not match!";
    public static final String MESSAGE_FAILED_WRONG_PASSWORD = "Current password is incorrect!";

    private final String current, next, confirm;
    private Credentials credentials;

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
            return new CommandResult(MESSAGE_FAILED_DIFF_PASSWORD);
        }else if(WorkWithLoginStorage.compareCredentials(username, current)){
            credentials = new Credentials(username, next, accesslevel);
            credentials.changePassword();
            if(credentials.validateCredentials()){
                saveUndoableToHistory(COMMAND_WORD + " "+ current + " " + next + " " + confirm);
                return new CommandResult(MESSAGE_SUCCESS);
            }else{
                return new CommandResult(MESSAGE_FAILED);
            }
        }else{
            return new CommandResult(MESSAGE_FAILED_WRONG_PASSWORD);
        }
    }

    @Override
    public void executeUndo(){
        credentials.setPassword(current);
        credentials.changePassword();
    }

    @Override
    public void executeRedo(){
        credentials.setPassword(next);
        credentials.changePassword();
    }
}
