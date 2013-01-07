package vn.fiosoft.gps;

import android.location.Location;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class MapOverlayItem extends OverlayItem {

	private Location location;
	
	public MapOverlayItem(GeoPoint point, String title, String snippet) {
		super(point, title, snippet);
		
	}

}
