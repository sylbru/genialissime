package fr.um2.projetl3.tarotandroid.jeu;

import fr.um2.projetl3.tarotandroid.clients.IJoueur;
import static fr.um2.projetl3.tarotandroid.jeu.Context.*;

public class Annonces 
{
	
	private static Contrat[] tableauDesContrats;
	
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
	protected static void phaseAnnonce()
	{
		int nombreDeJoueurs = P.getNombreDeJoueurs(); 
		tableauDesContrats = new Contrat[nombreDeJoueurs];
		
		int numeroDuJoueur = P.getNumJoueurApres(D.getNumDonneur());
		System.out.print("Le donneur était " + P.getNomNumJoueur(D.getNumDonneur()) + ", ");
		System.out.println("le premier à parler est " + P.getNomNumJoueur(numeroDuJoueur));
		
		Contrat contrat = Contrat.AUCUN;
		Contrat contratMax = Contrat.AUCUN;
		
		int numDernierJoueur = D.getNumDonneur();
		int numDernierJoueurTemporaire = D.getNumDonneur();

		int joueurQuiVaPrendre = -1;
		int combienVeulentPrendre = 0;
		
		for(int i=0;i<nombreDeJoueurs;i++)
		{
			tableauDesContrats[i]=Contrat.AUCUN;
		}
		
		boolean conditionArret = true;
		while(conditionArret)
		{
			if(tableauDesContrats[numeroDuJoueur] != Contrat.PASSE ) // si le joueur n'as pas passer il peut annoncer
			{
				if(joueurQuiVaPrendre==numeroDuJoueur) // sortie d'annonce : la boucle est revenu sur le joueur qui veux prendre
				{
					conditionArret = false;
					System.out.println("sortie d'annonce : la boucle est revenu sur le joueur qui veux prendre");
				}
				else
				{
					D.setNumJoueurEnContact(numeroDuJoueur);
					//System.out.println("Je demande l'annonce du joueur "+numeroDuJoueur);
					contrat = demanderAnnonceJoueur(numeroDuJoueur, contratMax);  // demande au joueur quel contrat il veut faire et renvoie un contrat valide
					System.out.println("coucou");
//					direJoueursAnnonce(contrat, numeroDuJoueur);
					
					if (contrat.getPoids() > contratMax.getPoids())
					{
						contratMax = contrat;
					}
					tableauDesContrats[numeroDuJoueur] = contrat ; // on stocke les contrat que les joueur veulent faire
					afficherTableauDesContrats();
					
					if(contrat != Contrat.PASSE) // si un joueur passe pas
					{
						combienVeulentPrendre++;
						
						if(contrat == Contrat.GARDE_CONTRE) // alors c'est une garde contre => la phase d'annonce est finit 
						{
							joueurQuiVaPrendre = numeroDuJoueur;
							conditionArret = false;
							System.out.println("sortie d'annonce : garde contre");
						}
						else 
						{
							joueurQuiVaPrendre = numeroDuJoueur;
							numDernierJoueurTemporaire = numeroDuJoueur;
						}
					}
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
			numeroDuJoueur = P.getNumJoueurApres(numeroDuJoueur);
		}
		
		System.out.println("Joueur qui va prendre : "+joueurQuiVaPrendre);
		System.out.println(nombreDeJoueurs);
		
		if(joueurQuiVaPrendre != -1)
		{
			D.setContratEnCours(tableauDesContrats[joueurQuiVaPrendre]);
			D.setPreneur(joueurQuiVaPrendre); // preneur est donc à -1 si personne n’a pris
			
			if(nombreDeJoueurs == 5)
			{
				phaseAppelRoi();
			}
		}
		else 
		{
			D.setContratEnCours(Contrat.AUCUN);
		}
	}
	
	private static void afficherTableauDesContrats()
	{
		for(int i=0; i<P.getNombreDeJoueurs(); i++) // on part de 0 donc pas besoin de modulo
		{
			System.out.println("Joueur "+i+" : "+tableauDesContrats[i].getName());
		}
	}

	/**
	 * Demande à un joueur son annonce, et vérifie si elle est valide (redemande jusqu’à recevoir une valide)
	 */
	protected static Contrat demanderAnnonceJoueur(int num, Contrat contratMax)
	{
		// TODO ajouter une limite
		Contrat annonceProposée = Contrat.AUCUN;
		int tentative = 0 ;
		do
		{
			tentative++;
			
			annonceProposée = P.getJoueur(num).demanderAnnonce(contratMax);
			if (annonceProposée.getPoids() <= contratMax.getPoids())
			{
				annonceProposée = Contrat.PASSE;
			}
			System.out.println(num+" annonce "+annonceProposée.getName());
		}
		while(!annonceValide(annonceProposée) && tentative < 3);
		
		if (tentative == 3)
		{
			System.out.println("Trop d'annonce invalide donc contrat := Passe");
			annonceProposée = Contrat.PASSE;
		}
		
		return annonceProposée;
	}
	
	private static boolean annonceValide(Contrat annonceProposée) 
	{
		if (annonceProposée != Contrat.PASSE)
		{	
			
			if (annonceProposée.getPoids() < getContratMax().getPoids())
			{
				return false;
			}
		}
		
		return true;
	}

	protected static void direJoueursAnnonce(Contrat c, int joueur)
	{
		int pos = 0;
		for(IJoueur j: P.getJoueurs())
		{
			j.direAnnonce(c, (joueur-pos)%P.getNombreDeJoueurs());
			pos++;
		}
	}
	
	/**
	 * Indique au joueur les annonces qu’il peut dire.
	 * TODO: à faire.
	 */
	public static Contrat getContratMax()
	{
		Contrat contratmax = Contrat.AUCUN;
		for(int i=0;i<P.getNombreDeJoueurs();i++)
		{
			if(contratmax.getPoids() < tableauDesContrats[i].getPoids())
			{
				contratmax = tableauDesContrats[i];
			}
		}
		
		return contratmax;
	}
	
	public static Contrat[] getTableauDesContrats() {
		return tableauDesContrats;
	}
	
	protected static void phaseAppelRoi()
	{
		Carte roi = P.getJoueur(D.getPreneur()).demanderAppelAuRoi();
		int nombreDeJoueurs = P.getNombreDeJoueurs();
		for(int i = 0; i<nombreDeJoueurs; i++)
		{
			if(D.getMain(i).possede(roi))
			{
				D.setJoueurAppele(i);
			} 
			else // si le chien n'est pas dans la main d'un joueur il est dans le chien, le preneur se retrouve donc tout seul.
			{
				D.setJoueurAppele(D.getPreneur());
			}
		}
	}
}
