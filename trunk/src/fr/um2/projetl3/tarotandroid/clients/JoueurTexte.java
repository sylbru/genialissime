package fr.um2.projetl3.tarotandroid.clients;

import java.util.Scanner;

import android.R.string;

import fr.um2.projetl3.tarotandroid.jeu.*;

public class JoueurTexte implements Joueur // implements Joueur (quand Joueur era une interface)
{
	private int pID;
	private Main pMain;
	private String nom;

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
			if(c.getPoids()>contrat.getPoids() && c.getPoids()!=0)
			{
				mauvais_Contrat=true;
			}
			else
			{
				System.out.println("Votre choix est invalide veuillez le refaire");
			}
		}
		if(compteur==5)
		{
			c = Contrat.PASSE;
			System.out.println("trop de mauvais choix, Passe par default");
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


	public int getID() {
		return pID;
	}

	public void setNomDuJoueur(String s) {
		// TODO Auto-generated method stub
		this.nom = s;
	}


	public String getNomDuJoueur() {
		// TODO Auto-generated method stub
		return this.nom;
	}

	public void addChienDansMain(Carte[] chien) {
		// TODO Auto-generated method stub
		for(Carte c:chien)
		{
			pMain.addCarte(c);
		}
	}

	public Carte[] demanderEcart() {
		// TODO Auto-generated method stub
		return null;
	}
}
