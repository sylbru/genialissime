package fr.um2.projetl3.tarotandroid.jeu;

import java.util.Collections;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;

import fr.um2.projetl3.tarotandroid.clients.IJoueur;

public class Donne
{
	private Main mainsDesJoueurs[];
	private Vector<Carte> chien;
	private Croupier croupier;
	private Partie P; // la partie à  laquelle appartient cette donne
	
	private Contrat contratEnCours;
	private int preneur;
	private int appelee;// LE joueur appele dans le mode a 5 joueurs
	private Vector<Carte> plisEnCours;
	private Vector<Carte> plisPrecedent;
	
	private int numJoueurEntame; // premier à  jouer dans le pli
	// protected pour calculer les pts des plis Attaque/Defense
	protected Vector<Carte> plisAttaque; 
	protected Vector<Carte> plisDefense;
	private int numDonneur; // celui qui distribue dans la donne (utilisï¿œ pour le premier tour)
	//pour l'echange de l'excuse a la fin du pli si il n'a pas ete possible avant
	private int ExcuseARemplacer =-1;
	private Vector<Carte> vecteurrecevantExcuse;
	private Vector<Carte> vecteurcontenantExcuse;
	
	private int numJoueurEnContact; // le joueur avec lequel on est en communication (utilisï¿œ pour savoir de qui on parle quand un joueur demande Â« sa Â» main)
	
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
			 P.getJoueur(i).direMain(mainsDesJoueurs[i].getCartes());
		 }
		 
	}

	/*
	 * --------------------------------------------------------------------------------------------
	 * ------------------------------------ Mï¿œthodes --------------------------------------------
	 * --------------------------------------------------------------------------------------------
	 */
	 
	void reveleChien(){
		croupier.reveleChien(chien);
	}
 
	 /** Méthode fini maisobject à  tester
	  *  // TODO test
	  * @author JB
	  * @author hhachiche
	  * 	
	  * @param vecteurContenantLePli
	  * @return l'indice du tableau ou se trouve la carte qui remporte le plis grà¢ce à  à§a on peut retrouver qui remporte le plis
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
	  * Phase de jeu des cartes dans une donne, après les annonces et l'écart, avant le comptage des points.
	  * (pour info, l'expression " jeu de la carte ", ça vient pas de moi,
	  * voir http://www.fftarot.fr/index.php/Decouvrir/Le-Jeu-de-la-carte.html )
	  */
	@SuppressWarnings("unchecked")
	protected void jeuDeLaCarte()
	{
		numJoueurEntame = P.getNumJoueurApres(numDonneur); // le premier à  jouer (celui qui est après le donneur)
		int nbCartesPosees; // cartes posées dans le tour (de 1 à  4, si 4 joueurs)
		int numJoueur;
		int numJoueurVainqueurPli;
		
		while (!donneFinie()) //donne comporte tous les pli sauf le dernier qui est regarde differament à  cause du comportement different de l'excuse etc...
		{
			 // un tour de jeu, on commence à  numJoueur = numJoueurEntame
			numJoueurEnContact = numJoueurEntame;
			
			numJoueur = numJoueurEntame;
			nbCartesPosees = 0;
			// assert vérifiant (à  chaque tour) que les joueurs ont tous bien le même nombre de cartes
			assert	(mainsDesJoueurs[0].nbCartesRestantes() == mainsDesJoueurs[1].nbCartesRestantes())
					&& (mainsDesJoueurs[1].nbCartesRestantes() == mainsDesJoueurs[2].nbCartesRestantes())
					&& (mainsDesJoueurs[2].nbCartesRestantes() == mainsDesJoueurs[3].nbCartesRestantes())
					: ("nb de cartes : "
							+ mainsDesJoueurs[0].nbCartesRestantes()
							+ mainsDesJoueurs[1].nbCartesRestantes()
							+ mainsDesJoueurs[2].nbCartesRestantes()
							+ mainsDesJoueurs[3].nbCartesRestantes());
			
			// TODO: On peut se débarrasser de nbCartesPosees en regardant si joueur après numJoueur = numJoueurEntame
			for (int fillerup=0;fillerup<P.getNombreDeJoueurs();fillerup++)
			{
				plisEnCours.add(new Carte(21));
			}
			while (nbCartesPosees < P.getNombreDeJoueurs())
			{
				System.out.println("On a posï¿œ "+nbCartesPosees+" cartes");
				System.out.println("Taille du pli "+plisEnCours.size());
				System.out.println("Le joueur en cours est "+numJoueur);
				
				//plisEnCours.insertElementAt(demanderCarteJoueur((numJoueur+P.getNombreDeJoueurs())%P.getNombreDeJoueurs()), (numJoueur+P.getNombreDeJoueurs())%P.getNombreDeJoueurs());
				plisEnCours.get((numJoueur+P.getNombreDeJoueurs())%P.getNombreDeJoueurs()).set(demanderCarteJoueur((numJoueur+P.getNombreDeJoueurs())%P.getNombreDeJoueurs()).uid());
				nbCartesPosees++;
				
				System.out.println("Taille du pli "+plisEnCours.size());
				croupier.direJoueursCarteJouee(plisEnCours.get((numJoueur+P.getNombreDeJoueurs())%P.getNombreDeJoueurs()), (numJoueur+P.getNombreDeJoueurs())%P.getNombreDeJoueurs());
				numJoueur = P.getNumJoueurApres((numJoueur+P.getNombreDeJoueurs())%P.getNombreDeJoueurs());
				setJoueurEnContactApres();
			}
			// nbCartesPosees == nbJoueurs : le tour est fini
			
			numJoueurVainqueurPli = vainqueurDuPli(plisEnCours);
			
			croupier.direJoueursPliRemporté(plisEnCours, numJoueurVainqueurPli);
			if(isJoueurAttaque(numJoueurVainqueurPli)) 
			{
				int a;
				if((a = excuseDanslePlis() )< 0)
				{
					plisAttaque.addAll(plisEnCours);
				}
				else //l'excuse est dans le pli
				{ 	
					if(isJoueurAttaque(a))//excuse remporte par l'attaque (partie a 5)
					{
						plisAttaque.addAll(plisEnCours);
					}
					else//excuse gardee par la defence
					{
						remplacerExcuse(plisDefense, plisEnCours,a,plisAttaque);
						plisAttaque.addAll(plisEnCours);
					}
				}
			}
			else
			{

				int a;
				if((a = excuseDanslePlis() )< 0)
				{
					plisDefense.addAll(plisEnCours);
				}
				else //l'excuse est dans le pli
				{ 	
					if(isJoueurDefense(a))//excuse remporte par la defence (partie a 5)
					{
						plisDefense.addAll(plisEnCours);
					}
					else//excuse gardee par l'attaque
					{
						remplacerExcuse(plisAttaque, plisEnCours,a,plisDefense);
						plisDefense.addAll(plisEnCours);
					}
				}
			}
			
			plisPrecedent = (Vector<Carte>) plisEnCours.clone(); // transfert de pliEnCours dans pliPrecedent
			plisEnCours.clear();
			numJoueurEntame = numJoueurVainqueurPli; // celui qui a gagné le pli entame au tour suivant
		}
		System.out.println("numero joueur entame = "+numJoueurEntame);
		dernierPli(numJoueurEntame);
	}
	
	public void remplacerExcuse(Vector<Carte> v1, Vector<Carte> v2, int a, Vector<Carte> v3)
	{
		Carte c;
		c= v2.get(a);
		v2.remove(a);
		boolean echange=false;
		System.out.println("remplace excuse l'escuse se a l'endroit "+a);
		System.out.println("cartes dans v1");
		for(int i=0; i<v1.size();i++)
		{
			System.out.println(v1.get(i).uid());
		}
		System.out.println("cartes dans plis en cours");
		for(int i=0; i<v2.size();i++)
		{
			System.out.println(v2.get(i).uid());
		}
		if(c.isExcuse()==false)
		{
			System.out.println("erreur critique : l'excuse n'est la ou elle devrait etre");
			return;
		}
		else
		{
			if(v1.size()==0)
			{
				System.out.println("le plis est vide on ne peut pas encore remplacer l'excuse");
				ExcuseARemplacer = a;
				vecteurrecevantExcuse = v1;
				vecteurcontenantExcuse = v3;
				return;
			}
			else
			{
				int i=0;
				while(!echange)
				{
					Carte c2 = v1.get(i);
					if(c2.isCouleur() && c2.getOrdre()<10)
					{
						v2.add(c2);
						v1.remove(c2);
						v1.add(c);
						echange=true;
						return;
					}
					else
					{
						if(i<v1.size()-1)
						i++;
						else
						{
							ExcuseARemplacer = a;
							vecteurrecevantExcuse = v1;
							vecteurcontenantExcuse = v3;
							return;
						}
					}
				}
			}
			if(!echange)
			{
				System.out.println("On a pas trouve de carte a echanger");
				ExcuseARemplacer = a;
				vecteurrecevantExcuse = v1;
				vecteurcontenantExcuse = v3;
			}
		}
	}
	
	public void dernierPli(int numJoueurEntame)
	{
		System.out.println("on entre dans le dernier pli ici  : le joueur qui entame est le joueeur numero : "+numJoueurEntame);
		int numJoueur = numJoueurEntame;
		numJoueurEnContact = numJoueurEntame;
		int numJoueurVainqueurPli;
		int nbCartesPosees = 0;
		for(int i=0; i<P.getNombreDeJoueurs();i++)
		{
			assert	(mainsDesJoueurs[i].nbCartesRestantes() == 1);
			System.out.println("nombre des cartes dans la main des joueur = "+mainsDesJoueurs[i].nbCartesRestantes());
			System.out.println("cartes : ");
			for(int j=0;j<mainsDesJoueurs[i].nbCartesRestantes();j++)
			{
				mainsDesJoueurs[i].affiche();
			}
		}
		for (int fillerup=0;fillerup<P.getNombreDeJoueurs();fillerup++)
		{
			plisEnCours.add(new Carte(21));
		}
		while (nbCartesPosees < P.getNombreDeJoueurs())
		{
			System.out.println("Le joueur en cours est "+numJoueur);
			System.out.println("On a posï¿œ "+nbCartesPosees+" cartes");
			System.out.println("Taille du pli "+plisEnCours.size());

			
			//plisEnCours.insertElementAt(demanderCarteJoueur((numJoueur+P.getNombreDeJoueurs())%P.getNombreDeJoueurs()), (numJoueur+P.getNombreDeJoueurs())%P.getNombreDeJoueurs());
			plisEnCours.get((numJoueur+P.getNombreDeJoueurs())%P.getNombreDeJoueurs()).set(demanderCarteJoueur((numJoueur+P.getNombreDeJoueurs())%P.getNombreDeJoueurs()).uid());
			nbCartesPosees++;
			
			System.out.println("Taille du pli "+plisEnCours.size());
			croupier.direJoueursCarteJouee(plisEnCours.get((numJoueur+P.getNombreDeJoueurs())%P.getNombreDeJoueurs()), (numJoueur+P.getNombreDeJoueurs())%P.getNombreDeJoueurs());
			numJoueur = P.getNumJoueurApres((numJoueur+P.getNombreDeJoueurs())%P.getNombreDeJoueurs());
			setJoueurEnContactApres();
		}
		
		numJoueurVainqueurPli = vainqueurDuPli(plisEnCours);
		croupier.direJoueursPliRemporté(plisEnCours, numJoueurVainqueurPli);
		if(isJoueurAttaque(numJoueurVainqueurPli)) 
				plisAttaque.addAll(plisEnCours);
		else
				plisDefense.addAll(plisEnCours);
		plisPrecedent = (Vector<Carte>) plisEnCours.clone(); // transfert de pliEnCours dans pliPrecedent
		plisEnCours.clear();
		if(ExcuseARemplacer!=-1)
		{
			remplacerExcuse(vecteurrecevantExcuse,vecteurcontenantExcuse,excuseDansleVecteur(vecteurcontenantExcuse),null);
		}
		numJoueurEntame = numJoueurVainqueurPli; // celui qui a gagnï¿œ le pli entame au tour suivant
	
	}
	//retourne la position de l'excuse dans le plis et -1 si l'excuse n'est pas dans le pli
	public int excuseDanslePlis()
	{
		for(int i=0; i< P.getNombreDeJoueurs(); i++ )
		{
			if(plisEnCours.get(i).isExcuse())
				return i;
		}
		return -1;
	}
	public int excuseDansleVecteur(Vector<Carte> v)
	{
		for(int i=0; i< v.size(); i++ )
		{
			if(v.get(i).isExcuse())
				return i;
		}
		return -1;
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
		if(numJ == numJoueurEntame || numJ == P.getNumJoueurApres((numJoueurEntame+P.getNombreDeJoueurs())%P.getNombreDeJoueurs()) && plisEnCours.get((numJoueurEntame+P.getNombreDeJoueurs())%P.getNombreDeJoueurs()).isExcuse())
		{
			return true; // si le joueur joue en premier ou s'il joue après l'excuse
		}
		else if(c.isExcuse())
		{
			return true; // s'il joue l'excuse 
		}
		// TODO
		// ! il ya un cas execptionnel ou il ne peut pas jouer l'excuse si autoriser3boutsDans1pli est à  false et qu'il y a déja deux bout sur la table :)
		// ! facile à  implementer mais à  faire au cas ou
		// à mettre en tout premier (et pour n'importe quel bout, pas seulement l'excuse)
		else if (c.isAtout())
		{
			// on vérifie que l'atout est plus haut que les autres.
			// (calcul de l'atout le plus haut dans le pli en cours)
			Carte atoutMax = new Carte(0);
			System.out.println(numJoueurEntame+" "+numJ);
			for(int i=numJoueurEntame; i!=numJ; i=P.getNumJoueurApres(i))
			{
				//System.out.println("Yoppe je get de "+i);
				if (plisEnCours.get((i+P.getNombreDeJoueurs())%P.getNombreDeJoueurs()).isAtout() && plisEnCours.get((i+P.getNombreDeJoueurs())%P.getNombreDeJoueurs()).getOrdre() > atoutMax.getOrdre())
				{
					atoutMax = plisEnCours.get((i+P.getNombreDeJoueurs())%P.getNombreDeJoueurs());
				}
			}
			// System.out.println("Atout max : "+atoutMax);
			// System.out.println("Atout proposï¿œ : "+c);
			
			if (c.getOrdre() < atoutMax.getOrdre() && mainsDesJoueurs[numJ].possedeAtoutPlusGrand(atoutMax.getOrdre()))
			{
				// System.out.println("sortie atout mauvais, pas ok");
				return false; // s'il n'a pas monté sur l'atout le plus haut alors qu'il pouvait
			}
			else // il a monté sur l'atout le plus haut ou bien il n'a pas monté mais ne pouvait pas, reste à  voir s'il pouvait jouer atout.

			{
				if (plisEnCours.get((numJoueurEntame+P.getNombreDeJoueurs())%P.getNombreDeJoueurs()).isAtout() || (plisEnCours.get((numJoueurEntame+P.getNombreDeJoueurs())%P.getNombreDeJoueurs()).isExcuse() && plisEnCours.get(P.getNumJoueurApres(numJoueurEntame)).isAtout()))
				{
					// System.out.println("sortie atout demandé, ok");
					return true; // si la 1re carte est Atout, ou bien Excuse puis Atout, c'est donc Atout demandé donc ok
				}
				else // Reste cas oà¹ 1re carte est Couleur, ou bien Excuse et la 2e est Couleur
				{
					Couleur coulDemandee;
					if(plisEnCours.get((numJoueurEntame+P.getNombreDeJoueurs())%P.getNombreDeJoueurs()).isExcuse())
					{
						coulDemandee = plisEnCours.get(P.getNumJoueurApres((numJoueurEntame+P.getNombreDeJoueurs())%P.getNombreDeJoueurs())).getCouleur();
					}
					else
					{
						coulDemandee = plisEnCours.get((numJoueurEntame+P.getNombreDeJoueurs())%P.getNombreDeJoueurs()).getCouleur();
					}
					// il faut que le joueur ne possède pas la couleur demandée pour pouvoir jouer atout :
					// System.out.println("sortie couleur demandée, "+!mainsDesJoueurs[numJ].possedeCouleur(coulDemandee));
					return !mainsDesJoueurs[(numJ+P.getNombreDeJoueurs())%P.getNombreDeJoueurs()].possedeCouleur(coulDemandee);					
				}
			}
		}
		else // c.isCouleur() == true 
		{
			Couleur coulDemandee;
			System.out.println(numJoueurEntame);
			if(plisEnCours.get((numJoueurEntame+P.getNombreDeJoueurs())%P.getNombreDeJoueurs()).isExcuse())
			{
				coulDemandee = plisEnCours.get(P.getNumJoueurApres(numJoueurEntame)).getCouleur();
			}
			else
			{
				coulDemandee = plisEnCours.get((numJoueurEntame+P.getNombreDeJoueurs())%P.getNombreDeJoueurs()).getCouleur();
			}
			return (coulDemandee == c.getCouleur()) || !mainsDesJoueurs[numJ].possedeCouleur(coulDemandee) && !mainsDesJoueurs[numJ].possedeAtout();
		}
		
	}
	

	/**
	 * Demande au joueur de jouer une carte et vï¿œrifie si elle est lï¿œgale (redemande si besoin). 
	 * @param num La position du joueur
	 */
	protected Carte demanderCarteJoueur(int num) 
	{
		num = (num+P.getNombreDeJoueurs())%P.getNombreDeJoueurs();
		Carte carteProposee;
		do
		{
			System.out.println("demande carte joueur donne "+num);
			carteProposee = croupier.demanderCarteJoueur(num);
			/*
			 * test des condition de la boucle 
			 * */
			 if(mainsDesJoueurs[num].possede(carteProposee)) System.out.println("contains !!!");
			if(isCarteLegale(carteProposee, num)) System.out.println("cartelegale");
		}
		while(!(mainsDesJoueurs[num].possede(carteProposee)&& isCarteLegale(carteProposee, num)));
		mainsDesJoueurs[num].removeCarte(carteProposee);
		System.out.println("Renvoyons "+carteProposee.toString());
		return carteProposee;
	}
	
	/**
	 * 
	 * @return un vecteur contenant les Cartes possibles (légales) à  jouer pour le joueur numJoueur
	 * Actuellement à§a regarde toutes ses cartes et fait appel à  isCarteLegale() pour chacune.
	 * Est-ce que ce serait plus efficace de procéder plus intelligemment ? à voir.

	 */
	public Vector<Carte> indiquerCartesLegalesJoueur()
	{
		Vector<Carte> cartesLegales = new Vector<Carte>();
		if(numJoueurEnContact < P.getNombreDeJoueurs())
		{
			for(Carte c: mainsDesJoueurs[(numJoueurEnContact+P.getNombreDeJoueurs())%P.getNombreDeJoueurs()].getCartes())
			{
				if(isCarteLegale(c, (numJoueurEnContact+P.getNombreDeJoueurs())%P.getNombreDeJoueurs() ))
				{
					//System.out.println(c.toString()+" est lï¿œgal");
					cartesLegales.add(c);
				}
			}
		}
		return cartesLegales;
	}
	
	/**
	 * 

	 * @return un vecteur contenant les cartes possibles pour l'écart

	 */
	public Vector<Carte> indiquerCartesLegalesEcart()
	{
		Vector<Carte> cartesLegalesEcart = new Vector<Carte>();
		if(numJoueurEnContact < P.getNombreDeJoueurs())
		{
			for(Carte c: mainsDesJoueurs[(numJoueurEnContact+P.getNombreDeJoueurs())%P.getNombreDeJoueurs()].getCartes())
			{
				if(!c.isBout() && !c.isAtout() && !(c.isCouleur() && c.getOrdre()==14))
				{
					cartesLegalesEcart.add(c);
				}
			}
		}
		return cartesLegalesEcart;
	}
	
	public boolean donneFinie()
	{
		return plisAttaque.size() + plisDefense.size() == Constantes.NOMBRE_CARTES_TOTALES-P.getNombreDeJoueurs();
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
			System.out.println("Plis dï¿œfense : "+plisDefense);
			
			

			Random rand = new Random(); // TODO: à déplacer à  un niveau plus haut pour pas en recréer un à  chaque fois

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
			
			
			System.out.println("Nouveau tas non rotatï¿œ : "+nouveauTas);
			// Coupe
			Random rGauss = new Random();
			int coupe;
			do
			{
				coupe = (int) Math.round(Constantes.NOMBRE_CARTES_TOTALES/2 + rGauss.nextGaussian()*5);
				// Gauss avec moyenne = 39 (78/2), variance = 5 (donne des rï¿œsultats satisfaisants)
			}
			while(coupe < 3 || coupe > 75); // on réessaie si jamais on a un résultat trop petit ou trop grand (statistiquement possible)
			System.out.println("Coupe à  la carte numéro " + coupe);

			
			System.out.println("Nouveau tas rotatï¿œ : "+nouveauTas);
			
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
				random = ((random + 1)+P.getNombreDeJoueurs()) % P.getNombreDeJoueurs();
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
	 * Utilisï¿œ par un client (IJoueur) pour demander sa main.
	 *  
	 * Renommer en getMaMain() ?
	 * 
	 * @return la main du joueur de numï¿œro numJoueurEnContact
	 */
	public Main getMain()
	{
		//System.out.println(P.getNomNumJoueur(numJoueurEnContact)+" (joueur:"+numJoueurEnContact+") demande sa main...");
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
		// ? est-ce que getID() correspond bien à  la position/au numéro ?

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
<<<<<<< .mine
	 * Sert à  incrémenter numDonneur pour le passer au joueur suivant.
	 * Utilisé à  chaque début de donne.
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
			System.out.println("Carte du chien donnï¿œe au preneur : "+c);
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
		croupier = new Croupier();
	}
	
	public Donne()
	{
		this.P = Context.P;
		init();
	}	
	
	public Donne(Partie P)
	{
		this.P = P;
		init();
	}

	
	public static void main(String[] args)
	{/*
		 Donne donne = new Donne(); // bon c'est le bordel entre les méthodes statiques et les non-statiques,

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