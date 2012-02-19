package fr.um2.projetl3.tarotandroid.jeu;

abstract public class Joueur
{
	// ! Classe incompl�te, ne pas encore utiliser cette interface!
	// TODO Finir l'interface Joueur
	// TODO En faire une vraie interface (au sens Java) [?]
	public abstract void setID(int pID);
	public abstract int getID();

	public abstract void setMain(Main pMain);


	public abstract Contrat demanderAnnonce(Contrat contrat);
	
	public abstract Carte demanderCarte();
	
	public abstract String nom();

}
