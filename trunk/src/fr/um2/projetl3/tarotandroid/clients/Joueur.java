package fr.um2.projetl3.tarotandroid.clients;

import fr.um2.projetl3.tarotandroid.jeu.Carte;
import fr.um2.projetl3.tarotandroid.jeu.Contrat;
import fr.um2.projetl3.tarotandroid.jeu.Main;

public interface Joueur
{
	/* 			Il faudrait creer une méthode qui permette à un joueur de faire son ecart 
	 * 			cette méthode devrait renvoyer l'ecart.
	 * 			
	 * 			il suffirait alors de faire juste un appel de cette méthode dans Partie
	 * 
	 * 
	 * 			il me semble que chaque joueur (IA joueur texte et client) vont gerer cette méthode differament 
	 * 			c'est pourquoi il me semble plus judicieux d ele mettre dans joueur
	 */
	
	
	// ! Classe incompl�te, ne pas encore utiliser cette interface!
	// TODO Finir l'interface Joueur
	// TODO En faire une vraie interface (au sens Java) [?]
	public abstract void setID(int pID);
	public abstract int getID();

	public abstract void setMain(Main pMain);
	public abstract void addChienDansMain(Carte[] chien);

	public abstract void setNomDuJoueur(String s);
	public abstract String getNomDuJoueur();

	public abstract Contrat demanderAnnonce(Contrat contrat);
	
	public abstract Carte demanderCarte();
	
	public abstract String nom();

}
