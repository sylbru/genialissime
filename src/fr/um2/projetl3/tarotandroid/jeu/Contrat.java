package fr.um2.projetl3.tarotandroid.jeu;

public class Contrat
{

	private String nom;
	private int ordre;
	private boolean chienRevele;// on voit le chien ou pas ?
	private boolean chienPourAttaque;// le chien est pour l'attaque

	public Contrat(String nom, int ordre)
	{
		this.nom = nom;
		chienRevele = true;
		chienPourAttaque = false;
	}
	
	public Contrat(String nom, int ordre, boolean chienRevele)
	{
		this.nom = nom;
		this.ordre = ordre;
		this.chienRevele = chienRevele;
		chienPourAttaque = false;
	}
	
	public Contrat(String nom, int ordre, boolean chienRevele, boolean chienPourAttaque)
	{
		this.nom = nom;
		this.ordre = ordre;
		this.chienRevele = chienRevele;
		this.chienPourAttaque = chienPourAttaque;
	}

	public String getName()
	{
		return nom;
	}
	

	public int getOrdre()
	{	
		return ordre;
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
	
	// Initialisation des différents contrats existants (permettre d’en désactiver certains selon les options)
	
	public static Contrat AUCUN = new Contrat("Aucune prise", -1); // utilisé seulement pour contratEnCours,
																   // quand tout le monde a passé
	public static Contrat PASSE = new Contrat("Passe", 0);
	public static Contrat PETITE = new Contrat("Petite", 1); // ! à voir entre pouce
	public static Contrat POUCE = new Contrat("Pouce", 2);   // !     et petite
	public static Contrat GARDE = new Contrat("Garde", 3);
	public static Contrat GARDE_SANS = new Contrat("Garde sans", 4, false);
	public static Contrat GARDE_CONTRE = new Contrat("Garde contre", 5, false, true);
	
}
