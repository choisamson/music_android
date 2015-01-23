package ca.sh6choi.beatsapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class APIService extends IntentService {

	/**
	 * A constructor is required, and must call the super IntentService(String)
	 * constructor with a name for the worker thread.
	 */
	public APIService() {
		super("APIService");
	}
	
	public final String API_URL = "https://partner.api.beatsmusic.com/v1/";
	public final String ENDPOINT = "api/search";
	
	private final String CLIENT_STRING = "client_id=9jnabxw7frdqjwc3y2ngfevk";

	/**
	 * The IntentService calls this method from the default worker thread with
	 * the intent that started the service. When this method returns,
	 * IntentService stops the service, as appropriate.
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
		
		boolean isCollection = intent.getBooleanExtra("isCollection", true);
		String APIresponse;
//		if (isCollection){
//			int offset = intent.getIntExtra("offset", 0);
//			APIresponse = retrieveCollectionData(offset);
//		} else {
			String query = intent.getStringExtra("query");
			APIresponse = retrieveSearch(query);
//		}

		Intent response = new Intent();
		response.setAction("ca.sh6choi.beatsapp.RESPONSE");
		response.addCategory(Intent.CATEGORY_DEFAULT);
		
		response.putExtra("isCollection", isCollection);
		response.putExtra("response", APIresponse);
		
		sendBroadcast(response);
	}
	
	private String retrieveSearch(String query){
		BufferedReader in = null;
		try {
			HttpClient client = new DefaultHttpClient();
			client.getParams().setParameter(CoreProtocolPNames.USER_AGENT,
					"android");
			HttpGet request = new HttpGet();
			request.setHeader("Content-Type", "text/plain; charset=utf-8");
			HttpResponse response;
			
				try {
					request.setURI(new URI(API_URL + ENDPOINT + 
							"?q=" + query + "&type=artist&limit=8&" + CLIENT_STRING));
					response = client.execute(request);
					in = new BufferedReader(new InputStreamReader(response
							.getEntity().getContent()));
				} catch (URISyntaxException e) {
					Log.e("beatApp", "Error getting data");
					Log.e("beatAppError", e.toString());
				} catch (ClientProtocolException e) {
					Log.e("beatApp", "Error getting data");
					Log.e("beatAppError", e.toString());
				} catch (IOException e) {
					Log.e("beatApp", "Error getting data");
					Log.e("beatAppError", e.toString());
				}
				
			
			StringBuffer sb = new StringBuffer("");
			String line = "";

			String NL = System.getProperty("line.separator");
			try {
				while ((line = in.readLine()) != null) {
					sb.append(line + NL);
				}
				in.close();
			} catch (Exception e) {

			}
			String page = sb.toString();
			return page;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					Log.d("BBB", e.toString());
				}
			}
		}
	}

	private String retrieveCollectionData(int offset) {
		BufferedReader in = null;
		try {
			HttpClient client = new DefaultHttpClient();
			client.getParams().setParameter(CoreProtocolPNames.USER_AGENT,
					"android");
			HttpGet request = new HttpGet();
			request.setHeader("Content-Type", "text/plain; charset=utf-8");
			HttpResponse response;
			
				try {
					request.setURI(new URI(API_URL + ENDPOINT + 
							"?offset=" + offset + "&fields=title&fields=id&fields=release_date"
							+ "&refs=artists&limit=8&" + CLIENT_STRING));
					response = client.execute(request);
					in = new BufferedReader(new InputStreamReader(response
							.getEntity().getContent()));
				} catch (URISyntaxException e) {
					Log.e("beatApp", "Error getting data");
					Log.e("beatAppError", e.toString());
				} catch (ClientProtocolException e) {
					Log.e("beatApp", "Error getting data");
					Log.e("beatAppError", e.toString());
				} catch (IOException e) {
					Log.e("beatApp", "Error getting data");
					Log.e("beatAppError", e.toString());
				}
				
			
			StringBuffer sb = new StringBuffer("");
			String line = "";

			String NL = System.getProperty("line.separator");
			try {
				while ((line = in.readLine()) != null) {
					sb.append(line + NL);
				}
				in.close();
			} catch (Exception e) {

			}
			String page = sb.toString();
			return page;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					Log.d("BBB", e.toString());
				}
			}
		}
	}
	
	private String retrieveAlbumData(String albumID) {
		BufferedReader in = null;
		try {
			HttpClient client = new DefaultHttpClient();
			client.getParams().setParameter(CoreProtocolPNames.USER_AGENT,
					"android");
			HttpGet request = new HttpGet();
			request.setHeader("Content-Type", "text/plain; charset=utf-8");
			HttpResponse response;
			
				try {
					Log.d("beatsApp", API_URL + ENDPOINT + "/" + albumID + "?" + CLIENT_STRING);
					request.setURI(new URI(API_URL + ENDPOINT + "/" + albumID + "?fields=title&fields=artist_display_name&"
							+ "fields=release_date&" + CLIENT_STRING));
					response = client.execute(request);
					in = new BufferedReader(new InputStreamReader(response
							.getEntity().getContent()));
				} catch (URISyntaxException e) {
					Log.e("beatApp", "Error getting data");
					Log.e("beatAppError", e.toString());
				} catch (ClientProtocolException e) {
					Log.e("beatApp", "Error getting data");
					Log.e("beatAppError", e.toString());
				} catch (IOException e) {
					Log.e("beatApp", "Error getting data");
					Log.e("beatAppError", e.toString());
				}
				
			
			StringBuffer sb = new StringBuffer("");
			String line = "";

			String NL = System.getProperty("line.separator");
			try {
				while ((line = in.readLine()) != null) {
					sb.append(line + NL);
				}
				in.close();
			} catch (Exception e) {

			}
			String page = sb.toString();
			return page;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					Log.d("BBB", e.toString());
				}
			}
		}
	}
}