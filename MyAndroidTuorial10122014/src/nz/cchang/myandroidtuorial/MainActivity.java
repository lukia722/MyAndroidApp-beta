package nz.cchang.myandroidtuorial;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ListView item_list;
	private TextView show_app_name;

	// ListView�ϥΪ��ۭqAdapter����
	private ItemAdapter itemAdapter;
	// �x�s�Ҧ��O�ƥ���List����
	private List<Item> items;

	// ��涵�ت���
	private MenuItem add_item, search_item, revert_item, share_item,
			delete_item;

	// �H��ܶ��ؼƶq
	private int selectedCount = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		processViews();
		processControllers();

		// �[�J�d�Ҹ��
		items = new ArrayList<Item>();

		items.add(new Item(1, new Date().getTime(), Colors.RED,
				"Hello MyAndroidApp", "Hello Content", "", 0, 0, 0));
		items.add(new Item(2, new Date().getTime(), Colors.BLUE, "Welcom to MyAndroidApp",
				"Welcom!!", "", 0, 0, 0));
		items.add(new Item(3, new Date().getTime(), Colors.GREEN, "Thank you for using MyAndroidApp",
				"Hello Content", "", 0, 0, 0));

		// �إߦۭqAdapter����
		itemAdapter = new ItemAdapter(this, R.layout.single_item, items);
		item_list.setAdapter(itemAdapter);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		// �p�G�Q�Ұʪ�Activity����Ƿ|�T�w�����G
		if (resultCode == Activity.RESULT_OK) {
			// Ū���O�ƪ���
			Item item = (Item) data.getExtras().getSerializable(
					"nz.cchang.myandroidtuorial.Item");

			// �p�G�s�W�O��
			if (requestCode == 0) {
				// �]�w�O�ƪ��󪺽s���P����ɶ�
				item.setId(items.size() + 1);

				// �[�J�s�ٮa���O�ƪ���
				items.add(item);

				// �q����Ƨ���
				itemAdapter.notifyDataSetChanged();
			}
			// �p�G�O�ק�O��
			else if (requestCode == 1) {
				// Ū���O�ƽs��
				int poistion = data.getIntExtra("poistion", -1);

				if (poistion != -1) {
					// �]�w�ק諸�O�ƪ���
					items.set(poistion, item);
					itemAdapter.notifyDataSetChanged();
				}
			}
		}
	}

	private void processViews() {
		item_list = (ListView) findViewById(R.id.item_list);
		show_app_name = (TextView) findViewById(R.id.show_app_name);
	}

	private void processControllers() {

		// �إ߿�涵���I����ť����
		OnItemClickListener itemListener = new OnItemClickListener() {
			// �Ĥ@�ӰѼƬO�ϥΪ̪�ListView����
			// �ĤG�ӰѼƬO�ϥΪ̿�ܪ�����
			// �ĤT�ӰѼƬO�ϥΪ̿�ܪ����ؽs���A�Ĥ@�ӬO0
			// �ĥ|�ӰѼƦb�o�̨S���γ~
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Ū����ܪ��O�ƪ���
				Item item = itemAdapter.getItem(position);

				// �p�G�w�g���Ŀ諸����
				if (selectedCount > 0) {
					// �B�z�O�_��ܤw��ܪ�����
					processMenu(item);
					// ���s�]�w�O�ƶ���
					itemAdapter.set(position, item);
				} else {
					// �ϥ�Action�W�٫إ߱Ұʥt�@��activity����ݭn��Intent����
					Intent intent = new Intent(
							"nz.cchang.myandroidtuorial.EDIT_ITEM");

					// �]�w�O�ƽs���P���D
					intent.putExtra("poistion", position);
					intent.putExtra("nz.cchang.myandroidtuorial.Item", item);

					// �I�s�ustartActivityForResult�v,�ĤG�ӰѼơu1�v��ܰ���ק�
					startActivityForResult(intent, 1);
				}
			}
		};

		// ���U��涵���I����ť����
		item_list.setOnItemClickListener(itemListener);

		// �إ߿�涵�ت�����ť����
		OnItemLongClickListener itemLongListener = new OnItemLongClickListener() {
			// �Ĥ@�ӰѼƬO�ϥΪ̪�ListView����
			// �ĤG�ӰѼƬO�ϥΪ̿�ܪ�����
			// �ĤT�ӰѼƬO�ϥΪ̿�ܪ����ؽs���A�Ĥ@�ӬO0
			// �ĥ|�ӰѼƦb�o�̨S���γ~
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// Ū����ܪ��O�ƪ���
				Item item = itemAdapter.getItem(position);
				// �B�z�O�_��ܥH��ܶ���
				processMenu(item);
				// ���s�]�w�O�ƶ���
				itemAdapter.set(position, item);
				return true;
			}
		};

		// ���U��涵�ت�����ť����
		item_list.setOnItemLongClickListener(itemLongListener);

		// �إߪ�����ť����
		OnLongClickListener listener = new OnLongClickListener() {

			@Override
			public boolean onLongClick(View view) {
				// TODO Auto-generated method stub
				AlertDialog.Builder dialog = new AlertDialog.Builder(
						MainActivity.this);
				dialog.setTitle(R.string.app_name).setMessage(R.string.about)
						.show();
				return false;
			}
		};

		// ���U������ť����
		show_app_name.setOnLongClickListener(listener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main_menu, menu);

		// ���o��涵�ت���
		add_item = menu.findItem(R.id.add_item);
		search_item = menu.findItem(R.id.search_item);
		revert_item = menu.findItem(R.id.revert_item);
		share_item = menu.findItem(R.id.share_item);
		delete_item = menu.findItem(R.id.delete_item);

		// �]�w��涵��
		processMenu(null);

		return true;
	}
	
	// �ϥΪ̿�ܩҦ�����涵�س��|�I�s�o�Ӥ�k
	public void clickMenuItem(MenuItem item) {
		// �ϥΰѼƨ��o�ϥΪ̿�ܪ���涵�ؤ���s��
		int itemId = item.getItemId();

		// �P�_�Ӫ�������u�@�A�ثe�٨S���[�J���檺�u�@����ݭn
		switch (itemId) {
		case R.id.search_item:
			break;

		// �ϥΪ̿�ܷs�W���涵��
		case R.id.add_item:
			// �ϥ�Activity���W�٫إߥt�@��Activity����ݭn��Intent����
			Intent intent = new Intent("nz.cchang.myandroidtuorial.ADD_ITEM");
			// �I�s�ustartActivityForResult�v�A�ĤG�ӰѼơu0�v�ثe�S���ϥιL
			startActivityForResult(intent, 0);
			break;

		// �����Ҧ��H�Ŀ諸����
		case R.id.revert_item:
			for (int i = 0; i < itemAdapter.getCount(); i++) {
				Item ri = itemAdapter.getItem(i);

				if (ri.isSelected()) {
					ri.setSelected(false);
					itemAdapter.set(i, ri);
				}
			}

			selectedCount = 0;
			processMenu(null);
			break;

		// �R��
		case R.id.delete_item:
			// �S�����
			if (selectedCount == 0) {
				break;
			}

			// �إ߻P��ܸ߰ݬO�_�R������ܮ�
			AlertDialog.Builder d = new AlertDialog.Builder(this);
			String message = getString(R.string.delete_item);
			d.setTitle(R.string.delete).setMessage(
					String.format(message, selectedCount));
			d.setPositiveButton(android.R.string.yes,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							// �R���Ҧ��H�Ŀ諸����
							int index = itemAdapter.getCount() - 1;

							while (index > -1) {
								Item item = itemAdapter.get(index);

								if (item.isSelected()) {
									itemAdapter.remove(item);
								}

								index--;
							}

							// �q����Ƨ���
							itemAdapter.notifyDataSetChanged();
						}
					});

			d.setNegativeButton(android.R.string.no, null);
			d.show();
			break;

		case R.id.googleplus_item:
			break;

		case R.id.facebook_item:
			break;
		}
	}
	
	// �B�z�O�_��ܤw��ܶ���
	private void processMenu(Item item) {
		// �p�G�ݭn�]�w�O�ƶ���
		if (item != null) {
			// �]�w�H�Ŀ諸���A
			item.setSelected(!item.isSelected());

			// �p��H�Ŀ�ƶq
			if (item.isSelected()) {
				selectedCount++;
			} else {
				selectedCount--;
			}

		}

		// �ھڪ���ܪ����p�A�]�w�O�_��ܿ�涵��
		add_item.setVisible(selectedCount == 0);
		search_item.setVisible(selectedCount == 0);
		revert_item.setVisible(selectedCount > 0);
		share_item.setVisible(selectedCount > 0);
		delete_item.setVisible(selectedCount > 0);
	}
	
	public void clickPreferences(MenuItem item) {
		// �]�w�Ұʤ���
		startActivity(new Intent(this, PrefActivity.class));
	}

	// ��k�W�ٸ�onClick���]�w�@�ˡA�Ѽƫ��A�Oandroid.view.View
	// �I�����ε{���W�٤����I�s����k
	public void aboutApp(View view) {
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}
}
