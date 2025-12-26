package Game.Pieces;

import Game.Features.*;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Bishop implements Piece {
    Position position;
    ChessBoard board;
    Color color;
    private ImageView imageView;

    public Bishop(Position position, ChessBoard board, Color color) {
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
    public PieceType getType() {return PieceType.BISHOP;}
    public Node getNode() {return imageView;}

    public List<Position> getValidMoves() {
        MoveLogic moveLogic = new MoveLogic();
        return moveLogic.bishopMoveSet(this, position, board);
    }
}

