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

    // Start/stop counting down this players clock
    public void startTicking() {clock.startTicking();}
    public void stopTicking() {clock.stopTicking();}

    // Access team via player
    public void addPiece(Piece piece) {team.add(piece);}
    public void removePiece(Piece piece) {team.remove(piece);}
    public boolean targets(Position position) {return team.targets(position);}
    public HashSet<Position> getAllTargets() {return team.getAllTargets();}
    public void calcAllTargets() {team.calcAllTargets();}
    public King getKing() {return team.getKing();}

    // Return true if player is currently checkmated
    public boolean isCheckmated() {
        return team.getKing().getValidMoves().isEmpty() && team.getKing().isInCheck();

        /*
        Todo: well what if a king has no valid moves but it has a piece that can block?
        Idea: make a method in chessboard that returns a copy of a chessboard after ONE specific move is made
        then run every possible move and see if any of them create a state on the new chessboard where the king is NOT in check
        */

        /*
        Todo: Also, when the king is in check, we cannot allow other pieces to move unless it gets king out of check
         */
    }

}
