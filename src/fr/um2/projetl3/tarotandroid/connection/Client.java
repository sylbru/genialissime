package fr.um2.projetl3.tarotandroid.connection;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import fr.um2.projetl3.tarotandroid.jeu.Carte;


public class Client 
{
	Socket socket = null;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String messagederreur;
	private MessageObjet message;
	private String host = "localhost";
	private int port = 4444;
	private boolean interompu = false;
	private Carte c;
	
	Client(){}
	
	void lancer()
	{
		try
		{

			socket = new Socket(host, port);
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(socket.getInputStream());
			

			do
			{
				//System.out.println(in.available());
				message = (MessageObjet) in.readObject();
				traitermessage();
				c.affiche();
				out.writeObject(c);

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
			System.out.println("eurreur : "+messagederreur);
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
	
	private void traitermessage()
	{
		switch (message.getmessage()) 
		{
			case -1: 
			{
				messagederreur = "rien a lire";
				System.out.println("rien a lire");
				interompu = true;
				break;
			}
			case 1: 
			{
				System.out.println("demander Carte");
				System.out.println(message.getcompl());
				//demandeCarte();
				c = new Carte(8);
				break;
			}
			case 2:
			{
				//demanderCarteEcart();
				break;
			} 	
			case 5:
			{
				System.out.println("le thread a envoie un message");
				//demanderCarteEcart();
				break;
			} 	
			default :
			{
				messagederreur = "commande inconnue";
				interompu = true;
				break;
			}
		}	
	}
	
	public static void main(String args[])
	{
		Client client = new Client();
		client.lancer();
	}
}
