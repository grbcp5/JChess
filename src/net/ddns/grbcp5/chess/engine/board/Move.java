package net.ddns.grbcp5.chess.engine.board;

import net.ddns.grbcp5.chess.engine.pieces.Piece;

/**
 * Created by GrantBroadwater on 3/1/17.
 */
public abstract class Move
{

    final Board board;
    final Piece movedPice;
    final int destinationCoordinate;

    private Move(final Board board, final Piece movedPice, final int destinationCoordinate)
    {
        this.board = board;
        this.movedPice = movedPice;
        this.destinationCoordinate = destinationCoordinate;
    }

    public static final class MajorMove extends Move
    {
        public MajorMove(final Board board, final Piece movedPice, final int destinationCoordinate)
        {
            super(board, movedPice, destinationCoordinate);
        }
    }

    public static final class AttackMove extends Move
    {
        final Piece attackedPiece;

        public AttackMove(final Board board, final Piece movedPice, final int destinationCoordinate,
                          final Piece attackedPiece)
        {
            super(board, movedPice, destinationCoordinate);

            this.attackedPiece = attackedPiece;
        }
    }
}
