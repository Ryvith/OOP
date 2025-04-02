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
    public static ParsedInput parseInput(String input) {

        if (input.matches(SET_PIECE)) {
            int row = input.charAt(0) - '1';
            int col = input.charAt(1) - 'a';
            return new ParsedInput(InputType.MOVE, new int[]{row, col});
        }

        if (input.matches(GAME_ID)) {
            return new ParsedInput(InputType.SWITCH_GAME, Integer.parseInt(input));
        }

        if (input.equals(PEACE) || input.equals(REVERSI)) {
            return new ParsedInput(InputType.NEW_GAME, input);
        }


        if (input.equals(QUIT)) {
            return new ParsedInput(InputType.QUIT, null);
        }

        return new ParsedInput(InputType.INVALID, null);
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

    public static class ParsedInput {
        public final InputType type;
        public final Object data;

        public ParsedInput(InputType type, Object data) {
            this.type = type;
            this.data = data;
        }
    }
}
