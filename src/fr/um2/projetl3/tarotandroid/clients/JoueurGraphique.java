package fr.um2.projetl3.tarotandroid.clients;

import static fr.um2.projetl3.tarotandroid.jeu.Context.P;

import java.util.Vector;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.GpsStatus.Listener;
import android.os.Looper;
import android.preference.ListPreference;
import android.widget.EditText;
import android.widget.Toast;
import fr.um2.projetl3.tarotandroid.activities.EcranJeu;
import fr.um2.projetl3.tarotandroid.connection.Cartes;
import fr.um2.projetl3.tarotandroid.jeu.Carte;
import fr.um2.projetl3.tarotandroid.jeu.Contrat;
import fr.um2.projetl3.tarotandroid.jeu.Donne;
import fr.um2.projetl3.tarotandroid.jeu.Main;

public class JoueurGraphique implements IJoueur {
		
	private EcranJeu ecran;
	
	private String nom;
	private Main main;
	
	public JoueurGraphique(EcranJeu ecran)
	{
		this.ecran = ecran;
	}
	
	public JoueurGraphique(String nom, EcranJeu ecran)
	{
		this.nom = nom;
		this.ecran = ecran;
	}
	
	private void majMain()
	{
		this.main = P.donne().getMain();
	}
	
	public void afficherMain(JoueurGraphique j)
	{
		
	}

	
	public void setNomDuJoueur(String s) {
		// TODO Auto-generated method stub

	}

	
	public String getNomDuJoueur() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Contrat demanderAnnonce(Contrat contrat) {
		return ecran.demanderAnnonce(contrat);
	}

	
	public Vector<Carte> demanderEcart() {
		return ecran.demanderEcart();
	}

	
	public Carte demanderCarte() {
		return ecran.demanderCarte();
	}

	
	public Carte demanderAppelAuRoi() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Carte demanderUneCartePourLecart() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void direChien(Vector<Carte> chien) {
		ecran.log("Chien : "+chien.toString());
	}

	
	public void direCarteJouee(Carte c, int j) {
		ecran.log(P.getNomNumJoueur(j) + " joue " + c);
		ecran.direCarteJouee(c, j);
	}

	
	public void direAnnonce(Contrat c, int j) {
		ecran.direAnnonce(c, j);
	}

	
	public void direPliRemporté(Vector<Carte> pli, int joueur) {
		ecran.log("Pli remporté par " + P.getNomNumJoueur(joueur));
		ecran.direPliRemporté(pli, joueur);
		//pli.clear();
	}

	private void recupererMain() {
		// TODO Auto-generated method stub
	}

	private void recupererPliEnCours() {
		// TODO Auto-generated method stub
	}

	private void recupererPliPrecedent() {
		// TODO Auto-generated method stub
	}


	public void direMain(Vector<Carte> main) {
		ecran.direMain(main);
	}

	public void direScore() {
		ecran.direScore();
		// TODO Auto-generated method stub
		
	}

	public void recupererScores() {
		// TODO Auto-generated method stub
		
	}

	public void direPliRemporté1(Carte[] pli, String joueur) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString()
	{
		return nom;
	}
	
}
