package Game.Pieces.Assets;

import Game.Features.ChessBoard;
import Game.Features.Position;
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
    public Color getOppositeColor() {throw new NullPointerException("Cannot get opposite color of null piece at " + position);}
    public Position getPosition() {return position;}
    public void setPosition(Position position) {this.position = position;}
    public ChessBoard getBoard() {throw new NullPointerException("Cannot get board of null piece at " + position);}
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
    public boolean hasMoved() {throw new IllegalArgumentException("Cannot use hasMoved on piece: " + getType());}
    public void setHasMoved(boolean hasMoved) {throw new IllegalArgumentException("Cannot use setHasMoved on piece: " + getType());}
    public boolean targets(Position position) {throw new NullPointerException("Cannot get targets of null piece at " + position);}
    public String toString() {return "NullPiece @ " + position;}

}

