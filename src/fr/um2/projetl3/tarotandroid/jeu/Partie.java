package fr.um2.projetl3.tarotandroid.jeu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

@SuppressWarnings("all")
public class Partie
{

	private Joueur[] joueurs; // ? initialisé de taille 3, 4 ou 5 selon type de
	private Scores scores;
	private static int nombreDeJoueurs;
	private Carte[] tas;
	private Donne donneEnCours;

	private Vector<Carte> chien[];
	private static int nombreDeCartesPourLeChien;

	public Joueur[] getJoueurs()
	{
		return joueurs;
	}

	public void setJoueur(int i, Joueur joueur)
	{
		this.joueurs[i] = joueur;
	}

	public Scores getScores()
	{
		return scores;
	}

	public void setScores(Scores scores)
	{
		this.scores = scores;
	}

	public static int getNombreDeJoueurs()
	{
		return nombreDeJoueurs;
	}

	public static int getnombreDeCartesPourLeChien()
	{
		return nombreDeCartesPourLeChien;
	}

	public void setNombreDeJoueur(int nombreDeJoueurs)
	{
		if (nombreDeJoueurs >= 3 || nombreDeJoueurs <= 5)
			this.nombreDeJoueurs = nombreDeJoueurs;
		else
		{
			System.out.println("Nombre de joueurs " + nombreDeJoueurs
					+ " invalide, on met à 4.");

			this.nombreDeJoueurs = 4;
		}

	}

	private void initialisationPartie()
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
		
		Collections.shuffle(Arrays.asList(tas));
		// Le tas est mélangé (début de la partie).
	}

	public Partie(int nombreDeJoueurs)
	{
		setNombreDeJoueur(nombreDeJoueurs);
		initialisationPartie();
	}

	public Partie()
	{
		setNombreDeJoueur(4);
		initialisationPartie();
	}

	public static void main(String[] args)
	{
		Partie partie = new Partie(2);
		partie.getJoueurs();
	}

}
