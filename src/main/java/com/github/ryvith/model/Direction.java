package com.github.ryvith.model;

import java.awt.*;

public enum Direction {
    UP(0, 1), DOWN(0, -1),
    LEFT(-1, 0), RIGHT(0, 1),
    LEFT_UP(-1, 1), RIGHT_UP(1,1),
    LEFT_DOWN(-1, -1), RIGHT_DOWN(1, -1);

    private final Point delta;
    Direction(int dx, int dy) {
        this.delta = new Point(dx, dy);
    }
}
