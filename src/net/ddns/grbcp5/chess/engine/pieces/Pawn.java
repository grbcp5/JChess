package net.ddns.grbcp5.chess.engine.pieces;

import com.google.common.collect.ImmutableList;
import net.ddns.grbcp5.chess.engine.board.Board;
import net.ddns.grbcp5.chess.engine.board.BoardUtils;
import net.ddns.grbcp5.chess.engine.board.Move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * Created by GrantBroadwater on 3/6/17.
 */
public class Pawn extends Piece
{
    private final static int[] CANDIDATE_LEGAL_MOVES = {7, 8, 9, 16};


    public Pawn(final int position, final Alliance alliance)
    {
        super(position, alliance);
    }

    @Override
    public Collection<Move> getLegalMoves(final Board board)
    {
        int candidateDestinationCoordinate;
        final List<Move> legalMoves = new ArrayList<Move>();

        for(final int candidateOffset: CANDIDATE_LEGAL_MOVES)
        {
            candidateDestinationCoordinate = this.position + candidateOffset * this.alliance.getDirection();

            if(!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate))
            {
                continue;
            }

            if(candidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isOccupied())
            {
                legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
            }
            else if(candidateOffset == 16 && this.isFirstMove() &&
                    this.isEleigibleToJump(candidateDestinationCoordinate, board))
            {
                legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
            }
            else if((candidateOffset == 7 || candidateOffset == 9) && !isAttackColumnException(candidateOffset))
            {
                final Piece attackedPiece = board.getTile(candidateDestinationCoordinate).getPiece();
                legalMoves.add(new Move.AttackMove(board,this,candidateDestinationCoordinate, attackedPiece));
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    private boolean isEleigibleToJump(final int candidatePosition, final Board board)
    {
        if (!((BoardUtils.isSecondRow(this.position) && this.getAlliance().isBlack()) ||
                (BoardUtils.isSeventhRow(this.position) && this.getAlliance().isWhite())))
        {
            return false;
        }

        final int behindJumpCoordinate = this.position + this.getAlliance().getDirection() * 8;

        if(board.getTile(behindJumpCoordinate).isOccupied() || board.getTile(candidatePosition).isOccupied())
        {
            return false;
        }

        return true;
    }

    private boolean isAttackColumnException(final int candidateOffset)
    {
        switch (this.getAlliance())
        {
            case BLACK:
                return (candidateOffset == 7 && BoardUtils.isFirstColumn(this.position)) ||
                        (candidateOffset == 9 && BoardUtils.isEighthColumn(this.position));
            case WHITE:
                return (candidateOffset == 9 && BoardUtils.isFirstColumn(this.position)) ||
                        (candidateOffset == 7 && BoardUtils.isEighthColumn(this.position));
            default:
                throw new RuntimeException("How the heck did you get here homie");
        }
    }

    @Override
    public String toString()
    {
        return PieceType.PAWN.toString();
    }
}
