package ca.sh6choi.beatsapp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ImageView;

public class Artist implements Parcelable{
	
	String id;
	String name;
	int popularity;
	boolean streamable;
	int totalAlbums;
	int totalSingles;
	int totalEPs;
	int totalLPs;
	int totalFreeplays;
	int totalCompilations;
	int totalTracks;
	boolean verified;
	int totalFollows;
	int totalFollowers;
	Bitmap thumbImage;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flag) {
		out.writeString(id);
		out.writeString(name);
		out.writeInt(popularity);
		out.writeString(Boolean.toString(streamable));
		out.writeInt(totalAlbums);
		out.writeInt(totalEPs);
		out.writeInt(totalLPs);
		out.writeInt(totalFreeplays);
		out.writeInt(totalCompilations);
		out.writeInt(totalTracks);
		out.writeString(Boolean.toString(verified));
		out.writeInt(totalFollows);
		out.writeInt(totalFollowers);
		
		//out.writeParcelable(thumbImage, 0);
		//thumbImage.writeToParcel(out, 0);
		/*Bitmap b = thumbImage;
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		b.compress(Bitmap.CompressFormat.PNG, 50, bs);
		out.writeByteArray(bs.toByteArray());*/
	}
	
	 public static final Parcelable.Creator<Artist> CREATOR = new Parcelable.Creator<Artist>() {
		 public Artist createFromParcel(Parcel in) {
		     return new Artist(in);
		 }
		
		 public Artist[] newArray(int size) {
		     return new Artist[size];
		 }
		};
	
	public Artist(JSONObject artistData){
		try {
			id = artistData.getString("id");
			name = artistData.getString("name");
			popularity = artistData.getInt("popularity");
			streamable = artistData.getBoolean("streamable");
			totalAlbums = artistData.getInt("total_albums");
			totalEPs = artistData.getInt("total_eps");
			totalLPs = artistData.getInt("total_lps");
			totalFreeplays = artistData.getInt("total_freeplays");
			totalCompilations = artistData.getInt("total_compilations");
			totalTracks = artistData.getInt("total_tracks");
			verified = artistData.getBoolean("verified");
			totalFollows = artistData.getInt("total_follows");
			totalFollowers = artistData.getInt("total_followed_by");
			//thumbImage = getImage();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	private Artist(Parcel in){
		id = in.readString();
		name = in.readString();
		popularity = in.readInt();
		streamable = Boolean.valueOf(in.readString());
		totalAlbums = in.readInt();
		totalEPs = in.readInt();
		totalLPs = in.readInt();
		totalFreeplays = in.readInt();
		totalCompilations = in.readInt();
		totalTracks = in.readInt();
		verified = Boolean.valueOf(in.readString());
		totalFollows = in.readInt();
		totalFollowers = in.readInt();
		//thumbImage = (Bitmap) in.readParcelable(null);
		/*byte[] im;
		in.readByteArray(im);
		Bitmap b = thumbImage;
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		b.compress(Bitmap.CompressFormat.PNG, 50, bs);
		out.writeByteArray(bs.toByteArray());*/
		
	}
	
	public String getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public int getPopularity(){
		return popularity;
	}
	
	public boolean getStreamable(){
		return streamable;
	}
	
	public int getTotalAlbums(){
		return totalAlbums;
	}
	
	public int getTotalEPs(){
		return totalEPs;
	}
	
	public int getTotalLPs(){
		return totalLPs;
	}
	
	public int getTotalFreeplays(){
		return totalFreeplays;
	}
	
	public int getTotalCompilations(){
		return totalCompilations;
	}
	
	public int getTotalTracks(){
		return totalTracks;
	}
	
	public boolean getVerified(){
		return verified;
	}
	
	public int getTotalFollows(){
		return totalFollows;
	}
	
	public int getTotalFollowers(){
		return totalFollowers;
	}
	
	public Bitmap getThumbImage(){
		return thumbImage;
	}
	
	public void setThumbImage(Bitmap img){
		thumbImage = img;
	}
	
	public ImageView setImage(ImageView iV){
		Bitmap bmp = getImage();
		
		try {
			bmp = Bitmap.createBitmap(bmp, bmp.getWidth()/2 - 125/2, 0, 125, 125);
		} catch (Exception e){
			Log.e("beatsapp", "failed to retrieve image");
			Log.d("beatsapp", "retrieving from " + Environment.getExternalStorageDirectory().getPath());
			//bmp = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getPath() + "/Blank.bmp");
			//bmp = Bitmap.createBitmap(bmp, bmp.getWidth()/2 - 125/2, bmp.getHeight()/2 - 125/2, 125, 125);
		}
		
		iV.setImageBitmap(bmp);
		
		return iV;
	}
	
	public Bitmap getThumb(){
		Bitmap bmp = getImage();
		
		try {
			bmp = Bitmap.createBitmap(bmp, bmp.getWidth()/2 - 125/2, 0, 125, 125);
		} catch (Exception e){
			Log.e("beatsapp", "Error getting image: " + e.getMessage());
			//bmp = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getPath() + "/Blank.bmp");
			//bmp = Bitmap.createBitmap(bmp, bmp.getWidth()/2 - 125/2, bmp.getHeight()/2 - 125/2, 125, 125);
		}
		
		return bmp;
	}
	
	public String getURL(){
		//return "https://partner.api.beatsmusic.com/v1/api/artists/" + id + "/images/default?size=small&client_id=9jnabxw7frdqjwc3y2ngfevk";
		String urlString = "https://partner.api.beatsmusic.com/v1/api/artists/" + id + "/images/default?size=small&client_id=9jnabxw7frdqjwc3y2ngfevk";
		try {
			URL url = new URL("https://partner.api.beatsmusic.com/v1/api/artists/" + id + "/images/default?size=small&client_id=9jnabxw7frdqjwc3y2ngfevk");

        	HttpURLConnection ucon = (HttpURLConnection) url.openConnection();
        	ucon.setInstanceFollowRedirects(false); 
        	URL secondURL = new URL(ucon.getHeaderField("Location"));
        	urlString = secondURL.toString();
        } catch (Exception e){
        	Log.e("beatsApp", "Error retrieving image:" + e.toString());
        	
        	Log.d("beatsApp", "Accesing: " + urlString);
        	
        	urlString = "https://partner.api.beatsmusic.com/v1/api/artists/" + id + "/images/default?size=small&client_id=9jnabxw7frdqjwc3y2ngfevk";
        }
		return urlString;
	}
	
	private Bitmap getImage(){
		URL url;
		try {
			url = new URL("https://partner.api.beatsmusic.com/v1/api/artists/" + id + "/images/default?size=small&client_id=9jnabxw7frdqjwc3y2ngfevk");
			
			InputStream stream = null;
			Bitmap bmp = null;
 
            try {            	
            	HttpURLConnection ucon = (HttpURLConnection) url.openConnection();
            	ucon.setInstanceFollowRedirects(false); 
            	URL secondURL = new URL(ucon.getHeaderField("Location")); 
            	HttpURLConnection conn = (HttpURLConnection) secondURL.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                
               
 
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                	
                    stream = conn.getInputStream();
                    bmp = BitmapFactory.decodeStream(stream);
                    stream.close();
                }
                
                thumbImage = bmp;
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            
			return bmp;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

}
