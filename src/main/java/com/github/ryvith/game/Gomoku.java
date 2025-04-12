package com.github.ryvith.game;

import com.github.ryvith.model.Direction;
import com.github.ryvith.model.Piece;
import com.github.ryvith.model.Player;
import lombok.Getter;

import java.awt.*;

@Getter
public class Gomoku extends Game{
    private int round = 0;
    private Player winner;

    public Gomoku(GameConfig config) {
        super(config, null);
    }

    @Override
    public boolean isValidMove(Point position) {
        return isOnBoard(position) && isPositionEmpty(position);
    }

    /**
     * 处理用户落子
     * @param position 落子位置
     * @return 落子成功/失败
     */
    @Override
    public boolean handleMove(Point position) {
        if(isValidMove(position)) {
            setPiece(position);
            round++;
            shouldGameEnd = hasFiveInLine(position, board.getPiece(position)); // 检查是否五子连线
            if(shouldGameEnd) {
                winner = currentPlayer;
            }
            return true;
        }
        return false;
    }

    /**
     * 检查从指定位置出发是否形成五子连线
     */
    private boolean hasFiveInLine(Point position, Piece color) {
        // 只需要检查四个主要方向，避免重复检查
        Direction[] checkDirections = {
                Direction.RIGHT,      // 水平
                Direction.UP,         // 垂直
                Direction.RIGHT_UP,   // 右上
                Direction.RIGHT_DOWN  // 右下
        };

        for (Direction dir : checkDirections) {
            if (countConsecutive(position, dir, color) >= 5) {
                return true;
            }
        }
        return false;
    }

    /**
     * 计算指定方向上的连续同色棋子数
     */
    private int countConsecutive(Point start, Direction dir, Piece piece) {
        int forwardCount = countInDirection(start, dir, piece);
        int backwardCount = countInDirection(start, Direction.getOppositeDirection(dir), piece);
        return forwardCount + backwardCount + 1;
    }

    /* 向指定方向计数连续同色棋子 */
    private int countInDirection(Point start, Direction dir, Piece piece) {
        int count = 0;
        Point current = new Point(start.x + dir.dx, start.y + dir.dy);

        while (isOnBoard(current)) {
            Piece currentPiece = board.getPiece(current);
            if (currentPiece == null || currentPiece != piece) {
                break;
            }
            count++;
            current.x += dir.dx;
            current.y += dir.dy;
        }
        return count;
    }


    // =============== 游戏结束 =============
    @Override
    public boolean shouldGameEnd() {
        // 每次落子时已判断完，此处无需全局判断
        return false;
    }

    @Override
    public GameResult handleGameEnd() {
        return new GameResult(0, 0, winner);
    }
}
