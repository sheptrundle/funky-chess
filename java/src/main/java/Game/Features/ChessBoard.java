package Game.Features;
import Game.Pieces.*;

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

    public ChessBoard flippedCopy() {
        ChessBoard flipped = new ChessBoard();
        int flippedRow = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                flipped.board[flippedRow][col] = board[7 - row][col];
            }
        }
        return flipped;
    }

    // Initialize a new piece and place it in a position
    public void createAndPlace(Color color, String pieceName, int row, int col) throws IllegalArgumentException{
        Piece piece;
        Position pos = new Position(row,col);
        if (pieceName.equals("pawn")) {
            piece = new Pawn(pos, this, color);
        }
        else if (pieceName.equals("rook")) {
            piece = new Rook(pos, this, color);
        }
        else if (pieceName.equals("knight")) {
            piece = new Knight(pos, this, color);
        }
        else if (pieceName.equals("king")) {
            piece = new King(pos, this, color);
        }
        else if (pieceName.equals("null")) {
            piece = new NullPiece(pos);
        }
        else {
            throw new IllegalArgumentException(pieceName + " is not a valid piece name");
        }
        setPieceAt(pos, piece);
    }

    // Set up the board in standard position
    public void initialize() {
        // Pawns
        for (int col = 0; col < 8; col++) {
            createAndPlace(Color.WHITE, "pawn", 1, col);
            createAndPlace(Color.BLACK, "pawn", 6, col);
        }

        // Kings
        createAndPlace(Color.WHITE, "king", 0, 4);
        createAndPlace(Color.BLACK, "king", 7, 4);

        // Queens
        createAndPlace(Color.BLACK, "queen", 0, 3);
        createAndPlace(Color.BLACK, "queen", 7, 3);

        // Knights
        createAndPlace(Color.WHITE, "knight", 0, 1);
        createAndPlace(Color.WHITE, "knight", 0, 6);
        createAndPlace(Color.BLACK, "knight", 7, 1);
        createAndPlace(Color.BLACK, "knight", 7, 6);

        // Rooks
        createAndPlace(Color.WHITE, "rook", 0, 0);
        createAndPlace(Color.WHITE, "rook", 0, 7);
        createAndPlace(Color.BLACK, "rook", 7, 0);
        createAndPlace(Color.BLACK, "rook", 7, 7);

        // Bishops
        createAndPlace(Color.WHITE, "bishop", 0, 2);
        createAndPlace(Color.BLACK, "bishop", 0, 5);
        createAndPlace(Color.WHITE, "bishop", 7, 2);
        createAndPlace(Color.BLACK, "bishop", 7, 5);

        // Null Pieces in Middle
        for (int row = 2; row < 6; row++) {
            for (int col = 0; col < 8; col++) {
                createAndPlace(Color.WHITE,"null", row, col);
            }
        }
    }

    // This will have to get wayyyy more complicated I assume?
    public void movePiece(Position from, Position to) {
        Piece movingPiece = getPieceAt(from);
        if (!movingPiece.exists()) {
            throw new IllegalArgumentException("Cannot move NullPiece from " + from + " to " + to);
        }
        movingPiece.setPosition(to);
        setPieceAt(to, movingPiece);
        setPieceAt(from, new NullPiece(from));
    }
}
