package UI.Model;

import Game.Features.ChessBoard;
import Game.Live.LiveGame;
import Game.Logic.PieceLogic;
import javafx.scene.control.Label;

public class GameCoordinator {

    private final ChessBoard board;
    private final TwoWayChessBoard twoWayBoard;
    private final LiveGame liveGame;
    private final BoardRenderer renderer;
    private final Label endResultLabel;

    public GameCoordinator(ChessBoard board,
                           TwoWayChessBoard twoWayBoard,
                           LiveGame liveGame,
                           BoardRenderer renderer,
                           Label endResultLabel) {
        this.board = board;
        this.twoWayBoard = twoWayBoard;
        this.liveGame = liveGame;
        this.renderer = renderer;
        this.endResultLabel = endResultLabel;
    }

    // Flips POV of Board and updates UI
    public void flipPOV() {
        twoWayBoard.switchPOV();
        renderer.updateUI();
    }

    // Sets the text of current turn label based on liveGame
    public void updateTurnLabel() {
        endResultLabel.setText("Current Turn = " + PieceLogic.colorToString(liveGame.getCurrentTurn()));
    }
}
