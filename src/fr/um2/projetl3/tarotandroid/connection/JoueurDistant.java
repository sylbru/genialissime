package fr.um2.projetl3.tarotandroid.connection;

import java.io.IOException;
import static fr.um2.projetl3.tarotandroid.jeu.Context.P;
import static fr.um2.projetl3.tarotandroid.jeu.Context.D;
import java.net.*;

import fr.um2.projetl3.tarotandroid.clients.IJoueur;
import fr.um2.projetl3.tarotandroid.connection.Cartes;
import fr.um2.projetl3.tarotandroid.jeu.Carte;
import fr.um2.projetl3.tarotandroid.jeu.Contrat;
import fr.um2.projetl3.tarotandroid.jeu.Main;
import fr.um2.projetl3.tarotandroid.connection.Serverthread;
import fr.um2.projetl3.tarotandroid.exceptions.entreeNulleException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;

public class JoueurDistant implements IJoueur{
	MessageObjet message;
	Serverthread server;

	public JoueurDistant(ServerSocket serverSocket) throws IOException
	{
		server = new Serverthread(serverSocket.accept());
		server.start();
	}
	public void setNomDuJoueur(String s) {}
	public String getNomDuJoueur(){return null;}
	public void direMain(Main m)
	{
		boolean mainnonenvoie = true;
		while(mainnonenvoie)
		{
			
			try 
			{
				message = new MessageObjet(1,"envoi main initialisation");
				server.sendMessage(message);
				message = (MessageObjet) server.liremessage();
				if (message.getmessage()== 1)
				{
					Cartes c = null;
					c.set(m.getCartes());
					server.sendMessage(c);
					message = (MessageObjet) server.liremessage();
					mainnonenvoie = false;
					
				}
			} 
			catch (OptionalDataException e) 
			{
				e.printStackTrace();
			} 
			catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public Contrat demanderAnnonce(Contrat contrat){return null;}
	public Carte[] demanderEcart(){return null;}
	
	public Carte demanderCarte()
	{
		message = new MessageObjet(1,"premiere demande de carte");
		server.sendMessage(message);
		Carte c = null;
		try {
		   	 c = (Carte) server.liremessage();
		}
		catch (OptionalDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
		
	}
	public Carte demanderRoi(){return null;}
	public Carte demanderUneCartePourLecart(){return null;}
	
	public void direChien(Carte[] chien){}
	public void direCarteJouee(Carte c, String j){}
	public void direAnnonce(Contrat c, String j){}
	public void direPliRemporté(Carte[] pli, String joueur){}
	
	
	public void recupererMain() {
		// TODO Auto-generated method stub
	}
	public void recupererPliEnCours() {
		// TODO Auto-generated method stub
	}
	public void recupererPliPrecedent() {
		// TODO Auto-generated method stub
	}
	
	public void effectuerRequetteJoueur(MessageObjet o)
	{
		MessageObjet mess;
		if(o.getmessage()==1)
		{
			mess= new MessageObjet(0,"demandepli recu");
			server.sendMessage(mess);
			Cartes c = null;
			c.set(D.getPlisPrecedent());
			server.sendMessage(c);
 		}
		else if(o.getmessage()==2)
		{

 		}
		else if(o.getmessage()==2)
		{

 		}
		else 
		{
			mess= new MessageObjet(-1,"requette inconnue");
			server.sendMessage(mess);
		}
	}
	
}
