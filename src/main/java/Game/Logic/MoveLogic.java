package Game.Logic;
import Game.Features.*;
import Game.Pieces.Pawn;
import Game.Pieces.Piece;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.List;

public class MoveLogic {

    public boolean isValidMove(Piece piece, Position destination) {
        ChessBoard board = piece.getBoard();

        if (piece == null) throw new NullPointerException("Moving piece ~" + piece + "~ is null");

        // Check if the destination is within bounds before anything else
        if (!destination.isOnBoard()) {return false;}

        Piece destPiece = board.getPieceAt(destination);

        // Can move to an open square or capture an opponents piece
        if (!destPiece.exists() || destPiece.getColor() != piece.getColor()) {
            return true;
        }

        // Cannot move to position of own teams piece, or anything off board
        return false;
    }

    // Return true if a move to a new position is targeted by the other team
    public boolean isTargeted(Position destination, Team otherTeam) {
        return otherTeam.targets(destination);
    }

    // Return all valid moves for a Knight at a given position on a given board
    public List<Position> knightMoveSet(Piece piece) {
        Position position = piece.getPosition();
        ChessBoard board = piece.getBoard();

        int[] dx = { 1,  2,  2,  1, -1, -2, -2, -1 };
        int[] dy = { 2,  1, -1, -2, -2, -1,  1,  2 };
        List<Position> validMoves = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            Position newPos = new Position(position.getRow() + dx[i], position.getColumn() + dy[i]);
            if (isValidMove(piece, newPos)) {
                validMoves.add(newPos);
            }
        }
        return validMoves;
    }

    // Return all valid moves for a Bishop at a given position on a given board
    public List<Position> bishopMoveSet(Piece piece) {
        Position position = piece.getPosition();
        ChessBoard board = piece.getBoard();

        int[] dy = {1, -1};
        int[] dx = {1, -1};
        List<Position> validMoves = new ArrayList<>();
        MoveLogic moveLogic = new MoveLogic();

        // Expand diagonals
        for (int y : dy) {
            for (int x : dx) {
                int row = position.getRow() + y;
                int col = position.getColumn() + x;
                while (moveLogic.isValidMove(piece, new Position(row, col))) {
                    Position pos = new Position(row, col);
                    validMoves.add(pos);
                    // Break if found capturable piece
                    if (board.getPieceAt(pos).exists()) {
                        break;
                    }

                    row += y;
                    col += x;
                }
            }
        }
        return validMoves;
    }

    // Return all valid moves for a Rook at a given position on a given board
    public List<Position> rookMoveset(Piece piece) {
        Position position = piece.getPosition();
        ChessBoard board = piece.getBoard();

        int[] dxy = {1, -1};
        List<Position> validMoves = new ArrayList<>();
        MoveLogic moveLogic = new MoveLogic();

        // Expand left/right
        for (int dx : dxy) {
            int col = position.getColumn() + dx;
            int row = position.getRow();
            while (moveLogic.isValidMove(piece, new Position(row, col))) {
                Position pos = new Position(row, col);
                validMoves.add(pos);
                // Break if found capturable piece
                if (board.getPieceAt(pos).exists()) {
                    break;
                }

                col += dx;
            }
        }

        // Expand up/down
        for (int dy : dxy) {
            int row = position.getRow() + dy;
            int col = position.getColumn();
            while (moveLogic.isValidMove(piece, new Position(row, col))) {
                Position pos = new Position(row, col);
                validMoves.add(pos);
                // Break if found capturable piece
                if (board.getPieceAt(pos).exists()) {
                    break;
                }

                row += dy;
            }
        }
        return validMoves;
    }

    public List<Position> queenMoveset(Piece piece) {
        List<Position> queenMoves = new ArrayList<>();
        queenMoves.addAll(rookMoveset(piece));
        queenMoves.addAll(bishopMoveSet(piece));
        return queenMoves;
    }

    // Return all valid moves for a Pawn at a given position on a given board
    public List<Position> pawnMoveSet(Pawn pawn, boolean onlyTargets) {
        Position position = pawn.getPosition();
        ChessBoard board = pawn.getBoard();

        List<Position> validMoves = new ArrayList<>();
        ChessBoard useBoard;
        Position usePos;
        boolean needsFlip = false;

        if (pawn.getColor() == Color.WHITE) {
            useBoard = board;
            usePos = position;
        } else {
            useBoard = board.flipped();
            usePos = position.flipped();
            needsFlip = true;
        }

        int row = usePos.getRow();
        int col = usePos.getColumn();

        // Diagonal captures
        int[] dx = {-1, 1};
        for (int d : dx) {
            Position diag = new Position(row + 1, col + d);
            if (!diag.isOnBoard()) continue;

            Piece target = useBoard.getPieceAt(diag);
            if (target.exists() && target.getColor() != pawn.getColor()) {
                validMoves.add(diag);
            }
        }

        // If specified to return only the targets squares, just return now
        if (onlyTargets) {
            return validMoves;
        }

        // Forward moves
        for (int i = 1; i <= 2; i++) {
            Position to = new Position(row + i, col);
            if (!to.isOnBoard()) break;

            if (!useBoard.getPieceAt(to).exists()) {
                validMoves.add(to);
            } else {
                break;
            }
            if (pawn.hasMoved()) break;
        }

        // Flip back if needed (black)
        if (needsFlip) {
            List<Position> flipped = new ArrayList<>();
            for (Position p : validMoves) {
                flipped.add(p.flipped());
            }
            return flipped;
        }
        return validMoves;
    }

    public boolean pawnPromotion(Piece piece, Position to) {
        return piece.getType() == PieceType.PAWN && (to.getRow() == 7 || to.getRow() == 0);
    }

    public boolean isCapture(Piece piece, Position to, ChessBoard board) {
        Piece other =  board.getPieceAt(to);
        return (other.exists() && piece.getColor() != other.getColor());
    }


    // Todo: implement these
    public void shortCastle(Piece king, Piece rook) {}
    public void longCastle(Piece king, Piece rook) {}
}
