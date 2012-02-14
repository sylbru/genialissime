package fr.um2.projetl3.tarotandroid.jeu;

import java.util.Vector;

public class CartesRemportes {
	private Vector<Carte> cartesremportes;
	
	public Vector<Carte> getcartesremportes(){
		return cartesremportes;
	}
	public int getsize(){
		return cartesremportes.size();
	}
	
	public Carte getCarte(int i) {
		return cartesremportes.get(i);
	}
	
	public boolean addCarte(Carte c)
	{
		return cartesremportes.add(c);
	}
	public boolean removeCarte(Carte c)
	{
		return cartesremportes.remove(c);
	}
	
	public CartesRemportes(){
		cartesremportes = new Vector<Carte>();
	}


}
