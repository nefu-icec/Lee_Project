package edu.nefu.main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import edu.nefu.XMLTool.DomTool;
import edu.nefu.constant.Constant;
import edu.nefu.database.DBMannager;
import edu.nefu.database.History;
import edu.nefu.upload.Uploadtool;

public class Histories extends Activity{
	
	DBMannager dbMannager;
	ArrayList<History> lists=null;
	
	private ListView listView_histories=null;
	private Button time=null;
	private Button region=null;
	private Button tree=null;
	private Button lbxb=null;
	private Button clearall=null;
	private Button submit_info=null;
	private TextView noneitem_prompt=null;
	private static History_Adapter adapter=null;
	String currentcmd="";
	
	private String uploadurl=Constant.uploadurl;//上传路径
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.histories);
		dbMannager=new DBMannager(Histories.this);
		initView();
	}
	
	public void initView(){
		noneitem_prompt=(TextView)findViewById(R.id.noneitem_prompt);
		listView_histories=(ListView)findViewById(R.id.listview_histories);
		time=(Button)findViewById(R.id.find_by_time);
		region=(Button)findViewById(R.id.find_by_region);
		tree=(Button)findViewById(R.id.find_by_tree);
		lbxb=(Button)findViewById(R.id.find_by_lbxb);
		clearall=(Button)findViewById(R.id.clearall);
		submit_info=(Button)findViewById(R.id.submit_info);
		
		currentcmd=Constant.select_all;
        refreshadapter(Constant.select_all);
        
		listView_histories.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				
				return true;
			}
		});
		
		submit_info.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    String xml=DomTool.generateXML(lists);
			    Intent intent=new Intent(Histories.this, Uploadtool.class);
			    intent.putExtra("uploadurl", uploadurl);
			    intent.putExtra("xml", xml);
			    startActivityForResult(intent, 3);
			}
		});
		
		clearall.setOnClickListener(new  OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder=new AlertDialog.Builder(Histories.this);
				builder.setTitle("警告").setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dbMannager.delete_allhistory();
						currentcmd=Constant.select_all;
						refreshadapter(Constant.select_all);
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				}).setMessage("确定要清空？这可能会导致您的已录入数据丢失");
				Dialog dialog=builder.create();
				dialog.show();
			}
		});
		time.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar calendar=Calendar.getInstance(Locale.CHINA);
				Date date=new Date();
				calendar.setTime(date);
				int year=calendar.get(Calendar.YEAR);
				int month=calendar.get(Calendar.MONTH);
				int day=calendar.get(Calendar.DAY_OF_MONTH);
				DatePickerDialog datePickerDialog=new DatePickerDialog(Histories.this, new OnDateSetListener() {
					
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear,
							int dayOfMonth) {
						// TODO Auto-generated method stub
						String riqi=String.valueOf(year)+"年"+String.valueOf(monthOfYear+1)+"月"+String.valueOf(dayOfMonth)+"日";
						currentcmd=Constant.orderbyriqi+riqi+"'";
						refreshadapter(Constant.orderbyriqi+riqi+"'");
					}
				}, year, month, day);
				datePickerDialog.show();
			}
		});
		region.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivityForResult(new Intent(Histories.this, SearchR.class), 0);
			}
		});
		tree.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivityForResult(new Intent(Histories.this, SearchT.class), 1);
			}
		});
		lbxb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivityForResult(new Intent(Histories.this, SearchLX.class), 2);
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		dbMannager.closeDB();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch(requestCode){
		case 0:
			if(resultCode==RESULT_OK){
				String region=data.getStringExtra("region");
				currentcmd=Constant.orderbyregion+region+"'";
				refreshadapter(Constant.orderbyregion+region+"'");
			}
			break;
		case 1:
			if(resultCode==RESULT_OK){
				String tree=data.getStringExtra("tree");
				currentcmd=Constant.orderbytree+tree+"'";
				refreshadapter(Constant.orderbytree+tree+"'");
			}
			break;
		case 2:
			if(resultCode==RESULT_OK){
			    String sql="SELECT * FROM history WHERE LBH='"+data.getStringExtra("lbh")+"' and XBH='"+data.getStringExtra("xbh")+"'";
			    currentcmd=sql;
				refreshadapter(sql);
			}
			break;
		case 3:
			if(resultCode==RESULT_OK){
				int res=Integer.parseInt(data.getStringExtra("result"));
				if(res==-1){
					Toast.makeText(Histories.this, "上传失败", Toast.LENGTH_SHORT).show();
				}
				else if(res==0){
					for(int i=0;i<lists.size();i++){
						dbMannager.delete_history("LBH=? and XBH=?", new String[]{String.valueOf(lists.get(i).lbh),String.valueOf(lists.get(i).xbh)});
					}
					refreshadapter(Constant.select_all);
				}
			}
			else if(resultCode==RESULT_CANCELED){
				Toast.makeText(Histories.this, "上传取消", Toast.LENGTH_SHORT).show();
			}
			break;
		default:break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	public void refreshadapter(String sql){
		 lists=dbMannager.query_history(sql);
		 if(lists.size()==0){
			 noneitem_prompt.setVisibility(View.VISIBLE);
			 listView_histories.setVisibility(View.GONE);
		 }
		 else{
			 noneitem_prompt.setVisibility(View.GONE);
			 adapter=new History_Adapter(Histories.this, lists);
		     listView_histories.setAdapter(adapter); 
		     listView_histories.setVisibility(View.VISIBLE);
		 }
	}
	
	class History_Adapter extends BaseAdapter{

		private ArrayList<History> lists;
		Context context;
		
		public History_Adapter(Context context, ArrayList<History> lists) {
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
			if(arg1==null){
				arg1=LayoutInflater.from(context).inflate(R.layout.histories_item, null);
				TextView id=(TextView)arg1.findViewById(R.id.text_id);
				id.setText(lists.get(arg0).id);
				TextView time=(TextView)arg1.findViewById(R.id.text_time);
				time.setText(lists.get(arg0).riqi);
				TextView region=(TextView)arg1.findViewById(R.id.text_region);
				region.setText(lists.get(arg0).region);
				TextView tree=(TextView)arg1.findViewById(R.id.text_tree);
				tree.setText(lists.get(arg0).tree);
				TextView db=(TextView)arg1.findViewById(R.id.text_db);
				db.setText(String.valueOf(lists.get(arg0).db));
				TextView dm=(TextView)arg1.findViewById(R.id.text_dm);
				dm.setText(String.valueOf(lists.get(arg0).dm));
				TextView dw=(TextView)arg1.findViewById(R.id.text_dw);
				dw.setText(String.valueOf(lists.get(arg0).dw));
				TextView de=(TextView)arg1.findViewById(R.id.text_de);
				de.setText(String.valueOf(lists.get(arg0).de));
				TextView ar=(TextView)arg1.findViewById(R.id.text_ar);
				ar.setText(String.valueOf(lists.get(arg0).ar));
				TextView lb=(TextView)arg1.findViewById(R.id.text_lb);
				lb.setText(String.valueOf(lists.get(arg0).lbh));
				TextView xb=(TextView)arg1.findViewById(R.id.text_xb);
				xb.setText(String.valueOf(lists.get(arg0).xbh));
				if(arg0%4==1){
					arg1.setBackgroundColor(Color.GREEN);
				}
				else if(arg0%4==2){
					arg1.setBackgroundColor(Color.LTGRAY);
				}
				else if(arg0%4==3){
					arg1.setBackgroundColor(Color.BLUE);
				}
				else if(arg0%4==0){
					arg1.setBackgroundColor(Color.YELLOW);
				}
			}
			return arg1;
		}
		
	}

}
