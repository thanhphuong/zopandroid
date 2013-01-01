package vn.fiosoft.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public class HttpConnection {	
	
	public static final int ERROR_CONNECTION = -100;
	
	private final String URL_LOCATION = "http://zerocool24h.0fees.net/webservice/savelocation";
	private final String URL_LOGIN = "http://zerocool24h.0fees.net/webservice/login";
	
	public HttpConnection(){
		
	}
	
	public int login(final String email, final String password){
		HttpResponse response = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();
			HttpPost httpPost = new HttpPost(URL_LOGIN);
			
			// Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("email", email));
	        nameValuePairs.add(new BasicNameValuePair("password", password));	       
	        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			
			response = httpClient.execute(httpPost, localContext);
			
			// Get hold of the response entity (-> the data):
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				int result = ERROR_CONNECTION;
				
				// Read the content stream
				InputStream instream = entity.getContent();
				
				String content = convertStreamToString(instream);				
				
				instream.close();
				
				result = Integer.parseInt(content);

				return result;
			} 

		}
		catch (Exception e)
		{
			return ERROR_CONNECTION;
		}
		
		return ERROR_CONNECTION;
	}
	
	public int sendLocation(final int pid, final long time, final double lat, final double lon){
		HttpResponse response = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();
			HttpPost httpPost = new HttpPost(URL_LOCATION);
			
			// Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
	        nameValuePairs.add(new BasicNameValuePair("pid", String.valueOf(pid)));
	        nameValuePairs.add(new BasicNameValuePair("time", formatDateTime(time)));
	        nameValuePairs.add(new BasicNameValuePair("latitude", String.valueOf(lat)));
	        nameValuePairs.add(new BasicNameValuePair("longitude", String.valueOf(lon)));
	        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			
			response = httpClient.execute(httpPost, localContext);
			
			// Get hold of the response entity (-> the data):
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				int result = ERROR_CONNECTION;
				
				// Read the content stream
				InputStream instream = entity.getContent();
				
				String content = convertStreamToString(instream);				
				
				instream.close();
				
				result = Integer.parseInt(content);

				return result;
			} 

		}
		catch (Exception e)
		{
			return ERROR_CONNECTION;
		}
		
		return ERROR_CONNECTION;		
	}

	
	private String convertStreamToString(InputStream is) {

	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();

	    String line = null;
	    try {
	        while ((line = reader.readLine()) != null) {
	            sb.append((line));
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            is.close();
	        } catch (IOException e) {
	            return null;
	        }
	    }
	    return sb.toString();
	}
	
	private String formatDateTime(long time) {
		Date date = new Date(time);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		return formatter.format(date).toString();		
	}

}