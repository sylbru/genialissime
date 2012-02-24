package fr.um2.projetl3.tarotandroid.jeu;

import fr.um2.projetl3.tarotandroid.clients.Joueur;


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
		int numeroDuJoueur = Donne.getNumJoueurApres(Donne.getNumDonneur());
		int compteurPourToutLeMondePasse = 0;
		int nombreDeJoueurs=Partie.getNombreDeJoueurs(); 
		Contrat contrat = Contrat.AUCUN;
		Contrat controle = Contrat.AUCUN;

		Contrat tableauDesContrat[] = new Contrat[nombreDeJoueurs]; 
		
		for(int i=0;i<nombreDeJoueurs;i++){
			tableauDesContrat[i]=Contrat.AUCUN;
		}
		
		Joueur joueurQuiVaPrendre = null;
		int combienVeulentPrendre = 0;
		
		while(conditionArret)
		{
			if(tableauDesContrat[numeroDuJoueur].getPoids() != 0 ) // si le joueur n'as pas passer il peut annoncer
			{
				if(joueurQuiVaPrendre==Partie.getJoueur(numeroDuJoueur)) 
				{
					conditionArret = false;
				}
				else
				{
				
					contrat = Partie.getJoueur(numeroDuJoueur).demanderAnnonce(contrat);  // demande au joueur quel contrat il veut faire et renvoie un contrat valide
					
					System.out.println("Contrat du joueur"+Partie.getJoueur(numeroDuJoueur).getNomDuJoueur()+" : "+contrat.getName());
					
					tableauDesContrat[numeroDuJoueur] = contrat ; // on stocke les contrat que les joueur veulent faire
	
					
					if(contrat != Contrat.PASSE) // si un joeur passe on le prend en compte dans un compteur utile plus loin.
					{
						combienVeulentPrendre++;
					
						if(contrat != Contrat.PASSE) // si un joeur passe on le prend en compte dans un compteur utile plus loin.
						{
							combienVeulentPrendre++;
							
							if(contrat.getPoids() == 5) // alors c'est une garde_sans => la phase d'annonce est finit 
							{
								joueurQuiVaPrendre = Partie.getJoueur(numeroDuJoueur);
								conditionArret = false;
							}
							else if((0 < contrat.getPoids()) && (contrat.getPoids() < 5))// cas o� c'est un contrat valble mais pas une garde_sans
							{
								joueurQuiVaPrendre = Partie.getJoueur(numeroDuJoueur);
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
				numeroDuJoueur++;
			}
		}
		Donne.setContratEnCours(contrat);
		Donne.setPreneur(joueurQuiVaPrendre);
		if(nombreDeJoueurs==5)
		{
			CarteCouleur Roi = joueurQuiVaPrendre.appelerRoi();
			for(int i = 0; i<nombreDeJoueurs; i++)
			{
				if(Partie.getJoueur(i).possedeRoi(Roi)){
					Donne.setJoueurAppele(Partie.getJoueur(i));
					// pas sûr que ce soit judicieux de faire ça ici, officiellement on ne sait pas qui
					// est avec qui avant que le roi appelé ne soit dévoilé
				} 
			}
		}
	}
}
