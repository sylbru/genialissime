package fr.um2.projetl3.tarotandroid.connection;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;


public class Client 
{
	Socket socket = null;
	private BufferedOutputStream out;
	private BufferedInputStream in;
	private String messagederreur;
	private int message;
	private String host = "localhost";
	private int port = 4444;
	private boolean interompu = false;
	
	Client(){}
	
	void lancer()
	{
		try
		{

			socket = new Socket(host, port);
			out = new BufferedOutputStream(socket.getOutputStream());
			out.flush();
			in = new BufferedInputStream(socket.getInputStream());
			

			do
			{
				System.out.println(in.available());
				message = in.read();
				traitermessage();
			
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
		switch (message) 
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
				//demandeCarte();
				break;
			}
			case 2:
			{
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
