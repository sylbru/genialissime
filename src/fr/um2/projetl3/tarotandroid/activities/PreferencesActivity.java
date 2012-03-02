package fr.um2.projetl3.tarotandroid.activities;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;

import com.um2.projetl3.tarotandroid.R;

public class PreferencesActivity extends PreferenceActivity implements
		OnSharedPreferenceChangeListener
{
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.prefs);
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
		
	}	
	public void onSharedPreferenceChanged(SharedPreferences arg0, String arg1)
	{
		Log.i("GENIALISSIME", "onSharedPreferencesChanged()");
	}

}
