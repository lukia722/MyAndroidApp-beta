package nz.cchang.myandroidtuorial;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ItemDAO {
	
	// 表格名稱
	public static final String TABLE_NAME = "item";
	
	// 編號表格欄位名稱，固定不變
	public static final String KEY_ID = "_id";
	
	// 其他表格欄位名稱
	public static final String DATETIME_COLUMN = "datetime";
	public static final String COLOR_COLUMN = "color";
	public static final String TITLE_COLUMN = "title";
	public static final String CONTENT_COLUMN = "content";
	public static final String FILENAME_COLUMN = "filename";
	public static final String LATITUDE_COLUMN = "latitude";
	public static final String LONGITUDE_COLUMN = "longitude";
	public static final String LASTMODIFY_COLUMN = "lastmodify";
	
	// 使用上面宣告的變數建立表格的sql指令
	public static final String CREATE_TABLE = 
			"CREATE TABLE " + TABLE_NAME + " (" + 
			KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			DATETIME_COLUMN + " INTEGER NOT NULL, " + 
			COLOR_COLUMN + " INTEFER NOT NULL," +
			TITLE_COLUMN + " TEXT NOT NULL, " + 
			CONTENT_COLUMN + " TEXT NOT NULL, " +
			FILENAME_COLUMN + " TEXT, " + 
			LATITUDE_COLUMN + " REAL, " +
			LONGITUDE_COLUMN + " REAL, " + 
			LASTMODIFY_COLUMN + "INTEGER) ";
	
	// 資料庫物件
	private SQLiteDatabase db;	
	

	// 建構子，一般的應用都不需要修改
	public ItemDAO(Context context){
		db = MyDBHelper.getDatabase(context);
	}
	
	
	// 關閉資料庫，一般的應用都不需要修改
	public void close() {
		db.close();
	}
	
	
	// 新增參數指定的物件
	public Item insert(Item item) {
		// 	見裡準備新增資料的ContnetValues物件
		ContentValues cv = new ContentValues();
		
		// 加入ContentValues物件包裝的新稱資料
		// 第一個參數是欄位名稱，第二個參數是欄位資料
		cv.put(DATETIME_COLUMN, item.getDatetime());
		cv.put(COLOR_COLUMN, item.getColor().parseColor());
		cv.put(TITLE_COLUMN, item.getTitle());
		cv.put(CONTENT_COLUMN, item.getContent());
		cv.put(FILENAME_COLUMN, item.getFileName());
		cv.put(LATITUDE_COLUMN, item.getLatitude());
		cv.put(LONGITUDE_COLUMN, item.getLongitude());
		cv.put(LASTMODIFY_COLUMN, item.getLastModify());
		
		
		// 新增一筆資料並取得編號
		// 第一個參數是表格名稱
		// 第二個參數是沒有指定欄位值的預設值
		// 第三個參數是包裝新增資料的ContentValues物件
		long id = db.insert(CONTENT_COLUMN, null, cv);
		
		// 設定編號 
		item.setId(id);
		
		return item;
	}
	
}
