package Game.Pieces.Assets;

import Game.Features.ChessBoard;
import Game.Features.Position;
import javafx.scene.Node;

import java.util.List;

public interface Piece {
    // Returns the color of piece
    public Color getColor();

    // Return the color as a string
    public String getColorAsString();

    // Return opposite color
    public Color getOppositeColor();

    // Returns position of the piece
    public Position getPosition();

    // Sets new position of the piece
    public void setPosition(Position position);

    // Returns the chessboard that piece is using
    public ChessBoard getBoard();

    // Returns a set of valid moves from given position
    public List<Position> getValidMoves();

    // Returns true if the piece targets or attacks a given position
    public boolean targets(Position position);

    // Returns false if Piece is NullPiece, otherwise true
    public boolean exists();

    // Returns what the piece is
    public PieceType getType();

    // Return node to use as an image on top of each square
    public Node getNode();

    // Return or set if the piece has moved (really only useful for Pawns)
    public boolean hasMoved();
    public void setHasMoved(boolean hasMoved);

    // Format {color pieceType @ position}
    public String toString();


}
