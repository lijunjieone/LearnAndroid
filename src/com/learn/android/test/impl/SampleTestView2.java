package com.learn.android.test.impl;


import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.learn.android.test.TestCaseListView;


public class SampleTestView2 extends TestCaseListView {

	private static final String LOGTAG = "SampleTestView";

	public SampleTestView2(Activity context) {
		super(context);
	}
	
	public void testCase1() {
		MyLinearLayout my=new MyLinearLayout(this.getContext());
		my.setBackgroundColor(Color.RED);
		showTestView(my);
//		showToastMessage("run test ");
	}
	
	public void testCase2() {
		MyLinearLayout my=new MyLinearLayout(this.getContext());
		my.setBackgroundColor(Color.RED);
		Button b=new Button(this.getContext());
		b.setText("b1");
		my.addView(b);
		showTestView(my);

//		showTestView(R.layout.test_sample_client_view);
	}
	
	public void testCase3() {
		ScrollView sv=new ScrollView(this.getContext());
		MyLinearLayout my=new MyLinearLayout(this.getContext());
		my.setBackgroundColor(Color.RED);
		my.setOrientation(LinearLayout.VERTICAL);
		for(int i=0;i<20;i++) {
			Button b=new Button(this.getContext());
			b.setText("b"+i);
			my.addView(b);
		}
		sv.addView(my);
		showTestView(sv);

//		showTestView(R.layout.test_sample_client_view);
	}


	public void testCase4() {
		ScrollView sv=new ScrollView(this.getContext());
		MyLinearLayout my=new MyLinearLayout(this.getContext());
		my.setOrientation(LinearLayout.VERTICAL);
		sv.setBackgroundColor(Color.BLUE);
		my.setBackgroundColor(Color.RED);
		Button b=new Button(this.getContext());
		b.setText("top");
		my.addView(b);
		
		WebView wv=new WebView(this.getContext());
		wv.loadUrl("http://www.qq.com");
		wv.getSettings().setUserAgentString("abc");
		wv.setWebViewClient(new WebViewClient() {
		
		});
		
		my.addView(wv);
		
		sv.addView(my);
		showTestView(sv);
	}
	
	class MyLinearLayout extends LinearLayout {

		public MyLinearLayout(Context context) {
			super(context);
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			Log.e(LOGTAG,"onDraw");
			super.onDraw(canvas);
		}
		
		@Override
		protected void onLayout(boolean changed, int l, int t, int r, int b) {
			Log.e(LOGTAG,"onLayout");

			super.onLayout(changed, l, t, r, b);
		}
		
		@Override
		public void draw(Canvas canvas) {
			Log.e(LOGTAG,"draw");
			super.draw(canvas);
		}
		
		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			Log.e(LOGTAG,"onMeasure");

			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
		
		@Override
		public boolean dispatchTouchEvent(MotionEvent ev) {
			if(ev.getAction()==MotionEvent.ACTION_DOWN) {
				
				Log.e(LOGTAG,"dispatchTouchEvent");
			}

			return super.dispatchTouchEvent(ev);
		}
		
		
		@Override
		public boolean onTouchEvent(MotionEvent event) {
	       if(event.getAction()==MotionEvent.ACTION_DOWN) {
	    	   Log.e(LOGTAG,"onTouchEvent");
				
			}

			return super.onTouchEvent(event);
		}
		
		@Override
		public boolean onInterceptTouchEvent(MotionEvent ev) {
	       if(ev.getAction()==MotionEvent.ACTION_DOWN) {
	    	   Log.e(LOGTAG,"onInterceptTouchEvent");
				
			}

			return super.onInterceptTouchEvent(ev);
		}
		
	}


}
