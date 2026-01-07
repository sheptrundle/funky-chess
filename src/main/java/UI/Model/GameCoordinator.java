package UI.Model;

import Game.Features.ChessBoard;
import Game.Live.LiveGame;
import Game.Logic.PieceLogic;
import Game.Pieces.Assets.Piece;
import javafx.scene.control.Label;

import java.util.HashSet;

public class GameCoordinator {

    private final TwoWayChessBoard twoWayBoard;
    private final LiveGame liveGame;
    private final BoardRenderer renderer;
    private final Label endResultLabel;

    public GameCoordinator(TwoWayChessBoard twoWayBoard,
                           LiveGame liveGame,
                           BoardRenderer renderer,
                           Label endResultLabel) {
        this.twoWayBoard = twoWayBoard;
        this.liveGame = liveGame;
        this.renderer = renderer;
        this.endResultLabel = endResultLabel;
    }

    // Flips POV of Board and updates UI
    public void flipPOV() {
        twoWayBoard.switchPOV();
        renderer.clearAllOverlays();
        renderer.updateUI();

        // Set up highlighted piece again if needed
        Piece highlightedPiece = renderer.getHighlightedPiece();
        if (highlightedPiece != null) {
            renderer.highlightPiece(highlightedPiece, "limegreen");
            renderer.highlightMoves(new HashSet<>(highlightedPiece.getValidMoves(true)), highlightedPiece);
            renderer.updateUI();
        }
    }

    // Sets the text of current turn label based on liveGame
    public void updateTurnLabel() {
        endResultLabel.setText("Current Turn = " + PieceLogic.colorToString(liveGame.getCurrentTurn()));
    }
}
