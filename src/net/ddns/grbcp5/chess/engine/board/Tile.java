package net.ddns.grbcp5.chess.engine.board;

/* Import statements */

import net.ddns.grbcp5.chess.engine.pieces.Piece;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

/**
 * Class for representing a tile on a chess board
 * <p>
 * Created by GrantBroadwater on 3/1/17.
 */
public abstract class Tile
{
    /**
     * Number of different tile locations
     */
    public static final int NUM_TILES = BoardUtils.NUM_TILES;

    // Tile coordinate location from 0 - 63
    protected final int tileCoordinate;

    private static final Map<Integer, EmptyTile> EMPTY_TILE_CACHE = createAllPossibleEmptyTiles();

    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles()
    {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<Integer, EmptyTile>();

        for (int i = 0; i < NUM_TILES; i++)
        {
            emptyTileMap.put(i, new EmptyTile(i));
        }

        return ImmutableMap.copyOf(emptyTileMap);
    }

    /**
     * Constructor for the abstract Tile class
     *
     * @param tileCoordinate location of tile in board from 0 - 63
     */
    public Tile(final int tileCoordinate)
    {
        this.tileCoordinate = tileCoordinate;
    }

    /**
     * Will indicate if a this tile currently has a chess piece
     *
     * @return true if a piece is currently on this tile, false otherwise
     */
    public abstract boolean isOccupied();

    /**
     * Accessor for the piece currently on this tile
     *
     * @return The piece object located on this tile, null if tile is empty
     */
    public abstract Piece getPiece();

    /**
     * Factory function for producing a chess tile
     * <p>
     * Factory function should be used because it grantees using the correct Tile subclass. Will also used cached
     * EmptyTile objects rather than regenerating new instances
     *
     * @param tileCoordinate location of tile on board from 0 - 63
     * @param pieceOnTile    piece located on the new tile
     * @return Tile instance with given values
     */
    public static Tile createTile(final int tileCoordinate, final Piece pieceOnTile)
    {
        return (pieceOnTile != null) ?
                new OccupiedTile(tileCoordinate, pieceOnTile) :
                EMPTY_TILE_CACHE.get(tileCoordinate);
    }

    /**
     * Tile subclass for tile without a piece on this tile
     */
    public static final class EmptyTile extends Tile
    {

        /**
         * Constructor for tile without piece on this tile
         *
         * @param tileCoordinate location of tile on board from 0 - 63
         */
        private EmptyTile(int tileCoordinate)
        {
            super(tileCoordinate);
        }

        /**
         * Will indicate if a this tile currently has a chess piece
         *
         * @return false by definition that this is an empty tile
         */
        @Override
        public boolean isOccupied()
        {
            return false;
        }

        /**
         * Accessor for the piece currently on this tile
         *
         * @return null by definition that this is an empty tile
         */
        @Override
        public Piece getPiece()
        {
            return null;
        }

        @Override
        public String toString()
        {
            return "-";
        }
    }

    public static final class OccupiedTile extends Tile
    {

        private final Piece pieceOnTile;

        /**
         * Constructor for a tile with a piece currently on this tile
         *
         * @param tileCoordinate location of tile on board form 0 - 63
         * @param pieceOnTile    piece placed on this tile
         */
        private OccupiedTile(final int tileCoordinate, final Piece pieceOnTile)
        {
            // Init superclass
            super(tileCoordinate);

            // Init member variables
            if (pieceOnTile != null)
            {
                this.pieceOnTile = pieceOnTile;
            } else
            {
                // TODO: Throw exception
                this.pieceOnTile = null;
            }
        }

        /**
         * Will indicate if a this tile currently has a chess piece
         *
         * @return true by definition that this is an occupied tile
         */
        @Override
        public boolean isOccupied()
        {
            return true;
        }

        /**
         * Accessor for the piece currently on this tile
         *
         * @return the piece currently placed on this tile
         */
        @Override
        public Piece getPiece()
        {
            return pieceOnTile;
        }

        @Override
        public String toString()
        {
            return this.getPiece().getAlliance().isBlack() ?
                    this.getPiece().toString().toLowerCase() :
                    this.getPiece().toString().toUpperCase();
        }
    }
}
