package com.github.ryvith.controller;

import com.github.ryvith.model.Board;
import com.github.ryvith.model.BoardInitializer;
import com.github.ryvith.model.StandardBoardInitializer;
import com.github.ryvith.ui.BoardPrinter;

public class gameController {
    public static void gameStart() {
        BoardInitializer initializer = new StandardBoardInitializer();
        Board board = Board.createBoard(8, initializer);
        BoardPrinter.printInitialBoard(board);
    }
}
