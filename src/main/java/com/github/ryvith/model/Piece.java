package com.github.ryvith.model;

import lombok.Getter;

@Getter
public enum Piece {
    WHITE('○'),
    BLACK('●'),
    EMPTY('·'),
    VALID('+');

    private final char symbol;
    Piece(char symbol){
        this.symbol = symbol;
    }

}
