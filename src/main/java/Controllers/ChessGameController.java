package Controllers;

import Game.Features.ChessBoard;
import Game.Live.LiveGame;
import Game.Logic.PieceLogic;
import Game.Pieces.Assets.Color;
import Game.Pieces.King;
import UI.CircleBuilder;
import UI.Gallery;
import Game.Pieces.NullPiece;
import Game.Pieces.Assets.Piece;
import Game.Features.Position;
import UI.TwoWayChessBoard;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.HashSet;

public class ChessGameController {
    @FXML private GridPane boardGrid;
    @FXML Label whiteClockLabel = new Label();
    @FXML Label blackClockLabel = new Label();
    @FXML Label endResultLabel = new Label();
    private StackPane[][] squares = new StackPane[8][8];
    private Circle[][] highlights = new Circle[8][8];
    private ChessBoard chessBoard;
    private TwoWayChessBoard twoWayBoard;
    private LiveGame liveGame;
    private HashSet<Position> validMoves;
    private Piece movingPiece;
    private Gallery gallery;
    private Piece highlightedCheck;


    public void initialize(Gallery gallery, Duration time) {
        this.gallery = gallery;
        // Set up Board, Two-Way, and Live Game
        ChessBoard board = new ChessBoard(gallery, time);
        twoWayBoard = new TwoWayChessBoard(board, Color.WHITE);
        setChessBoard(board);
        liveGame = new LiveGame(time, chessBoard.getPlayer(Color.WHITE), chessBoard.getPlayer(Color.BLACK));

        // Timeline to update UI labels
        Timeline clockUpdater = new Timeline(
                new KeyFrame(Duration.seconds(0.1), e -> {
                    whiteClockLabel.setText("White Time -> " + liveGame.whiteTimeLeft());
                    blackClockLabel.setText("Black Time -> " + liveGame.blackTimeLeft());

                    double redzone = time.toSeconds() / 10;

                    // White clock in redzone
                    if (liveGame.getTimeLeft(Color.WHITE).toSeconds() <= redzone) {
                        whiteClockLabel.setTextFill(Paint.valueOf("RED"));
                    }
                    if (liveGame.getTimeLeft(Color.BLACK).toSeconds() <= redzone) {
                        blackClockLabel.setTextFill(Paint.valueOf("RED"));
                    }

                    // Check if game has ended
                    if (!liveGame.isLive()) {
                        liveGame.stopClocks();
                        endResultLabel.setText("Game Over:\n" + PieceLogic.colorToString(liveGame.getWinner()) + " wins!" );
                    }
                })
        );
        clockUpdater.setCycleCount(Animation.INDEFINITE);
        clockUpdater.play();
    }

    // Sets current chessboard and updates UI
    public void setChessBoard(ChessBoard board) {
        this.chessBoard = board;
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
        Position clicked = twoWayBoard.uiToBoard(new Position(row, col));

        // First IF is a SUCCESSFUL TURN
        if (validMoves.contains(clicked)) {
            unhighlightPiece(movingPiece);
            chessBoard.movePiece(movingPiece, clicked);
            System.out.println("~~~" + movingPiece.getColor() + " " + movingPiece.getType() + " moved to " + movingPiece.getPosition() + "~~~");
            clearHighlights();

            // Update moving piece to no currently selected piece
            movingPiece = new NullPiece(new Position(-1, -1));

            liveGame.switchTurn();
            // Flip board and update current turn
            twoWayBoard.setPOV(liveGame.getCurrentTurn());
            endResultLabel.setText("Current Turn = " + PieceLogic.colorToString(liveGame.getCurrentTurn()));
        }

        // Not a successful move, instead update highlights/UI accordingly
        else {
            Piece piece = chessBoard.getPieceAt(clicked);

            // Cannot move opponent's pieces or empty spaces
            // Grey out the second boolean in below statement for easier testing
            if (!piece.exists() || piece.getColor() != liveGame.getCurrentTurn()) {}

            // If clicked on current moving piece, remove highlights/reset moving piece and move on
            else if (clicked.equals(movingPiece.getPosition())) {
                clearHighlights();
                unhighlightPiece(movingPiece);
                movingPiece = new NullPiece(new Position(-1, -1));
            }
            // Clicked on a valid piece, but not in previous moveset
            else {
                if (movingPiece.getPosition().isOnBoard()) {
                    unhighlightPiece(movingPiece);
                }
                highlightMoves(piece);
                movingPiece = piece;
                highlightPiece(movingPiece, "limegreen");
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
                Position modelPos = twoWayBoard.uiToBoard(new Position(row, col));
                Piece piece = chessBoard.getPieceAt(modelPos);
                square.getChildren().add(piece.getNode());
            }
        }
        // Highlight checks at end
        highlightChecks();
    }

    // Places lime green highlight on a piece
    private void highlightPiece(Piece piece, String color) {
        Position uiPos = twoWayBoard.boardToUI(piece.getPosition());
        StackPane square = squares[uiPos.getRow()][uiPos.getColumn()];
        square.setStyle("-fx-background-color: " + color);

    }

    // Removes lime green highlight on a piece
    private void unhighlightPiece(Piece piece) {
        Position uiPos = twoWayBoard.boardToUI(piece.getPosition());
        int row = uiPos.getRow();
        int col = uiPos.getColumn();
        StackPane square = squares[row][col];

        boolean isLight = (row + col) % 2 == 0;
        if (isLight) {square.setStyle("-fx-background-color: #f0d9b5");}
        else {square.setStyle("-fx-background-color: #b58863");}
    }

    // Highlight all valid moves for a piece
    private void highlightMoves(Piece piece) {
        // Clear old highlights first
        clearHighlights();

        for (Position boardPos : piece.getValidMoves()) {
            Position uiPos = twoWayBoard.boardToUI(boardPos);

            validMoves.add(boardPos);

            StackPane square = squares[uiPos.getRow()][uiPos.getColumn()];

            // Place circle on highlighted/valid moves
            Circle circle = CircleBuilder.buildCircle("blue");
            highlights[uiPos.getRow()][uiPos.getColumn()] = circle;
            square.getChildren().add(circle);
        }
    }

    // Highlight a king piece if and only if it is in check
    private void highlightChecks() {
        // Remove old check highlight if it exists
        if (highlightedCheck != null) {
            unhighlightPiece(highlightedCheck);
            highlightedCheck = null;
        }

        // Check both kings
        for (Color color : Color.values()) {
            King king = chessBoard.getKing(color);
            if (king.isInCheck()) {
                highlightPiece(king, "red");
            } else {
                unhighlightPiece(king);
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
