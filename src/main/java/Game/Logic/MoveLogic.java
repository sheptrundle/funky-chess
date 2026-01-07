package Game.Logic;
import Game.Features.*;
import Game.Pieces.Assets.Color;
import Game.Pieces.Assets.PieceType;
import Game.Pieces.Standard.Pawn;
import Game.Pieces.Assets.Piece;

import java.util.ArrayList;
import java.util.List;

public class MoveLogic {

    public boolean isValidMove(Piece piece, Position destination) {
        ChessBoard board = piece.getBoard();

        // Check if the destination is within bounds before anything else
        if (!destination.isOnBoard()) {return false;}

        Piece destPiece = board.getPieceAt(destination);

        // Can move to an open square or capture an opponents piece
        return !destPiece.exists() || destPiece.getColor() != piece.getColor();
    }

    // Return all valid moves for a Knight at a given position on a given board
    public List<Position> knightMoveSet(Piece piece, boolean strictlyLegal) {
        Position position = piece.getPosition();

        int[] dx = { 1,  2,  2,  1, -1, -2, -2, -1 };
        int[] dy = { 2,  1, -1, -2, -2, -1,  1,  2 };
        List<Position> validMoves = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            Position newPos = new Position(position.getRow() + dx[i], position.getColumn() + dy[i]);

            if (isValidMove(piece, newPos)) {
                // First see if this piece results in a check. Not allowed if so
                if (strictlyLegal && TargetLogic.inCheckAfterMove(piece, newPos)) {
                    continue;
                }
                validMoves.add(newPos);
            }
        }
        return validMoves;
    }

    // Return all valid moves for a Bishop at a given position on a given board
    public List<Position> bishopMoveSet(Piece piece, boolean strictlyLegal) {
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
                    // First see if this piece results in a check. Not allowed if so
                    Position pos = new Position(row, col);
                    if (strictlyLegal && TargetLogic.inCheckAfterMove(piece, pos)) {
                        row += y;
                        col += x;
                        continue;
                    }

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
    public List<Position> rookMoveset(Piece piece, boolean strictlyLegal) {
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
                // First see if this piece results in a check. Not allowed if so
                if (strictlyLegal && TargetLogic.inCheckAfterMove(piece, pos)) {
                    col += dx;
                    continue;
                }

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
                // See if this piece results in a check. Not allowed if so
                if (strictlyLegal && TargetLogic.inCheckAfterMove(piece, pos)) {
                    row += dy;
                    continue;
                }

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

    public List<Position> queenMoveset(Piece piece, boolean strictlyLegal) {
        List<Position> queenMoves = new ArrayList<>();
        queenMoves.addAll(rookMoveset(piece, strictlyLegal));
        queenMoves.addAll(bishopMoveSet(piece, strictlyLegal));
        return queenMoves;
    }

    public List<Position> kingMoveset(Piece piece, boolean strictlyLegal) {
        int[] dx = { 1,  1,  1,  0, 0, -1, -1, -1 };
        int[] dy = { -1,  0, 1, -1, 1, -1,  0,  1 };
        List<Position> validMoves = new ArrayList<>();
        Position position = piece.getPosition();
        ChessBoard board = piece.getBoard();
        Color color = piece.getColor();

        MoveLogic moveLogic = new MoveLogic();
        for (int i = 0; i < 8; i++) {
            Position newPos = new Position(position.getRow() + dx[i], position.getColumn() + dy[i]);
            if (moveLogic.isValidMove(piece, newPos)) {
                if (strictlyLegal && TargetLogic.inCheckAfterMove(piece, newPos)) continue;
                if (!TargetLogic.isTargeted(board, newPos, color)) validMoves.add(newPos);
            }
        }

        return validMoves;
    }

    // Return all valid moves for a Pawn at a given position on the real board
    public List<Position> pawnMoveSet(Pawn pawn, boolean strictlyLegal) {
        Position pos = pawn.getPosition();
        ChessBoard board = pawn.getBoard();
        Color color = pawn.getColor();

        List<Position> validMoves = new ArrayList<>();

        int row = pos.getRow();
        int col = pos.getColumn();

        // White moves up a row, black moves down a row
        int forward = (color == Color.WHITE) ? 1 : -1;

        // Diagonal captures
        int[] dx = {-1, 1};
        for (int dCol : dx) {
            Position diag = new Position(row + forward, col + dCol);
            if (!diag.isOnBoard()) continue;

            Piece diagonalPiece = board.getPieceAt(diag);
            if (diagonalPiece.exists() && diagonalPiece.getColor() != color) {
                // Check if this move leaves king in check
                if (strictlyLegal && TargetLogic.inCheckAfterMove(pawn, diag)) {
                    continue;
                }
                validMoves.add(diag);
            }
        }

        // Forward moves
        Position oneStep = new Position(row + forward, col);
        if (oneStep.isOnBoard() && !board.getPieceAt(oneStep).exists()) {
            if (!(strictlyLegal && TargetLogic.inCheckAfterMove(pawn, oneStep))) {
                validMoves.add(oneStep);

                // Two-step forward if pawn hasn't moved yet
                Position twoStep = new Position(row + 2 * forward, col);
                if (!pawn.hasMoved() && twoStep.isOnBoard() && !board.getPieceAt(twoStep).exists()) {
                    if (!(strictlyLegal && TargetLogic.inCheckAfterMove(pawn, twoStep))) {
                        validMoves.add(twoStep);
                    }
                }
            }
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
    public boolean canShortCastle(ChessBoard board, Color color) {
        return true;
    }
    public boolean canLongCastle(ChessBoard board, Color color) {
        return true;
    }
    public void shortCastle(Piece king, Piece rook) {}
    public void longCastle(Piece king, Piece rook) {}
}
