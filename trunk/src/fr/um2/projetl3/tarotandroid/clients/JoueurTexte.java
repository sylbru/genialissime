package fr.um2.projetl3.tarotandroid.clients;

import static fr.um2.projetl3.tarotandroid.jeu.Context.P;

import java.util.Scanner;
import java.util.Vector;

import fr.um2.projetl3.tarotandroid.connection.Cartes;
import fr.um2.projetl3.tarotandroid.jeu.Carte;
import fr.um2.projetl3.tarotandroid.jeu.Contrat;
import fr.um2.projetl3.tarotandroid.jeu.Couleur;
import fr.um2.projetl3.tarotandroid.jeu.Main;

public class JoueurTexte implements IJoueur
{
	private Main main;
	// ! c’est une copie « locale » de la main retournée par P.donne().getMain(), penser à mettre à jour à chaque modification !
	// Pour mettre à jour, on appelle majMain().
	// (utile pour éviter d’appeler P.donne().getMain() à chaque fois)
	
	private String nom;
	private boolean quiet = false; // comme on teste pour l’instant avec quatre JoueurTexte, 
								   // quiet permet de pas afficher tout quatre fois (un seul a quiet = true)
	
	/**
	 * Regarde dans Donne pour récupérer la main du joueur (met à jour le champ privé main).
	 */
	private void majMain()
	{
		this.main = P.donne().getMain();
	}
	
	public Contrat demanderAnnonce(Contrat contrat)
	{
		Contrat c = null;
		boolean mauvais_Contrat=true;
		System.out.println(nom + ", à vous de parler :");
		while(mauvais_Contrat)
		{
			switch(contrat.getPoids())
			{
			case 0:
				System.out.println(" Vos Choix :  0 = Passe, 1 = Petite, 2 = Garde, 3 = Garde sans, 4 = GardeContre");
				break;
			case 1:
				System.out.println(" Vos Choix :  0 = Passe, 1 = Petite, 2 = Garde, 3 = Garde sans, 4 = GardeContre");
				break;
			case 2:
				System.out.println(" Vos Choix :  0 = Passe, 2 = Garde, 3 = Garde sans, 4 = GardeContre");
				break;
			case 3:
				System.out.println(" Vos Choix :  0 = Passe, 3 = Garde sans, 4 = GardeContre");
				break;
			case 4:
				System.out.println(" Vos Choix :  0 = Passe, 4 = GardeContre");
				break;
			default:
				//! il faudrait lancer une exception
				System.out.println("0 = Passe, 1 = Petite, 2 = Garde, 3 = Garde sans, 4 = GardeContre");
			}
					
			int id = (new Scanner(System.in)).nextInt();
			switch (id)
			{
			case 0:
				c = Contrat.PASSE;
				break;
			case 1:
				c = Contrat.PETITE;
				break;
			case 2:
				c = Contrat.GARDE;
				break;
			case 3:
				c = Contrat.GARDE_SANS;
				break;
			case 4:
				c = Contrat.GARDE_CONTRE;
				break;
			default:
				System.out.println("Pas compris "+id+", disons Passe.");
				c = Contrat.PASSE;
			}
			if( c != Contrat.PASSE && c.getPoids() <= contrat.getPoids() )
			{
				System.out.println(c.getPoids()+","+contrat.getPoids()+id);
				mauvais_Contrat=true;
				System.out.println("Votre choix est invalide veuillez le refaire");
			}
			else
			{
				mauvais_Contrat=false;
			}
		}	
		return c;
	}
	
	public Carte demanderCarte()
	{
		int num;
		Scanner sc = new Scanner(System.in);
		majMain();
		Vector<Carte> carteslegales = P.donne().indiquerCartesLegalesJoueur();
		boolean choixtoutescartes = false; // pour choisir entre toutues les cartes de la main ou justes les carte legales
		
		if(choixtoutescartes)
		{
			do
			{
				main.affiche();
				afficheCarteLegales();
				System.out.println(nom + ", jouez une carte entre 1 et "+P.donne().getMain().nbCartesRestantes());
				
				num = sc.nextInt()-1;
				if(num < 0 || num >= main.nbCartesRestantes())
				{
					System.out.println("… entre 1 et "+ main.nbCartesRestantes()+" !");
				}
				
			} while(num < 0 || num >= main.nbCartesRestantes());
			// main.getCarte(num).affiche();
			return main.getCarte(num);
		}
		else 
		{
			do
			{
				main.affiche();
				afficheCarteLegales();				
				System.out.println(nom + ", jouez une carte entre 1 et "+carteslegales.size());
				
				num = sc.nextInt()-1;
				if(num < 0 || num >= carteslegales.size())
				{
					System.out.println("… entre 1 et "+ carteslegales.size()+" !");
				}
				
			} while(num < 0 || num >= carteslegales.size());
			// carteslegales.get(num).affiche();
		}
		return carteslegales.get(num);
	}
	
	private Vector<Carte> afficheCarteLegales()
	{
		Vector<Carte> cartesLegales;
		cartesLegales = P.donne().indiquerCartesLegalesJoueur();
		System.out.println("Vos cartes légales sont : ");
		for(Carte c: cartesLegales)
		{
			c.affiche();
		}
		System.out.println();
		return cartesLegales;
	}
// velizar.vesselinov@gmail.com	
	public JoueurTexte(String nom)
	{
		this.nom = nom;
		this.quiet = false;
		this.main = new Main();
	}
	
	public JoueurTexte(String nom, boolean quiet)
	{
		this(nom);
		this.quiet = quiet;
	}

	public void setNomDuJoueur(String s) 
	{
		this.nom = s;
	}

	public String getNomDuJoueur() 
	{
		return this.nom;
	}
	
	public String toString()
	{
		return "Joueur "+ this.nom;
	}

	/* **********************************************
	 * 
	 * Les méthodes demander…() servent à connaître le choix d’un joueur.
	 * (ex. choix d’une carte, d’une annonce, de l’écart…)
	 * 
	 * TODO: réorganiser et renommer certaines méthodes
	 * 
	 * ********************************************* */
	
	public Vector<Carte> demanderEcart() 
	{
		majMain();
		Vector<Carte> ecart = new Vector<Carte>();
		System.out.println("Vous allez devoir choisir "+P.getnombreDeCartesPourLeChien()+" cartes à mettre dans le votre ecart");
		main.affiche();
		for(int i=0;i < P.getnombreDeCartesPourLeChien();i++)
		{
			 ecart.add(demanderUneCartePourLecart());
		}
		
		return ecart;
	}

	public Carte demanderUneCartePourLecart() 
	{
		// TODO : à modifier pour ne pas supprimer les cartes
		int num;
		Scanner sc = new Scanner(System.in);
		Carte c = null;
		if(main == null || main.nbCartesRestantes() == 18) majMain(); // ?? C’est pour quoi ce test ?
		do
		{
			System.out.println("Mettez une carte à l'ecart en donnant un chiffre entre 1 et "+ main.nbCartesRestantes());
			main.affiche();
			num = sc.nextInt()-1;
			main.getCarte(num).affiche();
			if(num < 0 || num >= main.nbCartesRestantes())
			{
				System.out.println("… entre 1 et "+main.nbCartesRestantes()+" !");
			}
			if(!P.verificationCarteEcartValide(main.getCarte(num)))
			{
				System.out.println("Carte invalide !");
			}
			
		} while((num < 0 || num >= main.nbCartesRestantes()) && !P.verificationCarteEcartValide(main.getCarte(num)));
		c = main.getCarte(num);
		
		return c;
	}
	
	public Carte demanderAppelAuRoi()
	{
		Carte Roi = null;
		System.out.println("Donnez la couleur du roi que vous voulez appeler");
		int id=-1;
		while(id<=0 || id >4)
		{
			System.out.println(" Vos Choix :  1 = coeur, 2 = pique, 3 = treffle, 4 = carreau");
			id = (new Scanner(System.in)).nextInt();
			switch (id)
			{
			case 1:
				System.out.println("Vous avez appelé le roi de coeur");
				Roi = new Carte(Couleur.Coeur, 14);
				break;
			case 2:
				System.out.println("Vous avez appelé le roi de pique");
				Roi = new Carte(Couleur.Pique, 14);
				break;
			case 3:
				System.out.println("Vous avez appelé le roi de treffle");
				Roi = new Carte(Couleur.Trefle, 14);
				break;
			case 4:
				System.out.println("Vous avez appelé le roi de carreau");
				Roi = new Carte(Couleur.Carreau, 14);
				break;
			default:
				//! il faudrait lancer une exception
				System.out.println("Entree incorrecte, veuillez ressayer");
			}
		}
		return Roi;
	}

	/* **********************************************
	 * 
	 * Les méthodes dire…() servent à informer le joueur d’un événement.
	 * (ex. carte jouée, annonce d’un joueur, dévoilement du chien…)
	 * 
	 * ********************************************* */
	
	public void direChien(Vector<Carte> chien)
	{
		if(!quiet)
		{
			System.out.println(">> Chien :");
			for(Carte c: chien)
				c.affiche();
			System.out.println();
		}
	}
	
	public void direCarteJouee(Carte c, int j)
	{
		if(!quiet)
		{
			System.out.println(">> " + j + " joue " + c);
		}
	}
	
	// TODO: Il faudrait peut-être que ça indique aussi qui ouvrait et quelle carte a remporté le pli
	public void direPliRemporté(Vector<Carte> pli, int joueur)
	{
		if(!quiet)
		{
			System.out.print(">> Pli ");
			for(Carte c: pli)
			{
				c.affiche();
			}
			System.out.println(" remporté par " + joueur);
		}
	}
	
	public void direAnnonce(Contrat c, int joueur)
	{
		if(!quiet)
		{
			System.out.println(">> " + joueur + " annonce " + c);
		}
	}


	public void recevoirMain(Cartes c) {
		for(int i=0; i<c.size();i++)
		{
			main.addCarte(c.getcarte(i));
		}
			
		
	}


	public void direMain(Vector<Carte> m) {
		main.getCartes().addAll(m);
		System.out.print("Joueur "+nom+" > ");
		main.affiche();
	}

	public void direScore() {
		// TODO Auto-generated method stub
		
	}

	public void recupererScores() {
		// TODO Auto-generated method stub
		
	}

	public void direPliRemporté1(Carte[] pli, String joueur) {
		// TODO Auto-generated method stub
		
	}

	public void direScore(Vector<Integer[]> scores)
	{
		// TODO Auto-generated method stub
		
	}


}
