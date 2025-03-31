package com.github.ryvith.game;

import com.github.ryvith.model.Board;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class reversiGame extends GameMode {
    @Override
    boolean isValidMove(Board board, Point move) {
        return false;
    }

    public List<Point> getFlippedPoints(Board board, Point move){
        List<Point> flippedPoints = new ArrayList<Point>();
        return null;
    }
}
