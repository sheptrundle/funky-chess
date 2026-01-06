package UI.Model;

import Game.Live.LiveGame;
import Game.Logic.PieceLogic;
import Game.Pieces.Assets.Color;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

public class LiveUIBinder {

    private final Timeline timeline;
    private Label endResultLabel;

    public LiveUIBinder(LiveGame liveGame, Duration time, Label whiteClock, Label blackClock, Label endResultLabel) {

        timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.1), e -> {

                    // Continuously update clocks
                    whiteClock.setText("White Time -> " + liveGame.whiteTimeLeft());
                    blackClock.setText("Black Time -> " + liveGame.blackTimeLeft());

                    double redzone = time.toSeconds() / 10;

                    // White clock in the redzone
                    if (liveGame.getTimeLeft(Color.WHITE).toSeconds() <= redzone) {
                        whiteClock.setTextFill(Paint.valueOf("RED"));
                    }
                    // Black clock in the redzone
                    if (liveGame.getTimeLeft(Color.BLACK).toSeconds() <= redzone) {
                        blackClock.setTextFill(Paint.valueOf("RED"));
                    }

                    // Continuously check if game has ended
                    if (!liveGame.isLive()) {
                        liveGame.stopClocks();
                        endResultLabel.setText(
                                "Game Over:\n" + PieceLogic.colorToString(liveGame.getWinner()) + " wins!"
                        );
                    }
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    public void start() {
        timeline.play();
    }

    public void switchCurrentTurnLabel(LiveGame liveGame) {
        endResultLabel.setText("Current Turn = " + PieceLogic.colorToString(liveGame.getCurrentTurn()));
    }
}
