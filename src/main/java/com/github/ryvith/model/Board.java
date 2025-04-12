package com.github.ryvith.model;

import lombok.Value;

import java.awt.*;
import java.util.Arrays;

@Value
public class Board
{
    int size;
    Piece[][] grid;

    // 私有构造函数
    private Board(int size){
        this.size = size;
        grid = new Piece[size][size];
        for(Piece[] row: grid){
            Arrays.fill(row, Piece.EMPTY);
        }
    }

    public static Board createBoard(int size, BoardInitializer initializer){
        Board board = new Board(size);
        if(initializer != null) {
            initializer.initialize(board);
        }
        return board;
    }

    /* 落子 */
    public void setPiece(int x, int y, Piece piece){
        grid[x][y] = piece;
    }

    /* 获取某位置的棋子 */
    public Piece getPiece(Point position){
        if (position == null ||
                position.x < 0 || position.x >= size ||
                position.y < 0 || position.y >= size) {
            return null;
        }
        return grid[position.x][position.y];
    }


}
