package Controllers;
import Game.Features.Gallery;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class StartScreenController {
    @FXML private Label errorLabel;
    @FXML private ComboBox<Gallery> galleryDropdown;

    private Gallery gallery;

    public void initialize() {
        galleryDropdown.getItems().setAll(Gallery.values());
        galleryDropdown.setValue(Gallery.PIXEL);
    }

    @FXML
    public void handleStartGame(ActionEvent event) {
        try {
            // Set scene visually
            FXMLLoader loader = new FXMLLoader(StartScreenController.class.getResource("/FX/chess-board.fxml"));
            Parent root = loader.load();
            ChessGameController controller = loader.getController();
            controller.initialize(galleryDropdown.getValue());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Funky Chess");
            stage.show();
        } catch (IOException e) {
            errorLabel.setText("ERROR: " + e.getMessage());
            errorLabel.setVisible(true);
            e.printStackTrace();
        }
    }
}
