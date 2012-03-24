
package fr.um2.projetl3.tarotandroid.clients;

import fr.um2.projetl3.tarotandroid.jeu.Carte;
import fr.um2.projetl3.tarotandroid.jeu.Contrat;
import fr.um2.projetl3.tarotandroid.jeu.Main;
import fr.um2.projetl3.tarotandroid.connection.Cartes;

public interface IJoueur
{

	void setNomDuJoueur(String s);
	String getNomDuJoueur();
	void recevoirMain(Cartes c);
	
	Contrat demanderAnnonce(Contrat contrat);
	Carte[] demanderEcart();
	
	Carte demanderCarte();
	Carte demanderRoi();
	Carte demanderUneCartePourLecart();
	
	void direChien(Carte[] chien);
	void direCarteJouee(Carte c, String j);
	void direAnnonce(Contrat c, String j);
	void direPliRemporté1(Carte[] pli, String joueur);
	void direMain(Main m);
	void direScore();
	
	/*
	 * ------------------------------------------------------------------------------------------
	 * -------------------------------demande initier par le joueur---------------------------------------------
	 * ------------------------à mettre ailleur--------------------------
	 
	
	void recupererMain();
	// le joueur demande sa main au serveur en cas de probleme
	void recupererPliEnCours();
	// le joueur demande le pli en cours au serveur 
	void recupererPliPrecedent();
	// le joueur doit pouvoir acceder au dernier pli
	void recupererScores();
	*/

	void direPliRemporté(Carte[] pli, String joueur);

}
