package vn.fiosoft.zop;

import java.util.List;

import vn.fiosoft.common.Util;
import vn.fiosoft.feature.FeatureActivity;
import vn.fiosoft.gps.GPSTracker;
import vn.fiosoft.gps.MapItemizedOverlay;
import vn.fiosoft.service.ZOPApplication;
import vn.fiosoft.setting.SettingActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MainActivity extends MapActivity implements OnClickListener {

	private final int ZOOM_DEFAULT = 18;

	private MapView mapView;
	private List<Overlay> mapOverlays;
	private Drawable drawable;
	private MapItemizedOverlay mapItemizedOverlay;
	private GPSTracker mGPS;

	private ImageButton mSettingsButton;
	private ImageButton mCurrentLocationButton;
	private ImageButton mFeaturesButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ZOPApplication.start();

		mapView = (MapView) findViewById(R.id.mapview);
		mCurrentLocationButton = (ImageButton) findViewById(R.id.my_location);
		mSettingsButton = (ImageButton) findViewById(R.id.settings);
		mFeaturesButton = (ImageButton) findViewById(R.id.features);

		mCurrentLocationButton.setOnClickListener(this);
		mSettingsButton.setOnClickListener(this);
		mFeaturesButton.setOnClickListener(this);

		mapView.setBuiltInZoomControls(true);

		// map view
		mapOverlays = mapView.getOverlays();
		drawable = getResources().getDrawable(R.drawable.marker);

		MapController mapControl = mapView.getController();
		mapControl.setZoom(ZOOM_DEFAULT);

	}

	protected void showMyLocation(int zoom) {
		// check if GPS enabled
		if (mGPS.canGetLocation()) {

			Location myLocation = mGPS.getLocation();

			double lat = 0, lon = 0;
			if (myLocation != null) {
				lat = myLocation.getLatitude();
				lon = myLocation.getLongitude();
			}

			mapOverlays.clear();
			mapItemizedOverlay = new MapItemizedOverlay(drawable, this);

			GeoPoint point = new GeoPoint((int) (lat * 1E6), (int) (lon * 1E6));
			OverlayItem overlayItem = new OverlayItem(point, getResources().getString(R.string.current_location), "");
			mapItemizedOverlay.addOverlay(overlayItem);

			mapOverlays.add(mapItemizedOverlay);

			MapController mapControl = mapView.getController();
			mapControl.animateTo(point);
			mapControl.setZoom(zoom);

		} else {
			mGPS.showSettingsAlert(this);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		mGPS = new GPSTracker(this);
		showMyLocation(mapView.getZoomLevel());
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mGPS != null)
			mGPS.stopUsingGPS();
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();

		if (id == R.id.my_location) {
			mapOverlays.clear();
			showMyLocation(mapView.getZoomLevel());
		}

		if (id == R.id.features) {
			startActivity(new Intent(this, FeatureActivity.class));
		}

		if (id == R.id.settings) {
			startActivity(new Intent(this, SettingActivity.class));
		}

	}

	public void updateLocation(Location location) {
		showMyLocation(mapView.getZoomLevel());
	}

}
