package UI.Helpers;

import javafx.scene.layout.StackPane;

public class SquareSetter {
    public static void setSquare(StackPane square, int row, int col) {
        boolean isLight = (row + col) % 2 == 0;
        if (isLight) {
            square.setStyle("-fx-background-color: #f0d9b5");
        } else {
            square.setStyle("-fx-background-color: #b58863");
        }
    }
}
