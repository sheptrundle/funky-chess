package Game.Pieces;
import Game.ChessBoard;
import Game.Pieces.Features.Color;
import Game.Pieces.Features.PieceType;
import Game.Position;

import java.util.ArrayList;
import java.util.List;

public class King implements Piece {
    Position position;
    ChessBoard board;
    Color color;

    public King(Position position, ChessBoard board, Color color) {
        this.position = position;
        this.board = board;
        this.color = color;
    }

    public Position getPosition() {return position;}
    public void setPosition(Position position) {this.position = position;}
    public Color getColor() {return color;}
    public boolean exists() {return true;}
    public PieceType getType() {return PieceType.KING;}

    public List<Position> getValidMoves() {
        int[] dx = { 1,  1,  1,  0, 0, -1, -1, -1 };
        int[] dy = { -1,  0, 1, -1, 1, -1,  0,  1 };
        List<Position> validMoves = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            Position newPos = new Position(position.getRow() + dx[i], position.getColumn() + dx[i]);
            if (newPos.isOnBoard()) validMoves.add(newPos);
        }

        return validMoves;
    }
}
