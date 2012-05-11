package fr.um2.projetl3.tarotandroid.jeu;

import java.util.Arrays;
import java.util.Vector;

@SuppressWarnings("unused")
public class Contrat
{

	private String nom;
	private int poids;
	private boolean chienRevele;// on voit le chien ou pas ?
	private boolean chienPourAttaque;// le chien est pour l'attaque ?
	
	private int facteur;
	private int valeurContrat;
	
	public static boolean bavard = false;

	private boolean autorisé; // cool si pas de problème avec l’accent (en tout cas ça semble autorisé en Java)

	/*
	 * --------------------------------------------------------------------------------------------
	 * -----------------------------------Constructeurs--------------------------------------------
	 * --------------------------------------------------------------------------------------------
	 */
	private Contrat(boolean autorisé, String nom, int poids)
	{
		this.autorisé = autorisé;
		this.nom = nom;
		this.poids = poids;
		// ! la valeur de chienRevele et de chienPourAttaque depende du nom de contrat é modifier :
		// En fait l’idée c’est qu’on définit les contrats statiquement dans cette classe, et qu’on interdit
		// de les construire ailleurs (constructeurs privés).
		chienRevele = true;
		chienPourAttaque = true;
	}
	
	private Contrat(boolean autorisé, String nom, int poids, boolean chienRevele)
	{
		this(autorisé, nom, poids);
		this.chienRevele = chienRevele;
		// ! pareil que pour le constructeur précédent.
		chienPourAttaque = true;
	}
	
	private Contrat(boolean autorisé, String nom, int poids, boolean chienRevele, boolean chienPourAttaque)
	{
		this(autorisé, nom, poids, chienRevele);
		this.chienPourAttaque = chienPourAttaque;
	}

	/*
	 * ----------------------------------------------------------------------------------------------------
	 * ----------------------------------- getteur / setteur ----------------------------------------------
	 * ----------------------------------------------------------------------------------------------------
	 */
	
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
	
	private void setFacteur(int facteur)
	{
		this.facteur = facteur;
	}
	
	public int getFacteur()
	{
		return facteur;
	}
	
	private void setValeurContrat(int valeurContrat)
	{
		this.valeurContrat = valeurContrat;
	}
	
	public int getValeurContrat()
	{
		return valeurContrat;
	}
	
	public void setAutorisé(boolean valeur)
	{
		// if (bavard) System.out.println("On fait un setAutorisé de "+this.nom+" à "+valeur);
		this.autorisé = valeur;
	}
	
	public void autoriser()
	{
		setAutorisé(true);
	}
	
	public void interdire()
	{
		setAutorisé(false);
	}
	
	public boolean getAutorisé()
	{
		return autorisé;
	}
	
	public String toString()
	{
		// return super.toString() + " " + getName();
		return getName();
	}
	
	/*
	 * ----------------------------------------------------------------------------------------------
	 * ----------------------------------- méthodes spécifique --------------------------------------
	 * ----------------------------------------------------------------------------------------------
	 */
	
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
	
	/*
	 * ------------------------------------------------------------------------------------------------
	 * ---------------------------------Initialisation des contrats------------------------------------
	 * ------------------------------------Deux façon differentes--------------------------------------
	 * -------------------Les contrats à initialisées dépende des preference---------------------------
	 * -------------------Quand on lance une partie les préférence sont déjà définie-------------------
	 * ------------------------------------------------------------------------------------------------
	 */
	
	// Initialisation des différents contrats existants (permettre d’en désactiver certains selon les options)
	
	public static Contrat AUCUN = new Contrat(false, "Aucune prise", -1); // utilisé seulement pour contratEnCours,
																   		 // quand tout le monde a passé
	public static Contrat PASSE = new Contrat(true, "Passe", 0);
	public static Contrat PAROLE = new Contrat(false, "Parole", 1);
	public static Contrat PETITE = new Contrat(false, "Petite", 2);
	public static Contrat POUSSE = new Contrat(false, "Pousse", 3);
	public static Contrat GAE = new Contrat(false, "G.A.E.", 4);
	public static Contrat GARDE = new Contrat(true, "Garde", 5);
	public static Contrat GARDE_SANS = new Contrat(true, "Garde sans", 6, false);
	public static Contrat GARDE_CONTRE = new Contrat(true, "Garde contre", 7, false, false);

	public static Vector<Contrat> listeContrats = new Vector<Contrat>();
	static
	{
		listeContrats.add(Contrat.PASSE);
		listeContrats.add(Contrat.PAROLE);
		listeContrats.add(Contrat.PETITE);
		listeContrats.add(Contrat.POUSSE);
		listeContrats.add(Contrat.GAE);
		listeContrats.add(Contrat.GARDE);
		listeContrats.add(Contrat.GARDE_SANS);
		listeContrats.add(Contrat.GARDE_CONTRE);
	}
	public static Vector<Contrat> getListeContratsDisponibles()
	{
		Vector<Contrat> listeContratsDisponibles = new Vector<Contrat>();
		listeContratsDisponibles = (Vector<Contrat>) listeContrats.clone();
		if (bavard) System.out.println("tous : "+listeContratsDisponibles);
		
		for(Contrat c: listeContrats)
		{
			if(!c.autorisé)
			{
				if (bavard) System.out.println("On enlève "+c);
				listeContratsDisponibles.remove(c);
			} else
				if (bavard) System.out.println("On garde "+c);
		}
		
		return listeContratsDisponibles;
	}
	
	
	public static void initialiserContrats() // methode qui autorise ou non les contrats selon le type de comptage de points
	{
		if (PrefsRegles.ManiereDeCompter == true) // valeurContrat toujours à 25, c’est facteur qui change
		{
			if(PrefsRegles.autoriserParole)
			{
				Contrat.PAROLE.autoriser();
				Contrat.PAROLE.setFacteur(1);
				Contrat.PAROLE.setValeurContrat(25);
			}
			if(PrefsRegles.autoriserPetite)
			{
				Contrat.PETITE.autoriser();
				Contrat.PETITE.setFacteur(1);
				Contrat.PETITE.setValeurContrat(25);
			}
			if(PrefsRegles.autoriserGAE)
			{
				Contrat.GAE.autoriser();
				Contrat.GAE.setFacteur(2);
				Contrat.GAE.setValeurContrat(25);
			}
			Contrat.GARDE.setValeurContrat(25);
			Contrat.GARDE_SANS.setValeurContrat(25);
			Contrat.GARDE_CONTRE.setValeurContrat(25);
			
			Contrat.GARDE.setFacteur(2);
			Contrat.GARDE_SANS.setFacteur(4);
			Contrat.GARDE_CONTRE.setFacteur(6);
			
			if (bavard) System.out.println("Interdiction de la Pousse car ManiereDeCompter == true");
			Contrat.POUSSE.interdire();
			/*
			 * Alors je pense que je doit m'expliquer :
			 * on ne peux pas compter la pousse avec cette maniere de compter
			 * cette maniere fait intervenir des facteur celui de la pousse ne peux pas etre egal à celui de la petite ni celui de la garde
			 * et ne ^peut non plus être egal à 1.5 ( Faussage de calcul)
			 * 
			 * c'est pourquoi avec cette maniere de compter la pousse est forcement interdite.
			 * 
			 * Avis contraire se pronnoncer (mail)
			 * 
			 */
		}
		else // facteur toujours à 1, c’est valeurContrat qui change
		{
			if(PrefsRegles.autoriserParole)
			{
				Contrat.PAROLE.autoriser();
				Contrat.PAROLE.setFacteur(1);
				Contrat.PAROLE.setValeurContrat(10);
			}
			if(PrefsRegles.autoriserPetite)
			{
				Contrat.PETITE.autoriser();
				Contrat.PETITE.setFacteur(1);
				Contrat.PETITE.setValeurContrat(10);
				// pour moi c’était 10 pour la petite et 20 pour la pousse, donc je l’ai changé,
				// mais si quelqu’un est pas d’accord on pourra en discuter :)
			}
			if(PrefsRegles.autoriserPousse)
			{
				Contrat.POUSSE.autoriser();
				Contrat.POUSSE.setFacteur(1);
				Contrat.POUSSE.setValeurContrat(20);
			}
			if(PrefsRegles.autoriserGAE)
			{
				Contrat.GAE.autoriser();
				Contrat.GAE.setFacteur(1);
				Contrat.GAE.setValeurContrat(40);
			}
			Contrat.GARDE.setFacteur(1);
			Contrat.GARDE_SANS.setFacteur(1);
			Contrat.GARDE_CONTRE.setFacteur(1);
			
			Contrat.GARDE.setValeurContrat(40);
			Contrat.GARDE_SANS.setValeurContrat(80);
			Contrat.GARDE_CONTRE.setValeurContrat(160);
		}
	}
	
	
	
}
