package com.github.ryvith.game;

import com.github.ryvith.model.Board;
import com.github.ryvith.model.Piece;

import java.awt.*;

public class peaceGame extends GameMode {
    public peaceGame(String gameMode, int gameNumber) {
        super(gameMode, gameNumber);
    }

    @Override
    boolean isValidMove(Board board, Point position) {
        return board.getGrid()[position.x][position.y] == Piece.EMPTY;
    }
}
