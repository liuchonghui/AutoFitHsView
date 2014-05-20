package com.solo.test.autofithsview;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.solo.widget.autofithsview.AutoFitHsView;

class TestAdapter extends ArrayAdapter<String> implements
		AutoFitHsView.OnItemSetCenterListener {

	public TestAdapter(Context context, int resourceId, int textViewResourceId,
			List<String> objects) {
		super(context, resourceId, textViewResourceId, objects);
		sort(null);
	}

	@Override
	public void add(String object) {
		super.remove(object);
		super.add(object);
		sort(null);
	}

	@Override
	public void remove(String object) {
		super.remove(object);
		sort(null);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = super.getView(position, convertView, parent);

		ImageView image = (ImageView) view.findViewById(R.id.image);
		TextView text = (TextView) view.findViewById(android.R.id.text1);
		String content = text.getText().toString();
		if (content.equalsIgnoreCase("Chelsea")) {
			image.setImageResource(R.drawable.usr1);
		} else if (content.equalsIgnoreCase("Everton")) {
			image.setImageResource(R.drawable.usr2);
		} else if (content.equalsIgnoreCase("Liverpool")) {
			image.setImageResource(R.drawable.usr3);
		} else if (content.equalsIgnoreCase("Arsenal")) {
			image.setImageResource(R.drawable.usr4);
		} else if (content.equalsIgnoreCase("West Broom")) {
			image.setImageResource(R.drawable.usr5);
		} else if (content.equalsIgnoreCase("New Castle")) {
			image.setImageResource(R.drawable.usr6);
		} else if (content.equalsIgnoreCase("Norich City")) {
			image.setImageResource(R.drawable.usr7);
		} else if (content.equalsIgnoreCase("Swansea city")) {
			image.setImageResource(R.drawable.usr8);
		}

		return view;
	}

	@Override
	public void setOnCenter(int childIndex, View child) {
		child.startAnimation(AnimationUtils.loadAnimation(getContext(),
				R.anim.shake));
	}

	@Override
	public void leaveCenter(int childIndex, View child) {
	}
}
