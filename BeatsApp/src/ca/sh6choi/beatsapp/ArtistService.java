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

public class ArtistService extends IntentService {

	/**
	 * A constructor is required, and must call the super IntentService(String)
	 * constructor with a name for the worker thread.
	 */
	public ArtistService() {
		super("ArtistService");
	}

	/**
	 * The IntentService calls this method from the default worker thread with
	 * the intent that started the service. When this method returns,
	 * IntentService stops the service, as appropriate.
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
		// Normally we would do some work here, like download a file.
		// For our sample, we just sleep for 5 seconds.
		int offset = intent.getIntExtra("offset", 0);
		String artistJSON = retrieveArtistData(offset);
		
		Intent response = new Intent();
		response.setAction("ca.sh6choi.beatsapp.RESPONSE");
		response.addCategory(Intent.CATEGORY_DEFAULT);

		Artist artists[] = new Artist[8];
		
		JSONObject artistData;
		try {
			artistData = new JSONObject(artistJSON);

			if (artistData.getString("code").compareToIgnoreCase("OK") == 0) {
				JSONArray artistJSONs = artistData.getJSONArray("data");

				
				 for (int i = 0; i < 8; i ++){ 
					 JSONObject curArtist = artistJSONs.getJSONObject(i);
					 Artist current = new Artist(curArtist);
					 artists[i] = current;
				 }
			}
			
		} catch (JSONException e) {
			Log.e("beatApp", "Error getting data");
			Log.e("beatAppError", e.toString());
		}

		response.putExtra("artists", artists);

		intent.putExtra("artists", artists);

		sendBroadcast(response);
	}

	private String retrieveArtistData(int offset) {
		BufferedReader in = null;
		try {
			HttpClient client = new DefaultHttpClient();
			client.getParams().setParameter(CoreProtocolPNames.USER_AGENT,
					"android");
			HttpGet request = new HttpGet();
			request.setHeader("Content-Type", "text/plain; charset=utf-8");
			HttpResponse response;
			
				try {
					request.setURI(new URI("https://partner.api.beatsmusic.com/v1/api/artists?order_by=popularity&limit=8&offset=" + offset + "&client_id=9jnabxw7frdqjwc3y2ngfevk"));
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