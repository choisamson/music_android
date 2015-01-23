package ca.sh6choi.beatsapp;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.sh6choi.beatsapp.ArtistService;
import ca.sh6choi.beatsapp.R;
import ca.sh6choi.beatsapp.MainActivity.ServiceBroadcastReceiver;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.test.ServiceTestCase;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest{

    MainActivity activity;
    final CountDownLatch signal = new CountDownLatch(1);
    @Before
    public void setup()
    {
        this.activity = Robolectric.buildActivity(MainActivity.class).create().get();
        
        Intent request = new Intent(activity.getApplicationContext(), ArtistService.class);
    	request.putExtra("offset", 0);
    	activity.startService(request);
    }

    @Test
    public void testLoader() throws Exception 
    {
        ListView list = (ListView)this.activity.findViewById(android.R.id.list);
        
        assertTrue(list != null);
    }
    
    @Test
    public void testResponse(){
    	Intent request = new Intent(activity.getApplicationContext(), ArtistService.class);
    	request.putExtra("offset", 0);
    	activity.startService(request);
    	
    	try {
			Thread.sleep(10000);
			ListView list = (ListView) activity.findViewById(android.R.id.list);
	    	
	    	//assertTrue(list.getChildCount() == 8);
	    	
	    	View listEntry = list.getChildAt(0);
	    	
	    	TextView artistName = (TextView) listEntry.findViewById(R.id.nameLine);
	    	
	    	assertTrue(artistName.getText().toString().compareToIgnoreCase("Johann Sebastian Bach") == 0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 

    	
    }
    
    @Test
    public void testLoadButton(){
    	Button loadBtn = (Button) activity.findViewById(R.id.loadBtn);
    	
    	assertTrue(loadBtn.getVisibility() == View.VISIBLE);
    	
    	loadBtn.performClick();
    	
    	assertTrue(loadBtn.getVisibility() == View.INVISIBLE);
    	
    	ListView list = (ListView) activity.findViewById(android.R.id.list);
    	assertTrue(list.getChildCount() == 8);
    	
    	View listEntry = list.getChildAt(0);
    	
    	TextView artistName = (TextView) listEntry.findViewById(R.id.nameLine);
    	
    	assertTrue(artistName.getText().toString().compareToIgnoreCase("Frank Sinatra") == 0);
    	
    	
    }
}
