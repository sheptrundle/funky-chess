package Controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class ChessGameController {
    @FXML
    private GridPane boardGrid;
    private StackPane[][] squares = new StackPane[8][8];

    public void initialize() {createBoard();}

    public void createBoard() {
        // Build the board
        boardGrid.getChildren().clear();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {

                StackPane square = new StackPane();
                square.setPrefSize(100, 100);

                boolean isLight = (row + col) % 2 == 0;
                if (isLight) {square.setStyle("-fx-background-color: #f0d9b5");}
                else {square.setStyle("-fx-background-color: #b58863");}

                squares[row][col] = square;
                boardGrid.add(square, col, row);
            }
        }

        // Place all the pieces

    }
}
