package ca.sh6choi.beatsapp;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class AlbumOnClickListener implements OnClickListener {
	private final String artistID;

	public AlbumOnClickListener(String artistID) {
		this.artistID = artistID;
	}

	@Override
	public void onClick(View v) {
		Intent albumRequest = new Intent(v.getContext(), AlbumActivity.class);
		albumRequest.putExtra("isCollection", false);
		albumRequest.putExtra("albumID", artistID);
		albumRequest.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		v.getContext().startActivity(albumRequest);

	}
}
