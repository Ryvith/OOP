package com.github.ryvith.model;

import lombok.Getter;

@Getter
public class Player {
    private final String name;
    private final int num;
    private final int score;
    private final Piece piece;

    public Player(String name, int score,int num, Piece piece) {
        this.name = name;
        this.score = score;
        this.num = num;
        this.piece = piece;
    }
}
