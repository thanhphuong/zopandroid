package vn.fiosoft.zop;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import vn.fiosoft.gps.CustomItemizedOverlay;
import vn.fiosoft.gps.CustomOverlayItem;
import vn.fiosoft.gps.GPSTracker;
import vn.fiosoft.setting.SettingActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MainActivity extends MapActivity implements OnClickListener, OnItemClickListener {

	private boolean showPopupMenu;

	private MapView mapView;
	private List<Overlay> mapOverlays;
	private Drawable drawable;
	private CustomItemizedOverlay<CustomOverlayItem> itemizedOverlay;

	private Button mGroupName;
	private ImageButton mMoreButton;
	private ListView mPopupMenu;
	private List<MoreMenuItem> items;

	private ImageButton mCurrentLocationButton;

	private GPSTracker gps;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/digital-7.ttf");
		mGroupName = (Button) findViewById(R.id.group_name);
		mGroupName.setTypeface(tf);

		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);

		// List<Overlay> mapOverlays = mapView.getOverlays();
		// Drawable drawable =
		// this.getResources().getDrawable(R.drawable.androidmarker);
		// MapItemizedOverlay itemizedoverlay = new
		// MapItemizedOverlay(drawable, this);
		//
		// GeoPoint point = new GeoPoint(19240000,-99120000);
		// OverlayItem overlayitem = new OverlayItem(point, "Hola, Mundo!",
		// "I'm in Mexico City!");
		//
		// GeoPoint point2 = new GeoPoint(35410000, 139460000);
		// OverlayItem overlayitem2 = new OverlayItem(point2,
		// "Sekai, konichiwa!", "I'm in Japan!");
		//
		// itemizedoverlay.addOverlay(overlayitem);
		// itemizedoverlay.addOverlay(overlayitem2);
		// mapOverlays.add(itemizedoverlay);

		// MapController mapController = mapView.getController();
		//
		// ArrayList<GeoPoint> all_geo_points = getDirections(10.154929,
		// 76.390316, 10.015861, 76.341867);
		// GeoPoint moveTo = all_geo_points.get(0);
		// mapController.animateTo(moveTo);
		// mapController.setZoom(12);
		// mapView.getOverlays().add(new MyOverlay(all_geo_points));
		//
		// JSONObject jsonObject =
		// vn.fiosoft.zop.HttpClient1.SendHttpPost("https://twitter.com/statuses/user_timeline/vogella.json",
		// new JSONObject());

		// begin
		mMoreButton = (ImageButton) findViewById(R.id.more);
		mCurrentLocationButton = (ImageButton) findViewById(R.id.current_location);

		mMoreButton.setOnClickListener(this);
		mCurrentLocationButton.setOnClickListener(this);

		mPopupMenu = (ListView) findViewById(R.id.more_list);

		mPopupMenu.setVisibility(View.GONE);
		showPopupMenu = false;

		// map view
		mapOverlays = mapView.getOverlays();
		drawable = getResources().getDrawable(R.drawable.marker);

		// get current position
		gps = new GPSTracker(MainActivity.this);

		// check if GPS enabled
		if (gps.canGetLocation()) {

			itemizedOverlay = new CustomItemizedOverlay<CustomOverlayItem>(drawable, mapView);

			GeoPoint point = new GeoPoint((int) (gps.getLatitude() * 1E6), (int) (gps.getLongitude() * 1E6));
			CustomOverlayItem overlayItem = new CustomOverlayItem(point, "28/12/2012", "Bùi Thanh Phương Bùi Thanh Phương", "http://ia.media-imdb.com/images/M/MV5BMTM1MTk2ODQxNV5BMl5BanBnXkFtZTcwOTY5MDg0NA@@._V1._SX40_CR0,0,40,54_.jpg");
			itemizedOverlay.addOverlay(overlayItem);

			mapOverlays.add(itemizedOverlay);

			final MapController mc = mapView.getController();
			mc.animateTo(point);
			mc.setZoom(16);

		} else {
			// can't get location
			// GPS or Network is not enabled
			// Ask user to enable GPS/network in settings
			gps.showSettingsAlert();
		}

	}

	@Override
	protected void onResume() {
		super.onResume();

		refreshListAdapter();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	public ArrayList<GeoPoint> getDirections(final double lat1, final double lon1, final double lat2, final double lon2) {
		String url = "http://maps.googleapis.com:80/maps/api/directions/xml?origin=" + lat1 + "," + lon1 + "&destination=" + lat2 + "," + lon2 + "&sensor=false&units=metric";
		String tag[] = { "lat", "lng" };
		ArrayList<GeoPoint> list_of_geopoints = new ArrayList<GeoPoint>();
		HttpResponse response = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();
			HttpPost httpPost = new HttpPost(url);
			response = httpClient.execute(httpPost, localContext);
			InputStream in = response.getEntity().getContent();
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(in);
			if (doc != null) {
				NodeList nl1, nl2;
				nl1 = doc.getElementsByTagName(tag[0]);
				nl2 = doc.getElementsByTagName(tag[1]);
				if (nl1.getLength() > 0) {
					list_of_geopoints = new ArrayList<GeoPoint>();
					for (int i = 0; i < nl1.getLength(); i++) {
						Node node1 = nl1.item(i);
						Node node2 = nl2.item(i);
						double lat = Double.parseDouble(node1.getTextContent());
						double lng = Double.parseDouble(node2.getTextContent());
						list_of_geopoints.add(new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6)));
					}
				} else {
					// No points found
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list_of_geopoints;
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.more) {
			if (showPopupMenu == true) {
				showPopupMenu = false;
				mPopupMenu.setVisibility(View.GONE);
			} else {
				showPopupMenu = true;
				mPopupMenu.setVisibility(View.VISIBLE);
			}

		}

		if (id == R.id.current_location) {
			// create class object
			gps = new GPSTracker(MainActivity.this);

			// check if GPS enabled
			if (gps.canGetLocation()) {

				int latitude = (int) (gps.getLatitude() * 1000000);
				int longitude = (int) (gps.getLongitude() * 1000000);

				// \n is for new line
				Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

				List<Overlay> mapOverlays = mapView.getOverlays();
				mapOverlays.clear();
				Drawable drawable = this.getResources().getDrawable(R.drawable.location_map);
				MapItemizedOverlay itemizedoverlay = new MapItemizedOverlay(drawable, this);

				GeoPoint point = new GeoPoint(latitude, longitude);
				OverlayItem overlayitem = new OverlayItem(point, "Hi", "Current Location!");

				MapController mapController = mapView.getController();
				mapController.animateTo(point);
				itemizedoverlay.addOverlay(overlayitem);
				mapOverlays.add(itemizedoverlay);

			} else {
				// can't get location
				// GPS or Network is not enabled
				// Ask user to enable GPS/network in settings
				gps.showSettingsAlert();
			}
		}

	}

	@Override
	public void onBackPressed() {
		if (showPopupMenu == true) {
			showPopupMenu = false;
			mPopupMenu.setVisibility(View.GONE);
			return;
		}
		super.onBackPressed();
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

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		if (position == 1) {
			startActivity(new Intent(this, SettingActivity.class));			
		}

		showPopupMenu = false;
		mPopupMenu.setVisibility(View.GONE);

	}

	public void updateLocation(Location location) {
		
		mapOverlays.clear();
		
		itemizedOverlay = new CustomItemizedOverlay<CustomOverlayItem>(drawable, mapView);

		GeoPoint point = new GeoPoint((int) (location.getLatitude() * 1E6), (int) (location.getLongitude() * 1E6));
		CustomOverlayItem overlayItem = new CustomOverlayItem(point, "Tomorrow Never Dies (1997)", "(M gives Bond his mission in Daimler car)", "http://ia.media-imdb.com/images/M/MV5BMTM1MTk2ODQxNV5BMl5BanBnXkFtZTcwOTY5MDg0NA@@._V1._SX40_CR0,0,40,54_.jpg");
		itemizedOverlay.addOverlay(overlayItem);

		mapOverlays.add(itemizedOverlay);

		final MapController mc = mapView.getController();
		mc.animateTo(point);
		mc.setZoom(16);

	}

}
