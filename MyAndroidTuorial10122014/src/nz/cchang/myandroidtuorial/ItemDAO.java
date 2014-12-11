package nz.cchang.myandroidtuorial;

import android.content.ContentValues;
import android.content.Context;
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
	
	// �ϥΤW���ŧi���ܼƫإߪ�檺sql���O
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
		
		
		// �s�W�@����ƨè��o�s��
		// �Ĥ@�ӰѼƬO���W��
		// �ĤG�ӰѼƬO�S�����w���Ȫ��w�]��
		// �ĤT�ӰѼƬO�]�˷s�W��ƪ�ContentValues����
		long id = db.insert(CONTENT_COLUMN, null, cv);
		
		// �]�w�s�� 
		item.setId(id);
		
		return item;
	}
	
}
