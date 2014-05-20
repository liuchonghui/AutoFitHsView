package com.solo.widget.autofithsview;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * A HorizontalScrollView that auto fit its width due to children's width.
 * 
 * @author liu_chonghui
 * 
 */
public class AutoFitHsView extends HorizontalScrollView {
	/*
	 * HowTo: 
	 * <com.solo.widget.AutoFitHsView 
	 * android:id="@+id/scrollView"
	 * android:layout_width="wrap_content" 
	 * android:layout_height="wrap_content"
	 * android:layout_marginLeft="6dp" android:layout_marginRight="6dp"
	 * android:layout_marginTop="26dp" android:background="#66c4c4c4"
	 * android:fadingEdge="horizontal" android:scrollbars="none" />
	 */

	private BaseAdapter mAdapter;
	private AdapterDataSetObserver mDataSetObserver;

	public AutoFitHsView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (getChildCount() == 0) {
			LinearLayout ll = new LinearLayout(context);
			ll.setOrientation(LinearLayout.HORIZONTAL);
			ll.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			this.addView(ll);
		}
	}

	public void setAdapter(BaseAdapter adapter) {

		RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) getLayoutParams();
		lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);

		if (mAdapter != null) {
			mAdapter.unregisterDataSetObserver(mDataSetObserver);
		}

		mAdapter = adapter;

		if (mAdapter != null) {
			mDataSetObserver = new AdapterDataSetObserver();
			mAdapter.registerDataSetObserver(mDataSetObserver);

		}
		if (mAdapter == null || mAdapter.getCount() == 0) {
			return;
		}

		try {
			fillViewWithAdapter(mAdapter);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}

	}

	private void fillViewWithAdapter(BaseAdapter mAdapter)
			throws IllegalStateException {
		if (getChildCount() == 0) {
			throw new IllegalStateException("AutoFitHsView must have one child");
		}
		if (getChildCount() == 0 || mAdapter == null) {
			return;
		}

		ViewGroup parent = (ViewGroup) getChildAt(0);

		parent.removeAllViews();

		android.view.ViewGroup.LayoutParams lp = new android.view.ViewGroup.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		for (int i = 0; i < mAdapter.getCount(); i++) {
			parent.addView(mAdapter.getView(i, null, parent), lp);
		}
	}

	private class AdapterDataSetObserver extends DataSetObserver {

		@Override
		public void onChanged() {
			try {
				fillViewWithAdapter(mAdapter);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onInvalidated() {
		}

	}

	/**
	 * Set selected child on center.
	 * 
	 * @param index
	 */
	public void setCenter(int childIndex) {
		ViewGroup parent = (ViewGroup) getChildAt(0);
		if (parent == null) {
			throw new IllegalStateException("AutoFitHsView must have one child");
		}

		View prevChild = null;
		if (prevIndex >= 0) {
			prevChild = parent.getChildAt(prevIndex);
		}
		View child = parent.getChildAt(childIndex);
		if (child == null) {
			return;
		}

		if (mAdapter != null && mAdapter instanceof OnItemSetCenterListener) {
			((OnItemSetCenterListener) mAdapter)
					.setOnCenter(childIndex, child);
		}

		int scrollX = (child.getLeft() - (getWidth() / 2))
				+ (child.getWidth() / 2);
		smoothScrollTo(scrollX, 0);

		if (mAdapter != null && mAdapter instanceof OnItemSetCenterListener) {
			if (prevIndex >= 0 && prevChild != null) {
				((OnItemSetCenterListener) mAdapter).leaveCenter(prevIndex,
						prevChild);
			}
		}
		prevIndex = childIndex;
	}

	private int prevIndex = -1;

	public static interface OnItemSetCenterListener {
		/**
		 * Called when child view scroll to center.
		 * 
		 * @param childIndex
		 * @param child
		 */
		void setOnCenter(int childIndex, View child);

		/**
		 * Called when child view leave from center.
		 * 
		 * @param childIndex
		 * @param child
		 */
		void leaveCenter(int childIndex, View child);
	}

}
