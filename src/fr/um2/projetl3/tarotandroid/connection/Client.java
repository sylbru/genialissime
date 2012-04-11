package fr.um2.projetl3.tarotandroid.connection;
import java.io.IOException;
import java.net.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import android.net.*;

import static fr.um2.projetl3.tarotandroid.connection.Context.client;
import fr.um2.projetl3.tarotandroid.clients.JoueurTexte;
import fr.um2.projetl3.tarotandroid.jeu.Carte;


public class Client 
{
	Socket socket = null;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String messagederreur;
	private MessageObjet message;
	private String host = "162.38.121.192";
	private int port = 4444;
	private boolean interompu = false;
	private Carte c;
	public static JoueurTexte joueur;
	
	public Client()
	{
		joueur = new JoueurTexte("LUC");	
		try {
			socket = new Socket(host, port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void lancer()
	{
		try
		{


			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(socket.getInputStream());
			
			ProtocolClient pc = new ProtocolClient();

			do
			{
				out  = pc.traiterEntreeDonnes(in);
				out.flush();
			
			}while(!interompu);
			
		} 
		catch (UnknownHostException e) 
		{
			messagederreur = "UnknownHostException";
		}
		catch (ConnectException e) 
		{
			messagederreur = String.format("ConnectException: %s", e.getMessage());
		}
		catch (IOException e) 
		{
			messagederreur = String.format("IOException: %s", e.getMessage());
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			messagederreur = "erreur inconnue";
		} 
		finally 
		{
			System.out.println("messagess : "+messagederreur);
			try 
			{
				if (socket != null)
				{
					socket.close();
					in.close();
					out.close();
				}
				socket = null;
			} catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public JoueurTexte getJoueur()
	{
		return joueur;
	}
	public void interompre()
	{
		interompu = true;
	}
	
	public static void main(String args[])
	{
		client = new Client();
		client.lancer();
	}
}
