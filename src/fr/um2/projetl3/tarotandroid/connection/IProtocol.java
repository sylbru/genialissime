package fr.um2.projetl3.tarotandroid.connection;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectInputStream;

public interface IProtocol {
    static final int ERREUR = -1;
    static final int INITIALISATION = 0;
    static final int ATTENTEMAIN = 1;
    static final int ATTENTE = 1;
    static final int ATTENTECARTE = 2;
    static final int ATTENTECARTES = 2;
    static final int ENVOICARTEJEU = 3;
    static final int ENVOICARTESECART = 4;
    static final int ATTENTSCORE = 5;
    static final int JOUEURGANGNANT = 6;
    static final int JOUEURPERDANT = 7;
    static final int FINPARTIE = 8;
    static final int DEMANDERCARTE = 9;

    
    public Object traiterEntreeDonnes(ObjectInputStream entree);
}