package fr.um2.projetl3.tarotandroid.clients;

import static fr.um2.projetl3.tarotandroid.jeu.Context.D;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Vector;
import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;
import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.content.res.AssetManager;
import fr.um2.projetl3.tarotandroid.jeu.Carte;
import fr.um2.projetl3.tarotandroid.jeu.Contrat;
import fr.um2.projetl3.tarotandroid.jeu.Main;

public class JoueurIA implements IJoueur
{
	/*--- Attributs ---*/
	private Main pMain;		// Peut-être inutile
	private String pNom;	// Nom du joueur, arbitraire et sans incidence
	private LuaState L;		// Instance de la machine virtuelle Lua
	
	/*--- Constructeurs ---*/
	public JoueurIA(String pNom, XmlPullParser iaDefaut, int pID)
	{
		//AssetManager am = fr.um2.projetl3.tarotandroid.activities.Contexts.applicationContext.getAssets();
		L = LuaStateFactory.newLuaState();
		L.openLibs();
		
		try {
			InputStream is = fr.um2.projetl3.tarotandroid.activities.Contexts.applicationContext.getAssets().open("luascripts/default.lua");
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			String script = new String(buffer);
			System.out.println(script);
			L.LdoString(script);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		this.pNom = pNom;
		/*try {
			XmlPullParser xpp = iaDefaut;
			while (xpp.getEventType()!=XmlPullParser.END_DOCUMENT) {
				if (xpp.getEventType()==XmlPullParser.START_TAG){
					if(xpp.getName().equals("luascript")){
						//s = L.getLuaObject("s").getString()+"\n"+xpp.getAttributeValue(0);
						//System.out.println(xpp.getAttributeValue(null, "executer"));
						if (xpp.getAttributeValue(null, "executer").equals(new String("true")))
						{
							//System.out.println("Did lua");
							L.LdoString(xpp.getAttributeValue(null, "lua"));
						} else {
							//System.out.println("Skipped");
						}
						
					} else {
						//s = xpp.getName()+" Echoué";
					}
				}
				xpp.next();
			}
		} catch (Throwable t) {
		}*/
		
	}
	
	/*--- Fluxus ---*/
	public String popFluxus()
	{
		String s;
		
		try
		{
			L.LdoString("s = fluxus:pop()");
			s = L.getLuaObject("s").getString();
		}
		catch (Throwable t)
		{
			s = "Echec d'appel de Fluxus";
		}
		
		return s;
	}
	
	public String checkFluxus()
	{
		String s;
		
		try
		{
			L.LdoString("s = fluxus[1]");
			s = L.getLuaObject("s").getString();
		}
		catch (Throwable t)
		{
			s = "Echec d'appel de Fluxus";
		}
		
		return s;
	}
	
	public Boolean fluxusVide()
	{
		Boolean b;
		
		try
		{
			L.LdoString("b = fluxus:isEmpty()");
			b = L.getLuaObject("b").getBoolean();
		}
		catch (Throwable t)
		{
			b = true;
		}
		
		return b;
	}
	
	/*--- Gestion des paramètres du joueur ---*/
	public void setNomDuJoueur(String s)
	{
		this.pNom = "s";
	}

	public String getNomDuJoueur() 
	{
		return this.pNom;
	}

	/*--- Methodes "demander" ---*/
	public Contrat demanderAnnonce(Contrat contrat)
	{
		//this.updateLObjects();
		//this.chargerLegal();
		this.chargerMain();
		L.LdoString("cont,flal = tarot.demander.annonce()");
		int c = (int) L.getLuaObject("cont").getNumber();
		//int c = (int) Math.floor(Math.random()*5);
		System.out.println("Je m'appelle "+this.pNom+".\nMa main est ");
		D.getMain().affiche();
		System.out.println("et je fais le contrat numero "+c);
		int flal = (int) L.getLuaObject("flal").getNumber();
		System.out.println("Mon flal est "+flal);
		switch (c){
		case 0:
			return Contrat.PETITE;
		case 1:
			return Contrat.PETITE;
		case 2:
			return Contrat.GARDE;
		case 3:
			return Contrat.GARDE_SANS;
		case 4:
			return Contrat.GARDE_CONTRE;
		default:
			return Contrat.PASSE;
		}
		
	}
	
	public Vector<Carte> demanderEcart()
	{
		//this.updateLObjects();
		this.chargerMain();
		L.LdoString("ecart = tarot.demander.ecart()");
		int c;
		Vector<Carte> ecart = new Vector<Carte>();
		//System.out.println("Mon écart qui tue:");
		for (int i=1; i<=6; i++)
		{	
			L.LdoString("c = ecart:pop()");
			c = (int) L.getLuaObject("c").getNumber();
			//c = (int) Math.floor(Math.random()*78);
			//System.out.println(new Carte(c).toString());
			ecart.add(new Carte(c));
		}
		return ecart;
	}
	
	private void chargerMain(){
		L.LdoString("tarot.main:clear()");
		//System.out.println("Je suis "+pNom+" et je demande ma main");
		Vector<Carte> vCartes = D.getMain().getCartes();
		for (int i=0;i<vCartes.size();i++)
		{
			try {
				L.pushObjectValue(vCartes.elementAt(i).uid());
				L.setGlobal("input");
				L.LdoString("tarot.main:push(input)");
				//System.out.println("Pushed a "+vCartes.elementAt(i).toString());
			} catch (LuaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void chargerLegal(){
		L.LdoString("tarot.legal:clear()");
		Vector<Carte> vCartes = D.indiquerCartesLegalesJoueur();
		for (int i=0;i<vCartes.size();i++)
		{
			try {
				L.pushObjectValue(vCartes.elementAt(i).uid());
				L.setGlobal("input");
				L.LdoString("tarot.legal:push(input)");
				//System.out.println("Pushed a "+vCartes.elementAt(i).toString());
			} catch (LuaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void chargerPli(){
		L.LdoString("tarot.pli:clear()");
		Vector<Carte> vCartes = new Vector<Carte>();
		//System.out.println("Chargement du pli");
		for (int i=0; i<4; i++)
		{
			//System.out.println("Carte du pli numero "+i);
			if (D.getPlisEnCours().get(i) != null)
			{
				//System.out.println(D.getPlisEnCours()[i].toString());
				vCartes.add(D.getPlisEnCours().get(i));
			}
		}
		
		for (int i=0;i<vCartes.size();i++)
		{
			try {
				if (vCartes.elementAt(i) != null)
				{
					//System.out.println("Poussage de "+i);
					L.pushObjectValue(vCartes.elementAt(i).uid());
					L.setGlobal("input");
					L.LdoString("tarot.pli:push(input)");
				}
				//System.out.println("Pushed a "+vCartes.elementAt(i).toString());
			} catch (LuaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public Carte demanderCarte()
	{
		this.chargerLegal();
		int c;
		//this.updateLObjects();
		L.LdoString("c = tarot.demander.carte()");
		c = (int) L.getLuaObject("c").getNumber();
		//D.getMain().affiche();
		System.out.println(this.pNom+" "+new Carte(c).toString());
		return new Carte(c);
	}
	
	public Carte demanderAppelAuRoi()
	{
		this.updateLObjects();
		// TODO 
		return null;
	}	
	
	/*--- Methodes "dire" ---*/

	public void direChien(Vector<Carte> chien)
	{
		L.LdoString("tarot.chien:clear()");
		for(Iterator<Carte> it=chien.iterator();it.hasNext(); )
		{
			L.LdoString("tarot.chien:push("+it.next().uid()+")");
		}
	}

	public void direCarteJouee(Carte c, int j)
	{
		// TODO Auto-generated method stub
		
		
	}
	
	public void direAnnonce(Contrat c, int j)
	{
		// TODO Auto-generated method stub
		
	}

	public void direPliRemporté(Vector<Carte> pli, int joueur)
	{
		// TODO Auto-generated method stub
		
	}

	public void direMain(Vector<Carte> m) {
		//int icartes[] = new int[m.size()];
		
		L.LdoString("tarot.main.clear()");
				for (int i=0;i<m.size();i++)
		{
			try {
				L.pushObjectValue(m.elementAt(i).uid());
			} catch (LuaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			L.setGlobal("input");
			L.LdoString("tarot.main.push(input)");
		}
				
		//L.LdoString("tarot.dire.main(tarot.main)");
		//System.out.println(new Main(m).toString());

		
		// TODO Auto-generated method stub
		
	}

	public void direScore() {
		// TODO Auto-generated method stub
		
	}
	
	private void updateLObjects()
	{
		this.chargerMain();
		//this.chargerLegal();
		this.chargerPli();
		this.direAnnonce(D.getContratEnCours(), D.getPreneur());
	}
	
	@Override
	public String toString()
	{
		return pNom;
	}

}