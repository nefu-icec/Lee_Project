package edu.nefu.upload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import org.apache.http.params.CoreConnectionPNames;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import edu.nefu.constant.Constant;
import edu.nefu.database.DBMannager;
import edu.nefu.main.R;
/**
 * 上传数据的模块
 * @author WJH
 *
 */
public class Uploadtool extends Activity{
	
	public static final int finish=0;
	private String xml="";
	private String uploadurl="";
	DBMannager dbMannager;
	private Button cancelupload=null;
	private Intent intent=null;
	
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what){
			case finish:
				setResult(RESULT_OK, intent);
				finish();
				break;
			default:break;
			}
		}
	};
	
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uploadtool);
		intent=getIntent();
		this.uploadurl=intent.getStringExtra("uploadurl");
		this.xml=intent.getStringExtra("xml");
		cancelupload=(Button)findViewById(R.id.upload_cancel);
		cancelupload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    setResult(RESULT_CANCELED);
				finish();
			}
		});
		upload(xml);
		writefile();
	};
	
	public static final String resultfilename="/result.xml";
	
	public void writefile(){
		File file=new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+Constant.display_folder+resultfilename);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			file.delete();
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {	
	        FileOutputStream fos=new FileOutputStream(file); 
	        byte []bytes=xml.getBytes();
	        fos.write(bytes);
	        fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	public void upload(String xml){
		this.xml=xml;
		byte buffer[];
		buffer=xml.getBytes();
		if(buffer!=null){
			final Map<String, String> params=new HashMap<String, String>();
			params.put("xml", new String(buffer)); 
			new Thread(){
				public void run() {
					try {
						boolean flag=sendHttpClientPOSTRequest(uploadurl, params, "utf-8");
						if(!flag){
							intent=new Intent();
							intent.putExtra("result", "-1");
							handler.sendEmptyMessage(finish);
						}
						else{
							intent=new Intent();
							intent.putExtra("result", "0");
							handler.sendEmptyMessage(finish);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						intent=new Intent();
						intent.putExtra("result", "-1");
						handler.sendEmptyMessage(finish);
					}	
				};
			}.start();	
		}
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
		
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000); 
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);
		
		HttpResponse response=client.execute(post);
		if(response.getStatusLine().getStatusCode()==200){
		    return true;
		}
		return false;  
	}

}
