package com.learn.android.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class TestSuiteListView extends FrameLayout{
	
	protected static final String LOGTAG = "TestSuiteListView";
	protected TestListView mList;

	public TestSuiteListView(Activity context) {
		super(context);
		setupListView();
	}
	
	private void refreshView(View v) {
		this.removeAllViews();
		this.addView(v);
	}
	private void setupListView() {
		mList = new TestListView(getContext()) {
			@Override
			public void onloadTestClass(String className) {
//          	showToastMessage(className);
          	 //do load test case
				try {
					Class cls = Class.forName(className);
					Constructor con = cls.getConstructor(Activity.class);
					TestCaseListView view = (TestCaseListView) con
							.newInstance(getContext());
//					removeAllViews();
//					addView(view);
					refreshView(view);
//					getActivity().activeClientView(view);
					
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
          	
				
			}


		};
		
		mList.setBackgroundColor(Color.WHITE);
		mList.setTestTargetPackage("com.learn.android");
		//测试TestCaseListView的子类
		mList.setTestSuper(TestCaseListView.class);
		mList.loadTestList();
		Log.e(LOGTAG,"size="+mList.getAdapter().getCount());
		addView(mList,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
	}
	

}
