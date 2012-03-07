package fr.um2.projetl3.tarotandroid.jeu;

import fr.um2.projetl3.tarotandroid.clients.IJoueur;
import static fr.um2.projetl3.tarotandroid.jeu.Context.*;

public class Annonces 
{
	/*
	 *  ---------------------------------------------------------------------------------------------
	 *  ------------------------Juste la méthode pour effectuer la phase des annonces----------------
	 *  ------------------------------------faut encore faire des tests----------------------------------------
	 */
	
	/**
	 * @author JB
	 *   permet de connaitre le preneur et le type de contrat fait par le joueur
	 * 		et si le jeu est à 5 le joeur appelée est decidé
	 * 
	 */
	public static void phaseAnnonce()
	{
		boolean conditionArret = true;
		int numeroDuJoueur = P.donne().getNumJoueurApres(P.donne().getNumDonneur());
		System.out.println("Le donneur était n°"+P.donne().getNumDonneur()+", le premier à parler est n°"+numeroDuJoueur);
		int nombreDeJoueurs=P.getNombreDeJoueurs(); 
		Contrat contrat = Contrat.AUCUN;
		Contrat controle = Contrat.AUCUN;
		Contrat contratMax = Contrat.AUCUN;
		
		int numDernierJoueur = P.donne().getNumDonneur();
		int numDernierJoueurTemporaire = P.donne().getNumDonneur();

		IJoueur joueurQuiVaPrendre = null;
		int combienVeulentPrendre = 0;
		
		Contrat tableauDesContrat[] = new Contrat[nombreDeJoueurs]; 
		for(int i=0;i<nombreDeJoueurs;i++){
			tableauDesContrat[i]=Contrat.AUCUN;
		}

		while(conditionArret)
		{
			if(tableauDesContrat[numeroDuJoueur] != Contrat.PASSE ) // si le joueur n'as pas passer il peut annoncer
			{
				if(joueurQuiVaPrendre==P.getJoueur(numeroDuJoueur)) // sortie d'annonce : la boucle est revenu sur le joueur qui veux prendre
				{
					conditionArret = false;
					System.out.println("sortie d'annonce : la boucle est revenu sur le joueur qui veux prendre");
				}
				else
				{
					contrat = P.getJoueur(numeroDuJoueur).demanderAnnonce(contratMax);  // demande au joueur quel contrat il veut faire et renvoie un contrat valide
					direJoueursAnnonce(contrat, P.getJoueur(numeroDuJoueur));
					
					if (contrat.getPoids() > contratMax.getPoids())
					{
						System.out.println(" if poids actuel est > contrat max (contrat = "+contrat+")");
						contratMax = contrat;
					}
					System.out.println("Contrat du joueur "+P.getJoueur(numeroDuJoueur).getNomDuJoueur()+" : "+contrat.getName());
					
					tableauDesContrat[numeroDuJoueur] = contrat ; // on stocke les contrat que les joueur veulent faire
	
					if(contrat != Contrat.PASSE) // si un joeur passe pas
					{
						combienVeulentPrendre++;
						
						if(contrat.getPoids() == Contrat.GARDE_CONTRE.getPoids()) // alors c'est une garde_sans => la phase d'annonce est finit 
						{
							joueurQuiVaPrendre = P.getJoueur(numeroDuJoueur);
							conditionArret = false;
							System.out.println("sortie d'annonce : garde sans");
						}
						else 
						{
							joueurQuiVaPrendre = P.getJoueur(numeroDuJoueur);
							numDernierJoueurTemporaire = numeroDuJoueur;
						}
					}
					/*   Test
					 *	
					 *	System.out.println("numero du joueur : "+ numeroDuJoueur);
						System.out.println("numero du dernier : "+ numDernierJoueur);
						System.out.println("numero du dernier temporaire : "+ numDernierJoueurTemporaire); 
					 */
					if(numeroDuJoueur == numDernierJoueur) // si on as fait un tour d'annonce
					{
						System.out.println("4");
						// si il y a une seule prise on lance la partie
						if (combienVeulentPrendre == 0) // dans ce cas l� �a veux dire que tout le monde � passer
						{
							contrat = Contrat.AUCUN;
							conditionArret = false ;
							
							System.out.println("sortie d'annonce : tlm passe");
						}
						else if (combienVeulentPrendre == 1)
						{
							conditionArret = false;
							System.out.println("sortie d'annonce : un seul veux prendre");
						}
						else if(combienVeulentPrendre > 1) // si plusieur joueur veulent prendre on refait un tour des joueur qui voulaient prendre
						{
							combienVeulentPrendre = 1 ; // remit � u1 car si tout le monde passe apres il faut conserver celui qui avait pris en dernier
							numeroDuJoueur = numDernierJoueurTemporaire+1;
							// Pour que la boucle recommence juste apres le dernier joueur qui veux prendre
							numDernierJoueur = numDernierJoueurTemporaire;
							// Pour que la boucle s'arrete lorsque l'on retombe sur le dernier joueur à vouloir prendre
							
							/*	Test
							System.out.println("\tnumero du joueur : "+ numeroDuJoueur);
							System.out.println("\tnumero du dernier : "+ numDernierJoueur);
							System.out.println("\tnumero du dernier temporaire : "+ numDernierJoueurTemporaire);
							*/
						}
					}
				}
			}
			numeroDuJoueur = P.donne().getNumJoueurApres(numeroDuJoueur);
		}
		P.donne().setContratEnCours(contrat);
		P.donne().setPreneur(joueurQuiVaPrendre);
		
		if(nombreDeJoueurs==5) {phaseAppelRoi();}
	}
	
	public static void direJoueursAnnonce(Contrat c, IJoueur joueur)
	{
		for(IJoueur j: P.getJoueurs())
		{
			j.direAnnonce(c, joueur);
		}
	}
	
	public static void phaseAppelRoi()
	{
		Carte Roi = P.donne().getPreneur().appelerRoi();
		int nombreDeJoueurs = P.getNombreDeJoueurs();
		for(int i = 0; i<nombreDeJoueurs; i++)
		{
			if(P.getJoueur(i).possedeRoi(Roi))
			{
				P.donne().setJoueurAppele(P.getJoueur(i));
			} 
			else // si le chien n'est pas dans la main d'un joueur il est dans le chien, le preneur se retrouve donc tout seul.
			{
				P.donne().setJoueurAppele(P.donne().getPreneur());
			}
		}
	}
}
