package ca.sh6choi.beatsapp;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class GetImageTask extends AsyncTask<ImageID, Void, Bitmap> {
	private ImageView iV;
    @Override
    protected Bitmap doInBackground(ImageID... imgs) {
        Bitmap map = null;
        iV = imgs[0].getImageView();
        
        map = downloadImage(imgs[0].getArtist());
        return map;
    }

    // Sets the Bitmap returned by doInBackground
    @Override
    protected void onPostExecute(Bitmap result) {
    	try {
    		
    		if (iV == null){
    			Log.d("beatsApp", "hurr durr");
    			
    		}
    		if (result == null){
    			Log.d("beatsAPp", "herp der");
    		}
    		
    		iV.setImageBitmap(result);
    	} catch (Exception e){
    		Log.e("beatsApp", "Cannot get image: " + e.getMessage());
    	}
    }

    // Creates Bitmap from InputStream and returns it
    private Bitmap downloadImage(Artist ar) {
    	
    	if (ar.getThumbImage() != null){
    		return ar.getThumbImage();
    	}
        Bitmap bitmap = null;
        InputStream stream = null;

	    try {            	
	    	URL url = new URL("https://partner.api.beatsmusic.com/v1/api/artists/" + ar.getId()  
					+ "/images/default?size=small&client_id=9jnabxw7frdqjwc3y2ngfevk");
	    	
	    	HttpURLConnection ucon = (HttpURLConnection) url.openConnection();
	    	ucon.setInstanceFollowRedirects(false); 
	    	URL secondURL = new URL(ucon.getHeaderField("Location")); 
	    	HttpURLConnection conn = (HttpURLConnection) secondURL.openConnection();
	        conn.setRequestMethod("GET");
            conn.connect();
                
               
 
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            	
                stream = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(stream);
                stream.close();
            }
            
            ar.setThumbImage(bitmap);
        } catch (IOException iOE) {
            iOE.printStackTrace();
        }
        return bitmap;
    }

    // Makes HttpURLConnection and returns InputStream
    private InputStream getHttpConnection(URL url)
            throws IOException {
        InputStream stream = null;
        URLConnection connection = url.openConnection();

        try {
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();

            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                stream = httpConnection.getInputStream();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return stream;
    }
}