package com.github.ryvith.ui.handleOption;

import com.github.ryvith.game.GameMode;

public class HandleOption {

    /**
     * 分类处理操作
     * @param currentGame 当前游戏
     * @param input 用户输入
     */
    static public Option parseOption(UserInput.ParsedInput input, GameMode currentGame) {
        switch (input.type) {
            case MOVE:
                int[] coords = (int[]) parsedInput.data;
                return new MoveOption(coords[0], coords[1], gameManager);

            case SWITCH_GAME:
                return new SwitchGameOption((Integer) parsedInput.data, gameManager);

            case NEW_GAME:
                return new NewGameOption((String) input.data, currentGame.getConfig());

            case QUIT:
                QuitOption.INSTANCE.handleOption(gameMode, gameModeList);

            case INVALID:
            default:
                InvalidOption.INSTANCE.handleOption(gameMode, gameModeList);
        }
    }
}