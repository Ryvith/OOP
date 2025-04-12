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
    public boolean shouldGameEnd;
    /**
     * Game基类构造函数
     * @param config 游戏配置
     * @param boardInitializer 棋盘初始化策略
     */
    public Game(GameConfig config, BoardInitializer boardInitializer) {
        this.config = config;
        // 初始化棋盘，使用策略模式
        this.board = Board.createBoard(config.getBoardSize(), boardInitializer); // 初始化棋盘
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
    // ============= 玩家相关操作 ==============

    /* 根据棋子获取玩家 */
    public Player findPlayerByPiece(Piece piece) {
        for (Player player : players) {
            if(player.piece() == piece) {
                return player;
            }
        }
        return null;
    }

    /* 获取对手 */
    public Player getOpponent(Player player) {
        return currentPlayer == players[0]? players[1]:players[0];
    }

    /* 切换玩家 */
    public void switchPlayer() {
        this.currentPlayer = getOpponent(currentPlayer);
    }

    // ============== 落子相关操作 ==============

    // 是否在棋盘边界内
    public boolean isOnBoard(Point point) {
        return point.x >= 0 && point.x< board.getSize() && point.y >= 0 && point.y< board.getSize();
    }

    // 落子位置是否未已有棋子
    public boolean isPositionEmpty(Point point) {
        return board.getPiece(point) == Piece.EMPTY;
    }

    /**
     * 是否是有效的下棋操作
     */
    public abstract boolean isValidMove(Point position);

    /**
     *
     */
    public abstract boolean handleMove(Point position);


    /**
     * 落子
     */
    public void setPiece(Point position){
        if(isOnBoard(position) && isValidMove(position)){
            board.getGrid()[position.x][position.y] = currentPlayer.piece();
        }else{
            System.out.println("落子位置有误，请重新输入。");
        }
    };

    /* 计算棋子数量 */
    public int countPieces(Piece piece) {
        Piece[][] grid = getBoard().getGrid();
        int count = 0;
        for (Piece[] row : grid) {
            for (Piece p : row) {
                if (p == piece) count++;
            }
        }
        return count;
    }

    // ================ 游戏相关 =================

    /* 退出游戏 */
    public static void quitGame(){
        System.exit(0);
    }

    /* 无效输入 */
    public static void handleInvalidInput(){
        System.out.println("非法输入，请重新输入。");
    }


    /**
     * 游戏是否结束
     */
    public abstract boolean shouldGameEnd();

    /**
     * 处理游戏结束
     * @return GameResult 游戏结果相关信息
     */
    public abstract GameResult handleGameEnd();
}
