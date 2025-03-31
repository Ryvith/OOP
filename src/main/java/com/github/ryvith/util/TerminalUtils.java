package com.github.ryvith.util;

public class TerminalUtils {
    /* ANSI控制序列 */
    public static final String CLEAR_SCREEN = "\033[H\033[2J";
    public static final String CLEAR_LINE = "\033[2K";
    public static final String SHOW_CURSOR = "\033[?25h";
    public static final String HIDE_CURSOR = "\033[?25l";
    private static boolean isAnsiSupported = isAnsiSupported();

    /* 检查终端是否支持ANSI */
    public static boolean isAnsiSupported() {
        return System.getProperty("os.name").toLowerCase().contains("ansi");
    }

    // ======== 光标控制 ========

    /**
     * 移动光标到指定位置（1-based）
     * @param x 行号（从1开始)
     * @param y 列号（从1开始）
     */
    public static void moveCursorTo(int x, int y) {
        if (isAnsiSupported()) {
            System.out.printf("\033[%d;%dH",x, y);
        }
    }

    /* 打印带颜色的文本 */
    public static String colored(String str, AnsiColor color) {
        return isAnsiSupported() ? color.code + str + AnsiColor.RESET.code : str;
    }

    public enum AnsiColor {
        YELLOW("\033[33m"),
        GREY("\033[90m"),
        RESET("\033[0m"),;
        public final String code;

        AnsiColor(String code) {
            this.code = code;
        }
    }
}