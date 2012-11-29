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

import vn.fiosoft.gcm.CommonUtilities;
import vn.fiosoft.gcm.ServerUtilities;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.gcm.GCMRegistrar;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MainActivity extends MapActivity {

	private AsyncTask<Void, Void, Void> mRegisterTask;

	private Button mGroupName;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Typeface tf=Typeface.createFromAsset(getAssets(), "fonts/digital-7.ttf");
		mGroupName = (Button)findViewById(R.id.group_name);
		mGroupName.setTypeface(tf);
		
		
		
		//startGCMService();

		MapView mapView = (MapView) findViewById(R.id.mapview);
		//mapView.setBuiltInZoomControls(true);		
		 

		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(
				R.drawable.ic_launcher);
		HelloItemizedOverlay itemizedoverlay = new HelloItemizedOverlay(
				drawable, this);

		GeoPoint point = new GeoPoint(19240000, -99120000);
		OverlayItem overlayitem = new OverlayItem(point, "Hola, Mundo!",
				"I'm in Mexico City!");

		GeoPoint point2 = new GeoPoint(35410000, 139460000);
		OverlayItem overlayitem2 = new OverlayItem(point2, "Sekai, konichiwa!",
				"I'm in Japan!");

		itemizedoverlay.addOverlay(overlayitem);
		itemizedoverlay.addOverlay(overlayitem2);
		mapOverlays.add(itemizedoverlay);

		// MapController mapController = mapView.getController();
		//
		// ArrayList<GeoPoint> all_geo_points = getDirections(10.154929,
		// 76.390316, 10.015861, 76.341867);
		// GeoPoint moveTo = all_geo_points.get(0);
		// mapController.animateTo(moveTo);
		// mapController.setZoom(12);
		// mapView.getOverlays().add(new MyOverlay(all_geo_points));

	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	public void startGCMService() {
		if (CommonUtilities.SERVER_URL == null
				|| CommonUtilities.SENDER_ID == null)
			return;

		GCMRegistrar.checkDevice(this);

		GCMRegistrar.checkManifest(this);

		final String regId = GCMRegistrar.getRegistrationId(this);
		if (regId.equals("")) {
			GCMRegistrar.register(this, CommonUtilities.SENDER_ID);
		} else {
			// Device is already registered on GCM, check server.
			if (GCMRegistrar.isRegisteredOnServer(this)) {

			} else {
				final Context context = this;
				mRegisterTask = new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						boolean registered = ServerUtilities.register(context,
								regId);

						if (!registered) {
							GCMRegistrar.unregister(context);
						}
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						mRegisterTask = null;
					}

				};
				mRegisterTask.execute(null, null, null);
			}
		}
	}

	public ArrayList<GeoPoint> getDirections(final double lat1,
			final double lon1, final double lat2, final double lon2) {
		String url = "http://maps.googleapis.com:80/maps/api/directions/xml?origin="
				+ lat1
				+ ","
				+ lon1
				+ "&destination="
				+ lat2
				+ ","
				+ lon2
				+ "&sensor=false&units=metric";
		String tag[] = { "lat", "lng" };
		ArrayList<GeoPoint> list_of_geopoints = new ArrayList<GeoPoint>();
		HttpResponse response = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();
			HttpPost httpPost = new HttpPost(url);
			response = httpClient.execute(httpPost, localContext);
			InputStream in = response.getEntity().getContent();
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
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
						list_of_geopoints.add(new GeoPoint((int) (lat * 1E6),
								(int) (lng * 1E6)));
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

}
