package fr.um2.projetl3.tarotandroid.jeu;

public class Contrat
{

	private String nom;
	private int poids;
	private boolean chienRevele;// on voit le chien ou pas ?
	private boolean chienPourAttaque;// le chien est pour l'attaque ?
	
	private int facteur;
	private int valeurContrat;

	public Contrat(String nom, int poids)
	{
		this.nom = nom;
		// ! la valeur de chienRevele et de chienPourAttaque depende du nom de contrat � modifier :
		chienRevele = true;
		chienPourAttaque = false;
	}
	
	public Contrat(String nom, int poids, boolean chienRevele)
	{
		this.nom = nom;
		this.poids = poids;
		this.chienRevele = chienRevele;
		// ! pareil que pour le constructeur pr�c�dent.
		chienPourAttaque = false;
	}
	
	public Contrat(String nom, int poids, boolean chienRevele, boolean chienPourAttaque)
	{
		this.nom = nom;
		this.poids = poids;
		this.chienRevele = chienRevele;
		this.chienPourAttaque = chienPourAttaque;
	}
	// ! Proposition d'un constructeur alternatif mais finalement je pense que �a sert � rien � voir pour plus tard
	
	public Contrat(String nom, int poids, boolean chienRevele, boolean chienPourAttaque, int facteur, int valeurContrat)
	{
		this.nom = nom;
		this.poids = poids;
		this.chienRevele = chienRevele;
		this.chienPourAttaque = chienPourAttaque;
		this.facteur = facteur;
		this.valeurContrat = valeurContrat;
	}
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
	
	public boolean controleContrats(Contrat a,Contrat b)
	{
		if(b.getPoids()==0)
		{
			return true;
		}
		else if(a.getPoids()==5) return false;//?ce cas ne devrait jamais arriver on arrete de demander les jouers une fois le plus grand contract fait
		else if(a.getPoids()<b.getPoids()) return true;
		else return false;
		
	}
	
	// Initialisation des différents contrats existants (permettre d’en désactiver certains selon les options)
	
	public static Contrat AUCUN = new Contrat("Aucune prise", -1); // utilisé seulement pour contratEnCours,
																   // quand tout le monde a passé
	public static Contrat PASSE = new Contrat("Passe", 0);
	public static Contrat PETITE = new Contrat("Petite", 1); // ! à voir entre pouce
	public static Contrat POUSSE = new Contrat("Pousse", 2);   // !     et petite
	public static Contrat GARDE = new Contrat("Garde", 3);
	public static Contrat GARDE_SANS = new Contrat("Garde sans", 4, false);
	public static Contrat GARDE_CONTRE = new Contrat("Garde contre", 5, false, true);
	
	public void initialiseContrat() //methode qui autorise ou non les contrats selon le type de comptage de points
	{
		Contrat PASSE = new Contrat("Passe", 0, true, false, 0, 0);
		
		if (PrefsRegles.ManiereDeCompter == true)
		{
			if(PrefsRegles.autoriserParole)
			{
				Contrat PAROLE = new Contrat("Parole", 10, true, true, 1, 25);
			}
			if(PrefsRegles.autoriserPetite)
			{
				Contrat PETITE = new Contrat("Petite", 20, true, true, 1, 25);
			}
			if(PrefsRegles.autoriserGAE)
			{
				Contrat GAE = new Contrat("GAE", 40, true, true, 2, 25);
			}
			Contrat GARDE = new Contrat("Garde", 50, true, true, 2, 25);
			Contrat GARDE_SANS = new Contrat("Garde sans", 60, false, true, 4, 25);
			Contrat GARDE_CONTRE = new Contrat("Garde contre", 70, false, false, 6, 25);
		}
		else
		{
			if(PrefsRegles.autoriserParole)
			{
				Contrat PAROLE = new Contrat("Parole", 10, true, true, 1, 20);
			}
			if(PrefsRegles.autoriserPetite)
			{
				Contrat PETITE = new Contrat("Petite", 20, true, true, 1, 20);
			}
			if(PrefsRegles.autoriserPousse)
			{
				Contrat POUSSE = new Contrat("Pousse", 30, true, true, 1, 20);
			}
			if(PrefsRegles.autoriserGAE)
			{
				Contrat GAE = new Contrat("GAE", 40, true, true, 1, 40);
			}
			Contrat GARDE = new Contrat("Garde", 50, true, true, 1, 40);
			Contrat GARDE_SANS = new Contrat("Garde sans", 60, false, true, 1, 80);
			Contrat GARDE_CONTRE = new Contrat("Garde contre", 70, false, false, 1, 160);
		
		}
	}
	
	
	
}
