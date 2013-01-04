package vn.fiosoft.zop;

import java.util.List;

import vn.fiosoft.common.Util;
import vn.fiosoft.feature.FeatureActivity;
import vn.fiosoft.gps.CustomItemizedOverlay;
import vn.fiosoft.gps.CustomOverlayItem;
import vn.fiosoft.gps.GPSTracker;
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

public class MainActivity extends MapActivity implements OnClickListener {

	private final int ZOOM_DEFAULT = 18;
	private final String DATE_FORMAT = "MM-dd-yyyy HH:mm:ss";

	private MapView mapView;
	private List<Overlay> mapOverlays;
	private Drawable drawable;
	private CustomItemizedOverlay<CustomOverlayItem> itemizedOverlay;
	private GPSTracker mGPS;
	private Util mUtil;

	private ImageButton mSettingsButton;
	private ImageButton mCurrentLocationButton;
	private ImageButton mFeaturesButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mUtil = new Util();

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

		showMyLocation(ZOOM_DEFAULT);
	}

	protected void showMyLocation(int zoom) {
		// get current position
		mGPS = new GPSTracker(this);

		// check if GPS enabled
		if (mGPS.canGetLocation()) {

			Location myLocation = mGPS.getLocation();
			double lat = 0, lon = 0;
			long time = System.currentTimeMillis();
			if (myLocation != null) {
				lat = myLocation.getLatitude();
				lon = myLocation.getLongitude();
				time = myLocation.getTime();
			}

			itemizedOverlay = new CustomItemizedOverlay<CustomOverlayItem>(drawable, mapView);

			GeoPoint point = new GeoPoint((int) (lat * 1E6), (int) (lon * 1E6));
			CustomOverlayItem overlayItem = new CustomOverlayItem(time, point, mUtil.formatDateTime(time, DATE_FORMAT), getResources().getString(R.string.current_location), "http://ia.media-imdb.com/images/M/MV5BMTM1MTk2ODQxNV5BMl5BanBnXkFtZTcwOTY5MDg0NA@@._V1._SX40_CR0,0,40,54_.jpg");
			itemizedOverlay.addOverlay(overlayItem);

			mapOverlays.add(itemizedOverlay);

			final MapController mapControl = mapView.getController();
			mapControl.animateTo(point);
			mapControl.setZoom(zoom);

		} else {
			mGPS.showSettingsAlert();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

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

		mapOverlays.clear();
		showMyLocation(mapView.getZoomLevel());
	}

}
