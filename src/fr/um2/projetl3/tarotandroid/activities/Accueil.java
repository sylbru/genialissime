package fr.um2.projetl3.tarotandroid.activities;

import com.um2.projetl3.tarotandroid.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
        setContentView(R.layout.accueil);
        
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
        	}
        });
        
    }
}
