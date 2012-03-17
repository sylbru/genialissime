package fr.um2.projetl3.tarotandroid.activities;

import com.um2.projetl3.tarotandroid.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

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
    private static final long SPLASHTIME = 0;

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
                    //remove SplashScreen from view
                    Intent intent = new Intent(SplashScreen.this, Accueil.class);
                    startActivity(intent);
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
     * La fonction onCreate appelle super.onCreate puis affiche le layout (slash_screen.xml)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	/**
    	 * @see handleMessage
    	 */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        Message msg = new Message();
        msg.what = STOPSPLASH;
        splashHandler.sendMessageDelayed(msg, SPLASHTIME);
    }
}
