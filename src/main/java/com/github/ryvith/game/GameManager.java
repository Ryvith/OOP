package com.github.ryvith.game;

import com.github.ryvith.ui.handleOption.*;

import java.awt.*;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
public class GameManager {
    private final List<Game> games;
    private int currentGameIndex;
    @Setter
    private Game currentGame;

    /**
     * gameManager构造函数，注入游戏列表
     * @param games 管理的游戏集合（至少包含一个游戏）
     */
    public GameManager(List<Game> games) {
        if (games == null || games.isEmpty()) {
            throw new IllegalArgumentException("游戏列表不能为空");
        }
        this.games = games;
        this.currentGame = games.getFirst();
        this.currentGameIndex = 0;
    }


    /**
     * 根据不同用户操作类型，进行对应的操作处理
     * @param currentGame
     */
    private void handleUserInput(Game currentGame) {
        // 解析用户操作类型
        String input = UserInput.readInput();
        ParsedOption option = UserInput.parseInput(input);

        // 进行对应的操作处理
        switch (option.type()){
            case MOVE:
                currentGame.setPiece((Point) option.data());
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
            case PASS:
                break;
            case INVALID:
            default:
                Game.handleInvalidInput();
        }
    }

    /* 添加新游戏 */
    private void addNewGame(String type, GameConfig config) {
        Game game = switch(type) {
            case "peace" -> new Peace(config);
            case "reversi" -> new Reversi(config);
            default -> throw new IllegalArgumentException("未知类型");
        };
        games.add(game);
    }

    /* 切换游戏 */
    public void handleSwitchGame(int gameIndex) {
        if(gameIndex >= 1 && gameIndex <= games.size()){
            currentGameIndex = gameIndex;
        }else{
            System.out.println("无效的游戏编号，当前游戏编号范围1-"+games.size());
        }
    }

}
