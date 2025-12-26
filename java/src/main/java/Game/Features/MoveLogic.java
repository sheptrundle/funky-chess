package Game.Features;
import Game.Pieces.Bishop;
import Game.Pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class MoveLogic {

    public boolean isValidMove(Piece piece, ChessBoard board, Position destination) {
        if (piece == null) throw new NullPointerException("Moving piece ~" + piece + "~ is null");

        // Check if the destination is within bounds before anything else
        if (!destination.isOnBoard()) {return false;}

        Piece destPiece = board.getPieceAt(destination);

        // Can move to an open square or capture an opponents piece
        if (!destPiece.exists() || destPiece.getColor() != piece.getColor()) {
            if (piece.getType() == PieceType.KING) {
                // todo: check/mate logic goes here
            }
            return true;
        }

        // Cannot move to position of own teams piece, or anything off board
        return false;
    }

    // Return all valid moves for a Bishop at a given position on a given board
    public List<Position> bishopMoveSet(Piece piece, Position position, ChessBoard board) {
        int[] dy = {1, -1};
        int[] dx = {1, -1};
        List<Position> validMoves = new ArrayList<>();
        MoveLogic moveLogic = new MoveLogic();

        // Expand diagonals
        for (int y : dy) {
            for (int x : dx) {
                int row = position.getRow() + y;
                int col = position.getColumn() + x;
                while (moveLogic.isValidMove(piece, board, new Position(row, col))) {
                    validMoves.add(new Position(row, col));
                    row += y;
                    col += x;
                }
            }
        }
        return validMoves;
    }

    // Return all valid moves for a Rook at a given position on a given board
    public List<Position> rookMoveset(Piece piece, Position position, ChessBoard board) {
        int[] dxy = {1, -1};
        List<Position> validMoves = new ArrayList<>();
        MoveLogic moveLogic = new MoveLogic();

        // Expand left/right
        for (int dx : dxy) {
            int col = position.getColumn() + dx;
            int row = position.getRow();
            while (moveLogic.isValidMove(piece, board, new Position(row, col))) {
                validMoves.add(new Position(row, col));
                col += dx;
            }
        }

        // Expand up/down
        for (int dy : dxy) {
            int row = position.getRow() + dy;
            int col = position.getColumn();
            while (moveLogic.isValidMove(piece, board, new Position(row, col))) {
                validMoves.add(new Position(row, col));
                row += dy;
            }
        }
        return validMoves;
    }

    public List<Position> queenMoveset(Piece piece, Position position, ChessBoard board) {
        List<Position> queenMoves = new ArrayList<>();
        queenMoves.addAll(rookMoveset(piece, position, board));
        queenMoves.addAll(bishopMoveSet(piece, position, board));
        return queenMoves;
    }

    // Todo: implement these
    public void shortCastle(Piece king, Piece rook) {}
    public void longCastle(Piece king, Piece rook) {}
}
