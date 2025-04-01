package com.github.ryvith.ui;

import com.github.ryvith.model.Player;
import com.github.ryvith.game.*;
import com.github.ryvith.util.AnsiColors;

public class GameInfo {

    static private String getPlayerInfo(Player player, GameMode gameMode) {
        return String.format("[Player%d]%-10s %c\n",
                player.getNum(),
                player.getName(),
                player.equals(gameMode.getCurrentPlayer())? player.getPiece().getSymbol(): ' '
        );
    }


    /* 中间一列游戏信息的toString */
    static public String renderGameInfo(GameMode currentGame){
        String gameInfo = "Game " + currentGame.getGameNumber()+"\n";
        String player1info = getPlayerInfo(currentGame.getPlayer1(),currentGame);
        String player2info = getPlayerInfo(currentGame.getPlayer2(),currentGame);

        // 如果是reversi-game，追加得分
        if(currentGame.getGameMode().equals("reversi")){
            player1info += (AnsiColors.YELLOW + String.format(" : %-4d",currentGame.getPlayer1().getScore()) + AnsiColors.RESET);
            player2info += (AnsiColors.YELLOW + String.format(" : %-4d",currentGame.getPlayer2().getScore()) + AnsiColors.RESET);
        }

        return gameInfo + player1info + player2info;
    }

    /* 右侧游戏列表 */
    static String gameListInfo = """
            Game List
            1. peace
            2. reversi
            """;


}
