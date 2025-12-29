package Controllers;

import Game.Features.ChessBoard;
import Game.Live.LiveGame;
import Game.Pieces.Assets.Color;
import UI.CircleBuilder;
import UI.Gallery;
import Game.Pieces.NullPiece;
import Game.Pieces.Assets.Piece;
import Game.Features.Position;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.HashSet;

public class ChessGameController {
    @FXML private GridPane boardGrid;
    @FXML Label whiteClockLabel = new Label();
    @FXML Label blackClockLabel = new Label();
    private StackPane[][] squares = new StackPane[8][8];
    private Circle[][] highlights = new Circle[8][8];
    private ChessBoard chessBoard;
    private LiveGame liveGame;
    private HashSet<Position> validMoves;
    private Piece movingPiece;
    private Gallery gallery;
    private Circle checkHighlight;


    public void initialize(Gallery gallery, Duration time) {
        this.gallery = gallery;
        setChessBoard(new ChessBoard());
        liveGame = new LiveGame(time, chessBoard.getPlayer(Color.WHITE), chessBoard.getPlayer(Color.BLACK));

        // Timeline to update UI labels
        Timeline clockUpdater = new Timeline(
                new KeyFrame(Duration.seconds(0.1), e -> {
                    whiteClockLabel.setText("White Time -> " + liveGame.whiteTimeLeft());
                    blackClockLabel.setText("Black Time -> " + liveGame.blackTimeLeft());
                })
        );
        clockUpdater.setCycleCount(Animation.INDEFINITE);
        clockUpdater.play();
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

        // First IF is a SUCCESSFUL TURN
        // Move piece to this square
        System.out.println("clicked: " + clicked);
        if (validMoves.contains(clicked)) {
            System.out.println("Clicked is a valid move, moving...");
            chessBoard.movePiece(movingPiece, clicked);
            clearHighlights();

            // Update moving piece to no currently selected piece
            movingPiece = new NullPiece(new Position(-1, -1));

            System.out.println("MOVED");
            liveGame.switchTurn();

            // Check if game has ended
            if (!liveGame.isLive()) {
                liveGame.stopClocks();
                System.out.println("Checkmate");
            }
        }

        // Select a piece and highlight moves
        else {
            Piece piece = chessBoard.getPieceAt(clicked);

            // Cannot move opponent's pieces or empty spaces
            if (!piece.exists() || piece.getColor() != liveGame.getCurrentTurn()) {
                System.out.println("Clicked either null/enemy at " + piece.getPosition() + ", skipping");
            }
            // If clicked on current moving piece, remove highlights/reset moving piece and move on
            else if (clicked.equals(movingPiece.getPosition())) {
                clearHighlights();
                movingPiece = new NullPiece(new Position(-1, -1));
            }
            // Clicked on a valid piece, but not in previous moveset
            else {
                System.out.println("Highlighting new piece " + piece.getType() + " at " + piece.getPosition());
                highlightMoves(piece);
                movingPiece = piece;
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
        // Highlight checks at end
        highlightChecks();
    }

    // Highlight all valid moves for a piece
    private void highlightMoves(Piece piece) {
        System.out.println("Highlighting moves for " + piece.getColorAsString() + " " + piece.getType() + " at square " + piece.getPosition() + "...");
        // Clear old highlights first
        clearHighlights();

        for (Position pos : piece.getValidMoves()) {
            validMoves.add(pos);
            StackPane square = squares[pos.getRow()][pos.getColumn()];

            Circle circle = CircleBuilder.buildCircle("blue");

            highlights[pos.getRow()][pos.getColumn()] = circle;
            square.getChildren().add(circle);
        }
        System.out.println("DONE");
    }

    // Highlight a king piece if and only if it is in check
    private void highlightChecks() {
        // Remove old check highlight if it exists
        if (checkHighlight != null) {
            ((StackPane) checkHighlight.getParent()).getChildren().remove(checkHighlight);
            checkHighlight = null;
        }

        // Check both kings
        for (Color color : Color.values()) {
            if (chessBoard.getPlayer(color).getKing().isInCheck()) {

                Position kingPos = chessBoard.getPlayer(color).getKing().getPosition();
                StackPane square = squares[kingPos.getRow()][kingPos.getColumn()];

                checkHighlight = CircleBuilder.buildCircle("red");
                square.getChildren().add(checkHighlight);
            }
        }
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
