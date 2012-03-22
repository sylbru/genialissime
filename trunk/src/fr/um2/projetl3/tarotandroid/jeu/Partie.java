package fr.um2.projetl3.tarotandroid.jeu;

import static fr.um2.projetl3.tarotandroid.jeu.Context.P;
import static fr.um2.projetl3.tarotandroid.jeu.Context.D;

import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

import fr.um2.projetl3.tarotandroid.R;
import fr.um2.projetl3.tarotandroid.clients.IJoueur;
import fr.um2.projetl3.tarotandroid.clients.JoueurIA;
import fr.um2.projetl3.tarotandroid.clients.JoueurTexte;
import fr.um2.projetl3.tarotandroid.connection.MultiServeur;

@SuppressWarnings("all")
public class Partie
{
	private IJoueur[] joueurs; // initialisé de taille 3, 4 ou 5 selon type partie
	private Scores scores;
	private int nombreDeJoueurs;
	private int nombreDeCartesPourLeChien;
	private Stack<Carte> tas; // le tas de cartes, repris à la fin d’une donne pour être redistribué
	private Donne donne; // la donne en cours
	private boolean stopPartie; 
	
	/*
	 * --------------------------------------------------------------------------------------------
	 * ------------------------------------ accesseur --------------------------------------------
	 * --------------------------------------------------------------------------------------------
	 */
	
	/*
	 * Accesseur en lecture pour donne, utilisé à l’extérieur de la classe
	 * [ exemple dans Scores : P.donne().getPreneur() ]
	 */
	public Donne donne()
	{
		return donne;
	}
	
	/**
	 * 
	 * @param i
	 * @return la carte du tas à l’indice i
	 */
	public Carte getCarteDansTas(int i)
	{
		return tas.get(i);
	}
	
	/**
	 * Enlève la première carte du tas, et la retourne.
	 * @return la première carte du tas, et l’enlève.
	 */
	public Carte prendreCarteDuTas()
	{
		return tas.pop();
	}
	
	public Stack<Carte> getTas()
	{
		return tas;
	}
	
	public IJoueur[] getJoueurs()
	{
		return joueurs;
	}
	
	public IJoueur getJoueur(int num)
	{
		return joueurs[num];
	}
	
	/**
	 * Donne une chaîne de caractères 
	 * @return String de la forme « toStringDuJoueur (n°3) ».
	 */
	public String getNomNumJoueur(int num)
	{
		return joueurs[num] + " (n°" + num + ")";
	}

	public void setJoueur(int i, IJoueur joueur) //! changement fabrice : mis en public Pour pouvoir initialiser le tableau de joueurs a partir du server
	{
		joueurs[i] = joueur;
	}

	public Scores getScores()
	{
		return scores;
	}

	protected void setScores(Scores scores) // private ?
	{
		this.scores = scores;
	}

	public int getNumeroJoueur(IJoueur j)
	{
		int i = 0;
		boolean trouvé = false;
		while(!trouvé && i<getNombreDeJoueurs())
		{
			if(getJoueur(i) == j)
			{
				trouvé = true;
			}
			i++;
		}
		if(!trouvé)
		{
			System.out.println("Appel de getNumeroJoueur avec joueur inexistant");
			return -1;
		}
		else
		{
			return i; 
		}
	}
	
	public int getNombreDeJoueurs()
	{
		return nombreDeJoueurs;
	}

	public int getnombreDeCartesPourLeChien()
	{
		return nombreDeCartesPourLeChien;
	}

	protected void setNombreDeJoueurs(int nombreDeJoueurs) // private ?
	{
		if (nombreDeJoueurs >= 3 || nombreDeJoueurs <= 5)
		{
			this.nombreDeJoueurs = nombreDeJoueurs;
		}
		else
		{
			System.out.println("Nombre de joueurs " + nombreDeJoueurs
					+ " invalide, on met à 4.");

			this.nombreDeJoueurs = 4;
		}
		this.joueurs = new IJoueur[this.nombreDeJoueurs];

	}

	/*
	 * --------------------------------------------------------------------------------------------
	 * ------------------------------------ Initialisation --------------------------------------------
	 * --------------------------------------------------------------------------------------------
	 */
	protected void initialisationPartie() // private ?
	{
		Contrat.initialiserContrats();
		PrefsRegles.nombreAtoutsPoignee();// suivant le nombre de joueur au initialise les poignées
		
		scores = new Scores();
		nombreDeCartesPourLeChien = (nombreDeJoueurs == 5)? 3 : 6;

		// Initialisation du tas de cartes

		tas = new Stack<Carte>();
		int k = 0; // position dans le tableau tas[];
		int i, val = 0;

		// Création des cartes de couleur
		for (Couleur c : Couleur.values())
		{
			for (i = 1; i <= 14; i++)
			{
				tas.push(new Carte(c, i));
			}
		}

		// Création des atouts
		for (i = 0; i <= 21; i++)
		{
			tas.push(new Carte(i));
		}

		// Test
		/**
		for (i = 0; i < 78; i++)
			System.out.print(tas[i].toString() + ", ");
		System.out.println();
		/**/
		System.out.println(tas.size());
		Collections.shuffle(tas); // on mélange (avant la première donne)
	}

	/*
	 * --------------------------------------------------------------------------------------------
	 * ------------------------------------ Méthodes --------------------------------------------
	 * --------------------------------------------------------------------------------------------
	 */
	
	/*
	 * Utilisé pour regrouper les différents paquets de cartes pour reformer
	 * le tas à la fin de la donne. Fonctionne uniquement si tas[] est vide.
	 */
	protected void setTas(Stack<Carte> nouveauTas)
	{
		if(tas.size() > 0)
			System.out.println("Erreur : tentative de setTas() avec tas non vide");
		else
			tas = nouveauTas;
	}

	/**
	 * @author niavlys
	 * @param numJoueur un joueur
	 * @return Le numéro du joueur se trouvant après le joueur désigné en paramètre
	 */
	public int getNumJoueurApres(int numJoueur)
	{
		return (numJoueur+1) % getNombreDeJoueurs();
	}
	
	/**
	 * @author niavlys
	 * @return true si la partie est maintenant finie, en fonction des options dans PrefsRegles
	 */
	public boolean partieFinie()
	{
		if(!PrefsRegles.conditionFinDePartie)
			return stopPartie;
		else
		{
			if(PrefsRegles.conditionFinDonnesMax)
			{
				return (scores.nbDonnes() >= PrefsRegles.donnesMax);
			}
			else if(PrefsRegles.conditionFinScoreMax)
			{
				return (scores.meilleurScore() >= PrefsRegles.scoreMax);
			}
			else
				return true; // comme ça on verra tout de suite si on arrive dans ce cas (pas normal)
		}
	}
	
	
	/**
	 * --------------------------------------------------------------------------------------------------
	 * ---------------------------------------Phase chien / ecart----------------------------------
	 * ---------------------------------------à mettre peut-être dans donne--------------------------------------------
	 */
	/*
	 * renvoie vrai si l'ecart est bon
	 */
	public boolean verificationEcartValide(Carte[] ecart)
	{
		boolean ecartvalide = true;
		for(Carte c:ecart)
		{
			ecartvalide = ecartvalide && verificationCarteEcartValide(c);
		}
		for(int i=0;i<getnombreDeCartesPourLeChien();i++)
		{
			for(int j=i+1; j<getnombreDeCartesPourLeChien();j++)
			{
				if (ecart[i] == ecart[j])
				{
					ecartvalide = false;
				}
			}
		}
		return ecartvalide;
	}
	/*
	 * renvoie faut si une carte ne doit pas etre mis dans le chien
	 * vrai si la carte peut etre mise dans le chien
	 */
	public boolean verificationCarteEcartValide(Carte c)
	{
		if (c.isAtout())// si c'est un atout pas le doit de mettre au chien
		{
			return false;
		}
		else if(c.isCouleur()) // c'est donc une carte couleur // ? on refait la verification ? if iscartecouleur ?
		{
			if (c.getOrdre() == 14) // si c'est un roi pas le droit de mettre au chien
			{
				return false;
			}
		}
		else if(c.isExcuse())
		{
			return false;
		}
	return true;
	}
	
	public void phaseChienEcart()
	{
		if (donne.getContratEnCours().isChienRevele()) // petite ou garde
		{
			donne.reveleChien();
			
			donne.mettreChienDansPreneur(); // svp mettre des noms de méthodes un peu moins obscènes
			
			donne().setNumJoueurEnContact(donne().getPreneur());
			
			Carte[] ecart = new Carte[P.nombreDeCartesPourLeChien];
			ecart = demandeEcartPreneur();
			
			donne().setNumJoueurEnContact(donne().getNumDonneur()+1);
			
			System.out.println("TEST CHIEN");
			
			for(int i=0; i<P.nombreDeCartesPourLeChien; i++)
			{
				ecart[i].affiche();
			}
			donne.plisAttaque.addAll(Arrays.asList(ecart));
			donne.getMain(donne.getPreneur()).enleverEcart(ecart);
			
		}
		else if ( donne.getContratEnCours().isChienPourAttaque()) // garde sans
		{	
			// ici met le chien dans les plis de l'attaquant
			donne.mettreChienDansLesPlisDeLAttaque();
		}
		else // garde contre
		{
			// ici on met le chien dans les plis des défenseur
			donne.mettreChienDansLesPlisDeLaDefense();
		}
	}
	
	private Carte[] demandeEcartPreneur() 
	{
		Carte ecart[] = new Carte[P.getnombreDeCartesPourLeChien()]; 
		
		do
		{
			ecart = joueurs[D.getPreneur()].demanderEcart();
		}
		while(!verificationEcartValide(ecart));
		
		return ecart;
		
		
	}

	/**
	 * --------------------------------------------------------------------------------------------------
	 * ---------------------------------------lancement de la partie----------------------------------
	 * --------------------------------------------------------------------------------------------------
	 */
	
	protected void lancerPartie()
	{
		initialisationPartie();
		Contrat.initialiserContrats();
		//donneEnCours.init();
		while(!partieFinie())
		{
			donne = new Donne();
			D = donne;
			/*
			 * Je vois plutôt toute la partie suivante dans Donne pourquoi :
			 * toutes les phases suivant sont des parties integrantes d'une donne et non d'une partie entiere
			 * logiquement on as une partie qui contient X donne et une donne peut ou peut ne pas être jouer
			 * 
			 *   Alors ? quelqu'un en pense quelque chose ?
			 *   
			 *   non je pense que c'est meiu de le laisser là pourqui :
			 *   dans l while(!partieFinie)
			 *   	on à les phase d'une donne répéter tant que la partie n'est pas finie
			 * 
			 * 	faudrait arreter de mettre des choses dans donne sinon on va se retrouver avec 1K de ligen de code et se sera un peu la erde pour si retrouver
			 *  dans partie il n'y apas tant de ligne que ça et ça ne dérage pas.
			 *  
			 *  c'est plus d'un point de vue pratique que je préfére que ça reste ici
			 */
			donne.distribution();
			Annonces.phaseAnnonce(); // à voir (il faudrait que ce soit lié à donneEnCours d’une manière ou d’une autre)
			
			if(donne.getContratEnCours() != Contrat.AUCUN) // si il n'y a pas de contrat il faut arreter la donne.
			{
				System.out.println("Contrat en cours : "+donne.getContratEnCours()+" par "+P.getNomNumJoueur(D.getPreneur()));
				phaseChienEcart();
				donne.jeuDeLaCarte();
				scores.phaseScore();
				donne.reformerTas();
			}
			else
			{
				System.out.println("Aucun contrat de fait => nouvelle donne");
				// C’est le cas où tout le monde a passé.
				// Montrer les jeux de tout le monde avant de redistribuer ?
			}
		}
	}
	

	/*
	 * --------------------------------------------------------------------------------------------
	 * -----------------------------------------TEST--------------------------------------------
	 * --------------------------------------------------------------------------------------------
	 */
	public void lancerPartie4JoueursTexte()
	{
		setNombreDeJoueurs(4);
		setJoueur(0, new JoueurTexte("Nord"));
		setJoueur(1, new JoueurTexte("Est", true));
		setJoueur(2, new JoueurTexte("Sud", true));
		setJoueur(3, new JoueurTexte("Ouest", true));
		
		lancerPartie();
	}
	
	public void lancerPartie4JoueursIA(IJoueur a, IJoueur b, IJoueur c, IJoueur d)
	{
		setNombreDeJoueurs(4);
		setJoueur(0, a);
		setJoueur(1, b);
		setJoueur(2, c);
		setJoueur(3, d);
		
		lancerPartie();
	}	
	
	public void lancerPartieDistante()
	{
		setNombreDeJoueurs(4);
		MultiServeur server = new MultiServeur();
			server.lancer();
		
	}
	
	//! TODO Cette méthode est à SUPPRIMER quand l'application sera finie
	//! Breche de sécurité importante, utile uniquement pour tests.
	public static void testPartie(IJoueur a, IJoueur b, IJoueur c, IJoueur d)
	{
		P = new Partie();
		P.setNombreDeJoueurs(4);
		P.setJoueur(0, a);
		P.setJoueur(1, b);
		P.setJoueur(2, c);
		P.setJoueur(3, d);
		P.initialisationPartie();
		//P.lancerPartie();
		P.initialisationPartie();
		Contrat.initialiserContrats();
		D = new Donne();
		D.distribution();
		D.setContratEnCours(Contrat.GARDE);
		D.setPreneur(0);
		

	}

	public Partie(int nombreDeJoueurs)
	
	{
		setNombreDeJoueurs(nombreDeJoueurs);
		joueurs = new JoueurTexte[nombreDeJoueurs];
		nombreDeCartesPourLeChien = (nombreDeJoueurs == 5)? 3 : 6;
		
		// initialisationPartie();
	}

	public Partie()
	{
		setNombreDeJoueurs(4);
	}

	public static void main(String[] args)
	{
		P = new Partie();
		P.lancerPartieDistante();
		//P.lancerPartie4JoueursTexte();
		
	}


}
