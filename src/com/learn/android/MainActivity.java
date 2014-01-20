package com.learn.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.learn.android.test.TestSuiteListView;

public class MainActivity extends Activity {

	private static final String LOGTAG = "MainActivity";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TestSuiteListView testSuite = new TestSuiteListView(this);
		setContentView(testSuite);
	}

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.e(LOGTAG,"onKeyDown");
		return super.onKeyDown(keyCode, event);
	}
}
