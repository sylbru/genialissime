package fr.um2.projetl3.tarotandroid.jeu;

import java.util.Arrays;
import java.util.Random;
import java.util.Stack;
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
	 */

	 protected void distribution()
	 { 
		 incrementerNumDonneur();
		 int nombreDeJoueurs = P.getNombreDeJoueurs();
		 System.out.println("Donneur : "+P.getNomNumJoueur(numDonneur));
		 int numeroDuJoueur = P.getNumJoueurApres(numDonneur);
		 
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
		 
		 while(( nombreDeCartesPourLeChien - nombreDeCartesMisesAuChien ) != 0) 
		 {
			 randomMax = possibilitesMisesAuChien - ( nombreDeCartesPourLeChien - nombreDeCartesMisesAuChien );
			 
			 random = randomMin + (int)(Math.random() * ((randomMax - randomMin)+1));
			 // ! il faut que la valeur de retour soit comprise entre ]randomMin,randomMax] !
			 //System.out.println("random = "+random+" (entre "+randomMin+" et "+randomMax+")");
			 //nombreDeCartesMisesAuChien = (int) Math.random()*100 % 3; 
			 while(j<=(random*Constantes.CARTES_DISTRIBU_PAR_JOUEUR))
			 {
				 mainsDesJoueurs[numeroDuJoueur].addCarte(P.prendreCarteDuTas());
				 mainsDesJoueurs[numeroDuJoueur].addCarte(P.prendreCarteDuTas());
				 mainsDesJoueurs[numeroDuJoueur].addCarte(P.prendreCarteDuTas());
				 j += 3;
				 
				 numeroDuJoueur = P.getNumJoueurApres(numeroDuJoueur);
				 // System.out.println("> "+ P.getTas().size());
			 }
			System.out.println("Ajout au chien de "+P.getTas().peek()+" (k="+k+")");
			chien[k]=P.prendreCarteDuTas();
			// System.out.println("> "+ P.getTas().size());
			nombreDeCartesMisesAuChien++;
			j++;
			k++;
			randomMin = random+1;
			System.out.println();
		}
		 while(j<Constantes.NOMBRE_CARTES_TOTALES-1)
		 {
			 mainsDesJoueurs[numeroDuJoueur].addCarte(P.prendreCarteDuTas());
			 mainsDesJoueurs[numeroDuJoueur].addCarte(P.prendreCarteDuTas());
			 mainsDesJoueurs[numeroDuJoueur].addCarte(P.prendreCarteDuTas());
			 j += 3;
			 numeroDuJoueur = P.getNumJoueurApres(numeroDuJoueur);
			 System.out.println(">> "+P.getTas().size()+" (j="+j+")");
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
				j.direCarteJouee(c, joueur.toString());
			}
		}
		
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
		numJoueurEntame = P.getNumJoueurApres(numDonneur); // le premier à jouer (celui qui est après le donneur)
		int nbCartesPosees; // cartes posées dans le tour (de 1 à 4, si 4 joueurs)
		int numJoueur;
		int numJoueurVainqueurPli;
		
		while (!donneFinie()) // un tour de jeu, on commence à numJoueur = numJoueurEntame
		{
			System.out.println("jeueur entame "+numJoueurEntame);
			numJoueurEnContact = numJoueurEntame;
			
			numJoueur = numJoueurEntame;
			nbCartesPosees = 0;
			
			// TODO: On peut se débarasser de nbCartesPosees en regardant si joueur après numJoueur = numJoueurEntame
			while (nbCartesPosees < P.getNombreDeJoueurs())
			{
				plisEnCours[numJoueur] = demanderCarteJoueur(numJoueur); //changement fabrice : jai suppose que l'orde de cartes n'estpas important
				nbCartesPosees++;
				direJoueursCarteJouee(plisEnCours[numJoueur], P.getJoueur(numJoueur));
				
				numJoueur = P.getNumJoueurApres(numJoueur);
				setJoueurEnContactApres();
			}
			// nbCartesPosees == nbJoueurs : le tour est fini
			numJoueurVainqueurPli = vainqueurDuPli(plisEnCours); 
			
			if(isJoueurAttaque(numJoueurVainqueurPli)) 
			{
				plisAttaque.addAll(Arrays.asList(plisEnCours));
			}
			else
			{
				plisDefense.addAll(Arrays.asList(plisEnCours));
			}
			
			for(int i=0; i<P.getNombreDeJoueurs(); i++)// transfert de pliEnCours dans pliPrecedent 
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
	 * FIXME: souci au niveau des atouts : 
	 * Ex : Est joue le 21, Sud joue le 16. Ouest ne peut jouer que son 19 alors qu’il a 1, 7, 8, 
	 */
	public boolean isCarteLegale(Carte c, int numJ) // svp des noms de variable explicite ...
	{
		if(numJ == numJoueurEntame || numJ == P.getNumJoueurApres(numJoueurEntame) && plisEnCours[numJoueurEntame].isExcuse())
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
			for(int i=numJoueurEntame; i!=numJ; i=P.getNumJoueurApres(i))
			{
				if (plisEnCours[i].isAtout() && plisEnCours[i].getOrdre() > a.getOrdre())
				{
					a = plisEnCours[i];
				}
			}
			
			if (c.getOrdre() > a.getOrdre())
			{
				// l’atout est plus haut que les autres, reste à voir si il est autorisé en fonction de ce qui est demandé
				if ((plisEnCours[numJoueurEntame].isExcuse() && plisEnCours[P.getNumJoueurApres(numJoueurEntame)].isAtout()) || plisEnCours[numJoueurEntame].isAtout())
				{
					return true; // si la 1re carte est Atout, ou bien Excuse puis Atout, c’est donc Atout demandé donc ok
				}
				else // Reste cas où 1re carte est Couleur, ou bien Excuse et la 2e est Couleur
				{
					Couleur coulDemandee;
					if(plisEnCours[numJoueurEntame].isExcuse())
					{
						coulDemandee = plisEnCours[P.getNumJoueurApres(numJoueurEntame)].getCouleur();
					}
					else
					{
						coulDemandee = plisEnCours[numJoueurEntame].getCouleur();
					}
					 // il faut que le joueur ne possède pas la couleur demandée pour pouvoir jouer atout :
					return !mainsDesJoueurs[numJ].possedeCouleur(coulDemandee);					
				}
			} 
			else
			{
				return mainsDesJoueurs[numJ].atoutPlusGrand(c.getOrdre());
			}
		}
		else // c.isCouleur() == true 
		{
			Couleur coulDemandee;
			if(plisEnCours[numJoueurEntame].isExcuse())
			{
				coulDemandee = plisEnCours[P.getNumJoueurApres(numJoueurEntame)].getCouleur();
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
	public Vector<Carte> indiquerCartesLegalesJoueur()
	{
		Vector<Carte> cartesLegales = new Vector<Carte>();
		if(numJoueurEnContact < P.getNombreDeJoueurs())
		{
			for(Carte c: mainsDesJoueurs[numJoueurEnContact].getCartes())
			{
				if(isCarteLegale(c, numJoueurEnContact))
				{
					cartesLegales.add(c);
				}
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
		if(!P.getTas().empty())
		{
			System.out.println("Tas non vide ???");
		}
		else
		{
			Stack<Carte> nouveauTas = new Stack<Carte>();
			Random rand = new Random(); // TODO: À déplacer à un niveau plus haut pour pas en recréer un à chaque fois
			if(rand.nextBoolean())
			{
				nouveauTas.addAll(plisAttaque);
				nouveauTas.addAll(plisDefense);
			}
			else
			{
				nouveauTas.addAll(plisDefense);
				nouveauTas.addAll(plisAttaque);
			}
			P.setTas(nouveauTas);
			System.out.println(nouveauTas.size());
		}
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
	
	public void setJoueurEnContactApres()
	{
		numJoueurEnContact = P.getNumJoueurApres(numJoueurEnContact);
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
		numDonneur = P.getNumJoueurApres(numDonneur);
	}
	
	public int getPreneur() {
		return preneur;
	}
	public void setPreneur(int preneur) {
		this.preneur = preneur;
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
	public void mettreChienDansPreneur()
	{
		for(Carte c : chien)
		{
			System.out.println("crate mises au chien : "+c);
			mainsDesJoueurs[preneur].addCarte(c);
		}
	}
	public int getNumJoueurEnContact() 
	{
		return numJoueurEnContact;
	}

	public void setNumJoueurEnContact(int numJoueurEnContact) 
	{
		this.numJoueurEnContact = numJoueurEnContact;
	}

	
	/*
	 * ------------------------------------------------------------------------------------------
	 * -------------------------------Pour 5 joueur---------------------------------------------
	 * -------------------------------------------------------------------------------------------
	 */
	public int getJoueurAppele() {
		return appelee;
	}
	public void setJoueurAppele(int joueurappele) {
		this.appelee = joueurappele;
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
		plisPrecedent = new Carte[P.getNombreDeJoueurs()];
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
