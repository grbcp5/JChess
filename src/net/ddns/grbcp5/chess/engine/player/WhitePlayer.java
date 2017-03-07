package net.ddns.grbcp5.chess.engine.player;

import net.ddns.grbcp5.chess.engine.board.Board;
import net.ddns.grbcp5.chess.engine.board.Move;
import net.ddns.grbcp5.chess.engine.pieces.Piece;

import java.util.Collection;

/**
 *
 * Created by GrantBroadwater on 3/6/17.
 */
public class WhitePlayer extends Player
{
    public WhitePlayer(Board board, Collection<Move> legalMoves, Collection<Move> oppLegalMoves)
    {
        super(board, legalMoves, oppLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces()
    {
        return this.board.getWhitePieces();
    }
}
