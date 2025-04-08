package com.github.ryvith.game;

import com.github.ryvith.model.Board;
import com.github.ryvith.model.Piece;
import com.github.ryvith.model.StandardBoardInitializer;

import java.awt.*;

public class PeaceGame extends Game {
    public PeaceGame(GameConfig config) {
        super(config, new StandardBoardInitializer());
    }

    @Override
    public boolean isValidMove(Board board, Point position) {
        return board.getGrid()[position.x][position.y] == Piece.EMPTY;
    }

    @Override
    public boolean isGameOver() {
        return false;
    }

    @Override
    public void handleGameEnd() {

    }
}
