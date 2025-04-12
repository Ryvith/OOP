package com.github.ryvith.ui.handleOption;

/**
 * ParsedOption记录类
 * @param type 用户的操作类型
 * @param data 操作相关内容，如输入的字符串
 */
public record ParsedOption(UserInput.InputType type, Object data) {
}
