package GameSetup.Pieces;
import GameSetup.Piece;
import GameSetup.Position;

import java.util.ArrayList;
import java.util.List;

public class NullPiece implements Piece {
    Position position;

    public NullPiece(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }
    public List<Position> getValidMoves() {
        return new ArrayList<Position>();
    }
}
