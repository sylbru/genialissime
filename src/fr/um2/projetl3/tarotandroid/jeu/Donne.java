package fr.um2.projetl3.tarotandroid.jeu;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;

import fr.um2.projetl3.tarotandroid.clients.IJoueur;

public class Donne
{
	private Main mainsDesJoueurs[];
	private Vector<Carte> chien;
	private Partie P; // la partie à laquelle appartient cette donne
	
	private Contrat contratEnCours;
	private int preneur;
	private int appelee;// LE joueur appele dans le mode a 5 joueurs
	private Vector<Carte> plisEnCours;
	private Vector<Carte> plisPrecedent;
	
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
			 mainsDesJoueurs[i] = new Main();
		 
		 chien = new Vector<Carte>();
		 
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
			// System.out.println("Ajout au chien de "+P.getTas().peek()+" (k="+k+")");
			chien.add(k, P.prendreCarteDuTas());
			nombreDeCartesMisesAuChien++;
			j++;
			k++;
			randomMin = random+1;
		}
		 while(j<Constantes.NOMBRE_CARTES_TOTALES-1)
		 {
			 mainsDesJoueurs[numeroDuJoueur].addCarte(P.prendreCarteDuTas());
			 mainsDesJoueurs[numeroDuJoueur].addCarte(P.prendreCarteDuTas());
			 mainsDesJoueurs[numeroDuJoueur].addCarte(P.prendreCarteDuTas());
			 j += 3;
			 numeroDuJoueur = P.getNumJoueurApres(numeroDuJoueur);
		 }
		 for(int i=0; i<4; i++)
		 {
			 mainsDesJoueurs[i].affiche();
			 P.getJoueur(i).direMain(mainsDesJoueurs[i].getCartes());
		 }
		 //reveleChien();
		 
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
			Vector<Carte> vChien = new Vector<Carte>(); 
			for(Carte c :chien)
			{
				vChien.add(c);
			}
			j.direChien(vChien);
		}
	}
	
	/**
	 * @author niavlys
	 * Informe les joueurs du fait qu’une carte a été jouée par un joueur
	 * @param c La carte jouée
	 * @param joueur Le joueur qui a joué la carte
	 */
	public void direJoueursCarteJouee(Carte c, int joueur)
	{
		int pos = 0; 
		for(IJoueur j: P.getJoueurs())
		{
			j.direCarteJouee(c, (pos-joueur)%P.getNombreDeJoueurs());
		}
	}
	
	/**
	 * @author niavlys
	 * Informe les joueurs du fait qu’une carte a été jouée par un joueur
	 * @param c La carte jouée
	 * @param joueur Le joueur qui a joué la carte
	 */
	public void direJoueursPliRemporté(Vector<Carte> pli, int joueur)
	{
		int pos = 0;
		for(IJoueur j: P.getJoueurs())
		{
			Vector<Carte> vPli = new Vector<Carte>(); 
			int i = 0;
			while (pli.get(i)==null)
			{
				vPli.add(pli.get(i));
			}
			j.direPliRemporté(vPli, (pos-joueur)%P.getNombreDeJoueurs());
		}
	}
		
	 /** Méthode fini maisobject à tester
	  *  // TODO test
	  * @author JB
	  * @author hhachiche
	  * 	
	  * @param vecteurContenantLePli
	  * @return l'indice du tableau ou se trouve la carte qui remporte le plis grâce à ça on peut retrouver qui remporte le plis
	 * @throws Throwable 
	  */
	 public int vainqueurDuPli(Vector<Carte> vecteurContenantLePli)
	 {
		int indice = -1;
		int nombreDeJoueur = P.getNombreDeJoueurs();
		int i;
		
		Carte maxAtout = new Carte(0);
		for(i=0;i < nombreDeJoueur;i++)					//A chaque pli on commence par regarder s'il y a des atouts,si oui on prend la plus forte
		{
			if(vecteurContenantLePli.get(i).isAtout())
			{
				if((maxAtout.getOrdre()) < (vecteurContenantLePli.get(i).getOrdre()))
				{
					maxAtout = vecteurContenantLePli.get(i);
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
			
			if(vecteurContenantLePli.get(0).isExcuse())
			{			
				couleurDemander = vecteurContenantLePli.get(1).getCouleur();
			}
			else // if(! tableauContenantLePlis[2].isAtout()) // le code est bien ecrit du coup cette verification est inutile
			{
				couleurDemander = vecteurContenantLePli.get(0).getCouleur();
			}
			
			Carte maxCouleur = new Carte(couleurDemander, 0);
			for(i=0;i < nombreDeJoueur;i++)					
			{
				if(vecteurContenantLePli.get(i).isCouleur())
				{
					if(vecteurContenantLePli.get(i).getCouleur() == couleurDemander)
					{
						if(maxCouleur.getOrdre() < vecteurContenantLePli.get(i).getOrdre())
						{	
							maxCouleur = vecteurContenantLePli.get(i);
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
			// System.out.println("jeueur entame "+numJoueurEntame);
			numJoueurEnContact = numJoueurEntame;
			
			numJoueur = numJoueurEntame;
			nbCartesPosees = 0;
			
			// assert vérifiant (à chaque tour) que les joueurs ont tous bien le même nombre de cartes
			assert	(mainsDesJoueurs[0].nbCartesRestantes() == mainsDesJoueurs[1].nbCartesRestantes())
					&& (mainsDesJoueurs[1].nbCartesRestantes() == mainsDesJoueurs[2].nbCartesRestantes())
					&& (mainsDesJoueurs[2].nbCartesRestantes() == mainsDesJoueurs[3].nbCartesRestantes())
					: ("nb de cartes : "
							+ mainsDesJoueurs[0].nbCartesRestantes()
							+ mainsDesJoueurs[1].nbCartesRestantes()
							+ mainsDesJoueurs[2].nbCartesRestantes()
							+ mainsDesJoueurs[3].nbCartesRestantes());
			
			// TODO: On peut se débarrasser de nbCartesPosees en regardant si joueur après numJoueur = numJoueurEntame
			while (nbCartesPosees < P.getNombreDeJoueurs())
			{
				plisEnCours.add(numJoueur,demanderCarteJoueur(numJoueur)); //changement fabrice : jai suppose que l'orde de cartes n'estpas important
				nbCartesPosees++;
				direJoueursCarteJouee(plisEnCours.get(numJoueur), numJoueur);
				numJoueur = P.getNumJoueurApres(numJoueur);
				setJoueurEnContactApres();
			}
			// nbCartesPosees == nbJoueurs : le tour est fini
			numJoueurVainqueurPli = vainqueurDuPli(plisEnCours); 
			direJoueursPliRemporté(plisEnCours, numJoueurVainqueurPli);
			if(isJoueurAttaque(numJoueurVainqueurPli)) 
			{
				plisAttaque.addAll(plisEnCours);
			}
			else
			{
				plisDefense.addAll(plisEnCours);
			}
			
			for(int i=0; i<P.getNombreDeJoueurs(); i++)// transfert de pliEnCours dans pliPrecedent 
			{
				int a = (i + numJoueurEntame)% P.getNombreDeJoueurs();
				plisPrecedent.add(i,plisEnCours.get(a));
			}
			plisEnCours.clear();
			numJoueurEntame = numJoueurVainqueurPli; // celui qui a gagné le pli entame au tour suivant
		}
	}
	
	/**
	 * @author niavlys
	 * @param c une carte
	 * @param numJ un joueur
	 * @return true si la carte posée par le joueur (paramètres) est légale 
	 * 
	 */
	public boolean isCarteLegale(Carte c, int numJ) // svp des noms de variable explicite ...
	{
		// System.out.println("isCarteLegale, numJ = "+numJ+", carte = "+c+", numEntame = "+numJoueurEntame);
		if(numJ == numJoueurEntame || numJ == P.getNumJoueurApres(numJoueurEntame) && plisEnCours.get(numJoueurEntame).isExcuse())
		{
			return true; // si le joueur joue en premier ou s’il joue après l’excuse
		}
		else if(c.isExcuse())
		{
			return true; // s’il joue l’excuse 
		}
		// TODO
		// ! il ya un cas execptionnel ou il ne peut pas jouer l'excuse si autoriser3boutsDans1pli est à false et qu'il y a déja deux bout sur la table :)
		// ! facile à implementer mais à faire au cas ou
		// À mettre en tout premier (et pour n’importe quel bout, pas seulement l’excuse)
		
		else if (c.isAtout())
		{
			// on vérifie que l’atout est plus haut que les autres.
			// (calcul de l’atout le plus haut dans le pli en cours)
			Carte atoutMax = new Carte(0);
			for(int i=numJoueurEntame; i!=numJ; i=P.getNumJoueurApres(i))
			{
				if (plisEnCours.get(i).isAtout() && plisEnCours.get(i).getOrdre() > atoutMax.getOrdre())
				{
					atoutMax = plisEnCours.get(i);
				}
			}
			// System.out.println("Atout max : "+atoutMax);
			// System.out.println("Atout proposé : "+c);
			
			if (c.getOrdre() < atoutMax.getOrdre() && mainsDesJoueurs[numJ].possedeAtoutPlusGrand(atoutMax.getOrdre()))
			{
				// System.out.println("sortie atout mauvais, pas ok");
				return false; // s’il n’a pas monté sur l’atout le plus haut alors qu’il pouvait
			}
			else // il a monté sur l’atout le plus haut ou bien il n’a pas monté mais ne pouvait pas, reste à voir s’il pouvait jouer atout.
			{
				if (plisEnCours.get(numJoueurEntame).isAtout() || (plisEnCours.get(numJoueurEntame).isExcuse() && plisEnCours.get(P.getNumJoueurApres(numJoueurEntame)).isAtout()))
				{
					// System.out.println("sortie atout demandé, ok");
					return true; // si la 1re carte est Atout, ou bien Excuse puis Atout, c’est donc Atout demandé donc ok
				}
				else // Reste cas où 1re carte est Couleur, ou bien Excuse et la 2e est Couleur
				{
					Couleur coulDemandee;
					if(plisEnCours.get(numJoueurEntame).isExcuse())
					{
						coulDemandee = plisEnCours.get(P.getNumJoueurApres(numJoueurEntame)).getCouleur();
					}
					else
					{
						coulDemandee = plisEnCours.get(numJoueurEntame).getCouleur();
					}
					// il faut que le joueur ne possède pas la couleur demandée pour pouvoir jouer atout :
					// System.out.println("sortie couleur demandée, "+!mainsDesJoueurs[numJ].possedeCouleur(coulDemandee));
					return !mainsDesJoueurs[numJ].possedeCouleur(coulDemandee);					
				}
			}
		}
		else // c.isCouleur() == true 
		{
			Couleur coulDemandee;
			if(plisEnCours.get(numJoueurEntame).isExcuse())
			{
				coulDemandee = plisEnCours.get(P.getNumJoueurApres(numJoueurEntame)).getCouleur();
			}
			else
			{
				coulDemandee = plisEnCours.get(numJoueurEntame).getCouleur();
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
			//System.out.println(P.getJoueur(num).getNomDuJoueur()+" "+carteProposee.toString());
			/*
			 * test des condition de la boucle 
			 * if(mainsDesJoueurs[num].contains(carteProposee)) System.out.println("contains !!!");
			 * if(isCarteLegale(carteProposee, num)) System.out.println("cartelegale");
			 */
		}
		while(!(mainsDesJoueurs[num].possede(carteProposee)&& isCarteLegale(carteProposee, num)));
		
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
					//System.out.println(c.toString()+" est légal");
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
	
	
	protected void reformerTas()
	{
		Stack<Carte> nouveauTas = new Stack<Carte>();
		
		if(!P.getTas().empty())
		{
			System.out.println("Tas non vide ???");
		}
		else if (contratEnCours != Contrat.AUCUN)
		{
			System.out.println("Plis attaque : "+plisAttaque);
			System.out.println("Plis défense : "+plisDefense);
			
			
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
			plisAttaque.clear();
			plisDefense.clear();
			
			
			System.out.println("Nouveau tas non rotaté : "+nouveauTas);
			// Coupe
			Random rGauss = new Random();
			int coupe;
			do
			{
				coupe = (int) Math.round(Constantes.NOMBRE_CARTES_TOTALES/2 + rGauss.nextGaussian()*5);
				// Gauss avec moyenne = 39 (78/2), variance = 5 (donne des résultats satisfaisants)
			}
			while(coupe < 3 || coupe > 75); // on réessaie si jamais on a un résultat trop petit ou trop grand (statistiquement possible)
			System.out.println("Coupe à la carte numéro " + coupe);
			Collections.rotate(nouveauTas, coupe); 
			
			System.out.println("Nouveau tas rotaté : "+nouveauTas);
			
			P.setTas(nouveauTas);
			System.out.println(nouveauTas.size());
		}
		else
		{
			System.out.println("test");
			int random = (int)(Math.random() * P.getNombreDeJoueurs());
			Random rand = new Random();
			
			for(int i=0;i<P.getNombreDeJoueurs();i++)
			{
				if(!chien.isEmpty() || rand.nextBoolean())
				{
					nouveauTas.addAll(chien);
					chien.clear();
				}
				nouveauTas.addAll(mainsDesJoueurs[random].getCartes());
				mainsDesJoueurs[random].clear();
				random = (random + 1) % P.getNombreDeJoueurs();
			}
			
			if(!chien.isEmpty())
			{
				nouveauTas.addAll(chien);
				chien.clear();
			}
			
			P.setTas(nouveauTas);
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
	 * !changement fabrice : mis en public pour faire des tests.. Joueur en contact ne fonctionera pas avec le serveur qu'on a pour l'instant
	 */
	public Main getMain(int numJoueur)
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
	
	private void setJoueurEnContactApres()
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

	public Vector<Carte> getPlisPrecedent() {
		return plisPrecedent;
	}
	@SuppressWarnings("unused")
	private void setPlisPrecedent(Vector<Carte> plisPrecedent) {
		this.plisPrecedent = plisPrecedent;
	}
	
	public Vector<Carte> getPlisEnCours() {
		return plisEnCours;
	}
	@SuppressWarnings("unused")
	private void setPlisEnCours(Vector<Carte> plisEnCours) {
		this.plisEnCours = plisEnCours;
	}

	public void mettreChienDansLesPlisDeLAttaque()
	{
		 plisAttaque.addAll(chien);
	}
	public void mettreChienDansLesPlisDeLaDefense()
	{
		 plisDefense.addAll(chien);
	}
	public void mettreChienDansPreneur()
	{
		for(Carte c : chien)
		{
			System.out.println("Carte du chien donnée au preneur : "+c);
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
		plisEnCours = new Vector<Carte>();
		plisPrecedent = new Vector<Carte>();
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
