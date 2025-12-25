package Game.Features;

import Game.Pieces.Piece;

public class MoveLogic {

    public boolean isValidMove(Piece piece, ChessBoard board, Position destination) {
        if (piece == null) throw new NullPointerException("Moving piece ~" + piece + "~ is null");
        Piece destPiece = board.getPieceAt(destination);

        // Can move to an open square or capture an opponents piece
        if (destination.isOnBoard()) {
            if (!destPiece.exists() || destPiece.getColor() != piece.getColor()) {
                if (piece.getType() == PieceType.KING) {
                    // check/mate logic goes here
                }
                return true;
            }
        }

        // Cannot move to position of own teams piece, or anything off board
        return false;
    }

    // Todo: implement these
    public void shortCastle(Piece king, Piece rook) {}
    public void longCastle(Piece king, Piece rook) {}
}
