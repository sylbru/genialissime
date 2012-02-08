package fr.um2.projetl3.tarotandroid.jeu;

import java.util.Vector;

import fr.um2.projetl3.tarotandroid.exceptions.NombreDeJoueursInvalideException;

public class Partie
{
	private Joueur[] joueurs; // ? initialisé de taille 3, 4 ou 5 selon type de
								// partie (bien ?)
	private Scores scores;

	private int nombreDeJoueurs;	 // ? initialiser la taille du tableu de joeur en
									// fonction de cette valeur	
	private Carte[] tas;
	
	private Donne donneEnCours;

	public Joueur[] getJoueurs()
	{
		return joueurs;
	}

	public void setJoueurs(Joueur[] joueurs)
	{
		this.joueurs = joueurs;
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

	public int getNombreDeJoueur()
	{
		return nombreDeJoueurs;
	}

	public void setNombreDeJoueur(int nombreDeJoueurs)
	{
		if (nombreDeJoueurs >= 3 || nombreDeJoueurs <= 5)
			this.nombreDeJoueurs = nombreDeJoueurs;
		else
		{
			System.out.println("Nombre de joueurs "+ nombreDeJoueurs +" invalide, on met à 4.");
			
			this.nombreDeJoueurs = 4;
		}
	}
	
	private void init()
	{
		scores = new Scores();
		
	}
	
	public Partie(int nombreDeJoueurs)
	{
		setNombreDeJoueur(nombreDeJoueurs);
		init();
	}
	
	public Partie()
	{
		setNombreDeJoueur(4);
	}
	
	public static void main(String[] args)
	{
		Partie partie = new Partie(2);
		partie.getJoueurs();
	}

}
