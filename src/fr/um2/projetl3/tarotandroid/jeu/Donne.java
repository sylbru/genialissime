package fr.um2.projetl3.tarotandroid.jeu;

import java.util.Arrays;
import java.util.Map;
import java.util.Vector;

import fr.um2.projetl3.tarotandroid.clients.IJoueur;

public class Donne
{
	private Main mainsDesJoueurs[];
	private Carte chien[];
	private Partie P; // la partie à laquelle appartient cette donne
	
	private Contrat contratEnCours;
	private int preneur;
	private int appelee;// LE joueur appele dans le mode a 5 joueurs
	private Carte plisEnCours[];
	private Carte plisPrecedent[];
	
	private int numJoueurEntame; // premier à jouer dans le pli
	// protected pour calculer les pts des plis Attaque/Defense
	protected Vector<Carte> plisAttaque; 
	protected Vector<Carte> plisDefense;
	private int numDonneur; // celui qui distribue dans la donne (utilisé pour le premier tour)
	
	private int numJoueurEnContact; // le joueur avec lequel on est en communication (utilisé pour savoir de qui on parle quand un joueur demande « sa » main)
	
	/*
	 * --------------------------------------------------------------------------------------------
	 * -----------------------------------Distribution--------------------------------------------
	 * --------------------------------------------------------------------------------------------
	 */
	/**
	 * @author JB
	 * methode de distribution des cartes
	 * 
	 *  // TODO test + :
	 *  // ! Chose � modifier :
	 *  			=> suivant le sens des aiguille d'une montre ou non 	
	 */

	 protected void distribution()
	 { 
		 incrementerNumDonneur();
		 int nombreDeJoueurs = P.getNombreDeJoueurs();
		 int numeroDuJoueur = getNumJoueurApres(numDonneur);
		 System.out.println(numeroDuJoueur);
		 
		 int possibilitesMisesAuChien = 0;		 
		 int nombreDeCartesMisesAuChien = 0;
		 int nombreDeCartesPourLeChien = P.getnombreDeCartesPourLeChien();
	
		 mainsDesJoueurs = new Main[nombreDeJoueurs];
		 for(int i=0; i<nombreDeJoueurs; i++)
			 mainsDesJoueurs[i] = new Main(P.getJoueur(i));
		 
		 chien = new Carte[nombreDeCartesPourLeChien];

		 int randomMin = 1;
		 int randomMax;
		 // random(Min/Max) permette de savoir sur quel intervalle on doit faire le random
		 
		 int j=0,k=0, random;
		 	 
		 possibilitesMisesAuChien = (( Constantes.NOMBRE_CARTES_TOTALES - nombreDeCartesPourLeChien ) / Constantes.CARTES_DISTRIBU_PAR_JOUEUR) ;
		 
		 //je pense que ici sa doit etre !=0 
		 while(( nombreDeCartesPourLeChien - nombreDeCartesMisesAuChien ) != 0) 
		 {
			 randomMax = possibilitesMisesAuChien - ( nombreDeCartesPourLeChien - nombreDeCartesMisesAuChien );
			 
			 random = randomMin + (int)(Math.random() * ((randomMax - randomMin)+1));
			 // ! il faut que la valeur de retour soit comprise entre ]randomMin,randomMax] !
			 System.out.println("random = "+random+" (entre "+randomMin+" et "+randomMax+")");
			 //nombreDeCartesMisesAuChien = (int) Math.random()*100 % 3; 
			 while(j<=(random*Constantes.CARTES_DISTRIBU_PAR_JOUEUR))
			 {
				 System.out.print("Ajoute à joueur numéro "+numeroDuJoueur+" ");
				 System.out.print(P.getCarteDansTas(j).toString()+", ");
				 mainsDesJoueurs[numeroDuJoueur].addCarte(P.getCarteDansTas(j++));
				 
				 System.out.print(P.getCarteDansTas(j).toString()+", ");
				 mainsDesJoueurs[numeroDuJoueur].addCarte(P.getCarteDansTas(j++));
				 
				 System.out.print(P.getCarteDansTas(j).toString()+".");
				 mainsDesJoueurs[numeroDuJoueur].addCarte(P.getCarteDansTas(j++));
				 
				 numeroDuJoueur = getNumJoueurApres(numeroDuJoueur);
				 System.out.println();
			 }
			 	 System.out.println("Ajout au chien de "+P.getCarteDansTas(j)+" (k="+k+")");
				 chien[k]=P.getCarteDansTas(j);
				 nombreDeCartesMisesAuChien++;
				 //System.out.println(chien[k].toString());
				 j++;
				 k++;
				 randomMin = random+1;
		}
		 while(j<Constantes.NOMBRE_CARTES_TOTALES-1)
		 {
			 System.out.print("Ajoute à joueur "+numeroDuJoueur+" ");
			 System.out.print(P.getCarteDansTas(j).toString()+", ");
			 mainsDesJoueurs[numeroDuJoueur].addCarte(P.getCarteDansTas(j++));
			 System.out.print(P.getCarteDansTas(j).toString()+", ");
			 mainsDesJoueurs[numeroDuJoueur].addCarte(P.getCarteDansTas(j++));
			 System.out.print(P.getCarteDansTas(j).toString()+".");
			 mainsDesJoueurs[numeroDuJoueur].addCarte(P.getCarteDansTas(j++));	 
			 numeroDuJoueur = getNumJoueurApres(numeroDuJoueur);
			 System.out.println();
		 }
		 for(int i = 0 ; i < nombreDeJoueurs ; i++)
		 {
			 P.getJoueur(i).setMain(mainsDesJoueurs[numeroDuJoueur]);
		 }
		 for(int i=0; i<4; i++)
		 {
			 mainsDesJoueurs[i].affiche();
		 }
		 reveleChien();
		 
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
	  * @param tableauContenantLePli
	  * @return l'indice du tableau ou se trouve la carte qui remporte le plis grâce à ça on peut retrouver qui remporte le plis
	  */
	 public int vainqueurDuPli(Carte[] tableauContenantLePli) 
	 {
		int indice = -1;
		int nombreDeJoueur = P.getNombreDeJoueurs();
		int i;
		
		Carte maxAtout = new Carte(0);
		for(i=0;i < nombreDeJoueur;i++)					//A chaque pli on commence par regarder s'il y a des atouts,si oui on prend la plus forte
		{
			if(tableauContenantLePli[i].isAtout())
			{
				if((maxAtout.getOrdre()) < (tableauContenantLePli[i].getOrdre()))
				{
					maxAtout = tableauContenantLePli[i];
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
			
			if(tableauContenantLePli[0].isExcuse())
			{			
				couleurDemander = tableauContenantLePli[1].getCouleur();
			}
			else // if(! tableauContenantLePlis[2].isAtout()) // le code est bien ecrit du coup cette verification est inutile
			{
				couleurDemander = tableauContenantLePli[0].getCouleur();
			}
			
			Carte maxCouleur = new Carte(couleurDemander, 0);
			for(i=0;i < nombreDeJoueur;i++)					
			{
				if(tableauContenantLePli[i].isCouleur())
				{
					if(tableauContenantLePli[i].getCouleur() == couleurDemander)
					{
						if(maxCouleur.getOrdre() < tableauContenantLePli[i].getOrdre())
						{	
							maxCouleur = tableauContenantLePli[i];
							indice = i;
						}		
					}	
				}
			}
			return indice;
		}
	 }
	 
	 

	 /**
	  * Phase de jeu des cartes dans une donne, après les annonces et l’écart, avant le comptage des points.
	  * (pour info, l’expression « jeu de la carte », ça vient pas de moi,
	  * voir http://www.fftarot.fr/index.php/Decouvrir/Le-Jeu-de-la-carte.html )
	  */
	protected void jeuDeLaCarte()
	{
		numJoueurEntame = getNumJoueurApres(numDonneur); // le premier à jouer (celui qui est après le donneur)
		int nbCartesPosees; // cartes posées dans le tour (de 1 à 4, si 4 joueurs)
		int numJoueur;
		int numJoueurVainqueurPli;
		
		while (!donneFinie()) // un tour de jeu, on commence à numJoueur = numJoueurEntame
		{
			
			numJoueur = numJoueurEntame;
			nbCartesPosees = 0;
			
			while (nbCartesPosees < P.getNombreDeJoueurs())
			{
				plisEnCours[numJoueur] = demanderCarteJoueur(numJoueur); //changement fabrice : jai suppose que l'orde de cartes n'estpas important
				nbCartesPosees++;
				numJoueur = getNumJoueurApres(numJoueur);
			}
			// nbCartesPosees == nbJoueurs : le tour est fini
			numJoueurVainqueurPli = vainqueurDuPli(plisEnCours); 
			System.out.println("vainqeur du pli !! :"+numJoueurVainqueurPli);
			
			if(isJoueurAttaque(numJoueurVainqueurPli)) // isPreneur ne permet pas de faire ça ?
													// Non, on peut être dans l’attaque (appelé) sans être preneur
			{
				plisAttaque.addAll(Arrays.asList(plisEnCours));
			}
			else
			{
				plisDefense.addAll(Arrays.asList(plisEnCours));
			}
			
			// transfert de pliEnCours dans pliPrecedent 
			for(int i=0; i<P.getNombreDeJoueurs(); i++)
			{
				int a = (i + numJoueurEntame)% P.getNombreDeJoueurs();
				plisPrecedent[i] = plisEnCours[a];
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
			Carte a = new Carte(0);
			for(int i=numJoueurEntame; i!=numJ; i=getNumJoueurApres(i))
			{
				if (plisEnCours[i].isAtout() && plisEnCours[i].getOrdre() > a.getOrdre())
				{
					a = plisEnCours[i];
				}
			}
			
			if (c.getOrdre() > a.getOrdre())
			{
				// l’atout est plus haut que les autres, reste à voir si il est autorisé en fonction de ce qui est demandé
				if ((plisEnCours[numJoueurEntame].isExcuse() && plisEnCours[getNumJoueurApres(numJoueurEntame)].isAtout()) || plisEnCours[numJoueurEntame].isAtout())
				{
					return true; // si la 1re carte est Atout, ou bien Excuse puis Atout, c’est donc Atout demandé donc ok
				}
				else // Reste cas où 1re carte est Couleur, ou bien Excuse et la 2e est Couleur
				{
					Couleur coulDemandee;
					if(plisEnCours[numJoueurEntame].isExcuse())
					{
						coulDemandee = plisEnCours[getNumJoueurApres(numJoueurEntame)].getCouleur();
					}
					else
					{
						coulDemandee = plisEnCours[numJoueurEntame].getCouleur();
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
				coulDemandee = plisEnCours[getNumJoueurApres(numJoueurEntame)].getCouleur();
			}
			else
			{
				coulDemandee = plisEnCours[numJoueurEntame].getCouleur();
			}
			return (coulDemandee == c.getCouleur()) || !mainsDesJoueurs[numJ].possedeCouleur(coulDemandee) && !mainsDesJoueurs[numJ].possedeAtout();
		}
		
	}
	
	/**
	 * Demande au joueur de jouer une carte et vérifie si elle est légale (redemande si besoin). 
	 * @param num La position du joueur
	 */
	protected Carte demanderCarteJoueur(int num) 
	{
		Carte carteProposee;
		do
		{
			carteProposee = P.getJoueur(num).demanderCarte();
			/*
			 * test des condition de la boucle 
			 * if(mainsDesJoueurs[num].contains(carteProposee)) System.out.println("contains !!!");
			 * if(isCarteLegale(carteProposee, num)) System.out.println("cartelegale");
			 */
		}
		while(!(mainsDesJoueurs[num].contains(carteProposee)&& isCarteLegale(carteProposee, num)));
		mainsDesJoueurs[num].removeCarte(carteProposee);
		
		return carteProposee;
	}
	
	/**
	 * 
	 * @param numJoueur
	 * @return un vecteur contenant les Cartes possibles (légales) à jouer pour le joueur numJoueur
	 * Actuellement ça regarde toutes ses cartes et fait appel à isCarteLegale() pour chacune.
	 * Est-ce que ce serait plus efficace de procéder plus intelligemment ? À voir.
	 */
	protected Vector<Carte> indiquerCartesLegalesJoueur(int numJoueur)
	{
		Vector<Carte> cartesLegales = new Vector<Carte>();
		if(numJoueur < P.getNombreDeJoueurs());
		for(Carte c: mainsDesJoueurs[numJoueur].getCartes())
		{
			if(isCarteLegale(c, numJoueur))
			{
				cartesLegales.add(c);
			}
		}
		
		return cartesLegales;
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
	protected void reformerTas()
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
		//P.setTas(nouveauTas);
		System.out.println(nouveauTas.length+"\n"+nouveauTas);
	}
	
	/*
	 * ---------------------------------------------------------------------------------------------------
	 * -------------------------------------- accesseur --------------------------------------------------
	 * ---------------------------------------------------------------------------------------------------
	 */
	
	/**
	 * @author niavlys
	 * @return mainsDesJoueurs, le tableau des mains des joueurs.
	 */
	protected Main[] getMains()
	{
		return mainsDesJoueurs;
	}
	
	/**
	 * @author niavlys
	 * @param j
	 * @return la main du joueur j
	 */
	protected Main getMain(int numJoueur)
	{
		return mainsDesJoueurs[numJoueur];
	}
	
	/**
	 * @author niavlys
	 * Utilisé par un client (IJoueur) pour demander sa main.
	 *  
	 * Renommer en getMaMain() ?
	 * 
	 * @return la main du joueur de numéro numJoueurEnContact
	 */
	public Main getMain()
	{
		if(numJoueurEnContact >= 0 && numJoueurEnContact < P.getNombreDeJoueurs())
		{
			return mainsDesJoueurs[numJoueurEnContact];
		}
		else
		{
			return null;
		}
	}
	
	public boolean isJoueurAttaque(int num)
	{
		return num == preneur || (P.getNombreDeJoueurs() == 5 && num == appelee);
		// ? est-ce que getID() correspond bien à la position/au numéro ?
	}
	
	public boolean isJoueurDefense(int num)
	{
		return !isJoueurAttaque(num); 
	}
	
	public Contrat getContratEnCours() {
		return contratEnCours;
	}
	public void setContratEnCours(Contrat contratEnCours) {
		this.contratEnCours = contratEnCours;
	}
	
	/**
	 * @author niavlys
	 * @param numJoueur un joueur
	 * @return Le numéro du joueur se trouvant après le joueur désigné en paramètre
	 */
	public int getNumJoueurApres(int numJoueur)
	{
		int nouvNum;
		if(PrefsRegles.sensInverseAiguillesMontre)
		{
			nouvNum = numJoueur+1;
		}
		else
		{
			nouvNum = numJoueur-1;
		}

		nouvNum = nouvNum % P.getNombreDeJoueurs();
		numJoueurEnContact = nouvNum;
		return 	nouvNum;
	}

	public int getNumDonneur()
	{
		return numDonneur;
	}
	
	/**
	 * @author niavlys
	 * Sert à incrémenter numDonneur pour le passer au joueur suivant.
	 * Utilisé à chaque début de donne.
	 * TODO: déplacer dans Partie ?
	 */
	public void incrementerNumDonneur()
	{
		numDonneur = getNumJoueurApres(numDonneur);
	}
	
	public int getPreneur() {
		return preneur;
	}
	public void setPreneur(int preneur) {
		this.preneur = preneur;
	}
	// pour les parties a 5
	public int getJoueurAppele() {
		return appelee;
	}
	public void setJoueurAppele(int joueurAppele) {
		this.appelee = joueurAppele;
	}
	
	public Carte[] getPlisPrecedent() {
		return plisPrecedent;
	}
	public void setPlisPrecedent(Carte plisPrecedent[]) {
		this.plisPrecedent = plisPrecedent;
	}
	
	public Carte[] getPlisEnCours() {
		return plisEnCours;
	}
	public void setPlisEnCours(Carte plisEnCours[]) {
		this.plisEnCours = plisEnCours;
	}

	public void mettreChienDansLesPlisDeLAttaque()
	{
		 plisAttaque.addAll(Arrays.asList(chien));
	}
	public void mettreChienDansLesPlisDeLaDefense()
	{
		 plisDefense.addAll(Arrays.asList(chien));
	}
	public void mettreChienDansLaMainDuPreneur()
	{
		 P.getJoueur(preneur).addChienDansMain(chien);
	}
	
	/**
	 * Informe les joueurs du chien révélé
	 */
	public void reveleChien()
	{
		for(IJoueur j: P.getJoueurs())
		{
			j.direChien(chien);
		}
	}
	
	/**
	 * @author niavlys
	 * Informe les joueurs du fait qu’une carte a été jouée par un joueur
	 * @param c La carte jouée
	 * @param joueur Le joueur qui a joué la carte
	 */
	public void direJoueursCarteJouee(Carte c, IJoueur joueur)
	{
		for(IJoueur j: P.getJoueurs())
		{
			j.direCarteJouee(c, joueur);
		}
	}
	
	/*
	 * ------------------------------------------------------------------------------------------
	 * -------------------------------Initialisations---------------------------------------------
	 * -------------------------------------------------------------------------------------------
	 */
	public void init()
	{
		mainsDesJoueurs = new Main[P.getNombreDeJoueurs()];
		plisEnCours = new Carte[P.getNombreDeJoueurs()];
		plisEnCours = new Carte[P.getNombreDeJoueurs()];
		plisDefense = new Vector<Carte>();
		plisAttaque = new Vector<Carte>();
	}
	
	public Donne()
	{
		P = Context.P;
		init();
	}	
	
	public Donne(Partie P)
	{
		this.P = P;
		init();
	}

	
	public static void main(String[] args)
	{/*
		 Donne donne = new Donne(); // bon c’est le bordel entre les méthodes statiques et les non-statiques,
									// faudra en discuter.
		
		 /*
		 P.lancerPartie4JoueursTexte();
		/*
		init();
		
		P.setNombreDeJoueurs(4);
		plisEnCours = new Carte[4];
		plisEnCours[0] = new Carte(13);
		plisEnCours[1] = new Carte(1);
		//plisEnCours[1] = new CarteCouleur(Couleur.Trefle, 3);
		plisEnCours[2] = new CarteCouleur(Couleur.Carreau, 10);
		plisEnCours[3] = new Carte(12);
		//plisEnCours[3] = new CarteCouleur(Couleur.Trefle, 1);
		
		System.out.println(vainqueurDuPlis(plisEnCours));
		*/
		Partie.main(null);
		
	}
}
