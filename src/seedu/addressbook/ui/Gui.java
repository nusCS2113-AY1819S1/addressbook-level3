package seedu.addressbook.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seedu.addressbook.Main;
import seedu.addressbook.logic.Logic;

import java.io.File;
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
    private String version;

    public Gui(Logic logic_rms, String version) {
        this.logic = logic_rms;
        this.version = version;
    }

    public void start(Stage stage, Stoppable mainApp) throws IOException {
      // ADD DISPLAY OF STORAGE FILE PATH
        mainWindow = createMainWindow(stage, mainApp);
        mainWindow.displayRmsWelcomeMessage(version, logic.getStorageFilePath());

    }

    private MainWindow createMainWindow(Stage stage, Stoppable mainApp) throws IOException{
        FXMLLoader loader = new FXMLLoader();

        /* Note: When calling getResource(), use '/', instead of File.separator or '\\'
         * More info: http://docs.oracle.com/javase/8/docs/technotes/guides/lang/resources.html#res_name_context
         */
        loader.setLocation(Main.class.getResource("ui/mainwindow.fxml"));

        stage.setTitle(version);
        stage.setScene(new Scene(loader.load(), INITIAL_WINDOW_WIDTH, INITIAL_WINDOW_HEIGHT));
        stage.show();
        MainWindow mainWindow_rms = loader.getController();
        mainWindow_rms.setLogic(logic);
        mainWindow_rms.setMainApp(mainApp);
        return mainWindow_rms;
    }

}
