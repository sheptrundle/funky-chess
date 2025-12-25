package Game.Pieces;

import Game.Pieces.Features.Color;
import Game.Pieces.Features.PieceType;
import Game.Position;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.List;

public class NullPiece implements Piece {
    Position position;

    public NullPiece(Position position) {
        this.position = position;
    }

    public Color getColor() {throw new NullPointerException("Cannot get color of null piece at " + position);}
    public String getColorAsString() {throw new NullPointerException("Cannot get color (as string) of null piece at " + position);}
    public Position getPosition() {return position;}
    public void setPosition(Position position) {this.position = position;}
    public List<Position> getValidMoves() {throw new NullPointerException("Cannot get valid moves of null piece at " + position);}
    public boolean exists() {return false;}
    public PieceType getType() {return PieceType.NULLPIECE;}
    public Node getNode() {
        ImageView invisible = new ImageView();
        invisible.setFitWidth(80);
        invisible.setFitHeight(80);
        invisible.setOpacity(0); // completely invisible
        return invisible;
    }

}

