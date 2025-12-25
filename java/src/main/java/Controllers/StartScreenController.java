package Controllers;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class StartScreenController {
    @FXML private Label errorLabel;


    @FXML
    public void handleStartGame(ActionEvent event) {
        try {
            // Set scene visually
            FXMLLoader loader = new FXMLLoader(StartScreenController.class.getResource("/FX/chess-board.fxml"));
            Parent root = loader.load();
            ChessGameController controller = loader.getController();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Weird Chess");
            stage.show();
        } catch (IOException e) {
            errorLabel.setText("ERROR: " + e.getMessage());
            errorLabel.setVisible(true);
            e.printStackTrace();
        }
    }
}
