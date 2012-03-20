package fr.um2.projetl3.tarotandroid.jeu;

import static fr.um2.projetl3.tarotandroid.activities.Contexts.applicationContext;

import java.io.IOException;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.Toast;
import fr.um2.projetl3.tarotandroid.exceptions.CarteUIDInvalideException;

public class CarteGraphique extends Carte{

	public ImageView mImageView;
	
	/*
	 * Une fonction permettant de donner à chaque ressource (images des cartes) 
	 * dont j'ai besoin, le bon id.
	 * ex : atout 2 -> id 2 (correspondant à l'uid bien sur)
	 */
	
	public CarteGraphique(Couleur couleur, int ordre){
		//carteCouleur = true;
		super(couleur, ordre);
	}
	
	public CarteGraphique(int uid) throws CarteUIDInvalideException{
		super(uid);
		//mImageViews[uid].setImageResource(uid);
		mImageView = new ImageView(applicationContext);
		
		AssetManager am = applicationContext.getAssets();
		try
		{
			Drawable d = Drawable.createFromStream(am.open("cartes/" + uid + ".png"), null);
			mImageView.setImageDrawable(d);
		} catch (IOException e)
		{
			Toast.makeText(applicationContext, "Fail avec uid "+uid, 200).show();
			e.printStackTrace();
		}
	}
	
	public void affiche()
	{
		// Toast.makeText(applicationContext, mImageViews[uid()].toString(), 200).show();
	}

}
