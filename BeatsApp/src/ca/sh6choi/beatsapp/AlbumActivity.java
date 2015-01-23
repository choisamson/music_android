package ca.sh6choi.beatsapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class AlbumActivity extends Activity {
	private int offset = 0;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_album);
		Intent intent = getIntent();
		String albumID = intent.getStringExtra("albumID");
		Log.d("beatsApp", albumID);
		final Intent request = new Intent(this, APIService.class);
		request.putExtra("isCollection", false);
		request.putExtra("albumID", albumID);
		startService(request);

		ServiceBroadcastReceiver sBR = new ServiceBroadcastReceiver();

		IntentFilter intentFilter = new IntentFilter("ca.sh6choi.beatsapp.RESPONSE");
		intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
		registerReceiver(sBR, intentFilter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public class ServiceBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String response = intent.getStringExtra("response");
			Log.d("beatsApp", response);
			try {
				JSONObject responseJSON = new JSONObject(response);
				
				if (responseJSON.getString("code").compareToIgnoreCase("OK") == 0){
					//JSONArray data = responseJSON.getJSONArray("data");
				}
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

				RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.albumLayout);
				ProgressBar loading = (ProgressBar) findViewById(R.id.progressBar2);
				mainLayout.removeView(loading);
		}
	}
	
	private void populateTableWithAlbum(JSONObject data){
		
	}
}
