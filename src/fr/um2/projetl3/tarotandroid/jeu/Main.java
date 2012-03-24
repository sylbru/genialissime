package fr.um2.projetl3.tarotandroid.jeu;

import java.util.Vector;

import fr.um2.projetl3.tarotandroid.clients.IJoueur;
import fr.um2.projetl3.tarotandroid.clients.JoueurTexte;
import fr.um2.projetl3.tarotandroid.connection.Cartes;

public class Main
{
	private Vector<Carte> cartes;

	/**
	 * constructeur de main vide
	 */
	public Main()
	{
		cartes = new Vector<Carte>();
	}
	
	/**
	 * Ajoute une carte passée en paramètre dans la main.
	 * @param c une carte
	 * @return false si l’opération a échoué, true si elle a réussi.
	 */
	public boolean addCarte(Carte c)
	{
		// System.out.println("Ajout de " + c + " au joueur" + proprietaire);
		return cartes.add(c);
	}

	/**
	 * Enlève une carte passée en paramètre de la main.
	 * @param c une carte
	 * @return false si l’opération a échoué, true si elle a réussi.
	 */
	public boolean removeCarte(Carte c)
	{
		for (int i=0;i<cartes.size();i++)
		{
			if (c.uid()==cartes.get(i).uid()){
				cartes.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Donne le nombre de cartes restantes dans la main.
	 * @return le nombre de cartes restantes
	 */
	public int nbCartesRestantes()
	{
		return cartes.size();
	}
	
	/**
	 * Teste si une carte est dans la main ou pas.
	 * @param c une carte
	 * @return true si la main contient la carte c, false sinon.
	 */
	public boolean possede(Carte c)
	{
		for (Carte ca: cartes)
		{
			if (c.uid()==ca.uid()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Retourne le vecteur des cartes
	 * @return le vecteur contenant les cartes de la main.
	 */
	public Vector<Carte> getCartes()
	{
		return cartes;
	}
	
	/**
	 * Teste si la main possède la couleur passée en paramètre.
	 * @param coul une couleur
	 * @return true si la main possède au moins une carte de la couleur coul, false sinon.
	 */
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
	
	/**
	 * Teste si la main possède de l’atout.
	 * @return true si la main possède au moins un atout, false sinon.
	 */
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
	
	/**
	 * Enlève de la main l’écart passé en paramètre.
	 * @param ecart un tableau de cartes contenant l’écart
	 * On pourrait dire le nom du proprietaire de la main
	 */
	public void enleverEcart(Carte[] ecart)
	{
		for(Carte c: ecart)
		{
			if(possede(c))
			{
				removeCarte(c);
			}
			else
			{
				System.out.println("??? enleverEcart : carte " + c + " pas dans la main du joueur ");
			}
		}
	}
	
	/**
	 * Affiche la main, sous la forme :
	 * 
	 *   Main de NomDuJoueur : [ (7,♦) (2,♦) (3,♦) (4,♡) ]
	 *   
	 */
	public void affiche()
	{
		//System.out.print("Main de "+proprietaire.getNomDuJoueur()+" :\t");
		System.out.print("[ ");
		for(Carte c: cartes)
		{
			if(c != null)
				c.affiche();
			else
				System.out.print("# ");
		}
		System.out.println("]");
	}
	
	public Main(Cartes c)
	{
		for(int i = 0 ; i<c.size() ; i++)
		{
			cartes.add(c.getcarte(i));
		}
	}

	/**
	 * Donne la carte à l’indice indiqué en paramètre.
	 * @param num
	 * @return la carte à l’indice num
	 */
	public Carte getCarte(int num)
	{
		return cartes.elementAt(num);
	}
	
	/**
	 * Teste si la main possède un atout supérieur à l’ordre passé en paramètre
	 * @param ordre la valeur de l’atout à tester
	 * @return true si la main possède un atout supérieur à ordre (paramètre)
	 */
	public boolean possedeAtoutPlusGrand(int ordre)
	{
		// System.out.println("possede Atout plus grand que "+ordre+" ?");
		for(int i = 0; i< cartes.size(); i++)
		{
			if (cartes.get(i).isAtout() && cartes.get(i).getOrdre() > ordre)
			{
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args)
	{
		/*
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
		System.out.println(m.cartes.size());*/
	}

}
