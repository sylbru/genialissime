package fr.um2.projetl3.tarotandroid.activities;

import android.content.Context;

/* Ici, on peut mettre des applicationContext, des activityContext (ou autres), et y accéder
 * depuis toute classe où on aura fait un static import de ça.
 */

public class Contexts
{
	public static Context applicationContext; // pour accéder aux ressources depuis CarteGraphique
	
	public static final String TAG = "Genialissime";
}
