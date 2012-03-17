package fr.um2.projetl3.tarotandroid.connection;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import static fr.um2.projetl3.tarotandroid.jeu.Context.P;
import static fr.um2.projetl3.tarotandroid.jeu.Context.D;

import fr.um2.projetl3.tarotandroid.jeu.Carte;
import fr.um2.projetl3.tarotandroid.jeu.Main;

public class ProtocolServeur implements IProtocol
{
	private static final int ENVOICARTES = 0;
	private int etat;
	private boolean initialisé;
	
	
    ProtocolServeur()
    {
    	{
    		etat= ENVOICARTES;
    		initialisé = false;
    	}
    }


    public ObjectOutputStream traiterEntreeDonnes(ObjectInputStream entree)
    {
        ObjectOutputStream sortie = null;

        if(etat == INITIALISATION)
        {
        	Cartes main = null;
        	Main m = D.getMain(Serverthread.id);
        	main.set(m.getCartes());
			try 
			{
				sortie.writeObject(main);
			}
			catch (OptionalDataException e) 
			{
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
        	etat= DEMANDERCARTE ;
        }
/*        else if (etat == ATTENTE) 
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
        */
        else if(etat == ATTENTECARTE)
        {
        	Carte c = null;
			try {
				c = (Carte) entree.readObject();
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
        	c.affiche();
        }
       /* 
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
        */
        else if(etat == DEMANDERCARTE)
        {
        	MessageObjet message = new MessageObjet( 1, "premier envoi");
        	try 
        	{
				sortie.writeObject(message);
			} 
        	catch (IOException e) 
			{
				e.printStackTrace();
			}
        	etat = ATTENTECARTE;
        }
        return sortie;
    }
}