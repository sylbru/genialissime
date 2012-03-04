package fr.um2.projetl3.tarotandroid.jeu;

import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

import fr.um2.projetl3.tarotandroid.clients.IJoueur;
import fr.um2.projetl3.tarotandroid.clients.JoueurTexte;
import static fr.um2.projetl3.tarotandroid.jeu.Context.*;

@SuppressWarnings("all")
public class Partie
{
	private IJoueur[] joueurs; // initialisé de taille 3, 4 ou 5 selon type partie
	private Scores scores;
	private int nombreDeJoueurs;
	private int nombreDeCartesPourLeChien;
	private Carte[] tas; // le tas de cartes, repris à la fin d’une donne pour être redistribué
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
	
	public Carte getCarteDansTas(int i)
	{
		return tas[i];
	}
	
	public IJoueur[] getJoueurs()
	{
		return joueurs;
	}
	public IJoueur getJoueur(int i)
	{
		return joueurs[i];
	}

	public void setJoueur(int i, IJoueur joueur)
	{
		joueurs[i] = joueur;
	}

	public Scores getScores()
	{
		return scores;
	}

	public void setScores(Scores scores)
	{
		this.scores = scores;
	}

	public int getNombreDeJoueurs()
	{
		return nombreDeJoueurs;
	}

	public int getnombreDeCartesPourLeChien()
	{
		return nombreDeCartesPourLeChien;
	}

	public void setNombreDeJoueurs(int nombreDeJoueurs)
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
	protected void initialisationPartie()
	{
		Contrat.initialiserContrats();
		scores = new Scores();
		nombreDeCartesPourLeChien = (nombreDeJoueurs == 5)? 3 : 6;

		// Initialisation du tas de cartes

		tas = new Carte[78];
		int k = 0; // position dans le tableau tas[];
		int i, val = 0;

		// Création des cartes de couleur
		for (Couleur c : Couleur.values())
		{
			for (i = 1; i <= 14; i++)
			{
				tas[k++] = new Carte(c, i);
			}
		}

		// Création des atouts
		for (i = 0; i <= 21; i++)
		{
			tas[k++] = new Carte(i);
		}

		// Test
		/**
		for (i = 0; i < 78; i++)
			System.out.print(tas[i].toString() + ", ");
		System.out.println();
		/**/
		
		Collections.shuffle(Arrays.asList(tas)); // on mélange (avant la première donne)
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
	protected void setTas(Carte[] nouveauTas)
	{
		if(tas.length > 0)
			System.out.println("Erreur : tentative de setTas() avec tas[] non vide");
		else
			tas = nouveauTas;
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
	
	public boolean verificatioSiEcartPasValide(Carte[] ecart)
	{
		for(Carte c:ecart)
		{
			if (c.isBout())// si c'est un bout pas le doit de mettre au chien
			{
				return true;
			}
			else if(c.isCouleur()) // c'est donc une carte couleur // ? on refait la verification ? if iscartecouleur ?
			{
				if (c.valeur() == 14) // si c'est un roi pas le droit de mettre au chien
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public void phaseChienEcart()
	{
		if (donne.getContratEnCours().isChienRevele()) // petite ou garde
		{
			donne.reveleChien();// ici il faut révélé le chien
			
			donne.mettreChienDansLaMainDuPreneur();// ici il faut donner le chien au preneur
			
			// ensuite il faut lui dire quelles cartes il veut mettre à l'ecart une fois l'ecart fait on le met dans les plis de l'attaquant
			
			Carte[] ecartEnAttenteDeValidation = new Carte[nombreDeCartesPourLeChien];
			boolean ecartPasValide = true;
			
			while(ecartPasValide) // ? faudrait rajouter un compteur et afficher quelque chose non ?
			{
				ecartEnAttenteDeValidation = donne.getPreneur().demanderEcart();
				ecartPasValide = verificatioSiEcartPasValide(ecartEnAttenteDeValidation); 
			}
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
	
	public void lancerPartie()
	{
		initialisationPartie();
		//donneEnCours.init();
		while(!partieFinie())
		{
			donne = new Donne();
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
				phaseChienEcart();
				donne.jeuDeLaCarte();
				// comptePoints(); // (à voir avec méthodes de scores, peut-être les modifier pour qu’elles lisent
								   // directement dans Partie le contrat, les cartes remportées… ?)
				donne.reformerTas();
			}
			else
			{
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
		setJoueur(1, new JoueurTexte("Sud"));
		setJoueur(2, new JoueurTexte("Est"));
		setJoueur(3, new JoueurTexte("Ouest"));
		
		lancerPartie();
	}

	/*
	public Partie(int nombreDeJoueurs)
	
	{
		setNombreDeJoueurs(nombreDeJoueurs);
		joueurs = new JoueurTexte[nombreDeJoueurs];
		nombreDeCartesPourLeChien = (nombreDeJoueurs == 5)? 3 : 6;
		
		initialisationPartie();
	}

	public Partie()
	{
		setNombreDeJoueurs(4);
		initialisationPartie();
	}*/

	public static void main(String[] args)
	{
		P = new Partie();
		P.lancerPartie4JoueursTexte();
		//D = P.donneEnCours;
		
	}


}
