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
    public PieceType getType() {return PieceType.BISHOP;}
    public Node getNode() {return imageView;}

    public List<Position> getValidMoves() {
        int[] dy = {1, -1};
        int[] dx = {1, -1};
        List<Position> validMoves = new ArrayList<>();
        MoveLogic moveLogic = new MoveLogic();

        // Expand diagonals
        for (int y : dy) {
            for (int x : dx) {
                int row = position.getRow() + y;
                int col = position.getColumn() + x;

                while (moveLogic.isValidMove(this, board, new Position(row, col))) {
                    validMoves.add(new Position(row, col));
                    row += y;
                    col += x;
                }
            }
        }
        return validMoves;
    }
}

