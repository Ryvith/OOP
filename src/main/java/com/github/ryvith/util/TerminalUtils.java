package com.github.ryvith.util;

public class TerminalUtils {
    /* 移动光标到指定位置 */
    public static void moveCursor(int row, int col){
        System.out.printf("\033[%d;%dH",row,col);
    }


}
