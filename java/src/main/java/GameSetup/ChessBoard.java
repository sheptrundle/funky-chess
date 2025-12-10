package GameSetup;
import GameSetup.Pieces.NullPiece;

import java.util.ArrayList;

public class ChessBoard {
    public Piece[][] board;

    public ChessBoard() {
        board = new Piece[8][8];
    }

    public Piece getPieceAt(Position position) {
        return board[position.getRowAsIndex()][position.getColumnAsIndex()];
    }
    public void setPieceAt(Position position, Piece piece) {
        board[position.getRowAsIndex()][position.getColumnAsIndex()] = piece;
    }

    public void movePiece(Position from, Position to) {
        Piece movingPiece = getPieceAt(from);
        setPieceAt(from, new NullPiece(from));
        setPieceAt(to, movingPiece);
    }

}
