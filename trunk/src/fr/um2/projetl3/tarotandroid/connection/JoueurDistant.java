package fr.um2.projetl3.tarotandroid.connection;

import java.io.IOException;
import java.net.*;

import fr.um2.projetl3.tarotandroid.clients.IJoueur;
import fr.um2.projetl3.tarotandroid.connection.Cartes;
import fr.um2.projetl3.tarotandroid.jeu.Carte;
import fr.um2.projetl3.tarotandroid.jeu.Contrat;
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
	public void recevoirMain(Cartes c){}
	
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
		catch (entreeNulleException e)
		{
			e.affiche();
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
}
