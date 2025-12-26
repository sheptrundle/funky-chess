package Game.Features;

import Game.Pieces.Piece;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageFactory {

    public ImageView getImageView(Piece piece, Gallery gal) {
        String gallery = galleryToString(gal);
        String color = piece.getColorAsString();
        String type = pieceTypeToString(piece.getType());
        return new ImageView(
                new Image("/images/" + gallery + "/" + color + "_" + type + "_" + gallery + ".png")
        );

    }

    public String pieceTypeToString(PieceType pieceType) {
        switch (pieceType) {
            case PieceType.KING:
                return "king";
            case PieceType.QUEEN:
                return "queen";
            case PieceType.ROOK:
                return "rook";
            case PieceType.BISHOP:
                return "bishop";
            case PieceType.KNIGHT:
                return "knight";
            case PieceType.PAWN:
                return "pawn";
            default:
                return "null";

        }
    }

    public String galleryToString(Gallery gallery) {
        if (gallery == Gallery.ORGINAL) {
            return "og";
        } else if (gallery == Gallery.PIXEL) {
            return "pixel";
        } else {
            throw new IllegalArgumentException(gallery + " is not a valid gallery");
        }
    }
}
