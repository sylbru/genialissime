package fr.um2.projetl3.tarotandroid.jeu;

import java.io.Serializable;

import fr.um2.projetl3.tarotandroid.exceptions.CarteUIDInvalideException;

// test42

public class Carte implements Serializable
{

	private static final long serialVersionUID = 1L;
	private boolean carteCouleur; // ? si je mets à Atout c’est moins clair au niveau de l’excuse
	private Couleur couleur;
	private int ordre; // à la fois ordre de CarteCouleur et num de CarteAtout
	
	/* ****************************************************
	 * Constructeurs :
	 *  - si un seul paramètre, c’est un atout (ou excuse)
	 *  - si deux paramètres Couleur et int, c’est une carte de couleur
	 *  - (moche) si deux paramètres boolean et int, l’int est l’uid de la carte à créer
	 */
	
	public Carte(int ordreCarte)
	{
		if (ordreCarte < 0 || ordreCarte > 77) // Erreur
		{
			this.carteCouleur = false;
			this.ordre = -1;
		} else if (ordreCarte < 22) // Atout
		{
			this.carteCouleur = false;
			this.ordre = ordreCarte;
		} else if (ordreCarte < 36) // Coeur
		{
			this.carteCouleur = true;
			this.ordre = ordreCarte - 21;
			this.couleur = Couleur.Coeur;
		} else if (ordreCarte < 50) // Pique
		{
			this.carteCouleur = true;
			this.ordre = ordreCarte - 35;
			this.couleur = Couleur.Pique;
		} else if (ordreCarte < 64) // Carreau
		{
			this.carteCouleur = true;
			this.ordre = ordreCarte - 49;
			this.couleur = Couleur.Carreau;
		} else						// Trèfle
		{
			this.carteCouleur = true;
			this.ordre = ordreCarte - 63;
			this.couleur = Couleur.Trefle;
		}
	}
	
	public Carte(Couleur couleur, int ordre)
	{
		carteCouleur = true;
		this.couleur = couleur;
		this.ordre = ordre;
	}
	
	// le boolean sert uniquement à différencier le constructeur (moche, je sais)
	// donc il peut être à true ou false, on s’en fout.
	public Carte(boolean pourUid, int uid) throws CarteUIDInvalideException
	{
		if(uid >= 0 && uid <= 21)
		{
			carteCouleur = false;
			ordre = uid;
		}
		else if(uid < 78)
		{
			carteCouleur = true;
			ordre = ((uid-22) % 14) + 1; 
			
			switch(uid-ordre)
			{
			case 21:
				couleur = Couleur.Coeur;
				break;
			case 35:
				couleur = Couleur.Pique;
				break;
			case 49:
				couleur = Couleur.Carreau;
				break;
			case 63:
				couleur = Couleur.Trefle;
				break;
			}
		}
		else // uid < 0 || uid > 77
		{
			throw new CarteUIDInvalideException(uid);
		}
	}
	
	/* ***************************************
	 * Getteurs
	 * (pas de setteurs, on ne modifie pas les cartes après leur construction)
	 */
	public int getOrdre()
	{
		return ordre;
	}
	
	public Couleur getCouleur()
	{
		return couleur;
	}
	
	/* ***************************************
	 * Tests
	 */
	
	/**
	 * @author niavlys
	 * @return true si c’est une carte de couleur, false sinon.
	 */
	public boolean isCouleur()
	{
		return carteCouleur;
	}
	
	/**
	 * @author niavlys
	 * @return true si la carte est un (vrai) atout, false si c’est une carte de couleur ou l’Excuse.
	 * 
	 * ! j’ai du mal à décider pour l’excuse. Faut-il faire deux méthodes ? 
	 */
	public boolean isAtout()
	{
		return !carteCouleur && ordre > 0;
	}
	
	/**
	 * @author niavlys
	 * @return true si c'est un bout, false sinon
	 */
	public boolean isBout()
	{
		return !carteCouleur && (ordre == 0 || ordre == 1 || ordre == 21); 
	}
	
	/**
	 * @author niavlys
	 * @return true si c’est l’excuse, false sinon
	 */
	public boolean isExcuse()
	{
		return !carteCouleur && ordre == 0;
	}

	/* ******************************************** */
	
	/**
	 * @author niavlys
	 * @return la valeur de la carte, en demi-points
	 */
	public int valeur()
	{
		if(isCouleur())
		{
			if(ordre >=1 && ordre <= 10)
				return 1; // = 0.5 points
			else if(ordre == 11)
				return 3; // = 1.5 points
			else if(ordre == 12)
				return 5; // = 2.5 points
			else if(ordre == 13)
				return 7; // = 3.5 points
			else if(ordre == 14)
				return 9; // = 4.5 points
			else
			{
				System.out.println("ordre > 14 ou <= 0, problème.");
				return -1;
			}
		}
		else
		{
			if(isBout())
				return 9; // = 4.5 points
			else
				return 1; // = 0.5 points
		}
	}
	
	/**
	 * @author niavlys
	 * @return l’uid de la carte, un entier de 0 à 77
	 */
	public int uid()
	{
		if(!isCouleur())
		{
			return ordre;
		}
		else
		{
			switch(couleur)
			{
			case Coeur:
				return 21+ordre;
			case Pique:
				return 35+ordre;
			case Carreau:
				return 49+ordre;
			case Trefle:
				return 63+ordre;
			default:
				return -1;
			}
		}
	}
	
	/* ********************************************
	 * Affichage
	 */
	
	/**
	 * @author niavlys
	 * @return rien, affiche sous la forme V♥, 4A…
	 */
	public void affiche()
	{
		if(isCouleur())
		{
			String s = "(";
			
			if(ordre <= 10)
				s += Integer.toString(ordre);
			else if(ordre == 11)
				s += "V";
			else if(ordre == 12)
				s += "C";
			else if(ordre == 13)
				s += "D";
			else if(ordre == 14)
				s += "R";
			else
				s += "?";
			
			s += ",";
			
			switch(couleur)
			{
			case Trefle:
				s += "♣";
				break;
			case Carreau:
				s += "♦";
				break;
			case Coeur:
				s += "♡";
				break;
			case Pique:
				s += "♠";
				break;
			}
			
			s += ")";
			
			System.out.print(s+" ");
		}
		else
		{
			if(ordre == 0)
				System.out.print("(*Ex) ");
			else
				System.out.print("("+ordre+","+"A) ");
		}
	}
	
	/**
	 * @author niavlys
	 * @return String de la forme Vcoeur, 4Atout…
	 */
	public String toString()
	{
		if(isCouleur())
		{
			String s;
			if (ordre==1)
				s = "As";
			else if(ordre <= 10)
				s = Integer.toString(ordre);
			else if(ordre == 11)
				s = "Valet";
			else if(ordre == 12)
				s = "Cavalier";
			else if(ordre == 13)
				s = "Dame";
			else if(ordre == 14)
				s = "Roi";
			else
				s = "?";
			return s + " de " + couleur.name().toLowerCase();			
		}
		else
		{
			if(ordre == 0)
				return "Excuse";
			else
				return Integer.toString(ordre) + " d'atout";
		}
	}

	
	// TODO: méthodes concernant les relations entre les cartes (?)
	// Exemple : est-ce que cette carte est supérieure à celle-ci, est-elle de la même couleur que celle-ci ?
	// (À voir si on peut et si c’est bien de les mettre ici.)
}
