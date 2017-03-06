package net.ddns.grbcp5.chess.engine.board;

/**
 *
 * Created by GrantBroadwater on 3/1/17.
 */
public class BoardUtils
{
    private BoardUtils()
    {
        throw new RuntimeException("Cannot instantiate BoardUtils");
    }

    public static boolean isValidTileCoordinate(final int destinationCoordinat)
    {
        return 0 <= destinationCoordinat && destinationCoordinat < Tile.NUM_TILES;
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

    public static boolean isEigthColumn(final int currentPosition)
    {
        return isNColumn(currentPosition, 8);
    }

    private static boolean isNColumn(final int currentPosition, final int columnNumber)
    {
        return isValidTileCoordinate(currentPosition) && currentPosition % 8 == (columnNumber - 1);
    }

}
