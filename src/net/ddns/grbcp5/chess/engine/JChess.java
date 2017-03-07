package net.ddns.grbcp5.chess.engine;

import net.ddns.grbcp5.chess.engine.board.Board;

/**
 *
 * Created by GrantBroadwater on 3/6/17.
 */
public class JChess
{
    public static void main(String[] args)
    {
        Board board = Board.createInitialBoard();

        System.out.println(board);
    }
}
