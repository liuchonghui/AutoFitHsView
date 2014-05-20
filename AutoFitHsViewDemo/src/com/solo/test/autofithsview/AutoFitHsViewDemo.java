package com.solo.test.autofithsview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.solo.widget.autofithsview.AutoFitHsView;

/**
 * @author liu_chonghui
 * 
 */
public class AutoFitHsViewDemo extends Activity {

	AutoFitHsView autoFitHsView;
	TestAdapter mAdapter;

	Button btnAdd, btnRemove, btnPop1;
	static List<String> list = new ArrayList<String>();
	static {
		list.add("Arsenal");
		list.add("Chelsea");
		list.add("Liverpool");
		list.add("Everton");
		list.add("West Broom");
		list.add("New Castle");
		list.add("Norich City");
		list.add("Swansea city");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btnAdd = (Button) findViewById(R.id.btnAdd);
		btnRemove = (Button) findViewById(R.id.btnRemove);
		btnPop1 = (Button) findViewById(R.id.btnPop1);
		btnAdd.setOnClickListener(onClickListener);
		btnRemove.setOnClickListener(onClickListener);
		btnPop1.setOnClickListener(onClickListener);

		autoFitHsView = (AutoFitHsView) findViewById(R.id.horizontalScrollView);
		mAdapter = new TestAdapter(this, R.layout.test_item_1,
				android.R.id.text1, new ArrayList<String>());
		autoFitHsView.setAdapter(mAdapter);
	}

	int index = 0;

	OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			final int id = view.getId();
			if (R.id.btnAdd == id) {
				if (index >= 0 && index <= list.size()) {
					mAdapter.add(list.get(index));
					index = index == list.size() - 1 ? list.size() - 1
							: index + 1;
				}
			} else if (R.id.btnRemove == id) {
				if (index >= 0 && index < list.size()) {
					mAdapter.remove(list.get(index));
					index = index == 0 ? 0 : index - 1;
				}
			} else if (R.id.btnPop1 == id) {
				autoFitHsView.setCenter((int) (Math.random() * mAdapter
						.getCount()));
			}
		}
	};

	String arsenal = "Arsenal";
	String everton = "Everton";
	String tottenHam = "TottenHam";
}
