package Game.Live;

import Game.Pieces.Assets.Color;
import Game.Pieces.Assets.Piece;
import Game.Pieces.Assets.PieceType;
import Game.Pieces.Standard.King;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashSet;

public class Player {
    private final ChessClock clock;
    private Color color;
    private HashSet<Piece> pieces;

    public Player(Color color, Duration initialTime) {
        this.color = color;
        this.clock = new ChessClock(initialTime);
        this.pieces = new HashSet<>();
    }

    // Getter for Clock
    public ChessClock getClock() {return clock;}

    // Start/stop counting down this players clock
    public void startTicking() {clock.startTicking();}
    public void stopTicking() {clock.stopTicking();}

    // Access team via player
    public void addPiece(Piece piece) {pieces.add(piece);}
    public void removePiece(Piece piece) {pieces.remove(piece);}
    public HashSet<Piece> getPieces() {return pieces;}

    // Get color
    public Color getColor() {return color;}

    // Return the King
    public King getKing() {
        for (Piece piece : pieces) {
            if (piece.getType().equals(PieceType.KING)) {
                return (King) piece;
            }
        }
        throw new IllegalArgumentException("King not found in pieces: " + pieces.toString());
    }

    // Return a string of all pieces
    public String toString() {
        ArrayList<String> parts = new ArrayList<>();
        for (Piece piece : pieces) {
            parts.add(piece.toString());
        }
        return parts.toString();
    }

    // Return true if player is currently checkmated
    public boolean isCheckmated() {
        return getKing().getValidMoves().isEmpty() && getKing().isInCheck();
    }

}
