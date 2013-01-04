package vn.fiosoft.feature;

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

public class FeatureAdapter extends ArrayAdapter<FeatureItem> {

	private List<FeatureItem> mItems;
	private Activity mActivity;

	public FeatureAdapter(Activity activity, List<FeatureItem> items) {
		super(activity, R.layout.item_feature_list, items);
		this.mItems = items;
		this.mActivity = activity;
	}

	private static class ViewHolder {
		public TextView text;
		public ImageView image;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		final ViewHolder viewHolder;

		if (rowView == null) {
			LayoutInflater layoutInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = layoutInflater.inflate(R.layout.item_feature_list, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.text = (TextView) rowView.findViewById(R.id.text);
			viewHolder.image = (ImageView) rowView.findViewById(R.id.image);

			rowView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) rowView.getTag();
		}

		FeatureItem item = mItems.get(position);

		if (item == null)
			return null;

		viewHolder.text.setText(item.getText());
		viewHolder.image.setImageResource(item.getImageResourceId());

		return rowView;
	}

}