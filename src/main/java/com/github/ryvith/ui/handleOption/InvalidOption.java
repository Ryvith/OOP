package com.github.ryvith.ui.handleOption;

import com.github.ryvith.game.GameController;
import com.github.ryvith.game.GameMode;

import java.util.List;

/**
 * 处理无效操作
 */
public enum InvalidOption implements Option{
    INSTANCE;

    @Override
    public void handleOption(GameController controller) {
        System.out.println("无效操作，请重新输入。");
    }


}
