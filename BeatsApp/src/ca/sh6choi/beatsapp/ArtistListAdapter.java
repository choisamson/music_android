package ca.sh6choi.beatsapp;

import java.util.List;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ArtistListAdapter extends ArrayAdapter<Artist> {
	  private final Context context;
	  private final List<Artist> artists;

	  public ArtistListAdapter(Context context, List<Artist> artists) {
		super(context, R.layout.artist_list_adapter, artists);
	    
	    this.context = context;
	    this.artists = artists;
	  }

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.artist_list_adapter, parent, false);
	    TextView nameText = (TextView) rowView.findViewById(R.id.nameLine);
	    TextView secondText = (TextView) rowView.findViewById(R.id.secondLine);
	    ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
	    
	    if (imageView == null){
	    	Log.d("beatsApp", "missing image view");
	    }
	    nameText.setText(artists.get(position).getName());
	    secondText.setText("Popularity: " + artists.get(position).getPopularity());
		   
		   GetImageTask loadImageTask = new GetImageTask();
		   ImageID thumbnail = new ImageID(artists.get(position), imageView);
		   
		   try {
			   loadImageTask.execute(new ImageID[]{thumbnail});
		   } catch (Exception e){
			   Log.e("beatsApp", "Error loading image for " + artists.get(position).getName());
		   }

	    return rowView;
	  }
	}
