package net.ddns.grbcp5.chess.engine.pieces;

import com.google.common.collect.ImmutableList;
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
 *
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

        /* For each of the possible leagal Moves */
        for (int currentCandidateOffset : CANDIDATE_LEGAL_MOVES)
        {
            /* Calculate where you would move to */
            candidateDestinationCoordinate = this.position + currentCandidateOffset;

            /* If the move-to location is on the board then... */
            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate))
            {
                /* If this is an edge case then try the next move */
                if (isColumnExclusion(this.position, currentCandidateOffset))
                {
                    continue;
                }

                /* Get tile object on the board for this coordinate */
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);

                /* If no piece is on the move-to tile then... */
                if (!candidateDestinationTile.isOccupied())
                {
                    /* Add a move to this tile to the list of legal moves */
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                }
                else /* If a piece is on the move-to tile */
                {
                    /* Get the piece at the move-to tile */
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();

                    /* If that piece is not the same color as this piece then...*/
                    final Alliance destinationPieceAlliance = pieceAtDestination.getAlliance();
                    if (this.alliance != destinationPieceAlliance)
                    {
                        /* Add an attacking move to the move-to tile to the list of legal moves */
                        legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate,
                                pieceAtDestination));
                    }
                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
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
        return BoardUtils.isSeventhColumn(currentPosition) && ((candidateOffset == -6) || (candidateOffset == 10));
    }

    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset)
    {
        return BoardUtils.isEighthColumn(currentPosition) && ((candidateOffset == -15) || (candidateOffset == -6) ||
                (candidateOffset == 10) || (candidateOffset == 17));
    }
}
