package com.github.ryvith.game;

import com.github.ryvith.ui.handleOption.*;

import java.util.List;

import static com.github.ryvith.ui.handleOption.HandleOption.parseOption;

public class GameController {
    private final List<GameMode> games;
    private int currentGameIndex;
    private boolean isRunning;

    /**
     * 构造函数注入游戏列表
     * @param games 管理的游戏集合（至少包含一个游戏）
     */
    public GameController(List<GameMode> games) {
        if (games == null || games.isEmpty()) {
            throw new IllegalArgumentException("游戏列表不能为空");
        }
        this.games = games;
        this.currentGameIndex = 0;
    }

    /**
     * 启动游戏主循环
     */
    public void start(){
        isRunning = true;
        while (isRunning) {
            GameMode currentGame = games.get(currentGameIndex);

            // 渲染游戏
            GamePrinter(currentGame);

            // 获取和处理用户操作
            UserInput.ParsedInput input = UserInput.parseInput((UserInput.readInput()));
            Option option = parseOption(input,currentGame);
            option.handleOption(this);

            // 检查游戏结束
            if(currentGame.isGameOver()){
                currentGame.handleGameEnd();
            }
        }
    }

    /* 添加新游戏 */
    public void addNewGame(String type, GameConfig config) {
        GameMode game = switch(type) {
            case "peace" -> new PeaceGame(config);
            case "reversi" -> new ReversiGame(config);
            default -> throw new IllegalArgumentException("未知类型");
        };
        games.add(game);
    }
}
