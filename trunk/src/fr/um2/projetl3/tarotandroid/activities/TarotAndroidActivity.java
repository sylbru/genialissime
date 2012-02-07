package fr.um2.projetl3.tarotandroid.activities;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.um2.projetl3.tarotandroid.R;

public class TarotAndroidActivity extends Activity
{
	private String data; // ? (?)
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

   @Override
    protected void onPause()
    {
    	super.onPause();
    	// useless atm
    }
}
