package edu.nefu.constant;
/**
 * 程序中用到的常量
 */
public class Constant {
	
	public static final String display_file="/map.ini"; 
	public static final String display_folder="/MapData";
	public static final String download_folder="/MapData/download";
	public static final String default_display_file="/map.ini";
	public static final String default_display_folder="/data/data/edu.nefu.main/MapData/";
	public static boolean ishasSD=false;
	
	public static final String select_all="SELECT * FROM history";
	public static final String orderbyriqi="SELECT * FROM history where riqi='";
	public static final String orderbyregion="SELECT * FROM history where region='";
	public static final String orderbytree="SELECT * FROM history where tree='";
	
	public static final String select_all_task="SELECT * FROM task";
	
	public static final String apk="/LeeProject.apk";
	
	public static String url="192.168.1.105";
	
	public static String testurl="http://"+url+":8080/DeforestationManagement/AndroidServiceServlet?task=update";
	public static String uploadurl="http://"+url+":8080/DeforestationManagement/AndroidServiceServlet?task=postCheckData";
	public static String serverurl="http://"+url+":8080/DeforestationManagement/AndroidServiceServlet?task=getCheckList";
	public static String problem="http://"+url+":8080/DeforestationManagement/AndroidServiceServlet?task=problem";

}
