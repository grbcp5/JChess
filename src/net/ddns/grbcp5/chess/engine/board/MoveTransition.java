package net.ddns.grbcp5.chess.engine.board;

/**
 *
 * Created by GrantBroadwater on 3/6/17.
 */
public class MoveTransition
{
    private final Board transitionBoard;
    private final Move move;
    private final MoveStatus moveStatus;

    public MoveTransition(final Board transitionBoard, final Move move, final MoveStatus moveStatus)
    {
        this.transitionBoard = transitionBoard;
        this.move = move;
        this.moveStatus = moveStatus;
    }
}
