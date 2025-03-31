package com.github.ryvith.model;

/*
* board初始化策略接口，可能有多种实现（如 Standard, Random, Custom）
*
* */
public interface BoardInitializer{
    void initialize(Board board);
}
