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
		int J = Partie.getNombreDeJoueurs();
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
		int J = Partie.getNombreDeJoueurs();
		int valeurScore = calculScore(typeDuContrat, Gain);
		Integer[] dernierResultat = new Integer[J];
		Integer[] derniereLigne = new Integer[J];
		derniereLigne = scores.lastElement();
		dernierResultat = ScoreLigne(valeurScore, joueurReussie, joueurContrat);
		for(int i = 0; i<J; i++)
		{
			derniereLigne[i] = derniereLigne[i] + dernierResultat[i];
		}
		scores.add(derniereLigne);
	}
	
	public void affiche(){
		System.out.println("Scores : ");
		int J = Partie.getNombreDeJoueurs();
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
		Integer[] lscore = new Integer[Partie.getNombreDeJoueurs()];
		if(joueurReussie)
		{
			for(int i=0; i < Partie.getNombreDeJoueurs(); i++)
			{
				if(i == joueurContrat)
				{
					lscore[i] = valeurScore * Partie.getNombreDeJoueurs();
				}
				else
				{
					lscore[i] = -valeurScore;
				}
			}
		}
		else 
		{
			for(int i=0; i < Partie.getNombreDeJoueurs(); i++)
			{
				if(i == joueurContrat)
				{
					lscore[i] = -valeurScore * Partie.getNombreDeJoueurs();
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
		if( typeDuContrat.getName() == "Garde"){
			resultat*=2;
		}
		else if( typeDuContrat.getName() == "Garde sans"){
			resultat*=4;
		}
		else if( typeDuContrat.getName() == "Garde contre"){
			resultat*=6;
		}
		
		return resultat;
	}

/* test de classe	*/
	public static void main(String[] args)
	{
		Scores S = new Scores();
		S.affiche();
		Contrat C = Contrat.GARDE_SANS;
		S.calculDerniereLigneScore(C,10,true,2);
		C = Contrat.PETITE;
		S.calculDerniereLigneScore(C,20,false,0);
	
		S.affiche();

	}

}


 
/**
 * @author jbsubils
 */
// ! gros bordel dans cette class je vais la modifier
