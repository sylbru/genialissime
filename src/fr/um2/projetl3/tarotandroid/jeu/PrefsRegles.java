package fr.um2.projetl3.tarotandroid.jeu;

public class PrefsRegles
{
	// annonces
	public static boolean autoriserPetite = true;
	public static boolean autoriserPouce = false; // Pousse ? // c'est ecrit sur wikipedia ...
	public static boolean autoriserGAE = false; // c'est quoi la GAE : pareil que garde mais avec un poid inferieur je n'aime pas du tout ce contrat mais apparrement c'est jouer par des gens = potentielement des futurees utilisateurs
	
	// compte des points
	public static boolean compterMisere = true;
	public static int pointsMisere = 10; // à voir selon méthode de comptage de points ? 
	
	// il faudrait aussi implementer la façon de compter les points quel méthode on utilise ?  
	
	// jeu
	public static boolean autoriser3boutsDans1pli = true;
	
	// fin de la partie
	public static boolean conditionFinDePartie = true; // à false si pas de condition de fin de partie
		// conditions : faut-il faire des champs différents comme ça ?
		// (et s’assurer que les deux sont pas true en même temps)
		// ou bien faire un seul booléen ? (true -> score max ; false -> donnes max)
		public static boolean conditionFinScoreMax = true;
		public static int scoreMax = 1000;
		
		public static boolean conditionFinDonnesMax = false;
		public static int donnesMax = 42;
	
}
