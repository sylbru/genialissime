package fr.um2.projetl3.tarotandroid.activities;

import fr.um2.projetl3.tarotandroid.jeu.PrefsRegles;
import static fr.um2.projetl3.tarotandroid.activities.Contexts.applicationContext;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import fr.um2.projetl3.tarotandroid.R;
import fr.um2.projetl3.tarotandroid.jeu.PrefsRegles;

public class PreferencesActivity extends PreferenceActivity implements
		OnSharedPreferenceChangeListener
{
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.prefs);
		
		/*final CheckBox SDJ=(CheckBox) findViewById(R.id.valide);
		SDJ.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener ()
		{
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if(SDJ.isChecked()) PrefsRegles.sensInverseAiguillesMontre=false;
				else PrefsRegles.sensInverseAiguillesMontre=true;*/
				
				/*TextView  affiche =(TextView)findViewById(R.id.affichage);
				affiche.setText(sensInverseAiguillesMontre.toString());
				<TextView
	android:id="@+id/affichage"
	android:text="Affiche : "
	android:layout_width="fill_parent"
	android:layout_height="fill_parent"
	android:orientation="vertical"
	/>*/
			/*}
			
		});*/
		
		
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
	
	
    public void onCreate1(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);}
    
 
}
