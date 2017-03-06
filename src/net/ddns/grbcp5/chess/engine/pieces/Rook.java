package net.ddns.grbcp5.chess.engine.pieces;

import com.google.common.collect.ImmutableList;
import net.ddns.grbcp5.chess.engine.board.Board;
import net.ddns.grbcp5.chess.engine.board.BoardUtils;
import net.ddns.grbcp5.chess.engine.board.Move;
import net.ddns.grbcp5.chess.engine.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * Created by GrantBroadwater on 3/6/17.
 */
public class Rook extends Piece
{
    private static final int[] CANDIDATE_MOVES_VECTOR_COORDINATES = {-8, -1, 1, 8};


    public Rook(final int position, final Alliance alliance)
    {
        super(position, alliance);
    }

    @Override
    public Collection<Move> getLegalMoves(final Board board)
    {
        int candidateDestinationCoordinate;
        final List<Move> legalMoves = new ArrayList<Move>();

        /* For each direction the bishop can move */
        for(final int candidateCoordinateOffset : CANDIDATE_MOVES_VECTOR_COORDINATES)
        {
            /* If this is an edge case then continue to the next direction */
            if(isColumnExclusion(this.position, candidateCoordinateOffset))
                continue;

            /* "Slide" in that direction */
            candidateDestinationCoordinate = this.position + candidateCoordinateOffset;

            while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate))
            {
                /* Get board tile of the move-to coordinate */
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);

                /* If the tile is not occupied then add a move to the list of legal moves */
                if (!candidateDestinationTile.isOccupied())
                {
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                }
                else /* If there is a piece on the move-to tile */
                {
                    /* Get the piece at the move-to tile */
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();

                    /* If the piece on the move-to tile is not the same color as this piece then... */
                    final Alliance destinationPieceAlliance = pieceAtDestination.getAlliance();
                    if (this.alliance != destinationPieceAlliance)
                    {
                        /* Add an attacking move to the list of legal moves */
                        legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate,
                                pieceAtDestination));
                    }

                    break;
                }

                /* Prep for next move-to piece on our current "sliding"/direction vector */

                /* If that piece is an edge case then continue to the next direction */
                if(isColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset))
                    break;

                /* "Slide to the next coordinate in our current direction vector */
                candidateDestinationCoordinate += candidateCoordinateOffset;
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    private static boolean isColumnExclusion(final int currentPosition, final int offset)
    {
        return isFirstColumnExclusion(currentPosition, offset) ||
                isEighthColumnExclusion(currentPosition, offset);
    }

    private static boolean isFirstColumnExclusion(final int currentPosition, final int offset)
    {
        return BoardUtils.isFirstColumn(currentPosition) && (offset == -1);
    }

    private static boolean isEighthColumnExclusion(final int currentPosition, final int offset)
    {
        return BoardUtils.isEighthColumn(currentPosition) && (offset == 1 );
    }
}
