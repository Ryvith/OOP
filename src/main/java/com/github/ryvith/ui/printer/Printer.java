package com.github.ryvith.ui.printer;
import com.github.ryvith.game.GameMode;
import com.github.ryvith.util.Ansi;

import java.util.Arrays;

public class Printer {
    static public void print(GameMode currentGameMode) {
        Ansi.cleanScreen();
        String left = BoardInfo.renderBoardInfo(currentGameMode.getBoard());
        String middle = GameInfo.renderGameInfo(currentGameMode);
        String right = GameInfo.gameListInfo;

        String fullOutput = combineThreeColumns(left, middle, right);
        System.out.println(fullOutput);

    }

    // 缓冲区版本
    static public void bufferPrinter(GameMode currentGameMode) {
        Ansi.cleanScreen();
        String left = BoardInfo.renderBoardInfo(currentGameMode.getBoard());
        String middle = GameInfo.renderGameInfo(currentGameMode);
        String right = GameInfo.gameListInfo;
        ScreenBuffer buffer = new Printer.ScreenBuffer();
        buffer.update(combineThreeColumns(left, middle, right));
        buffer.flush();
    }

    private static String combineThreeColumns(String left, String middle, String right) {
        String[] leftLines = left.split("\n");
        String[] middleLines = middle.split("\n");
        String[] rightLines = right.split("\n");

        // 动态计算各栏所需宽度
        int leftWidth = calculateMaxWidth(leftLines);
        int middleWidth = calculateMaxWidth(middleLines);

        int maxLines = Math.max(Math.max(leftLines.length, middleLines.length), rightLines.length);
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < maxLines; i++) {
            // 左侧内容
            String leftLine = (i < leftLines.length) ? leftLines[i]+"  " : "  ";
            builder.append(padRight(leftLine, leftWidth));

            // 中间内容
            String middleLine = (i < middleLines.length) ? middleLines[i] : "";
            builder.append(padRight(middleLine, middleWidth));

            // 右侧内容
            if (i < rightLines.length) {
                builder.append(rightLines[i]);
            }

            builder.append("\n");
        }

        return builder.toString();
    }

    private static int calculateMaxWidth(String[] lines) {
        return Arrays.stream(lines)
                .mapToInt(line -> line.replaceAll("\u001B\\[[;\\d]*m", "").length())
                .max()
                .orElse(0);
    }

    private static String padRight(String s, int targetWidth) {
        int actualWidth = s.replaceAll("\u001B\\[[;\\d]*m", "").length();
        if (actualWidth >= targetWidth) {
            return s;
        }
        return s + " ".repeat(targetWidth - actualWidth);
    }

    private static class ScreenBuffer {
        private final StringBuilder buffer = new StringBuilder();
        private int lastLineCount = 0;

        public void update(String content) {
            buffer.setLength(0);
            buffer.append(content);

            // 计算当前行数
            int currentLineCount = content.split("\n").length;

            // 如果新内容行数少于之前，用空行填充
            if (currentLineCount < lastLineCount) {
                buffer.append("\n".repeat(lastLineCount - currentLineCount));
            }
            lastLineCount = currentLineCount;
        }

        public void flush() {
            System.out.print(buffer.toString());
        }
    }
}

