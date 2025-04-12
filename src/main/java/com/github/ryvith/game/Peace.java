package com.github.ryvith.game;

import com.github.ryvith.model.StandardBoardInitializer;
import com.github.ryvith.model.Piece;

import java.awt.*;

public class Peace extends Game {
    public Peace(GameConfig config) {
        super(config, new StandardBoardInitializer());
    }

    /* 落子位置验证 */
    @Override
    public boolean isValidMove(Point position) {
        return isOnBoard(position) && isPositionEmpty(position);
    }

    /* 处理落子 */
    @Override
    public boolean handleMove(Point position) {
        if (isValidMove(position)) {
            setPiece(position);
            return true;
        }
        return false;
    }


    @Override
    public boolean shouldGameEnd() {
        return countPieces(Piece.EMPTY) == 0;
    }

    @Override
    public GameResult handleGameEnd() {
        return new GameResult(0, 0, null);
    }

}
