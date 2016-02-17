package edu.nefu.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
/**
 * 数据库的管理模块
 * @author WJH
 *
 */
public class DBMannager {
	
	private DBHelper helper;
	private SQLiteDatabase db;
	
	public DBMannager(Context context) {
		// TODO Auto-generated constructor stub
		helper=new DBHelper(context);
		db=helper.getWritableDatabase();
//		db.execSQL("CREATE TABLE IF NOT EXISTS history" +  
//                " (riqi TEXT, region TEXT, tree TEXT, LBH INTEGER, XBH INTEGER, db INTEGER, dm INTEGER, dw INTEGER, de INTEGER, ar INTEGER)"); 
//		db.execSQL("CREATE TABLE IF NOT EXISTS color" +
//				" (c INTEGER)");
//		db.execSQL("CREATE TABLE IF NOT EXISTS task" +  
//                " (riqi TEXT, region TEXT, tree TEXT, LBH INTEGER, XBH INTEGER, state INTEGER)"); 
	}
	
	public void add_model(Model model){
		db.beginTransaction();
		try{
			db.execSQL("INSERT INTO model VALUES(?,?,?,?,?,?,?,?,?,?)", new Object[]{model.time, model.temp, model.humi, model.light, model.tentemp, model.twetemp, model.tenhumi, model.twehumi, model.co2, model.rain});
		    db.setTransactionSuccessful();
		}finally{
			db.endTransaction();
		}
	}
	
	public Model getmodel(){
		Model res=null;
		Cursor cursor=db.rawQuery("SELECT * FROM model", null);
		cursor.moveToLast();
	    res=new Model(cursor.getString(cursor.getColumnIndex("time")),
	    		cursor.getDouble(cursor.getColumnIndex("temp")),
	    		cursor.getDouble(cursor.getColumnIndex("humi")),
	    		cursor.getDouble(cursor.getColumnIndex("light")),
	    		cursor.getDouble(cursor.getColumnIndex("tentemp")),
	    		cursor.getDouble(cursor.getColumnIndex("twetemp")),
	    		cursor.getDouble(cursor.getColumnIndex("tenhumi")),
	    		cursor.getDouble(cursor.getColumnIndex("twehumi")),
	    		cursor.getDouble(cursor.getColumnIndex("co2")),
	    		cursor.getDouble(cursor.getColumnIndex("rain")));
	    cursor.close();
	    return res;
	}
	
	public void delete_allmodel(){
		db.beginTransaction();
		try{
			db.execSQL("DELETE from model");
		    db.setTransactionSuccessful();
		}finally{
			db.endTransaction();
		}
	}
	
	public void delete_allhistory(){
		db.beginTransaction();
		try{
			db.execSQL("DELETE from history");
		    db.setTransactionSuccessful();
		}finally{
			db.endTransaction();
		}
	}
	
	public void delete_alltask(){
		db.beginTransaction();
		try{
			db.execSQL("DELETE from task");
		    db.setTransactionSuccessful();
		}finally{
			db.endTransaction();
		}
	}
	
	public void delete_history(String whereClause, String[] whereArgs){
		db.beginTransaction();
		try{
			db.delete("history", whereClause, whereArgs);
		    db.setTransactionSuccessful();
		}finally{
			db.endTransaction();
		}
	}
	
	public void delete_task(String whereClause, String[] whereArgs){
		db.beginTransaction();
		try{
			db.delete("task", whereClause, whereArgs);
		    db.setTransactionSuccessful();
		}finally{
			db.endTransaction();
		}
	}
	
	public void add_history(History history){
		db.beginTransaction();
		try{
			db.execSQL("INSERT INTO history VALUES(?,?,?,?,?,?,?,?,?,?,?)", new Object[]{history.id, history.riqi,history.region,history.tree,history.lbh,history.xbh,history.db,history.dm,history.dw,history.de,history.ar});
		    db.setTransactionSuccessful();
		}finally{
			db.endTransaction();
		}
	}
	
	public void add_task(Task task){
		db.beginTransaction();
		try{
			db.execSQL("INSERT INTO task VALUES(?,?,?,?,?,?,?)", new Object[]{task.id,task.riqi,task.region,task.tree,task.lbh,task.xbh,task.state});
			db.setTransactionSuccessful();
		}finally{
			db.endTransaction();
		}
	}
	
	public void changecolor(double c){
		db.beginTransaction();
		try{
			int count=0;
			Cursor cursor=db.rawQuery("SELECT * FROM color", null);
			ContentValues values=new ContentValues();
			values.put("c", c);
			while(cursor.moveToNext()){
				count++;
			}
			if(count==0){
				db.insert("color", null, values);			
			}
			else{	
				db.update("color", values, null, null);
			}
			cursor.close();
		    db.setTransactionSuccessful();
		}finally{
			db.endTransaction();
		}
	}
	
	public int getColor(){
		int res=0xff63B8FF;
		Cursor cursor=db.rawQuery("SELECT * FROM color", null);
	    if(cursor.moveToNext()){
	    	res=cursor.getInt(cursor.getColumnIndex("c"));
	    }
	    cursor.close();
	    return res;
	}
	
	public ArrayList<History> query_history(String sql, int from, int size){
		ArrayList<History> histories=new ArrayList<History>();
		Cursor c=db.rawQuery(sql, null);
		int count=1;
		c.moveToPosition(from);
		while(c.moveToNext()&&(count<=size)){
			History history=new History(c.getString(c.getColumnIndex("id")),
					                    c.getString(c.getColumnIndex("riqi")), 
					                    c.getString(c.getColumnIndex("region")),
					                    c.getString(c.getColumnIndex("tree")),
					                    c.getInt(c.getColumnIndex("LBH")),
					                    c.getInt(c.getColumnIndex("XBH")),
					                    c.getInt(c.getColumnIndex("db")),
					                    c.getInt(c.getColumnIndex("dm")),
					                    c.getInt(c.getColumnIndex("dw")),
					                    c.getInt(c.getColumnIndex("de")),
					                    c.getInt(c.getColumnIndex("ar")));
			histories.add(history);
			count++;
		}
		c.close();
		return histories;
	}
	
	public ArrayList<History> query_history(String sql){
		ArrayList<History> histories=new ArrayList<History>();
		Cursor c=db.rawQuery(sql, null);
		while(c.moveToNext()){
			History history=new History(c.getString(c.getColumnIndex("id")),
					                    c.getString(c.getColumnIndex("riqi")), 
					                    c.getString(c.getColumnIndex("region")),
					                    c.getString(c.getColumnIndex("tree")),
					                    c.getInt(c.getColumnIndex("LBH")),
					                    c.getInt(c.getColumnIndex("XBH")),
					                    c.getInt(c.getColumnIndex("db")),
					                    c.getInt(c.getColumnIndex("dm")),
					                    c.getInt(c.getColumnIndex("dw")),
					                    c.getInt(c.getColumnIndex("de")),
					                    c.getInt(c.getColumnIndex("ar")));
			histories.add(history);
		}
		c.close();
		return histories;
	}
	
	public ArrayList<Task> query_task(String sql, int from, int size){
		ArrayList<Task> tasks=new ArrayList<Task>();
		Cursor c=db.rawQuery(sql, null);
		int count=1;
		c.moveToPosition(from);
		while(c.moveToNext()&&(count<=size)){
			Task task=new Task(c.getString(c.getColumnIndex("id")),
					           c.getString(c.getColumnIndex("riqi")), 
					           c.getString(c.getColumnIndex("region")),
					           c.getString(c.getColumnIndex("tree")),
					           c.getInt(c.getColumnIndex("LBH")),
					           c.getInt(c.getColumnIndex("XBH")),
					           c.getInt(c.getColumnIndex("state")));
			tasks.add(task);
			count++;
		}
		c.close();
		return tasks;
	}
	
	public ArrayList<Task> query_task(String sql){
		ArrayList<Task> tasks=new ArrayList<Task>();
		Cursor c=db.rawQuery(sql, null);
		while(c.moveToNext()){
			Task task=new Task(c.getString(c.getColumnIndex("id")),
					           c.getString(c.getColumnIndex("riqi")), 
					           c.getString(c.getColumnIndex("region")),
					           c.getString(c.getColumnIndex("tree")),
					           c.getInt(c.getColumnIndex("LBH")),
					           c.getInt(c.getColumnIndex("XBH")),
					           c.getInt(c.getColumnIndex("state")));
			tasks.add(task);
		}
		c.close();
		return tasks;
	}
	
	public void update_task(ContentValues values, String whereClause, String[]whereArgs){
		db.beginTransaction();
		try{
			db.update("task", values, whereClause, whereArgs);
		    db.setTransactionSuccessful();
		}finally{
			db.endTransaction();
		}
	}
	
	public void closeDB(){
		db.close();
	}
	
}
