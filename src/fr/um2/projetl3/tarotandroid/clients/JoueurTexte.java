package fr.um2.projetl3.tarotandroid.clients;

import java.util.Scanner;

import android.R.string;

import fr.um2.projetl3.tarotandroid.jeu.*;
import static fr.um2.projetl3.tarotandroid.jeu.Context.*;

public class JoueurTexte implements IJoueur // implements Joueur (quand Joueur era une interface)
{
	private int pID;
	private Main pMain;
	private String nom;
	private boolean quiet = false; // comme on teste pour l’instant avec quatre JoueurTexte, 
								   // quiet permet de pas afficher tout quatre fois (un seul a quiet à true)

	public void setID(int pID)
	{
		this.pID = pID;
	}

	public Main getMain()
	{
		return pMain;
	}
	
	public void setMain(Main pMain)
	{
		this.pMain = pMain;
	}
	
	public String nom()
	{
		return nom;
	}

	public Contrat demanderAnnonce(Contrat contrat)
	{
		Contrat c = null;
		int compteur=0;//pour que le joueur ne puisse pas rentrer plus de 5 fois une mauvaise garde
		boolean mauvais_Contrat=true;
		System.out.println("À vous de parler :");
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
	/**/
	public Carte demanderCarte()
	{
		int num;
		Scanner sc = new Scanner(System.in);
		do
		{
			pMain.affiche();
			System.out.println("Jouez une carte en donnant un chiffre entre 1 et "+pMain.nbCartesRestantes());
			num = sc.nextInt();
			if(num < 0 || num >= pMain.nbCartesRestantes())
			{
				System.out.println("… entre 1 et "+pMain.nbCartesRestantes()+" !");
			}
		} while(num < 0 || num >= pMain.nbCartesRestantes());
		
		return pMain.getCarte(num);
	}/**/
	
	public JoueurTexte(String nom)
	{
		this.nom = nom;
	}
	
	public JoueurTexte(int pid, String nom)
	{
		pID = pid;
		this.nom = nom;
	}
	
	public JoueurTexte(String nom, boolean quiet)
	{
		this.nom = nom;
		this.quiet = quiet;
	}


	public int getID() 
	{
		return pID;
	}

	public void setNomDuJoueur(String s) 
	{
		// TODO Auto-generated method stub
		this.nom = s;
	}


	public String getNomDuJoueur() 
	{
		// TODO Auto-generated method stub
		return this.nom;
	}

	public void addChienDansMain(Carte[] chien) 
	{
		// TODO Auto-generated method stub
		for(Carte c:chien)
		{
			pMain.addCarte(c);
		}
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
		Carte ecart[] = new Carte[P.getnombreDeCartesPourLeChien()];
		System.out.println("Vous allez devoir choisir "+P.getnombreDeCartesPourLeChien()+" cartes à mettre dans le votre ecart");
		pMain.affiche();
		for(int i=0;i < P.getnombreDeCartesPourLeChien();i++)
		{
			ecart[i] = demananderUneCartePourLecart();
		}
		return ecart;
	}

	private Carte demananderUneCartePourLecart() 
	{
	
		int num;
		Scanner sc = new Scanner(System.in);
		do
		{
			System.out.println("Mettez une carte à l'ecart en donnant un chiffre entre 1 et "+pMain.nbCartesRestantes());
			num = sc.nextInt();
			if(num < 0 || num >= pMain.nbCartesRestantes())
			{
				System.out.println("… entre 1 et "+pMain.nbCartesRestantes()+" !");
			}
		} while(num < 0 || num >= pMain.nbCartesRestantes());
		
		return pMain.getCarte(num);
		
	}
	
	
	public Carte appelerRoi()
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
				System.out.println(" Vous avez appele le roi de coeur");
				Roi = new Carte(Couleur.Coeur, 14);
				break;
			case 2:
				System.out.println("Vous avez appele le roi de pique");
				Roi = new Carte(Couleur.Pique, 14);
				break;
			case 3:
				System.out.println(" Vous avez appele le roi de treffle");
				Roi = new Carte(Couleur.Trefle, 14);
				break;
			case 4:
				System.out.println(" Vous avez appele le roi de carreau");
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
		return pMain.roiDansLaMain(roi);
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
