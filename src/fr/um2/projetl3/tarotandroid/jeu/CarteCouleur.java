package fr.um2.projetl3.tarotandroid.jeu;

public class CarteCouleur extends Carte
{
	
	private Couleur couleur;
	private int ordre; // ? ou ordre (à renommer ?)
	private int valeur; // valeur en demi-points (1 pour les petites et les atouts, 3 pour les valets…)
	private int uid; // id « universel » (pour les communications) 
	
	public int getOrdre()
	{
		return ordre;
	}
	
	public Couleur getCouleur()
	{
		return couleur;
	}
	
	public int getUid()
	{
		return uid;
	}

	@Override
	public boolean isCouleur()
	{
		return true;
	}
	
	private CarteCouleur(Couleur couleur, int ordre, int valeur) // TODO: rajouter uid
	{
		this.couleur = couleur;
		this.ordre = ordre;
		this.valeur = valeur;
		// TODO: rajouter uid
	}
	
	// Initialisation des 56 cartes de couleur 
	// (pas de souci si on décide autre chose au final mais tout-à-l’heure on s’est
	// plus ou moins mis d’accord sur cette façon de faire. À discuter mercredi ou avant)
	
	public static Carte c1Tr = new CarteCouleur(Couleur.Trefle, 1, 1);
	public static Carte c2Tr = new CarteCouleur(Couleur.Trefle, 2, 1);
	public static Carte c3Tr = new CarteCouleur(Couleur.Trefle, 3, 1);
	public static Carte c4Tr = new CarteCouleur(Couleur.Trefle, 4, 1);
	public static Carte c5Tr = new CarteCouleur(Couleur.Trefle, 5, 1);
	public static Carte c6Tr = new CarteCouleur(Couleur.Trefle, 6, 1);
	public static Carte c7Tr = new CarteCouleur(Couleur.Trefle, 7, 1);
	public static Carte c8Tr = new CarteCouleur(Couleur.Trefle, 8, 1);
	public static Carte c9Tr = new CarteCouleur(Couleur.Trefle, 9, 1);
	public static Carte c10Tr = new CarteCouleur(Couleur.Trefle, 10, 1);
	public static Carte cVTr = new CarteCouleur(Couleur.Trefle, 11, 3); // (3+1)/2 = 2
	public static Carte cCTr = new CarteCouleur(Couleur.Trefle, 12, 5); // (5+1)/2 = 3
	public static Carte cDTr = new CarteCouleur(Couleur.Trefle, 13, 7); // …
	public static Carte cRTr = new CarteCouleur(Couleur.Trefle, 14, 9);
	
	public static Carte c1Ca = new CarteCouleur(Couleur.Carreau, 1, 1);
	public static Carte c2Ca = new CarteCouleur(Couleur.Carreau, 2, 1);
	public static Carte c3Ca = new CarteCouleur(Couleur.Carreau, 3, 1);
	public static Carte c4Ca = new CarteCouleur(Couleur.Carreau, 4, 1);
	public static Carte c5Ca = new CarteCouleur(Couleur.Carreau, 5, 1);
	public static Carte c6Ca = new CarteCouleur(Couleur.Carreau, 6, 1);
	public static Carte c7Ca = new CarteCouleur(Couleur.Carreau, 7, 1);
	public static Carte c8Ca = new CarteCouleur(Couleur.Carreau, 8, 1);
	public static Carte c9Ca = new CarteCouleur(Couleur.Carreau, 9, 1);
	public static Carte c10Ca = new CarteCouleur(Couleur.Carreau, 10, 1);
	public static Carte cVCa = new CarteCouleur(Couleur.Carreau, 11, 3);
	public static Carte cCCa = new CarteCouleur(Couleur.Carreau, 12, 5);
	public static Carte cDCa = new CarteCouleur(Couleur.Carreau, 13, 7);
	public static Carte cRCa = new CarteCouleur(Couleur.Carreau, 14, 9);
	
	public static Carte c1Co = new CarteCouleur(Couleur.Coeur, 1, 1);
	public static Carte c2Co = new CarteCouleur(Couleur.Coeur, 2, 1);
	public static Carte c3Co = new CarteCouleur(Couleur.Coeur, 3, 1);
	public static Carte c4Co = new CarteCouleur(Couleur.Coeur, 4, 1);
	public static Carte c5Co = new CarteCouleur(Couleur.Coeur, 5, 1);
	public static Carte c6Co = new CarteCouleur(Couleur.Coeur, 6, 1);
	public static Carte c7Co = new CarteCouleur(Couleur.Coeur, 7, 1);
	public static Carte c8Co = new CarteCouleur(Couleur.Coeur, 8, 1);
	public static Carte c9Co = new CarteCouleur(Couleur.Coeur, 9, 1);
	public static Carte c10Co = new CarteCouleur(Couleur.Coeur, 10, 1);
	public static Carte cVCo = new CarteCouleur(Couleur.Coeur, 11, 3);
	public static Carte cCCo = new CarteCouleur(Couleur.Coeur, 12, 5);
	public static Carte cDCo = new CarteCouleur(Couleur.Coeur, 13, 7);
	public static Carte cRCo = new CarteCouleur(Couleur.Coeur, 14, 9);
	
	public static Carte c1Pi = new CarteCouleur(Couleur.Pique, 1, 1);
	public static Carte c2Pi = new CarteCouleur(Couleur.Pique, 2, 1);
	public static Carte c3Pi = new CarteCouleur(Couleur.Pique, 3, 1);
	public static Carte c4Pi = new CarteCouleur(Couleur.Pique, 4, 1);
	public static Carte c5Pi = new CarteCouleur(Couleur.Pique, 5, 1);
	public static Carte c6Pi = new CarteCouleur(Couleur.Pique, 6, 1);
	public static Carte c7Pi = new CarteCouleur(Couleur.Pique, 7, 1);
	public static Carte c8Pi = new CarteCouleur(Couleur.Pique, 8, 1);
	public static Carte c9Pi = new CarteCouleur(Couleur.Pique, 9, 1);
	public static Carte c10Pi = new CarteCouleur(Couleur.Pique, 10, 1);
	public static Carte cVPi = new CarteCouleur(Couleur.Pique, 11, 3);
	public static Carte cCPi = new CarteCouleur(Couleur.Pique, 12, 5);
	public static Carte cDPi = new CarteCouleur(Couleur.Pique, 13, 7);
	public static Carte cRPi = new CarteCouleur(Couleur.Pique, 14, 9);
	
}
