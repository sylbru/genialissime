package fr.um2.projetl3.tarotandroid.activities;

import static fr.um2.projetl3.tarotandroid.activities.Contexts.TAG;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import fr.um2.projetl3.tarotandroid.R;
import fr.um2.projetl3.tarotandroid.exceptions.CarteUIDInvalideException;
import fr.um2.projetl3.tarotandroid.jeu.CarteGraphique;

public class EcranJeu extends Activity
{
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		ImageView imgV = new ImageView(this);
		try
		{
			// imgV.setImageDrawable(Drawable.createFromStream(applicationContext.getAssets().open("cartes/42.png"), null));
			imgV.setImageDrawable((new CarteGraphique(42)).mImageView.getDrawable());
		}
		catch (CarteUIDInvalideException e)
		{
			e.printStackTrace();
		}
		
		setContentView(R.layout.ecran_jeu);
		RelativeLayout rl = (RelativeLayout) findViewById(R.id.mainLayout);
		
		LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		
		rl.addView(imgV, lp);
		
		Log.d(TAG, "h="+imgV.getHeight()+","+imgV.getWidth());
		
		setContentView(rl);
	}
	
}
