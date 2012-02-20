package fr.um2.projetl3.tarotandroid.jeu;

import java.util.Arrays;
import java.util.Vector;

import fr.um2.projetl3.tarotandroid.clients.Joueur;

public class Donne
{
	private static Carte donneAvant[];
	private static Main mainsDesJoueurs[];
	private static Carte chien[];
	private static Contrat contratEnCours;
	private static Joueur preneur;
	private static Carte plisEnCours[] = new Carte[4]; // TODO: Définir la taille ailleurs (3, 4 ou 5 joueurs)
	private static Carte plisPrecedent[] = new Carte[4]; // idem
	private static int numJoueurEntame;
	private static Vector<Carte> plisAttaque;
	private static Vector<Carte> plisDefense;
	private static int numJoueurPremier; // premier à distribuer
	
	/**
	 * @author JB
	 * 
	 * methode de distribution des cartes
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
	 public static void distribution()
	 {
		 int nombreDeJoueurs = Partie.getNombreDeJoueurs();
		 int numeroDuJoueur = 0; // ! j'en ai besoin pour savoir � quel joueur je vais donner les cartes
		 
		 int possibilitesMisesAuChien = 0;		 
		 int nombreDeCartesMisesAuChien = 0;
		 int nombreDeCartesPourLeChien = Partie.getnombreDeCartesPourLeChien();
		
		 donneAvant = new Carte[Constantes.NOMBRE_CARTES_TOTALES-1]; // ??? Tableau de 77 cartes ?
		 mainsDesJoueurs = new Main[nombreDeJoueurs];
		 chien = new Carte[nombreDeCartesPourLeChien];
		 /*
		  * à voir pour la donne précedente les cartes seront distribué par rapport à l'indice j du tableau de la donne précedente
		  */
		 
 
		 int randomMin = 1;
		 int randomMax;
		 // random(Min/Max) permette de savoir sur quel intervalle on doit faire le random
		 
		 int j=0,l,k=0;
		 		 
		 possibilitesMisesAuChien = (( Constantes.NOMBRE_CARTES_TOTALES - nombreDeCartesPourLeChien ) / Constantes.CARTES_DISTRIBU_PAR_JOUEUR) ;
		 
		 
		 while(( nombreDeCartesPourLeChien - nombreDeCartesMisesAuChien ) == 0) 
		 {
			 randomMax = possibilitesMisesAuChien - ( nombreDeCartesPourLeChien - nombreDeCartesMisesAuChien );
			 
			 randomMin = randomMin + (int)(Math.random() * ((randomMax - randomMin)+1));
			 // ! il faut que la valeur de retour soit comprise entre ]randomMin,randomMax] !
			 
			 //nombreDeCartesMisesAuChien = (int) Math.random()*100 % 3; 
			 
			 while(j<=(randomMin*Constantes.CARTES_DISTRIBU_PAR_JOUEUR))
			 {
				 if (numeroDuJoueur == nombreDeJoueurs)
				 {
					 numeroDuJoueur = 0;
				 }
				 
				 mainsDesJoueurs[numeroDuJoueur].addCarte(donneAvant[j++]);
				 mainsDesJoueurs[numeroDuJoueur].addCarte(donneAvant[j++]);
				 mainsDesJoueurs[numeroDuJoueur].addCarte(donneAvant[j++]);				 
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
		 while(j<Constantes.NOMBRE_CARTES_TOTALES-1)
		 {
			 if (numeroDuJoueur == nombreDeJoueurs) 
			 {
				 numeroDuJoueur = 0;	
			 }
			 
			 mainsDesJoueurs[numeroDuJoueur].addCarte(donneAvant[j++]);
			 mainsDesJoueurs[numeroDuJoueur].addCarte(donneAvant[j++]);
			 mainsDesJoueurs[numeroDuJoueur].addCarte(donneAvant[j++]);	
			 // l'incrementation du j doit se faire avant
			 
			 numeroDuJoueur++;
		 }
		 
		 // affectation des mains aux joueurs
	 }

	 /**
	  * 
	  * @author JB
	  * @author hhachiche
	  * 	Probleme au niveau de la ligne 134
	  * 	je ne sais pas d'où vien le prb quelqu'un peux m'aider ?	
	  * @param tableauContenantLePlis
	  * @return l'indice du tableau ou se trouve la carte qui remporte le plis grâce à ça on peut retrouver qui remporte le plis
	  */
	 /*
	 public int vainqueurDuPlis(Carte[] tableauContenantLePlis)
	 {
		int indice = -1;
		Carte max;
		int nombreDeJoueur = Partie.getNombreDeJoueurs();
		
		for(i=0;i < nombreDeJoueur;i++)					//A chaque pli on commence par regarder s'il y a des atouts,si oui on prend la plus forte
		{
			if(tableauContenantLePlis[i].isAtout())
			{
				
// ! Prb ici				if((((CarteAtout)max).getNum())) < (((CarteAtout)tableauContenantLePlis[i]).getNum())
				{
					max = tableauContenantLePlis[i];
					indice = i;
				}
				
				
			}
		}
		
		if (indice != -1)// si on as trouver un atout, on retourne l'indice
		{
			return indice;
		}
		else
		{
			Couleur couleurDemander = null;
			
			if(tableauContenantLePlis[1].isExcuse())
			{			
				couleurDemander = ((CarteCouleur)tableauContenantLePlis[2]).getCouleur();
			}
			else // if(! tableauContenantLePlis[2].isAtout())
			{
				couleurDemander = ((CarteCouleur)tableauContenantLePlis[1]).getCouleur();
			}
			
			for(i=0;i < nombreDeJoueur;i++)					
			{
				if(tableauContenantLePlis[i].isCarteCouleur())
				{
					if((CarteCouleur)tableauContenantLePlis[1].getCouleur() == CouleurDemander)
					{
						if(((CarteCouleur)max.getOrdre()) < ((CarteCouleur)tableauContenantLePlis[i].getOrdre()))
						{	
							max = tableauContenantLePlis[i];
							indice = i;
						}
							
					}
					
				}
			}
		// ici il faut faire en sorte de renvoyer l'indice de latout le plus fort sinon la carte la plus forte de mla couleur demander

			return indice;
		}
<<<<<<< .mine
	 }
	*/
	 // fin de la fonction vianqueur du plis


	public int getNumJoueurApres(int numJoueur)
	{
		return (numJoueur+1)%Partie.getNombreDeJoueurs();
	}

	public void jeuDeLaCarte()
	{
		numJoueurEntame = getNumJoueurApres(numJoueurPremier); // le premier à jouer (celui qui est après le donneur)
		int nbCartesPosees; // cartes posées dans le tour (de 1 à 4, si 4 joueurs)
		int numJoueur;
		int numJoueurVainqueurPli;
		
		while (!donneFinie()) // un tour de jeu, on commence à numJoueur = numJoueurEntame
		{
			numJoueur = numJoueurEntame;
			nbCartesPosees = 0;
			
			while (nbCartesPosees < Partie.getNombreDeJoueurs())
			{
				demanderCarteJoueur(numJoueur);
				nbCartesPosees++;
				numJoueur = getNumJoueurApres(numJoueur);
			}
			// nbCartesPosees == nbJoueurs : le tour est fini
			numJoueurVainqueurPli = 0;//vainqueurDuPlis(plisEnCours); // vainqueur du plis provoque une erreur donc je commente pour pas qu'elle se repercute iciS
			
			if(isJoueurAttaque(numJoueurVainqueurPli))
			{
				plisAttaque.addAll(Arrays.asList(plisEnCours));
			}
			else
			{
				plisDefense.addAll(Arrays.asList(plisEnCours));
			}
			numJoueurEntame = numJoueurVainqueurPli;
		}
	}
	
	/**
	 * @author niavlys
	 * @return true si le joueur passé en paramètre est en attaque
	 * @param num numéro du joueur concerné
	 */
	public boolean isJoueurAttaque(int num)
	{
		return num == preneur.getID(); // ? est-ce que getID() correspond bien à la position/au numéro ?
	}
	
	/**
	 * @author niavlys
	 * @return true si le joueur passé en paramètre est en attaque
	 * @param num numéro du joueur concerné
	 */
	public boolean isJoueurDefense(int num)
	{
		return !isJoueurAttaque(num);
	}
	
	public boolean isCarteLegale(Carte c)
	{
		// regarder pliEnCours et numJoueurEntame
		return false;
	}
	
	/**
	 * Demande au joueur de jouer une carte et vérifie si elle est légale. 
	 * @param num La position du joueur
	 */
	public void demanderCarteJoueur(int num)
	{
		// vérifier que Joueur j est dans Partie.getJoueurs() ?
		Carte carteProposee;
		do
			carteProposee = Partie.getJoueur(num).demanderCarte();
		while (mainsDesJoueurs[num].contains(carteProposee) // getID, c’est la bonne méthode ?
			&& isCarteLegale(carteProposee));
		// je pense que ce serait mieux d’avoir les mains dans les joueurs, et d’y accéder par J.getMain()
	}
	
	public boolean donneFinie()
	{
		return plisAttaque.size() + plisDefense.size() == Constantes.NOMBRE_CARTES_TOTALES;
	}
	
	/**
	 * 
	 * FIXME: fonctionne pas pour l’instant, peut-être qu’il faut songer à utiliser un Vector pour tas[],
	 * ce serait plus simple. Mais dans ce cas ce serait bien de vérifier qu’il dépasse pas 78 cartes.
	 */
	public static void reformerTas()
	{
		Carte[] nouveauTas = new Carte[78];
		Carte[] arrayPlisAttaque = new Carte[78];
		plisAttaque.toArray(arrayPlisAttaque);
		Carte[] arrayPlisDefense = new Carte[78];
		plisDefense.toArray(arrayPlisDefense);
		
		if((int)Math.random() == 1)
		{
			// nouveauTas = Arrays.copyOf(arrayPlisAttaque, Constantes.NOMBRE_CARTES_TOTALES);
			nouveauTas = arrayPlisAttaque;
			System.arraycopy(plisAttaque, 0, nouveauTas, plisAttaque.size(), plisDefense.size());
		}
		else
		{
			// nouveauTas = Arrays.copyOf(arrayPlisDefense, arrayPlisDefense.length + arrayPlisAttaque.length);
			nouveauTas = arrayPlisDefense;
			System.arraycopy(plisDefense, 0, nouveauTas, plisDefense.size(), plisAttaque.size());
		}
		//Partie.setTas(nouveauTas);
		System.out.println(nouveauTas.length+"\n"+nouveauTas);
	}
	
	public static void main(String[] args)
	{
		/*
		plisAttaque.add(new CarteAtout(14));
		plisDefense = new Vector<Carte>();
		plisDefense.add(new CarteCouleur(Couleur.Coeur, 11));
		Donne.reformerTas();
		*/
		
	}
	public static Contrat getContratEnCours() {
		return contratEnCours;
	}
	public static void setContratEnCours(Contrat contratEnCours) {
		Donne.contratEnCours = contratEnCours;
	}
	
	public static Joueur getPreneur() {
		return preneur;
	}
	public static void setPreneur(Joueur preneur) {
		Donne.preneur = preneur;
	}
	
	public static Carte[] getPlisPrecedent() {
		return plisPrecedent;
	}
	public static void setPlisPrecedent(Carte plisPrecedent[]) {
		Donne.plisPrecedent = plisPrecedent;
	}
	
	public static Carte[] getPlisEnCours() {
		return plisEnCours;
	}
	public static void setPlisEnCours(Carte plisEnCours[]) {
		Donne.plisEnCours = plisEnCours;
	}

}
