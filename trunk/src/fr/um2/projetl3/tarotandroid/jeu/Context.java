package fr.um2.projetl3.tarotandroid.jeu;

/*
 * Les champs statiques de cette classe sont prévus pour être importés
 * dans les classes qui ont besoin comme ceci (exemple avec P) :
 * 
 *    import static fr.um2.projetl3.tarotandroid.jeu.Context.P;
 * 
 * Grâce au mot-clé static, on peut accéder à P sans rien d’autre : P.lancerPartie() par exemple.
 */

// Je sais pas trop ce que signifie « Context » en général mais j’ai
// l’impression que c’est pour ce genre d’utilisation.
public class Context
{
	public static Partie P; // la partie en cours
	public static Donne D; // la donne en cours (?)  ! Pas encore utilisé !
	
	
	public static void main(String[] args)
	{
		
	}
}
