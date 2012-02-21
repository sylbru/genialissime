package fr.um2.projetl3.tarotandroid.jeu;

import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

import fr.um2.projetl3.tarotandroid.clients.Joueur;
import fr.um2.projetl3.tarotandroid.clients.JoueurTexte;

@SuppressWarnings("all")
public class Partie
{
	private static Joueur[] joueurs; // initialisé de taille 3, 4 ou 5 selon type partie
	private static Scores scores;
	private static int nombreDeJoueurs;
	private static Carte[] tas; // le tas de carte, repris à la fin d’une donne pour être redistribué
	private static Donne donneEnCours;
	private static int nombreDeCartesPourLeChien;
	
	
	private static boolean stopPartie; 
	
	public static Joueur[] getJoueurs()
	{
		return joueurs;
	}
	public static Joueur getJoueur(int i)
	{
		return joueurs[i];
	}

	public static void setJoueur(int i, Joueur joueur)
	{
		joueurs[i] = joueur;
	}

	public static Scores getScores()
	{
		return scores;
	}

	public static void setScores(Scores scores)
	{
		Partie.scores = scores;
	}

	public static int getNombreDeJoueurs()
	{
		return nombreDeJoueurs;
	}

	public static int getnombreDeCartesPourLeChien()
	{
		return nombreDeCartesPourLeChien;
	}

	public static void setNombreDeJoueurs(int nombreDeJoueurs)
	{
		if (nombreDeJoueurs >= 3 || nombreDeJoueurs <= 5)
			Partie.nombreDeJoueurs = nombreDeJoueurs;
		else
		{
			System.out.println("Nombre de joueurs " + nombreDeJoueurs
					+ " invalide, on met à 4.");

			Partie.nombreDeJoueurs = 4;
		}

	}

	protected static void initialisationPartie()
	{
		scores = new Scores();

		// Initialisation du tas de cartes

		tas = new Carte[78];
		int k = 0; // position dans le tableau tas[];
		int i, val = 0;

		// Création des cartes de couleur
		for (Couleur c : Couleur.values())
		{
			for (i = 1; i <= 14; i++)
			{
				tas[k++] = new CarteCouleur(c, i);
			}
		}

		// Création des atouts
		for (i = 0; i <= 21; i++)
		{
			tas[k++] = new CarteAtout(i);
		}

		// Test
		for (i = 0; i < 78; i++)
			System.out.print(tas[i].toString() + ", ");
		System.out.println();
		
		Collections.shuffle(Arrays.asList(tas)); // on mélange (avant la première donne)
	}
	
	public boolean verificatioSiEcartPasValide(Carte[] ecart)
	{
		for(Carte c:ecart)
		{
			if(c.isAtout()) // si c'est un atout
			{
				if (((CarteAtout)c).isBout())// si c'est un bout pas le doit de mettre au chien
				{
					return true;
				}
			}
			else // c'est donc une carte couleur // ? on refait la verification ? if iscartecouleur ?
			{
				if (((CarteCouleur)c).valeur() == 14) // si c'est un roi pas le droit de mettre au chien
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public void phaseChienEcart()
	{
		if (Donne.getContratEnCours().isChienRevele()) // petite ou garde
		{
			Donne.reveleChien();// ici il faut révélé le chien
			
			Donne.mettreChienDansLaMainDuPreneur();// ici il faut donner le chien au preneur
			
			// ensuite il faut lui dire quelles cartes il veut mettre à l'ecart une fois l'ecart fait on le met dans les plis de l'attaquant
			
			Carte[] ecartEnAttenteDeValidation = new Carte[nombreDeCartesPourLeChien];
			boolean ecartPasValide = true;
			
			while(ecartPasValide) // ? faudrait rajouter un compteur et afficher quelque chose non ?
			{
				ecartEnAttenteDeValidation = Donne.getPreneur().demanderEcart();
				ecartPasValide = verificatioSiEcartPasValide(ecartEnAttenteDeValidation); 
			}
		}
		else if ( Donne.getContratEnCours().isChienPourAttaque()) // garde sans
		{	
			// ici met le chien dans les plis de l'attaquant
			Donne.mettreChienDansLesPlisDeLAttaque();
		}
		else // garde contre
		{
			// ici on met le chien dans les plis des défenseur
			Donne.mettreChienDansLesPlisDeLaDefense();
		}
	}
	public void lancerPartie()
	{
		initialisationPartie();
		while(!partieFinie())
		{
			/*
			 * Je vois plutôt toute la partie suivante dans Donne pourquoi :
			 * toutes les phases suivant sont des parties integrantes d'une donne et non d'une partie entiere
			 * logiquement on as une partie qui contient X donne et une donne peut ou peut ne pas être jouer
			 * 
			 *   Alors ? quelqu'un en pense quelque chose ?
			 * 
			 */
			Donne.distribution();
			Annonces.phaseAnnonce();
					
			if(donneEnCours.getContratEnCours() != Contrat.AUCUN) // si il n'y a pas de contrat il faut arreter la donne.
			{
				// jeuDeLaCarte(); // pas de meilleur nom pour l’instant, on dit comme ça au bridge, mais au tarot ?
				// comptePoints(); // (à voir avec méthodes de scores, peut-être les modifier pour qu’elles lisent
								   // directement dans Partie le contrat, les cartes remportées… ?)
				Donne.reformerTas();
			}
			else
			{
				phaseChienEcart();
				// une phase deroulement des plis est à faire surement dans donne et appeler ici.
				// puis apres il faut appeler suivant les préference choisit, la méthode de comptage des scores puis les affiché bien sûr
			}
	
		}
	}
	
	/*
	 * Utilisé pour regrouper les différents paquets de cartes pour reformer
	 * le tas à la fin de la donne. Fonctionne uniquement si tas[] est vide.
	 */
	protected static void setTas(Carte[] nouveauTas)
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
	public static boolean partieFinie()
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
	}

	public static void main(String[] args)
	{
		Partie partie = new Partie(2);
		partie.getJoueurs();
	}


}
