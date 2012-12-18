package vn.fiosoft.settings;

import java.util.ArrayList;
import java.util.List;
import vn.fiosoft.zop.R;
import android.app.ListActivity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class SettingActivity extends ListActivity{

	private ListView mListView;
	private List<SettingItem> items;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_settings);

		mListView = getListView();	
		refreshListAdapter();
	}

	

	public void refreshListAdapter() {
		items = new ArrayList<SettingItem>();

		// create items, when add new item, you must change height of list with
		// 1 item = 45dp + 1dp border
		Resources res = getResources();
		items.add(new SettingItem(res.getString(R.string.manage_accounts), "btphuong2345@gmail.com"));
		items.add(new SettingItem(res.getString(R.string.labs), ""));
		items.add(new SettingItem(res.getString(R.string.about), ""));

		mListView.setAdapter(new SettingAdapter(this, items));
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

				SettingItem item = items.get(position);
				Toast.makeText(SettingActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();

			}
		});

	}

}
