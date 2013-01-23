package vn.fiosoft.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Util {
	public Typeface getFont(Context context) {
		return Typeface.createFromAsset(context.getAssets(), "fonts/digital-7.ttf");
	}

	public String formatDateTime(long time, String format) {
		Date date = new Date(time);
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date).toString();
	}

	public boolean checkNetwork(Context context) {
		// check network
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();

		if (info == null || info.getState() != NetworkInfo.State.CONNECTED)
			return false;
		return true;
	}

//	public ArrayList<GeoPoint> getDirections(final double lat1, final double lon1, final double lat2, final double lon2) {
//		String url = "http://maps.googleapis.com:80/maps/api/directions/xml?origin=" + lat1 + "," + lon1 + "&destination=" + lat2 + "," + lon2 + "&sensor=false&units=metric";
//		String tag[] = { "lat", "lng" };
//		ArrayList<GeoPoint> list_of_geopoints = new ArrayList<GeoPoint>();
//		HttpResponse response = null;
//		try {
//			HttpClient httpClient = new DefaultHttpClient();
//			HttpContext localContext = new BasicHttpContext();
//			HttpPost httpPost = new HttpPost(url);
//			response = httpClient.execute(httpPost, localContext);
//			InputStream in = response.getEntity().getContent();
//			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//			Document doc = builder.parse(in);
//			if (doc != null) {
//				NodeList nl1, nl2;
//				nl1 = doc.getElementsByTagName(tag[0]);
//				nl2 = doc.getElementsByTagName(tag[1]);
//				if (nl1.getLength() > 0) {
//					list_of_geopoints = new ArrayList<GeoPoint>();
//					for (int i = 0; i < nl1.getLength(); i++) {
//						Node node1 = nl1.item(i);
//						Node node2 = nl2.item(i);
//						double lat = Double.parseDouble(node1.getTextContent());
//						double lng = Double.parseDouble(node2.getTextContent());
//						list_of_geopoints.add(new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6)));
//					}
//				} else {
//					// No points found
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return list_of_geopoints;
//	}
}
