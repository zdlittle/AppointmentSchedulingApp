package Main;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InventoryProgram extends Application {
/**
 * Javadocs Folder Path
 * Software1\javadocs
 */

    /**
     * FUTURE ENHANCEMENT
     * An enhancement to the application may be to calculate the price of the product depending on the price of the associated parts.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes the application and the inventory
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Inventory inv = new Inventory();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
        View_Controller.MainScreenController controller = new View_Controller.MainScreenController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

}

