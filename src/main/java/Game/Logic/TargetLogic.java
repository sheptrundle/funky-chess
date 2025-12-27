package Game.Logic;

import Game.Features.ChessBoard;
import Game.Features.Color;
import Game.Features.Position;
import Game.Pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class TargetLogic {

    // Return all squares that a piece can target (move to, capture, or recapture)
    public List<Position> getTargetsForPiece(Piece piece) {
        List<Position> targets = new ArrayList<>();
        Position start = piece.getPosition();
        ChessBoard board = piece.getBoard();

        switch (piece.getType()) {
            // Pawn
            case PAWN:
                int direction = (piece.getColor() == Color.WHITE) ? 1 : -1;

                int[][] diagonals = {
                        { direction, -1 },
                        { direction,  1 }
                };

                for (int[] dir : diagonals) {
                    int row = start.getRow() + dir[0];
                    int col = start.getColumn() + dir[1];
                    Position pos = new Position(row, col);

                    if (pos.isOnBoard()) {
                        targets.add(pos);
                    }
                }
                return targets;

            // Knight
            case KNIGHT:
                int[] dx = { 1,  2,  2,  1, -1, -2, -2, -1 };
                int[] dy = { 2,  1, -1, -2, -2, -1,  1,  2 };

                for (int i = 0; i < 8; i++) {
                    Position newPos = new Position(start.getRow() + dx[i], start.getColumn() + dy[i]);
                    if (newPos.isOnBoard()) {
                        targets.add(newPos);
                    }
                }
                return targets;

            // Bishop
            case BISHOP:
                int[][] bishopDirs = {
                        { 1,  1}, { 1, -1},
                        {-1,  1}, {-1, -1}
                };
                return slidingTargets(piece, bishopDirs);

            // Rook
            case ROOK:
                int[][] rookDirs = {
                        { 1,  0}, {-1,  0},
                        { 0,  1}, { 0, -1}
                };
                return slidingTargets(piece, rookDirs);

            // Queen
            case QUEEN:
                int[][] queenDirs = {
                        { 1,  0}, {-1,  0}, { 0,  1}, { 0, -1},
                        { 1,  1}, { 1, -1}, {-1,  1}, {-1, -1}
                };
                return slidingTargets(piece, queenDirs);

            // King
            case KING:
                int[][] kingDirs = {
                        { 1,  0}, {-1,  0}, { 0,  1}, { 0, -1},
                        { 1,  1}, { 1, -1}, {-1,  1}, {-1, -1}
                };

                for (int[] dir : kingDirs) {
                    int row = start.getRow() + dir[0];
                    int col = start.getColumn() + dir[1];
                    Position pos = new Position(row, col);

                    if (pos.isOnBoard()) {
                        targets.add(pos);
                    }
                }
                return targets;


            // Null piece
            default:
                throw new IllegalArgumentException("Invalid piece type to get targets for: " +  piece.getType());
        }
    }

    // Helper method for Rook, Bishop, Queen
    public List<Position> slidingTargets(Piece piece, int[][] directions) {
        List<Position> targets = new ArrayList<>();

        ChessBoard board = piece.getBoard();
        Position start = piece.getPosition();

        for (int[] dir : directions) {
            int row = start.getRow();
            int col = start.getColumn();

            while (true) {
                row += dir[0];
                col += dir[1];
                Position pos = new Position(row, col);

                // Stop sliding if went off board
                if (!pos.isOnBoard()) break;

                targets.add(pos);

                // Stop sliding once any piece is encountered
                if (board.getPieceAt(pos).exists()) break;
            }
        }
        return targets;
    }
}
