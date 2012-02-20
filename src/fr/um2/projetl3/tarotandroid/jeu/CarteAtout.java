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
	public boolean isBout()
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
	

	// @Override
	public boolean isCouleur()
	{
		return false;
	}
	
	// @Override
	/**
	 * @return true si la carte est un (vrai) atout, false si c’est l’Excuse.
	 */
	public boolean isAtout()
	{
		return (num != 0);
	}

	// @Override
	public int valeur()
	{
		if(isBout())
			return 9; // = 4.5 points
		else
			return 1; // = 0.5 points
	}
	
	@Override
	public String toString()
	{
		if(num == 0)
			return "Excuse";
		else
			return Integer.toString(num) + "Atout";
	}
	
	public void affiche()
	{
		if(num == 0)
			System.out.print("*Ex ");
		else
			System.out.print("( "+num+","+"A ) ");
	}
	
	public CarteAtout(int num)
	{
		this.num = num;
	}

	public boolean isExcuse() 
	{
		return (num == 0);
	}

}
