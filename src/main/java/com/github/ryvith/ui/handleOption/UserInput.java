package com.github.ryvith.ui.handleOption;

import java.util.Scanner;

public class UserInput {

    private static final String SET_PIECE = "^[1-8][a-hA-H]$";
    private static final String GAME_ID = "^\\d+$";
    private static final String PEACE = "peace";
    private static final String REVERSI = "reversi";
    private static final String QUIT = "quit";

    static Scanner scanner = new Scanner(System.in);

    /* 读取输入 */
    public static String readInput() {
        String input = scanner.nextLine();
        return input.trim().toLowerCase();
    }

    /**
     * 读取并解析用户输入
     *
     * @param input 用户键盘标准输入
     * @return 用户的操作类型和输入内容
     */
    public static ParsedOption parseInput(String input) {

        if (input.matches(SET_PIECE)) {
            int row = input.charAt(0) - '1';
            int col = input.charAt(1) - 'a';
            return new ParsedOption(InputType.MOVE, new int[]{row, col});
        }

        if (input.matches(GAME_ID)) {
            return new ParsedOption(InputType.SWITCH_GAME, Integer.parseInt(input));
        }

        if (input.equals(PEACE) || input.equals(REVERSI)) {
            return new ParsedOption(InputType.NEW_GAME, input);
        }


        if (input.equals(QUIT)) {
            return new ParsedOption(InputType.QUIT, null);
        }

        return new ParsedOption(InputType.INVALID, null);
    }

    // 用户输入类型
    public enum InputType {
        MOVE,
        SWITCH_GAME,
        NEW_GAME,
        PASS,
        QUIT,
        INVALID
    }

    /**
     * ParsedOption记录类
     * @param type 用户的操作类型
     * @param data 操作相关内容，如输入的字符串
     */
    public record ParsedOption(InputType type, Object data) {
    }
}
