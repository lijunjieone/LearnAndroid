package com.learn.android.test.impl;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.learn.android.R;
import com.learn.android.test.TestCaseListView;
import com.learn.android.test.impl.CustomView3.CustomGestureListener;


public class ScrollerTestView2 extends TestCaseListView {

	private static final String LOGTAG = "ScrollerTestView2";

	public ScrollerTestView2(Activity context) {
		super(context);
	}
	
	
	public void testLayout() {
		showTestView(R.layout.test_layout_2);
	}
	
	public void testLayoutLoadSina() {
		ViewGroup vg=(ViewGroup)View.inflate(this.getContext(), R.layout.test_layout_2, null);
		WebView webview=(WebView)vg.findViewById(R.id.webView1);
		webview.loadUrl("http://i.ifeng.com");
		webview.setWebViewClient(new WebViewClient() {
			
		});
		
		showTestView(vg);
	}
	
	public void testButtonScroll2() {
		final MyLinearLayout f=new MyLinearLayout(this.getContext());
		final MyLinearLayout f2=new MyLinearLayout(this.getContext());
//		f2.setBackgroundColor(Color.GREEN);

		final Button b=new Button(this.getContext());
		final GestureDetector mGestureDetector = new GestureDetector(this.getContext(), new GestureDetector.OnGestureListener() {

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
				f.scrollBy(0, dis);
				f2.scrollBy(0, -dis);
//				scrollBy(0, dis);
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
		});

		FrameLayout f1=new FrameLayout(this.getContext()){
			@Override
			public boolean dispatchTouchEvent(MotionEvent ev) {
				mGestureDetector.onTouchEvent(ev);

				return super.dispatchTouchEvent(ev);
			}
		};
		f.setClickable(true);
		f.setOrientation(LinearLayout.VERTICAL);
		f.setBackgroundColor(Color.RED);
		b.setText("abc");
		
		WebView v=new WebView(this.getContext());
		v.loadUrl("http://m.qq.com");
		v.setWebViewClient(new WebViewClient() {
			
		});
		
		f.addView(b);
		f.addView(v,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,2));
		f1.addView(f);
		FrameLayout.LayoutParams f2Params=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,180);
		Button b2=new Button(this.getContext());
		b2.setText("bottom");
		f2.addView(b2);
		f2Params.gravity=Gravity.BOTTOM;
		f1.addView(f2,f2Params);
		f1.setBackgroundColor(Color.BLUE);
		showTestView(f1);
	}
	
	public void testButtonScroll() {
		ViewGroup vg=(ViewGroup)View.inflate(this.getContext(), R.layout.test_layout_2, null);
		final WebView webview=(WebView)vg.findViewById(R.id.webView1);
		webview.loadUrl("http://i.ifeng.com");
		webview.setWebViewClient(new WebViewClient() {
			
		});
		
		FrameLayout f1=(FrameLayout)vg.findViewById(R.id.f1);
		f1.setClickable(true);
		f1.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.e(LOGTAG,"ev="+event.toString());

				return false;
			}
		});
		
		vg.setOnGenericMotionListener(new View.OnGenericMotionListener() {
			
			@Override
			public boolean onGenericMotion(View v, MotionEvent event) {
				Log.e(LOGTAG,"onGenericMotion.ev="+event.toString());

				if(event.getAction()==MotionEvent.ACTION_MOVE) {
					int dis=(int)(event.getY());
					webview.scrollBy(0, dis);
				}

				return false;
			}
		});
		showTestView(vg);

	}
	
	
	public void testLoadLayout() {
		ViewGroup vg=(ViewGroup)View.inflate(this.getContext(), R.layout.fullscreen_layout2, null);
		FrameLayout f=(FrameLayout)vg.findViewById(R.id.main_content);
		final WebView webview=new WebView(this.getContext());
		webview.loadUrl("http://i.ifeng.com");
		webview.setWebViewClient(new WebViewClient() {
			
		});

		f.addView(webview);
		
		FrameLayout topContent=(FrameLayout)vg.findViewById(R.id.top_content);
		topContent.setBackground(null);
		Button b=new Button(this.getContext());
		b.setText("top");
		topContent.addView(b);
		
		FrameLayout bottomContent=(FrameLayout)vg.findViewById(R.id.bottom_content);
		bottomContent.setBackground(null);
		Button b1=new Button(this.getContext());
		b1.setText("bottom");
		bottomContent.addView(b1);
	
		FrameLayout.LayoutParams fl=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
		showTestView(vg,fl);
	}
	
	public void testLoadLayoutByXml() {

		showTestView(R.layout.fullscreen_layout2);
	}
	
	public class MyLinearLayout extends LinearLayout {

		public MyLinearLayout(Context context) {
			super(context);
		}
		public MyLinearLayout(Context context, AttributeSet attrs) {
			super(context, attrs);
		}
		public MyLinearLayout(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
		}
		
		@Override
		public boolean dispatchTouchEvent(MotionEvent ev) {
			Log.e(LOGTAG,"ev="+ev.toString());
			return super.dispatchTouchEvent(ev);
		}
	
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
//			scrollBy(0, dis);
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


class MyFrameLayout extends FrameLayout {

	private Context mContext;
	
	private ViewGroup mTopPanel;
	private ViewGroup mBottomPanel;
	private int mAllDistance2=0;
	private int mBottomHeight=50;
	private int mAllDistance=0;
	private int mTopHeight=100;
	
	public MyFrameLayout(Context context) {
		super(context);
		init(context);
	}
	
	public MyFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	public MyFrameLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	private void init(Context context) {
		mContext=context;
		mTopPanel=(ViewGroup)this.findViewById(R.id.top_panel);
		mBottomPanel=(ViewGroup)this.findViewById(R.id.bottom_content);
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		mGestureDetector.onTouchEvent(ev);

		return super.dispatchTouchEvent(ev);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mTopPanel=(ViewGroup)this.findViewById(R.id.top_panel);
		mBottomPanel=(ViewGroup)this.findViewById(R.id.bottom_content);

	}
	
	private int getActualBottomDis(int distanceY) {
		int dis = (int) (distanceY+1);
		if(mAllDistance2>=0&&mAllDistance2<=mBottomHeight){
			mAllDistance2+=dis;
			if(mAllDistance2>mBottomHeight) {
				dis=mBottomHeight-(mAllDistance2-dis);
				mAllDistance2=mBottomHeight;
			}else if(mAllDistance2<0) {
				dis=dis-mAllDistance2;
				mAllDistance2=0;
				
			}
		}else {
			dis=0;
		}
		return dis;
	}
	private int getActualTopDis(int distanceY) {
		int dis = (int) (distanceY+1);
		if(mAllDistance>=0&&mAllDistance<=mTopHeight){
			mAllDistance+=dis;
			if(mAllDistance>mTopHeight) {
				dis=mTopHeight-(mAllDistance-dis);
				mAllDistance=mTopHeight;
			}else if(mAllDistance<0) {
				dis=dis-mAllDistance;
				mAllDistance=0;
				
			}
		}else {
			dis=0;
		}
		return dis;
	}

	final GestureDetector mGestureDetector = new GestureDetector(this.getContext(), new GestureDetector.OnGestureListener() {

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
			if(mTopPanel!=null) {
//				int dis=getActualTopDis(dis);
				mTopPanel.scrollBy(0, getActualTopDis(dis));
			}
			if(mBottomPanel!=null) {
				mBottomPanel.scrollBy(0, -getActualBottomDis(dis));
			}
//			f.scrollBy(0, dis);
//			f2.scrollBy(0, -dis);
//			scrollBy(0, dis);
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
	});


}
