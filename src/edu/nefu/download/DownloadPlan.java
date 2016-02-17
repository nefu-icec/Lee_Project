package edu.nefu.download;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import edu.nefu.XMLTool.DomTool;
import edu.nefu.database.DBMannager;
import edu.nefu.database.Task;
import edu.nefu.main.R;
/**
 * 下载采伐计划的模块
 * @author WJH
 *
 */
public class DownloadPlan extends Activity{
	
	private DBMannager dbMannager=null;
	public static final int finish=0;
	public static final int error=1;
	private ArrayList<Task> lists=null;
	private Intent intent=null;
	
	String wjh="";
	
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what){
			case finish:
				dbMannager.closeDB();
				intent=new Intent();
				intent.putExtra("result", "0");
				intent.putExtra("wjh", wjh);
				setResult(RESULT_OK, intent);
				finish();
				break;
			case error:
				intent=new Intent();
				intent.putExtra("result", "-1");
				setResult(RESULT_OK, intent);
				dbMannager.closeDB();
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
		downloadplan(intent.getStringExtra("serverurl"));
	};
	
	public void downloadplan(final String serverurl) {
		// TODO Auto-generated constructor stub
		dbMannager=new DBMannager(DownloadPlan.this);
		new Thread(){
			public void run() {
				try {
					String str=sendHttpClientPOSTRequest(serverurl, null, "utf-8");
					wjh=str;
					lists=DomTool.getParseResult_Task(str);
					for(int i=0;i<lists.size();i++){
						String sql="SELECT * FROM task WHERE LBH='"+lists.get(i).lbh+"' and XBH='"+lists.get(i).xbh+"' and state='"+String.valueOf(-1)+"'";
						ArrayList<Task> t=dbMannager.query_task(sql);
						if(t.size()==0){
							dbMannager.add_task(lists.get(i));
						}
					}
					handler.sendEmptyMessage(finish);	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					handler.sendEmptyMessage(error);
				}
			};
		}.start();
	}
	
	public String sendHttpClientPOSTRequest(String uploadurl, Map<String, String> params, String encoding) throws Exception{
        List<NameValuePair> param=new ArrayList<NameValuePair>();
		if(params!=null&&!params.isEmpty()){
		    for(Map.Entry<String, String> entry:params.entrySet()){
		        param.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		    }
		}
		
//		UrlEncodedFormEntity entity=new UrlEncodedFormEntity(param, encoding);
//		HttpPost post=new HttpPost(uploadurl);
//		post.setEntity(entity);
		HttpGet get=new HttpGet(uploadurl);
		
		DefaultHttpClient client=new DefaultHttpClient();
		
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000); 
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
		
//		HttpResponse response=client.execute(post);
		HttpResponse response=client.execute(get);
		if(response.getStatusLine().getStatusCode()==200){
			BufferedReader reader=new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuilder sb=new StringBuilder();
			for(String s=reader.readLine();s!=null;s=reader.readLine()){
				sb.append(s);
			}
		    return sb.toString();
		}
		return "";  
	}

}
