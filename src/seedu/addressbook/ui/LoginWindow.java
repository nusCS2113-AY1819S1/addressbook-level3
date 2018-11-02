package seedu.addressbook.ui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import seedu.addressbook.Main;
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
    private String USERNAME, PASSWORD;
    private Stoppable mainApp;

    public void setMainApp(Stoppable mainApp){
        this.mainApp = mainApp;
    }


    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private Label message;

    @FXML
    void handleButtonClick(ActionEvent event) throws Exception {
            USERNAME = username.getText();
            PASSWORD = password.getText();
            username.clear();
            password.clear();
            attemptLogin();
    }

    private void attemptLogin() throws Exception {
        if (!login.main(USERNAME, PASSWORD)) {
            tries--;
            message.setText("Incorrect Username/Password! Please Try Again! " + tries + " left.");
        }else{
            Main.change();
        }
        if(tries==0) {
            exitApp();
        }
    }

    private void exitApp() throws Exception {
        mainApp.stop();
    }
}
