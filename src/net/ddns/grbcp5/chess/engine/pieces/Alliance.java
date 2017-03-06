package net.ddns.grbcp5.chess.engine.pieces;

/**
 *
 *
 * Created by GrantBroadwater on 3/1/17.
 */
public enum Alliance
{
    WHITE
            {
                @Override
                final int getDirection()
                {
                    return -1;
                }

                @Override
                boolean isBlack()
                {
                    return false;
                }

                @Override
                boolean isWhite()
                {
                    return true;
                }
            },
    BLACK
            {
                @Override
                int getDirection()
                {
                    return 1;
                }

                @Override
                boolean isBlack()
                {
                    return true;
                }

                @Override
                boolean isWhite()
                {
                    return false;
                }
            };

    abstract int getDirection();
    abstract boolean isBlack();
    abstract boolean isWhite();
    }
