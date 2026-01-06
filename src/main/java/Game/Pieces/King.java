package Game.Pieces;
import Game.Features.*;
import Game.Live.Player;
import Game.Logic.MoveLogic;
import Game.Logic.PieceLogic;
import Game.Logic.TargetLogic;
import Game.Pieces.Assets.Color;
import Game.Pieces.Assets.Piece;
import Game.Pieces.Assets.PieceType;
import UI.ImageFactory;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.HashSet;
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
        ImageFactory imageFactory = new ImageFactory();
        this.imageView = imageFactory.getImageView(this, board.getGallery());
        this.imageView.setFitWidth(80);
        this.imageView.setFitHeight(80);
    }

    // Getters and Setter
    public Position getPosition() {return position;}
    public void setPosition(Position position) {
        this.position = position;
        hasMoved = true;
    }
    public ChessBoard getBoard() {return board;}
    public Color getColor() {return color;}
    public String getColorAsString() {
        return (color == Color.WHITE) ? "white" : "black";
    }
    public Color getOppositeColor() {
        return PieceLogic.getOppositeColor(color);
    }
    public boolean exists() {return true;}
    public PieceType getType() {return PieceType.KING;}
    public Node getNode() {return imageView;}
    public boolean hasMoved() {return hasMoved;}
    public void setHasMoved(boolean hasMoved) {this.hasMoved = hasMoved;}
    public String toString() {return PieceLogic.colorToString(color) + " " + getType() + " @ " + position;}

    public List<Position> getValidMoves() {
       MoveLogic moveLogic = new MoveLogic();
       return moveLogic.kingMoveset(this);
    }

    public boolean targets(Position position) {
        return TargetLogic.getTargetsForPiece(this).contains(position);
    }

    // Return the opposing player on current game
    public Player getOppositePlayer() {
        return switch (getColor()) {
            case WHITE -> board.getPlayer(Color.BLACK);
            case BLACK -> board.getPlayer(Color.WHITE);
        };
    }

    // Return true if king is currently in check
    public boolean isInCheck() {
        return TargetLogic.isTargeted(board, position, color);
    }
}
