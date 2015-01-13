package nz.cchang.myandroidtuorial;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import android.R.anim;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

public class ItemActivity extends Activity {

	private EditText title_text, content_text;
	private EditText it_date_text, time_text , location_text, people_text, activity_text;
	
	
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
	
	private String it_dateText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item);

		processViews();
		selectDate(it_date_text);

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
			it_date_text.setText(item.getIt_date());
			time_text.setText(item.getTime());				
			location_text.setText(item.getLocation());
			people_text.setText(item.getPeople());
			activity_text.setText(item.getActivity());
		}

		// �s�W�O��
		else {
			item = new Item();
		}
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == Activity.RESULT_OK) {
			switch (requestCode) {
			// �Ӭ�
			case START_CAMERA:
				item.setFileName(fileName);
				break;
			// ����
			case START_RECORD:
				// �]�w�����ɮצW��
				item.setFileName(fileName);
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
		picture = (ImageView)findViewById(R.id.picture);
		it_date_text = (EditText)findViewById(R.id.it_date_text);
		time_text = (EditText)findViewById(R.id.time_text);	
		location_text = (EditText)findViewById(R.id.location_text);
		people_text = (EditText)findViewById(R.id.people_text);
		activity_text = (EditText)findViewById(R.id.activity_text);

	}
	
	public void selectDate(View view) {
		Calendar eDate = Calendar.getInstance();
		DatePickerDialog datePickerDialog = new DatePickerDialog (ItemActivity.this, datePicDlgOnDateSelLis, 
				eDate.get(Calendar.YEAR), eDate.get(Calendar.MONTH), eDate.get(Calendar.DATE));
		datePickerDialog.setTitle("Select date:");
		datePickerDialog.setIcon(android.R.drawable.ic_dialog_info);
		datePickerDialog.show();
	}
	
	public DatePickerDialog.OnDateSetListener datePicDlgOnDateSelLis =
			new DatePickerDialog.OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					// TODO Auto-generated method stub
					it_dateText = Integer.toString(dayOfMonth) + "/"
							+ Integer.toString(monthOfYear + 1) + "/"
							+ Integer.toString(year);
					it_date_text.setText(it_dateText);
				}
			};

	// �I���T�w�P�������|�I�s�o�Ӥ�k
	public void onSubmit(View view) {
		// �T�w���s
		if (view.getId() == R.id.ok_item) {
			// Ū���ϥΪ̿�J�����D�P���e
			String titleText = title_text.getText().toString();
			String contentText = content_text.getText().toString();
			String it_dateText = it_date_text.getText().toString();
			String timeText = time_text.getText().toString();
			String locationText=location_text.getText().toString();
			String peopleText = people_text.getText().toString();
			String activityText = activity_text.getText().toString();

			// �]�w�O�ƪ��󪺼��D�P���e
			item.setTitle(titleText);
			item.setContent(contentText);
			item.setIt_date(it_dateText);
			item.setTime(timeText);
			item.setLocation(locationText);
			item.setPeople(peopleText);
			item.setActivity(activityText);
			

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
			// �����ɮצW��
			final File recordFile = configFileName("R", ".mp3");
			
			if (recordFile.exists()) {
				// �߰ݼ����٬O���ۿ��s����ܮ�
				AlertDialog.Builder d = new AlertDialog.Builder(this);
				
				d.setTitle(R.string.title_record)
				.setCancelable(true);
				d.setPositiveButton(R.string.record_play, new DialogInterface.OnClickListener() {
					
//					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						// ����
						Intent playIntent = new Intent(ItemActivity.this, PlayActivity.class);
						playIntent.putExtra("fileName", recordFile.getAbsolutePath());
						startActivity(playIntent);
						
					}
				});
				d.setNegativeButton(R.string.record_new, new DialogInterface.OnClickListener() {
					
//					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						goToRecord(recordFile);
					}
				});
				
				// ��ܹ�ܮ�
				d.show();
			} else {
				goToRecord(recordFile);
			}
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
	
	private void goToRecord(File recordFile) {
		// ����
		Intent recordIntent = new Intent(this, RecordActivity.class);
		recordIntent.putExtra("fileName", recordFile.getAbsolutePath());
		startActivityForResult(recordIntent, START_RECORD);
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
}
