package edu.nefu.download;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import edu.nefu.XMLTool.DomTool;
import edu.nefu.constant.Constant;
import edu.nefu.main.R;
/**
 * 更新应用程序的模块
 * @author WJH
 *
 */
public class UpdateTool{
	
	private Context mContext;
	
	private static String savePath;//保存用的文件夹名
    private static String saveFileName;//保存apk的路径
    
    private String TestUrl=Constant.testurl;//判断是否需要更新所用的路径
    private String apkUrl="";//下载资源所在的路径
	private String updateMsg="亲，有最新的软件包哦~";
	private String nNeedupdate="您使用的是最新版本~";
	
	private Dialog noticeDialog;
	private Dialog downloadDialog;
	private Dialog waitDialog;
	
	public static final int isupdate=1;
	public static final int isfinish=2;
	public static final int requset=3;
	
	public Thread downLoadThread;
	public Thread testThread;
	private ProgressBar mProgress;
	private int progress;
	private boolean interceptFlag=false;
	private boolean intertest=false;
	
	private String[] res=null;
	
	private Handler mHandler = new Handler(){  
        public void handleMessage(Message msg) {  
            switch (msg.what) {  
            case isupdate:  
                mProgress.setProgress(progress);  
                break;  
            case isfinish:  
                installApk();  
                break;  
            case requset:
            	waitDialog.dismiss();
            	if(!intertest){
            		if(isNeedUpdate()){
            			showNoticeDialog();
            		}
            		else{
            			Toast.makeText(mContext, nNeedupdate, Toast.LENGTH_SHORT).show();
            		}
            	}
            	break;
            default:  
                break;  
            }  
        };  
    };  
	
	public UpdateTool(Context context) {
		// TODO Auto-generated constructor stub
		this.mContext=context;
		if(Constant.ishasSD){
			savePath=Environment.getExternalStorageDirectory().getPath()+Constant.download_folder;
		}
		else{
			savePath=Constant.default_display_folder+"/download";
		}
		saveFileName=savePath+Constant.apk;
		File file=new File(savePath);
		if(!file.exists()){
			file.mkdirs();
		}
	}
	
	public void TestUpdate(){
		String xml=null;
		HttpGet get=new HttpGet(TestUrl);
		DefaultHttpClient client=new DefaultHttpClient();
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000); 
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
		try {
			HttpResponse response=client.execute(get);
			if(response.getStatusLine().getStatusCode()==200){
				BufferedReader reader=new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				StringBuilder sb=new StringBuilder();
				for(String s=reader.readLine();s!=null;s=reader.readLine()){
					sb.append(s);
				}
			    xml=sb.toString();
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(xml!=null){
			res=DomTool.getParseResult_Update(xml);
			if(res==null){
				res=new String[]{"-1","-1"};
			}
		}
		else{
			res=new String[]{"-1","-1"};
		}
	}
	
	public boolean isNeedUpdate(){
		int versionCode=0;
	    try{
	        versionCode=mContext.getPackageManager().getPackageInfo("edu.nefu.main", 0).versionCode;
	    }catch(NameNotFoundException e){
	        e.printStackTrace();
	    }
	    int newVersion=Integer.parseInt(res[0]);
	    if(newVersion>versionCode){
	    	apkUrl=res[1];
	    	return true;
	    }
		return false;
	}
	
	public void checkupdateInfo(){
		AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
		builder.setTitle("检查更新中");
		builder.setNegativeButton("取消", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				waitDialog.dismiss();
			}
		});
		waitDialog=builder.create();
		waitDialog.show();
		new Thread(){
			public void run() {
				TestUpdate();
				mHandler.sendEmptyMessage(requset);
			};
		}.start();
    }
	
	public void showNoticeDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);  
	    builder.setTitle("软件版本更新");  
	    builder.setMessage(updateMsg);  
	    builder.setPositiveButton("下载", new OnClickListener() {           
	        @Override  
	        public void onClick(DialogInterface dialog, int which) {  
	            dialog.dismiss();  
	            showDownloadDialog();             
	        }  
	        });  
	    builder.setNegativeButton("以后再说", new OnClickListener() {             
	        @Override  
	        public void onClick(DialogInterface dialog, int which) {  
	            dialog.dismiss();                 
	        }  
	    });  
	    noticeDialog=builder.create();  
	    noticeDialog.show();  
	}
	
    public void showDownloadDialog(){
    	AlertDialog.Builder builder=new AlertDialog.Builder(mContext);  
        builder.setTitle("软件版本更新");  
        final LayoutInflater inflater=LayoutInflater.from(mContext);  
        View v=inflater.inflate(R.layout.progress, null);
        mProgress=(ProgressBar)v.findViewById(R.id.progress);  
          
        builder.setView(v);  
        builder.setNegativeButton("取消", new OnClickListener() {   
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                interceptFlag=true;  
            }  
        });  
        downloadDialog=builder.create();  
        downloadDialog.show();  
        downloadApk();
    }
    
    private Runnable mdownApkRunnable=new Runnable() {      
        @Override  
        public void run(){  
            try{  
                URL url=new URL(apkUrl);  
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();  
                conn.setConnectTimeout(3000);
                conn.connect();  
                int length=conn.getContentLength();  
                InputStream is=conn.getInputStream();  
                  
                File file=new File(savePath);  
                if(!file.exists()){  
                    file.mkdir();  
                }  
                String apkFile=saveFileName;  
                File ApkFile=new File(apkFile);  
                FileOutputStream fos=new FileOutputStream(ApkFile);  
                  
                int count=0;  
                byte buf[]=new byte[1024];  
                  
                do{                   
                    int numread=is.read(buf);  
                    count +=numread;  
                    progress=(int)(((float)count/length)*100);  
                    mHandler.sendEmptyMessage(isupdate);  
                    if(numread<=0){       
                        mHandler.sendEmptyMessage(isfinish);  
                        break;  
                    }  
                    fos.write(buf,0,numread);  
                }while(!interceptFlag);
                
                fos.close();  
                is.close();  
                downloadDialog.dismiss();
            }catch (MalformedURLException e) {  
                e.printStackTrace();  
            }catch(IOException e){  
                e.printStackTrace();  
            }  
        }  
    };
    
    public void downloadApk(){
    	 if(isNetWorkAvailable()){
    		 downLoadThread=new Thread(mdownApkRunnable);  
             downLoadThread.start();  
    	 }
    	 else{
    		 Toast.makeText(mContext, "请检查好网络连接再下载，哈~", Toast.LENGTH_SHORT).show();
    		 downloadDialog.dismiss();
    	 }
    }
    
    public void installApk(){
    	 File apkfile=new File(saveFileName);  
         if(!apkfile.exists()) {  
             return;  
         }      
         Intent i=new Intent(Intent.ACTION_VIEW);  
         i.setDataAndType(Uri.parse("file://"+apkfile.toString()), "application/vnd.android.package-archive");   
         mContext.startActivity(i);  
    }
    
    public boolean isNetWorkAvailable(){
    	ConnectivityManager manager=(ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
    	NetworkInfo info=manager.getActiveNetworkInfo();
    	if(info!=null){
    		if(info.isAvailable()){
    			return true;
    		}
    	}
    	return false;
    }

}
