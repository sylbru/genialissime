package fr.um2.projetl3.tarotandroid.jeu;

import java.util.Vector;

public class Scores
{
	private Vector<LigneScore> scores;
	

}

class LigneScore
{
	//Integer[] lscore = new Integer[Partie.getNombreDeJoueur()];
	

	public void ScoreLigne(Integer[] lscore, int valeurScore, int joueurReussie){
		
	}
	
	public int calculScore(Contrat typeDuContrat, int Gain)
	{
		int resultat = 25;
		resultat = resultat + Gain ;
		
		// ! faire les types de contrats 
		if( typeDuContrat.getName() == NomDesContrats.Garde){
			resultat*=2;
		}
		else if( typeDuContrat.getName() == NomDesContrats.GardeSans){
			resultat*=4;
		}
		else if( typeDuContrat.getName() == NomDesContrats.GardeContre){
			resultat*=6;
		}
		
		return resultat;
		//TODO
	}

}
/**
 * @author jbsubils
 */
// ! gros bordel dans cette class je vais la modifier
