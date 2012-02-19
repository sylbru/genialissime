package fr.um2.projetl3.tarotandroid.jeu;


public class Annonces 
{
	/**
	 * @author JB
	 * 
	 *   phase d'annonce pas encore fini
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
		Joueur joueurQuiVaPrendre = null;
		// ! devra etre initialisé au nombre de joeur dans la partie j'ai était obligé d'initialiser à null pour pas avoir d'erreur
		
		
		while(conditionArret)
		{
			
			contrat = Partie.getJoueur(numeroDuJoueur).demanderAnnonce();  // demande au joueur quel contrat il veut faire
			
			// ! ici il faut faire en sorte que le contrat que choisit le preneur soit superieur au contrat pris par un prédécesseur
			
			tableauDesContrat[numeroDuJoueur] = contrat ; // on stock les contrat que les joueur veulent faire
			
			if(contrat == Contrat.PASSE) // si un joeur passe on le prend en compte dans un compteur utile plus loin.
			{
				compteurPourToutLeMondePasse++;
			}
			
			/**
			 * 		 
			si passe, petite, garde ou garde sans  on continue à demander
				sauf si on est arriver au dernier joeur là cas particulier
				
				
			
			/**/
			if (compteurPourToutLeMondePasse == nombreDeJoueur) // dans ce cas là ça veux dire que tout le monde à passer
			{
				contrat = Contrat.AUCUN;
				conditionArret = false ;
			}
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
