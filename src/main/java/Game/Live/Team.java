package Game.Live;

import Game.Features.Position;
import Game.Logic.TargetLogic;
import Game.Pieces.Assets.Piece;
import Game.Pieces.Assets.PieceType;
import Game.Pieces.King;

import java.util.HashSet;

public class Team {
    private HashSet<Piece> pieces;
    private HashSet<Position> allTargets;

    public Team() {
        pieces = new HashSet<>();
        allTargets = new HashSet<>();
    }

    public void add(Piece piece) {pieces.add(piece);}
    public void remove(Piece piece) {pieces.remove(piece);}

    public boolean targets(Position position) {
        return allTargets.contains(position);
    }

    // Returns this players king piece
    public King getKing() {
        for (Piece piece : pieces) {
            if (piece.getType().equals(PieceType.KING)) {
                return (King) piece;
            }
        }
        throw new IllegalArgumentException("King not found in pieces: " + pieces.toString());
    }

    // Return all current targets
    public HashSet<Position> getAllTargets() {return allTargets;}

    public void calcAllTargets() {
        allTargets.clear();
        for (Piece piece : pieces) {
            TargetLogic targetLogic = new TargetLogic();
            allTargets.addAll(targetLogic.getTargetsForPiece(piece));
        }
    }
}
