package fr.um2.projetl3.tarotandroid.clients;

import java.util.Scanner;

//import android.R.string;

import fr.um2.projetl3.tarotandroid.jeu.*;
import static fr.um2.projetl3.tarotandroid.jeu.Context.*;

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
	
	// TODO: La vérification de la validité du contrat ne doit pas se faire ici mais dans Annonces
	public Contrat demanderAnnonce(Contrat contrat)
	{
		Contrat c = null;
		int compteur=0;//pour que le joueur ne puisse pas rentrer plus de 5 fois une mauvaise garde
		boolean mauvais_Contrat=true;
		System.out.println(nom + ", à vous de parler :");
		while(mauvais_Contrat && compteur<5)
		{
			compteur++;
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
		if(compteur==5)
		{
			c = Contrat.PASSE;
			System.out.println("trop de mauvais choix, Passe par defaut");
		}
			
		return c;
	}
	
	public Carte demanderCarte()
	{
		int num;
		Scanner sc = new Scanner(System.in);
		majMain();
		do
		{
			main.affiche();
			//indiquerCartesLegalesJoueur(num); // !! Cette fonction ne fait rien d'utile pour l'instant
			System.out.println("Jouez une carte en donnant un chiffre entre 1 et "+P.donne().getMain().nbCartesRestantes());
			num = sc.nextInt()-1;
			if(num < 0 || num >= main.nbCartesRestantes())
			{
				System.out.println("… entre 1 et "+ main.nbCartesRestantes()+" !");
			}
		} while(num < 0 || num >= main.nbCartesRestantes());
		main.getCarte(num).affiche();
		return P.donne().getMain().getCarte(num);
	}
	
	public JoueurTexte(String nom)
	{
		this.nom = nom;
		this.quiet = false;
	}
	
	public JoueurTexte(String nom, boolean quiet)
	{
		this.nom = nom;
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
	
	public Carte[] demanderEcart() 
	{
		majMain();
		Carte ecart[] = new Carte[P.getnombreDeCartesPourLeChien()];
		System.out.println("Vous allez devoir choisir "+P.getnombreDeCartesPourLeChien()+" cartes à mettre dans le votre ecart");
		main.affiche();
		for(int i=0;i < P.getnombreDeCartesPourLeChien();i++)
		{
			ecart[i] = demanderUneCartePourLecart();
		}
		return ecart;
	}

	private Carte demanderUneCartePourLecart() 
	{
		int num;
		Scanner sc = new Scanner(System.in);
		majMain();
		do
		{
			System.out.println("Mettez une carte à l'ecart en donnant un chiffre entre 1 et "+ main.nbCartesRestantes());
			num = sc.nextInt();
			System.out.println("numero entrez : "+  num);
			if(num <= 0 || num > main.nbCartesRestantes())
			{
				System.out.println("… entre 1 et "+main.nbCartesRestantes()+" !");
			}
		} while(num <= 0 || num > main.nbCartesRestantes());
		
		return main.getCarte(num-1);
	}
	
	public Carte demanderRoi()
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
	
	public boolean possedeRoi(Carte roi)
	{
		return main.roiDansLaMain(roi);
	}

	/* **********************************************
	 * 
	 * Les méthodes dire…() servent à informer le joueur d’un événement.
	 * (ex. carte jouée, annonce d’un joueur, dévoilement du chien…)
	 * 
	 * ********************************************* */
	
	public void direChien(Carte[] chien)
	{
		if(!quiet)
		{
			System.out.println("Chien :");
			for(Carte c: chien)
				c.affiche();
			System.out.println();
		}
	}
	
	public void direCarteJouee(Carte c, IJoueur j)
	{
		if(!quiet)
		{
			System.out.println(j.getNomDuJoueur() + " joue " + c);
		}
	}
	
	public void direAnnonce(Contrat c, IJoueur j)
	{
		if(!quiet)
		{
			System.out.println(j.getNomDuJoueur() + " annonce " + c);
		}
	}

}
