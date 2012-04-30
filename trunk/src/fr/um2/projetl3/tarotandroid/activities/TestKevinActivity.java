package fr.um2.projetl3.tarotandroid.activities;

import static fr.um2.projetl3.tarotandroid.jeu.Context.P;
//import android.R;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import fr.um2.projetl3.tarotandroid.R;

import fr.um2.projetl3.tarotandroid.clients.JoueurIA;
import fr.um2.projetl3.tarotandroid.jeu.Carte;
import fr.um2.projetl3.tarotandroid.jeu.Partie;

public class TestKevinActivity extends Activity {
	JoueurIA testy;
	JoueurIA testy2;
	JoueurIA testy3;
	JoueurIA testy4;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		testy = new JoueurIA("Archimède", 3);
		testy2 = new JoueurIA("Bertrand", 3);
		testy3 = new JoueurIA("Clovis", 3);
		testy4 = new JoueurIA("Dartagnan", 3);
				super.onCreate(savedInstanceState);
		setContentView(R.layout.kevlayout);
		//testy = new JoueurIA("Archimède", getResources().getXml(R.xml.intelligence), 3);
		/*final LuaState L = LuaStateFactory.newLuaState();
		L.openLibs();
		try {
			L.pushObjectValue(testy);
		} catch (LuaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		L.setGlobal("testy");
		*/
		final Button boutonTest = (Button) findViewById(R.id.kevsbutton1);
		boutonTest.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				P = new Partie();
				
				P.lancerPartie4JoueursIA(testy, testy2, testy3, testy4);
				Partie.testPartie(testy, testy, testy, testy);
				
				String s = "Pas reçu d'objet depuis Lua";
				while (!testy.fluxusVide())
				{	
					System.out.println("Pop");
					Toast.makeText(getBaseContext(),
        				testy.popFluxus(),Toast.LENGTH_SHORT).show();
				}
				boutonTest.setTextSize(20);
				boutonTest.setText(s);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Rien
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return true;
		// Rien
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		// useless atm
	}

}
