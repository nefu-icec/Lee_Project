package edu.nefu.constant;

import java.io.File;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
/**
 * 自动删除下载好的APK
 * @author WJH
 *
 */
public class MyBroadcastReceiver extends BroadcastReceiver
{
	public static final String tag="APKDelete";
	
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		// TODO Auto-generated method stub
		String savePath=Environment.getExternalStorageDirectory().getPath()+Constant.download_folder;
		String saveFileName=savePath+Constant.apk;
		File file=new File(saveFileName);
		if(file.exists()){
			file.delete();
		}
	}

}
