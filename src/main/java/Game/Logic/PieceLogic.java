package Game.Logic;

import Game.Pieces.Assets.Color;

public class PieceLogic {

    public static Color getOppositeColor(Color color) {
        return switch (color) {
            case WHITE -> Color.BLACK;
            case BLACK -> Color.WHITE;
        };
    }

    public static String colorToString(Color color) {
        return switch (color) {
            case WHITE -> "White";
            case BLACK -> "Black";
        };
    }
}
