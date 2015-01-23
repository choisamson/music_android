package ca.sh6choi.beatsapp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends ListActivity {

	private List<Artist> artists = new ArrayList<Artist>();
	private ArtistListAdapter adapter;
	private int offset = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		offset = 0;
		setContentView(R.layout.activity_main);

//		ListView list = (ListView) findViewById(android.R.id.list);
//
//		list.setOnScrollListener(new OnScrollListener() {
//			private int preLast;
//
//			@Override
//			public void onScroll(AbsListView lw, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
//				switch (lw.getId()) {
//				case android.R.id.list:
//					// Make your calculation stuff here. You have all your
//					// needed info from the parameters of this function.
//
//					// Sample calculation to determine if the last
//					// item is fully visible.
//					final int lastItem = firstVisibleItem + visibleItemCount;
//					if (lastItem == totalItemCount) {
//						if (preLast != lastItem) { // to avoid multiple calls
//													// for last item
//							Log.d("Last", "Last");
//							Intent retrieve = new Intent(getApplicationContext(), ArtistService.class);
//							retrieve.putExtra("offset", offset);
//							startService(retrieve);
//							
//							Log.d("beatsApp", Integer.toString(lw.getCount()));
//							preLast = lastItem;
//						}
//					}
//				}
//
//			}
//
//			@Override
//			public void onScrollStateChanged(AbsListView lV, int scrollState) {
//				// TODO Auto-generated method stub
//
//			}
//
//		});
		
		Button loadingButton = (Button) findViewById(R.id.loadBtn);
		loadingButton.setVisibility(View.INVISIBLE);
		loadingButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent request = new Intent(v.getContext(), ArtistService.class);
				request.putExtra("offset", offset);
				
				//ListView list = (ListView) findViewById(android.R.id.list);
				artists.clear();
				adapter.notifyDataSetChanged();
				
				Button loadingButton = (Button) findViewById(R.id.loadBtn);
				loadingButton.setVisibility(View.INVISIBLE);
				ProgressBar loading = (ProgressBar) findViewById(R.id.progressBar1);
				loading.setVisibility(View.VISIBLE);
				startService(request);
			}
			
		});
		
		final Intent retrieve = new Intent(this, ArtistService.class);
		retrieve.putExtra("offset", offset);
		startService(retrieve);

		ServiceBroadcastReceiver sBR = new ServiceBroadcastReceiver();

		IntentFilter intentFilter = new IntentFilter(
				"ca.sh6choi.beatsapp.RESPONSE");
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

			Parcelable[] result = intent.getParcelableArrayExtra("artists");
			Log.d("beatsApp", "Data received");
			
			for (int i = 0; i < result.length; i++) {
				try {
					if (result[i] != null){
						artists.add((Artist) result[i]);
						offset++;
					}
				} catch (NullPointerException e){
					
				}
			}
			
			ProgressBar loading = (ProgressBar) findViewById(R.id.progressBar1);
			loading.setVisibility(View.INVISIBLE);
			
			if (adapter == null) {
				
				adapter = new ArtistListAdapter(getApplicationContext(),
						artists);
				setListAdapter(adapter);
				
				
			} else {
				
				adapter.notifyDataSetChanged();
			}
			
			Button loadingButton = (Button) findViewById(R.id.loadBtn);
			loadingButton.setVisibility(View.VISIBLE);
		}
	}
}