package nz.cchang.myandroidtuorial;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {
	
	// 資料庫名稱
	public static final String DATABASE_NAME = "mydata.db";
	// 資料庫版本，資料結構改變的時候要更改這個數字，通常是+1
	public static final int VERSION = 1;
	// 資料庫物件，固定的欄位變數
	private static SQLiteDatabase database;
	
	
	// 建構子，在一般的應用都不需要修改
	public MyDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	
	// 需要資料庫的元件的這個方法，這個方法一般的應用都不需要修改
	public static SQLiteDatabase getDatabase(Context context) {
		if (database == null || !database.isOpen()) {
			database = new MyDBHelper(context, DATABASE_NAME, null, VERSION).getWritableDatabase();
		}
		
		return database;
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		// 建立應用程式需要的表格
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// 刪除原有的表格
		
		
		// 呼叫 onCreate建立新版表格
		onCreate(db);
	}

}
