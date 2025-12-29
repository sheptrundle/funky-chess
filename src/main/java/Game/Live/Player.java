package Game.Live;

import Game.Features.Position;
import Game.Pieces.Assets.Color;
import Game.Pieces.Assets.Piece;
import Game.Pieces.King;
import javafx.util.Duration;

import java.util.HashSet;

public class Player {
    private final ChessClock clock;
    private Team team;
    private Color color;

    public Player(Color color, Duration initialTime) {
        this.color = color;
        this.team = new Team();
        this.clock = new ChessClock(initialTime);
    }

    // Getters and setters for team
    public Team getTeam() {return team;}
    public void setTeam(Team team) {this.team = team;}

    // Getter for Clock
    public ChessClock getClock() {return clock;}

    // Start counting down this players clock
    public void startTicking() {

    }

    // Access team via player
    public void addPiece(Piece piece) {team.add(piece);}
    public void removePiece(Piece piece) {team.remove(piece);}
    public boolean targets(Position position) {return team.targets(position);}
    public HashSet<Position> getAllTargets() {return team.getAllTargets();}
    public void calcAllTargets() {team.calcAllTargets();}
    public King getKing() {return team.getKing();}

}
