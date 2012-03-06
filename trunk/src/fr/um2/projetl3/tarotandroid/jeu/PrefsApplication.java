package fr.um2.projetl3.tarotandroid.jeu;

import static fr.um2.projetl3.tarotandroid.jeu.Context.P;

public class PrefsApplication {

	/**
	 * ---------------------------------------------------------------------------------------------------
	 * ---------------------------------- Nom des joueurs-------------------------------------------------
	 * -----------------------à mettre dans preference application ---------------------------------------
	 * ---------------------------------------------------------------------------------------------------
	 */
	
	public static void setNomDuJoueur(int i,String s) //pour modifier nom du joueur
	{
		P.getJoueur(i).setNomDuJoueur(s);
	}
	
	public static void setNomsDesJoueurs(String s1, String s2, String s3) // modifier nom des joueurs pour partie a 3
	{
		P.getJoueur(1).setNomDuJoueur(s1);
		P.getJoueur(2).setNomDuJoueur(s2);
		P.getJoueur(3).setNomDuJoueur(s3);
	}
	
	public static void setNomsDesJoueurs(String s1, String s2, String s3, String s4) // modifier nom des joueurs pour partie a 4
	{
		P.getJoueur(1).setNomDuJoueur(s1);
		P.getJoueur(2).setNomDuJoueur(s2);
		P.getJoueur(3).setNomDuJoueur(s3);
		P.getJoueur(4).setNomDuJoueur(s4);
		
	}
	
	public static void setNomsDesJoueurs(String s1, String s2, String s3, String s4, String s5) // modifier nom des joueurs pour partie a 5
	{
		P.getJoueur(1).setNomDuJoueur(s1);
		P.getJoueur(2).setNomDuJoueur(s2);
		P.getJoueur(3).setNomDuJoueur(s3);
		P.getJoueur(4).setNomDuJoueur(s4);
		P.getJoueur(5).setNomDuJoueur(s5);
	}
	
	/**
	 * ---------------------------------------------------------------------------------------------------
	 * ---------------------------------- à completer-------------------------------------------------
	 * ---------------------------------------------------------------------------------------------------
	 */
}
