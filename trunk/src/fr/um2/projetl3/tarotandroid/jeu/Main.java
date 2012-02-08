package fr.um2.projetl3.tarotandroid.jeu;

import java.util.Vector;

public class Main {
	private Vector<Carte> cartes;
	
	public boolean addCarte(Carte c)
	{
		return cartes.add(c);
	}
	public boolean removeCarte(Carte c)
	{
		return cartes.remove(c);
	}
	
}
