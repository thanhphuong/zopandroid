package vn.fiosoft.zop;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MoreListAdapter extends ArrayAdapter<MoreMenuItem> {

	private List<MoreMenuItem> mItems = new ArrayList<MoreMenuItem>();
	private Activity mActivity;

	private static class ViewHolder {
		public TextView text;
	}

	public MoreListAdapter(Activity activity, List<MoreMenuItem> items) {
		super(activity, R.layout.item_more_list, items);
		this.mActivity = activity;
		this.mItems = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View rowView = convertView;
		final ViewHolder viewHolder;

		if (rowView == null) {
			LayoutInflater layoutInflater = (LayoutInflater) mActivity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = layoutInflater.inflate(R.layout.item_more_list, parent,
					false);
			viewHolder = new ViewHolder();
			viewHolder.text = (TextView) rowView.findViewById(R.id.more_item);

			rowView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) rowView.getTag();
		}

		MoreMenuItem item = mItems.get(position);

		if (item == null)
			return null;

		viewHolder.text.setText(item.getText());
		viewHolder.text.setEnabled(item.isActive());

		return rowView;

	}

}
