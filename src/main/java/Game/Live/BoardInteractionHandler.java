package Game.Live;

import Game.Features.ChessBoard;
import Game.Features.Position;
import Game.Logic.Castle;
import Game.Logic.SpecialMovesLogic;
import Game.Pieces.Assets.Piece;
import Game.Pieces.Assets.NullPiece;
import UI.Model.BoardRenderer;
import UI.Model.GameCoordinator;
import UI.Model.LiveUIBinder;
import UI.Model.TwoWayChessBoard;

import java.util.HashSet;

public class BoardInteractionHandler {

    private final ChessBoard chessBoard;
    private final TwoWayChessBoard twoWayBoard;
    private final LiveGame liveGame;
    private final BoardRenderer renderer;
    private final GameCoordinator gameCoordinator;

    private final HashSet<Position> validMoves = new HashSet<>();
    private Piece movingPiece = new NullPiece(new Position(-1, -1));

    public BoardInteractionHandler(ChessBoard chessBoard,
                                   TwoWayChessBoard twoWayBoard,
                                   LiveGame liveGame,
                                   BoardRenderer renderer,
                                   GameCoordinator gameCoordinator) {
        this.chessBoard = chessBoard;
        this.twoWayBoard = twoWayBoard;
        this.liveGame = liveGame;
        this.renderer = renderer;
        this.gameCoordinator = gameCoordinator;
    }

    public void handleClick(int row, int col) {
        Position clicked = twoWayBoard.uiToBoard(new Position(row, col));

        // First IF is a SUCCESSFUL TURN
        if (validMoves.contains(clicked)) {
            renderer.unhighlightPiece(movingPiece);

            // Check if this move is a castle
            Castle castleType = SpecialMovesLogic.isCastle(movingPiece, clicked);
            if (castleType != Castle.NULL) {
                SpecialMovesLogic.makeCastle(chessBoard, movingPiece, castleType);
            }

            // Normal move
            else {
                chessBoard.movePiece(movingPiece, clicked);
            }

            renderer.clearHighlights(validMoves);

            // Update moving piece to no currently selected piece
            movingPiece = new NullPiece(new Position(-1, -1));

            liveGame.switchTurn();
            // Flip board and update current turn
            twoWayBoard.setPOV(liveGame.getCurrentTurn());
            gameCoordinator.updateLabels();

            // See if checkmate after a successful move
            liveGame.checkCheckmates();
        }

        // Not a successful move, instead update highlights/UI accordingly
        else {
            Piece piece = chessBoard.getPieceAt(clicked);

            // Cannot move opponent's pieces or empty spaces
            // Grey out the second boolean in below statement for easier testing
            if (!piece.exists() || piece.getColor() != liveGame.getCurrentTurn()) {return;}

            // If clicked on current moving piece, remove highlights/reset moving piece and move on
            if (clicked.equals(movingPiece.getPosition())) {
                renderer.clearHighlights(validMoves);
                renderer.unhighlightPiece(movingPiece);
                movingPiece = new NullPiece(new Position(-1, -1));
            }
            // Clicked on a valid piece, but not in previous moveset
            else {
                if (movingPiece.getPosition().isOnBoard()) {
                    renderer.unhighlightPiece(movingPiece);
                }
                renderer.highlightMoves(validMoves, piece);
                movingPiece = piece;
                renderer.highlightPiece(movingPiece, "limegreen");
            }
        }

        // Update UI at the end of every click
        renderer.updateUI();
    }
}
