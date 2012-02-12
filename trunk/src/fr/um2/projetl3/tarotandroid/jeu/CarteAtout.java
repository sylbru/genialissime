package fr.um2.projetl3.tarotandroid.jeu;


public class CarteAtout implements Carte
{
	private int num;
	
	public int getNum()
	{
		return num;
	}
	
	/**
	 * @author sylvain
	 * @return boolean si c'est un bout true sinon false
	 */
	private boolean isBout()
	{
		switch(num)
		{
			case 0:  // Excuse
				return true;
			case 1:  // Petit
				return true;
			case 21: // … 21
				return true;
			default:
				return false;
		}
	}

	@Override
	public boolean isCouleur()
	{
		return false;
	}
	
	@Override
	public boolean isAtout()
	{
		return true;
	}

	@Override
	public int valeur()
	{
		if(isBout())
			return 9; // = 4.5 points
		else
			return 1;
	}
	
	@Override
	public String toString()
	{
		if(num == 0)
			return "Excuse";
		else
			return Integer.toString(num) + "Atout";
	}
	
	public CarteAtout(int num)
	{
		this.num = num;
	}

}
