package edu.nefu.XMLTool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import edu.nefu.database.Model;

public class TxtParser {
	
	private static String filepath=null;

	public static void setPath(String filepath) {
		// TODO Auto-generated constructor stub
		TxtParser.filepath=filepath;
	}
	
	public static Model parser(){
		Model res=null;
		try{
            String encoding="GBK";
            File file=new File(filepath);
            if(file.isFile()&&file.exists()){
                InputStreamReader read=new InputStreamReader(new FileInputStream(file), encoding);
                BufferedReader bufferedReader=new BufferedReader(read);
                String lineTxt=null;
                String resline="";
                while((lineTxt=bufferedReader.readLine())!=null){
                	resline=lineTxt;
                };
                String t[]=resline.split("\\s+");
                if(t.length==11){
                	res=new Model(t[0]+" "+t[1], Double.parseDouble(t[2]), Double.parseDouble(t[3]), Double.parseDouble(t[4]), Double.parseDouble(t[5]), Double.parseDouble(t[6]), Double.parseDouble(t[7]), Double.parseDouble(t[8]), Double.parseDouble(t[9]), Double.parseDouble(t[10]));
                }
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        }catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
	    return res;
	}
	
}
