package fr.um2.projetl3.tarotandroid.clients;

import fr.um2.projetl3.tarotandroid.jeu.Carte;
import fr.um2.projetl3.tarotandroid.jeu.Contrat;
import fr.um2.projetl3.tarotandroid.jeu.Main;

abstract public class Joueur
{
	// ! Classe incompl�te, ne pas encore utiliser cette interface!
	// TODO Finir l'interface Joueur
	// TODO En faire une vraie interface (au sens Java) [?]
	public abstract void setID(int pID);
	public abstract int getID();

	public abstract void setMain(Main pMain);

	public abstract void setNomDuJoueur(String s);
	public abstract String getNomDuJoueur();

	public abstract Contrat demanderAnnonce(Contrat contrat);
	
	public abstract Carte demanderCarte();
	
	public abstract String nom();

}
