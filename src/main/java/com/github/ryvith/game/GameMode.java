package com.github.ryvith.game;

import com.github.ryvith.model.Board;

import java.awt.*;
import lombok.Getter;

@Getter
public abstract class GameMode {
    private final String gameMode;
    private final int gameNumber;

    public GameMode(String gameMode, int gameNumber) {
        this.gameMode = gameMode;
        this.gameNumber = gameNumber;
    }

    boolean isOnBoard(Board board, Point point) {
        return point.x >= 0 && point.x<= board.getSize() && point.y >= 0 && point.y<= board.getSize();
    }

    abstract boolean isValidMove(Board board, Point move);
}
