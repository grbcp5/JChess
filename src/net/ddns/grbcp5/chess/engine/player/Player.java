package net.ddns.grbcp5.chess.engine.player;

import net.ddns.grbcp5.chess.engine.board.Board;
import net.ddns.grbcp5.chess.engine.board.Move;
import net.ddns.grbcp5.chess.engine.board.MoveTransition;
import net.ddns.grbcp5.chess.engine.pieces.Alliance;
import net.ddns.grbcp5.chess.engine.pieces.King;
import net.ddns.grbcp5.chess.engine.pieces.Piece;

import java.util.Collection;

/**
 *
 * Created by GrantBroadwater on 3/6/17.
 */
public abstract class Player
{
    protected final Board board;
    protected final King playersKing;
    protected final Collection<Move> legalMoves;

    public Player(final Board board, final Collection<Move> legalMoves, final Collection<Move> oppLegalMoves)
    {
        this.board = board;
        this.playersKing =  establishKing();
        this.legalMoves = legalMoves;
    }

    private King establishKing()
    {
        for(final Piece piece : this.getActivePieces())
        {
            if (piece.toString().toLowerCase().equals(Piece.PieceType.KING.toString().toLowerCase()))
            {
                return (King) piece;
            }
        }

        throw new RuntimeException("No King found");
    }

    public abstract Collection<Piece> getActivePieces();

    public abstract Alliance getAlliance();

    public abstract Player getOpponent();

    public boolean isLegalMove(final Move move)
    {
        return this.legalMoves.contains(move);
    }

    public boolean isInCheck()
    {
        return false;
    }

    public boolean isInCheckMate()
    {
        return false;
    }

    public boolean isInStaleMate()
    {
        return false;
    }

    public boolean isCastled()
    {
        return false;
    }

    public MoveTransition Move(final Move move)
    {
        return null;
    }

}
