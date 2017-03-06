package net.ddns.grbcp5.chess.engine.pieces;

import net.ddns.grbcp5.chess.engine.Alliance;
import net.ddns.grbcp5.chess.engine.board.Board;
import net.ddns.grbcp5.chess.engine.board.Move;

import java.util.Collection;

/**
 * Created by GrantBroadwater on 3/1/17.
 */
public abstract class Piece
{

    protected final int position;



    protected final Alliance alliance;


    public Piece(final int position, final Alliance alliance)
    {
        this.position = position;
        this.alliance = alliance;
    }

    public Alliance getAlliance()
    {
        return alliance;
    }

    public abstract Collection<Move> getLegalMoves(final Board board);

}
