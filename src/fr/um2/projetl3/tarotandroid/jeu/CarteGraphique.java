package fr.um2.projetl3.tarotandroid.jeu;

import java.lang.reflect.Field;

import android.R;
import android.widget.ImageView;
import fr.um2.projetl3.tarotandroid.exceptions.CarteUIDInvalideException;

public class CarteGraphique extends Carte{

	Field[] fields = R.drawable.class.getFields();
	ImageView CardsView[] = new ImageView[fields.length - 1];
	
	public CarteGraphique(int ordreAtout) {
		super(ordreAtout);
	}
	
	public CarteGraphique(Couleur couleur, int ordre){
		//carteCouleur = true;
		super(couleur, ordre);
	}
	
	public CarteGraphique(boolean pourUid, int uid) throws CarteUIDInvalideException{
		super(pourUid, uid);
		int currentResId = 0;
		try {
			currentResId = fields[uid].getInt(R.drawable.class);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        CardsView[uid].setImageResource(currentResId);
	}
	
	public void affiche(int uid)
	{
		CardsView[uid] = new ImageView(null);
	}

}
