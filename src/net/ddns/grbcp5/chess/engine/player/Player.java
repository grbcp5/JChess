package net.ddns.grbcp5.chess.engine.player;

import com.google.common.collect.ImmutableList;
import net.ddns.grbcp5.chess.engine.board.Board;
import net.ddns.grbcp5.chess.engine.board.Move;
import net.ddns.grbcp5.chess.engine.board.MoveStatus;
import net.ddns.grbcp5.chess.engine.board.MoveTransition;
import net.ddns.grbcp5.chess.engine.pieces.Alliance;
import net.ddns.grbcp5.chess.engine.pieces.King;
import net.ddns.grbcp5.chess.engine.pieces.Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * Created by GrantBroadwater on 3/6/17.
 */
public abstract class Player
{
    protected final Board board;
    protected final King playersKing;
    protected final Collection<Move> legalMoves;
    protected final Collection<Move> oppMoves;

    public Player(final Board board, final Collection<Move> legalMoves, final Collection<Move> oppLegalMoves)
    {
        this.board = board;
        this.playersKing =  establishKing();
        this.legalMoves = legalMoves;
        this.oppMoves = oppLegalMoves;
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

    public boolean isInCheckMate()
    {
        return isInCheck() && !this.hasEscapeMoves();
    }

    protected boolean hasEscapeMoves()
    {
        for (final Move move : legalMoves)
        {
            final MoveTransition transition = makeMove(move);
            if(transition.getMoveStatus() == MoveStatus.DONE)
            {
                return true;
            }
        }

        return false;
    }

    public boolean isInStaleMate()
    {
        return !this.isInCheckMate() && !this.hasEscapeMoves();
    }

    public boolean isCastled()
    {
        return false;
    }

    public MoveTransition makeMove(final Move move)
    {
        if(!isLegalMove(move))
        {
            return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
        }

        final Board transitionBoard = move.execute();

        final Collection<Move> kingAttacks = Player.calculateAttacksOnTile(
                transitionBoard.getCurrentPlayer().getOpponent().getPlayersKing().getPosition(),
                transitionBoard.getCurrentPlayer().getLegalMoves());

        if(!kingAttacks.isEmpty())
        {
            return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
        }

        return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
    }

    public boolean isInCheck()
    {
        return !Player.calculateAttacksOnTile(this.playersKing.getPosition(), this.oppMoves).isEmpty();
    }

    public static Collection<Move> calculateAttacksOnTile(final int tilePosition, Collection<Move> moveSet)
    {
        final List<Move> attackMoves = new ArrayList<Move>();

        for(final Move move : moveSet)
        {
            if(tilePosition == move.getDestinationCoordinate())
            {
                attackMoves.add(move);
            }
        }

        return ImmutableList.copyOf(attackMoves);
    }

    public Collection<Move> getLegalMoves()
    {
        return legalMoves;
    }

    public King getPlayersKing()
    {
        return playersKing;
    }
}
