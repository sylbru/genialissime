package fr.um2.projetl3.tarotandroid.jeu;

public class Contrat
{

	private String nom;
	private int poids;
	private boolean chienRevele;// on voit le chien ou pas ?
	private boolean chienPourAttaque;// le chien est pour l'attaque ?

	public Contrat(String nom, int poids)
	{
		this.nom = nom;
		// ! la valeur de chienRevele et de chienPourAttaque depende du nom de contrat ‡ modifier :
		chienRevele = true;
		chienPourAttaque = false;
	}
	
	public Contrat(String nom, int poids, boolean chienRevele)
	{
		this.nom = nom;
		this.poids = poids;
		this.chienRevele = chienRevele;
		// ! pareil que pour le constructeur prÈcÈdent.
		chienPourAttaque = false;
	}
	
	public Contrat(String nom, int poids, boolean chienRevele, boolean chienPourAttaque)
	{
		this.nom = nom;
		this.poids = poids;
		this.chienRevele = chienRevele;
		this.chienPourAttaque = chienPourAttaque;
	}
	// ! Proposition d'un constructeur alternatif mais finalement je pense que Áa sert ‡ rien ‡ voir pour plus tard
	/**
	public Contrat(String nom)
	{
		this.nom = nom;
		this.poids = poids;
		
		switch(nom)
		{
			case AUCUN :  
				
			case PETITE :  
				
			case POUCE : 
			
			case GARDE :
			
			case GARDE_SANS :
			
			case GARDE_CONTRE :
				
			
		}
	}
	/**/
	public String getName()
	{
		return nom;
	}
	

	public int getPoids()
	{	
		return poids;
	}

	
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
	
	public String toString()
	{
		return getName();
	}
	
	// Initialisation des diff√©rents contrats existants (permettre d‚Äôen d√©sactiver certains selon les options)
	
	public static Contrat AUCUN = new Contrat("Aucune prise", -1); // utilis√© seulement pour contratEnCours,
																   // quand tout le monde a pass√©
	public static Contrat PASSE = new Contrat("Passe", 0);
	public static Contrat PETITE = new Contrat("Petite", 1); // ! √† voir entre pouce
	public static Contrat POUCE = new Contrat("Pouce", 2);   // !     et petite
	public static Contrat GARDE = new Contrat("Garde", 3);
	public static Contrat GARDE_SANS = new Contrat("Garde sans", 4, false);
	public static Contrat GARDE_CONTRE = new Contrat("Garde contre", 5, false, true);
	
}
