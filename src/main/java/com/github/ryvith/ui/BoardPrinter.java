package com.github.ryvith.ui;

import com.github.ryvith.model.Board;
import com.github.ryvith.model.Piece;
import com.github.ryvith.util.AnsiColors;

public class BoardPrinter {
    public static String renderBoardInfo(Board board){
        StringBuilder sb = new StringBuilder();
        int boardSize = board.getSize();
        // 拼接列标签
        sb.append(AnsiColors.BLUE).append(" ");
        for(int i = 0; i< boardSize; i++){
            sb.append(" ").append((char) ('A' + i));
        }
        sb.append(AnsiColors.RESET).append("\n");

        // 拼接行标签和棋盘
        for(int i = 0; i< boardSize; i++){
            sb.append(AnsiColors.BLUE).append(i+1).append(AnsiColors.RESET);
            for(int j = 0; j< boardSize; j++){
                Piece piece = board.getGrid()[i][j];
                sb.append(" ");
                if(piece == Piece.VALID){
                    sb.append(AnsiColors.YELLOW);
                }else if(piece == Piece.EMPTY){
                    sb.append(AnsiColors.GRAY);
                }
                sb.append(piece.getSymbol()).append(AnsiColors.RESET);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
