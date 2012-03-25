package fr.um2.projetl3.tarotandroid.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import static fr.um2.projetl3.tarotandroid.connection.Context.client;
import static fr.um2.projetl3.tarotandroid.jeu.Context.D;

import fr.um2.projetl3.tarotandroid.connection.Client;
import fr.um2.projetl3.tarotandroid.jeu.Carte;
import fr.um2.projetl3.tarotandroid.jeu.Main;


public class ProtocolClient implements IProtocol
{
	private int etat;
	private boolean initialisé;
	private MessageObjet message;
	private String messagederreur;
	private int requetteJoueur;
	
	
    ProtocolClient()
    {
    	{
    		etat= INITIALISATION;
    		initialisé = false;
    		message = null;
    	}
    }


    public ObjectOutputStream traiterEntreeDonnes(ObjectInputStream entree)
    {
        ObjectOutputStream sortie = null;

        if(etat == INITIALISATION)
        {
        	MessageObjet mess = null;
			try 
			{
				mess = (MessageObjet) entree.readObject();
				etat = mess.getmessage();
				mess = new MessageObjet(etat, "message recu");
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
        else if (etat == ATTENTEMAIN)
        {
        	Cartes c = null;
        	try {
				c = (Cartes) entree.readObject();
				Main m = new Main (c);
				Client.joueur.direMain(m.getCartes());
	        	MessageObjet mess = new MessageObjet(2, "cartes recus");				
			} catch (OptionalDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	etat = ATTENTE;
        }
        else if (etat == ATTENTE) 
        {
            while(true)
            {
            	//on regarde si il y a quelquechose en entree
            	try 
            	{
					if(entree.available()!=0)
					{
					    message = (MessageObjet) entree.readObject();
					    etat = traitermessage();
					}
					if(requetteJoueur!=0)
					{
						message.setmessage(requetteJoueur);
						sortie.writeObject(message);
						message = (MessageObjet) entree.readObject();
						System.out.println(message.getcompl());
						reponserequette(requetteJoueur,entree);
						requetteJoueur = 0;
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
        else if(etat == ATTENTECARTES ){}
        
        else if(etat == ENVOICARTEJEU) 
        {
			System.out.println(message.getcompl());
        	Carte c = Client.joueur.demanderCarte();
			try 
			{
				sortie.writeObject(c);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
        }
        else if(etat == ENVOICARTESECART){}
        else if(etat == ATTENTSCORE ){}
        else if(etat == JOUEURGANGNANT ){}
        else if(etat == JOUEURPERDANT){}
        else if(etat == FINPARTIE){}
   
        return sortie;
    }

	private void reponserequette(int req, ObjectInputStream entree) {
		MessageObjet mess;
		if(req==1)
		{

			Cartes c = null;
			try {
				c =(Cartes) entree.readObject();
			} catch (OptionalDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//c.afficherpli(); //! il faudra faire le fonction de demande
			
 		}
		else if(req==2)
		{

 		}
		else if(req==2)
		{

 		}
	}


	private int traitermessage()
	{
		switch (message.getmessage()) 
		{
			case -1: 
			{
				messagederreur = "rien a lire";
				System.out.println("rien a lire");
				client.interompre();
				return ERREUR;
			}
			case 1: 
			{
				System.out.println(message.getcompl());
				return ENVOICARTEJEU;

			}
			case 2:
			{
				//demanderCartesEcart();
				return ENVOICARTESECART;
			} 	
			default :
			{
				messagederreur = "commande inconnue";
				client.interompre();
				return ERREUR;
			}
		}	
	}
	public void setRequetteJoueur(int i)
	{
		requetteJoueur = i;
	}
}
