package edu.nefu.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
/**
 * 问题反馈模块
 * @author WJH
 *
 */
public class Problem extends Activity{
	
	public static final int finish=0;
	private static Dialog waitingDialog;
	private EditText problem_content;
	private EditText problem_contact;
	private Button problem_submit;
	private Button problem_cancel;
	AlertDialog.Builder builder;
	private String serverurl="";//问题反馈的URL
	
	private static Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what){
			case finish:
				waitingDialog.dismiss();
				break;
			default:break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.problem);
		initView();
		Intent intent=getIntent();
		serverurl=intent.getStringExtra("serverurl");
		builder=new AlertDialog.Builder(Problem.this);
		builder.setTitle("上传中...");
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				waitingDialog.dismiss();
			}
		});
	}
	
	public void submit(String contact, String content){
		final Map<String, String> params=new HashMap<String, String>();
		params.put("contact", contact); 
		params.put("content", content); 
		waitingDialog=builder.create();
		waitingDialog.show();
		new Thread(){
			public void run() {
				try {
					sendHttpClientPOSTRequest(serverurl, params, "utf-8");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				handler.sendEmptyMessage(finish);
			};
		}.start();	
	}
	
	public void initView(){
		problem_content=(EditText)findViewById(R.id.problem_content);
		problem_contact=(EditText)findViewById(R.id.problem_contact);
		problem_submit=(Button)findViewById(R.id.problem_submit);
		problem_cancel=(Button)findViewById(R.id.problem_cancel);
		
		problem_submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String content=problem_content.getText().toString();
				String contact=problem_contact.getText().toString();
				if(!contact.trim().equals("")&&!content.trim().equals("")){
					submit(contact, content);
				}
			}
		});
		problem_cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	public boolean sendHttpClientPOSTRequest(String uploadurl, Map<String, String> params, String encoding) throws Exception{
        List<NameValuePair> param=new ArrayList<NameValuePair>();
		if(params!=null&&!params.isEmpty()){
		    for(Map.Entry<String, String> entry:params.entrySet()){
		        param.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		    }
		}
		UrlEncodedFormEntity entity=new UrlEncodedFormEntity(param, encoding);
		HttpPost post=new HttpPost(uploadurl);
		post.setEntity(entity);
		DefaultHttpClient client=new DefaultHttpClient();
		HttpResponse response=client.execute(post);
		if(response.getStatusLine().getStatusCode()==200){
		    return true;
		}
		return false;  
	}

}
