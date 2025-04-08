package com.github.ryvith.game;

import com.github.ryvith.model.*;

import java.awt.*;

import lombok.Getter;

@Getter
public abstract class Game {
    protected final GameConfig config;
    protected Board board;
    protected Player[] players;
    protected Player currentPlayer;

    /**
     * Game基类构造函数
     * @param config 游戏配置
     * @param boardInitializer 棋盘初始化策略
     */
    public Game(GameConfig config, BoardInitializer boardInitializer) {
        this.config = config;
        // 初始化棋盘，使用策略模式
        this.board = Board.createBoard(config.getBoardSize(),boardInitializer); // 初始化棋盘
        // 2.初始化玩家
        this.players = new Player[]{
                new Player(
                        config.getPlayer1Name(),
                        0,
                        1,
                        Piece.BLACK //玩家1先手，为黑棋
                ),
                new Player(
                        config.getPlayer2Name(),
                        0,
                        2,
                        Piece.WHITE
                )
        };
        this.currentPlayer = players[0];
    }


    // 是否在棋盘边界内
    public boolean isOnBoard(Board board, Point point) {
        return point.x >= 0 && point.x<= board.getSize() && point.y >= 0 && point.y<= board.getSize();
    }

    /**
     * 是否是有效的下棋操作
     * @param board 棋盘
     * @param position
     * @return 是/否
     */
    public abstract boolean isValidMove(Board board, Point position);

    /**
     * 处理用户下棋操作
     * @param board 当前游戏棋盘
     * @param position 落子位置
     */
    public void setPiece(Board board, Point position){
        if(isOnBoard(board, position) && isValidMove(board, position)){
            board.getGrid()[position.x][position.y] = currentPlayer.getPiece();
        }else{
            System.out.println("落子位置有误，请重新输入。");
        }
    };

    /* 退出游戏 */
    public static void quitGame(){
        System.exit(0);
    }

    /* 无效输入 */
    public static void handleInvalidInput(){
        System.out.println("非法输入，请重新输入。");
    }

    /**
     * 游戏是否结束，和平模式判断棋盘是否满了，reversi模式判断是否有一方胜利
     * @return 是/否
     */
    public abstract boolean isGameOver();

    /**
     * 处理游戏结束
     */
    public abstract void handleGameEnd();
}
