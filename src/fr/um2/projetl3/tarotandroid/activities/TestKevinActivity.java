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
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		testy = new JoueurIA("Archimède", getResources().getXml(R.xml.intelligence), 3);
		JoueurIA testy2 = new JoueurIA("Bertrand", getResources().getXml(R.xml.intelligence), 3);
		JoueurIA testy3 = new JoueurIA("Clovis", getResources().getXml(R.xml.intelligence), 3);
		JoueurIA testy4 = new JoueurIA("Dartagnan", getResources().getXml(R.xml.intelligence), 3);
		P = new Partie();
		
		P.lancerPartie4JoueursIA(testy, testy2, testy3, testy4);
		//Partie.testPartie(testy, testy, testy, testy);
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
				//ImageView iv = new ImageView();
				
				String s = "Pas reçu d'objet depuis Lua";
				while (!testy.fluxusVide())
				{
					Toast.makeText(getBaseContext(),
        				testy.popFluxus(),Toast.LENGTH_SHORT).show();
				}
				/*if (!testy.fluxusVide())
				{
					s = testy.popFluxus();
				} else {
					Carte c = testy.demanderCarte();
					s = c.toString();
				}*/
				Carte c = testy.demanderCarte();
				s = c.toString();
				/*try {
					L.pushObjectValue(s);
				} catch (LuaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				L.setGlobal("s");*/
				//InputStream is = getResources().openRawResource(R.raw.test);
				//L.LdoFile("raw/test");  //getResources().getResourceName(R.raw.test)+".lua");
				/*try {
					s = "You what?";
					L.LdoString("s='start'");
					XmlPullParser xpp = getResources().getXml(R.xml.intelligence);
					while (xpp.getEventType()!=XmlPullParser.END_DOCUMENT) {
						if (xpp.getEventType()==XmlPullParser.START_TAG){
							if(xpp.getName().equals("luascript")){
								//s = L.getLuaObject("s").getString()+"\n"+xpp.getAttributeValue(0);
								L.LdoString(xpp.getAttributeValue(null, "lua"));
							} else {
								//s = xpp.getName()+" Echoué";
							}
						}
						xpp.next();
					}
				} catch (Throwable t) {
					s = t.toString();
				}*/
				//LuaObject fluxvide = L.getLuaObject("fluxus.estVide");
				//L.LdoString("s=test()");
				/*try {
					while (){
						L.LdoString("");
					}
				} catch (LuaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/

				
				//s = L.getLuaObject("s").toString();
				
				// I WAS HERE ! L.LdoString(xpp);
				
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
