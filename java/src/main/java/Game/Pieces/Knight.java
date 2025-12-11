package Game.Pieces;
import Game.ChessBoard;
import Game.Pieces.Features.Color;
import Game.Pieces.Features.MoveLogic;
import Game.Pieces.Features.PieceType;
import Game.Position;

import java.util.ArrayList;
import java.util.List;

public class Knight implements Piece {
    Position position;
    ChessBoard board;
    Color color;

    public Knight(Position position, ChessBoard board, Color color) {
        this.position = position;
        this.board = board;
        this.color = color;
    }

    public Position getPosition() {return position;}
    public void setPosition(Position position) {this.position = position;}
    public Color getColor() {return color;}
    public boolean exists() {return true;}
    public PieceType getType() {return PieceType.KNIGHT;}

    // Return a list of all the valid moves from knight at its current position
    public List<Position> getValidMoves() {
        MoveLogic moveLogic = new MoveLogic();

        int[] dx = { 1,  2,  2,  1, -1, -2, -2, -1 };
        int[] dy = { 2,  1, -1, -2, -2, -1,  1,  2 };
        List<Position> validMoves = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            Position newPos = new Position(position.getRow() + dx[i], position.getColumn() + dy[i]);
            if (moveLogic.isValidMove(this, board, newPos)) {
                validMoves.add(newPos);
            }
        }

        return validMoves;
    }
}