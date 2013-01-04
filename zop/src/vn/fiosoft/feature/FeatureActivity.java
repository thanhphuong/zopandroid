package vn.fiosoft.feature;

import java.util.ArrayList;
import java.util.List;
import vn.fiosoft.zop.R;
import android.app.ListActivity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FeatureActivity extends ListActivity implements OnItemClickListener {

	private ListView mListView;
	private List<FeatureItem> items;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_settings);

		mListView = getListView();
		refreshListAdapter();
	}

	public void refreshListAdapter() {
		items = new ArrayList<FeatureItem>();

		// create items, when add new item, you must change height of list with
		// 1 item = 45dp + 1dp border
		Resources res = getResources();
		items.add(new FeatureItem(res.getString(R.string.search), R.drawable.action_search));
		items.add(new FeatureItem(res.getString(R.string.directions), R.drawable.location_directions));

		mListView.setAdapter(new FeatureAdapter(this, items));
		mListView.setOnItemClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		// if (position == 0) {
		// startActivity(new Intent(this, AccountManageActivity.class));
		// }
	}

}