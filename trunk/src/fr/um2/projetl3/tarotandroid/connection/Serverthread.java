package fr.um2.projetl3.tarotandroid.connection;

import java.net.*;
import java.io.*;

public class Serverthread extends Thread {
    private Socket socket = null;
	private BufferedOutputStream out;
	private BufferedInputStream in;
	private int message;

    public Serverthread(Socket socket) {
	super("ServerThread");
	this.socket = socket;
    }

    public void run() // il semble que la methode doit etre appele run autremend Serverthread(serverSocket.accept()).start(); ne marche pas parcque la methode start fait appel uniquement a une methode run
    {
    		try
    		{
    			System.out.println("kennt en hei un ?");
    			out = new BufferedOutputStream(socket.getOutputStream());
    			out.flush();
    			in = new BufferedInputStream(socket.getInputStream());

    			message = 1;
    			sendMessage();
    			System.out.println("message gescheckt");
    		} catch (IOException e) {
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
    			out.write(message);
    			out.flush();
    			System.out.println("server>" + message);
    		}
    		catch(IOException ioException){
    			ioException.printStackTrace();
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