package seedu.addressbook;

import javafx.application.Application;
import javafx.application.Platform;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
//import org.gradle.internal.impldep.org.apache.commons.lang.WordUtils;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.communications.ChatServer;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.communications.ChatClient;
import seedu.addressbook.logic.Logic;
import seedu.addressbook.login.WorkWithLoginStorage;
import seedu.addressbook.ui.Gui;
import seedu.addressbook.ui.LoginWindow;
import seedu.addressbook.ui.Stoppable;
import seedu.addressbook.login.login;
import seedu.addressbook.login.Credentials;

/**
 * Main entry point to the application.
 */
public class Main extends Application implements Stoppable{

    /** Version info of the program. */

    public static final String VERSION = "MediBook - Version 1.3";
    private static Gui gui;
    public static Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        gui = new Gui(new Logic(), VERSION);
        gui.start(primaryStage, this);
        gui.startLogin(primaryStage, this);
    }

    public static void  change(){
        gui.setAccount();
        window.setScene(gui.MainScene);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
        new CommandResult("sdsafadsfadf");
        System.exit(0);
    }
}



