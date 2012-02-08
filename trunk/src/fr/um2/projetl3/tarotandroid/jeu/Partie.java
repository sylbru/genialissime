package fr.um2.projetl3.tarotandroid.jeu;

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
			System.out.println("Nombre de joueurs "+ nombreDeJoueurs +" invalide, on met à 4.");
			
			this.nombreDeJoueurs = 4;
		}

	}
	private void initialisationDunePartie()
	{
		scores = new Scores();
		
	}
	
	public Partie(int nombreDeJoueurs)
	{
		setNombreDeJoueur(nombreDeJoueurs);
		initialisationDunePartie();
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
