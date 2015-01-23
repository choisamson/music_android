package ca.sh6choi.beatsapp;

import android.widget.ImageView;

public class ImageID {
	private Artist ar;
	private ImageView iV;
	
	public ImageID(Artist ar, ImageView iV)
	{
		this.ar = ar;
		this.iV = iV;
	}
	
	public Artist getArtist(){
		return ar;
	}
	
	public ImageView getImageView(){
		return iV;
	}
}
