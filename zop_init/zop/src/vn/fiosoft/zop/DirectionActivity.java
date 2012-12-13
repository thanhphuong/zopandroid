package vn.fiosoft.zop;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.android.maps.GeoPoint;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class DirectionActivity extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_direction);
		ArrayList<GeoPoint> all_geo_points = getDirections(10.154929, 76.390316, 10.015861, 76.341867);
	}
	
//	private static final String TAG = "MainActivity";
//	private static final String URL = "http://www.gooogle.com:80";
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_direction);
//
//		// JSON object to hold the information, which is sent to the server
//		JSONObject jsonObjSend = new JSONObject();
//
//		try {
//			// Add key/value pairs
//			jsonObjSend.put("key_1", "value_1");
//			jsonObjSend.put("key_2", "value_2");
//
//			// Add a nested JSONObject (e.g. for header information)
//			JSONObject header = new JSONObject();
//			header.put("deviceType","Android"); // Device type
//			header.put("deviceVersion","2.0"); // Device OS version
//			header.put("language", "es-es");	// Language of the Android client
//			jsonObjSend.put("header", header);
//			
//			// Output the JSON object we're sending to Logcat:
//			Log.i(TAG, jsonObjSend.toString(2));
//
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//
//		// Send the HttpPostRequest and receive a JSONObject in return
//		JSONObject jsonObjRecv = vn.fiosoft.zop.HttpClient.SendHttpPost(URL, jsonObjSend);
//
//		/*
//		 *  From here on do whatever you want with your JSONObject, e.g.
//		 *  1) Get the value for a key: jsonObjRecv.get("key");
//		 *  2) Get a nested JSONObject: jsonObjRecv.getJSONObject("key")
//		 *  3) Get a nested JSONArray: jsonObjRecv.getJSONArray("key") 
//		 */
//
//
//	}
	
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
}
