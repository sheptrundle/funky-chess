package Game.Pieces;
import Game.ChessBoard;
import Game.Pieces.Features.Color;
import Game.Pieces.Features.MoveLogic;
import Game.Pieces.Features.PieceType;
import Game.Position;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Pawn implements Piece {
    Position position;
    ChessBoard board;
    Color color;
    boolean hasMoved;
    private ImageView imageView;

    public Pawn(Position position, ChessBoard board, Color color) {
        this.position = position;
        this.board = board;
        this.color = color;
        hasMoved = false;

        // Set up the image for specific piece
        this.imageView = new ImageView(
                new Image("/images/" + getColorAsString() + "_pawn.png")
        );
        this.imageView.setFitWidth(80);
        this.imageView.setFitHeight(80);
    }

    // Getters and Setters
    public Position getPosition() {return position;}
    public void setPosition(Position position) {
        this.position = position;
        hasMoved = false;
    }
    public Color getColor() {return color;}
    public String getColorAsString() {
        return (color == Color.WHITE) ? "white" : "black";
    }
    public boolean exists() {return true;}
    public PieceType getType() {return PieceType.PAWN;}
    public boolean hasMoved() {return hasMoved;}
    public Node getNode() {return imageView;}

    public List<Position> diagonalAttacks() {
        List<Position> diagonalAttacks = new ArrayList<>();
        int currRow = position.getRow();
        int currCol = position.getColumn();
        Position leftDiag = new Position(currRow - 1, currCol + 1);
        Position rightDiag = new Position(currRow + 1, currCol + 1);

        // Check left diagonal
        if (board.getPieceAt(leftDiag).exists() && board.getPieceAt(leftDiag).getColor() != color) {
            diagonalAttacks.add(leftDiag);
        }
        // Check right diagonal
        if (board.getPieceAt(rightDiag).exists() && board.getPieceAt(rightDiag).getColor() != color) {
            diagonalAttacks.add(rightDiag);
        }
        return diagonalAttacks;
    }

    public List<Position> getValidMoves() {
        List<Position> validMoves = new ArrayList<>();
        MoveLogic moveLogic = new MoveLogic();

        // On first row, can go up 1 or 2 spaces
        for (int i = 1; i <= 2; i++) {
            Position to = new Position(position.getRow(), position.getColumn() + i);
            if (moveLogic.isValidMove(this, board, to)) {
                validMoves.add(to);
            }
            // Only allow 2 space move if Pawn hasn't moved yet
            if (hasMoved) {break;}
        }
        // Add diagonal attacks
        validMoves.addAll(diagonalAttacks());

        return validMoves;
    }
}