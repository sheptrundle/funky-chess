package Game.Pieces;
import Game.ChessBoard;
import Game.Pieces.Features.Color;
import Game.Pieces.Features.PieceType;
import Game.Position;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class King implements Piece {
    Position position;
    ChessBoard board;
    Color color;
    boolean hasMoved;
    private ImageView imageView;

    public King(Position position, ChessBoard board, Color color) {
        this.position = position;
        this.board = board;
        this.color = color;
        hasMoved = false;

        // Set up the image for specific piece
        this.imageView = new ImageView(
                new Image("/images/" + getColorAsString() + "_king.png")
        );
        this.imageView.setFitWidth(80);
        this.imageView.setFitHeight(80);
    }

    // Getters and Setter
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
    public PieceType getType() {return PieceType.KING;}
    public boolean hasMoved() {return hasMoved;}
    public Node getNode() {return imageView;}

    public List<Position> getValidMoves() {
        int[] dx = { 1,  1,  1,  0, 0, -1, -1, -1 };
        int[] dy = { -1,  0, 1, -1, 1, -1,  0,  1 };
        List<Position> validMoves = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            Position newPos = new Position(position.getRow() + dx[i], position.getColumn() + dy[i]);
            if (newPos.isOnBoard()) validMoves.add(newPos);
        }

        return validMoves;
    }
}
