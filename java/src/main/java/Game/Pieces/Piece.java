package Game.Pieces;

import Game.Pieces.Features.Color;
import Game.Pieces.Features.PieceType;
import Game.Position;
import javafx.scene.Node;

import java.util.List;

public interface Piece {
    // Returns the color of piece
    public Color getColor();

    // Return the color as a string
    public String getColorAsString();

    // Returns position of the piece
    public Position getPosition();

    // Sets new position of the piece
    public void setPosition(Position position);

    // Returns a set of valid moves from given position
    public List<Position> getValidMoves();

    // Returns false if Piece is NullPiece, otherwise true
    public boolean exists();

    // Returns what the piece is
    public PieceType getType();

    // Return node to use as an image on top of each square
    public Node getNode();

}
