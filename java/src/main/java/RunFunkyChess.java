import Controllers.StartScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class RunFunkyChess extends Application {

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FX/start-screen.fxml"));
        Parent root = loader.load();
        StartScreenController controller = loader.getController();

        // Set scene
        Scene scene = new Scene(root);
        stage.setTitle("Funky Chess");
        stage.setScene(scene);
        stage.show();


    }
}
