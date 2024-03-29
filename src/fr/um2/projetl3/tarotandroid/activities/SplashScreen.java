package fr.um2.projetl3.tarotandroid.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import fr.um2.projetl3.tarotandroid.R;

/**
 * Page de garde
 * Instanciation du Handler, élément principal de la classe
 * 
 * @author Jennifer
 */

public class SplashScreen extends Activity
{
	/**
	 * SPLASHTIME contient le décalage de 2 secondes
	 * STOPSPLASH est la demande d'arrêt
	 */
	private static final int STOPSPLASH = 0;
	private static final int PRESTOPSPLASH = 1;
    private static final long SPLASHTIME = 3000;
    private boolean recu = false;

    private Handler splashHandler = new Handler() 
    {
        /**
         * La fonction handleMessage est appelé lorsque msg.what = STOPSPLASH
         * et passe donc à l'activité Accueil
         * @param msg
         * 		Message qui contient le traitement demandé (ici une attente de 5secondes)
         * @throws erreurs possibles ? 
         * @author Jennifer
         */
        @Override
        public void handleMessage(Message msg) 
        {
            switch (msg.what) 
            {
            case STOPSPLASH:
            	if(!recu)
            	{
                	//remove SplashScreen from view
                	Intent intent = new Intent(SplashScreen.this, Accueil.class);
                    startActivity(intent);
                    finish();
            	}
                break;
            case PRESTOPSPLASH:
        		//remove SplashScreen from view
            	Intent intent = new Intent(SplashScreen.this, Accueil.class);
                startActivity(intent);
                finish();
            	break;
            }
            super.handleMessage(msg);
        }
    };//fin de la classe Handler
    
    
    /**
     * La fonction onCreate est appelée quand l'activité est créée
     * @param savedInstanceState
     * 		L'attribut vaut null au démarrage de l'application, 
     * 		sinon il contiendra un état sauvegardé de l'application
     * @throws erreurs possibles ?
     * @author Jennifer
     * 
     * La fonction onCreate appelle super.onCreate puis affiche le layout (splash_screen.xml)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	/**
    	 * @see handleMessage
    	 */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        View Carte_ecran = findViewById(R.id.Carte_ecran);
        Carte_ecran.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		Message msg = new Message();
        		msg.what = PRESTOPSPLASH;
                recu = true;
        		splashHandler.sendMessage(msg);
        	}
        });
        
        Message msg = new Message();
        msg.what = STOPSPLASH;
        splashHandler.sendMessageDelayed(msg, SPLASHTIME);
    }
}
