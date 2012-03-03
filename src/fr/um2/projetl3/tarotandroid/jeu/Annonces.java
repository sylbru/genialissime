package fr.um2.projetl3.tarotandroid.jeu;

import fr.um2.projetl3.tarotandroid.clients.Joueur;
import static fr.um2.projetl3.tarotandroid.jeu.Context.*;

public class Annonces 
{
	/*
	 *  ---------------------------------------------------------------------------------------------
	 *  ------------------------Juste la méthode pour effectuer la phase des annonces----------------
	 *  ---------------------------------------------------------------------------------------------
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
		int compteurPourToutLeMondePasse = 0;
		int nombreDeJoueurs=P.getNombreDeJoueurs(); 
		Contrat contrat = Contrat.AUCUN;
		Contrat controle = Contrat.AUCUN;
		Contrat contratMax = Contrat.AUCUN;

		Contrat tableauDesContrat[] = new Contrat[nombreDeJoueurs]; 
		
		for(int i=0;i<nombreDeJoueurs;i++){
			tableauDesContrat[i]=Contrat.AUCUN;
		}
		
		Joueur joueurQuiVaPrendre = null;
		int combienVeulentPrendre = 0;
		
		while(conditionArret)
		{
			if(tableauDesContrat[numeroDuJoueur] != Contrat.PASSE ) // si le joueur n'as pas passer il peut annoncer
			{
				if(joueurQuiVaPrendre==P.getJoueur(numeroDuJoueur)) 
				{
					conditionArret = false;
				}
				else
				{
				
					contrat = P.getJoueur(numeroDuJoueur).demanderAnnonce(contratMax);  // demande au joueur quel contrat il veut faire et renvoie un contrat valide
					
					if (contrat.getPoids() > contratMax.getPoids())
					{
						System.out.println(" if poids actuel est > contrat max (contrat = "+contrat+")");
						contratMax = contrat;
					}
					/*
					 * TODO
					 * Le problème est maintenant là : si quelqu’un Petite et quelqu’un d’autre Garde, 
					 * celui qui a dit Petite ne peut pas surenchérir.
					 */
					
					
					System.out.println("Contrat du joueur "+P.getJoueur(numeroDuJoueur).getNomDuJoueur()+" : "+contrat.getName());
					
					tableauDesContrat[numeroDuJoueur] = contrat ; // on stocke les contrat que les joueur veulent faire
	
					
					if(contrat != Contrat.PASSE) // si un joeur passe on le prend en compte dans un compteur utile plus loin.
					{
						combienVeulentPrendre++;
					
						if(contrat != Contrat.PASSE) // si un joeur passe on le prend en compte dans un compteur utile plus loin.
						{
							combienVeulentPrendre++;
							
							if(contrat.getPoids() == 5) // alors c'est une garde_sans => la phase d'annonce est finit 
							{
								joueurQuiVaPrendre = P.getJoueur(numeroDuJoueur);
								conditionArret = false;
							}
							else if((0 < contrat.getPoids()) && (contrat.getPoids() < 5))// cas o� c'est un contrat valble mais pas une garde_sans
							{
								joueurQuiVaPrendre = P.getJoueur(numeroDuJoueur);
							}
						}
						if(numeroDuJoueur == nombreDeJoueurs) // si le numero du joueur est egal au nbr de joueur on as fait un tour d'annonce
						{
							// si il y a une seule prise on lance la partie
							if (combienVeulentPrendre == 0) // dans ce cas l� �a veux dire que tout le monde � passer
							{
								contrat = Contrat.AUCUN;
								conditionArret = false ;
							}
							else if (combienVeulentPrendre == 1)
							{
								conditionArret = false;
							}
							else if(combienVeulentPrendre > 1) // si plusieur joueur veulent prendre on refait un tour des joueur qui voulaient prendre
							{
								combienVeulentPrendre = 1 ; // remit � un car si tout le monde passe apres il faut conserver celui qui avait pris en dernier
								numeroDuJoueur = -1; // ! pas s�r manque des truc a faire u=ici je croit 
							}
						}
					}
				}
				numeroDuJoueur = P.donne().getNumJoueurApres(numeroDuJoueur);
			}
		}
		P.donne().setContratEnCours(contrat);
		P.donne().setPreneur(joueurQuiVaPrendre);
		
		if(nombreDeJoueurs==5)
		{
			phaseAppelRoi();
		}
	}
	
	public static void phaseAppelRoi()
	{
		Carte Roi = P.donne().getPreneur().appelerRoi();
		int nombreDeJoueurs = P.getNombreDeJoueurs();
		for(int i = 0; i<nombreDeJoueurs; i++)
		{
			if(P.getJoueur(i).possedeRoi(Roi)){
				P.donne().setJoueurAppele(P.getJoueur(i));
				// pas sûr que ce soit judicieux de faire ça ici, officiellement on ne sait pas qui
				// est avec qui avant que le roi appelé ne soit dévoilé
			} 
			else // si le chien n'est pas dans la main d'un joueur il est dans le chien, le preneur se retrouve donc tout seul.
			{
				P.donne().setJoueurAppele(P.donne().getPreneur());
			}
		}
	}
}
