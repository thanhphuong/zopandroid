/***
 * Copyright (c) 2011 readyState Software Ltd
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package vn.fiosoft.gps;

import java.util.ArrayList;

import vn.fiosoft.http.HttpConnection;
import vn.fiosoft.setting.SettingActivity;
import vn.fiosoft.zop.library.mapviewballoons.BalloonOverlayView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;


public class CustomItemizedOverlay<Item extends OverlayItem> extends vn.fiosoft.zop.library.mapviewballoons.BalloonItemizedOverlay<CustomOverlayItem> {

	private ArrayList<CustomOverlayItem> m_overlays = new ArrayList<CustomOverlayItem>();
	private Context c;
	
	public CustomItemizedOverlay(Drawable defaultMarker, MapView mapView) {
		super(boundCenter(defaultMarker), mapView);
		c = mapView.getContext();
	}

	public void addOverlay(CustomOverlayItem overlay) {
	    m_overlays.add(overlay);
	    populate();
	}

	@Override
	protected CustomOverlayItem createItem(int i) {
		return m_overlays.get(i);
	}

	@Override
	public int size() {
		return m_overlays.size();
	}

	@Override
	protected boolean onBalloonTap(int index, CustomOverlayItem item) {
		GeoPoint point = item.getPoint();
//		Intent intent = new Intent(c, DetailLocationActivity.class);
//		intent.putExtra("latitude", point.getLatitudeE6()/1E6);
//		intent.putExtra("longitude", point.getLongitudeE6()/1E6);
//		c.startActivity(intent);
		
		HttpConnection connection = new HttpConnection();
//		int result = connection.sendLocation(1001, item.getTime(), point.getLatitudeE6()/1E6, point.getLongitudeE6()/1E6);
		int result = connection.login("btphuong2345@yahoo.com", "23041985");
		Toast.makeText(c, String.valueOf(result), Toast.LENGTH_SHORT).show();
		
		return true;
	}

	@Override
	protected BalloonOverlayView<CustomOverlayItem> createBalloonOverlayView() {
		// use our custom balloon view with our custom overlay item type:
		return new CustomBalloonOverlayView<CustomOverlayItem>(getMapView().getContext(), getBalloonBottomOffset());
	}

}
