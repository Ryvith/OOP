package com.github.ryvith.game;

import com.github.ryvith.model.Board;
import com.github.ryvith.model.StandardBoardInitializer;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class ReversiGame extends Game {
    public ReversiGame(GameConfig config) {
        super(config, new StandardBoardInitializer());
    }

    @Override
    public boolean isValidMove(Board board, Point move) {
        return false;
    }

    /**
     * 获得需要翻转的棋子
     * @param board
     * @param position 以当前棋子为中心
     * @return 返回需要翻转的棋子位置列表
     */
    public List<Point> getFlippedPoints(Board board, Point position){
        List<Point> flippedPoints = new ArrayList<Point>();
        Point currentPosition = position;
        while(board.getGrid()[position.x][position.y].getSymbol() != board.getGrid()[currentPosition.x][currentPosition.y].getSymbol()){
            flippedPoints.add(new Point(currentPosition.x,currentPosition.y));

        }
        return null;
    }
}
