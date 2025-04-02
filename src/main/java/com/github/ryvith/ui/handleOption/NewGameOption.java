package com.github.ryvith.ui.handleOption;

import com.github.ryvith.game.*;

public class NewGameOption implements Option {
    private final String gameType;
    private final GameConfig currentConfig;

    public NewGameOption(String gameType, GameConfig currentConfig) {
        this.gameType = gameType;
        this.currentConfig = currentConfig;
    }

    @Override
    public void handleOption(GameController controller) {
        controller.addNewGame(gameType, currentConfig);
        System.out.println("已创建" + gameType + "新游戏");
    }
}