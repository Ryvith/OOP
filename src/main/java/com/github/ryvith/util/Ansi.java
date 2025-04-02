package com.github.ryvith.util;

public class Ansi {
    public static final String RESET = "\u001B[0m";
    public static final String YELLOW = "\u001B[33m";  // 普通黄色前景色
    public static final String GRAY = "\u001B[37m";
    public static final String BLUE = "\u001B[34m";
    public static final String CLEAR = "\033[H\033[2J";

    // 移动光标到指定位置（行,列从1开始）
    public static void moveCursor(int row, int col) {
        System.out.printf("\033[%d;%dH", row, col);
    }

    /* 清屏 */
    public static void cleanScreen() {
        System.out.printf(CLEAR);
        System.out.flush();
    }
}
