package fr.um2.projetl3.tarotandroid.connection;

import java.io.Serializable;
import java.util.Vector;

import fr.um2.projetl3.tarotandroid.jeu.Carte;

public class MessageObjet implements Serializable{

private static final long serialVersionUID = 356469922603334998L;
private int message;
private String compl;
 
 public MessageObjet(int mess)
 {
	 message = mess;
	 compl=null;
 }

 public MessageObjet(int mess, String c)
 {
	 message = mess;
	 compl = c;
 }
 
 public void setmessage(int mess ){
	 message = mess;
 }
 public int getmessage(){
	 return message;
 }
 public void setcomp(String c ){
	 compl = c;
 }
 public String getcompl(){
	 return compl;
 }
 
}


