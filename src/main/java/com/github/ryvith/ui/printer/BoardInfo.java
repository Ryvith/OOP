package com.github.ryvith.ui.printer;

import com.github.ryvith.model.Board;
import com.github.ryvith.model.Piece;
import com.github.ryvith.util.Ansi;

public class BoardInfo {
    public static String renderBoardInfo(Board board){
        StringBuilder sb = new StringBuilder();
        int boardSize = board.getSize();
        // 拼接列标签
        sb.append(Ansi.BLUE).append(" ");
        for(int i = 0; i< boardSize; i++){
            sb.append(" ").append((char) ('A' + i));
        }
        sb.append(Ansi.RESET).append("\n");

        // 拼接行标签和棋盘
        for(int i = 0; i< boardSize; i++){
            sb.append(Ansi.BLUE).append(i+1).append(Ansi.RESET);
            for(int j = 0; j< boardSize; j++){
                Piece piece = board.getGrid()[i][j];
                sb.append(" ");
                if(piece == Piece.VALID){
                    sb.append(Ansi.YELLOW);
                }else if(piece == Piece.EMPTY){
                    sb.append(Ansi.GRAY);
                }
                sb.append(piece.getSymbol()).append(Ansi.RESET);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
