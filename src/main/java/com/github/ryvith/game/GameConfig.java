package com.github.ryvith.game;

import lombok.Getter;

/**
 * 游戏配置类（不可变，保证线程安全）
 */

@Getter
public final class GameConfig {
    private final int boardSize;
    private final String player1Name;
    private final String player2Name;
    private final GameMode game;

    // 私有构造器，强制通过Builder创建
    private GameConfig(final int boardSize, final String player1Name, final String player2Name, final GameMode game) {
        this.boardSize = boardSize;
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.game = game;
    }


    /**
     * Builder静态内部类（负责灵活构造配置）
     */
    public static class GameBuilder {
        // 默认值
        private int size = 8;
        private String player1 = "player1";
        private String player2 = "player2";
        private GameMode game;

        public GameBuilder withSize(int size) {
            this.size = size;
            return this;
        }

        public GameBuilder withPlayers(String player1, String player2) {
            this.player1 = player1;
            this.player2 = player2;
            return this;
        }

        public GameBuilder withGameMode(GameMode game){
            this.game = game;
            return this;
        }


        // 构建不可变的GameConfig对象，可以访问Config私有构造方法
        public GameConfig build() {
            return new GameConfig(size, player1, player2, game);
        }
    }
}
