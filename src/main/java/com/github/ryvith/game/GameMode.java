package com.github.ryvith.game;

import com.github.ryvith.model.Board;

import java.awt.*;

import com.github.ryvith.model.Player;
import lombok.Getter;

@Getter
public abstract class GameMode {
    private final String gameMode;
    private final int gameNumber;
    private final Player player1;
    private final Player player2;
    private Player currentPlayer;

    public GameMode(String gameMode, int gameNumber, Player player1, Player player2) {
        this.gameMode = gameMode;
        this.gameNumber = gameNumber;
        this.player1 = player1;
        this.player2 = player2;
    }

    boolean isOnBoard(Board board, Point point) {
        return point.x >= 0 && point.x<= board.getSize() && point.y >= 0 && point.y<= board.getSize();
    }

    abstract boolean isValidMove(Board board, Point move);
}
