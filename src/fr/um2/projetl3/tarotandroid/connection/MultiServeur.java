package fr.um2.projetl3.tarotandroid.connection;


import java.net.*;
import java.io.*;

public class MultiServeur {
	
	ServerSocket serverSocket;
	boolean listening;

	
	MultiServeur()
	{
		serverSocket = null;
		listening = true;
	}
	
	void lancer()
	{
		try 
		{
			serverSocket = new ServerSocket(4444);
		} 
		catch (IOException e) 
		{
			System.err.println("Could not listen on port: 4444.");
	        System.exit(-1);
		}
	
		while (listening)
			try {
				System.out.println("vierum thread");
				new Serverthread(serverSocket.accept()).start();
				System.out.println("nom thread");
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
	
	}
	public static void main(String args[])
	{
		MultiServeur server = new MultiServeur();
		while(true){
			server.lancer();
		}
	}
}