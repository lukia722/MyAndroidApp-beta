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

	// ListView使用的自訂Adapter物件
	private ItemAdapter itemAdapter;
	// 儲存所有記事本的List物件
	private List<Item> items;

	// 選單項目物件
	private MenuItem add_item, search_item, revert_item, share_item,
			delete_item;

	// 以選擇項目數量
	private int selectedCount = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		processViews();
		processControllers();

		// 加入範例資料
		items = new ArrayList<Item>();

		items.add(new Item(1, new Date().getTime(), Colors.RED,
				"Hello MyAndroidApp", "Hello Content", "", 0, 0, 0));
		items.add(new Item(2, new Date().getTime(), Colors.BLUE, "Welcom to MyAndroidApp",
				"Welcom!!", "", 0, 0, 0));
		items.add(new Item(3, new Date().getTime(), Colors.GREEN, "Thank you for using MyAndroidApp",
				"Hello Content", "", 0, 0, 0));

		// 建立自訂Adapter物件
		itemAdapter = new ItemAdapter(this, R.layout.single_item, items);
		item_list.setAdapter(itemAdapter);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		// 如果被啟動的Activity元件傳會確定的結果
		if (resultCode == Activity.RESULT_OK) {
			// 讀取記事物件
			Item item = (Item) data.getExtras().getSerializable(
					"nz.cchang.myandroidtuorial.Item");

			// 如果新增記事
			if (requestCode == 0) {
				// 設定記事物件的編號與日期時間
				item.setId(items.size() + 1);

				// 加入新稱家的記事物件
				items.add(item);

				// 通知資料改變
				itemAdapter.notifyDataSetChanged();
			}
			// 如果是修改記事
			else if (requestCode == 1) {
				// 讀取記事編號
				int poistion = data.getIntExtra("poistion", -1);

				if (poistion != -1) {
					// 設定修改的記事物件
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

		// 建立選單項目點擊監聽物件
		OnItemClickListener itemListener = new OnItemClickListener() {
			// 第一個參數是使用者的ListView物件
			// 第二個參數是使用者選擇的項目
			// 第三個參數是使用者選擇的項目編號，第一個是0
			// 第四個參數在這裡沒有用途
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 讀取選擇的記事物件
				Item item = itemAdapter.getItem(position);

				// 如果已經有勾選的項目
				if (selectedCount > 0) {
					// 處理是否顯示已選擇的項目
					processMenu(item);
					// 重新設定記事項目
					itemAdapter.set(position, item);
				} else {
					// 使用Action名稱建立啟動另一個activity元件需要的Intent物件
					Intent intent = new Intent(
							"nz.cchang.myandroidtuorial.EDIT_ITEM");

					// 設定記事編號與標題
					intent.putExtra("poistion", position);
					intent.putExtra("nz.cchang.myandroidtuorial.Item", item);

					// 呼叫「startActivityForResult」,第二個參數「1」表示執行修改
					startActivityForResult(intent, 1);
				}
			}
		};

		// 註冊選單項目點擊監聽物件
		item_list.setOnItemClickListener(itemListener);

		// 建立選單項目長按監聽物件
		OnItemLongClickListener itemLongListener = new OnItemLongClickListener() {
			// 第一個參數是使用者的ListView物件
			// 第二個參數是使用者選擇的項目
			// 第三個參數是使用者選擇的項目編號，第一個是0
			// 第四個參數在這裡沒有用途
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// 讀取選擇的記事物件
				Item item = itemAdapter.getItem(position);
				// 處理是否顯示以選擇項目
				processMenu(item);
				// 重新設定記事項目
				itemAdapter.set(position, item);
				return true;
			}
		};

		// 註冊選單項目長按監聽物件
		item_list.setOnItemLongClickListener(itemLongListener);

		// 建立長按監聽物件
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

		// 註冊長按鍵聽物件
		show_app_name.setOnLongClickListener(listener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main_menu, menu);

		// 取得選單項目物件
		add_item = menu.findItem(R.id.add_item);
		search_item = menu.findItem(R.id.search_item);
		revert_item = menu.findItem(R.id.revert_item);
		share_item = menu.findItem(R.id.share_item);
		delete_item = menu.findItem(R.id.delete_item);

		// 設定選單項目
		processMenu(null);

		return true;
	}
	
	// 使用者選擇所有的選單項目都會呼叫這個方法
	public void clickMenuItem(MenuItem item) {
		// 使用參數取得使用者選擇的選單項目元件編號
		int itemId = item.getItemId();

		// 判斷該直醒什麼工作，目前還沒有加入執行的工作元件需要
		switch (itemId) {
		case R.id.search_item:
			break;

		// 使用者選擇新增散單項目
		case R.id.add_item:
			// 使用Activity的名稱建立另一個Activity元件需要的Intent物件
			Intent intent = new Intent("nz.cchang.myandroidtuorial.ADD_ITEM");
			// 呼叫「startActivityForResult」，第二個參數「0」目前沒有使用過
			startActivityForResult(intent, 0);
			break;

		// 取消所有以勾選的項目
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

		// 刪除
		case R.id.delete_item:
			// 沒有選擇
			if (selectedCount == 0) {
				break;
			}

			// 建立與顯示詢問是否刪除的對話框
			AlertDialog.Builder d = new AlertDialog.Builder(this);
			String message = getString(R.string.delete_item);
			d.setTitle(R.string.delete).setMessage(
					String.format(message, selectedCount));
			d.setPositiveButton(android.R.string.yes,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							// 刪除所有以勾選的項目
							int index = itemAdapter.getCount() - 1;

							while (index > -1) {
								Item item = itemAdapter.get(index);

								if (item.isSelected()) {
									itemAdapter.remove(item);
								}

								index--;
							}

							// 通知資料改變
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
	
	// 處理是否顯示已選擇項目
	private void processMenu(Item item) {
		// 如果需要設定記事項目
		if (item != null) {
			// 設定以勾選的狀態
			item.setSelected(!item.isSelected());

			// 計算以勾選數量
			if (item.isSelected()) {
				selectedCount++;
			} else {
				selectedCount--;
			}

		}

		// 根據狀選擇的狀況，設定是否顯示選單項目
		add_item.setVisible(selectedCount == 0);
		search_item.setVisible(selectedCount == 0);
		revert_item.setVisible(selectedCount > 0);
		share_item.setVisible(selectedCount > 0);
		delete_item.setVisible(selectedCount > 0);
	}
	
	public void clickPreferences(MenuItem item) {
		// 設定啟動元件
		startActivity(new Intent(this, PrefActivity.class));
	}

	// 方法名稱跟onClick的設定一樣，參數型態是android.view.View
	// 點擊應用程式名稱元件後呼叫的方法
	public void aboutApp(View view) {
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}
}
