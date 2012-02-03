package fr.um2.projetl3.tarotandroid.jeu;


public class Annonces 
{
	public annonce4joueurs()
	{
	
		Contrat con = new Contrat(NomDesContrats.AucuneGarde);
		Contrat controle = new Contrat();
		for(int i; i<8 ; i++ )
		{
			controle = demandejouer(con,i%4);
			if	(controleContrats(con,controle))
			{
				if (controle=="AucuneGarde"){
					informejoueurs(con,controle);
				}
				con=controle;
				informejouers(con);
			}
			else
			{
				gardeillegale(i%4);
			}
		}
	}
	
	
	public boolean controleContrats(Contrat a,Contrat b)
	{
		if(b.getPoids()==0)
		{
			return true;
		}
		else if(a.getPoids()==5) return false;//?ce cas ne devrait jamais arriver on arrete de demander les jouers une fois le plus grand contract fait
		else if(agetPoids()<bgetPoids()) return true;
		else return false;
		
	}

	
	public void informejoueurs(){
		//TO-DO informe tous les joueurs si le jouer n'a pas pris de contrats ou si le contrats a augmente
	}
	
	public void gardeillegale(int i){
		//TO-DO informe le jouer i que sa garde est illegale
	}
}
