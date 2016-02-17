package edu.nefu.main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import edu.nefu.constant.Constant;
import edu.nefu.database.DBMannager;
import edu.nefu.database.Task;
import edu.nefu.download.DownloadPlan;
import edu.nefu.main.MyListView.OnRefreshListener;

public class MyTask extends Activity {
	
	private MyListView task_list=null;
	private Button update_task=null;
	private Button exit_task=null;
	private static final int group=15;
	private int currentptr=-1;
	static DBMannager dbMannager=null;
	private ArrayList<Task> lists=null;
	private static MyTask_Adapter adapter=null;
	private String serverurl=Constant.serverurl;//取得任务的URL
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mytask);
		initView();
	}
	
	public void initView(){
		lists=new ArrayList<Task>();
		dbMannager=new DBMannager(MyTask.this);
		task_list=(MyListView)findViewById(R.id.task_list);
		update_task=(Button)findViewById(R.id.update_task);
		exit_task=(Button)findViewById(R.id.exit_task);
		
		update_task.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MyTask.this, DownloadPlan.class);
				intent.putExtra("serverurl", serverurl);
				startActivityForResult(intent, 0);
			}
		});
		exit_task.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	    refreshadapter();
		task_list.setAdapter(adapter);
		task_list.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				task_list.onRefreshComplete("更新时间："+getCalendarString());
			}
		});
//		task_list.setOnMoreListener(new OnMoreListener() {
//			
//			@Override
//			public void onMore() {
//				// TODO Auto-generated method stub
//				refreshadapter();
//				task_list.setAdapter(adapter);
//				task_list.onMoreComplete();
//			}
//		});
		task_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode){
		case 0:
			if(resultCode==RESULT_OK){
				int res=Integer.parseInt(data.getStringExtra("result"));
				if(res==-1){
					Toast.makeText(MyTask.this, "下载失败", Toast.LENGTH_SHORT).show();
				}
				else if(res==0){
					refreshadapter();
					task_list.setAdapter(adapter);
				}
			}
			break;
		default:break;
		}
	}
	
	public String getCalendarString() {
		String mHour_S="";
		String mMinter_S="";
	    Calendar c=Calendar.getInstance(Locale.CHINA);
	    int mHour=c.get(Calendar.HOUR_OF_DAY);
	    int mMinter=c.get(Calendar.MINUTE);
	    mHour_S=String.valueOf(mHour);
	    mMinter_S=String.valueOf(mMinter);
	    if(mHour<10){
	    	mHour_S="0"+mHour_S;
	    }
	    if(mMinter<10){
	    	mMinter_S="0"+mMinter_S;
	    }
	    return mHour_S+":"+mMinter_S;
    }
	
	public void refreshadapter(){
		ArrayList<Task> t=dbMannager.query_task(Constant.select_all_task, currentptr, group);
		for(int i=0;i<t.size();i++){
			lists.add(t.get(i));
		}
		currentptr=currentptr+lists.size();
		task_list.setSelection(currentptr);
	    adapter=new MyTask_Adapter(MyTask.this, lists);
	}
	
	class MyTask_Adapter extends BaseAdapter{

		private ArrayList<Task> lists;
		Context context;
		
		public MyTask_Adapter(Context context, ArrayList<Task> lists) {
			// TODO Auto-generated constructor stub
			this.lists=lists;
			this.context=context;
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
			arg1=LayoutInflater.from(context).inflate(R.layout.mytask_item, null);
			TextView id=(TextView)arg1.findViewById(R.id.text_id_task);
			id.setText(lists.get(arg0).id);
			TextView time=(TextView)arg1.findViewById(R.id.text_time_task);
			time.setText(lists.get(arg0).riqi);
			TextView region=(TextView)arg1.findViewById(R.id.text_region_task);
			region.setText(lists.get(arg0).region);
			TextView tree=(TextView)arg1.findViewById(R.id.text_tree_task);
			tree.setText(lists.get(arg0).tree);
			TextView lb=(TextView)arg1.findViewById(R.id.text_lb_task);
			lb.setText(String.valueOf(lists.get(arg0).lbh));
			TextView xb=(TextView)arg1.findViewById(R.id.text_xb_task);
			xb.setText(String.valueOf(lists.get(arg0).xbh));
			TextView st=(TextView)arg1.findViewById(R.id.text_state_task);
			st.setText(String.valueOf(lists.get(arg0).state));
			if(arg0%3==1){
				arg1.setBackgroundColor(Color.GREEN);
			}
			else if(arg0%3==2){
				arg1.setBackgroundColor(Color.LTGRAY);
			}
			else if(arg0%3==0){
				arg1.setBackgroundColor(Color.BLUE);
			}
			return arg1;
		}
		
	}

}
