package nz.cchang.myandroidtuorial;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ItemDAO {
	
	// ���W��
	public static final String TABLE_NAME = "item";
	
	// �s��������W�١A�T�w����
	public static final String KEY_ID = "_id";
	
	// ��L������W��
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
	
	// �ϥΤW���ŧi���ܼƫإߪ�檺sql���O
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
	
	// ��Ʈw����
	private SQLiteDatabase db;	
	

	// �غc�l�A�@�몺���γ����ݭn�ק�
	public ItemDAO(Context context){
		db = MyDBHelper.getDatabase(context);
	}
	
	
	// ������Ʈw�A�@�몺���γ����ݭn�ק�
	public void close() {
		db.close();
	}
	
	
	// �s�W�Ѽƫ��w������
	public Item insert(Item item) {
		// 	���̷ǳƷs�W��ƪ�ContnetValues����
		ContentValues cv = new ContentValues();
		
		// �[�JContentValues����]�˪��s�ٸ��
		// �Ĥ@�ӰѼƬO���W�١A�ĤG�ӰѼƬO�����
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
		
		
		// �s�W�@����ƨè��o�s��
		// �Ĥ@�ӰѼƬO���W��
		// �ĤG�ӰѼƬO�S�����w���Ȫ��w�]��
		// �ĤT�ӰѼƬO�]�˷s�W��ƪ�ContentValues����
		long id = db.insert(TABLE_NAME, null, cv);
		
		// �]�w�s�� 
		item.setId(id);
		
		return item;
	}
	
	
	// �ק�Ѽƫ��w������
	public boolean update(Item item) {
		// �إ߷ǳƭק��ƪ�ContentValues����
		ContentValues cv = new ContentValues();
		
		// �Ĥ@�ӰѼƬO���W�١A�ĤG�ӰѼƬO�����
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
		
		
		// �]�w�ק��ƪ����󬰽s��
		// �榡���u���W�١׸�ơv
		String where = KEY_ID + "=" + item.getId();
		
		// ����ק��ƨæ^�ǭק��Ƽƶq�O�_���\
		return db.update(TABLE_NAME, cv, where, null) > 0;
	}
	
	
	// �R���Ѽƫ��w�s�������
	public boolean delete(long id) {
		// �]�w���󬰽s���A�榡���u���W�١׸�ơv
		String where = KEY_ID + "=" +id;
		// �R�����w�s���w�^��R���O�_���\
		return db.delete(TABLE_NAME, where, null) > 0;
	}
	
	
	// Ū���Ҧ��O�Ƹ��
	public List<Item> getAll() {
		List<Item> result = new ArrayList<Item>();
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);
		
		
		while (cursor.moveToNext()) {
			result.add(getRecord(cursor));
		}
		
		cursor.close();
		return result;
	}
	
	
	// ���o���w�s������ƪ���
	public Item get(long id) {
		// �ǳƦ^�ǵ��G�Ϊ�����
		Item item =null;
		// �ϥ��ܸ����d�߱���
		String where = KEY_ID + "=" +id;
		// ����d�� 
		Cursor result = db.query(TABLE_NAME, null, where, null, null, null, null, null);
		
		// �p�G���d�ߵ��G
		if (result.moveToFirst()) {
			// Ū���]�ˤ@����ƪ�����
			item = getRecord(result);
		}
		
		// ����Cursor����
		result.close();
		// �^�ǵ��G
		return item;
	}

	// ��Cursor�ثe����ƥ]�ˬ�����
	private Item getRecord(Cursor cursor) {
		// TODO Auto-generated method stub
		// �ǳƦ^�ǵ��G�Ϊ�����
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
		
		// �^�ǵ��G
		return result;
	}
	
	
	// ���o��Ƽƶq
	public int getCount() {
		int result = 0;
		Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);
		
		if (cursor.moveToNext()) {
			result = cursor.getInt(0);
		}
		
		return result;
	}
	
	
	// �إ߽d�Ҹ��
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
