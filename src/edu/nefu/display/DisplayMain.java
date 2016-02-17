package edu.nefu.display;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import cn.creable.gridgis.controls.CustomDrawGeometrySelector;
import cn.creable.gridgis.controls.ICustomDraw;
import cn.creable.gridgis.controls.ICustomDrawDataCenter;
import cn.creable.gridgis.display.FillSymbol;
import cn.creable.gridgis.display.ISymbol;
import cn.creable.gridgis.display.LineSymbol;
import cn.creable.gridgis.display.SimpleRenderer;
import cn.creable.gridgis.display.UniqueValueRenderer;
import cn.creable.gridgis.geodatabase.IFeature;
import cn.creable.gridgis.mapLayer.IFeatureLayer;
import cn.creable.gridgis.mapLayer.ILayer;
import cn.creable.gridgis.shapefile.EditFeatureAttTool;
import cn.creable.gridgis.shapefile.IEditListener;
import cn.creable.gridgis.shapefile.IEditTool;
import cn.creable.gridgis.shapefile.ShapefileLayer;
import edu.nefu.XMLTool.TxtParser;
import edu.nefu.constant.Constant;
import edu.nefu.database.DBMannager;
import edu.nefu.database.History;
import edu.nefu.database.Model;
import edu.nefu.database.Task;
import edu.nefu.main.R;
/**
 * 实现对地图的数据操作
 * @author WJH
 *
 */
public class DisplayMain extends Activity implements IEditListener{
	
	private MyMapView mapView=null;
	private DBMannager mannager;
	
	private IEditTool editTool;
	private IFeature ft;
	
	public String region="";
	public String tree="";
	public double calV=0;
	public int db=0;
	public int dm=0;
	public int dw=0;
	public int de=0;
	public int ar=0;
	private int lbh;
	private int xbh;
	public int currentcolor=0xff000000;
	
	private Button move=null;
	private Button update=null;
	private Button changecolor=null;
	private Button gps=null;
	private Button dismodel=null;
	private LayoutInflater inflater=null;
	private View view=null;
	private PopupWindow pop=null;
	
	public static boolean flag=true;
	private static final int finish=0;
	
	private String id="";
	
	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
		    switch(msg.what){
		    case finish:
		    	getCurrentColor();
		    	flag=true;
		    	break;
		    default:break;
		    }	
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_main);
		mannager=new DBMannager(this);
		
		inflater=LayoutInflater.from(this);
		view=inflater.inflate(R.layout.dismodel, null);
		
		TextView time=(TextView)view.findViewById(R.id.distimet);
		TextView temp=(TextView)view.findViewById(R.id.distempt);
		TextView humi=(TextView)view.findViewById(R.id.dishumit);
		TextView light=(TextView)view.findViewById(R.id.dislightt);
		TextView tentemp=(TextView)view.findViewById(R.id.distentempt);
		TextView twetemp=(TextView)view.findViewById(R.id.distwetempt);
		TextView tenhumi=(TextView)view.findViewById(R.id.distenhumit);
		TextView twehumi=(TextView)view.findViewById(R.id.distwehumit);
		TextView co2=(TextView)view.findViewById(R.id.disco2t);
		TextView rain=(TextView)view.findViewById(R.id.disraint);
		
		TxtParser.setPath(Environment.getExternalStorageDirectory().getAbsolutePath()+Constant.display_folder+"/data.txt");
		
		Model model=TxtParser.parser();
		
		mannager.add_model(model);
		
		model=mannager.getmodel();
		
		time.setText(model.time.toString());
		temp.setText(String.valueOf(model.temp));
		humi.setText(String.valueOf(model.humi));
		light.setText(String.valueOf(model.light));
		tentemp.setText(String.valueOf(model.tentemp));
		twetemp.setText(String.valueOf(model.twetemp));
		tenhumi.setText(String.valueOf(model.tenhumi));
		twehumi.setText(String.valueOf(model.twehumi));
		co2.setText(String.valueOf(model.co2));
		rain.setText(String.valueOf(model.rain));
		
        pop=new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, false); 
        pop.setOutsideTouchable(false); 
        //pop.setFocusable(true); 
        
		new Thread(){
			public void run() {
				while(flag){
					try {
						Thread.sleep(1);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				handler.sendEmptyMessage(finish);
			};
		}.start();
		initView();
	}
	
	public void initView(){
		mapView=(MyMapView)findViewById(R.id.display_map);
		mannager=new DBMannager(DisplayMain.this);
		move=(Button)findViewById(R.id.display_button_movestate);
		update=(Button)findViewById(R.id.display_button_updatestate);
		gps=(Button)findViewById(R.id.display_button_gps);
		changecolor=(Button)findViewById(R.id.display_button_changecolor);
		
		dismodel=(Button)findViewById(R.id.dismodel);
		dismodel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		        if(pop.isShowing()) { 
                    pop.dismiss(); 
                } else { 
                    pop.showAsDropDown(v); 
                } 
			}
		});
		move.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mapView.getMapControl().setPanTool();
			}
		});
		update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditFeatureAttTool tool=new EditFeatureAttTool(mapView.getMapControl());
				tool.selector.setOffset(0, 10);
				editTool=tool;
				editTool.setListener(DisplayMain.this);
				mapView.getMapControl().setCurrentTool(tool);
			}
		});
		changecolor.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(DisplayMain.this, Color.class);
				startActivityForResult(intent, 1);
			}
		});
		gps.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ICustomDraw draw=mapView.getMapControl().getCustomDraw();
				if(draw!=null&&draw instanceof ICustomDrawDataCenter)
				{
					CustomDrawGeometrySelector s=new CustomDrawGeometrySelector(mapView.getMapControl(),(ICustomDrawDataCenter)draw);
					s.setOffset(0, 20);
					mapView.getMapControl().setCurrentTool(s);
				}
			}
		});
	}
	
	public void changeColor(){
		ShapefileLayer lyr=(ShapefileLayer)mapView.getMapControl().getMap().getLayer(0);
		SimpleRenderer renderer=new SimpleRenderer(null, null, new FillSymbol(currentcolor, new LineSymbol(1,0xffffffff)));
		lyr.setRenderer(renderer);
		mapView.getMapControl().refresh();
		//mapView.getMapControl().refreshAfter(0);
		mannager.changecolor(currentcolor);
		setRender();
	}
	
	public void getCurrentColor(){
		currentcolor=mannager.getColor();
		changeColor();
		setRender();
	}

	public void setRender(){
		String sql_notcomplete="SELECT * FROM task WHERE state='"+String.valueOf(-1)+"'";
		String sql_completed="SELECT * FROM task WHERE state='"+String.valueOf(0)+"'";
		ArrayList<Task> ntasks=mannager.query_task(sql_notcomplete);
		ArrayList<Task> ctasks=mannager.query_task(sql_completed);
		for(int k=0;k<ctasks.size();k++){
			ArrayList<int[]>res=new ArrayList<int[]>();
			res.add(new int[]{ctasks.get(k).lbh,ctasks.get(k).xbh});
			ShapefileLayer lyr=(ShapefileLayer)mapView.getMapControl().getMap().getLayer(0);
			ISymbol symbol=lyr.getRenderer().getSymbolByFeature(null);
			UniqueValueRenderer renderer=new UniqueValueRenderer(symbol, lyr);
			for(int i=0;i<res.size();i++){
				String str=res.get(i)[0]+"_"+res.get(i)[1];
				renderer.setSymbol(str, new FillSymbol(0xff6F8391, new LineSymbol(1, currentcolor)));
				renderer.setFieldIndex(10);
				lyr.setRenderer(renderer);
			}
		}
		for(int k=0;k<ntasks.size();k++){
			ArrayList<int[]>res=new ArrayList<int[]>();
			res.add(new int[]{ntasks.get(k).lbh,ntasks.get(k).xbh});
			ShapefileLayer lyr=(ShapefileLayer)mapView.getMapControl().getMap().getLayer(0);
			ISymbol symbol=lyr.getRenderer().getSymbolByFeature(null);
			UniqueValueRenderer renderer=new UniqueValueRenderer(symbol, lyr);
			for(int i=0;i<res.size();i++){
				String str=res.get(i)[0]+"_"+res.get(i)[1];
				renderer.setSymbol(str, new FillSymbol(0xffFF00FF, new LineSymbol(1, currentcolor)));
				renderer.setFieldIndex(10);
				lyr.setRenderer(renderer);
			}
		}
		mapView.getMapControl().refresh();
		//mapView.getMapControl().refreshAfter(0);
	}

	@Override
	public void onAddFeature(IFeature arg0, ILayer arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeleteFeature(IFeature arg0, ILayer arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpdateFeature(IFeature arg0, ILayer arg1) {
		// TODO Auto-generated method stub
		this.ft=arg0;
		IFeatureLayer flayer=(IFeatureLayer)arg1;
		String[] fields=flayer.getFeatureClass().getFields();
		String[] values=ft.getValues();
		int count=fields.length;
		for(int i=0;i<count;i++){
			if(fields[i].equals("LBH")){
				lbh=Integer.parseInt(values[i]);
			}
			else if(fields[i].equals("XBH")){
				xbh=Integer.parseInt(values[i]);
			}
		}
		this.showInputActivity();
	}
	
	private void showInputActivity(){
		Intent intent=new Intent(DisplayMain.this, InputDialog.class);
		startActivityForResult(intent, 0);
	}
	
	public void closeDb(){
		mannager.closeDB();
	}
	
	public void insert(){
		String sql_history="SELECT * FROM history WHERE LBH='"+String.valueOf(lbh)+"' and XBH='"+String.valueOf(xbh)+"'";
		String sql_task="SELECT * FROM task WHERE LBH='"+String.valueOf(lbh)+"' and XBH='"+String.valueOf(xbh)+"'";
		ArrayList<History> lists=mannager.query_history(sql_history);
		if(lists.size()==0){
			String riqi="";
			Calendar calendar=Calendar.getInstance(Locale.CHINA);
			Date date=new Date();
			calendar.setTime(date);
			int year=calendar.get(Calendar.YEAR);
			int month=calendar.get(Calendar.MONTH);
			int day=calendar.get(Calendar.DAY_OF_MONTH);
			riqi=String.valueOf(year)+"年"+String.valueOf(month+1)+"月"+String.valueOf(day)+"日";
			String id="";
			ArrayList<Task> task_temp=mannager.query_task(sql_task);
			if(task_temp.size()>0){
				id=task_temp.get(0).id;
				History history=new History(id, riqi, region, tree, lbh, xbh, db, dm, dw, de, ar);
				mannager.add_history(history);
				if(mannager.query_task("SELECT * FROM task WHERE LBH='"+String.valueOf(lbh)+"' and XBH='"+String.valueOf(xbh)+"'").size()!=0){
					mannager.delete_task("LBH=? and XBH=?", new String[]{String.valueOf(lbh), String.valueOf(xbh)});
					Task task=new Task(id, riqi, region, tree, lbh, xbh, 0);
					mannager.add_task(task);
				}
				Toast.makeText(DisplayMain.this, "录入数据成功！", Toast.LENGTH_SHORT).show();
			}
			else{
				Toast.makeText(DisplayMain.this, "该区域未在验收计划之内！", Toast.LENGTH_SHORT).show();
			}
		}
		else{
			Toast.makeText(DisplayMain.this, "已经录入数据。不可更改！", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void insert_new(){
		String sql_task="SELECT * FROM task WHERE LBH='"+String.valueOf(lbh)+"' and XBH='"+String.valueOf(xbh)+"'";
		ArrayList<Task> task_temp=mannager.query_task(sql_task);
		if(task_temp.size()>0){
			LinearLayout linearLayoutMain=new LinearLayout(DisplayMain.this);
			linearLayoutMain.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			ListView listView=new ListView(DisplayMain.this);
			listView.setFadingEdgeLength(0);
			List<String> data=new ArrayList<String>();
			for(int i=0;i<task_temp.size();i++){
				data.add(task_temp.get(i).id);
			}
			listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,data));
			linearLayoutMain.addView(listView);
			AlertDialog.Builder builder=new AlertDialog.Builder(DisplayMain.this);
			builder.setTitle("选择您所需的任务ID").setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.cancel();
				}
			}).setView(linearLayoutMain);
			final Dialog dialog=builder.create();
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					id=arg0.getAdapter().getItem(arg2).toString();
					String sql_history="SELECT * FROM history WHERE LBH='"+String.valueOf(lbh)+"' and XBH='"+String.valueOf(xbh)+"' and id='"+id+"'";
					ArrayList<History> lists=mannager.query_history(sql_history);
					if(lists.size()==0){
						String riqi="";
						Calendar calendar=Calendar.getInstance(Locale.CHINA);
						Date date=new Date();
						calendar.setTime(date);
						int year=calendar.get(Calendar.YEAR);
						int month=calendar.get(Calendar.MONTH);
						int day=calendar.get(Calendar.DAY_OF_MONTH);
						riqi=String.valueOf(year)+"年"+String.valueOf(month+1)+"月"+String.valueOf(day)+"日";
						History history=new History(id, riqi, region, tree, lbh, xbh, db, dm, dw, de, ar);
						mannager.add_history(history);
						mannager.delete_task("id=? and LBH=? and XBH=?", new String[]{id, String.valueOf(lbh), String.valueOf(xbh)});
						Task task=new Task(id, riqi, region, tree, lbh, xbh, 0);
						mannager.add_task(task);
						dialog.cancel();
					}
					else{
						Toast.makeText(DisplayMain.this, "已经录入数据。不可更改！", Toast.LENGTH_SHORT).show();
					}
				}
			});
		}
		else{
			Toast.makeText(DisplayMain.this, "该区域未在验收计划之内！", Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode){
		case 0:
			if(resultCode==RESULT_CANCELED){
				Toast.makeText(DisplayMain.this, "输入已取消", Toast.LENGTH_SHORT).show();
			}
			else if(resultCode==RESULT_OK){
				region=data.getStringExtra("region");
				tree=data.getStringExtra("tree");
				calV=data.getDoubleExtra("calV", 0);
				db=data.getIntExtra("db", 0);
				dm=data.getIntExtra("dm", 0);
				dw=data.getIntExtra("dw", 0);
				de=data.getIntExtra("de", 0);
				ar=data.getIntExtra("ar", 0);
				insert_new();//改过的
			}
			break;
		case 1:
			if(resultCode==RESULT_OK){
				currentcolor=data.getIntExtra("currentcolor", 0xff63B8FF);
				changeColor();
			}
			break;
		default:break;
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		flag=true;
//		if(mapView!=null){
//			mapView.getMapControl().closeMap();
//		}
		closeDb();
	}

}
