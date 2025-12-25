package Controllers;

import Game.Features.ChessBoard;
import Game.Pieces.Piece;
import Game.Features.Position;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class ChessGameController {
    @FXML
    private GridPane boardGrid;
    private StackPane[][] squares = new StackPane[8][8];
    private ChessBoard chessBoard;

    public void initialize() {
        setChessBoard(new ChessBoard());
    }

    public void setChessBoard(ChessBoard board) {
        this.chessBoard = board;
        createGrid();
        chessBoard.initialize();
        updateUI();
    }

    public void placePiece(Piece piece, int row, int col) {
        StackPane square = squares[row][col];
        square.getChildren().add(piece.getNode());
    }

    public void createGrid() {
        // Build the grid
        boardGrid.getChildren().clear();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {

                StackPane square = new StackPane();
                square.setPrefSize(100, 100);

                boolean isLight = (row + col) % 2 == 0;
                if (isLight) {square.setStyle("-fx-background-color: #f0d9b5");}
                else {square.setStyle("-fx-background-color: #b58863");}

                squares[row][col] = square;

                // Use 7 - row because the FX board is flipped
                boardGrid.add(square, col, 7 - row);
            }
        }
    }

    public void updateUI() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                StackPane square = squares[row][col];
                square.getChildren().clear();

                Piece piece = chessBoard.getPieceAt(new Position(row, col));
                square.getChildren().add(piece.getNode());
            }
        }
    }
}
