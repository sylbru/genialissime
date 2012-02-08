package fr.um2.projetl3.tarotandroid.jeu;

import java.util.Vector;

public class Scores
{
	private Vector<Integer[]> scores;
	/*
	 * cette fonction actualise le tableau de score en calculant la derniere ligne
	 * les variables en paramettre sont le type du contrat, le gain(de combien le jouer la remporte ou perdu, valeur toujour positive)
	 * si le joueur qui a fait le contrat l'a remporte, et finalament quel est le jouer qui a fait le contrat
	 /**/
	public Scores()
	{
		int J = Partie.getNombreDeJoueur();
		scores = new Vector<Integer[]>();
		Integer[] derniereLigne = new Integer[J];
		for(int i =0; i<J;i++)
		{
			derniereLigne[i]=0;
		}
		scores.add(derniereLigne);

	}
	
	
	public void calculDerniereLigneScore(Contrat typeDuContrat, int Gain,boolean joueurReussie, int joueurContrat)
	{
		int J = Partie.getNombreDeJoueur();
		int valeurScore = calculScore(typeDuContrat, Gain);
		Integer[] dernierResultat = new Integer[J];
		Integer[] derniereLigne = new Integer[J];
		derniereLigne = scores.lastElement();
		dernierResultat = ScoreLigne(valeurScore, joueurReussie, joueurContrat);
		for(int i = 0; i<J; i++)
		{
			derniereLigne[i]+=dernierResultat[i];
		}
		scores.add(derniereLigne);
	}
	
	public void affiche(){
		System.out.println("Scores : ");
		int J = Partie.getNombreDeJoueur();
		int I = scores.size();
		/*
		pour afficher les noms de joueurs en debut de tableau 
		
		for(int i = 0; i<J ; i++)
		{
		 
			System.out.print(getNomJoueur(i));
		}
		/**/
		for(int i = 0; i<I; i++)
		{
			for(int j = 0; j<J; j++)
			{
				System.out.print(scores.get(i)[j]);
				System.out.print("\t");
			}
			System.out.println();
		}
		
	}
	




	public Integer[] ScoreLigne(int valeurScore, boolean joueurReussie, int joueurContrat)
	{
		Integer[] lscore = new Integer[Partie.getNombreDeJoueur()];
		if(joueurReussie)
		{
			for(int i=0; i < Partie.getNombreDeJoueur(); i++)
			{
				if(i == joueurContrat)
				{
					lscore[i] = valeurScore * Partie.getNombreDeJoueur();
				}
				else
				{
					lscore[i] = -valeurScore;
				}
			}
		}
		else 
		{
			for(int i=0; i < Partie.getNombreDeJoueur(); i++)
			{
				if(i == joueurContrat)
				{
					lscore[i] = -valeurScore * Partie.getNombreDeJoueur();
				}
				else
				{
					lscore[i] = valeurScore;
				}
			}
		}
		return lscore;
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
	}
}
/* test de classe	
	public static void main(String[] args)
	{
		Scores S = new Scores();
		Contrat C = new Contrat(NomDesContrats.Garde);
		S.calculDerniereLigneScore(C,10,true,2);
		C.setName(NomDesContrats.GardeSans);
		S.calculDerniereLigneScore(C,20,false,0);
	
		S.affiche();

	}

*/


 
/**
 * @author jbsubils
 */
// ! gros bordel dans cette class je vais la modifier
