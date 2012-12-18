package vn.fiosoft.settings;

import java.util.List;
import vn.fiosoft.zop.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SettingAdapter extends ArrayAdapter<SettingItem> {

	private List<SettingItem> mItems;
	private Activity mActivity;
		
	public SettingAdapter(Activity activity, List<SettingItem> items){
		super(activity, R.layout.settings_list_item, items);
		this.mItems = items;
		this.mActivity = activity;
	}
	
	private static class ViewHolder {
		public TextView text;
		public TextView label;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {	
		View rowView = convertView;
		final ViewHolder viewHolder;

		if (rowView == null) {
			LayoutInflater layoutInflater = (LayoutInflater) mActivity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = layoutInflater.inflate(R.layout.settings_list_item, parent,
					false);
			viewHolder = new ViewHolder();
			viewHolder.text = (TextView) rowView.findViewById(R.id.text);
			viewHolder.label = (TextView) rowView.findViewById(R.id.label);

			rowView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) rowView.getTag();
		}

		SettingItem item = mItems.get(position);

		if (item == null)
			return null;

		viewHolder.text.setText(item.getText());		
		if(item.getLabel().equals("")){
			viewHolder.label.setVisibility(View.GONE);
		}else{
			viewHolder.label.setText(item.getLabel());
			viewHolder.label.setVisibility(View.VISIBLE);
		}

		return rowView;
	}
	
}
