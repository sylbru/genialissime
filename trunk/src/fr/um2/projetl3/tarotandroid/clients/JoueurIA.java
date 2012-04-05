package fr.um2.projetl3.tarotandroid.clients;

import static fr.um2.projetl3.tarotandroid.jeu.Context.P;
import static fr.um2.projetl3.tarotandroid.jeu.Context.D;

import java.util.Vector;

import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaObject;
import org.keplerproject.luajava.LuaStateFactory;
import org.xmlpull.v1.XmlPullParser;

import android.util.Log;

import fr.um2.projetl3.tarotandroid.connection.Cartes;
import fr.um2.projetl3.tarotandroid.jeu.Carte;
import fr.um2.projetl3.tarotandroid.jeu.Contrat;
import fr.um2.projetl3.tarotandroid.jeu.Couleur;
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
		this.pNom = pNom;
		L = LuaStateFactory.newLuaState();
		L.openLibs();
		try {
			XmlPullParser xpp = iaDefaut;
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
		}
	}
	
	/*--- Fluxus ---*/
	public String popFluxus()
	{
		String s;
		
		try
		{
			L.LdoString("s = fluxus.pop()");
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
			L.LdoString("b = fluxus.isEmpty()");
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
		// TODO Auto-generated method stub
		return this.pNom;
	}

	/*--- Methodes "demander" ---*/
	public Contrat demanderAnnonce(Contrat contrat)
	{
		L.LdoString("cont = tarot.demander.annonce()");
		int c = (int) L.getLuaObject("cont").getNumber();
		System.out.println(c);
		switch (c){
		case 0:
			return Contrat.PASSE;
		case 1:
			return Contrat.GARDE_SANS;
		case 2:
			return Contrat.GARDE_CONTRE;
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
		L.LdoString("ecart = tarot.demander.ecart()");
		Vector<Carte> ecart = new Vector<Carte>();
		int cecart[] = new int[6];
		try {
			cecart = (int[]) L.getLuaObject("ecart").getObject();
		} catch (LuaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i=0; i<6; i++)
		{
			ecart.add(new Carte(cecart[i]));
		}
		
		return ecart;
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
		System.out.println("Chargement du pli");
		for (int i=0; i<4; i++)
		{
			System.out.println("Carte du pli numero "+i);
			if (D.getPlisEnCours()[i]!=null)
			{
				System.out.println(D.getPlisEnCours()[i].toString());
				vCartes.add(D.getPlisEnCours()[i]);
			}
		}
		
		for (int i=0;i<vCartes.size();i++)
		{
			try {
				if (vCartes.elementAt(i) != null)
				{
					System.out.println("Poussage de "+i);
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
		int c;
		
		chargerLegal();
		chargerPli();
		L.LdoString("c = tarot.demander.carte()");
		c = (int) L.getLuaObject("c").getNumber();
		//System.out.println(this.pNom+" "+new Carte(c).toString());
		return new Carte(c);
	}
	
	public Carte demanderRoi()
	{
		//! TODO Méthode inutile à mon humble avis
		return null;
	}	
	
	/*--- Methodes "dire" ---*/

	public void direChien(Vector<Carte> chien)
	{
		
		
	}

	public void direCarteJouee(Carte c, String j)
	{
		// TODO Auto-generated method stub
		
		
	}
	
	public void direAnnonce(Contrat c, String j)
	{
		// TODO Auto-generated method stub
		
	}

	public void direPliRemporté(Vector<Carte> pli, String joueur)
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
				
		L.LdoString("tarot.dire.main(tarot.main)");
		System.out.println(new Main(m).toString());

		
		// TODO Auto-generated method stub
		
	}

	public void direScore() {
		// TODO Auto-generated method stub
		
	}
	
	private void updateLObjects()
	{
		
	}

}
