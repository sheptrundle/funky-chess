package UI.Model;

import Game.Features.ChessBoard;
import Game.Features.Position;
import Game.Live.BoardInteractionHandler;
import Game.Pieces.Assets.Color;
import Game.Pieces.Standard.King;
import Game.Pieces.Assets.Piece;
import UI.Helpers.SquareSetter;
import UI.Helpers.SquareState;
import UI.Images.CircleBuilder;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Stack;

public class BoardRenderer {

    private final GridPane boardGrid;
    private final ChessBoard chessBoard;
    private final TwoWayChessBoard twoWayBoard;

    private final StackPane[][] squares = new StackPane[8][8];
    private final Circle[][] highlights = new Circle[8][8];

    private Piece highlightedCheck;
    private Piece highlightedPiece;

    public BoardRenderer(GridPane boardGrid, ChessBoard chessBoard, TwoWayChessBoard twoWayBoard) {
        this.boardGrid = boardGrid;
        this.chessBoard = chessBoard;
        this.twoWayBoard = twoWayBoard;
    }

    // Set up the square grid
    public void createGrid(BoardInteractionHandler handler) {
        // Build the grid
        boardGrid.getChildren().clear();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                final int r = row;
                final int c = col;

                StackPane square = new StackPane();
                square.setPrefSize(100, 100);

                // Apply BASE color only
                SquareSetter.applyBaseColor(square, r, c, Color.WHITE);

                // Ensure no overlay state at creation
                SquareSetter.applyState(square, SquareState.NORMAL);

                squares[row][col] = square;
                square.setOnMouseClicked(e -> handler.handleClick(r, c));

                // Use 7 - row because the FX board is flipped
                boardGrid.add(square, col, 7 - row);
            }
        }
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

    public Piece getHighlightedPiece() {
        return highlightedPiece;
    }

    // Places colored highlight on a piece and updates memory
    public void highlightPiece(Piece piece, String color) {
        Position uiPos = twoWayBoard.boardToUI(piece.getPosition());
        StackPane square = squares[uiPos.getRow()][uiPos.getColumn()];
        SquareSetter.applyState(square, SquareState.SELECTED);

        // Update in memory
        highlightedPiece = piece;
    }

    // Just places the highlight, no memory involved
    public void highlightPieceUI(Piece piece, String color) {
        Position uiPos = twoWayBoard.boardToUI(piece.getPosition());
        StackPane square = squares[uiPos.getRow()][uiPos.getColumn()];
        SquareSetter.applyState(square, SquareState.SELECTED);
    }

    // Removes highlight on a piece
    public void unhighlightPiece(Piece piece) {
        Position uiPos = twoWayBoard.boardToUI(piece.getPosition());
        StackPane square = squares[uiPos.getRow()][uiPos.getColumn()];
        SquareSetter.applyState(square, SquareState.NORMAL);

        // Update in memory as well
        highlightedPiece = null;
    }

    // Just removes the highlight, no memory involved
    public void unhighlightPieceUI(Piece piece) {
        Position uiPos = twoWayBoard.boardToUI(piece.getPosition());
        StackPane square = squares[uiPos.getRow()][uiPos.getColumn()];
        SquareSetter.applyState(square, SquareState.NORMAL);
    }

    // Highlight all valid moves for a piece
    public void highlightMoves(HashSet<Position> validMoves, Piece piece) {
        // Clear old highlights first
        clearHighlights(validMoves);

        for (Position boardPos : piece.getValidMoves(true)) {
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
    public void highlightChecks() {

        // Clear previous check highlight
        if (highlightedCheck != null) {
            Position uiPos = twoWayBoard.boardToUI(highlightedCheck.getPosition());
            StackPane square = squares[uiPos.getRow()][uiPos.getColumn()];
            SquareSetter.applyState(square, SquareState.NORMAL);
            highlightedCheck = null;
        }

        // Check both kings
        for (Color color : Color.values()) {
            King king = chessBoard.getKing(color);

            Position uiPos = twoWayBoard.boardToUI(king.getPosition());
            StackPane square = squares[uiPos.getRow()][uiPos.getColumn()];

            if (king.isInCheck()) {
                SquareSetter.applyState(square, SquareState.CHECK);
                highlightedCheck = king;
            }
        }
    }


    // Clear current highlights for validMoves
    public void clearHighlights(HashSet<Position> validMoves) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (highlights[r][c] != null) {
                    squares[r][c].getChildren().remove(highlights[r][c]);
                    highlights[r][c] = null;
                }
            }
        }
        validMoves.clear();
    }

    // Clear all overlays on entire board
    public void clearAllOverlays() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                // Remove overlays without calling highlight/unhighlight
                StackPane square = squares[r][c];
                SquareSetter.applyState(square, SquareState.NORMAL);
                square.getChildren().remove(highlights[r][c]);
            }
        }
    }
}

