package fr.um2.projetl3.tarotandroid.jeu;

import java.util.Vector;

public class Partie
{
	private Joueur[] joueurs; // ? initialisé de taille 3, 4 ou 5 selon type de partie (bien ?)
	private Scores scores;
	private static int NombreDeJoueur; // ? initialiser la taille du tableu de joeur en fonction de cette valeur
	
	
	public Joueur[] getJoueurs() {
		return joueurs;
	}
	public void setJoueurs(Joueur[] joueurs) {
		this.joueurs = joueurs;
	}
	public Scores getScores() {
		return scores;
	}
	public void setScores(Scores scores) {
		this.scores = scores;
	}
	public static int getNombreDeJoueur() {
		return NombreDeJoueur;
	}
	public void setNombreDeJoueur(int nombreDeJoueur) {
		NombreDeJoueur = nombreDeJoueur;
	}
	

}
