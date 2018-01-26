package com.handmark.pulltorefresh.suspending;

/**
 * 悬浮头  模式监听
 * <p>
 * 如果当前group没有子项，并且也不是展开状态，就返回DOCKING_HEADER_HIDDEN状态，不绘制悬停标题；
 * 如果到达了当前group的最后一个子项，进入DOCKING_HEADER_DOCKING状态；
 * 其他情况，在当前group内部滚动，返回DOCKING_HEADER_DOCKED状态。
 */
public interface IDockingController {

    int DOCKING_HEADER_HIDDEN = 1;
    int DOCKING_HEADER_DOCKING = 2;
    int DOCKING_HEADER_DOCKED = 3;

    int getDockingState(int firstVisibleGroup, int firstVisibleChild);
}
