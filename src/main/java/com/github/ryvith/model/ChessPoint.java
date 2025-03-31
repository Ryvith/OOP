package com.github.ryvith.model;

import java.awt.*;

public enum ChessPoint {
    UP(0, 1), DOWN(0, -1),
    LEFT(-1, 0), RIGHT(0, 1),
    LEFT_UP(-1, 1), RIGHT_UP(1,1),
    LEFT_DOWN(-1, -1), RIGHT_DOWN(1, -1);

    private final Point delta;

    ChessPoint(int dx, int dy) {
        this.delta = new Point(dx, dy);
    }

    /**
     * 获取移动后的棋子坐标
     * @param point 当前棋子
     * @param direction 移动的方向（UP,DOWN等八个方向之一）
     * @return 移动后的棋子坐标
     */
    public static Point getAdjacentPoint(Point point, ChessPoint direction) {
        return new Point(
                point.x + direction.delta.x,
                point.y + direction.delta.y
        );
    }
}
