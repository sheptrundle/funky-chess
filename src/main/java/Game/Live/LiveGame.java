package Game.Live;

import Game.Logic.PieceLogic;
import Game.Pieces.Assets.Color;
import javafx.util.Duration;

public class LiveGame {
    Player whitePlayer;
    Player blackPlayer;
    Color currentTurn;

    // Start a live game and begin ticking for white
    public LiveGame(Duration time) {
        whitePlayer = new Player(Color.WHITE,  time);
        blackPlayer = new Player(Color.BLACK, time);
        currentTurn = Color.WHITE;
        whitePlayer.startTicking();
    }

    // Returns the correct player based on current turn
    public Player getPlayer(boolean isCurrent) {
        if (currentTurn == Color.WHITE) {
            if (isCurrent) {
                return whitePlayer;
            } else {
                return blackPlayer;
            }
        } else {
            if (isCurrent) {
                return blackPlayer;
            } else {
                return whitePlayer;
            }
        }
    }

    // Switches the current turn and starts/stops ticking for both players
    public void switchTurn() {
        getPlayer(true).stopTicking();
        getPlayer(false).startTicking();
        currentTurn = PieceLogic.getOppositeColor(currentTurn);
    }

    // Returns the current time
    public Duration getTimeLeft(Color color) {
        if (color == Color.WHITE) {
            return whitePlayer.getClock().getTimeLeft();
        } else {
            return blackPlayer.getClock().getTimeLeft();
        }
    }

}
