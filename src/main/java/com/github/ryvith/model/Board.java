package com.github.ryvith.model;

import lombok.Value;

import java.awt.*;

@Value
public class Board
{
    int size;
    Piece[][] grid;

    // 私有构造函数
    private Board(int size){
        this.size = size;
        grid = new Piece[size][size];
    }

    public static Board createBoard(int size, BoardInitializer initializer){
        Board board = new Board(size);
        initializer.initialize(board);
        return board;
    }

    /* board类内部的setPiece方法 */
    void setPiece(int x, int y, Piece piece){
        grid[x][y] = piece;
    }

    /* 对外的setPiece重载 */
    void setPiece(Point point, Piece piece){
        grid[point.x][point.y] = piece;
    }
}
