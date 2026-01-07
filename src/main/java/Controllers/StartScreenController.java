package Controllers;
import UI.Images.Gallery;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;

public class StartScreenController {
    @FXML private Label errorLabel;
    @FXML private ComboBox<Gallery> galleryDropdown;
    @FXML private ComboBox<Integer> timeDropdown;

    private Gallery gallery;

    public void initialize() {
        // Gallery
        galleryDropdown.getItems().setAll(Gallery.values());
        galleryDropdown.setValue(Gallery.PIXEL);

        // Time
        List<Integer> times = List.of(1, 5, 10);
        timeDropdown.getItems().setAll(times);
        timeDropdown.setValue(10);

    }

    @FXML
    public void handleStartGame(ActionEvent event) {
        try {
            // Set scene visually
            FXMLLoader loader = new FXMLLoader(StartScreenController.class.getResource("/FX/chess-board.fxml"));
            Parent root = loader.load();
            ChessGameController controller = loader.getController();
            controller.initialize(galleryDropdown.getValue(), Duration.minutes(timeDropdown.getValue()));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set up CSS and scene
            Scene scene = new Scene(root);
            scene.getStylesheets().add(
                    getClass().getResource("/CSS/board.css").toExternalForm()
            );

            stage.setScene(scene);
            stage.setTitle("Funky Chess");
            stage.show();
        } catch (IOException e) {
            errorLabel.setText("ERROR: " + e.getMessage());
            errorLabel.setVisible(true);
            e.printStackTrace();
        }
    }
}
