package fr.um2.projetl3.tarotandroid.jeu;


public class CarteCouleur implements Carte
{
	
	private Couleur couleur;
	private int ordre; // ? ou ordre (à renommer ?)
	private int uid; // id « universel » (pour les communications) 
	
	public int getOrdre()
	{
		return ordre;
	}
	
	public Couleur getCouleur()
	{
		return couleur;
	}
	
	public int getUid()
	{
		return uid;
	}

	// @Override
	public boolean isCouleur()
	{
		return true;
	}

	// @Override
	public boolean isAtout()
	{
		return false;
	}
	
	// @Override
	public int valeur()
	{
		if(ordre >=1 && ordre <= 10)
			return 1;
		else if(ordre == 11)
			return 3;
		else if(ordre == 12)
			return 5;
		else if(ordre == 13)
			return 7;
		else if(ordre == 14)
			return 9;
		else
		{
			System.out.println("ordre > 14 ou <= 0, problème.");
			return -1;
		}
	}
	@Override
	public String toString()
	{
		String s;
		if(ordre <= 10)
			s = Integer.toString(ordre);
		else if(ordre == 11)
			s = "V";
		else if(ordre == 12)
			s = "C";
		else if(ordre == 13)
			s = "D";
		else if(ordre == 14)
			s = "R";
		else
			s = "?";
		return s + couleur.name().toLowerCase();
	}
	
	public CarteCouleur(Couleur couleur, int ordre) // TODO: rajouter uid
	{
		this.couleur = couleur;
		this.ordre = ordre;
		// TODO: rajouter uid
	}

	@Override
	public void affiche() 
	{
		String s;
		if(ordre <= 10)
			s = Integer.toString(ordre);
		else if(ordre == 11)
			s = "V";
		else if(ordre == 12)
			s = "C";
		else if(ordre == 13)
			s = "D";
		else if(ordre == 14)
			s = "R";
		else
			s = "?";
		
		switch(couleur)
		{
		case Trefle:
			s += "♣";
			break;
		case Carreau:
			s += "♦";
			break;
		case Coeur:
			s += "♥";
			break;
		case Pique:
			s += "♠";
			break;
		}
		
		System.out.print(s+" ");
	}	
}
