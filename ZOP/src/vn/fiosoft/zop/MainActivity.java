package vn.fiosoft.zop;

import java.util.ArrayList;
import java.util.List;

import vn.fiosoft.feature.FeatureActivity;
import vn.fiosoft.http.HttpConnection;
import vn.fiosoft.service.ZOPApplication;
import vn.fiosoft.setting.SettingActivity;
import vn.fiosoft.setting.accountmanage.Account;
import vn.fiosoft.setting.accountmanage.AccountManage;
import vn.fiosoft.zop.gps.GPSTracker;
import vn.fiosoft.zop.gps.MapItemizedOverlay;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import vn.fiosoft.common.Constants;
import vn.fiosoft.common.Util;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MainActivity extends MapActivity implements OnClickListener, OnItemClickListener {

	private final int ZOOM_DEFAULT = 18;

	private MapView mapView;
	private List<Overlay> mapOverlays;
	private Drawable drawable;
	private MapItemizedOverlay mapItemizedOverlay;
	private GPSTracker mGPS;

	private Button mSettingsButton;
	private ImageButton mCurrentLocationButton;	

	private TextView mNameTextView;

	private Account mAccount;

	private ListView mPopupMenu;
	private List<MoreMenuItem> items;
	private boolean showPopupMenu;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ZOPApplication.start();

		mPopupMenu = (ListView) findViewById(R.id.more_list);
		mapView = (MapView) findViewById(R.id.mapview);
		mCurrentLocationButton = (ImageButton) findViewById(R.id.my_location);
		mSettingsButton = (Button) findViewById(R.id.settings);		
		mNameTextView = (TextView) findViewById(R.id.name);

		mCurrentLocationButton.setOnClickListener(this);
		mSettingsButton.setOnClickListener(this);		

		mapView.setBuiltInZoomControls(true);

		mPopupMenu.setVisibility(View.GONE);
		showPopupMenu = false;

		// map view
		mapOverlays = mapView.getOverlays();
		drawable = getResources().getDrawable(R.drawable.marker);

		MapController mapControl = mapView.getController();
		mapControl.setZoom(ZOOM_DEFAULT);

	}

	@Override
	protected void onResume() {
		super.onResume();
		loadAccount();
		Location myLocation = getMyLocation();
		showMyLocation(myLocation, true);
		refreshListAdapter();
	}

	public void refreshListAdapter() {
		items = new ArrayList<MoreMenuItem>();

		// create items, when add new item, you must change height of list with
		// 1 item = 45dp + 1dp border
		Resources res = getResources();
		items.add(new MoreMenuItem(res.getString(R.string.clear_map), false));
		items.add(new MoreMenuItem(res.getString(R.string.settings), true));
		items.add(new MoreMenuItem(res.getString(R.string.help), true));

		mPopupMenu.setAdapter(new MoreListAdapter(this, items));
		mPopupMenu.setOnItemClickListener(this);

	}

	protected void loadAccount() {
		// load data
		AccountManage accountManage = new AccountManage(this);
		mAccount = accountManage.getAccountActivated();
		if (mAccount == null) {
			mNameTextView.setText("no account");
		} else {
			mNameTextView.setText(mAccount.getEmail());
		}
	}

	protected Location getMyLocation() {
		mGPS = new GPSTracker(this);
		// check if GPS enabled
		if (mGPS.canGetLocation()) {
			Location myLocation = mGPS.getLocation();
			return myLocation;

		} else {
			mGPS.showSettingsAlert(this);
		}
		return null;
	}

	protected void showMyLocation(Location myLocation, boolean isSetCenter) {

		if (myLocation == null)
			return;

		double lat = 0, lon = 0;

		lat = myLocation.getLatitude();
		lon = myLocation.getLongitude();

		mapOverlays.clear();
		mapItemizedOverlay = new MapItemizedOverlay(drawable, this);

		GeoPoint point = new GeoPoint((int) (lat * 1E6), (int) (lon * 1E6));
		OverlayItem overlayItem = new OverlayItem(point, getResources().getString(R.string.current_location), "");
		mapItemizedOverlay.addOverlay(overlayItem);

		mapOverlays.add(mapItemizedOverlay);

		MapController mapControl = mapView.getController();
		mapControl.setZoom(mapView.getZoomLevel());
		if (isSetCenter) {
			mapControl.animateTo(point);
		}

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();

		if (id == R.id.my_location) {
			Location myLocation = mGPS.getLocation();
			showMyLocation(myLocation, true);
		}

		

		if (id == R.id.settings) {
			// startActivity(new Intent(this, SettingActivity.class));
			// }
			// if (id == R.id.more) {
			if (showPopupMenu == true) {
				showPopupMenu = false;
				mPopupMenu.setVisibility(View.GONE);
			} else {
				showPopupMenu = true;
				mPopupMenu.setVisibility(View.VISIBLE);
			}

		}
	}

	public void updateLocation(Location location) {
		showMyLocation(location, false);
	}

	public void shareLocation(int share) {
		Util util = new Util();
		if (util.checkNetwork(this) == false) {
			// not network
			return;
		}
		switch (share) {
		case Constants.SHARE_LOCATION_FRIEND:
			break;
		case Constants.SHARE_LOCATION_GROUP:
			break;
		default:
			// send to server
			Location location = mGPS.getLocation();
			if (location != null) {
				HttpConnection connection = new HttpConnection();
				connection.sendLocation(1001, location.getTime(), location.getLatitude(), location.getLongitude());
			}
		}
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
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		if (position == 1) {
			startActivity(new Intent(this, SettingActivity.class));
		}

		showPopupMenu = false;
		mPopupMenu.setVisibility(View.GONE);

	}

}
