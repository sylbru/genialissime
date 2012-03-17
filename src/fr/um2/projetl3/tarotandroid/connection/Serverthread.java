package fr.um2.projetl3.tarotandroid.connection;

import java.net.*;
import java.io.*;

import fr.um2.projetl3.tarotandroid.jeu.Carte;

public class Serverthread extends Thread {
    private Socket socket = null;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private MessageObjet message;
	private int id;
	private Carte carte;
	

    Serverthread(Socket socket, int id) {
	super("ServerThread");
	this.socket = socket;
	this.id = id;
    }

    public void run() // il semble que la methode doit etre appele run autremend Serverthread(serverSocket.accept()).start(); ne marche pas parcque la methode start fait appel uniquement a une methode run
    {
    		try
    		{
    			System.out.println("kennt en hei un ?");
    			out = new ObjectOutputStream(socket.getOutputStream());
    			out.flush();
    			in = new ObjectInputStream(socket.getInputStream());

    			message = new MessageObjet( 1, "premier envoi");
    			sendMessage();
    			carte =(Carte) in.readObject();
    			System.out.print("message recu par le thread id ="+id+" carte : ");carte.affiche();
    			System.out.println("message gescheckt");
    		} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println("Classe non trouve");
				e.printStackTrace();
			}
    		finally{
    			//4: Closing connection
    			try{
    				in.close();
    				out.close();
    				System.out.println("hei misst en lo zoumaan");
    				socket.close();
    			}
    			catch(IOException ioException){
    				ioException.printStackTrace();
    			}
    		}
    	}
    	void sendMessage()
    	{
    		try{
    			out.writeObject(message);
    			out.flush();
    			//System.out.println("server>" + message);
    		}
    		catch(IOException ioException){
    			ioException.printStackTrace();
    		}
    	}
    	/*
    	 * envoier message sert a envoier des message au joueurs a partir du jeu
    	 * ces message ne sont pas supposé engendrer une reponse si l'id vaut 5 (tous les joueuers)
    	 * par exemple :	- envoier le chien au joueurs pour l'affiche
    	 * 				 	- envoier le score pour l'afficher
    	 * 					- informer les joueurs si une personne a quitte la partie 
    	 * 					- etc
    	 * 
    	 * sinon on envoie le message uniquement au joueur correstpondant a l'id 
    	 */
    	public void envoyermessage(int id)
    	{
    		if(id==this.id || id == 5)
    		{
        		try
        		{
        			out.write(1);
        			out.flush();
        			System.out.println("server>" + 1);
        		}
        		catch(IOException ioException){
        			ioException.printStackTrace();
        		}
    		}
    	}
    	
    	
}  	
    	//*******************************************************************************
    	//*******************************************************************************
    	
    	//*******************************************************************************
    	//*******************************************************************************
    	
    	//*******************************************************************************
    	
    	
 /*   	
	try {
	    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	    BufferedReader in = new BufferedReader(
				    new InputStreamReader(
				    socket.getInputStream()));

	    String inputLine, outputLine;
	    KnockKnockProtocol kkp = new KnockKnockProtocol();
	    outputLine = kkp.processInput(null);
	    out.println(outputLine);

	    while ((inputLine = in.readLine()) != null) {
		outputLine = kkp.processInput(inputLine);
		out.println(outputLine);
		if (outputLine.equals("Bye"))
		    break;
	    }
	    out.close();
	    in.close();
	    socket.close();

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
*/