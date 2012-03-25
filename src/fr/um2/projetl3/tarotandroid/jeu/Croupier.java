package fr.um2.projetl3.tarotandroid.jeu;

public class Croupier {

	/**
	 * @author JB
	 * 
	 *  méthode qui donne la iéme main au iéme joueur
	 * 
	 * @param i
	 */
	void direMain(int i)
	{
		
	}
	
	/**
	 * @author JB
	 * méthode qui donne les mains à tous les joueur
	 */
	void direMains()
	{
		int nbJoueur = 4; // prb pr recup le nb de joueur
		for(int i=0;i<nbJoueur;i++)
		{
			direMain(i);
		}
	}
	
	/**
	 * @author JB
	 * 
	 * Dit le chien à tout le monde 
	 * inclure donner le chien au preneur ?
	 * 
	 */
	void reveleChien()
	{
		
	}
	
	/**
	 * @author JB
	 * 
	 * Demande l'ecart au preneur
	 * le recupere et le met dans le plis de l'attaque
	 */
	void phaseEcart()
	{
		
	}
	
	/**
	 * @author JB
	 * 
	 * c'est la phase des plis
	 * 	on demande une carte à un joueur on la valide
	 *   ensuite on fait de même pour les autres joueurs.
	 *  puis on range le pli dans le bon vecteur (defense ou attaque)
	 *  et ceci tant qu'il reste des cartes au joueur
	 */
	void jeuDeLaCarte()
	{
		int modifierLorsqueJePourrais = (Constantes.NOMBRE_CARTES_TOTALES-6)/4;
		
		for(int i=0;i<modifierLorsqueJePourrais;i++)
		{
			
		}
	}
	
	/**
	 * @author JB
	 * 
	 * appel la méthode de calcule des scores puis montre le resultat aux joueurs
	 */
	void montrerScore()
	{
		
	}
	
	/**
	 * @author JB
	 * 
	 * deroulement d'une donne
	 */
	void faireDonne()
	{
		
	}
	
	/**
	 * @author JB
	 * 
	 * deroulement d'une partie.
	 */
	void fairePartie()
	{
		
	}

}
