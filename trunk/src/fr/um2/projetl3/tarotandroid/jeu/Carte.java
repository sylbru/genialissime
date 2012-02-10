package fr.um2.projetl3.tarotandroid.jeu;

public abstract class Carte
{
	public abstract boolean isCouleur();
	public boolean isAtout() { return !isCouleur(); }

	// Test
	public static void main(String[] args)
	{
		Carte[] maMain = new Carte[5];
		maMain[0] = CarteAtout.a19;
		maMain[1] = CarteAtout.a21;
		maMain[2] = CarteCouleur.cRPi;
		maMain[3] = CarteCouleur.c10Tr;
		maMain[4] = CarteCouleur.c1Co;
		for(Carte c: maMain)
		{
			if(c.isCouleur())
				System.out.println("C’est un atout");
			else
				System.out.println("C’est une couleur");
		}
	}
}
