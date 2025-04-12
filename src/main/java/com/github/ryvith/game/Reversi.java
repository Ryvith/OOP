package com.github.ryvith.game;

import com.github.ryvith.model.*;
import com.github.ryvith.model.StandardBoardInitializer;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Reversi extends Game {
    public Reversi(GameConfig config) {
        super(config, new StandardBoardInitializer());
    }

 /* 判断落子位置是否有效 */
    @Override
    public boolean isValidMove(Point position) {
        // 基础验证
        if(!isOnBoard(position) || !isPositionEmpty(position)){
            return false;
        }
        // 核心验证规则，至少翻转对手一个棋子
        return !getFlippedPoints(position,currentPlayer).isEmpty();
    }

    // =============== 翻转棋子 ===============

    /**
     * 处理落子，进行翻转
     * @param position 落子位置
     * @return 落子成功/失败
     */
    @Override
    public boolean handleMove(Point position) {
        List<Point> flipped = getFlippedPoints(position, currentPlayer);
        if(!flipped.isEmpty()) {
            flipPieces(position, flipped);
            return true;
        }
        return false;
    }

    /* 计算某处落子可翻转的棋子 */
    public List<Point> getFlippedPoints(Point position, Player currentPlayer) {
        Player opponent = getOpponent(currentPlayer);
        List<Point> flipped = new ArrayList<>();

        // 如果位置已经有棋子，直接返回空列表
        if(board.getPiece(position) != Piece.EMPTY) {
            return flipped;
        }

        for(Direction dir : Direction.values()){
            List<Point>temp = new ArrayList<>();
            Point current = new Point(position.x + dir.dx, position.y+ dir.dy);
            // 获取需要翻转的对手的棋子
            while(isOnBoard(current) && board.getPiece(current) == opponent.piece()){
                temp.add(new Point(current));
                current.x += dir.dx;
                current.y += dir.dy;
            }
            // 验证是否以己方棋子结尾，如果是，添加所有需要翻转的棋子
            if(isOnBoard(current) && board.getPiece(current) == currentPlayer.piece() && !temp.isEmpty()){
                flipped.addAll(temp);
            }
        }
        return flipped;
    }

    /* 获取场上可以落子的有效位 */
    public Map<Point, List<Point>> getValidPosition(){
        Map<Point, List<Point>> validPosition = new HashMap<Point, List<Point>>();
        for(int i = 0; i < board.getSize(); i++){
            for(int j = 0; j < board.getSize(); j++){
                Point pos = new Point(i, j);
                if(board.getPiece(pos) == Piece.EMPTY){
                    List<Point> flipped = getFlippedPoints(pos, currentPlayer);
                    if(!flipped.isEmpty()){
                        validPosition.put(pos, flipped);
                    }
                }
            }
        }
        return validPosition;
    }

    /* 执行翻转操作 */
    public void flipPieces(Point position, List<Point> toFlip) {
        board.getGrid()[position.x][position.y] = currentPlayer.piece(); // 落子
        System.out.println("翻转棋子: " + toFlip);
        for (Point p : toFlip) {
            board.getGrid()[p.x][p.y] = currentPlayer.piece(); // 翻转对手棋子
        }
    }

    /* 当前玩家是否可以pass */
    public boolean canPass(){
        return getValidPosition().isEmpty();
    }

    /**
     * 处理玩家跳过回合的操作
     * @return 是否成功执行了Pass操作
     * @throws IllegalStateException 当玩家仍有合法落子位置时抛出
     */
    public boolean handlePass() throws IllegalStateException {
        if (!canPass()) {
            throw new IllegalStateException("当前玩家仍有合法落子位置，不能跳过回合");
        }
        switchPlayer();
        return !canPass(); // false表示游戏应该结束，true表示pass成功，游戏继续
    }

    // ========== 游戏结束相关操作 ===========

    /* 判断游戏是否应该结束，双方都无法落子时游戏结束 */
    @Override
    public boolean shouldGameEnd() {
        if(!canPass()) return false; // 当前玩家仍有有效位则不结束
        switchPlayer();
        boolean canOpponentPass = canPass();
        switchPlayer();
        return canOpponentPass;
    }

    /* 游戏结束时，计算并返回游戏结果 */
    @Override
    public GameResult handleGameEnd() {
        int blackScore = countPieces(Piece.BLACK);
        int whiteScore = countPieces(Piece.WHITE);

        // 判断胜者
        Player winner = blackScore > whiteScore
                ? findPlayerByPiece(Piece.BLACK)
                : blackScore < whiteScore
                ? findPlayerByPiece(Piece.WHITE)
                : null;  // 平局时没有胜者
        return new GameResult(blackScore,whiteScore,winner);
    }

}
