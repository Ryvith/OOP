package com.github.ryvith.ui.printer;

import com.github.ryvith.model.Player;
import com.github.ryvith.gameMode.*;
import com.github.ryvith.util.Ansi;

public class GameInfo {

    static private String getPlayerInfo(Player player, GameMode gameMode) {
        return String.format("[Player%d]%-8s %c",
                player.getNum(),
                player.getName(),
                player.equals(gameMode.getCurrentPlayer())? player.getPiece().getSymbol(): ' '
        );
    }


    /* 中间一列游戏信息的toString */
    static public String renderGameInfo(GameMode currentGameMode){
        String gameInfo = "GameMode " + currentGameMode.getGameNumber()+"\n";
        String player1info = getPlayerInfo(currentGameMode.getPlayer1(), currentGameMode);
        String player2info = getPlayerInfo(currentGameMode.getPlayer2(), currentGameMode);

        // 如果是reversi-gameMode，追加得分
        if(currentGameMode.getGameMode().equals("reversi")){
            player1info += (Ansi.YELLOW + String.format(" : %-4d", currentGameMode.getPlayer1().getScore()) + Ansi.RESET);
            player2info += (Ansi.YELLOW + String.format(" : %-4d", currentGameMode.getPlayer2().getScore()) + Ansi.RESET);
        }

        return gameInfo + player1info + "\n" + player2info + "\n";
    }

    /* 右侧游戏列表 */
    static String gameListInfo = """
            GameMode List
            1. peace
            2. reversi
            """;


}
