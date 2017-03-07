package net.ddns.grbcp5.chess.engine.board;

import com.google.common.collect.ImmutableList;
import net.ddns.grbcp5.chess.engine.pieces.*;
import net.ddns.grbcp5.chess.engine.player.BlackPlayer;
import net.ddns.grbcp5.chess.engine.player.WhitePlayer;

import java.util.*;

import static net.ddns.grbcp5.chess.engine.pieces.Alliance.WHITE;
import static net.ddns.grbcp5.chess.engine.pieces.Alliance.BLACK;

/**
 *    a   b   c   d   e   f   g   h
 *   |-------------------------------|
 * 8 | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 |
 *   |-------------------------------|
 * 7 | 8 | 9 |10 |11 |12 |13 |14 |15 |
 *   |-------------------------------|
 * 6 |16 |   |   |   |   |   |   |   |
 *   |-------------------------------|
 * 5 |24 |   |   |   |   |   |   |   |
 *   |-------------------------------|
 * 4 |32 |   |   |   |   |   |   |   |
 *   |-------------------------------|
 * 3 |40 |   |   |   |   |   |   |   |
 *   |-------------------------------|
 * 2 |48 |   |   |   |   |   |   |   |
 *   |-------------------------------|
 * 1 |56 |57 |58 |59 |60 |61 |62 |63 |
 *   |-------------------------------|
 *
 * Created by GrantBroadwater on 3/1/17.
 */
public class Board
{
    private final List<Tile> gameBoard;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;

    public Collection<Piece> getWhitePieces()
    {
        return this.whitePieces;
    }

    public Collection<Piece> getBlackPieces()
    {
        return this.blackPieces;
    }

    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;

    public Board(final Builder builder)
    {
        gameBoard = createGameBoard(builder);
        this.whitePieces = calculateActivePieces(this.gameBoard, WHITE);
        this.blackPieces = calculateActivePieces(this.gameBoard, BLACK);

        final Collection<Move> whiteStandardLegalMoves = calculateLegalMoves(this.whitePieces);
        final Collection<Move> blackStandardLegalMoves = calculateLegalMoves(this.blackPieces);

        this.whitePlayer = new WhitePlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
        this.blackPlayer = new BlackPlayer(this, blackStandardLegalMoves, whiteStandardLegalMoves);
    }

    private Collection<Move> calculateLegalMoves(Collection<Piece> pieceSet)
    {
        final List<Move> legalMoves = new ArrayList<>();

        for(final Piece piece : pieceSet)
        {
            legalMoves.addAll(piece.getLegalMoves(this));
        }

        return ImmutableList.copyOf(legalMoves);
    }

    private static Collection<Piece> calculateActivePieces(List<Tile> gameBoard, Alliance alliance)
    {
        final List<Piece> activePieces = new ArrayList<>();

        for(final Tile tile : gameBoard)
        {
            if(tile.isOccupied())
            {
                final Piece piece = tile.getPiece();
                if(piece.getAlliance() == alliance)
                {
                    activePieces.add(piece);
                }
            }
        }

        return ImmutableList.copyOf(activePieces);
    }

    public Tile getTile(final int tileCoordinate)
    {
        return gameBoard.get(tileCoordinate);
    }

    private static List<Tile> createGameBoard(final Builder builder)
    {
        final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];

        for(int i=0; i<BoardUtils.NUM_TILES; i++)
        {
            tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
        }

        return ImmutableList.copyOf(tiles);
    }

    @Override
    public String toString()
    {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i=0; i<BoardUtils.NUM_TILES; i++)
        {
            final String tileText = this.gameBoard.get(i).toString();
            stringBuilder.append(String.format("%3s", tileText));
            if((i+1) % BoardUtils.NUM_TILES_PER_ROW == 0)
            {
                stringBuilder.append("\n");

            }
        }

        return stringBuilder.toString();
    }

    public static Board createInitialBoard()
    {
        final Builder builder = new Builder();

        builder.setPiece(new Rook(0, BLACK));
        builder.setPiece(new Knight(1, BLACK));
        builder.setPiece(new Bishop(2, BLACK));
        builder.setPiece(new Queen(3, BLACK));
        builder.setPiece(new King(4, BLACK));
        builder.setPiece(new Bishop(5, BLACK));
        builder.setPiece(new Knight(6, BLACK));
        builder.setPiece(new Rook(7, BLACK));
        builder.setPiece(new Pawn(8, BLACK));
        builder.setPiece(new Pawn(9, BLACK));
        builder.setPiece(new Pawn(10, BLACK));
        builder.setPiece(new Pawn(11, BLACK));
        builder.setPiece(new Pawn(12, BLACK));
        builder.setPiece(new Pawn(13, BLACK));
        builder.setPiece(new Pawn(14, BLACK));
        builder.setPiece(new Pawn(15, BLACK));

        builder.setPiece(new Pawn(48, WHITE));
        builder.setPiece(new Pawn(49, WHITE));
        builder.setPiece(new Pawn(50, WHITE));
        builder.setPiece(new Pawn(51, WHITE));
        builder.setPiece(new Pawn(52, WHITE));
        builder.setPiece(new Pawn(53, WHITE));
        builder.setPiece(new Pawn(54, WHITE));
        builder.setPiece(new Pawn(55, WHITE));
        builder.setPiece(new Rook(56, WHITE));
        builder.setPiece(new Knight(57, WHITE));
        builder.setPiece(new Bishop(58, WHITE));
        builder.setPiece(new Queen(59, WHITE));
        builder.setPiece(new King(60, WHITE));
        builder.setPiece(new Bishop(61, WHITE));
        builder.setPiece(new Knight(62, WHITE));
        builder.setPiece(new Rook(63, WHITE));

        builder.setMoveMaker(WHITE);

        return builder.build();

    }

    public static class Builder
    {
        Map<Integer, Piece> boardConfig;
        Alliance nextMoveMaker;

        public Builder()
        {
            boardConfig = new HashMap<Integer, Piece>(64);
            nextMoveMaker = WHITE;
        }

        public Board build()
        {
            return new Board(this);
        }

        public Builder setPiece(final Piece piece)
        {
            boardConfig.put(piece.getPosition(), piece);
            return this;
        }

        public Builder setMoveMaker(final Alliance nextMoveMaker)
        {
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }
    }
}
