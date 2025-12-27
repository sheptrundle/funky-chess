package Game.Live;

import java.time.Duration;

public class ChessClock {
    private Duration timeLeft;

    public ChessClock(Duration startingTime) {
        this.timeLeft = startingTime;
    }

    public void tick(Duration delta) {
        timeLeft = timeLeft.minus(delta);
    }

    public boolean isOutOfTime() {
        return timeLeft.isZero() || timeLeft.isNegative();
    }

    public Duration getTimeLeft() {
        return timeLeft;
    }
}
