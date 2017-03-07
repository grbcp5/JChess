package net.ddns.grbcp5.chess.engine.pieces;

import net.ddns.grbcp5.chess.engine.board.Board;
import net.ddns.grbcp5.chess.engine.board.Move;

import java.util.Collection;

/**
 * Created by GrantBroadwater on 3/1/17.
 */
public abstract class Piece
{

    protected final int position;
    protected final boolean isFirstMove;

    protected final Alliance alliance;


    public Piece(final int position, final Alliance alliance)
    {
        this.position = position;
        this.alliance = alliance;
        this.isFirstMove = false;
    }

    protected final boolean isFirstMove()
    {
        return this.isFirstMove;
    }

    public Alliance getAlliance()
    {
        return alliance;
    }
    public int getPosition()
    {
        return position;
    }

    public abstract Collection<Move> getLegalMoves(final Board board);


    public enum PieceType
    {
        PAWN("P"),
        KNIGHT("N"),
        BISHOP("B"),
        ROOK("R"),
        QUEEN("Q"),
        KING("K");


        private String pieceName;

        PieceType(final String pieceName)
        {
            this.pieceName = pieceName;
        }

        @Override
        public String toString()
        {
            return this.pieceName;
        }
    }
}
