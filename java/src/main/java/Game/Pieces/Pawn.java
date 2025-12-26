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
        hasMoved = true;
    }
    public Color getColor() {return color;}
    public String getColorAsString() {
        return (color == Color.WHITE) ? "white" : "black";
    }
    public boolean exists() {return true;}
    public PieceType getType() {return PieceType.PAWN;}
    public boolean hasMoved() {return hasMoved;}
    public Node getNode() {return imageView;}

    public List<Position> getValidMoves() {
        MoveLogic moveLogic = new MoveLogic();
        return moveLogic.pawnMoveSet(this, position, board);
    }
}