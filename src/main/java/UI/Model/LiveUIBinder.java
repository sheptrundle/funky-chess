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

    public LiveUIBinder(LiveGame liveGame,
                        Duration time,
                        Label whiteClock,
                        Label blackClock,
                        Label endResultLabel) {

        timeline = new Timeline();

        // Register reactive listener for game end
        liveGame.addGameEndListener(() -> {
            timeline.stop();           // stop the Timeline
            liveGame.stopClocks();     // stop internal clocks
            endResultLabel.setText(
                    "Game Over:\n" + PieceLogic.colorToString(liveGame.getWinner()) + " wins!"
            );
        });

        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(0.1), e -> {

                    // Continuously update clocks
                    whiteClock.setText("White Time -> " + liveGame.whiteTimeLeft());
                    blackClock.setText("Black Time -> " + liveGame.blackTimeLeft());

                    double redzone = time.toSeconds() / 10;

                    // White clock in redzone
                    if (liveGame.getTimeLeft(Color.WHITE).toSeconds() <= redzone) {
                        whiteClock.setTextFill(Paint.valueOf("RED"));
                    }
                    // Black clock in redzone
                    if (liveGame.getTimeLeft(Color.BLACK).toSeconds() <= redzone) {
                        blackClock.setTextFill(Paint.valueOf("RED"));
                    }

                    // Poll for time expiration
                    liveGame.checkTimes();
                })
        );

        timeline.setCycleCount(Animation.INDEFINITE);
    }

    public void start() {
        timeline.play();
    }
}
