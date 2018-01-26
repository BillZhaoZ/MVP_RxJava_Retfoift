package com.handmark.pulltorefresh.suspending;

import android.view.View;

/**
 * 悬浮头  更新监听
 */
public interface IDockingHeaderUpdateListener {

    void onUpdate(View headerView, int groupPosition, boolean expanded);

}
