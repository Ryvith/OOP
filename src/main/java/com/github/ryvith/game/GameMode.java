package com.github.ryvith.game;

import com.github.ryvith.model.Board;

import java.awt.*;

abstract class GameMode {

    boolean isOnBoard(Board board, Point point) {
        return point.x >= 0 && point.x<= board.getSize() && point.y >= 0 && point.y<= board.getSize();
    }

    abstract boolean isValidMove(Board board, Point move);
}
