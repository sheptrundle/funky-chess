package Game;
import Game.Pieces.Features.Color;
import Game.Pieces.NullPiece;
import Game.Pieces.Pawn;
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
        for (int col = 1; col < 8; col++) {
            // Place white pawns
            Position whitePawnLoc = new Position(1, col);
            Piece whitePawn = new Pawn(whitePawnLoc, this, Color.WHITE);
            setPieceAt(whitePawnLoc, whitePawn);
            // Place black pawns
            Position blackPawnLoc = new Position(7, col);
            Piece blackPawn = new Pawn(blackPawnLoc, this, Color.BLACK);
            setPieceAt(blackPawnLoc, blackPawn);
        }
    }

    // This will have to get wayyyy more complicated I assume
    public void movePiece(Position from, Position to) {
        Piece movingPiece = getPieceAt(from);
        movingPiece.setPosition(to);
        setPieceAt(to, movingPiece);
        setPieceAt(from, new NullPiece(from));
    }
}
