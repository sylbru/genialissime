package fr.um2.projetl3.tarotandroid.clients;

import java.util.Scanner;

import fr.um2.projetl3.tarotandroid.jeu.*;

public class JoueurTexte extends Joueur // implements Joueur (quand Joueur sera une interface)
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

	public Contrat demanderAnnonce()
	{
		System.out.println("À vous de parler :");
		System.out.println("0 = Passe, 1 = Petite, 2 = Garde, 3 = Garde sans, 4 = GardeContre");

		Contrat c;
		
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

}
