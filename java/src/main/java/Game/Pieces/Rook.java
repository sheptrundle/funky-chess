package Game.Pieces;

import Game.Features.ChessBoard;
import Game.Features.Color;
import Game.Features.MoveLogic;
import Game.Features.PieceType;
import Game.Features.Position;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Rook implements Piece {
    Position position;
    ChessBoard board;
    Color color;
    private ImageView imageView;

    public Rook(Position position, ChessBoard board, Color color) {
        this.position = position;
        this.board = board;
        this.color = color;

        // Set up the image for specific piece
        this.imageView = new ImageView(
                new Image("/images/" + getColorAsString() + "_rook.png")
        );
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
    public PieceType getType() {return PieceType.ROOK;}
    public Node getNode() {return imageView;}

    public List<Position> getValidMoves() {
       MoveLogic moveLogic = new MoveLogic();
       return moveLogic.rookMoveset(this, position, board);
    }

}
