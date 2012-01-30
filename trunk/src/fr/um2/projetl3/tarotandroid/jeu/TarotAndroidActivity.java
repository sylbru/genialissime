package fr.um2.projetl3.tarotandroid.jeu;

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
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    private static final int SAD_ID = 1;
    
    @Override
    protected void onPause()
    {
    	super.onPause();
    	
    	String ns = Context.NOTIFICATION_SERVICE;
    	NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);

    	int icon = R.drawable.ic_launcher;
    	CharSequence notifTexte = "Om nom nom";
    	long when = System.currentTimeMillis();

    	Notification notification = new Notification(icon, notifTexte, when);
    	
    	Context context = getApplicationContext();
    	CharSequence contentTitle = "Vous avez quitté ?";
    	CharSequence contentText = "Pourquoi ?";
    	Intent notificationIntent = new Intent(this, TarotAndroidActivity.class);
    	PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

    	notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

    	mNotificationManager.notify(SAD_ID, notification);
    }
}