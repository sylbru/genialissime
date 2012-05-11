package fr.um2.projetl3.tarotandroid.activities;

import static fr.um2.projetl3.tarotandroid.activities.Contexts.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import fr.um2.projetl3.tarotandroid.R;

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
	Button boutonOptions;
	Button boutonTest;
		
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
        setContentView(R.layout.accueil_horizontal);
        applicationContext = getApplicationContext();
        
        boutonCommencer = (Button) findViewById(R.id.boutonCommencer);
        boutonOptions = (Button) findViewById(R.id.butOptions);
        boutonTest = (Button) findViewById(R.id.boutonTest);
        
        boutonCommencer.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
    			Intent Intent = new Intent(getApplicationContext(), EcranJeu.class);
    			startActivity(Intent);
        	}
        });
        
        boutonOptions.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View arg0)
			{
				startActivity(new Intent(getApplicationContext(), PreferencesActivity.class));
			}
		});
        
        boutonTest.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
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
		case R.id.quitter:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
