package UI;

import Game.Features.ChessBoard;
import Game.Features.Position;
import Game.Logic.PieceLogic;
import Game.Pieces.Assets.Color;
import Game.Pieces.Assets.Piece;

/**
 * A wrapper around a single ChessBoard that allows
 * viewing and interacting with the board from either
 * White or Black POV without mutating the board.
 */
public class TwoWayChessBoard {

    private final ChessBoard board;
    private Color currentPOV;

    public TwoWayChessBoard(ChessBoard board, Color startingPOV) {
        this.board = board;
        this.currentPOV = startingPOV;
    }

    // Control POV
    public void setPOV(Color pov) {this.currentPOV = pov;}
    public Color getPOV() {return currentPOV;}

    public ChessBoard getBoard() {return board;}


    // Coordinate Mapping

    // Convert a UI position to a model (board) position
    public Position uiToBoard(Position uiPos) {
        if (currentPOV == Color.WHITE) {
            return uiPos;
        }
        return new Position(
                7 - uiPos.getRow(),
                7 - uiPos.getColumn()
        );
    }

    // Convert a board position to a UI position
    public Position boardToUI(Position boardPos) {
        if (currentPOV == Color.WHITE) {
            return boardPos;
        }
        return new Position(
                7 - boardPos.getRow(),
                7 - boardPos.getColumn()
        );
    }

    // Board access
    public Piece getPieceAtUI(Position uiPos) {
        Position boardPos = uiToBoard(uiPos);
        return board.getPieceAt(boardPos);
    }


    public void movePieceFromUI(Position uiFrom, Position uiTo) {
        Position boardFrom = uiToBoard(uiFrom);
        Position boardTo = uiToBoard(uiTo);

        Piece piece = board.getPieceAt(boardFrom);
        board.movePiece(piece, boardTo);
    }

    // Switching POV helper
    public void switchPOV() {
        currentPOV = PieceLogic.getOppositeColor(currentPOV);
    }
}

