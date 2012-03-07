
package fr.um2.projetl3.tarotandroid.clients;

import fr.um2.projetl3.tarotandroid.jeu.Carte;
import fr.um2.projetl3.tarotandroid.jeu.Contrat;
import fr.um2.projetl3.tarotandroid.jeu.Main;

public interface IJoueur
{
	void setMain(Main pMain);
	void addChienDansMain(Carte[] chien);

	void setNomDuJoueur(String s);
	String getNomDuJoueur();
	String nom();
	
	Contrat demanderAnnonce(Contrat contrat);
	Carte[] demanderEcart();
	
	Carte demanderCarte();
	Carte appelerRoi();
	boolean possedeRoi(Carte roi);
	
	void direChien(Carte[] chien);
	void direCarteJouee(Carte c, IJoueur j);
	void direAnnonce(Contrat c, IJoueur j);
}
