package fr.um2.projetl3.tarotandroid.connection;

import java.io.Serializable;
import java.util.Vector;

import fr.um2.projetl3.tarotandroid.jeu.Carte;

public class Cartes implements Serializable{

	private static final long serialVersionUID = 6098455379378002199L;
	private Vector<Carte> carte;
	
	public Cartes(Vector<Carte> c){
		carte = c;
	}
	
	public void set(Vector<Carte> c){
		carte = c;
	}
	
	public Carte getcarte(int i){
		return carte.get(i);
	}

	public int size() {
		return carte.size();
	}
	
}