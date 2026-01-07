package Game.Live;

import Game.Features.ChessBoard;
import Game.Logic.PieceLogic;
import Game.Pieces.Assets.Color;
import Game.Pieces.Assets.PieceType;
import javafx.application.Platform;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class LiveGame {
    Player whitePlayer;
    Player blackPlayer;
    ChessBoard board;
    Color currentTurn;
    boolean isLive;

    // Start a live game and begin ticking for white
    public LiveGame(ChessBoard board) {
        this.board = board;
        whitePlayer = board.getPlayer(Color.WHITE);
        blackPlayer = board.getPlayer(Color.BLACK);
        currentTurn = Color.WHITE;
        whitePlayer.startTicking();
        isLive = true;
    }

    // Listeners for end-of-game events
    private final List<Runnable> gameEndListeners = new ArrayList<>();

    public void addGameEndListener(Runnable listener) {
        gameEndListeners.add(listener);
    }

    private void triggerGameEnd() {
        for (Runnable listener : gameEndListeners) {
            Platform.runLater(listener);
        }
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

    // Ensure neither player is checkmated, then
    public void checkCheckmates() {
        boolean wasLive = isLive;
        isLive = !(board.isCheckmated(Color.WHITE) || board.isCheckmated(Color.BLACK));
        if (wasLive && !isLive) {
            triggerGameEnd();
        }
    }
    // Ensure both clocks are >0
    public void checkTimes() {
        // update whiteTimeLeft and blackTimeLeft
        if (whitePlayer.getClock().getTimeLeft().toSeconds() <= 0 || blackPlayer.getClock().getTimeLeft().toSeconds() <= 0) {
            isLive = false;
            triggerGameEnd();
        }
    }
    public boolean isLive() {
        return isLive;
    }

    public Color getWinner() {
        if (isLive()) {
            throw new IllegalArgumentException("Cannot determine winner of a game that is still live");
        }

        if (board.isCheckmated(Color.WHITE) || whitePlayer.getClock().isOutOfTime()) {
            return Color.BLACK;
        } else {
            return Color.WHITE;
        }
    }
}
