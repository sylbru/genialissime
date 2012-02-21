package fr.um2.projetl3.tarotandroid.jeu;

import android.R.string;

public class PrefsRegles
{
	// annonces
	public static boolean autoriserParole = false;
	public static boolean autoriserPetite = true;
	public static boolean autoriserPouce = false; // Pousse ? // c'est ecrit sur wikipedia ...(se renseigner quand on est pas sur d'un truc)
	public static boolean autoriserGAE = false; // c'est quoi la GAE : pareil que garde mais avec un poid inferieur je n'aime pas du tout ce contrat mais apparrement c'est jouer par des gens = potentielement des futurees utilisateurs
	
	// compte des points
	public static boolean compterMisere = false; // j'ai mit false car c'est apas si courant que ça de compter la misère
	public static int pointsMisere = 10; // à voir selon méthode de comptage de points ? // la methode de comptage des point ne change pas là -10 pour les joueur n'ayant pas la misère et pour le joueur qui a la misère +10*nbrDeJoueur
	
	public static boolean petitAuBout = false;
	public static int pointpetitAuBout = 10;
	
	public static boolean roiAuBout = false;
	public static int pointRoiAuBout = 10;
	
	//Poignée : à fair par Heykel
	
	
	public static boolean ManiereDeCompter = true;
	// il faudrait aussi implementer la façon de compter les points quel méthode on utilise ? voilà apres faut choisir 
	
	// jeu
	public static boolean autoriser3boutsDans1pli = true;
	
	// noms des joueur
	
	public static void setNomDuJoueur(int i,String s) //pour modifier nom du joueur
	{
		Partie.getJoueur(i).setNomDuJoueur(s);
	}
	
	public static void setNomsDesJoueurs(String s1, String s2, String s3) // modifier nom des joueurs pour partie a 3
	{
		Partie.getJoueur(1).setNomDuJoueur(s1);
		Partie.getJoueur(2).setNomDuJoueur(s2);
		Partie.getJoueur(3).setNomDuJoueur(s3);
	}
	
	public static void setNomsDesJoueurs(String s1, String s2, String s3, String s4) // modifier nom des joueurs pour partie a 4
	{
		Partie.getJoueur(1).setNomDuJoueur(s1);
		Partie.getJoueur(2).setNomDuJoueur(s2);
		Partie.getJoueur(3).setNomDuJoueur(s3);
		Partie.getJoueur(4).setNomDuJoueur(s4);
		
	}
	
	public static void setNomsDesJoueurs(String s1, String s2, String s3, String s4, String s5) // modifier nom des joueurs pour partie a 5
	{
		Partie.getJoueur(1).setNomDuJoueur(s1);
		Partie.getJoueur(2).setNomDuJoueur(s2);
		Partie.getJoueur(3).setNomDuJoueur(s3);
		Partie.getJoueur(4).setNomDuJoueur(s4);
		Partie.getJoueur(5).setNomDuJoueur(s5);
	}
	
	// fin de la partie
	public static boolean conditionFinDePartie = true; // à false si pas de condition de fin de partie
		// conditions : faut-il faire des champs différents comme ça ? 
		// (et s’assurer que les deux sont pas true en même temps)
		// ou bien faire un seul booléen ? (true -> score max ; false -> donnes max)
	
		// quand on passe à true conditionFinScoreMax  il faut passer l'autre à false
		public static boolean conditionFinScoreMax = true;
		public static int scoreMax = 1000;
		
		public static boolean conditionFinDonnesMax = false;
		public static int donnesMax = 42;
	
}
