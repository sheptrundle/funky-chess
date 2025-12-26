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

    // Sets current chessboard and updates UI
    public void setChessBoard(ChessBoard board) {
        this.chessBoard = board;
        createGrid();
        chessBoard.initialize();
        updateUI();
    }

    // Places a piece into the squares array
    public void placePieceIntoSquare(Piece piece, int row, int col) {
        StackPane square = squares[row][col];
        square.getChildren().add(piece.getNode());
    }

    // Set up the square grid
    public void createGrid() {
        // Build the grid
        boardGrid.getChildren().clear();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                final int r = row;
                final int c = col;

                StackPane square = new StackPane();
                square.setPrefSize(100, 100);

                boolean isLight = (row + col) % 2 == 0;
                if (isLight) {square.setStyle("-fx-background-color: #f0d9b5");}
                else {square.setStyle("-fx-background-color: #b58863");}

                squares[row][col] = square;
                square.setOnMouseClicked(event -> handleFirstClick(r, c)); //todo: fix this

                // Use 7 - row because the FX board is flipped
                boardGrid.add(square, col, 7 - row);
            }
        }
    }
    private void handleFirstClick(int row, int col) {
        Position clicked = new Position(row, col);
        // First click: select a piece
        Piece piece = chessBoard.getPieceAt(clicked);
        if (piece.exists()) {
            highlightMoves(piece);
        }
    }

    // Shows all the UI (highlights and pieces) based on current chessBoard state
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

    private void highlightMoves(Piece piece) {
        for (Position p : piece.getValidMoves()) {
            squares[p.getRow()][p.getColumn()]
                    .setStyle("-fx-border-color: green; -fx-border-width: 3;");
        }
    }
}
