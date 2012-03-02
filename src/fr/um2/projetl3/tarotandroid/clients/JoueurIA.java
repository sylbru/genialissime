package fr.um2.projetl3.tarotandroid.clients;

import fr.um2.projetl3.tarotandroid.jeu.Carte;
import fr.um2.projetl3.tarotandroid.jeu.CarteCouleur;
import fr.um2.projetl3.tarotandroid.jeu.Contrat;
import fr.um2.projetl3.tarotandroid.jeu.Main;
import org.keplerproject.luajava.*;

public class JoueurIA implements Joueur {
	
	private int pID;
	private Main pMain;
	private String nom;
	
	private LuaState L;
	
	public JoueurIA()
	{
		L = LuaStateFactory.newLuaState();
		L.openLibs();
		try {
			L.pushObjectValue(this);
		} catch (LuaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		L.setGlobal("javapi");
	}
	
	public void setID(int pID) {
		this.pID=pID;

	}

	public int getID() {
		return pID;
	}

	public void setMain(Main pMain) {
		this.pMain = pMain;
	}


	public void addChienDansMain(Carte[] chien) {
		// LOOOOL
	}

	public void setNomDuJoueur(String s) {
		this.nom = s;
	}

	public String getNomDuJoueur() {
		// TODO Auto-generated method stub
		return null;
	}

	public Contrat demanderAnnonce(Contrat contrat) {
		// TODO Auto-generated method stub
		return null;
	}

	public Carte[] demanderEcart() {
		// LOOOL
		return null;
	}

	public Carte demanderCarte() {
		// LOOOL
		return null;
	}

	public CarteCouleur appelerRoi() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean possedeRoi(CarteCouleur roi) {
		// TODO Auto-generated method stub
		return false;
	}

	public String nom() {
		// TODO Auto-generated method stub
		return null;
	}

}
