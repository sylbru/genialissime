package fr.um2.projetl3.tarotandroid.jeu;


public class CarteAtout extends Carte
{
	private int num;
	
	public int getNum()
	{
		return num;
	}
	
	/**
	 * 
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
	
	private CarteAtout(int num)
	{
		this.num = num;
	}
	
	// Initialisation des 21 atouts + l’excuse
	
	public static Carte a1 = new CarteAtout(1);
	public static Carte a2 = new CarteAtout(2);
	public static Carte a3 = new CarteAtout(3);
	public static Carte a4 = new CarteAtout(4);
	public static Carte a5 = new CarteAtout(5);
	public static Carte a6 = new CarteAtout(6);
	public static Carte a7 = new CarteAtout(7);
	public static Carte a8 = new CarteAtout(8);
	public static Carte a9 = new CarteAtout(9);
	public static Carte a10 = new CarteAtout(10);
	public static Carte a11 = new CarteAtout(11);
	public static Carte a12 = new CarteAtout(12);
	public static Carte a13 = new CarteAtout(13);
	public static Carte a14 = new CarteAtout(14);
	public static Carte a15 = new CarteAtout(15);
	public static Carte a16 = new CarteAtout(16);
	public static Carte a17 = new CarteAtout(17);
	public static Carte a18 = new CarteAtout(18);
	public static Carte a19 = new CarteAtout(19);
	public static Carte a20 = new CarteAtout(20);
	public static Carte a21 = new CarteAtout(21);
	public static Carte aExcuse = new CarteAtout(0);
	
}
