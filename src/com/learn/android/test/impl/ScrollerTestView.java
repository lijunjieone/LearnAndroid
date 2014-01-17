package com.learn.android.test.impl;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.learn.android.R;
import com.learn.android.test.TestCaseListView;


public class ScrollerTestView extends TestCaseListView {

	private static final String LOGTAG = "ScrollerTestView";

	public ScrollerTestView(Activity context) {
		super(context);
	}
	
	
	public void testScroll1() {
		CustomView v=new CustomView(this.getContext());
		TextView tv=new TextView(this.getContext());
		tv.setText("test");
		v.addView(tv);
		v.setBackgroundColor(Color.BLUE);
		showTestView(v);
	}
	
	
	public void testLayout1() {
		showTestView(R.layout.test_layout_1);
	}
	
	public void testLayout2() {
		LinearLayout f=(LinearLayout)View.inflate(getContext(),R.layout.test_layout_2,null);
		
		WebView v=(WebView)f.findViewById(R.id.webView1);
		v.setWebViewClient(new WebViewClient() {
			
		});
		v.loadUrl("http://i.ifeng.com");
		
		showTestView(f);
	}
	
	public void testLayout21() {
		LinearLayout f=(LinearLayout)View.inflate(getContext(),R.layout.test_layout_2,null);
		f.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.e(LOGTAG,"LinearLayout.onTouch"+event);
				return false;
			}
		});
		
		WebView v=(WebView)f.findViewById(R.id.webView1);
		v.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.e(LOGTAG,"WebView.onTouch"+event);

				return false;
			}
		});
		v.setWebViewClient(new WebViewClient() {
			
		});
		v.loadUrl("http://i.ifeng.com");
		CustomView2 custom=new CustomView2(this.getContext());

		custom.addView(f,FILL_LAYOUTPARAMS);
		showTestView(custom);
	}

	
	
	public void testLayout3() {
		
		CustomView3 custom=new CustomView3(this.getContext());
		custom.setOrientation(LinearLayout.VERTICAL);

//		custom.addView(f,FILL_LAYOUTPARAMS);
		Button b=new Button(this.getContext());
		b.setText("top");
		custom.getCustomViewTop().addView(b);
		Button b2=new Button(this.getContext());
		b2.setText("bottom");
		Button b3=new Button(this.getContext());
		b3.setText("center");
		custom.getCustomViewBottom().addView(b2);
		custom.getCenter().addView(b3);
		showTestView(custom);
	}

	public void testLayout4() {
		
		CustomView3 custom=new CustomView3(this.getContext());
		custom.setOrientation(LinearLayout.VERTICAL);

//		custom.addView(f,FILL_LAYOUTPARAMS);
		Button b=new Button(this.getContext());
		b.setText("top");
		custom.getCustomViewTop().addView(b);
		Button b2=new Button(this.getContext());
		b2.setText("bottom");
		WebView v=new WebView(getContext());
		v.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.e(LOGTAG,"WebView.onTouch"+event);

				return false;
			}
		});
		v.setWebViewClient(new WebViewClient() {
			
		});
		v.loadUrl("http://i.ifeng.com");
		custom.getCustomViewBottom().addView(b2);
		custom.getCenter().addView(v);
		showTestView(custom);
	}

}
class CustomView extends LinearLayout {

	private static final String TAG = "ScrollerTestView";

	private Scroller mScroller;
	private GestureDetector mGestureDetector;
	
	
	public CustomView(Context context) {
		this(context, null);
	}
	
	public CustomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setClickable(true);
		setLongClickable(true);
		mScroller = new Scroller(context);
		mGestureDetector = new GestureDetector(context, new CustomGestureListener());
	}

	//调用此方法滚动到目标位置
	public void smoothScrollTo(int fx, int fy) {
		int dx = fx - mScroller.getFinalX();
		int dy = fy - mScroller.getFinalY();
		smoothScrollBy(dx, dy);
	}

	//调用此方法设置滚动的相对偏移
	public void smoothScrollBy(int dx, int dy) {

		//设置mScroller的滚动偏移量
		mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
		invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
	}
	
	@Override
	public void computeScroll() {
	
		//先判断mScroller滚动是否完成
		if (mScroller.computeScrollOffset()) {
		
			//这里调用View的scrollTo()完成实际的滚动
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			
			//必须调用该方法，否则不一定能看到滚动效果
			postInvalidate();
		}
		super.computeScroll();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP :
			Log.i(TAG, "get Sy" + getScrollY());
//			smoothScrollTo(0, 0);
			break;
		default:
				return mGestureDetector.onTouchEvent(event);
		}
		return super.onTouchEvent(event);
	}
	
	class CustomGestureListener implements GestureDetector.OnGestureListener {

		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

		@Override
		public void onShowPress(MotionEvent e) {
			
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			return false;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			
			int dis = (int)((distanceY-0.5)/2);
			Log.i(TAG, dis + ".");
			smoothScrollBy(0, dis);
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			return false;
		}
	}
 }


class CustomView2 extends LinearLayout {

	private static final String TAG = "ScrollerTestView";

	private Scroller mScroller;
	private GestureDetector mGestureDetector;
	
	public CustomView2(Context context) {
		this(context, null);
	}
	
	public CustomView2(Context context, AttributeSet attrs) {
		super(context, attrs);
		setClickable(true);
		setLongClickable(true);
		mScroller = new Scroller(context);
		mGestureDetector = new GestureDetector(context, new CustomGestureListener());
	}

	//调用此方法滚动到目标位置
	public void smoothScrollTo(int fx, int fy) {
		int dx = fx - mScroller.getFinalX();
		int dy = fy - mScroller.getFinalY();
		smoothScrollBy(dx, dy);
	}

	//调用此方法设置滚动的相对偏移
	public void smoothScrollBy(int dx, int dy) {

		//设置mScroller的滚动偏移量
		mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
		invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
	}
	
	@Override
	public void computeScroll() {
	
		//先判断mScroller滚动是否完成
		if (mScroller.computeScrollOffset()) {
		
			//这里调用View的scrollTo()完成实际的滚动
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			
			//必须调用该方法，否则不一定能看到滚动效果
			postInvalidate();
		}
		super.computeScroll();
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_UP :
			Log.i(TAG, "get Sy" + getScrollY());
//			smoothScrollTo(0, 0);
			break;
		default:
			if(getScrollY()>100) {
				return super.dispatchTouchEvent(ev);
			}else {
				return mGestureDetector.onTouchEvent(ev);
			}
		}
		return super.dispatchTouchEvent(ev);
	}
//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		switch (event.getAction()) {
//		case MotionEvent.ACTION_UP :
//			Log.i(TAG, "get Sy" + getScrollY());
////			smoothScrollTo(0, 0);
//			break;
//		default:
//			return mGestureDetector.onTouchEvent(event);
//		}
//		return super.onTouchEvent(event);
//	}
	
	class CustomGestureListener implements GestureDetector.OnGestureListener {

		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

		@Override
		public void onShowPress(MotionEvent e) {
			
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			return false;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			
			int dis = (int)((distanceY-0.5)/2);
			Log.i(TAG, dis + ".");
			smoothScrollBy(0, dis);
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			return false;
		}
	}
 }




class CustomView3 extends LinearLayout {

	private static final String TAG = "ScrollerTestView";

	private Scroller mScroller;
	private GestureDetector mGestureDetector;
	
	private CustomView4 mCustomViewTop;
	
	private FrameLayout mCenter;
	private CustomView4 mCustomViewBottom;
	private int mScreenHeight=0;
	public CustomView3(Context context) {
		this(context, null);
	}
	
	public CustomView3(Context context, AttributeSet attrs) {
		super(context, attrs);
		setClickable(true);
		setLongClickable(true);
		mScroller = new Scroller(context);
		mCustomViewTop=new CustomView4(context);
		mCenter=new FrameLayout(context);
		mCustomViewBottom=new CustomView4(context);
		LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 2.0f);
		LinearLayout.LayoutParams wrap_content=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

		this.setBackgroundColor(Color.BLUE);
		mCustomViewTop.setBackgroundColor(Color.CYAN);
		mCenter.setBackgroundColor(Color.GREEN);
		mCustomViewBottom.setBackgroundColor(Color.RED);
		this.addView(mCustomViewTop,wrap_content);
		this.addView(mCenter,ll);
		this.addView(mCustomViewBottom,wrap_content);
		mScreenHeight=((Activity) context).getWindowManager().getDefaultDisplay().getHeight();
		mGestureDetector = new GestureDetector(context, new CustomGestureListener());
	}
	
	
	public LinearLayout getCustomViewTop() {
		return mCustomViewTop;
	}
	
	public FrameLayout getCenter() {
		return mCenter;
	}
	
	public LinearLayout getCustomViewBottom() {
		return mCustomViewBottom;
	}

	//调用此方法滚动到目标位置
	public void smoothScrollTo(int fx, int fy) {
		int dx = fx - mScroller.getFinalX();
		int dy = fy - mScroller.getFinalY();
		smoothScrollBy(dx, dy);
	}

	//调用此方法设置滚动的相对偏移
	public void smoothScrollBy(int dx, int dy) {

		//设置mScroller的滚动偏移量
		mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
		invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
	}
	
	@Override
	public void computeScroll() {
	
		//先判断mScroller滚动是否完成
		if (mScroller.computeScrollOffset()) {
		
			//这里调用View的scrollTo()完成实际的滚动
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			
			//必须调用该方法，否则不一定能看到滚动效果
			postInvalidate();
		}
		super.computeScroll();
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_UP :
			Log.i(TAG, "get Sy" + getScrollY());
//			smoothScrollTo(0, 0);
			break;
		default:
			mGestureDetector.onTouchEvent(ev);
			return super.dispatchTouchEvent(ev);

//			if(getScrollY()>100) {
//				return super.dispatchTouchEvent(ev);
//			}else {
//				return mGestureDetector.onTouchEvent(ev);
//			}
		}
		return super.dispatchTouchEvent(ev);
	}
//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		switch (event.getAction()) {
//		case MotionEvent.ACTION_UP :
//			Log.i(TAG, "get Sy" + getScrollY());
////			smoothScrollTo(0, 0);
//			break;
//		default:
//			return mGestureDetector.onTouchEvent(event);
//		}
//		return super.onTouchEvent(event);
//	}
	
	class CustomGestureListener implements GestureDetector.OnGestureListener {

		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

		@Override
		public void onShowPress(MotionEvent e) {
			
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			return false;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			
			int dis = (int)((distanceY-0.5)/2);
			Log.i(TAG, dis + "."+distanceY+"."+mScreenHeight);
			mCustomViewTop.smoothScrollBy(0, dis);
			mCustomViewBottom.smoothScrollBy(0, -dis);
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			return false;
		}
	}
 }


class CustomView4 extends LinearLayout {

	private static final String TAG = "ScrollerTestView";

	private Scroller mScroller;
	private GestureDetector mGestureDetector;
	
	public CustomView4(Context context) {
		this(context, null);
	}
	
	public CustomView4(Context context, AttributeSet attrs) {
		super(context, attrs);
		setClickable(true);
		setLongClickable(true);
		mScroller = new Scroller(context);
	}

	//调用此方法滚动到目标位置
	public void smoothScrollTo(int fx, int fy) {
		int dx = fx - mScroller.getFinalX();
		int dy = fy - mScroller.getFinalY();
		smoothScrollBy(dx, dy);
	}

	//调用此方法设置滚动的相对偏移
	public void smoothScrollBy(int dx, int dy) {

		//设置mScroller的滚动偏移量
		mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
		invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
	}
	
	@Override
	public void computeScroll() {
	
		//先判断mScroller滚动是否完成
		if (mScroller.computeScrollOffset()) {
		
			//这里调用View的scrollTo()完成实际的滚动
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			
			//必须调用该方法，否则不一定能看到滚动效果
			postInvalidate();
		}
		super.computeScroll();
	}
	
}
		