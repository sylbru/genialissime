
package fr.um2.projetl3.tarotandroid.clients;

import fr.um2.projetl3.tarotandroid.jeu.Carte;
import fr.um2.projetl3.tarotandroid.jeu.Contrat;
import fr.um2.projetl3.tarotandroid.jeu.Main;
import fr.um2.projetl3.tarotandroid.connection.Cartes;

public interface IJoueur
{

	void setNomDuJoueur(String s);
	String getNomDuJoueur();
	
	/*
	 * ------------------------------------------------------------------------------------------
	 * -------------------------------demande initier par le seveur---------------------------------------------
	 * -------------------------------------------------------------------------------------------
	 */
	
	Contrat demanderAnnonce(Contrat contrat);
	Carte[] demanderEcart();
	
	Carte demanderCarte();
	Carte demanderRoi();
	Carte demanderUneCartePourLecart();
	
	void direChien(Carte[] chien);
	void direCarteJouee(Carte c, String j);
	void direAnnonce(Contrat c, String j);
	void direPliRemporté(Carte[] pli, String joueur);
	void direMain(Main m);
	
	
	/*
	 * ------------------------------------------------------------------------------------------
	 * -------------------------------demande initier par le joueur---------------------------------------------
	 * -------------------------------------------------------------------------------------------
	 */
	
	void recupererMain();
	void recupererPliEnCours();
	void recupererPliPrecedent();
	
}
