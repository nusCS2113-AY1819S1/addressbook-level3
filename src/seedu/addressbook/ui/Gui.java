package seedu.addressbook.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seedu.addressbook.logic.Logic;
import seedu.addressbook.Main;
import seedu.addressbook.login.login;

import java.io.IOException;

/**
 * The GUI of the App
 */
public class Gui {

    /** Offset required to convert between 1-indexing and 0-indexing.  */
    public static final int DISPLAYED_INDEX_OFFSET = 1;

    public static final int INITIAL_WINDOW_WIDTH = 800;
    public static final int INITIAL_WINDOW_HEIGHT = 600;
    private final Logic logic;

    private MainWindow mainWindow;
    public Scene MainScene, LoginScene;
    private LoginWindow loginWindow;
    private String version;

    public Gui(Logic logic, String version) {
        this.logic = logic;
        this.version = version;
    }

    public void start(Stage stage, Stoppable mainApp) throws IOException {
        mainWindow = createMainWindow(stage, mainApp);
        mainWindow.displayWelcomeMessage(version, logic.getStorageFilePath());
    }

    private MainWindow createMainWindow(Stage stage, Stoppable mainApp) throws IOException{
        FXMLLoader loader = new FXMLLoader();

        /* Note: When calling getResource(), use '/', instead of File.separator or '\\'
         * More info: http://docs.oracle.com/javase/8/docs/technotes/guides/lang/resources.html#res_name_context
         */
        loader.setLocation(Main.class.getResource("ui/mainwindow.fxml"));

        stage.setTitle(version);
        MainScene = new Scene(loader.load(), INITIAL_WINDOW_WIDTH, INITIAL_WINDOW_HEIGHT);
        stage.setScene(MainScene);
        stage.show();
        MainWindow mainWindow = loader.getController();
        mainWindow.setLogic(logic);
        mainWindow.setMainApp(mainApp);
        return mainWindow;
    }
    public void startLogin(Stage stage, Stoppable mainApp) throws IOException {
        loginWindow = createLoginWindow(stage, mainApp);
    }

    private LoginWindow createLoginWindow(Stage stage, Stoppable mainApp) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("ui/signin.fxml"));

        stage.setTitle("Welcome to MediBook");
        LoginScene = new Scene(loader.load(), INITIAL_WINDOW_WIDTH,INITIAL_WINDOW_HEIGHT);
        stage.setScene(LoginScene);
        stage.show();
        LoginWindow loginWindow = loader.getController();
        loginWindow.setMainApp(mainApp);
        return loginWindow;
    }

    public void setAccount(){
        this.mainWindow.setAccount(login.getUsernameF(), login.getAccesslevelF());
    }

}
