/**********************************************************
 *     _              _  _  _  ____          __   _       *
 *    / \   _ __   __| || |(_)/ ___|  ___ __/ /__| |_     *
 *   / _ \ | '_  \/ _  || || |\___ \ / _ \_   _|_   _|    *
 *  / ___ \| | | | (_) || || | ___) | (_) || |   | |_     *
 * /_/   \_|_| |_|\____)|_||_||____/ \___/ /_/   \___|    *
 *                                                        *
 **********************************************************
 * Copyright 2015, AndliSoft.com.                         *
 * All rights, including trade secret rights, reserved.   *
 **********************************************************/

package com.handmark.pulltorefresh.swipe;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;


/**
 * 
 * @author baoyz
 * @date 2014-8-24
 * 
 */
public class SwipeMenuExpandableAdapter implements ExpandableListAdapter, SwipeMenuExpandableView.OnSwipeItemClickListener {

	private ExpandableListAdapter mAdapter;
	private Context mContext;
	private SwipeMenuExpandableListView.OnMenuItemClickListener onMenuItemClickListener;

	public SwipeMenuExpandableAdapter(Context context, ExpandableListAdapter adapter) {
		mAdapter = adapter;
		mContext = context;
	}

	public void createMenu(SwipeMenuExpandable menu) {
		// Test Code
		SwipeMenuItem item = new SwipeMenuItem(mContext);
		item.setTitle("Item 1");
		item.setBackground(new ColorDrawable(Color.GRAY));
		item.setWidth(300);
		menu.addMenuItem(item);

		item = new SwipeMenuItem(mContext);
		item.setTitle("Item 2");
		item.setBackground(new ColorDrawable(Color.RED));
		item.setWidth(300);
		menu.addMenuItem(item);
	}

	@Override
	public void onItemClick(SwipeMenuExpandableView view, SwipeMenuExpandable menu, int index) {
		if (onMenuItemClickListener != null) {
			onMenuItemClickListener.onMenuItemClick(view.getPosition(), view.getChildPosition(), menu, index);
		}
	}

	public void setOnMenuItemClickListener(SwipeMenuExpandableListView.OnMenuItemClickListener onMenuItemClickListener) {
		this.onMenuItemClickListener = onMenuItemClickListener;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		mAdapter.registerDataSetObserver(observer);
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		mAdapter.unregisterDataSetObserver(observer);
	}

	@Override
	public boolean areAllItemsEnabled() {
		return mAdapter.areAllItemsEnabled();
	}

	@Override
	public boolean hasStableIds() {
		return mAdapter.hasStableIds();
	}

	@Override
	public boolean isEmpty() {
		return mAdapter.isEmpty();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return mAdapter.getChild(groupPosition, childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {

		return mAdapter.getChildId(groupPosition, childPosition);
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		SwipeMenuExpandableLayout layout = null;
		if (convertView == null) {
			View contentView = mAdapter.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
			SwipeMenuExpandable menu = new SwipeMenuExpandable(mContext);
			// menu.setViewType(mAdapter.getItemViewType(position));
			createMenu(menu);

			SwipeMenuExpandableView menuView = new SwipeMenuExpandableView(menu, (SwipeMenuExpandableListView) parent);
			menuView.setOnSwipeItemClickListener(this);
			SwipeMenuExpandableListView listView = (SwipeMenuExpandableListView) parent;

			layout = new SwipeMenuExpandableLayout(contentView, menuView, listView.getCloseInterpolator(), listView.getOpenInterpolator());
			layout.setPosition(groupPosition);
			layout.setChildPosition(childPosition);
		} else {
			layout = (SwipeMenuExpandableLayout) convertView;
			layout.closeMenu();
			layout.setPosition(groupPosition);
			layout.setChildPosition(childPosition);
			mAdapter.getChildView(groupPosition, childPosition, isLastChild, layout.getContentView(), parent);
		}
		return layout;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return mAdapter.getChildrenCount(groupPosition);
	}

	@Override
	public long getCombinedChildId(long groupId, long childId) {
		return mAdapter.getCombinedChildId(groupId, childId);
	}

	@Override
	public long getCombinedGroupId(long groupId) {
		return mAdapter.getCombinedGroupId(groupId);
	}

	@Override
	public Object getGroup(int groupPosition) {
		return mAdapter.getGroup(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return mAdapter.getGroupCount();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return mAdapter.getGroupId(groupPosition);
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		return mAdapter.getGroupView(groupPosition, isExpanded, convertView, parent);
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return mAdapter.isChildSelectable(groupPosition, childPosition);
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		mAdapter.onGroupCollapsed(groupPosition);
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		mAdapter.onGroupExpanded(groupPosition);

	}

}
