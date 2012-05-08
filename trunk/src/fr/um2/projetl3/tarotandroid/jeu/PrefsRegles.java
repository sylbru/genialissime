package fr.um2.projetl3.tarotandroid.jeu;

import static fr.um2.projetl3.tarotandroid.jeu.Context.*;
import static fr.um2.projetl3.tarotandroid.activities.Contexts.applicationContext;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

@SuppressWarnings("unused")
public class PrefsRegles // ! à ne pas toucher graphiquement Heykel se charge de la liaison graphique pour les préférences
						// (niavlys) de toute façon cette classe ne va rien contenir de graphique normalement 
{
	public static SharedPreferences sp;
	/**
	 * ---------------------------------------------------------------------------------------------------
	 * ---------------------------------- Sens du jeu-------------------------------------------------
	 * ---------------------------------------------------------------------------------------------------
	 */
	public static boolean sensInverseAiguillesMontre = true;
	
	/**
	 * ---------------------------------------------------------------------------------------------------
	 * ---------------------------------autorisation des contrat------------------------------------------
	 * ---------------------------------------------------------------------------------------------------
	 */
	public static boolean autoriserParole = false; 
	public static boolean autoriserPetite = true;
	public static boolean autoriserPousse = false; 
	public static boolean autoriserGAE = false; 
	// c'est quoi la GAE : pareil que garde mais avec un poid inferieur je n'aime pas du tout ce contrat mais apparrement c'est jouer par des gens = potentielement des futurees utilisateurs
	
	/**
	 * ---------------------------------------------------------------------------------------------------
	 * ---------------------------------- comptage des points---------------------------------------------
	 * ---------------------------------------------------------------------------------------------------
	 */
	
	public static boolean compterMisere = false; // j'ai mit false car c'est apas si courant que ça de compter la misère
	public static int pointsMisere = 10; // à voir selon méthode de comptage de points ? // la methode de comptage des point ne change pas là -10 pour les joueur n'ayant pas la misère et pour le joueur qui a la misère +10*nbrDeJoueur
	
	public static boolean petitAuBout = false;
	public static int pointpetitAuBout = 10;
	
	public static boolean roiAuBout = false;
	public static int pointRoiAuBout = 10;
	
	public static boolean arrondirA5 = true; //pour arrondir le comptage des points a 5, sinon il est arrondi a 10
	public static boolean arrondirA10 = false;
	public static boolean nePasArrondir = false;
	
	//Poignée : 
	
	//points selon le type de poignee
	// ces valeurs font un peu beaucoup :
	// JOR simple 10 double 20 et triple 30 ça me parait mieux
	public static int pointPoigneeSimple = 20; 
	public static int pointPoigneeDouble = 30;
	public static int pointPoigneeTriple = 40;
	
	//nombre d'atouts (pr partie a 4 joueurs) par défaut
	public static int PoigneeSimple = 10;
	public static int PoigneeDouble = 13;
	public static int PoigneeTriple = 15;
	
	//methode definissants le nombre d'atouts par rapport aux nombre de joueurs
	//  ? (niavlys) Pas sûr que ce soit bien de définir ça ici dans une fonction (à revoir en tout cas, pas utilisable pour l’instant)
	public static void nombreAtoutsPoignee()
	{
		if(P.getNombreDeJoueurs() == 3)
		{
			PoigneeSimple = 13;
			PoigneeDouble = 15;
			PoigneeTriple = 18;
		}
		else if (P.getNombreDeJoueurs() == 5)
		{
			PoigneeSimple = 8;
			PoigneeDouble = 10;
			PoigneeTriple = 13;
		}
		else 
		{
			PoigneeSimple = 10;
			PoigneeDouble = 13;
			PoigneeTriple = 15;
		}

	}
	
	public static boolean ManiereDeCompter = true;
	// il faudrait aussi implementer la façon de compter les points quel méthode on utilise ? voilà apres faut choisir 
	
	// jeu
	public static boolean autoriser3boutsDans1pli = true;
	
	
	/**
	 * ---------------------------------------------------------------------------------------------------
	 *------------------------------condition de fin de partie-------------------------------------------- 
	 * ---------------------------------------------------------------------------------------------------
	 */
	
	public static boolean conditionFinDePartie = true; // à false si pas de condition de fin de partie
	
		// quand on passe à true conditionFinScoreMax  il faut passer l'autre à false
		public static boolean conditionFinScoreMax = true;
		public static int scoreMax = 1000;
		
		public static boolean conditionFinDonnesMax = false;
		public static int donnesMax = 42;
		
		public static void preferencesActives()  //Methode permettant de, au moment d'une partie, de prendre en compte toutes les préférences
		{
	    	PrefsRegles.sensInverseAiguillesMontre = sp.getBoolean("SDJ", false);
	    	PrefsRegles.autoriserParole = sp.getBoolean("PAR", false);
	    	PrefsRegles.autoriserPetite = sp.getBoolean("PET", false);
	    	PrefsRegles.autoriserPousse = sp.getBoolean("POU", false);
	    	PrefsRegles.autoriserGAE = sp.getBoolean("GAE", false);
	    	
	    	if (sp.getString("SDJ", "Kevin")== "Kevin")
	    	{
	    		PrefsRegles.ManiereDeCompter = false;
	    	}
	    	else PrefsRegles.ManiereDeCompter = true;
	    	
	    	PrefsRegles.compterMisere = sp.getBoolean("MIS", false);
	    	PrefsRegles.petitAuBout = sp.getBoolean("PAB", false);
	    	PrefsRegles.roiAuBout = sp.getBoolean("RAB", false);
	    	PrefsRegles.autoriser3boutsDans1pli = sp.getBoolean("A3BDP", false);
	    	
	    	if (sp.getString("ARR", "Ne pas arrondir") == "Ne pas arrondir")
	    	{
	    		PrefsRegles.nePasArrondir = true;
	    		PrefsRegles.arrondirA5 = false;
	    		PrefsRegles.arrondirA10 = false;
	    	}
	    	else if (sp.getString("ARR", "Ne pas arrondir") == "Arrondir à 5")
	    	{
	    		PrefsRegles.nePasArrondir = false;
	    		PrefsRegles.arrondirA5 = true;
	    		PrefsRegles.arrondirA10 = false;
	    	}
	    	else
	    	{
	    		PrefsRegles.nePasArrondir = false;
	    		PrefsRegles.arrondirA5 = false;
	    		PrefsRegles.arrondirA10 = true;
	    	}
	    	
	    	if (sp.getString("CDS", "Conditions par défaut") == "Score maximum")
	    	{
	    		PrefsRegles.conditionFinDonnesMax = false;
	    		PrefsRegles.conditionFinScoreMax = true;
	    		PrefsRegles.scoreMax = sp.getInt("SMAX", 1000);
	    	}
	    	else if (sp.getString("CDS", "Conditions par défaut") == "Donnes maximales")
	    	{
	    		PrefsRegles.conditionFinDonnesMax = true;
	    		PrefsRegles.conditionFinScoreMax = false;
	    		PrefsRegles.donnesMax = sp.getInt("DMAX", 42);
	    	}
	    	else
	    	{
	    		PrefsRegles.conditionFinDonnesMax = false;
	    		PrefsRegles.conditionFinScoreMax = true;
	    		PrefsRegles.scoreMax = 1000;
	    	}
		}	
	
	
}
