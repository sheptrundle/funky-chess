package UI.Helpers;

import Game.Pieces.Assets.Color;
import javafx.scene.layout.StackPane;

public class SquareSetter {

    public static void applyBaseColor(StackPane square, int row, int col, Color pov) {
        boolean isLight = (row + col) % 2 == 0;

        square.getStyleClass().removeAll("square-light", "square-dark");

        if (pov == Color.WHITE) {
            square.getStyleClass().add(isLight ? "square-light" : "square-dark");
        } else {
            square.getStyleClass().add(isLight ? "square-dark" : "square-light");
        }
    }

    public static void applyState(StackPane square, SquareState state) {
        square.getStyleClass().removeAll(
                "square-selected",
                "square-check"
        );

        switch (state) {
            case SELECTED -> square.getStyleClass().add("square-selected");
            case CHECK    -> square.getStyleClass().add("square-check");
            case NORMAL   -> { /* nothing */ }
        }
    }

    public void setAllSquares(StackPane[][] squares, Color pov) {
        for (int r = 0; r < squares.length; r++) {
            for (int c = 0; c < squares[r].length; c++) {
                SquareSetter.applyBaseColor(squares[r][c], r, c, pov);
            }
        }
    }
}

