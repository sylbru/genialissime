package fr.um2.projetl3.tarotandroid.clients;

import static fr.um2.projetl3.tarotandroid.jeu.Context.P;
import android.app.Activity;
import fr.um2.projetl3.tarotandroid.activities.EcranJeu;
import fr.um2.projetl3.tarotandroid.jeu.Carte;
import fr.um2.projetl3.tarotandroid.jeu.Contrat;
import fr.um2.projetl3.tarotandroid.jeu.Donne;
import fr.um2.projetl3.tarotandroid.jeu.Main;

public class JoueurGraphique implements IJoueur {
		
	private EcranJeu ecran;
	private Donne DonneDuJoueur;
	
	private String nom;
	private Main main;
	
	public JoueurGraphique(){
		
	}
	
	public JoueurGraphique(Donne d){
		DonneDuJoueur = d;
	}
	
	private void majMain()
	{
		this.main = P.donne().getMain();
	}
	
	public void afficherMain(JoueurGraphique j)
	{
		
	}

	@Override
	public void setNomDuJoueur(String s) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getNomDuJoueur() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Contrat demanderAnnonce(Contrat contrat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Carte[] demanderEcart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Carte demanderCarte() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Carte demanderRoi() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Carte demanderUneCartePourLecart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void direChien(Carte[] chien) {
		// TODO Auto-generated method stub

	}

	@Override
	public void direCarteJouee(Carte c, String j) {
		// TODO Auto-generated method stub

	}

	@Override
	public void direAnnonce(Contrat c, String j) {
		// TODO Auto-generated method stub

	}

	@Override
	public void direPliRemporté(Carte[] pli, String joueur) {
		// TODO Auto-generated method stub

	}

}
