package com.github.ryvith.game;

import com.github.ryvith.ui.handleOption.*;

import java.awt.*;
import java.util.List;


public class GameController {
    private final List<Game> games;
    private int currentGameIndex;
    private boolean isRunning;

    /**
     * 构造函数注入游戏列表
     * @param games 管理的游戏集合（至少包含一个游戏）
     */
    public GameController(List<Game> games) {
        if (games == null || games.isEmpty()) {
            throw new IllegalArgumentException("游戏列表不能为空");
        }
        this.games = games;
        this.currentGameIndex = 1;
    }

    /**
     * 启动游戏主循环
     */
    public void start(){
        isRunning = true;
        while (isRunning) {
            Game currentGame = games.get(currentGameIndex);

            // 渲染游戏
            // GamePrinter(currentGame);

            // 获取和处理用户操作
            UserInput.ParsedOption option = UserInput.parseInput((UserInput.readInput()));


            // 检查游戏结束
            if(currentGame.isGameOver()){
                currentGame.handleGameEnd();
                continue;
            }

            handleUserInput(currentGame);

        }
    }

    /**
     * 根据不同用户操作类型，进行对应的操作处理
     * @param currentGame
     */
    private void handleUserInput(Game currentGame) {
        // 解析用户操作类型
        String input = UserInput.readInput();
        UserInput.ParsedOption option = UserInput.parseInput(input);

        // 进行对应的操作处理
        switch (option.type()){
            case MOVE:
                currentGame.setPiece(currentGame.board, (Point) option.data());
                break;

            case SWITCH_GAME:
                handleSwitchGame((int)option.data());
                break;

            case NEW_GAME:
                addNewGame((String)option.data(), currentGame.config);
                break;

            case QUIT:
                Game.quitGame();
                break;

            case INVALID:
                Game.handleInvalidInput();
        }
    }

    /* 添加新游戏 */
    private void addNewGame(String type, GameConfig config) {
        Game game = switch(type) {
            case "peace" -> new PeaceGame(config);
            case "reversi" -> new ReversiGame(config);
            default -> throw new IllegalArgumentException("未知类型");
        };
        games.add(game);
    }

    /* 切换游戏 */
    private void handleSwitchGame(int gameIndex) {
        if(gameIndex >= 1 && gameIndex <= games.size()){
            currentGameIndex = gameIndex;
        }else{
            System.out.println("无效的游戏编号，当前游戏编号范围1-"+games.size());
        }
    }

}
