package edu.nefu.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import edu.nefu.constant.Constant;
import edu.nefu.database.DBMannager;
import edu.nefu.database.Task;

public class IP_Config extends Activity{
	
	private Button ip_ok=null;
	private Button ip_cancel=null;
	private EditText ip1=null;
	private EditText ip2=null;
	private EditText ip3=null;
	private EditText ip4=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ipconfig);
		
		Task task=new Task("1", "2013-9-8", "全省通用", "红松", 40, 9, -1);
		DBMannager dm=new DBMannager(IP_Config.this);
		dm.add_task(task);
		task=new Task("2", "2013-9-8", "全省通用", "赤松", 39, 14, -1);
		dm.add_task(task);
		
		ip_ok=(Button)findViewById(R.id.ip_ok);
		ip_cancel=(Button)findViewById(R.id.ip_cancel);
		ip1=(EditText)findViewById(R.id.ip1);
		ip2=(EditText)findViewById(R.id.ip2);
		ip3=(EditText)findViewById(R.id.ip3);
		ip4=(EditText)findViewById(R.id.ip4);
		ip_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Constant.url=ip1.getText().toString()+"."+ip2.getText().toString()+"."+ip3.getText().toString()+"."+ip4.getText().toString();
				Constant.serverurl="http://"+Constant.url+":8080/DeforestationManagement/AndroidServiceServlet?task=update";
				Constant.uploadurl="http://"+Constant.url+":8080/DeforestationManagement/AndroidServiceServlet?task=postCheckData";
				Constant.testurl="http://"+Constant.url+":8080/DeforestationManagement/AndroidServiceServlet?task=update";
				Constant.problem="http://"+Constant.url+":8080/DeforestationManagement/AndroidServiceServlet?task=problem";
				Toast.makeText(getBaseContext(), Constant.uploadurl, Toast.LENGTH_SHORT).show();
			}
		});
		ip_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

}
