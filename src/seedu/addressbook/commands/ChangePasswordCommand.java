package seedu.addressbook.commands;

import seedu.addressbook.login.*;

import java.io.IOException;

public class ChangePasswordCommand {
    public static final String COMMAND_WORD = "changepw";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ";\n" + "ads" ;

    public static final String MESSAGE_SUCCESS = "asd";

    public ChangePasswordCommand(Credentials username) throws IOException {
        username.editPassword();
    }
}
