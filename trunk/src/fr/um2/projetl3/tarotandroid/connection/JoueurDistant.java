package fr.um2.projetl3.tarotandroid.connection;

import java.io.IOException;
import static fr.um2.projetl3.tarotandroid.jeu.Context.P;
import static fr.um2.projetl3.tarotandroid.jeu.Context.D;
import java.net.*;
import java.util.Vector;

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
	public void direMain(Vector<Carte> m)
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
					c.set(m);
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
	public Vector<Carte> demanderEcart(){return null;}
	
	public Carte demanderCarte()
	{
		Carte c = null;

		try {
			if((message = (MessageObjet) server.liremessage())!=null) // on commence par regarder si le joueur n'a pas fait une requette
			{
				effectuerRequetteJoueur(message);					//si oui on la traite
			}	
				message = new MessageObjet(1,"premiere demande de carte"); // sinon on demande leu jeu d'une carte
				server.sendMessage(message);
				message = (MessageObjet) server.liremessage(); 
				if(message.getmessage()!= 0)						//on controle si le joueur n'a pas fait un autre requette 0 signifie que le joueur a bien recu la demande
				{
					effectuerRequetteJoueur(message);
					message = (MessageObjet) server.liremessage();  // on lit si'il a bien recu la demande 
				}
				c = (Carte) server.liremessage();         // on attend la carte
				
			
		} catch (OptionalDataException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return c;
		
	}
	public Carte demanderRoi(){return null;}
	public Carte demanderUneCartePourLecart(){return null;}
	
	public void direChien(Vector<Carte> chien){}
	public void direCarteJouee(Carte c, String j){}
	public void direAnnonce(Contrat c, String j){}
	public void direPliRemporté(Vector<Carte> pli, String joueur){}
	
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
	public void direScore() {
		// TODO Auto-generated method stub
		
	}
	public void recupererScores() {
		// TODO Auto-generated method stub
		
	}
	public void recevoirMain(Cartes c) {
		// TODO Auto-generated method stub
		
	}
	public void direPliRemporté1(Carte[] pli, String joueur) {
		// TODO Auto-generated method stub
		
	}
	
}
