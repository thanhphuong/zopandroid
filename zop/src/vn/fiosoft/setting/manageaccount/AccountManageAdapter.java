package vn.fiosoft.setting.manageaccount;

import java.util.List;
import vn.fiosoft.zop.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AccountManageAdapter extends ArrayAdapter<Account> {

	private List<Account> mItems;
	private Activity mActivity;

	public AccountManageAdapter(Activity activity, List<Account> items) {
		super(activity, R.layout.item_account_manage_list, items);
		this.mItems = items;
		this.mActivity = activity;
	}

	private static class ViewHolder {
		public TextView text;
		public TextView statusSync;
		public ImageView iconSync;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		final ViewHolder viewHolder;

		if (rowView == null) {
			LayoutInflater layoutInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = layoutInflater.inflate(R.layout.item_account_manage_list, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.text = (TextView) rowView.findViewById(R.id.text);
			viewHolder.statusSync = (TextView) rowView.findViewById(R.id.status_sync);
			viewHolder.iconSync = (ImageView) rowView.findViewById(R.id.icon_sync);

			rowView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) rowView.getTag();
		}

		Account item = mItems.get(position);

		if (item == null)
			return null;

		viewHolder.text.setText(item.getEmail());
		switch (item.getSync()) {
		case Account.SYNC_ERROR:
			viewHolder.statusSync.setText(R.string.sync_error);
			viewHolder.iconSync.setVisibility(View.INVISIBLE);
			break;
		case Account.SYNC_ON:
			viewHolder.statusSync.setText(R.string.sync_on);
			viewHolder.iconSync.setVisibility(View.VISIBLE);
			break;
		case Account.SYNC_OFF:
			viewHolder.statusSync.setText(R.string.sync_off);
			viewHolder.iconSync.setVisibility(View.INVISIBLE);
			break;
		}

		return rowView;
	}

}