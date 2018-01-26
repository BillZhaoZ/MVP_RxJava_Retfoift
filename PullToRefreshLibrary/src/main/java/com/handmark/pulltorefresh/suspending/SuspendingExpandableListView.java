package com.handmark.pulltorefresh.suspending;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ExpandableListView;

import com.handmark.pulltorefresh.ptr.PullToRefreshSuspendingExpandableListView;


/**
 * 悬浮分组的  ExpandableListView
 * 手动绘制悬浮头  并通过代码判断进行显示和隐藏（滚动到不同位置，悬停标题的显示是不同的，因此需要根据滚动状态定义一个状态机的切换。）
 */
public class SuspendingExpandableListView extends ExpandableListView implements OnScrollListener {

    private View mDockingHeader; // 悬浮头
    private int mDockingHeaderWidth;
    private int mDockingHeaderHeight;
    private boolean mDockingHeaderVisible; // 悬浮头 是否可见
    private int mDockingHeaderState = IDockingController.DOCKING_HEADER_HIDDEN; // 悬浮头状态  默认不可见
    private IDockingHeaderUpdateListener mListener;

    public SuspendingExpandableListView(Context context) {
        this(context, null);
    }

    public SuspendingExpandableListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SuspendingExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 添加滚动监听
        setOnScrollListener(this);
    }

    /**
     * 设置悬浮头  更新监听
     *
     * @param header
     * @param listener
     */
    public void setDockingHeader(PullToRefreshSuspendingExpandableListView mListView, View header, IDockingHeaderUpdateListener listener) {
        mDockingHeader = header;
        mListener = listener;
        listView = mListView;
    }

    PullToRefreshSuspendingExpandableListView listView;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 测量标题视图的宽度和高度
        if (mDockingHeader != null) {
            measureChild(mDockingHeader, widthMeasureSpec, heightMeasureSpec);

            mDockingHeaderWidth = mDockingHeader.getMeasuredWidth();
            mDockingHeaderHeight = mDockingHeader.getMeasuredHeight();
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        // 摆放
        if (mDockingHeader != null) {
            mDockingHeader.layout(0, 0, mDockingHeaderWidth, mDockingHeaderHeight);
        }

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        // 悬停标题是画上去的，而不是加到view hierarchy里去，具体根据滚动的情况确定如何画。
        if (mDockingHeaderVisible) {
            drawChild(canvas, mDockingHeader, getDrawingTime());
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        /**
         * 滚动到不同位置，悬停标题的显示是不同的，因此需要根据滚动状态定义一个状态机的切换。
         * 让DockingExpandableListView实现OnScrollListener接口，并重写onScroll()方法：
         */
        long packedPosition = getExpandableListPosition(firstVisibleItem);
        int groupPosition = getPackedPositionGroup(packedPosition);
        int childPosition = getPackedPositionChild(packedPosition);

        // update layout_refresh_header_view view based on first visible item
        // IMPORTANT: refer to getPackedPositionChild():
        // If this group does not contain a child, returns -1. Need to handle this case in controller.
        updateDockingHeader(groupPosition, childPosition);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    /**
     * 更新悬浮头
     *
     * @param groupPosition
     * @param childPosition
     */
    private void updateDockingHeader(int groupPosition, int childPosition) {
        if (getExpandableListAdapter() == null) {
            return;
        }

        if (getExpandableListAdapter() instanceof IDockingController) {
            IDockingController dockingController = (IDockingController) getExpandableListAdapter();
            mDockingHeaderState = dockingController.getDockingState(groupPosition, childPosition);

            // 根据悬浮头状态  更新UI
            switch (mDockingHeaderState) {

                case IDockingController.DOCKING_HEADER_HIDDEN: // 如果当前group没有子项，并且也不是展开状态，就返回DOCKING_HEADER_HIDDEN状态，不绘制悬停标题；
                    mDockingHeaderVisible = false;
                    break;

                case IDockingController.DOCKING_HEADER_DOCKED: // 其他情况，在当前group内部滚动，返回DOCKING_HEADER_DOCKED状态。
                    if (mListener != null) {
                        mListener.onUpdate(mDockingHeader, groupPosition, isGroupExpanded(groupPosition));
                    }

                    // Header view might be "GONE" status at the beginning, so we might not be able
                    // to get its width and height during initial measure procedure.
                    // Do manual measure and layout operations here.
                    mDockingHeader.measure(
                            MeasureSpec.makeMeasureSpec(mDockingHeaderWidth, MeasureSpec.AT_MOST),
                            MeasureSpec.makeMeasureSpec(mDockingHeaderHeight, MeasureSpec.AT_MOST));

                    mDockingHeader.layout(0, 0, mDockingHeaderWidth, mDockingHeaderHeight);
                    mDockingHeaderVisible = true;
                    break;

                case IDockingController.DOCKING_HEADER_DOCKING: // 如果到达了当前group的最后一个子项，进入DOCKING_HEADER_DOCKING状态；
                    if (mListener != null) {
                        mListener.onUpdate(mDockingHeader, groupPosition, isGroupExpanded(groupPosition));
                    }

                    View firstVisibleView = getChildAt(0);
                    int yOffset;

                    if (firstVisibleView.getBottom() < mDockingHeaderHeight) {
                        yOffset = firstVisibleView.getBottom() - mDockingHeaderHeight;
                    } else {
                        yOffset = 0;
                    }

                    // The yOffset is always non-positive. When a new layout_refresh_header_view view is "docking",
                    // previous layout_refresh_header_view view need to be "scrolled over". Thus we need to draw the
                    // old layout_refresh_header_view view based on last child's scroll amount.
                    mDockingHeader.measure(
                            MeasureSpec.makeMeasureSpec(mDockingHeaderWidth, MeasureSpec.AT_MOST),
                            MeasureSpec.makeMeasureSpec(mDockingHeaderHeight, MeasureSpec.AT_MOST));

                    // 根据滑动  不同的绘制  悬浮头的位置   因为yOffset是负值  并且是变小的  所以会有一种被退出去的感觉
                    mDockingHeader.layout(0, yOffset, mDockingHeaderWidth, mDockingHeaderHeight + yOffset);
                    mDockingHeaderVisible = true;
                    break;
            }
        }
    }

    /**
     * 这个标题视图是画上去，而不是添加到view hierarchy里的，因此它是无法响应touch事件的！那就需要我们自己根据点击区域进行判断了，
     * 需要重写onInterceptTouchEvent()和onTouchEvent()方法
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN && mDockingHeaderVisible) {

            Rect rect = new Rect();
            mDockingHeader.getDrawingRect(rect);

            if (rect.contains((int) ev.getX(), (int) ev.getY())
                    && mDockingHeaderState == IDockingController.DOCKING_HEADER_DOCKED) {

                // 如果当前是DOCKING_HEADER_DOCKED状态，并且点击区域命中了标题视图的drawing rect，
                // 那么就需要拦截touch事件
                return true;
            }
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (mDockingHeaderVisible) {
            Rect rect = new Rect();
            mDockingHeader.getDrawingRect(rect);

            switch (ev.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    if (rect.contains((int) ev.getX(), (int) ev.getY())) {
                        // forbid event handling by list view's item
                        return true;
                    }
                    break;

                case MotionEvent.ACTION_UP:

                    long flatPostion = getExpandableListPosition(getFirstVisiblePosition());
                    int groupPos = ExpandableListView.getPackedPositionGroup(flatPostion);

                    if (rect.contains((int) ev.getX(), (int) ev.getY()) &&
                            mDockingHeaderState == IDockingController.DOCKING_HEADER_DOCKED) {

                        // 在手指抬起时根据group当前的状态执行收起或者展开的动作。
                        if (isGroupExpanded(groupPos)) {
                            collapseGroup(groupPos);

                            // 分组展开，内部滑动一定距离  后关闭分组   条目位置需要优化
                            // listView.getRefreshableView().smoothScrollToPosition(groupPos, getScroolYDistance());

                            Log.e("collapseGroup", groupPos + "");
                        } else {
                            expandGroup(groupPos);
                        }

                        return true;
                    }
                    break;
            }
        }

        return super.onTouchEvent(ev);
    }
}
