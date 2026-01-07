package Game.Logic;

import Game.Features.ChessBoard;
import Game.Features.Position;
import Game.Pieces.Assets.Color;
import Game.Pieces.Assets.Piece;
import Game.Pieces.Assets.PieceType;
import Game.Pieces.Standard.King;

import java.util.ArrayList;
import java.util.List;

public class SpecialMovesLogic {


    // Todo: implement these
    public static List<Position> availableCastles(ChessBoard board, Color color) {
        List<Position> availableCastles = new ArrayList<>();
        King king = board.getKing(color);
        System.out.println("~~~Getting available castles for " + king + "~~~");

        // Check is King has moved
        if (king.hasMoved()) {return availableCastles;}

        // Check Rook to left
        switch (color) {
            // White castles
            case WHITE:
                // Rook positions
                Piece whiteLeftRook = board.getPieceAt(new Position(0, 0));
                Piece whiteRightRook = board.getPieceAt(new Position(0, 7));

                // Short castle
                System.out.println("*Checking SHORT castle*");
                if (canCastle(king, whiteRightRook, board)) {
                    availableCastles.add(new Position(0, 6));
                }

                // Long castle
                System.out.println("*Checking LONG castle*");
                if (canCastle(king, whiteLeftRook, board)) {
                    availableCastles.add(new Position(0, 2));
                }

                break;

            // Black castles
            case BLACK:
                // Rook positions
                Piece blackLeftRook = board.getPieceAt(new Position(7, 0));
                Piece blackRightRook = board.getPieceAt(new Position(7, 7));

                // Short castle
                System.out.println("*Checking SHORT castle*");
                if (canCastle(king, blackRightRook, board)) {
                    availableCastles.add(new Position(7, 6));
                }

                // Long castle
                System.out.println("*Checking LONG castle*");
                if (canCastle(king, blackLeftRook, board)) {
                    availableCastles.add(new Position(7, 2));
                }

                break;
        }
        return availableCastles;
    }

    // Logic to see if the castle is even possible
    public static boolean canCastle(King king, Piece rook, ChessBoard board) {
        // Ensure pieces exist
        if (!king.exists() || !rook.exists()) {
            System.out.println("FAIL: either King or Rook doesnt exist");
            return false;
        }

        // Cannot castle out of check
        if (king.isInCheck()) {
            System.out.println("FAIL: Cannot castle out of check");
            return false;
        }

        // Rook piece is not actually a rook
        if (rook.getType() != PieceType.ROOK) return false;

        // Find out if its a short or long castle
        boolean isLongCastle = rook.getPosition().getColumn() < king.getPosition().getColumn();
        int increment = isLongCastle ? -1 : 1;
        int distance  = isLongCastle ? 3 : 2;

        // Check if either piece has moved already
        if (king.hasMoved() || rook.hasMoved()) {
            System.out.println("FAIL: either King or Rook has already moved");
            return false;
        }

        int currCol = king.getPosition().getColumn() + increment;

        // Check space between
        for (int i = 0; i < distance; i++) {
            Position throughCastle = new Position(king.getPosition().getRow(),  currCol);
            // Cant castle through pieces
            if (board.getPieceAt(throughCastle).exists()) {
                System.out.println("FAIL: Piece at " + throughCastle + " exists");
                return false;
            }

            // Cant castle through check
            if (TargetLogic.inCheckAfterMove(king, throughCastle)) {
                System.out.println("FAIL: Cannot castle through check");
                return false;
            }

            currCol += increment;
        }

        // All requirements met, able to castle
        System.out.println("SUCCESS");
        return true;
    }

    // Todo: Implement this!!!! Its easy!!!
    // Make the actual castle move
    public static void makeCastle(ChessBoard board, Piece king, Castle castleType) {
        Piece castledRook;
        int row = king.getPosition().getRow();

        if (castleType == Castle.SHORT) {
            castledRook = board.getPieceAt(new Position(row, 7));
            board.movePiece(king, new Position(row, 6));
            board.movePiece(castledRook, new Position(row, 5));
        }

        else if (castleType == Castle.LONG) {
            castledRook = board.getPieceAt(new Position(row, 0));
            board.movePiece(king, new Position(row, 2));
            board.movePiece(castledRook, new Position(row, 3));
        }

    }

    // Returns true if the move you are trying to make is a castle
    public static Castle isCastle(Piece piece, Position to) {
        if (piece.getType() != PieceType.KING) return Castle.NULL;

        int kingCol = piece.getPosition().getColumn();
        int toCol = to.getColumn();

        if (toCol < kingCol - 1) {
            return Castle.LONG;
        } else if (toCol > kingCol + 1) {
            return Castle.SHORT;
        }
        return Castle.NULL;
    }
}
