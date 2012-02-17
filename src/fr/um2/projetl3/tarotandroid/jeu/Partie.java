package fr.um2.projetl3.tarotandroid.jeu;

import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

@SuppressWarnings("all")
public class Partie
{
	private static Joueur[] joueurs; // ? initialisé de taille 3, 4 ou 5 selon type partie
	private static Scores scores;
	private static int nombreDeJoueurs;
	private static Carte[] tas; // le tas de carte, repris à la fin d’une donne pour être redistribué
	private static Donne donneEnCours;

	private static int nombreDeCartesPourLeChien;
	private static Vector<Carte> chien[];
	private static Vector<Carte> plisAttaque;
	private static Vector<Carte> plisDefense;
	
	private static Contrat contratEnCours;

	public static Joueur[] getJoueurs()
	{
		return joueurs;
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
	
	/*
	public void lancerPartie()
	{
		initialisationPartie();
		while(!partieFinie())
		{
			Donne.distribution(); // distribution (à renommer ?)
			contratEnCours = Donne.annonces();
			if(contratEnCours != Contrat.AUCUN)
			{
				// jeuDeLaCarte(); // pas de meilleur nom pour l’instant, on dit comme ça au bridge, mais au tarot ?
				// comptePoints(); // (à voir avec méthodes de scores, peut-être les modifier pour qu’elles lisent
								   // directement dans Partie le contrat, les cartes remportées… ?)
				// regrouperTas();
			}
			
		}
	}*/
	
	public boolean partieFinie()
	{
		return false; // temporaire
	}

	public Partie(int nombreDeJoueurs)
	{
		setNombreDeJoueurs(nombreDeJoueurs);
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
