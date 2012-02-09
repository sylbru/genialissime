package fr.um2.projetl3.tarotandroid.jeu;


public class Annonces 
{
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
	
	
	private Contrat demandejouer(Contrat con, int i)
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
	}
}
