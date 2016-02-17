package edu.nefu.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import edu.nefu.constant.Constant;
import edu.nefu.display.DisplayMain;
import edu.nefu.display.MapData;
import edu.nefu.download.UpdateTool;
/**
 * 给用户呈现的主界面的搭建
 * @author WJH
 *
 */
public class MainActivity extends Activity implements OnTouchListener,OnGestureListener{
	
	private Timer tExit=new Timer();  
	private ExitTimerTask exitTimerTask=new ExitTimerTask(); 
	
	private LinearLayout layout_left=null;
	private LinearLayout layout_right=null;
	private ListView tools=null;
	private GestureDetector detector=null;
	private Button display_tools=null;
	
	private final static int SLIDE_SPEED=30;
	private int RIGHT_WIDTH=0;
	private boolean isScrolling=false;
	private float ScrollX;
	private int window_width;
	private boolean hasMeasured=false;
	private boolean display_tools_enable=true;
	
	private Button view_map;
	private Button more_tools;
	private Button histories;
	private Button mytask;
	private int curView=0;
	
//	private int horizontalMinDistance=20;  
//	private int minVelocityX=10;  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getSDstate();
		MKfiles();
		setContentView(R.layout.activity_main);
		initView();
		getRIGHT_WIDTH();
	}
	
	public void getSDstate(){
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			Constant.ishasSD=true;
		}
	}
	
	public void MKfiles(){
		File file=null;
		if(Constant.ishasSD==true){
			file=new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+Constant.display_folder);
			if(!file.exists()){
				file.mkdirs();
			}
		}else{
			file=new File(Constant.default_display_folder); 
			if(!file.exists()){
			    file.mkdirs();
			}
		}
		MapData mapData=new MapData(MainActivity.this);
		mapData.copyToSDCard(file.getAbsolutePath());
	}
	
	public void getRIGHT_WIDTH(){
		ViewTreeObserver observer=layout_left.getViewTreeObserver();
		observer.addOnPreDrawListener(new OnPreDrawListener() {
			
			@Override
			public boolean onPreDraw() {
				// TODO Auto-generated method stub
				if(!hasMeasured){
					window_width=getWindowManager().getDefaultDisplay().getWidth();
					RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams)layout_left.getLayoutParams();
					layoutParams.width=window_width; 
					layout_left.setLayoutParams(layoutParams);
					RIGHT_WIDTH=layout_right.getWidth();
					hasMeasured=true;
				}
				return true;
			}
		});
	}
	
	public void initView(){
		layout_left=(LinearLayout)findViewById(R.id.layout_left);
		layout_right=(LinearLayout)findViewById(R.id.layout_right);
		tools=(ListView)findViewById(R.id.tools);
		display_tools=(Button)findViewById(R.id.translate_button);
		
		display_tools.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(display_tools_enable==true){
					new MyAsynTask().execute(-SLIDE_SPEED);
					display_tools_enable=false;
				}else{
					new MyAsynTask().execute(SLIDE_SPEED);
					display_tools_enable=true;
				}
			}
		});
		
		tools.setAdapter(new MyAdapter(MainActivity.this));
		tools.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				switch(arg2){
				case 0:
					UpdateTool tool=new UpdateTool(MainActivity.this);
					tool.checkupdateInfo();
					break;
				case 1:
					startActivity(new Intent(MainActivity.this, AboutActivity.class));
					break;
				case 2:
					Intent intent=new Intent(MainActivity.this, Problem.class);
					intent.putExtra("serverurl", Constant.problem);
					startActivity(intent);
					break;
				default:break;
				}
			}
			
		});
		
		layout_left.setOnTouchListener(this);
		layout_right.setOnTouchListener(this);
		
		detector=new GestureDetector(this);
		detector.setIsLongpressEnabled(false);
	    
	    view_map=(Button)findViewById(R.id.view_map);
	    more_tools=(Button)findViewById(R.id.more_tools);
	    mytask=(Button)findViewById(R.id.mytask);
	    histories=(Button)findViewById(R.id.histories);
	    
	    view_map.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this, DisplayMain.class));
			}
		});
	    more_tools.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(MainActivity.this, "更多功能正在开发中。。。", Toast.LENGTH_SHORT).show();
			    startActivity(new Intent(MainActivity.this, IP_Config.class));	
			}
		});
	    mytask.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this, MyTask.class));
			}
		});
	    histories.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this, Histories.class));
			}
		});
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		ScrollX=0;  
        isScrolling=false;  
		return true;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
//		if((e1.getX()-e2.getX())>horizontalMinDistance&&(Math.abs(velocityX)>minVelocityX)){
//			new MyAsynTask().execute(-SLIDE_SPEED);
//			display_tools_enable=false;
//		}
//		else if((e2.getX()-e1.getX()>horizontalMinDistance)&&(Math.abs(velocityX)>minVelocityX)){
//			new MyAsynTask().execute(SLIDE_SPEED);
//			display_tools_enable=true;
//		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		isScrolling=true;
		ScrollX+=distanceX;
		RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams)layout_left.getLayoutParams();
		layoutParams.leftMargin-=ScrollX;
		if(layoutParams.leftMargin>=0){
			isScrolling=false;
			layoutParams.leftMargin=0;
		}else if(layoutParams.leftMargin<=-RIGHT_WIDTH){
			isScrolling=false;
			layoutParams.leftMargin=-RIGHT_WIDTH;
		}
		layout_left.setLayoutParams(layoutParams);
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if(event.getAction()==MotionEvent.ACTION_UP&&isScrolling==true){
			RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams)layout_left.getLayoutParams();
			if(layoutParams.leftMargin<(-window_width/2)){
				new MyAsynTask().execute(-SLIDE_SPEED);
//				curView=1;
				display_tools_enable=false;
			}else{
				new MyAsynTask().execute(SLIDE_SPEED);
//				curView=0;
				display_tools_enable=true;
			}
		}
	    return detector.onTouchEvent(event);    
	}
	
	class MyAsynTask extends AsyncTask<Integer, Integer, Void>{

		@Override
		protected Void doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			int times=0;
			if(RIGHT_WIDTH%Math.abs(params[0])==0){
				times=RIGHT_WIDTH/Math.abs(params[0]);
			}else{
				times=RIGHT_WIDTH/Math.abs(params[0])+1;
			}
			for(int i=0;i<times;i++){
				publishProgress(params[0]);
				try {
					Thread.sleep(Math.abs(params[0]));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(params[0]>0){
				curView=0;
			}
			else{
				curView=1;
			}
			return null;
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams)layout_left.getLayoutParams();
			if(values[0]>0){
				layoutParams.leftMargin=Math.min(layoutParams.leftMargin+values[0], 0);
			}else{
				layoutParams.leftMargin=Math.max(layoutParams.leftMargin+values[0], -RIGHT_WIDTH);
			}
			layout_left.setLayoutParams(layoutParams);
		}
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
//		if(keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){
//			RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams)layout_left.getLayoutParams();
//			if(layoutParams.leftMargin<0){
//				new MyAsynTask().execute(SLIDE_SPEED);
//				return false;
//			}
//		}
		if(curView==0){
			if(keyCode==KeyEvent.KEYCODE_BACK){
				if (!UserHelper.getIsExit()){  
		            UserHelper.setIsExit(true);  
		            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();  
		            if(tExit!=null) {  
		                if(exitTimerTask!=null) {  
		                    exitTimerTask.cancel();  
		                }  
		                exitTimerTask=new ExitTimerTask();  
		                tExit.schedule(exitTimerTask, 2*1000);  
		            }  
		        }  
		        else {  
		            UserHelper.setIsExit(false);  
		            finish();  
		            System.exit(0);  
		        }  
			}
			return true;
		}
		else if(curView==1){
			RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams)layout_left.getLayoutParams();
			if(layoutParams.leftMargin<0){
				new MyAsynTask().execute(SLIDE_SPEED);
				display_tools_enable=true;
				return false;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	class MyAdapter extends BaseAdapter{

		ArrayList<String> lists=new ArrayList<String>();
		Context context;
		
		public MyAdapter(Context context) {
			// TODO Auto-generated constructor stub
			this.context=context;
			lists.add("检查更新");
			lists.add("关于我们");
			lists.add("问题反馈");
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return lists.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return lists.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			if(arg1==null){
				arg1=LayoutInflater.from(context).inflate(R.layout.listitem, null);
				TextView textView=(TextView)arg1.findViewById(R.id.text_item);
				textView.setText(lists.get(arg0));
			}
			return arg1;
		}
		
	}

}
