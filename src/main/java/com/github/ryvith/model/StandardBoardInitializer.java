package com.github.ryvith.model;

/*
 * 标准黑白棋初始化
 *
 * */
public class StandardBoardInitializer implements BoardInitializer{
    @Override
    public void initialize(Board board) {
        // 设置中心四子
        int center = board.getSize()/2 -1;
        board.setPiece(center,center, Piece.WHITE);
        board.setPiece(center,center+1, Piece.BLACK);
        board.setPiece(center+1, center, Piece.BLACK);
        board.setPiece(center+1, center+1, Piece.WHITE);
    }
}
