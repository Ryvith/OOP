package com.github.ryvith.game;

import com.github.ryvith.model.Player;

public record GameResult(int blackScore, int whiteScore, Player winner) {
}
