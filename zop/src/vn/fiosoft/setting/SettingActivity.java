package vn.fiosoft.setting;

import java.util.ArrayList;
import java.util.List;

import vn.fiosoft.setting.accountmanage.Account;
import vn.fiosoft.setting.accountmanage.AccountManage;
import vn.fiosoft.setting.accountmanage.AccountManageActivity;
import vn.fiosoft.zop.R;
import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SettingActivity extends ListActivity implements OnItemClickListener {

	private ListView mListView;
	private List<SettingItem> items;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		mListView = getListView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		refreshListAdapter();
	}

	public void refreshListAdapter() {
		AccountManage accountManage = new AccountManage(this);
		Account account = accountManage.getAccountActivated();
		String email = "";
		if (account == null)
			email = "no account";
		else
			email = account.getEmail();

		items = new ArrayList<SettingItem>();

		// create items, when add new item, you must change height of list with
		// 1 item = 45dp + 1dp border
		Resources res = getResources();
		items.add(new SettingItem(res.getString(R.string.manage_accounts), email));
		items.add(new SettingItem(res.getString(R.string.labs), ""));
		items.add(new SettingItem(res.getString(R.string.about), ""));

		mListView.setAdapter(new SettingAdapter(this, items));
		mListView.setOnItemClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

		switch (position) {
		case 0:
			startActivity(new Intent(this, AccountManageActivity.class));
			break;
		case 1:
			break;
		case 2:
			startActivity(new Intent(this, AboutActivity.class));
			break;
		}

	}

}
