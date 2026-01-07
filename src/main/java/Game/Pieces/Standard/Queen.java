package Game.Pieces.Standard;

import Game.Features.*;
import Game.Logic.MoveLogic;
import Game.Logic.PieceLogic;
import Game.Logic.TargetLogic;
import Game.Pieces.Assets.Color;
import Game.Pieces.Assets.Piece;
import Game.Pieces.Assets.PieceType;
import UI.Images.ImageFactory;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.List;

public class Queen implements Piece {
    Position position;
    ChessBoard board;
    Color color;
    boolean hasMoved;
    private ImageView imageView;

    public Queen(Position position, ChessBoard board, Color color) {
        this.position = position;
        this.board = board;
        this.color = color;
        hasMoved = false;

        // Set up the image for specific piece
        ImageFactory imageFactory = new ImageFactory();
        this.imageView = imageFactory.getImageView(this, board.getGallery());
        this.imageView.setFitWidth(80);
        this.imageView.setFitHeight(80);
    }

    // Getters and Setters
    public Position getPosition() {return position;}
    public void setPosition(Position position) {this.position = position;}
    public ChessBoard getBoard() {return board;}
    public Color getColor() {return color;}
    public String getColorAsString() {
        return (color == Color.WHITE) ? "white" : "black";
    }
    public Color getOppositeColor() {
        PieceLogic pieceLogic = new PieceLogic();
        return pieceLogic.getOppositeColor(color);
    }
    public boolean exists() {return true;}
    public PieceType getType() {return PieceType.QUEEN;}
    public Node getNode() {return imageView;}
    public boolean hasMoved() {return hasMoved;}
    public void setHasMoved(boolean hasMoved) {this.hasMoved = hasMoved;}
    public String toString() {return PieceLogic.colorToString(color) + " " + getType() + " @ " + position;}

    public List<Position> getValidMoves(boolean strictlyLegal) {
        MoveLogic moveLogic = new MoveLogic();
        return moveLogic.queenMoveset(this, strictlyLegal);
    }

    public boolean targets(Position position) {
        return TargetLogic.getTargetsForPiece(this).contains(position);
    }
}
