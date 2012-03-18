package fr.um2.projetl3.tarotandroid.jeu;

import java.lang.reflect.Field;

import android.R;
import android.R.drawable;
import android.widget.ImageView;
import fr.um2.projetl3.tarotandroid.exceptions.CarteUIDInvalideException;

public class CarteGraphique extends Carte{

	ImageView mImageViews[] = new ImageView[78];
	/*
	 * Une fonction permettant de donner à chaque ressource (images des cartes) 
	 * dont j'ai besoin, le bon id.
	 * ex : atout 2 -> id 2 (correspondant à l'uid bien sur)
	 */
	
	public CarteGraphique(int ordreAtout) {
		super(ordreAtout);
	}
	
	public CarteGraphique(Couleur couleur, int ordre){
		//carteCouleur = true;
		super(couleur, ordre);
	}
	
	public CarteGraphique(boolean pourUid, int uid) throws CarteUIDInvalideException{
		super(pourUid, uid);
		mImageViews[uid].setImageResource(uid);
	}
	
	public void affiche(int uid)
	{
	}

}
