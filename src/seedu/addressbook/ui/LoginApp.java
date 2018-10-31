package seedu.addressbook.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seedu.addressbook.ui.LoginWindow.*;

//import org.gradle.internal.impldep.org.apache.commons.lang.WordUtils;


/**
 * Main entry point to the application.
 */
public class LoginApp extends Application implements Stoppable{

    /** Version info of the program. */

    public static final String VERSION = "MediBook - Version 1.3";
    private Gui gui;

//    public static final String VERSION = "AddressBook Level 3 - Version 1.0";
//    private Gui gui;
//
//    @Override
//    public void start(Stage primaryStage) throws Exception{
//        gui = new Gui(new Logic(), VERSION);
//        gui.start(primaryStage, this);
//    }
//
//    @Override
//    public void stop() throws Exception {
//        super.stop();
//        Platform.exit();
//        System.exit(0);
//    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(LoginApp.class.getResource("ui/signin.fxml"));
        primaryStage.setTitle("Welcome to MediBook");
        primaryStage.setScene(new Scene(loader.load(), 800,600));
        primaryStage.show();
    }

    public static void main(String[] args) {

//        if(login.main()) {
//            WorkWithLoginStorage.addLogin(new Credentials("S1234567A", "S1234567a"));
//            try {
//                launch(args);
//            } catch (IllegalValueException e){
//                System.out.println(e);
//            }
            launch(args);
//        }else{
            System.exit(0);
        }
    }



