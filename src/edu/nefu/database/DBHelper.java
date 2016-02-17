package edu.nefu.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * 数据库的创建
 */
public class DBHelper extends SQLiteOpenHelper{

	public static final String DATABASE_NAME="gfa.db";
	public static final int DATABASE_VERSION=1; 
	
	public DBHelper(Context context) {
		// TODO Auto-generated constructor stub
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE IF NOT EXISTS history" +  
                " (id TEXT, riqi TEXT, region TEXT, tree TEXT, LBH INTEGER, XBH INTEGER, db INTEGER, dm INTEGER, dw INTEGER, de INTEGER, ar INTEGER)"); 
		db.execSQL("CREATE TABLE IF NOT EXISTS color" +
				" (c INTEGER)");
		db.execSQL("CREATE TABLE IF NOT EXISTS task" +  
                " (id TEXT, riqi TEXT, region TEXT, tree TEXT, LBH INTEGER, XBH INTEGER, state INTEGER)"); 
	    db.execSQL("CREATE TABLE IF NOT EXISTS model"+
                " (time TEXT, temp DOUBLE, humi DOUBLE, light DOUBLE, tentemp DOUBLE, twetemp DOUBLE, tenhumi DOUBLE, twehumi DOUBLE, co2 DOUBLE, rain DOUBLE)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS history");
		db.execSQL("DROP TABLE IF EXISTS color");
		db.execSQL("DROP TABLE IF EXISTS task");
		db.execSQL("DROP TABLE IF EXISTS model");
		onCreate(db);
	}

}
