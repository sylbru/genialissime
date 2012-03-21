package fr.um2.projetl3.tarotandroid.connection;

import java.io.Serializable;
import static fr.um2.projetl3.tarotandroid.jeu.Context.P;
import static fr.um2.projetl3.tarotandroid.jeu.Context.D;
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
	
	public void set (Carte[] c){
		for(int i=0; i<P.getNombreDeJoueurs();i++)
		{
			carte.add(c[i]);
		}
	}
	
	public Carte getcarte(int i){
		return carte.get(i);
	}

	public int size() {
		return carte.size();
	}
	
}