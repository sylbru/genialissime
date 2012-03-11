
package fr.um2.projetl3.tarotandroid.clients;

import fr.um2.projetl3.tarotandroid.jeu.Carte;
import fr.um2.projetl3.tarotandroid.jeu.Contrat;
import fr.um2.projetl3.tarotandroid.jeu.Main;

public interface IJoueur
{

	void setNomDuJoueur(String s);
	String getNomDuJoueur();
	
	Contrat demanderAnnonce(Contrat contrat);
	Carte[] demanderEcart();
	
	Carte demanderCarte();
	Carte demanderRoi();
	Carte demanderUneCartePourLecart();
	
	boolean possedeRoi(Carte roi);
	// TODO: À enlever, on ne demande pas au joueur s’il possède un roi, on regarde dans mainsDesJoueurs 
	
	void direChien(Carte[] chien);
	void direCarteJouee(Carte c, String j);
	void direAnnonce(Contrat c, String j);
	void direPliRemporté(Carte[] pli, String joueur);
}
