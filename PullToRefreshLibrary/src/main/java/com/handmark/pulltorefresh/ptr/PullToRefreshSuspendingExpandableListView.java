package com.handmark.pulltorefresh.ptr;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;

import com.handmark.pulltorefresh.PullToRefreshBase;
import com.handmark.pulltorefresh.suspending.SuspendingExpandableListView;

/**
 * 可以下拉刷新的  悬浮分组的  ExpandableListView
 * <p>
 * 特殊案例  基于ptr库 和 DockingExpandableListView
 * <p>
 * Created by Bill on 17/6/26.
 */
public class PullToRefreshSuspendingExpandableListView extends PullToRefreshBase<SuspendingExpandableListView> {

    private static final boolean DEBUG = false;
    private static final String LOG_TAG = "PullToRefresh";

    public PullToRefreshSuspendingExpandableListView(Context context) {
        super(context);
    }

    public PullToRefreshSuspendingExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected SuspendingExpandableListView createRefreshableView(Context context, AttributeSet attrs) {

        final SuspendingExpandableListView lv = new SuspendingExpandableListView(context);

        // Set it to this so it can be used in ListActivity/ListFragment
        lv.setId(android.R.id.list);

        return lv;
    }

    /**
     * 上拉加载
     *
     * @return
     */
    @Override
    protected boolean isReadyForPullEnd() {
        return isLastItemVisible();
    }

    /**
     * 从头部下拉刷新
     *
     * @return
     */
    @Override
    protected boolean isReadyForPullStart() {
        return isFirstItemVisible();
    }

    /**
     * 第一个条目 是否可见
     *
     * @return
     */
    private boolean isFirstItemVisible() {
        final Adapter adapter = mRefreshableView.getAdapter();

        if (null == adapter || adapter.isEmpty()) {
            if (DEBUG) {
                Log.d(LOG_TAG, "isFirstItemVisible. Empty View.");
            }
            return true;

        } else {

            /**
             * This check should really just be:
             * mRefreshableView.getFirstVisiblePosition() == 0, but PtRListView
             * internally use a HeaderView which messes the positions up. For
             * now we'll just add one to account for it and rely on the inner
             * condition which checks getTop().
             */
            if (mRefreshableView.getFirstVisiblePosition() <= 1) {
                final View firstVisibleChild = mRefreshableView.getChildAt(0);

                if (firstVisibleChild != null) {
                    return firstVisibleChild.getTop() >= mRefreshableView.getTop();
                }
            }
        }

        return false;
    }

    /**
     * 最后一个条目  是否可见
     *
     * @return
     */
    private boolean isLastItemVisible() {
        final Adapter adapter = mRefreshableView.getAdapter();

        if (null == adapter || adapter.isEmpty()) {
            if (DEBUG) {
                Log.d(LOG_TAG, "isLastItemVisible. Empty View.");
            }
            return true;
        } else {

            final int lastItemPosition = mRefreshableView.getCount() - 1;
            final int lastVisiblePosition = mRefreshableView.getLastVisiblePosition();

            if (DEBUG) {
                Log.d(LOG_TAG, "isLastItemVisible. Last Item Position: " + lastItemPosition + " Last Visible Pos: "
                        + lastVisiblePosition);
            }

            /**
             * This check should really just be: lastVisiblePosition ==
             * lastItemPosition, but PtRListView internally uses a FooterView
             * which messes the positions up. For me we'll just subtract one to
             * account for it and rely on the inner condition which checks
             * getBottom().
             */
            if (lastVisiblePosition >= lastItemPosition - 1) {

                final int childIndex = lastVisiblePosition - mRefreshableView.getFirstVisiblePosition();
                final View lastVisibleChild = mRefreshableView.getChildAt(childIndex);

                if (lastVisibleChild != null) {
                    return lastVisibleChild.getBottom() <= mRefreshableView.getBottom();
                }
            }
        }

        return false;
    }
}
