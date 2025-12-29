package Game.Live;

import Game.Logic.PieceLogic;
import Game.Pieces.Assets.Color;
import javafx.util.Duration;

public class LiveGame {
    Player whitePlayer;
    Player blackPlayer;
    Color currentTurn;

    // Start a live game and begin ticking for white
    public LiveGame(Duration time, Player whitePlayer, Player blackPlayer) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        currentTurn = Color.WHITE;
        whitePlayer.startTicking();
    }

    // Returns the current turn as a Color
    public Color getCurrentTurn() {return currentTurn;}

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

    // Time left for each player, String formatted
    public String whiteTimeLeft() {return whitePlayer.getClock().getTimeLeftString();}
    public String blackTimeLeft() {return blackPlayer.getClock().getTimeLeftString();}

    // Switches the current turn and starts/stops ticking for both players
    public void switchTurn() {
        getPlayer(true).stopTicking();
        getPlayer(false).startTicking();
        currentTurn = PieceLogic.getOppositeColor(currentTurn);
    }

    // Stops both clocks
    public void stopClocks() {
        blackPlayer.stopTicking();
        whitePlayer.stopTicking();
    }

    // Returns the current time
    public Duration getTimeLeft(Color color) {
        if (color == Color.WHITE) {
            return whitePlayer.getClock().getTimeLeft();
        } else {
            return blackPlayer.getClock().getTimeLeft();
        }
    }

    // Ensure neither player is checkmated, then ensure both clocks are >0
    public boolean isLive() {
        return (!whitePlayer.isCheckmated() && !blackPlayer.isCheckmated())
                && !whitePlayer.getClock().isOutOfTime()
                && !blackPlayer.getClock().isOutOfTime();
    }
}
