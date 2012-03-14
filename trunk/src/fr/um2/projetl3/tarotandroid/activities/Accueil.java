package fr.um2.projetl3.tarotandroid.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.um2.projetl3.tarotandroid.R;

/**
 * Seconde page appelée après la page de garde
 * 
 * @author Jenn
 */

public class Accueil extends Activity 
{
	/**
	 * Les 3 boutons instanciés dans Accueil.xml 
	 */
	Button boutonCommencer;
	Button boutonReprendre;
	Button boutonTest;
	
    private void setLayout(int orientation) {
 
    	final int res = (orientation == Configuration.ORIENTATION_LANDSCAPE ? 
    	    	    	     R.layout.accueil_horizontal : 
    	    	    	     R.layout.accueil_vertical);
 
    	setContentView(res);
    }
	
    /**
     * Affichage des 3 boutons ainsi que les écouteurs
     * @see SplashScreen.java
     * @author Jennifer
     * 
     */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.accueil);
        setLayout(getResources().getConfiguration().orientation);
        
        boutonCommencer = (Button) findViewById(R.id.boutonCommencer);
        boutonReprendre = (Button) findViewById(R.id.boutonReprendre);
        boutonTest = (Button) findViewById(R.id.boutonTest);
        
        boutonCommencer.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		Toast.makeText(getBaseContext(),
        				"Vous allez commencer une nouvelle partie !",Toast.LENGTH_SHORT).show();
        	}
        });
 
        boutonReprendre.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		Toast.makeText(getBaseContext(),
        				"Vous allez reprendre la partie sauvegardée !",Toast.LENGTH_SHORT).show();
        	}
        });
 
        boutonTest.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		Toast.makeText(getBaseContext(),
        				"Heu...",Toast.LENGTH_SHORT).show();
        		startActivity(new Intent(getApplicationContext(), TestKevinActivity.class));
        	}
        });
        
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainmenu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.itemprefs:
			Intent myIntent = new Intent(getApplicationContext(), PreferencesActivity.class);
			startActivity(myIntent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
    @Override
    public void onConfigurationChanged(Configuration newConfig) {        
        super.onConfigurationChanged(newConfig);
 
        setLayout(newConfig.orientation);
    }
}
