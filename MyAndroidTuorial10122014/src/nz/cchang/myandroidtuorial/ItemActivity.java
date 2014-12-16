package nz.cchang.myandroidtuorial;

import java.io.File;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class ItemActivity extends Activity {

	private EditText title_text, content_text;

	// 啟動功能用的請求代碼
	private static final int START_CAMERA = 0;
	private static final int START_RECORD = 1;
	private static final int START_LOCATION = 2;
	private static final int START_ALARM = 3;
	private static final int START_COLOR = 4;

	// 記事物件
	private Item item;
	
	// 檔案名稱
	private String fileName;
	
	// 照片
	private ImageView picture;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item);

		processViews();

		// 取得Intent物件
		Intent intent = getIntent();
		// 取得Action名稱
		String action = intent.getAction();

		// 如果是修改記事
		if (action.equals("nz.cchang.myandroidtuorial.EDIT_ITEM")) {
			// 接收記事次見與設定標題、內容
			item = (Item) intent.getExtras().getSerializable(
					"nz.cchang.myandroidtuorial.Item");
			title_text.setText(item.getTitle());
			content_text.setText(item.getContent());
		}

		// 新增記事
		else {
			item = new Item();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == Activity.RESULT_OK) {
			switch (requestCode) {
			// 照相
			case START_CAMERA:
				item.setFileName(fileName);
				break;
			case START_RECORD:
				break;
			case START_LOCATION:
				break;
			case START_ALARM:
				break;
			// 設定顏色
			case START_COLOR:
				int colorId = data.getIntExtra("colorId",
						Colors.LIGHTGREY.parseColor());
				item.setColor(getColors(colorId));
				break;
			}
		}
	}
	
	public static Colors getColors(int color) {
		Colors result = Colors.LIGHTGREY;

		if (color == Colors.BLUE.parseColor()) {
			result = Colors.BLUE;
		} else if (color == Colors.PURPLE.parseColor()) {
			result = Colors.PURPLE;
		} else if (color == Colors.GREEN.parseColor()) {
			result = Colors.GREEN;
		} else if (color == Colors.ORANGE.parseColor()) {
			result = Colors.ORANGE;
		} else if (color == Colors.RED.parseColor()) {
			result = Colors.RED;
		}

		return result;

	}
	
	private void processViews() {
		// TODO Auto-generated method stub
		title_text = (EditText) findViewById(R.id.title_text);
		content_text = (EditText) findViewById(R.id.content_text);
	}

	// 點擊確定與取消都會呼叫這個方法
	public void onSubmit(View view) {
		// 確定按鈕
		if (view.getId() == R.id.ok_item) {
			// 讀取使用者輸入的標題與內容
			String titleText = title_text.getText().toString();
			String contentText = content_text.getText().toString();

			// 設定記事物件的標題與內容
			item.setTitle(titleText);
			item.setContent(contentText);

			// 如果是修改記事
			if (getIntent().getAction().equals(
					"nz.cchang.myandroidtuorial.EDIT_ITEM")) {
				item.setLastModify(new Date().getTime());
			}
			// 新增記事
			else {
				item.setDatetime(new Date().getTime());
				// 建立 SharedPreferences物件
				SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
				// 讀取設定的預設顏色
				int color = sharedPreferences.getInt("DEFAULT_COLOR", -1);
				item.setColor(getColors(color));
			}

			Intent result = getIntent();
			// 設定回傳的記事物件
			result.putExtra("nz.cchang.myandroidtuorial.Item", item);
			setResult(Activity.RESULT_OK, result);
		}

		// 結束
		finish();
	}

	// 以後需要擴充的功能
	public void clickFunction(View view) {
		int id = view.getId();

		switch (id) {
		case R.id.take_picture:
			// 起動向機元件用的Intent物件
			Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			
			
			// 照片檔案名稱
			File pictureFile = configFileName("p", ".jpg");
			Uri uri = Uri.fromFile(pictureFile);
			
			
			// 設定檔案名稱
			intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
			
			
			// 啟動相機元件
			startActivityForResult(intentCamera, START_CAMERA);
			break;

		case R.id.record_sound:
			break;

		case R.id.set_location:
			break;

		case R.id.set_alarm:
			break;

		case R.id.select_color:
			// 啟動設定顏色的Activity元件
			startActivityForResult(new Intent(this, ColorActivity.class),
					START_COLOR);
			break;
		}
	}

	private File configFileName(String prefix, String extension) {
		// TODO Auto-generated method stub
		// 	如果記事資料已經有檔案名稱
		if (item.getFileName() != null && item.getFileName().length() > 0) {
			fileName = item.getFileName();
		} 
		// 產生檔案名稱
		else {
			fileName = FileUtil.getUniqueFileName();
		}
		return new File(FileUtil.getExternalStorageDir(FileUtil.APP_DIR), prefix + fileName + extension);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
		// 如果有檔案名稱
		if (item.getFileName() != null && item.getFileName().length() > 0) {
			// 照片檔案物件
			File file = configFileName("p", ".jpg");
			
			// 如果照片檔案存在
			if (file.exists()) {
				// 顯示照片元件
				picture.setVisibility(View.VISIBLE);
				// 設定照片
				FileUtil.fileToImageView(file.getAbsolutePath(), picture);
			}
		}
	}
}
