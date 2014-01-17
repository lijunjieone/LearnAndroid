package com.learn.android.test;


import android.app.Activity;

import com.learn.android.R;


public class SampleTestView extends TestCaseListView {

	private static final String LOGTAG = "SampleTestView";

	public SampleTestView(Activity context) {
		super(context);
	}
	
	public void testCase1() {
		showToastMessage("run test ");
	}
	
	public void testCase2() {
		showTestView(R.layout.test_sample_client_view);
	}
	
	public void testHello() {
		showToastMessage("hello");
	}
	
	


}
