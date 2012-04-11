package fr.um2.projetl3.tarotandroid.connection;
import static fr.um2.projetl3.tarotandroid.jeu.Context.P;

import java.net.*;
import java.io.*;

import fr.um2.projetl3.tarotandroid.clients.JoueurTexte;


public class MultiServeur extends Thread {
	
	ServerSocket serverSocket;
	boolean listening;
	int id = 0;

	
	public MultiServeur()
	{
		serverSocket = null;
		listening = true;
	}
	
	public void run()
	{
		System.out.println("Début run, création socket");
		try 
		{
			serverSocket = new ServerSocket(4444);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			System.err.println("Could not listen on port: 4444.");
	        System.exit(-1);
		}
		//P.setJoueur(0, new JoueurTexte("JT0", true));
		//P.setJoueur(1, new JoueurTexte("JT1", true));
		//P.setJoueur(2, new JoueurTexte("JT2", true));
		//int i =3;
		int i=0;
		while (i<P.getNombreDeJoueurs())
			// System.out.println("while sur P, i="+i);
			try {
				JoueurDistant  joueur = new JoueurDistant(serverSocket); 
				System.out.println("joueur numero "+i+" connectee (peut etre)");
				P.setJoueur(i, joueur);
				//System.out.println("nom thread");
				//server.envoyermessage(1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Fin run()");
	
	}
	public static void main(String args[])
	{
		MultiServeur server = new MultiServeur();
		while(true){
			server.run();
		}
	}
}