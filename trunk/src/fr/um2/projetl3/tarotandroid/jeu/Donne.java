package fr.um2.projetl3.tarotandroid.jeu;

import java.awt.font.NumericShaper;
import java.util.Arrays;
import java.util.Vector;

import fr.um2.projetl3.tarotandroid.clients.Joueur;
import fr.um2.projetl3.tarotandroid.clients.JoueurTexte;

public class Donne
{
	private static Main mainsDesJoueurs[];
	private static Carte chien[];
	
	private static Contrat contratEnCours;
	private static Joueur preneur;
	private static Joueur appelee;// LE joueur appele dans le mode a 5 joueurs
	private static Carte plisEnCours[];
	private static Carte plisPrecedent[];
	
	private static int numJoueurEntame; // premier à jouer dans le pli
	// protected pour calculer les pts des plis Attaque/Defense
	protected static Vector<Carte> plisAttaque; 
	protected static Vector<Carte> plisDefense;
	private static int numDonneur; // celui qui distribue dans la donne (utilisé pour le premier tour)
	
	
	/*
	 * --------------------------------------------------------------------------------------------
	 * -----------------------------------Distribution--------------------------------------------
	 * --------------------------------------------------------------------------------------------
	 */
	/**
	 * @author JB
	 * 
	 * methode de distribution des cartes
	 * 
	 *  // TODO test + :
	 *  // ! Chose � modifier :
	 *  			=> suivant le sens des aiguille d'une montre ou non 	
	 */

	 public static void distribution()
	 { 
		 incrementerNumDonneur();
		 int nombreDeJoueurs = Partie.getNombreDeJoueurs();
		 int numeroDuJoueur = getNumJoueurApres(numDonneur); // ! j'en ai besoin pour savoir à quel joueur je vais donner les cartes
		 
		 int possibilitesMisesAuChien = 0;		 
		 int nombreDeCartesMisesAuChien = 0;
		 int nombreDeCartesPourLeChien = Partie.getnombreDeCartesPourLeChien();
	
		 mainsDesJoueurs = new Main[nombreDeJoueurs];
		 for(int i=0; i<nombreDeJoueurs; i++)
			 mainsDesJoueurs[i] = new Main((Constantes.NOMBRE_CARTES_TOTALES-nombreDeCartesPourLeChien)/nombreDeJoueurs, Partie.getJoueur(i));
		 
		 chien = new Carte[nombreDeCartesPourLeChien];

		 int randomMin = 1;
		 int randomMax;
		 // random(Min/Max) permette de savoir sur quel intervalle on doit faire le random
		 
		 int j=0,k=0;
		 	 
		 possibilitesMisesAuChien = (( Constantes.NOMBRE_CARTES_TOTALES - nombreDeCartesPourLeChien ) / Constantes.CARTES_DISTRIBU_PAR_JOUEUR) ;
		 
		 //je pense que ici sa doit etre !=0 
		 while(( nombreDeCartesPourLeChien - nombreDeCartesMisesAuChien ) != 0) 
		 {
			 randomMax = possibilitesMisesAuChien - ( nombreDeCartesPourLeChien - nombreDeCartesMisesAuChien );
			 
			 randomMin = randomMin + (int)(Math.random() * ((randomMax - randomMin)+1));
			 // ! il faut que la valeur de retour soit comprise entre ]randomMin,randomMax] !
			 System.out.println("randomMin = "+randomMin);
			 //nombreDeCartesMisesAuChien = (int) Math.random()*100 % 3; 
			 
			 while(j<=(randomMin*Constantes.CARTES_DISTRIBU_PAR_JOUEUR))
			 {
				 System.out.print("Ajoute à joueur "+numeroDuJoueur+" ");
				 System.out.print(Partie.getCarteDansTas(j).toString()+", ");
				 mainsDesJoueurs[numeroDuJoueur].addCarte(Partie.getCarteDansTas(j++));
				 
				 System.out.print(Partie.getCarteDansTas(j).toString()+", ");
				 mainsDesJoueurs[numeroDuJoueur].addCarte(Partie.getCarteDansTas(j++));
				 
				 System.out.print(Partie.getCarteDansTas(j).toString()+".");
				 mainsDesJoueurs[numeroDuJoueur].addCarte(Partie.getCarteDansTas(j++));
				 
				 numeroDuJoueur = getNumJoueurApres(numeroDuJoueur);
				 System.out.println();
			 }
			 for(int l=0;l<=nombreDeCartesMisesAuChien;l++)
			 {
				 System.out.println("k="+k);
				 chien[k]=Partie.getCarteDansTas(j);
				 System.out.println(chien[k].toString());
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
		 for(int i = 0 ; i < nombreDeJoueurs ; i++)
		 {
			 Partie.getJoueur(i).setMain(mainsDesJoueurs[numeroDuJoueur]);
		 } 
	}

		/*
		 * --------------------------------------------------------------------------------------------
		 * ------------------------------------ Méthodes --------------------------------------------
		 * --------------------------------------------------------------------------------------------
		 */
	 /** Méthode fini mais à tester
	  *  // TODO test
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

	 /**
	  * explication à donner ....
	  */
	public void jeuDeLaCarte()
	{
		numJoueurEntame = getNumJoueurApres(numDonneur); // le premier à jouer (celui qui est après le donneur)
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
				// ! il va faloir creer une méthode à peu près la même mais qui prend le plis en cours et dit au joueur quelles sont les cartes de sa main jouable
				nbCartesPosees++;
				numJoueur = getNumJoueurApres(numJoueur);
			}
			// nbCartesPosees == nbJoueurs : le tour est fini
			numJoueurVainqueurPli = vainqueurDuPlis(plisEnCours);
			
			
			if(isJoueurAttaque(numJoueurVainqueurPli)) // isPreneur ne permet pas de faire ça ?
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
	
	/**
	 * @author niavlys
	 * @param c une carte
	 * @param numJ un joueur
	 * @return true si la carte posée par le joueur (paramètres) est légale 
	 * 
	 *  ok mettre plus d'explication svp des méthodes 
	 */
	public boolean isCarteLegale(Carte c, int numJ) // svp des noms de variable explicite ...
	{
		if(numJ == numJoueurEntame || numJ == getNumJoueurApres(numJoueurEntame) && plisEnCours[numJoueurEntame].isExcuse())
			return true; // si le joueur jouee en premier ou s’il joue après l’excuse
		else if(c.isExcuse())
			return true; // s’il joue l’excuse 
		// ! il ya un cas execptionnel ou il ne peut pas jouer l'excuse si autoriser3boutsDans1pli est à false et qu'il y a déja deux bout sur la table :)
		// ! facile à implementer mais à faire au cas ou
		else if (c.isAtout())
		{
			// on vérifie que l’atout est plus haut que les autres.
			
			// (calcul de l’atout le plus haut dans le pli en cours)
			CarteAtout a = new CarteAtout(0);
			for(int i=numJoueurEntame; i<numJ; i=getNumJoueurApres(i))
			{
				if (plisEnCours[i].isAtout() && ((CarteAtout)plisEnCours[i]).getNum() > a.getNum())
				{
					a = (CarteAtout)plisEnCours[i];
				}
			}
			
			if (((CarteAtout)c).getNum() > a.getNum())
			{
				// l’atout est plus haut que les autres, reste à voir si il est autorisé en fonction de ce qui est demandé
				if ((plisEnCours[numJoueurEntame].isExcuse() && plisEnCours[getNumJoueurApres(numJoueurEntame)].isAtout()) || plisEnCours[numJoueurEntame].isAtout())
				{
					return true; // si la 1re carte est Atout, ou bien Excuse et la deuxième est Atout
				}
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
			return (coulDemandee == ((CarteCouleur)c).getCouleur()) || !mainsDesJoueurs[numJ].possedeCouleur(coulDemandee) && !mainsDesJoueurs[numJ].possedeAtout();
		}
	}
	
	/**
	 * Demande au joueur de jouer une carte et vérifie si elle est légale. 
	 * @param num La position du joueur

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
	/*
	 * ---------------------------------------------------------------------------------------------------
	 * -------------------------------------- accesseur --------------------------------------------------
	 * ---------------------------------------------------------------------------------------------------
	 */
	
	public boolean isJoueurAttaque(int num)
	{
		if(Partie.getNombreDeJoueurs()==5)
		{
			return (num == preneur.getID() || num ==appelee.getID()); // ? est-ce que getID() correspond bien à la position/au numéro ? // ! oubli d'implementatinon pour le jeu à cinq ||joueurAppeler.getID
		}
		else 
			return num == preneur.getID();
	}
	
	public boolean isJoueurDefense(int num)
	{
		return !isJoueurAttaque(num); 
	}
	
	public static Contrat getContratEnCours() {
		return contratEnCours;
	}
	public static void setContratEnCours(Contrat contratEnCours) {
		Donne.contratEnCours = contratEnCours;
	}
	
	/**
	 * @author niavlys
	 * @param numJoueur un joueur
	 * @return Le numéro du joueur se trouvant après le joueur désigné en paramètre
	 * TODO: gérer le sens de rotation (à voir avec PrefsRegles)
	 */
	public static int getNumJoueurApres(int numJoueur)
	{
		return (numJoueur+1)%Partie.getNombreDeJoueurs();
	}

	public static int getNumDonneur()
	{
		return numDonneur;
	}
	
	public static void incrementerNumDonneur()
	{
		numDonneur = getNumJoueurApres(numDonneur);
	}
	
	public static Joueur getPreneur() {
		return preneur;
	}
	public static void setPreneur(Joueur preneur) {
		Donne.preneur = preneur;
	}
	// pour les parties a 5
	public static Joueur getJoueurAppele() {
		return appelee;
	}
	public static void setJoueurAppele(Joueur joueurappele) {
		Donne.appelee = joueurappele;
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
	
	/*
	 * ------------------------------------------------------------------------------------------
	 * -------------------------------Initialisations---------------------------------------------
	 * -------------------------------------------------------------------------------------------
	 */
	public static void init()
	{
		mainsDesJoueurs = new Main[Partie.getNombreDeJoueurs()];
		plisEnCours = new Carte[Partie.getNombreDeJoueurs()];
		plisEnCours = new Carte[Partie.getNombreDeJoueurs()];
	}
	
	private void initialisationDonne()
	{
		plisDefense = new Vector<Carte>();
		plisAttaque = new Vector<Carte>();
	}


	
	public static void main(String[] args)
	{
		// Donne donne = new Donne(); // bon c’est le bordel entre les méthodes statiques et les non-statiques,
									// faudra en discuter.
		Partie.lancerPartie4JoueursTexte();
		
	}
}
