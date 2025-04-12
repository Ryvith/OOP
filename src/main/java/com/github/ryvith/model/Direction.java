package com.github.ryvith.model;

import java.awt.*;

public enum Direction {
    UP(0, 1), DOWN(0, -1),
    LEFT(-1, 0), RIGHT(1, 0),
    LEFT_UP(-1, 1), RIGHT_UP(1,1),
    LEFT_DOWN(-1, -1), RIGHT_DOWN(1, -1);

    public final int dx;
    public final int dy;

    private Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public static Direction getOppositeDirection(Direction dir) {
        return switch (dir) {
            case UP -> Direction.DOWN;
            case DOWN -> Direction.UP;
            case LEFT -> Direction.RIGHT;
            case RIGHT -> Direction.LEFT;
            case LEFT_UP -> Direction.RIGHT_DOWN;
            case RIGHT_UP -> Direction.LEFT_DOWN;
            case LEFT_DOWN -> Direction.RIGHT_UP;
            case RIGHT_DOWN -> Direction.LEFT_UP;
            default -> throw new IllegalArgumentException("未知方向");
        };
    }
}
