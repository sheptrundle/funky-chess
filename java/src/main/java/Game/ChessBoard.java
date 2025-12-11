package Game;
import Game.Pieces.NullPiece;
import Game.Pieces.Piece;

public class ChessBoard {
    public Piece[][] board;

    public ChessBoard() {
        board = new Piece[8][8];
    }

    // Return piece at a position
    public Piece getPieceAt(Position position) {
        return board[position.getRow()][position.getColumn()];
    }

    // Sets a piece at position
    public void setPieceAt(Position position, Piece piece) {
        board[position.getRow()][position.getColumn()] = piece;
    }

    // Set up the board in standard position
    public void initialize() {
        // implement this
    }

    // This will have to get wayyyy more complicated I assume
    public void movePiece(Position from, Position to) {
        Piece movingPiece = getPieceAt(from);
        movingPiece.setPosition(to);
        setPieceAt(to, movingPiece);
        setPieceAt(from, new NullPiece(from));
    }
}
