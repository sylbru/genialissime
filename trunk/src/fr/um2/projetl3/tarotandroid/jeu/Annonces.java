package fr.um2.projetl3.tarotandroid.jeu;

import fr.um2.projetl3.tarotandroid.clients.Joueur;


public class Annonces 
{
	/**
	 * @author JB
	 * 
	 *   phase d'annonce pas encore fini
	 *   	à modifier :
	 *   		pour l'instant on peut faire une petite m�me si le joueur pr�cedent � fait une garde
	 *   				il faut modifier la m�thode controlContrat pour qu'elle v�rifie que le joeur demande bien un contrat sup�tieur au pr�cedent
	 *   				ou passer directement dans demande annonce le dernier contrat le plus fort pour pas que le joueur est le choix de prendre un contrat inferieur
	 *   					je pense que la deuxieme solution est la meilleure car la premiere peut rendre infinie la phase d'annonce
	 *   		je pense que qu'un joueur peut surencherir sur �a propre ench�re 
	 *   				pour r�soudre �a on pourrait utiliser une variable qui m�morise le dernier preneur pour pas qu'il puisse surencherir 
	 *   
	 *   permet de connaitre le preneur et le type de contrat fait par le joueur
	 * 
	 */
	public static void phaseAnnonce()
	{
		boolean conditionArret = true;
		int compteurPourToutLeMondePasse = 0;
		int nombreDeJoueur=Partie.getNombreDeJoueurs(); 
		Contrat contrat = new Contrat("Aucune prise", -1);
		Contrat controle = new Contrat("Aucune prise", -1);
		int numeroDuJoueur = 0;
		Contrat tableauDesContrat[] = new Contrat[nombreDeJoueur]; 
		
		for(int i=0;i<nombreDeJoueur;i++){
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
						if(numeroDuJoueur == nombreDeJoueur) // si le numero du joueur est egal au nbr de joueur on as fait un tour d'annonce
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
	}
	/*
	public void annonce4joueurs()
	{
	
		Contrat con = Contrat.PASSE;
		Contrat controle = Contrat.PASSE;
		for(int i=0; i<8 ; i++ )
		{
			controle = demandejouer(con,i%4);
			if	(controleContrats(con,controle))
			{
				if (controle.getName()=="Passe"){
					informejoueurs(con,controle);
				}
				con=controle;
				informejoueurs(con);
			}
			else
			{
				gardeillegale(i%4);
			}
		}
	}
	*/
	
	public static void informejoueurs(Contrat ancien, Contrat nouveau){
		//TO-DO informe tous les joueurs si le jouer n'a pas pris de contrats ou si le contrats a augmente
	}
	public static void informejoueurs(Contrat con){
		//TO-DO informe tous les joueurs si le jouer n'a pas pris de contrats ou si le contrats a augmente
	}
	
	public static void gardeillegale(int i){
		//TO-DO informe le jouer i que sa garde est illegale
		// ! je comprend pas cette m�thode en quoi une garde est illegale ( �quel moment une garde peut �tre ill�gale) ?
	}
}
