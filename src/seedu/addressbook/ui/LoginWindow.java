package seedu.addressbook.ui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import seedu.addressbook.Main;
import seedu.addressbook.login.login;

/**
 * Main Window of the LoginGUI.
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
        tryLogin();
    }

    @FXML
    void handleonKeyPressed(KeyEvent event) throws Exception {
        if(event.getCode() == KeyCode.ENTER) {
            tryLogin();
        }
    }

    private void tryLogin() throws Exception {
        if(username.getText().trim().equals("") && password.getText().trim().equals("")){
            message.setText("Please Enter Username and Password!");
        }else if(username.getText().trim().equals("")){
            message.setText("Please Enter Username!");
        }else if(password.getText().trim().equals("")){
            message.setText("Please Enter Password");
        }else {
            USERNAME = username.getText();
            PASSWORD = password.getText();
            username.clear();
            password.clear();
            attemptLogin();
        }
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
