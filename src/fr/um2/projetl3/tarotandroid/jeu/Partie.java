package fr.um2.projetl3.tarotandroid.jeu;

import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

import fr.um2.projetl3.tarotandroid.clients.Joueur;

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

	private static void initialisationPartie()
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
	
	/**/
	public void lancerPartie()
	{
		initialisationPartie();
		while(!partieFinie())
		{
			Donne.distribution();
			Annonces.phaseAnnonce();
			
			if(donneEnCours.getContratEnCours() != Contrat.AUCUN)
			{
				// jeuDeLaCarte(); // pas de meilleur nom pour l’instant, on dit comme ça au bridge, mais au tarot ?
				// comptePoints(); // (à voir avec méthodes de scores, peut-être les modifier pour qu’elles lisent
								   // directement dans Partie le contrat, les cartes remportées… ?)
				Donne.reformerTas();
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
