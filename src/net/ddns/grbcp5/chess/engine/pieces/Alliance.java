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
                public boolean isBlack()
                {
                    return false;
                }

                @Override
                public boolean isWhite()
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
                public boolean isBlack()
                {
                    return true;
                }

                @Override
                public boolean isWhite()
                {
                    return false;
                }
            };

    abstract int getDirection();
    public abstract boolean isBlack();
    public abstract boolean isWhite();
    }
