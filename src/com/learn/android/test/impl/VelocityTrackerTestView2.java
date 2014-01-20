package com.learn.android.test.impl;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.learn.android.R;
import com.learn.android.test.TestCaseListView;


public class VelocityTrackerTestView2 extends TestCaseListView {

	private static final String LOGTAG = "SampleTestView";

	public VelocityTrackerTestView2(Activity context) {
		super(context);
	}
	
	
	public void testCase1() {
		FrameLayout first=new FrameLayout(this.getContext());
		first.setLayoutParams(FILL_LAYOUTPARAMS);
		first.setBackgroundColor(Color.RED);
		showTestView(first);
	}
	
	
	public void testCase3() {
		TextView tv=new TextView(this.getContext());
		tv.setText("abc");
		showTestView(tv);
	}
	public void testCase2() {
		
		MyViewGroup my=new MyViewGroup(this.getContext());
		FrameLayout first=new FrameLayout(this.getContext());
		first.setLayoutParams(FILL_LAYOUTPARAMS);
		first.setBackgroundColor(Color.RED);
		FrameLayout second=new FrameLayout(this.getContext());
		second.setBackgroundColor(Color.BLACK);
		second.setLayoutParams(FILL_LAYOUTPARAMS);
	
		FrameLayout third=new FrameLayout(this.getContext());
		third.setBackgroundColor(Color.BLUE);
		third.setLayoutParams(FILL_LAYOUTPARAMS);
		
		my.addView(first);
		my.addView(second);
		my.addView(third);
		
		showTestView(my,FILL_LAYOUTPARAMS);

//		showTestView(R.layout.test_sample_client_view);
	}
		
	class MyViewGroup extends ViewGroup {
	    private Scroller mScroller;
	    private int mScaledTouchSlop = 0;
	    private int mCurrentLayoutFlag = 0;// 当前显示页的标识
	    private int mScrollingX = 0;
	    private static final int FLING_VELOCITY = 1000;// 滑动时的比较数值
	    private static final int REST_STATE = 1;
	    private static final int SCROLLING_STATE = 2;
	    private static final String TAG = "MyViewGroup";
	    private int mTouchState = REST_STATE;// 触摸状态标识
	    private float mLastMovingX;// 记录按下时X的坐标
	    private VelocityTracker mVelocityTracker;
	    public MyViewGroup(Context context) {
	        super(context);
	        // TODO Auto-generated constructor stub
	        mScroller = new Scroller(context);
	        // 在User触控滑动前预测移动位移
	        mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
	        // 配置ViewGroup的宽为WRAP_CONTENT，高为MATCH_PARENT
	        MyViewGroup.this.setLayoutParams(new ViewGroup.LayoutParams(
	                ViewGroup.LayoutParams.WRAP_CONTENT,
	                ViewGroup.LayoutParams.MATCH_PARENT));
	    }
//	    public MyViewGroup(Context context, AttributeSet attrs) {
//	        super(context, attrs);
//	        // TODO Auto-generated constructor stub
//	        mScroller = new Scroller(context);
//	        // 在用户触控之前预测移动位移
//	        mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
//	        // 配置ViewGroup的宽为WRAP_CONTENT，高为MATCH_PARENT
//	        MyViewGroup.this.setLayoutParams(new ViewGroup.LayoutParams(
//	                ViewGroup.LayoutParams.WRAP_CONTENT,
//	                ViewGroup.LayoutParams.MATCH_PARENT));
//	        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
//	                R.styleable.SlideStyledAttributes);
//	        mCurrentLayoutFlag = mTypedArray.getInteger(
//	                R.styleable.SlideStyledAttributes_view_screen, 0);
//	    }
	    /**
	     * 复写onMeasure方法，并判断所在Layout Flag
	     */
	    @Override
	    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	        int width = MeasureSpec.getSize(widthMeasureSpec);
//	        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//	        if (widthMode != MeasureSpec.EXACTLY) {
//	            throw new IllegalStateException("error mode.");
//	        }
//	        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//	        if (heightMode != MeasureSpec.EXACTLY) {
//	            throw new IllegalStateException("error mode.");
//	        }
	        int count = getChildCount();
	        for (int i = 0; i < count; i++) {
	            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
	        }
	        scrollTo(mCurrentLayoutFlag * width, 0);
	    }
	    /**
	     * 继承在ViewGroup必须重写的onLayout()方法
	     */
	    @Override
	    protected void onLayout(boolean changed, int l, int t, int r, int b) {
	        // TODO Auto-generated method stub
	        int childLeft = 0;
	        int count = getChildCount();
	        for (int i = 0; i < count; i++) {
	            View child = getChildAt(i);
	            if (child.getVisibility() != View.GONE) {
	                int childWidth = child.getMeasuredWidth();
	                child.layout(childLeft, 0, childLeft + childWidth,
	                        child.getMeasuredHeight());
	                childLeft += childWidth;
	            }
	        }
	    }
	    /**
	     * 复写computeScroll()方法告诉View已更新
	     */
	    @Override
	    public void computeScroll() {
	        // TODO Auto-generated method stub
	        if (mScroller.computeScrollOffset()) {
	            // 取得目前Scroller的X offset
	            mScrollingX = mScroller.getCurrX();
	            // 移动至scroll移动的position
	            scrollTo(mScrollingX, 0);
	            // 调用invalidate()方法处理来自non-UI thread的移动请求。
	            postInvalidate();
	        }
	    }
	    /**
	     * 触摸拦截，每次触摸都会先调用该方法，返回true，则不在进行往下传，这后面的触摸处理事件不能被激发
	     */
	    @Override
	    public boolean onInterceptTouchEvent(MotionEvent ev) {
	        // 实现onInterceptTouchEvent方法拦截User手指触控屏幕移动事件
	        if ((ev.getAction() == MotionEvent.ACTION_MOVE)
	                && (mTouchState != REST_STATE)) {
	            return true;
	        }
	        switch (ev.getAction()) {
	        // 按住触控屏幕事件开始
	        case MotionEvent.ACTION_DOWN:
	            // 记录按下的X坐标
	            mLastMovingX = ev.getX();
	            mTouchState = mScroller.isFinished() ? REST_STATE : SCROLLING_STATE;
	            break;
	        // 按住触控屏幕其移动事件
	        case MotionEvent.ACTION_MOVE:
	            // 判断ACTION_MOVE事件间的移动X坐标间距
	            int intShiftX = (int) Math.abs(ev.getX() - mLastMovingX);
	            if (intShiftX > mScaledTouchSlop) {
	                mTouchState = SCROLLING_STATE;
	            }
	            break;
	        case MotionEvent.ACTION_CANCEL:
	        case MotionEvent.ACTION_UP:
	            // 手指离开屏幕
	            mTouchState = REST_STATE;
	            break;
	        }
	        return mTouchState != REST_STATE;
	    }
	    @Override
	    public boolean onTouchEvent(MotionEvent event) {
	        if (mVelocityTracker == null) {
	            // 实现flinging事件，通过obtain()取得新的tracking实例
	            mVelocityTracker = VelocityTracker.obtain();
	        }
	        // 将User触控的MotionEvent加入Tracker
	        mVelocityTracker.addMovement(event);
	        // 判断user的onTouchEvent屏幕触控事件
	        switch (event.getAction()) {
	        case MotionEvent.ACTION_DOWN:
	            // 判断滑动事件是否完成，并停止滑动动画
	            if (!mScroller.isFinished()) {
	                mScroller.abortAnimation();
	            }
	            // 当手指按下屏幕触发事件时，记录X的坐标
	            mLastMovingX = event.getX();
	            break;
	        case MotionEvent.ACTION_UP:
	            // 当手指离开屏幕时，记录下mVelocityTracker的记录，并取得X轴滑动速度
	            VelocityTracker velocityTracker = mVelocityTracker;
	            velocityTracker.computeCurrentVelocity(1000);
	            float xVelocity = velocityTracker.getXVelocity();
	            // 当X轴滑动速度大于1000，且mCurrentLayoutFlag>0
	            if (xVelocity > FLING_VELOCITY && mCurrentLayoutFlag > 0) {
	                // 向左移动画面
	                snapToScreen(mCurrentLayoutFlag - 1);
	            } else if (xVelocity < -FLING_VELOCITY
	                    && mCurrentLayoutFlag < getChildCount() - 1) {
	                // 向右移动画面
	                snapToScreen(mCurrentLayoutFlag + 1);
	            } else {
	                snapToDestination();
	            }
	            if (mVelocityTracker != null) {
	                mVelocityTracker.recycle();
	                mVelocityTracker = null;
	            }
	            mTouchState = REST_STATE;
	            break;
	        case MotionEvent.ACTION_CANCEL:
	            mTouchState = REST_STATE;
	            break;
	        }
	        mScrollingX = MyViewGroup.this.getScrollX();
	        return true;
	    }
	    /**
	     * 当不需要滑动时，会调用该方法
	     */
	    private void snapToDestination() {
	        int screenWidth = getWidth();
	        int whichScreen = (mScrollingX + (screenWidth / 2)) / screenWidth;
	        snapToScreen(whichScreen);
	    }
	    /**
	     * 切换界面时调用的方法
	     * 
	     * @param whichScreen
	     *            需要移至的目标界面的flag
	     */
	    private void snapToScreen(int whichScreen) {
	        // TODO Auto-generated method stub
	        mCurrentLayoutFlag = whichScreen;
	        int newX = whichScreen * getWidth();
	        int delta = newX - mScrollingX;
	        mScroller.startScroll(mScrollingX, 0, delta, 0, Math.abs(delta) * 2);
	        // 静止重绘View的画面
	        invalidate();
	    }
	}

}


