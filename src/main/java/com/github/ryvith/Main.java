package com.github.ryvith;

import com.github.ryvith.game.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 创建游戏配置
        GameConfig config = new GameConfig.GameBuilder()
                .withSize(8)
                .withPlayers("Tom", "Jerry")
                .build();

        Game reversi = new ReversiGame(config);
        Game peace = new PeaceGame(config);

        new GameController(List.of(reversi, peace)).start();
    }
}
