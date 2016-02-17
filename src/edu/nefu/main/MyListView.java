package edu.nefu.main;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * ʵ������ˢ��
 * @author WJH
 *
 */
public class MyListView extends ListView implements OnScrollListener {

	  private static final int TAP_TO_REFRESH=1;
	  private static final int PULL_TO_REFRESH=2;
	  private static final int RELEASE_TO_REFRESH=3;
	  private static final int REFRESHING=4;

	  private OnRefreshListener mOnRefreshListener;
	  private OnMoreListener mOnMoreListener;

	  private OnScrollListener mOnScrollListener;
	  private LayoutInflater mInflater;

	  private RelativeLayout mRefreshView;
	  private LinearLayout mFooter;
	  private ProgressBar mFooterProgress;
	  private TextView mFooterText;
	  private TextView mRefreshViewText;
	  private ImageView mRefreshViewImage;
	  private ProgressBar mRefreshViewProgress;
	  private TextView mRefreshViewLastUpdated;

	  private int mCurrentScrollState;
	  private int mRefreshState;

	  private RotateAnimation mFlipAnimation;
	  private RotateAnimation mReverseFlipAnimation;

	  private int mRefreshViewHeight;
	  private int mRefreshOriginalTopPadding;
	  private int mLastMotionY;

	  private boolean mBounceHack;
	  private int mTotalItemCount;
	  private int mAfterMoreSelection;

	  public MyListView(Context context) {
	      super(context);
	      init(context);
	  }

	  public MyListView(Context context, AttributeSet attrs) {
	      super(context, attrs);
	      init(context);
	  }

	  public MyListView(Context context, AttributeSet attrs, int defStyle) {
	      super(context, attrs, defStyle);
	      init(context);
	  }

	  private void init(Context context) {
		  mFlipAnimation=new RotateAnimation(0, -180,
			                 RotateAnimation.RELATIVE_TO_SELF, 0.5f,
			                 RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		  mFlipAnimation.setInterpolator(new LinearInterpolator());
		  mFlipAnimation.setDuration(250);
		  mFlipAnimation.setFillAfter(true);
		  mReverseFlipAnimation=new RotateAnimation(-180, 0,
			                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
			                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
			                        mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
			                        mReverseFlipAnimation.setDuration(250);
			                        mReverseFlipAnimation.setFillAfter(true);

		  mInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		  
		  mRefreshView=(RelativeLayout)mInflater.inflate(R.layout.pull_to_refresh_header, this, false);
	      mRefreshViewText=(TextView)mRefreshView.findViewById(R.id.pull_to_refresh_text);
		  mRefreshViewImage=(ImageView)mRefreshView.findViewById(R.id.pull_to_refresh_image);
		  mRefreshViewProgress=(ProgressBar)mRefreshView.findViewById(R.id.pull_to_refresh_progress);
		  mRefreshViewLastUpdated=(TextView)mRefreshView.findViewById(R.id.pull_to_refresh_updated_at);
		  mRefreshViewImage.setMinimumHeight(50);
	      mRefreshView.setOnClickListener(new OnClickRefreshListener());
		  mRefreshOriginalTopPadding=mRefreshView.getPaddingTop();
		  
		  mFooter=(LinearLayout)mInflater.inflate(R.layout.push_to_more_footer, this, false);
		  mFooter.setMinimumHeight(50);
	      mFooterProgress=(ProgressBar)mFooter.findViewById(R.id.push_to_more_progress);
		  mFooterText=(TextView)mFooter.findViewById(R.id.push_to_more_text);
		  mRefreshState=TAP_TO_REFRESH;
		  addHeaderView(mRefreshView);
		  super.setOnScrollListener(this);
		  measureView(mRefreshView);
	      mRefreshViewHeight=mRefreshView.getMeasuredHeight();
	  }

	  @Override
	  protected void onAttachedToWindow() {
	      super.onAttachedToWindow();
	      setSelection(1);
	  }

	  @Override
	  public void setAdapter(ListAdapter adapter) {
	      super.setAdapter(adapter);
	      //addFooterView(mFooter);
	      setSelection(1);
	  }

	  @Override
	  public void setOnScrollListener(AbsListView.OnScrollListener l) {
	      mOnScrollListener=l;
	  }

	  public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
	      mOnRefreshListener=onRefreshListener;
	  }

	  public void setOnMoreListener(OnMoreListener onMoreListener) {
	      mOnMoreListener=onMoreListener;
	  }

	  public void setLastUpdated(CharSequence lastUpdated) {
	      if(lastUpdated!=null){
	          mRefreshViewLastUpdated.setVisibility(View.VISIBLE);
	          mRefreshViewLastUpdated.setText(lastUpdated);
	      }else{
	          mRefreshViewLastUpdated.setVisibility(View.GONE);
	      }
	  }

	  @Override
	  public boolean onTouchEvent(MotionEvent event) {
	      final int y=(int)event.getY();
	      mBounceHack=false;
	      switch(event.getAction()){
	      case MotionEvent.ACTION_UP:
	          if(!isVerticalScrollBarEnabled()){
	              setVerticalScrollBarEnabled(true);
	          }
	          if(getFirstVisiblePosition()==0&&mRefreshState!=REFRESHING){
	              if((mRefreshView.getBottom()>=mRefreshViewHeight||mRefreshView.getTop()>=0)&&mRefreshState==RELEASE_TO_REFRESH){
	                  mRefreshState = REFRESHING;
	                  prepareForRefresh();
	                  onRefresh();
	              }else if(mRefreshView.getBottom()<mRefreshViewHeight||mRefreshView.getTop()<=0){
	                  resetHeader();
	                  setSelection(1);
	              }
	          }else if(getLastVisiblePosition()==mTotalItemCount-1){
	              mFooterText.setVisibility(View.GONE);
	              mFooterProgress.setVisibility(View.VISIBLE);
	              onMore();
	          }
	          break;
	      case MotionEvent.ACTION_DOWN:
	          mLastMotionY=y;
	          break;
	      case MotionEvent.ACTION_MOVE:
	          applyHeaderPadding(event);
	          break;
	      }
	      return super.onTouchEvent(event);
	  }

	  public void onMoreComplete() {
	      setSelection(mAfterMoreSelection);
	      mFooterText.setVisibility(View.VISIBLE);
	      mFooterProgress.setVisibility(View.GONE);
	  }

	  @Override
	  public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
	      mTotalItemCount=totalItemCount;
	      mAfterMoreSelection=firstVisibleItem;
	      if(mCurrentScrollState==SCROLL_STATE_TOUCH_SCROLL&&mRefreshState!=REFRESHING){
	          if(firstVisibleItem==0){
	              mRefreshViewImage.setVisibility(View.VISIBLE);
	              if((mRefreshView.getBottom()>=mRefreshViewHeight+20||mRefreshView.getTop()>=0)&&mRefreshState!=RELEASE_TO_REFRESH){
	                  mRefreshViewText.setText(R.string.pull_to_refresh_release_label);
	                  mRefreshViewImage.clearAnimation();
	                  mRefreshViewImage.startAnimation(mFlipAnimation);
	                  mRefreshState=RELEASE_TO_REFRESH;
	              }else if(mRefreshView.getBottom()<mRefreshViewHeight+20&&mRefreshState!=PULL_TO_REFRESH){
	                  mRefreshViewText.setText(R.string.pull_to_refresh_pull_label);
	                  if(mRefreshState!=TAP_TO_REFRESH){
	                      mRefreshViewImage.clearAnimation();
	                      mRefreshViewImage.startAnimation(mReverseFlipAnimation);
	                  }
	                  mRefreshState=PULL_TO_REFRESH;
	              }
	          }else{
	              mRefreshViewImage.setVisibility(View.GONE);
	              resetHeader();
	          }
	      }else if(mCurrentScrollState==SCROLL_STATE_FLING&&firstVisibleItem==0&&mRefreshState!=REFRESHING){
	          setSelection(1);
	          mBounceHack=true;
	      }else if(mBounceHack&&mCurrentScrollState==SCROLL_STATE_FLING) {
	          setSelection(1);
	      }
	      if(mOnScrollListener!=null){
	          mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
	      }
	  }

	  @Override
	  public void onScrollStateChanged(AbsListView view, int scrollState) {
	      mCurrentScrollState=scrollState;
	      if(mCurrentScrollState==SCROLL_STATE_IDLE){
	          mBounceHack=false;
	      }
	      if(mOnScrollListener!=null){
	          mOnScrollListener.onScrollStateChanged(view, scrollState);
	      }
	  }

	  private void applyHeaderPadding(MotionEvent ev) {
	      int pointerCount=ev.getHistorySize();
	      for(int p=0;p<pointerCount;p++){
	          if(mRefreshState==RELEASE_TO_REFRESH){
	              if(isVerticalFadingEdgeEnabled()){
	                  setVerticalScrollBarEnabled(false);
	              }
	              int historicalY=(int)ev.getHistoricalY(p);
	              int topPadding=(int)(((historicalY-mLastMotionY)-mRefreshViewHeight)/5.0);
	              mRefreshView.setPadding(mRefreshView.getPaddingLeft(),
	              topPadding, mRefreshView.getPaddingRight(),
	              mRefreshView.getPaddingBottom());
	          }
	      }
	  }

	  private void resetHeaderPadding() {
	      mRefreshView.setPadding(mRefreshView.getPaddingLeft(),
	      mRefreshOriginalTopPadding, mRefreshView.getPaddingRight(),
	      mRefreshView.getPaddingBottom());
	  }

	  private void resetHeader() {
	      if(mRefreshState!=TAP_TO_REFRESH){
	          mRefreshState=TAP_TO_REFRESH;
	          resetHeaderPadding();
	          mRefreshViewText.setText(R.string.pull_to_refresh_tap_label);
	          mRefreshViewImage.setImageResource(R.drawable.pulltorefresh_down_arrow);
	          mRefreshViewImage.clearAnimation();
	          mRefreshViewImage.setVisibility(View.GONE);
	          mRefreshViewProgress.setVisibility(View.GONE);
	      }
	  }

	  private void measureView(View child) {
	      ViewGroup.LayoutParams p=child.getLayoutParams();
	      if(p==null){
	          p=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
	          ViewGroup.LayoutParams.WRAP_CONTENT);
	      }
	      int childWidthSpec=ViewGroup.getChildMeasureSpec(0, 0+0, p.width);
	      int lpHeight=p.height;
	      int childHeightSpec;
	      if(lpHeight>0){
	          childHeightSpec=MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
	      }else{
	          childHeightSpec=MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
	      }
	      child.measure(childWidthSpec, childHeightSpec);
	  }

	  public void prepareForRefresh() {
	      resetHeaderPadding();
	      mRefreshViewImage.setVisibility(View.GONE);
	      mRefreshViewImage.setImageDrawable(null);
	      mRefreshViewProgress.setVisibility(View.VISIBLE);
	      mRefreshViewText.setText(R.string.pull_to_refresh_refreshing_label);
	      mRefreshState=REFRESHING;
	  }

	  public void onRefresh() {
	      if(mOnRefreshListener!=null){
	          mOnRefreshListener.onRefresh();
	      }
	  }

	  public void onRefreshComplete(CharSequence lastUpdated) {
	      setLastUpdated(lastUpdated);
	      onRefreshComplete();
	  }

	  public void onRefreshComplete() {
	      resetHeader();
	      if(mRefreshView.getBottom()>0){
	          invalidateViews();
	          setSelection(1);
	      }
	  }

	  private class OnClickRefreshListener implements OnClickListener {

	      @Override
	      public void onClick(View v) {
	          if(mRefreshState!=REFRESHING){
	              prepareForRefresh();
	              onRefresh();
	          }
	      }
	  }
	  
	  public interface OnRefreshListener {
	      public void onRefresh();
	  }

	  public void onMore() {
	      if(mOnMoreListener!=null){
	          mOnMoreListener.onMore();
	      }
	  }

	  public interface OnMoreListener {
	      public void onMore();
	  }
}
