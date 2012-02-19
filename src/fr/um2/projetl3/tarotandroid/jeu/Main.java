package fr.um2.projetl3.tarotandroid.jeu;

import java.util.Vector;

import fr.um2.projetl3.tarotandroid.clients.Joueur;
import fr.um2.projetl3.tarotandroid.clients.JoueurTexte;

public class Main
{
	private Vector<Carte> cartes;
	private int nbCartesInitial;
	
	private Joueur proprietaire;

	public boolean addCarte(Carte c)
	{
		if(cartes.size() < nbCartesInitial)
			return cartes.add(c);
		else
		{
			System.out.println("Erreur lors de l’ajout de "+c+", il y a déjà "+nbCartesInitial+" cartes dans la main.");
			return false;
		}
	}

	public boolean removeCarte(Carte c)
	{
		return cartes.remove(c);
	}
	
	public int nbCartesRestantes()
	{
		return cartes.size();
	}
	
	public void affiche()
	{
		System.out.println("Main de "+proprietaire.nom()+" :");
		System.out.print("[ ");
		for(Carte c: cartes)
		{
			if(c != null)
				c.affiche();
			else
				System.out.print("# ");
		}
		System.out.println("] \n");
		System.out.println("-----");
	}
	
	public Main(int nbCartesInitial, Joueur proprietaire)
	{
		this.nbCartesInitial = nbCartesInitial;
		this.proprietaire = proprietaire;
		cartes = new Vector<Carte>();
	}
	
	public static void main(String[] args)
	{
		Joueur j1 = new JoueurTexte("Truc");
		//System.out.println(j1.demanderAnnonce().getName()); erreur
		
		Main m = new Main(5, j1);
		m.addCarte(new CarteCouleur(Couleur.Trefle, 12));
		m.addCarte(new CarteAtout(14));
		m.addCarte(new CarteAtout(0));
		m.addCarte(new CarteAtout(21));
		m.addCarte(new CarteCouleur(Couleur.Carreau, 2));
		//m.addCarte(new CarteCouleur(Couleur.Carreau, 1));
		m.affiche();
		System.out.println(m.cartes.size());
	}

	public Carte getCarte(int num)
	{
		return cartes.elementAt(num);
	}

}
