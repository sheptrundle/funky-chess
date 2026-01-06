package Game.Features;
import Game.Live.Player;
import Game.Logic.MoveLogic;
import Game.Pieces.Assets.Color;
import Game.Pieces.Assets.NullPiece;
import Game.Pieces.Assets.Piece;
import Game.Pieces.Standard.*;
import UI.Images.Gallery;
import javafx.util.Duration;


public class ChessBoard {
    public Piece[][] board;
    Gallery gallery;
    Duration time;
    Player whitePlayer;
    Player blackPlayer;

    public ChessBoard(Gallery gallery, Duration time) {
        board = new Piece[8][8];
        setLiveFields(gallery, time);
    }

    // Returns the gallery of the board
    public Gallery getGallery() {return gallery;}

    // Sets the gallery of the board
    public void setLiveFields(Gallery gallery, Duration time) {
        this.gallery = gallery;
        this.time = time;
        whitePlayer = new Player(Color.WHITE, time);
        blackPlayer = new Player(Color.BLACK, time);
    }

    // Return piece at a position
    public Piece getPieceAt(Position position) {
        return board[position.getRow()][position.getColumn()];
    }

    // Sets a piece at position
    public void setPieceAt(Position position, Piece piece) {
        board[position.getRow()][position.getColumn()] = piece;
    }

    // Return a specific player (either white or black)
    public Player getPlayer(Color color) {
        return switch (color) {
            case WHITE -> whitePlayer;
            case BLACK -> blackPlayer;
        };
    }

    // Returns a flipped copy of the current board
    public ChessBoard flipped() {
        ChessBoard flipped = new ChessBoard(gallery, time);
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                flipped.board[row][col] = board[7 - row][col];
            }
        }
        return flipped;
    }

    // Find and return the King for a given color
    public King getKing(Color color) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = getPieceAt(new Position(r, c));
                if (p instanceof King && p.getColor() == color) {
                    return (King) p;
                }
            }
        }
        throw new IllegalStateException("King not found for " + color);
    }

    // Initialize a new piece and place it in a position
    public void createAndPlace(Color color, String pieceName, int row, int col) throws IllegalArgumentException{
        Piece piece;
        Position pos = new Position(row,col);
        piece = switch (pieceName) {
            case "pawn" -> new Pawn(pos, this, color);
            case "rook" -> new Rook(pos, this, color);
            case "bishop" -> new Bishop(pos, this, color);
            case "knight" -> new Knight(pos, this, color);
            case "queen" -> new Queen(pos, this, color);
            case "king" -> new King(pos, this, color);
            case "null" -> new NullPiece(pos);
            default -> throw new IllegalArgumentException(pieceName + " is not a valid piece name");
        };
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
        createAndPlace(Color.WHITE, "queen", 0, 3);
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
        createAndPlace(Color.WHITE, "bishop", 0, 5);
        createAndPlace(Color.BLACK, "bishop", 7, 2);
        createAndPlace(Color.BLACK, "bishop", 7, 5);

        // Null Pieces in Middle
        for (int row = 2; row < 6; row++) {
            for (int col = 0; col < 8; col++) {
                createAndPlace(Color.WHITE,"null", row, col);
            }
        }

        setStartingPlayers();
    }

    // Move a piece from one position to another
    public void movePiece(Piece piece, Position to) {
        Position from = piece.getPosition();
        MoveLogic moveLogic = new MoveLogic();
        if (!piece.exists()) {
            throw new IllegalArgumentException("Cannot move NullPiece from " + from + " to " + to);
        }

        // Update player's teams if the move is a capture
        if (moveLogic.isCapture(piece, to, this)) {
            Piece capturedPiece = getPieceAt(to);
            switch (capturedPiece.getColor()) {
                case WHITE:
                    whitePlayer.removePiece(capturedPiece);
                    break;
                case BLACK:
                    blackPlayer.removePiece(capturedPiece);
                    break;
            }
        }

        // Logic for if the move is a pawn promotion (auto-Queen)
        if (moveLogic.pawnPromotion(piece, to)) {
            Queen newQueen = new Queen(to, this, piece.getColor());
            setPieceAt(to, newQueen);
            setPieceAt(from, new NullPiece(from));
            Player promotingPlayer = getPlayer(piece.getColor());

            // Remove pawn, add a new queen
            promotingPlayer.removePiece(piece);
            promotingPlayer.addPiece(newQueen);
        }

        // Regular move
        else {
            piece.setPosition(to);
            setPieceAt(to, piece);
            setPieceAt(from, new NullPiece(from));
        }
    }

    public void setStartingPlayers() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Position pos = new Position(row, col);
                Piece piece = getPieceAt(pos);

                // Skip null pieces
                if (!piece.exists()) {continue;}

                // Add white piece
                else if (piece.getColor() == Color.WHITE) {
                    whitePlayer.addPiece(piece);
                }
                // Add black piece
                else if (piece.getColor() == Color.BLACK) {
                    blackPlayer.addPiece(piece);
                }
                else {throw new  IllegalArgumentException("Failed getting color from piece " + piece);}
            }
        }
    }
}
