package edu.nefu.display;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.content.Context;
/**
 * 地图数据的展开
 * @author WJH
 *
 */
public class MapData {
	
	private Context context=null;
	
	public MapData(final Context context) {
		// TODO Auto-generated constructor stub
		this.context=context;
	}
	
	public void copyToSDCard(String folderpath){
		String[] files={""};
		try {
			files=context.getAssets().list("");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		for(int i=1;i<=2;i++){
			File fileTarget=new File(folderpath,files[i]);
			try {
				if(!fileTarget.exists()){
					fileTarget.createNewFile();
					InputStream is=context.getAssets().open(files[i]);
					copychildToSDCard(is, fileTarget);
					if(fileTarget.getName().equals("map.zip")){
						try {
							unzipSingleFileHereWithFileName(folderpath+"/"+files[i], "map.dat");
							File file=new File(folderpath+"/"+files[i]);
							if(file.exists()){
								file.delete();
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void copychildToSDCard(InputStream is,File toFile){
		if(!toFile.exists()){
			try {
				toFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		FileOutputStream fos;
		try {
			fos=new FileOutputStream(toFile);
			byte[] buffer=new byte[8*1024];
			int count=0;
			while((count=is.read(buffer))>0){
				fos.write(buffer, 0, count);
			}
			fos.close();
			is.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void unzipSingleFileHereWithFileName(String zipPath, String name)throws IOException{
        File zipFile=new File(zipPath);
        File unzipFile=new File(zipFile.getParent()+"/"+name);
        if(!unzipFile.exists()){
        	unzipFile.createNewFile();
        }
        ZipInputStream zipInStream=null;
        FileOutputStream unzipOutStream=null;
        try {
            zipInStream=new ZipInputStream(new FileInputStream(zipFile));
            ZipEntry zipEntry=zipInStream.getNextEntry();
            if(!zipEntry.isDirectory()) {
                unzipOutStream=new FileOutputStream(unzipFile);
                byte[] buf=new byte[4096];
                int len=-1;
                while((len=zipInStream.read(buf))!=-1){
                    unzipOutStream.write(buf, 0, len);
                }
            }
        } finally {
            if(unzipOutStream!=null){
                unzipOutStream.close();
            }
            if(zipInStream!=null){
                zipInStream.close();
            }
        }
    }

}
