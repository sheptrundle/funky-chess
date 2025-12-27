package Game.Features;

import Game.Pieces.Pawn;
import Game.Pieces.Piece;
import java.util.HashSet;

public class Team {
    private HashSet<Piece> pieces;

    public Team() {
        pieces = new HashSet<>();
    }

    public void add(Piece piece) {pieces.add(piece);}
    public void remove(Piece piece) {pieces.remove(piece);}

    public HashSet<Position> getAllTargets() {
        HashSet<Position> targets = new HashSet<>();
        for (Piece piece : pieces) {
            // Only look at diagonal targets for pawns
            if (piece.getType() == PieceType.PAWN) {
                MoveLogic moveLogic = new MoveLogic();
                targets.addAll(moveLogic.pawnMoveSet((Pawn) piece, piece.getPosition(), piece.getBoard(), true));
            }
            // Normal piece, look at all moves
            else {
                targets.addAll(piece.getValidMoves());
            }
        }
        return targets;
    }
}
