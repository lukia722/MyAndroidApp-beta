package nz.cchang.myandroidtuorial;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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


//	public static final String DATE_COLUMN = "date";
//	public static final String TIME_COLUMN = "time";
//	public static final String LOCATION_COLUMN = "location";
//	public static final String PEOPLE_COLUMN = "people";
//	public static final String ACTIVITY_COLUMN = "activity";
	
	// 使用上面宣告的變數建立表格的sql指令
	public static final String CREATE_TABLE = 
			"CREATE TABLE " + TABLE_NAME + " (" + 
			KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			DATETIME_COLUMN + " INTEGER NOT NULL, " + 
			COLOR_COLUMN + " INTEFER NOT NULL, " +
			TITLE_COLUMN + " TEXT NOT NULL, " +
			CONTENT_COLUMN + " TEXT NOT NULL, " +
			FILENAME_COLUMN + " TEXT, " + 
			LATITUDE_COLUMN + " REAL, " +
			LONGITUDE_COLUMN + " REAL, " + 
			LASTMODIFY_COLUMN + " INTEGER " + ") ";
	
//	DATE_COLUMN + " TEXT NOT NULL, " +
//	TIME_COLUMN + " TEXT NOT NULL, " +
//	LOCATION_COLUMN + " TEXT, " +
//	PEOPLE_COLUMN + " TEXT, " +
//	ACTIVITY_COLUMN + " TEXT, " + 
	
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

//		cv.put(DATE_COLUMN, item.getDate());		
//		cv.put(TIME_COLUMN, item.getTime());
//		cv.put(LOCATION_COLUMN, item.getLocation());
//		cv.put(PEOPLE_COLUMN, item.getPeople());
//		cv.put(ACTIVITY_COLUMN, item.getActivity());
		
		
		// 新增一筆資料並取得編號
		// 第一個參數是表格名稱
		// 第二個參數是沒有指定欄位值的預設值
		// 第三個參數是包裝新增資料的ContentValues物件
		long id = db.insert(TABLE_NAME, null, cv);
		
		// 設定編號 
		item.setId(id);
		
		return item;
	}
	
	
	// 修改參數指定的物件
	public boolean update(Item item) {
		// 建立準備修改資料的ContentValues物件
		ContentValues cv = new ContentValues();
		
		// 第一個參數是欄位名稱，第二個參數是欄位資料
		cv.put(DATETIME_COLUMN, item.getDatetime());
		cv.put(COLOR_COLUMN, item.getColor().parseColor());
		cv.put(TITLE_COLUMN, item.getTitle());
		cv.put(CONTENT_COLUMN, item.getContent());
		cv.put(FILENAME_COLUMN, item.getFileName());
		cv.put(LATITUDE_COLUMN, item.getLatitude());
		cv.put(LONGITUDE_COLUMN, item.getLongitude());
		cv.put(LASTMODIFY_COLUMN, item.getLastModify());		

//		cv.put(DATE_COLUMN, item.getDate());		
//		cv.put(TIME_COLUMN, item.getTime());
//		cv.put(LOCATION_COLUMN, item.getLocation());
//		cv.put(PEOPLE_COLUMN, item.getPeople());
//		cv.put(ACTIVITY_COLUMN, item.getActivity());
		
		
		// 設定修改資料的條件為編號
		// 格式為「欄位名稱＝資料」
		String where = KEY_ID + "=" + item.getId();
		
		// 執行修改資料並回傳修改資料數量是否成功
		return db.update(TABLE_NAME, cv, where, null) > 0;
	}
	
	
	// 刪除參數指定編號的資料
	public boolean delete(long id) {
		// 設定條件為編號，格式為「欄位名稱＝資料」
		String where = KEY_ID + "=" +id;
		// 刪除指定編號定回穿刪除是否成功
		return db.delete(TABLE_NAME, where, null) > 0;
	}
	
	
	// 讀取所有記事資料
	public List<Item> getAll() {
		List<Item> result = new ArrayList<Item>();
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);
		
		
		while (cursor.moveToNext()) {
			result.add(getRecord(cursor));
		}
		
		cursor.close();
		return result;
	}
	
	
	// 取得指定編號的資料物件
	public Item get(long id) {
		// 準備回傳結果用的物件
		Item item =null;
		// 使用變號為查詢條件
		String where = KEY_ID + "=" +id;
		// 執行查詢 
		Cursor result = db.query(TABLE_NAME, null, where, null, null, null, null, null);
		
		// 如果有查詢結果
		if (result.moveToFirst()) {
			// 讀取包裝一筆資料的物件
			item = getRecord(result);
		}
		
		// 關閉Cursor物件
		result.close();
		// 回傳結果
		return item;
	}

	// 把Cursor目前的資料包裝為物件
	private Item getRecord(Cursor cursor) {
		// TODO Auto-generated method stub
		// 準備回傳結果用的物件
		Item result = new Item();
		
		result.setId(cursor.getLong(0));
		result.setDatetime(cursor.getLong(1));
		result.setColor(ItemActivity.getColors(cursor.getInt(2)));

		result.setTitle(cursor.getString(3));
		result.setContent(cursor.getString(4));
		result.setFileName(cursor.getString(5));
		result.setLatitude(cursor.getDouble(6));
		result.setLongitude(cursor.getDouble(7));
		result.setLastModify(cursor.getLong(8));

//		result.setDate(cursor.getString(3));		
//		result.setTime(cursor.getString(4));
//		result.setLocation(cursor.getString(6));
//		result.setPeople(cursor.getString(7));
//		result.setActivity(cursor.getString(8));
		
		// 回傳結果
		return result;
	}
	
	
	// 取得資料數量
	public int getCount() {
		int result = 0;
		Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);
		
		if (cursor.moveToNext()) {
			result = cursor.getInt(0);
		}
		
		return result;
	}
	
	
	// 建立範例資料
	public void sample() {
//		Item item = new Item(0, new Date().getTime(), Colors.RED, "01/02/2014", "Morning", "Hello MyAndroidApp", "Home", "Carole", "Breakfast", "Hello Content", "", 0, 0, 0);
//		Item item2 = new Item(0, new Date().getTime(), Colors.BLUE, "10/05/2014","Afternoon", "Welcome to MyAndroidApp", "Office","Luke", "Chatting", "Welcome!!", "", 0, 0, 0);
//		Item item3 = new Item(0, new Date().getTime(), Colors.GREEN, "10/10/2014","Afternoon", "Thank you for using MyAndroidApp", "Tea Room", "Coffee", "Janet", "Hello Content", "", 25.04719, 121.516981, 0);
//		Item item4 = new Item(0, new Date().getTime(), Colors.ORANGE, "22/12/2014","Evening", "Data stores on SQLiteDatabase", "Supermarket", "Shopping", "Amber", "Hello Data", "", 0, 0, 0);
	
		Item item = new Item(0, new Date().getTime(), Colors.RED, "Hello MyAndroidApp",  "Hello Content", "", 0, 0, 0);
		Item item2 = new Item(0, new Date().getTime(), Colors.BLUE,  "Welcome to MyAndroidApp",  "Welcome!!", "", 0, 0, 0);
		Item item3 = new Item(0, new Date().getTime(), Colors.GREEN,  "Thank you for using MyAndroidApp", "Hello Content", "", 25.04719, 121.516981, 0);
		Item item4 = new Item(0, new Date().getTime(), Colors.ORANGE,  "Data stores on SQLiteDatabase",  "Hello Data", "", 0, 0, 0);
		
		insert(item);
		insert(item2);
		insert(item3);
		insert(item4);
	}
}
