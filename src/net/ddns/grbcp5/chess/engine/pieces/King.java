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
public class King extends Piece
{

    private static final int[] CANDIDATE_LEGAL_MOVES = {-9, -8, -7, -1, 1, 7, 8, 9};

    public King(final int position, final Alliance alliance)
    {
        super(position, alliance);
    }

    @Override
    public Collection<Move> getLegalMoves(final Board board)
    {
        final List<Move> legalMoves = new ArrayList<Move>();
        int candidateDestinationCoordinate;

        for(final int candidateOffset : CANDIDATE_LEGAL_MOVES)
        {
            candidateDestinationCoordinate = this.position + candidateOffset;

            if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate))
            {
                if(isColumnExclusion(this.position, candidateOffset))
                {
                    continue;
                }

               /* Get tile object on the board for this coordinate */
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);

                /* If no piece is on the move-to tile then... */
                if (!candidateDestinationTile.isOccupied())
                {
                    /* Add a move to this tile to the list of legal moves */
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
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
                        legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate,
                                pieceAtDestination));
                    }
                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    private static boolean isColumnExclusion(final int currentPosition, final int offset)
    {
        return isFirstColumnExclusion(currentPosition, offset) || isEighthColumnExclusion(currentPosition, offset);
    }

    private static boolean isFirstColumnExclusion(final int curPos, final int offset)
    {
        return BoardUtils.isFirstColumn(curPos) && (offset == -9 || offset == -1 || offset == 7);
    }

    private static boolean isEighthColumnExclusion(final int curPos, final int offset)
    {
        return BoardUtils.isEighthColumn(curPos) && (offset == -7 || offset == 1 || offset == 9);
    }
}
