package fr.um2.projetl3.tarotandroid.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import static fr.um2.projetl3.tarotandroid.connection.Context.client;

import fr.um2.projetl3.tarotandroid.connection.Client;
import fr.um2.projetl3.tarotandroid.jeu.Carte;
import fr.um2.projetl3.tarotandroid.jeu.Main;


public class ProtocolClient implements IProtocol
{
	private int etat;
	private boolean initialisé;
	private MessageObjet message;
	private String messagederreur;
	
	
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
        	Cartes main = null;
			try 
			{
				main = (Cartes) entree.readObject();
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
        	Client.joueur.recevoirMain(main);
        	etat= ATTENTE;
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
}
