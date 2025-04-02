package com.github.ryvith.ui.handleOption;

import com.github.ryvith.game.GameMode;

import java.util.List;


/**
 * 退出游戏命令实现，单例模式实现
 */
public enum QuitOption implements Option {
    INSTANCE;

    @Override
    public void handleOption(GameMode gameMode, List<GameMode> gameModeList) {
        // 直接退出，终止程序
        System.exit(0);
    }
}



