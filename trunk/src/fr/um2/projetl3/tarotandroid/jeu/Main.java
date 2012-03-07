package fr.um2.projetl3.tarotandroid.jeu;

import java.util.Vector;

import fr.um2.projetl3.tarotandroid.clients.IJoueur;
import fr.um2.projetl3.tarotandroid.clients.JoueurTexte;

public class Main
{
	private Vector<Carte> cartes;
	private IJoueur proprietaire;

	public boolean addCarte(Carte c)
	{
		return cartes.add(c);
	}

	public boolean removeCarte(Carte c)
	{
		return cartes.remove(c);
	}
	
	public int nbCartesRestantes()
	{
		return cartes.size();
	}
	
	public boolean contains(Carte c)
	{
		return cartes.contains(c);
	}
	
	public Vector<Carte> getCartes()
	{
		return cartes;
	}
	
	public boolean possedeCouleur(Couleur coul)
	{
		boolean couleurExiste = false;
		for(Carte c: cartes)
		{
			if(c.isCouleur())
			{
				if(c.getCouleur() == coul)
				{
					couleurExiste = true;
					break;
				}
			}
		}
		return couleurExiste;
	}
	
	public boolean possedeAtout()
	{
		boolean atoutPresent = false;
		for(Carte c: cartes)
		{
			if(c.isAtout() && !c.isExcuse())
			{
				atoutPresent = true;
				break;
			}
		}
		return atoutPresent;
	}
	
	public void enleverEcart(Carte[] ecart)
	{
		for(Carte c: ecart)
		{
			if(contains(c))
			{
				removeCarte(c);
			}
		}
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
		System.out.println("]");
		System.out.println("-----");
	}
	
	public String getNomProprietaire()
	{
		return proprietaire.nom();
	}
	
	public Main(IJoueur proprietaire)
	{
		this.proprietaire = proprietaire;
		cartes = new Vector<Carte>();
	}
	
	public static void main(String[] args)
	{
		IJoueur j1 = new JoueurTexte("Truc");
		//System.out.println(j1.demanderAnnonce().getName()); erreur
		
		Main m = new Main(j1);
		m.addCarte(new Carte(Couleur.Trefle, 12));
		m.addCarte(new Carte(14));
		m.addCarte(new Carte(0));
		m.addCarte(new Carte(21));
		m.addCarte(new Carte(Couleur.Carreau, 2));
		//m.addCarte(new CarteCouleur(Couleur.Carreau, 1));
		m.affiche();
		System.out.println(m.cartes.size());
	}

	public Carte getCarte(int num)
	{
		return cartes.elementAt(num);
	}
	
	
	public boolean roiDansLaMain(Carte roi)
	{ 
		if(roi.getOrdre()==14)
		{
			for(int i=0; i<cartes.size(); i++)
			{
				if(cartes.get(i) == roi)
				{
					return true;
				}
			}
			return false;
		}
		else
		{	
			System.out.println("appel de Roi avec une carte non roi");
			return false;
		}
	}


}
