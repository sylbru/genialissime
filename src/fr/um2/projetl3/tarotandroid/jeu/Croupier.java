package fr.um2.projetl3.tarotandroid.jeu;

import static fr.um2.projetl3.tarotandroid.jeu.Context.P;

import java.util.Vector;

import fr.um2.projetl3.tarotandroid.clients.IJoueur;

public class Croupier {

	/**
	 * @author JB
	 * 
	 *  méthode qui donne la iéme main au iéme joueur
	 * 
	 * @param i
	 */
	void direMain(int i)
	{
		P.getJoueur(i).direMain(P.donne().getMain(i).getCartes());
	}
	
	/**
	 * @author JB
	 * méthode qui donne les mains à tous les joueur
	 */
	void direMains()
	{
		int nbJoueur = P.getNombreDeJoueurs();
		for(int i=0;i<nbJoueur;i++)
		{
			direMain(i);
		}
	}
	
	/**
	 * @author JB
	 * 
	 * Dit le chien à tout le monde 
	 * inclure donner le chien au preneur ?
	 * 
	 */
	void reveleChien(Vector<Carte> chien)
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
	public void direJoueursCarteJouee(Carte c, int joueur)
	{
		int pos = 0; 
		for(IJoueur j: P.getJoueurs())
		{
			j.direCarteJouee(c, ((pos-joueur)+P.getNombreDeJoueurs())%P.getNombreDeJoueurs());
		}
	}
	/**
	 * @author niavlys
	 * Informe les joueurs du fait qu’un pli a été remporté
	 * @param pli le contenu du pli remporté
	 * @param joueur Le joueur qui a remporté le pli
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
			j.direPliRemporté(vPli, ((pos-joueur)+P.getNombreDeJoueurs())%P.getNombreDeJoueurs());
		}
	}
	
	public Carte demanderCarteJoueur(int num)
	{
		Carte carteProposee;

			System.out.println("demande carte joueur croupier");
			System.out.println("Demandons au joueur "+num+ " soit "+(num%P.getNombreDeJoueurs()));
			carteProposee = P.getJoueur(num%P.getNombreDeJoueurs()).demanderCarte();
			System.out.println(P.getJoueur(num%P.getNombreDeJoueurs()).getNomDuJoueur()+" "+carteProposee.toString());

		return carteProposee;
	}
	
	public Croupier(){}

}
