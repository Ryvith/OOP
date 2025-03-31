package com.github.ryvith.ui;

import com.github.ryvith.model.Board;

public class BoardPrinter {
    public static void printInitialBoard(Board board){
        // 打印列标签
        System.out.print(" ");
        for(int i = 0; i< board.getSize(); i++){
            System.out.print(" "+(char)('A'+i));
        }
        System.out.println();

        // 打印行标签和棋盘
        for(int i = 0; i< board.getSize(); i++){
            System.out.print(i+1);
            for(int j = 0; j< board.getSize(); j++){
                System.out.print(" "+ board.getGrid()[i][j].getSymbol());
            }
            System.out.println();
        }
    }
}
