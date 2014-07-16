package com.example.testmms;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDb extends SQLiteOpenHelper {

	static MyDb sInstance;
	static MyDb getInstance(Context c){
		if(null == sInstance)
			sInstance = new MyDb(c,"mms",null, 1,null);
		return sInstance;
	}
	
	private MyDb(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
	}

	@Override
	public void onCreate(SQLiteDatabase a) {
		String sql = "CREATE TABLE if not exists sms (" +
	"_id INTEGER PRIMARY KEY AUTOINCREMENT,sender TEXT," + "content TEXT, date TEXT );";
		a.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	}

	void insert(String t, String content, String date){
		SQLiteDatabase db =this.getWritableDatabase();
		ContentValues values = new ContentValues(2);
		values.put("sender", t);
		values.put("content", content);
		values.put("date", date);
		db.insert("sms", null, values);
		db.close();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
