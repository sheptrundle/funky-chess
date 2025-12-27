package Game.Features;
import Game.Pieces.Pawn;
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
                // todo: check/mate logic goes here, gonna need other classes to make this word I bet
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
    public List<Position> rookMoveset(Piece piece, Position position, ChessBoard board) {
        int[] dxy = {1, -1};
        List<Position> validMoves = new ArrayList<>();
        MoveLogic moveLogic = new MoveLogic();

        // Expand left/right
        for (int dx : dxy) {
            int col = position.getColumn() + dx;
            int row = position.getRow();
            while (moveLogic.isValidMove(piece, board, new Position(row, col))) {
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
            while (moveLogic.isValidMove(piece, board, new Position(row, col))) {
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

    public List<Position> queenMoveset(Piece piece, Position position, ChessBoard board) {
        List<Position> queenMoves = new ArrayList<>();
        queenMoves.addAll(rookMoveset(piece, position, board));
        queenMoves.addAll(bishopMoveSet(piece, position, board));
        return queenMoves;
    }

    // Return all valid moves for a Pawn at a given position on a given board
    public List<Position> pawnMoveSet(Pawn pawn, Position position, ChessBoard board, boolean onlyTargets) {
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


    // Todo: implement these
    public void shortCastle(Piece king, Piece rook) {}
    public void longCastle(Piece king, Piece rook) {}
}
