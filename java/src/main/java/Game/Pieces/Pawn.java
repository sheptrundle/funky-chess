package Game.Pieces;
import Game.ChessBoard;
import Game.Pieces.Features.Color;
import Game.Pieces.Features.PieceType;
import Game.Position;

import java.util.ArrayList;
import java.util.List;

public class Pawn implements Piece {
    Position position;
    ChessBoard board;
    Color color;

    public Pawn(Position position, ChessBoard board, Color color) {
        this.position = position;
        this.board = board;
        this.color = color;
    }

    public Position getPosition() {return position;}
    public void setPosition(Position position) {this.position = position;}
    public Color getColor() {return color;}
    public boolean exists() {return true;}
    public PieceType getType() {return PieceType.PAWN;}

    public List<Position> getValidMoves() {
        List<Position> validMoves = new ArrayList<>();

        // On first row, can go up 1 or 2 spaces
        if (position.getRow() == 1) {

        }

        // Implement this algorithm
        return null;
    }
}