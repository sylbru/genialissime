package fr.um2.projetl3.tarotandroid.jeu;

public class Contrat
{

	private String nom;
	private int poids;
	private boolean chienRevele;// on voit le chien ou pas ?
	private boolean chienPourAttaque;// le chien est pour l'attaque

	public Contrat(String nom, int poids)
	{
		this.nom = nom;
		chienRevele = true;
		chienPourAttaque = false;
	}
	
	public Contrat(String nom, int poids, boolean chienRevele)
	{
		this.nom = nom;
		this.poids = poids;
		this.chienRevele = chienRevele;
		chienPourAttaque = false;
	}
	
	public Contrat(String nom, int poids, boolean chienRevele, boolean chienPourAttaque)
	{
		this.nom = nom;
		this.poids = poids;
		this.chienRevele = chienRevele;
		this.chienPourAttaque = chienPourAttaque;
	}

	public String getName()
	{
		return nom;
	}
	
	/*
	public int getPoids()
	{
		if (nom == NomDesContrats.AucunePrise)
			return 0;
		if (nom == NomDesContrats.Pouce)
			return 1;
		if (nom == NomDesContrats.Petite)
			return 2;
		if (nom == NomDesContrats.Garde)
			return 3;
		if (nom == NomDesContrats.GardeSans)
			return 4;
		if (nom == NomDesContrats.GardeContre)
			return 5;
		return 0;
	}*/
	/*
	 * remplace par une methode qui renvoit le poids en fonction du nom il faut
	 * avoir le poids par contrat quelquepart et ici me semble le plus intuitif,
	 * public void setPoids(int poids) { this.poids = poids; }
	 */
	
	public boolean isChienRevele()
	{
		return chienRevele;
	}
	
	public boolean isChienPourAttaque()
	{
		return chienPourAttaque;
	}

	public void setChienPourAttaque(boolean chienPourAttaque)
	{
		this.chienPourAttaque = chienPourAttaque;
	}
	
	// Initialisation des différents contrats existants (permettre d’en désactiver certains selon les options)
	
	public static Contrat PASSE = new Contrat("Passe", 0);
	public static Contrat PETITE = new Contrat("Petite", 1); // ! à voir entre pouce
	public static Contrat POUCE = new Contrat("Pouce", 2);   // !     et petite
	public static Contrat GARDE = new Contrat("Garde", 4);
	public static Contrat GARDE_SANS = new Contrat("Garde sans", 4, false);
	public static Contrat GARDE_CONTRE = new Contrat("Garde contre", 6, false, true);
	
}
