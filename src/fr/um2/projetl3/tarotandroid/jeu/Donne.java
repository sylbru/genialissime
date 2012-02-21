package fr.um2.projetl3.tarotandroid.jeu;

import java.util.Arrays;
import java.util.Vector;

import fr.um2.projetl3.tarotandroid.clients.Joueur;
import fr.um2.projetl3.tarotandroid.clients.JoueurTexte;

public class Donne
{
	private static Carte Parti[];
	private static Main mainsDesJoueurs[];
	private static Carte chien[];
	
	private static Contrat contratEnCours;
	private static Joueur preneur;
	
	private static Carte plisEnCours[] = new Carte[Partie.getNombreDeJoueurs()];
	private static Carte plisPrecedent[] = new Carte[Partie.getNombreDeJoueurs()];
	
	private static int numJoueurEntame; // premier à jouer dans le pli
	private static Vector<Carte> plisAttaque;
	private static Vector<Carte> plisDefense;
	private static int numJoueurPremier; // celui qui distribue dans la donne (utilisé pour le premier tour)
	
	/**
	 * @author JB
	 * 
	 * methode de distribution des cartes
	 *  
	 *  // ! Chose � modifier :
	 *  			=> suivant le sens des aiguille d'une montre ou non 
	 *  	
	 */

	 public static void distribution()
	 { 
		 int nombreDeJoueurs = Partie.getNombreDeJoueurs();
		 int numeroDuJoueur = 0; // ! j'en ai besoin pour savoir à quel joueur je vais donner les cartes
		 
		 int possibilitesMisesAuChien = 0;		 
		 int nombreDeCartesMisesAuChien = 0;
		 int nombreDeCartesPourLeChien = Partie.getnombreDeCartesPourLeChien();
	
		 mainsDesJoueurs = new Main[nombreDeJoueurs];
		 for(int i=0; i<nombreDeJoueurs; i++)
			 mainsDesJoueurs[i] = new Main(18, Partie.getJoueur(i));
		 // TODO: ce 18 devrait être défini dans Constantes ou ailleurs
		 
		 
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
				 mainsDesJoueurs[numeroDuJoueur].addCarte(Partie.getCarteDansTas(j++));
				 mainsDesJoueurs[numeroDuJoueur].addCarte(Partie.getCarteDansTas(j++));
				 mainsDesJoueurs[numeroDuJoueur].addCarte(Partie.getCarteDansTas(j++));
				 numeroDuJoueur = getNumJoueurApres(numeroDuJoueur);
			 }
			 for(l=0;l<=nombreDeCartesMisesAuChien;l++)
			 {

				 chien[k]=Partie.getCarteDansTas(j);
				 Donne.reveleChien();
				 j++;
				 k++;
				 randomMin++;
			 }
			 
		}
		 while(j<Constantes.NOMBRE_CARTES_TOTALES-1)
		 {
			 mainsDesJoueurs[numeroDuJoueur].addCarte(Partie.getCarteDansTas(j++));
			 mainsDesJoueurs[numeroDuJoueur].addCarte(Partie.getCarteDansTas(j++));
			 mainsDesJoueurs[numeroDuJoueur].addCarte(Partie.getCarteDansTas(j++));	 
			 numeroDuJoueur = getNumJoueurApres(numeroDuJoueur);
		 }
		 // ! affectation des mains aux joueurs
	 }

	 /**
	  * 
	  * @author JB
	  * @author hhachiche
	  * 	
	  * @param tableauContenantLePlis
	  * @return l'indice du tableau ou se trouve la carte qui remporte le plis grâce à ça on peut retrouver qui remporte le plis
	  */
	 public int vainqueurDuPlis(Carte[] tableauContenantLePlis) 
	 {
		int indice = -1;
		int nombreDeJoueur = Partie.getNombreDeJoueurs();
		int i;
		
		CarteAtout maxAtout = new CarteAtout(0);
		for(i=0;i < nombreDeJoueur;i++)					//A chaque pli on commence par regarder s'il y a des atouts,si oui on prend la plus forte
		{
			if(tableauContenantLePlis[i].isAtout())
			{
				if((((CarteAtout)maxAtout).getNum()) < (((CarteAtout)tableauContenantLePlis[i]).getNum()))
				{
					maxAtout = (CarteAtout) tableauContenantLePlis[i];
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
			else // if(! tableauContenantLePlis[2].isAtout()) // le code est bien ecrit du coup cette verification est inutile
			{
				couleurDemander = ((CarteCouleur)tableauContenantLePlis[1]).getCouleur();
			}
			
			CarteCouleur maxCouleur = new CarteCouleur(couleurDemander, 0);
			for(i=0;i < nombreDeJoueur;i++)					
			{
				if(tableauContenantLePlis[i].isCouleur())
				{
					if(((CarteCouleur)tableauContenantLePlis[1]).getCouleur() == couleurDemander)
					{
						if(((CarteCouleur)maxCouleur).getOrdre() < ((CarteCouleur)tableauContenantLePlis[i]).getOrdre())
						{	
							maxCouleur = (CarteCouleur) tableauContenantLePlis[i];
							indice = i;
						}		
					}	
				}
			}
			return indice;
		}
	 }
	/**/
	 // fin de la fonction vianqueur du plis


	public static int getNumJoueurApres(int numJoueur)
	{
		return (numJoueur+1)%Partie.getNombreDeJoueurs();
	}

	public void jeuDeLaCarte()
	{
		numJoueurEntame = getNumJoueurApres(numJoueurPremier); // le premier à jouer (celui qui est après le donneur)
		int nbCartesPosees; // cartes posées dans le tour (de 1 à 4, si 4 joueurs)
		int numJoueur;
		int numJoueurVainqueurPli;
		
		initialisationDonne();
		
		while (!donneFinie()) // un tour de jeu, on commence à numJoueur = numJoueurEntame
		{
			numJoueur = numJoueurEntame;
			nbCartesPosees = 0;
			
			while (nbCartesPosees < Partie.getNombreDeJoueurs())
			{
				plisEnCours[nbCartesPosees] = demanderCarteJoueur(numJoueur);
				nbCartesPosees++;
				numJoueur = getNumJoueurApres(numJoueur);
			}
			// nbCartesPosees == nbJoueurs : le tour est fini
			numJoueurVainqueurPli = vainqueurDuPlis(plisEnCours);
			
			if(isJoueurAttaque(numJoueurVainqueurPli))
			{
				plisAttaque.addAll(Arrays.asList(plisEnCours));
			}
			else
			{
				plisDefense.addAll(Arrays.asList(plisEnCours));
			}
			
			// transfert de pliEnCours dans pliPrecedent 
			for(int i=0; i<Partie.getNombreDeJoueurs(); i++)
			{
				plisPrecedent[i] = plisEnCours[i];
				plisEnCours[i] = null;
			}
			
			numJoueurEntame = numJoueurVainqueurPli; // celui qui a gagné le pli entame au tour suivant
		}
	}
	
	private void initialisationDonne()
	{
		plisDefense = new Vector<Carte>();
		plisAttaque = new Vector<Carte>();
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
	 * @return true si le joueur passé en paramètre est en défense
	 * @param num numéro du joueur concerné
	 */
	public boolean isJoueurDefense(int num)
	{
		return !isJoueurAttaque(num); // suffit sinon de faire return num != preneur.getID();
	}
	
	/**
	 * @author niavlys
	 * @param c une carte
	 * @param numJ un joueur
	 * @return true si la carte posée par le joueur (paramètres) est légale 
	 */
	public boolean isCarteLegale(Carte c, int numJ)
	{
		if(numJ == numJoueurEntame || numJ == getNumJoueurApres(numJoueurEntame) && plisEnCours[numJoueurEntame].isExcuse())
			return true; // si le joueur jouee en premier ou s’il joue après l’excuse
		else if(c.isExcuse())
			return true; // s’il joue l’excuse 
		// ! il ya un cas execptionnel ou il ne peut pas jouer l'excuse si autoriser3boutsDans1pli est à false et qu'il y a déja deux bout sur la table :)
		else if (c.isAtout())
		{
			// on vérifie que l’atout est plus haut que les autres.
			
			// (calcul de l’atout le plus haut dans le pli en cours)
			CarteAtout a = new CarteAtout(0);
			for(int i=numJoueurEntame; i<numJ; i=getNumJoueurApres(i))
				if (plisEnCours[i].isAtout() && ((CarteAtout)plisEnCours[i]).getNum() > a.getNum())
					a = (CarteAtout)plisEnCours[i];
			
			if (((CarteAtout)c).getNum() > a.getNum())
			{
				// l’atout est plus haut que les autres, reste à voir si
				// il est autorisé en fonction de ce qui est demandé
				if ((plisEnCours[numJoueurEntame].isExcuse() && plisEnCours[getNumJoueurApres(numJoueurEntame)].isAtout())
				  || plisEnCours[numJoueurEntame].isAtout())
					return true; // si la 1re carte est Atout, ou bien Excuse et la deuxième est Atout
				else // Reste cas où 1re carte est Couleur, ou bien Excuse et la 2e est Couleur
				{
					Couleur coulDemandee;
					if(plisEnCours[numJoueurEntame].isExcuse())
					{
						coulDemandee = ((CarteCouleur)plisEnCours[getNumJoueurApres(numJoueurEntame)]).getCouleur();
					}
					else
					{
						coulDemandee = ((CarteCouleur)plisEnCours[numJoueurEntame]).getCouleur();
					}
					 // il faut que le joueur ne possède pas la couleur demandée pour pouvoir jouer atout :
					return !mainsDesJoueurs[numJ].possedeCouleur(coulDemandee);					
				}
			} else return false;
		}
		else // c.isCouleur() == true 
		{
			Couleur coulDemandee;
			if(plisEnCours[numJoueurEntame].isExcuse())
			{
				coulDemandee = ((CarteCouleur)plisEnCours[getNumJoueurApres(numJoueurEntame)]).getCouleur();
			}
			else
			{
				coulDemandee = ((CarteCouleur)plisEnCours[numJoueurEntame]).getCouleur();
			}
			return (coulDemandee == ((CarteCouleur)c).getCouleur())
					|| !mainsDesJoueurs[numJ].possedeCouleur(coulDemandee) && !mainsDesJoueurs[numJ].possedeAtout();
		}
	}
	
	/**
	 * Demande au joueur de jouer une carte et vérifie si elle est légale. 
	 * @param num La position du joueur
	 * 
	 */
	public Carte demanderCarteJoueur(int num)
	{
		// vérifier que Joueur j est dans Partie.getJoueurs() ?
		Carte carteProposee;
		do
			carteProposee = Partie.getJoueur(num).demanderCarte();
		while (mainsDesJoueurs[num].contains(carteProposee) // getID, c’est la bonne méthode ?
			&& isCarteLegale(carteProposee, num));
		return carteProposee;
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
		// Donne donne = new Donne(); // bon c’est le bordel entre les méthodes statiques et les non-statiques,
									// faudra en discuter.
		Partie.lancerPartie4JoueursTexte();
		
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

	public static void mettreChienDansLesPlisDeLAttaque()
	{
		 plisAttaque.addAll(Arrays.asList(chien));
	}
	public static void mettreChienDansLesPlisDeLaDefense()
	{
		 plisDefense.addAll(Arrays.asList(chien));
	}
	public static void mettreChienDansLaMainDuPreneur()
	{
		 preneur.addChienDansMain(chien);
	}
	
	public static void reveleChien()
	{
		for(Carte c:chien)
		{
			c.affiche();
		}
	}
	
	/**
	 * Pour initialiser les tableaux. On peut pas le mettre en statique parce que
	 * ça dépend du nombre de joueurs, donc ça doit être fait une fois qu’il est défini.
	 */
	public static void init()
	{
		mainsDesJoueurs = new Main[Partie.getNombreDeJoueurs()];
	}
	
}
