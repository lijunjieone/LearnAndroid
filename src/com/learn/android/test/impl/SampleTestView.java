package com.learn.android.test.impl;


import android.app.Activity;

import com.learn.android.R;
import com.learn.android.test.TestCaseListView;


public class SampleTestView extends TestCaseListView {

	private static final String LOGTAG = "SampleTestView";

	public SampleTestView(Activity context) {
		super(context);
	}
	
	public void testCase1() {
//		showToastMessage("run test ");
		showToastMessage(this.getContext().getFileStreamPath("test.txt").getAbsolutePath());
	}
	
	public void testCase2() {
		showTestView(R.layout.test_sample_client_view);
	}
	
	public void testHello() {
		showToastMessage("hello");
	}
	
	


}
