package nz.cchang.myandroidtuorial;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {
	
	// ��Ʈw�W��
	public static final String DATABASE_NAME = "mydata.db";
	// ��Ʈw�����A��Ƶ��c���ܪ��ɭԭn���o�ӼƦr�A�q�`�O+1
	public static final int VERSION = 1;
	// ��Ʈw����A�T�w������ܼ�
	private static SQLiteDatabase database;
	
	
	// �غc�l�A�b�@�몺���γ����ݭn�ק�
	public MyDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	
	// �ݭn��Ʈw�����󪺳o�Ӥ�k�A�o�Ӥ�k�@�몺���γ����ݭn�ק�
	public static SQLiteDatabase getDatabase(Context context) {
		if (database == null || !database.isOpen()) {
			database = new MyDBHelper(context, DATABASE_NAME, null, VERSION).getWritableDatabase();
		}
		
		return database;
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		// �إ����ε{���ݭn�����
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// �R���즳�����
		
		
		// �I�s onCreate�إ߷s�����
		onCreate(db);
	}

}
