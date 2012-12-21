package vn.fiosoft.setting.manageaccount;

import java.util.ArrayList;
import java.util.List;

import vn.fiosoft.setting.SettingActivity;
import vn.fiosoft.setting.SettingAdapter;
import vn.fiosoft.zop.R;
import android.app.ListActivity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class AccountManageActivity extends ListActivity {

	private ListView mListView;
	private List<Account> items;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_account_manage);

		mListView = getListView();
		refreshListAdapter();
	}

	public void refreshListAdapter() {
		items = new ArrayList<Account>();

		// create items, when add new item, you must change height of list with
		// 1 item = 45dp + 1dp border
		items.add(new Account(1, "btphuong2345@gmail.comsdfasdfasdfsadfsafdsdfasfsafsd", Account.SYNC_ERROR));
		items.add(new Account(2, "btphuong2345@gmail.com", Account.SYNC_ON));
		items.add(new Account(3, "btphuong2345@gmail.com", Account.SYNC_OFF));

		mListView.setAdapter(new AccountManageAdapter(this, items));
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

				Account item = items.get(position);
				Toast.makeText(AccountManageActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();

			}
		});

	}

}
