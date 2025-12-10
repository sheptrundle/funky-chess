package GameSetup;

import java.util.List;

public interface Piece {

    public Position getPosition();
    public List<Position> getValidMoves();

}
