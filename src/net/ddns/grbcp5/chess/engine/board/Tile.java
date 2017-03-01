package net.ddns.grbcp5.chess.engine.board;

import net.ddns.grbcp5.chess.engine.pieces.Piece;

/**
 * Created by GrantBroadwater on 3/1/17.
 */
public abstract class Tile
{

    // Tile coordinate location from 0 - 63
    int tileCoordinate;

    public Tile( int tileCoordinate )
    {
        this.tileCoordinate = tileCoordinate;
    }

    public abstract boolean isOccupied();

    public abstract Piece getPiece();

    public static final class EmptyTile extends Tile
    {

        public EmptyTile( int tileCoordinate ) {
            super( tileCoordinate );
        }

        @Override
        public boolean isOccupied()
        {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }
    }
}
