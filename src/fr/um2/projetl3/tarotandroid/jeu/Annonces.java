package fr.um2.projetl3.tarotandroid.jeu;


public class Annonces 
{
	/**
	 * @author JB
	 * 
	 *   phase d'annonce pas encore fini
	 *   	à modifier :
	 *   		pour l'instant on peut faire une petite même si le joueur précedent à fait une garde
	 *   				il faut modifier la méthode controlContrat pour qu'elle vérifie que le joeur demande bien un contrat supétieur au précedent
	 *   				ou passer directement dans demande annonce le dernier contrat le plus fort pour pas que le joueur est le choix de prendre un contrat inferieur
	 *   		je pense que qu'un joueur peut surencherir sur ça propre enchère 
	 *   				pour résoudre ça on pourrait utiliser une variable qui mémorise le dernier preneur pour pas qu'il puisse surencherir 
	 *   
	 *   permet de connaitre le preneur
	 * 
	 */
	public void phaseAnnonce()
	{
		boolean conditionArret = true;
		int compteurPourToutLeMondePasse = 0;
		int nombreDeJoueur=Partie.getNombreDeJoueurs(); 
		Contrat contrat = new Contrat("Aucune prise", -1);;
		int numeroDuJoueur = 0;
		Contrat tableauDesContrat[] = null; 
		// ! tableau à mettre à la taille du nombre de joeur et remplir de contrat AUCUNE
		Joueur joueurQuiVaPrendre = null;
		
		int combienVeulentPrendre = 0;
		
		
		
		while(conditionArret)
		{
			if(tableauDesContrat[numeroDuJoueur].getPoids() == 0) // si le joueur n'as pas passer il peut annoncer
			{
				
				/**        ICI il manque un tru important expliquer dans les ligne suivantes                 **/
				contrat = Partie.getJoueur(numeroDuJoueur).demanderAnnonce();  // demande au joueur quel contrat il veut faire
				
				// ! ici il faut faire en sorte que le contrat que choisit le preneur soit superieur au contrat pris par un prédécesseur
				// ? à faire dans demandeAnnonce()
				// pour la suite je considère ça comme fait.
				
				
				
				
				tableauDesContrat[numeroDuJoueur] = contrat ; // on stock les contrat que les joueur veulent faire
				
				if(contrat != Contrat.PASSE) // si un joeur passe on le prend en compte dans un compteur utile plus loin.
				{
					combienVeulentPrendre++;
					
					if(contrat.getPoids() == 5) // alors c'est une garde_sans => la phase d'annonce est finit 
					{
						joueurQuiVaPrendre = Partie.getJoueur(numeroDuJoueur);
						conditionArret = false;
					}
					else if((0 < contrat.getPoids()) && (contrat.getPoids() < 5))// cas où c'est un contrat valble mais pas une garde_sans
					{
						joueurQuiVaPrendre = Partie.getJoueur(numeroDuJoueur);
					}
				}
				if(numeroDuJoueur == nombreDeJoueur) // si le numero du joueur est egal au nbr de joueur on as fait un tour d'annonce
				{
					// si il y a une seule prise on lance la partie
					if (combienVeulentPrendre == 0) // dans ce cas là ça veux dire que tout le monde à passer
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
						combienVeulentPrendre = 1 ; // remit à un car si tout le monde passe apres il faut conserver celui qui avait pris en dernier
						numeroDuJoueur = 0; // ! pas sûr manque des truc a faire u=ici je croit 
					}
				}
				numeroDuJoueur++;
			}
		}
		Donne.setContratEnCours(contrat);
		Donne.setPreneur(joueurQuiVaPrendre);
	}
	
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
	
	
	private Contrat demandejouer(Contrat con, int i) // ! méthode déja existante dan Joueur
	{
		// TODO Auto-generated method stub
		return null;
	}


	public boolean controleContrats(Contrat a,Contrat b)
	{
		if(b.getPoids()==0)
		{
			return true;
		}
		else if(a.getPoids()==5) return false;//?ce cas ne devrait jamais arriver on arrete de demander les jouers une fois le plus grand contract fait
		else if(a.getPoids()<b.getPoids()) return true;
		else return false;
		
	}

	
	public void informejoueurs(Contrat ancien, Contrat nouveau){
		//TO-DO informe tous les joueurs si le jouer n'a pas pris de contrats ou si le contrats a augmente
	}
	public void informejoueurs(Contrat con){
		//TO-DO informe tous les joueurs si le jouer n'a pas pris de contrats ou si le contrats a augmente
	}
	
	public void gardeillegale(int i){
		//TO-DO informe le jouer i que sa garde est illegale
		// ! je comprend pas cette méthode en quoi une garde est illegale ( àquel moment une garde peut être illégale) ?
	}
}
