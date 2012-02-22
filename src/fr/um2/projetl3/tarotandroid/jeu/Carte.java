package fr.um2.projetl3.tarotandroid.jeu;


public interface Carte
{
	boolean isCouleur();
	boolean isAtout();
	boolean isExcuse();
	boolean isBout();
	void affiche();
	int valeur(); // en demi-points
	
	
	// TODO: méthodes concernant les relations entre les cartes (?)
	// Exemple : est-ce que cette carte est supérieure à celle-ci, est-elle de la même couleur que celle-ci ?
	// (À voir si on peut et si c’est bien de les mettre ici.)
}
