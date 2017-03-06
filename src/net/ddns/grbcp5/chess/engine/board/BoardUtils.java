package net.ddns.grbcp5.chess.engine.board;

/**
 *
 * Created by GrantBroadwater on 3/1/17.
 */
public class BoardUtils
{
    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_PER_ROW = 8;

    private BoardUtils()
    {
        throw new RuntimeException("Cannot instantiate BoardUtils");
    }

    public static boolean isValidTileCoordinate(final int destinationCoordinat)
    {
        return 0 <= destinationCoordinat && destinationCoordinat < NUM_TILES;
    }

    public static boolean isFirstColumn(final int currentPosition)
    {
        return isNColumn(currentPosition, 1);
    }

    public static boolean isSecondColumn(final int currentPosition)
    {
        return isNColumn(currentPosition, 2);
    }

    public static boolean isSeventhColumn(final int currentPosition)
    {
        return isNColumn(currentPosition, 7);
    }

    public static boolean isEighthColumn(final int currentPosition)
    {
        return isNColumn(currentPosition, 8);
    }

    private static boolean isNColumn(final int currentPosition, final int columnNumber)
    {
        return isValidTileCoordinate(currentPosition) && currentPosition % NUM_TILES_PER_ROW == (columnNumber - 1);
    }

    public static boolean isSecondRow(final int currentPosition)
    {
        return 8 <= currentPosition && currentPosition < 16;
    }

    public static boolean isSeventhRow(final int currentPosition)
    {
        return 48 <= currentPosition && currentPosition < 56;
    }
}
