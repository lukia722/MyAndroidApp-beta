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

	// �Ұʥ\��Ϊ��ШD�N�X
	private static final int START_CAMERA = 0;
	private static final int START_RECORD = 1;
	private static final int START_LOCATION = 2;
	private static final int START_ALARM = 3;
	private static final int START_COLOR = 4;

	// �O�ƪ���
	private Item item;
	
	// �ɮצW��
	private String fileName;
	
	// �Ӥ�
	private ImageView picture;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item);

		processViews();

		// ���oIntent����
		Intent intent = getIntent();
		// ���oAction�W��
		String action = intent.getAction();

		// �p�G�O�ק�O��
		if (action.equals("nz.cchang.myandroidtuorial.EDIT_ITEM")) {
			// �����O�Ʀ����P�]�w���D�B���e
			item = (Item) intent.getExtras().getSerializable(
					"nz.cchang.myandroidtuorial.Item");
			title_text.setText(item.getTitle());
			content_text.setText(item.getContent());
		}

		// �s�W�O��
		else {
			item = new Item();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == Activity.RESULT_OK) {
			switch (requestCode) {
			// �Ӭ�
			case START_CAMERA:
				item.setFileName(fileName);
				break;
			case START_RECORD:
				break;
			case START_LOCATION:
				break;
			case START_ALARM:
				break;
			// �]�w�C��
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

	// �I���T�w�P�������|�I�s�o�Ӥ�k
	public void onSubmit(View view) {
		// �T�w���s
		if (view.getId() == R.id.ok_item) {
			// Ū���ϥΪ̿�J�����D�P���e
			String titleText = title_text.getText().toString();
			String contentText = content_text.getText().toString();

			// �]�w�O�ƪ��󪺼��D�P���e
			item.setTitle(titleText);
			item.setContent(contentText);

			// �p�G�O�ק�O��
			if (getIntent().getAction().equals(
					"nz.cchang.myandroidtuorial.EDIT_ITEM")) {
				item.setLastModify(new Date().getTime());
			}
			// �s�W�O��
			else {
				item.setDatetime(new Date().getTime());
				// �إ� SharedPreferences����
				SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
				// Ū���]�w���w�]�C��
				int color = sharedPreferences.getInt("DEFAULT_COLOR", -1);
				item.setColor(getColors(color));
			}

			Intent result = getIntent();
			// �]�w�^�Ǫ��O�ƪ���
			result.putExtra("nz.cchang.myandroidtuorial.Item", item);
			setResult(Activity.RESULT_OK, result);
		}

		// ����
		finish();
	}

	// �H��ݭn�X�R���\��
	public void clickFunction(View view) {
		int id = view.getId();

		switch (id) {
		case R.id.take_picture:
			// �_�ʦV������Ϊ�Intent����
			Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			
			
			// �Ӥ��ɮצW��
			File pictureFile = configFileName("p", ".jpg");
			Uri uri = Uri.fromFile(pictureFile);
			
			
			// �]�w�ɮצW��
			intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
			
			
			// �Ұʬ۾�����
			startActivityForResult(intentCamera, START_CAMERA);
			break;

		case R.id.record_sound:
			break;

		case R.id.set_location:
			break;

		case R.id.set_alarm:
			break;

		case R.id.select_color:
			// �Ұʳ]�w�C�⪺Activity����
			startActivityForResult(new Intent(this, ColorActivity.class),
					START_COLOR);
			break;
		}
	}

	private File configFileName(String prefix, String extension) {
		// TODO Auto-generated method stub
		// 	�p�G�O�Ƹ�Ƥw�g���ɮצW��
		if (item.getFileName() != null && item.getFileName().length() > 0) {
			fileName = item.getFileName();
		} 
		// �����ɮצW��
		else {
			fileName = FileUtil.getUniqueFileName();
		}
		return new File(FileUtil.getExternalStorageDir(FileUtil.APP_DIR), prefix + fileName + extension);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
		// �p�G���ɮצW��
		if (item.getFileName() != null && item.getFileName().length() > 0) {
			// �Ӥ��ɮת���
			File file = configFileName("p", ".jpg");
			
			// �p�G�Ӥ��ɮצs�b
			if (file.exists()) {
				// ��ܷӤ�����
				picture.setVisibility(View.VISIBLE);
				// �]�w�Ӥ�
				FileUtil.fileToImageView(file.getAbsolutePath(), picture);
			}
		}
	}
}
