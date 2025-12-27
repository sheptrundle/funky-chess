package Game.Features;

import Game.Pieces.Piece;

import java.util.HashSet;

public class Team {
    private HashSet<Piece> pieces;
    public ChessBoard chessBoard;

    public Team() {
        pieces = new HashSet<>();
    }

    public void addPiece(Piece piece) {pieces.add(piece);}
    public void removePiece(Piece piece) {pieces.remove(piece);}

    // public HashSet<Position> getAllTargets() {

    //}
}
