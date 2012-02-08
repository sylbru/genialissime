package fr.um2.projetl3.tarotandroid.jeu;
import java.lang.Math;

public class Donne
{
	private Carte donneAvant[];
	private Main mainDesJoueur[];
	private Carte chien[];
	
	
	
	/**
	 * @author JB
	 * 
	 * methode de disbrution des cartes
	 * 
	 * sauf erreur de ma part les calcules devrait �tre bon, je verifir� avec quelqu'un
	 * 
	 *  // ? je ne sais pas ou mettre les constantes
	 *  
	 *  // ! Chose � modifier :
	 *  		mettre les constantes au bon endroit
	 *  		changer la valeur 6 par Chien.getNombreDeCartes() 
	 *  		� la fin il faut affecter les mains qui sont dans le tableau aux joeur
	 *  			=> suivant le sens des aiguille d'une montre ou non 
	 *  	
	 */
	
	/**/
	 public void donne()
	 {
		 
		 int NOMBRE_CARTES_TOTALES=78; 
		 int CARTES_DISTRIBU_PAR_JOUEUR = 3;
		 
		 int nombreDeJoueur = Partie.getNombreDeJoueurs(); 
		 int numeroDuJoueur = 0; // ! j'en ai besoin pour savoir � quel joueur je vais donner les cartes
		 
		 int possibilitesMisesAuChien = 0;		 
		 int nombreDeCartesMisesAuChien = 0;
		 int nombreDeCartesPourLeChien = 6;
		
		 donneAvant = new Carte[NOMBRE_CARTES_TOTALES-1];
		 mainDesJoueur = new Main[nombreDeJoueur] ;
		 chien = new Carte[nombreDeCartesPourLeChien];
		 /*
		  * à voir pour la donne précedente les cartes seront distribué par rapport à l'indice j du tableau de la donne précedente
		  */
		 
 
		 int randomMin = 1;
		 int randomMax;
		 // random(Min/Max) permette de savoir sur quel intervalle on doit faire le random
		 
		 int j=0,l,k=0;
		 		 
		 possibilitesMisesAuChien = (( NOMBRE_CARTES_TOTALES - nombreDeCartesPourLeChien ) / CARTES_DISTRIBU_PAR_JOUEUR) ;
		 
		 
		 while(( nombreDeCartesPourLeChien - nombreDeCartesMisesAuChien ) == 0) 
		 {
			 randomMax = possibilitesMisesAuChien - ( nombreDeCartesPourLeChien - nombreDeCartesMisesAuChien );
			 
			 randomMin = randomMin + (int)(Math.random() * ((randomMax - randomMin)+1));
			 // ! il faut que la valeur de retour soit comprise entre ]randomMin,randomMax] !
			 
			 //nombreDeCartesMisesAuChien = (int) Math.random()*100 % 3; 
			 
			 while(j<=(randomMin*CARTES_DISTRIBU_PAR_JOUEUR))
			 {
				 if (numeroDuJoueur == nombreDeJoueur)
				 {
					 numeroDuJoueur = 0;
				 }
				 
				 mainDesJoueur[numeroDuJoueur].addCarte(donneAvant[j++]);
				 mainDesJoueur[numeroDuJoueur].addCarte(donneAvant[j++]);
				 mainDesJoueur[numeroDuJoueur].addCarte(donneAvant[j++]);				 
				 // l'incrementation du j doit se faire avant l'affectation au tableau
				 numeroDuJoueur++;
			 }
			 for(l=0;l<=nombreDeCartesMisesAuChien;l++)
			 {
				 chien[k]=donneAvant[j];
				 j++;
				 k++;
				 randomMin++;
			 }

			 
		}
		 while(j<NOMBRE_CARTES_TOTALES-1)
		 {
			 if (numeroDuJoueur == nombreDeJoueur) 
			 {
				 numeroDuJoueur = 0;	
			 }
			 
			 mainDesJoueur[numeroDuJoueur].addCarte(donneAvant[j++]);
			 mainDesJoueur[numeroDuJoueur].addCarte(donneAvant[j++]);
			 mainDesJoueur[numeroDuJoueur].addCarte(donneAvant[j++]);	
			 // l'incrementation du j doit se faire avant
			 
			 numeroDuJoueur++;
		 }
		 
		 // affectation des mains aux joueurs
	 }
	 /**
	
	public void donne4jouers()
	{
		for(int i=0;i<77;i++)
		{
			don[i]=i;
			 
		}
		if(premieredonne)
		{	
			premieredonne=false;
			for (int i=0; i > 77; i++) // ? il n'y a pas une invertion entre > et < ?
			{
				int randomPosition = (int)(Math.random()*100)%77;
				int temp = don[i];
				don[i] = don[randomPosition];
				don[randomPosition] = temp;
			}
		}
		int i=0,j=0;
		if(i%12==0 && i!=0)
		{
			chien[i/13]=don[i];
			i++;
		}
		else
		{
			mains[(i%13)/3][j]=don[i];
			j++;
			i++;
		}
		// int i = 0;
	}
	/**/
	public void annonces()
	{
		// TODO
	}
	
	public void jeu()
	{
		// TODO
	}
	
}
