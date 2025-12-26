package Game.Pieces;
import Game.Features.*;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Knight implements Piece {
    Position position;
    ChessBoard board;
    Color color;
    private ImageView imageView;

    public Knight(Position position, ChessBoard board, Color color) {
        this.position = position;
        this.board = board;
        this.color = color;

        // Set up the image for specific piece
        ImageFactory imageFactory = new ImageFactory();
        this.imageView = imageFactory.getImageView(this, board.getGallery());
        this.imageView.setFitWidth(80);
        this.imageView.setFitHeight(80);
    }

    // Getters and Setters
    public Position getPosition() {return position;}
    public void setPosition(Position position) {this.position = position;}
    public Color getColor() {return color;}
    public String getColorAsString() {
        return (color == Color.WHITE) ? "white" : "black";
    }
    public boolean exists() {return true;}
    public PieceType getType() {return PieceType.KNIGHT;}
    public Node getNode() {return imageView;}

    // Return a list of all the valid moves from knight at its current position
    public List<Position> getValidMoves() {
        MoveLogic moveLogic = new MoveLogic();

        int[] dx = { 1,  2,  2,  1, -1, -2, -2, -1 };
        int[] dy = { 2,  1, -1, -2, -2, -1,  1,  2 };
        List<Position> validMoves = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            Position newPos = new Position(position.getRow() + dx[i], position.getColumn() + dy[i]);
            if (moveLogic.isValidMove(this, board, newPos)) {
                validMoves.add(newPos);
            }
        }

        return validMoves;
    }
}