package net.ddns.grbcp5.chess.engine.pieces;

import net.ddns.grbcp5.chess.engine.Alliance;
import net.ddns.grbcp5.chess.engine.board.Board;
import net.ddns.grbcp5.chess.engine.board.BoardUtils;
import net.ddns.grbcp5.chess.engine.board.Move;
import net.ddns.grbcp5.chess.engine.board.Move.MajorMove;
import net.ddns.grbcp5.chess.engine.board.Move.AttackMove;
import net.ddns.grbcp5.chess.engine.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Created by GrantBroadwater on 3/1/17.
 */
public class Knight extends Piece
{

    private final static int[] CANDIDATE_LEGAL_MOVES = {-17, -15, -10, -6, 6, 10, 15, 17};

    public Knight(final int position, final Alliance alliance)
    {
        super(position, alliance);
    }

    @Override
    public Collection<Move> getLegalMoves(final Board board)
    {
        int candidateDestinationCoordinate;
        final List<Move> legalMoves = new ArrayList<Move>(8);

        for (int currentCandidateOffset : CANDIDATE_LEGAL_MOVES)
        {
            candidateDestinationCoordinate = this.position + currentCandidateOffset;

            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate))
            {
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);

                if (isColumnExclusion(this.position, currentCandidateOffset))
                {
                    continue;
                }

                if (!candidateDestinationTile.isOccupied())
                {
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                }
                else
                {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance destinationPieceAlliance = pieceAtDestination.getAlliance();

                    if (this.alliance != destinationPieceAlliance)
                    {
                        legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate,
                                pieceAtDestination));
                    }
                }
            }
        }

        return legalMoves;
    }

    private static boolean isColumnExclusion(final int currentPosition, final int candidateOffset)
    {
        return isFirstColumnExclusion(currentPosition, candidateOffset) ||
                isSecondColumnExclusion(currentPosition, candidateOffset) ||
                isSeventhColumnExclusion(currentPosition, candidateOffset) ||
                isEighthColumnExclusion(currentPosition, candidateOffset);
    }

    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset)
    {
        return BoardUtils.isFirstColumn(currentPosition) && ((candidateOffset == -17) || (candidateOffset == -10)
                || (candidateOffset == 6) || (candidateOffset == 15));
    }

    private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset)
    {
        return BoardUtils.isSecondColumn(currentPosition) && ((candidateOffset == -10) || candidateOffset == 6);
    }

    private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset)
    {
        return BoardUtils.isSecondColumn(currentPosition) && ((candidateOffset == -6) || (candidateOffset == 10));
    }

    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset)
    {
        return BoardUtils.isEigthColumn(currentPosition) && ((candidateOffset == -15) || (candidateOffset == -6) ||
                (candidateOffset == 10) || (candidateOffset == 17));
    }
}
