package net.ddns.grbcp5.chess.engine.board;

import net.ddns.grbcp5.chess.engine.pieces.Piece;

import java.util.HashMap;
import java.util.Map;
import com.google.common.collect.ImmutableMap;

/**
 * Class for representing a tile on a chess board
 *
 * Created by GrantBroadwater on 3/1/17.
 */
public abstract class Tile
{
    public static final int NUM_PIECES = 64;

    // Tile coordinate location from 0 - 63
    protected final int tileCoordinate;

    private static final Map<Integer, EmptyTile> EMPTY_TILE_MAP = createAllPossibleEmptyTiles();

    private static Map<Integer,EmptyTile> createAllPossibleEmptyTiles()
    {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<Integer, EmptyTile>();

        for (int i=0; i<NUM_PIECES; i++)
        {
            emptyTileMap.put(i, new EmptyTile(i));
        }

        return ImmutableMap.copyOf(emptyTileMap);
    }

    public Tile(final int tileCoordinate)
    {
        this.tileCoordinate = tileCoordinate;
    }

    public abstract boolean isOccupied();

    public abstract Piece getPiece();

    public static final class EmptyTile extends Tile
    {

        public EmptyTile(int tileCoordinate)
        {
            super(tileCoordinate);
        }

        @Override
        public boolean isOccupied()
        {
            return false;
        }

        @Override
        public Piece getPiece()
        {
            return null;
        }
    }

    public static final class OccupiedTile extends Tile
    {
        private final Piece pieceOnTile;

        public OccupiedTile(final int tileCoordinate, final Piece pieceOnTile)
        {
            // Init superclass
            super(tileCoordinate);

            // Init member variables
            if (pieceOnTile != null)
            {
                this.pieceOnTile = pieceOnTile;
            }
            else
            {
                // TODO: Throw exception
                this.pieceOnTile = null;
            }
        }

        @Override
        public boolean isOccupied()
        {
            return true;
        }

        @Override
        public Piece getPiece()
        {
            return pieceOnTile;
        }
    }
}
