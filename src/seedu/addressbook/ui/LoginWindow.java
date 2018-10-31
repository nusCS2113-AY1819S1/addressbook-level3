package seedu.addressbook.ui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.commands.ExitCommand;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.logic.Logic;
import seedu.addressbook.login.login;

import java.util.List;
import java.util.Optional;

import static seedu.addressbook.common.Messages.*;

/**
 * Main Window of the GUI.
 */
public class LoginWindow {

    public Button button;
    private int tries = 3;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private Label message;

    @FXML
    boolean handleButtonClick(ActionEvent event) {
            String user = username.getText();
            String pass = password.getText();
            username.clear();
            password.clear();
            if (!login.main(user, pass)) {
                message.setText("Incorrect Username/Password! Please Try Again! " + tries +" left.");
                tries--;
            }else{
                return true;
            }
        return false;
    }

//    private Logic logic;
//    private Stoppable mainApp;
//
//    public LoginWindow(){
//    }
//
//    public void setLogic(Logic logic){
//        this.logic = logic;
//    }
//
//    public void setMainApp(Stoppable mainApp){
//        this.mainApp = mainApp;
//    }
//

//
//    private void exitApp() throws Exception {
//        mainApp.stop();
//    }
//
//    /** Returns true of the result given is the result of an exit command */
//    private boolean isExitCommand(CommandResult result) {
//        return result.feedbackToUser.equals(ExitCommand.MESSAGE_EXIT_ACKNOWEDGEMENT);
//    }
//
//    /** Clears the command input box */
//    private void clearCommandInput() {
//        commandInput.setText("");
//    }
//
//    /** Clears the output display area */
//    public void clearOutputConsole(){
//        outputConsole.clear();
//    }
//
//    /** Displays the result of a command execution to the user. */
//    public void displayResult(CommandResult result) {
//        clearOutputConsole();
//        final Optional<List<? extends ReadOnlyPerson>> resultPersons = result.getRelevantPersons();
//        if(resultPersons.isPresent()) {
//            display(resultPersons.get());
//        }
//        display(result.feedbackToUser);
//    }
//
//    public void displayWelcomeMessage(String version, String storageFilePath) {
//        String storageFileInfo = String.format(MESSAGE_USING_STORAGE_FILE, storageFilePath);
//        display(MESSAGE_WELCOME, version, MESSAGE_PROGRAM_LAUNCH_ARGS_USAGE, storageFileInfo);
//    }
//
//    /**
//     * Displays the list of persons in the output display area, formatted as an indexed list.
//     * Private contact details are hidden.
//     */
//    private void display(List<? extends ReadOnlyPerson> persons) {
//        display(new Formatter().format(persons));
//    }
//
//    /**
//     * Displays the given messages on the output display area, after formatting appropriately.
//     */
//    private void display(String... messages) {
//        outputConsole.setText(outputConsole.getText() + new Formatter().format(messages));
//    }

}
