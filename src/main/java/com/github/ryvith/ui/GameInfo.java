package com.github.ryvith.ui;

import com.github.ryvith.model.Player;
import com.github.ryvith.game.*;

public class GameInfo {
    private Player currentPlayer;
    private Player player1;
    private Player player2;
    private int currentGameNum;


    public GameInfo(int currentGameNum) {
        this.currentGameNum = currentGameNum;
    }

    private String getPlayerInfo(Player player) {
        return String.format("[Player%d]%-10s %c",
                player.getNum(),
                player.getName(),
                player.equals(currentPlayer)? player.getPiece().getSymbol(): ' '
        );
    }


    public void printGameInfo(GameMode currentGame){
        String player1info = getPlayerInfo(player1);
        String player2info = getPlayerInfo(player2);

        // 如果是reversi-game，追加得分
        if(currentGame.getGameMode().equals("reversi")){
            player1info += String.format(" : %-4d",player1.getScore());
            player2info += String.format(" : %-4d",player2.getScore());
        }

        GotoPoint
    }

}
