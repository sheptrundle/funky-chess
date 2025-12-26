package Controllers;

import Game.Features.ChessBoard;
import Game.Features.Gallery;
import Game.Pieces.NullPiece;
import Game.Pieces.Piece;
import Game.Features.Position;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.HashSet;

public class ChessGameController {
    @FXML private GridPane boardGrid;
    private StackPane[][] squares = new StackPane[8][8];
    private Circle[][] highlights = new Circle[8][8];
    private ChessBoard chessBoard;
    private HashSet<Position> validMoves;
    private Piece movingPiece;
    private Gallery gallery;

    public void initialize(Gallery gallery) {
        this.gallery = gallery;
        setChessBoard(new ChessBoard());
    }

    // Sets current chessboard and updates UI
    public void setChessBoard(ChessBoard board) {
        this.chessBoard = board;
        chessBoard.setGallery(gallery);
        createGrid();
        chessBoard.initialize();
        validMoves = new HashSet<>();
        movingPiece = new NullPiece(new Position(-1, -1));
        updateUI();
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
                square.setOnMouseClicked(event -> handleClick(r, c));

                // Use 7 - row because the FX board is flipped
                boardGrid.add(square, col, 7 - row);
            }
        }
    }

    private void handleClick(int row, int col) {
        Position clicked = new Position(row, col);

        // Move piece to this square
        System.out.println("clicked: " + clicked);
        if (validMoves.contains(clicked)) {
            System.out.println("Clicked is a valid move, moving...");
            chessBoard.movePiece(movingPiece, clicked);
            clearHighlights();

            // Update moving piece to no currently selected piece
            movingPiece = new NullPiece(new Position(-1, -1));

            System.out.println("MOVED");
        }

        // Select a piece and highlight moves
        else {
            Piece piece = chessBoard.getPieceAt(clicked);
            // If clicked on current moving piece, remove highlights/reset moving piece and move on
            if (clicked.equals(movingPiece.getPosition())) {
                clearHighlights();
                movingPiece = new NullPiece(new Position(-1, -1));
            }
            // Clicked on a valid piece, but not in previous moveset
            else if (piece.exists()) {
                System.out.println("Highlighting new piece " + piece.getType() + " at " + piece.getPosition());
                highlightMoves(piece);
                movingPiece = piece;
            }
            // Clicked on an invalid (null) piece, nothing happens
            else {
                System.out.println("Clicked empty square at " + piece.getPosition() + ", skipping");
            }
        }

        // Update UI at the end of every click
        updateUI();
    }

    // Shows all the UI (highlights and pieces) based on current chessBoard state
    public void updateUI() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                StackPane square = squares[row][col];
                // Remove only piece nodes
                square.getChildren().removeIf(node -> node instanceof ImageView);

                // Add current piece
                Piece piece = chessBoard.getPieceAt(new Position(row, col));
                square.getChildren().add(piece.getNode());
            }
        }
    }

    private void highlightMoves(Piece piece) {
        System.out.println("Highlighting moves for " + piece.getColorAsString() + " " + piece.getType() + " at square " + piece.getPosition() + "...");
        // Clear old highlights first
        clearHighlights();

        for (Position pos : piece.getValidMoves()) {
            validMoves.add(pos);
            StackPane square = squares[pos.getRow()][pos.getColumn()];

            Circle circle = new Circle(33);
            circle.setFill(Color.rgb(200, 150, 150, 0.6)); // light gray and semi-transparent
            circle.setMouseTransparent(true); // clicks pass through

            highlights[pos.getRow()][pos.getColumn()] = circle;
            square.getChildren().add(circle);
        }
        System.out.println("DONE");
    }

    private void clearHighlights() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (highlights[row][col] != null) {
                    squares[row][col].getChildren().remove(highlights[row][col]);
                    highlights[row][col] = null;
                }
            }
        }
        validMoves.clear();
    }
}
