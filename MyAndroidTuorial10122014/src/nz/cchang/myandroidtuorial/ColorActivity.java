package nz.cchang.myandroidtuorial;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class ColorActivity extends Activity {

	private LinearLayout color_gallery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_color);

		processViews();

		ColorListener listener = new ColorListener();

		for (Colors c : Colors.values()) {
			Button button = new Button(this);
			button.setId(c.parseColor());
			LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(
					128, 128);
			layout.setMargins(6, 6, 6, 6);
			button.setLayoutParams(layout);
			button.setBackgroundColor(c.parseColor());

			button.setOnClickListener(listener);

			color_gallery.addView(button);
		}
	}

	private void processViews() {
		// TODO Auto-generated method stub
		color_gallery = (LinearLayout) findViewById(R.id.color_gallery);
	}

	private class ColorListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			String action = ColorActivity.this.getIntent().getAction();
			
			// �g�ѳ]�w����Ұ�
			// TODO Auto-generated method stub
			if (action!= null && action.equals("nz.cchang.myandroidtuorial.CHOOSE_COLOR" )) {
				
				// �إ�SharedPreferences����
				SharedPreferences.Editor editor = 
						PreferenceManager.getDefaultSharedPreferences(ColorActivity.this).edit();
				// �x�s�w�]�C��
				editor.putInt("DEFAULT_COLOR", view.getId());
				// �g�J�]�w��
				editor.commit();
				finish();
			}
			
			// �g�ѷs�W�|�O�ק�O�ƪ�����Ұ�
			else {
			Intent result = getIntent();
			result.putExtra("colorId", view.getId());
			setResult(Activity.RESULT_OK, result);
			finish();	
			}
		}

	}

}
