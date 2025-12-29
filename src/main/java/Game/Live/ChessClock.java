package Game.Live;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.animation.Animation;

public class ChessClock {

    private Duration timeLeft;
    private Timeline timeline;

    public ChessClock(Duration startingTime) {
        this.timeLeft = startingTime;

        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> tick())
        );
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    private void tick() {
        timeLeft = timeLeft.subtract(Duration.seconds(1));

        if (timeLeft.lessThanOrEqualTo(Duration.ZERO)) {
            timeLeft = Duration.ZERO;
            stopTicking();
        }
    }

    public void startTicking() {
        timeline.play();
    }

    public void stopTicking() {
        timeline.stop();
    }

    public boolean isOutOfTime() {
        return timeLeft.lessThanOrEqualTo(Duration.ZERO);
    }

    public Duration getTimeLeft() {
        return timeLeft;
    }
}
