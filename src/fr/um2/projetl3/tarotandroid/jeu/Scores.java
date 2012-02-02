package fr.um2.projetl3.tarotandroid.jeu;

import java.util.Vector;

public class Scores
{
	private Vector<LigneScore> scores;
	
	static public int getScore(NomDesContrats c){
		if (c==NomDesContrats.Pouce) {
			return 10;
		} else {
			return 20;
		}
	}
}

class LigneScore
{
	Integer[] lscore;
	
	public int calculScore(int reussite, Contrat typeDuContrat)
	{
		int resultat = 0;
		
		// ! faire les types de contrats 
		resultat = Scores.getScore(typeDuContrat.getName());
		
		return resultat;
		//TODO
	}

}
/**
 * @author jbsubils
 */
// ! gros bordel dans cette class je vais la modifier
