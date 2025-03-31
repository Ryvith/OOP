package com.github.ryvith.controller;

import com.github.ryvith.model.Board;
import com.github.ryvith.model.BoardInitializer;
import com.github.ryvith.model.Player;
import com.github.ryvith.model.Piece;
import com.github.ryvith.model.StandardBoardInitializer;
import com.github.ryvith.ui.BoardPrinter;

public class gameController {
    public static void gameStart() {
        BoardInitializer initializer = new StandardBoardInitializer();
        Board board = Board.createBoard(8, initializer);
        BoardPrinter.printInitialBoard(board);

        Player player1 = new Player("Tom",0, 1,Piece.WHITE);
        Player player2 = new Player("Jerry",0,2,Piece.BLACK);
    }
}
