package seedu.addressbook.commands;

import seedu.addressbook.login.*;

import java.io.IOException;

public class ChangePasswordCommand extends Command {
    public static final String COMMAND_WORD = "changepw";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ";\n" + "ads";

    public static final String MESSAGE_SUCCESS = "asd";

//    public ChangePasswordCommand(Credentials username) throws IOException {
//        username.editPassword();
//    }

//    @Override
//    public CommandResult execute() {
//        try {
//            addressBook.addPerson(toAdd);
//            commandHistory.checkForAction();
//            commandHistory.addHistory(COMMAND_WORD + " " + toAdd.toString());
//            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
//        } catch (UniquePersonList.DuplicatePersonException dpe) {
//            return new CommandResult(MESSAGE_DUPLICATE_PERSON);
//        }
//    }
}
