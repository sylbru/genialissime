package fr.um2.projetl3.tarotandroid.exceptions;

public class entreeNulleException extends Exception
{
	String err;
	public entreeNulleException(String endroitoulerreurcestproduite)
	{
	err = "le stream lu est vide , l'erreur s'est produite dans la fonction : "+endroitoulerreurcestproduite;

	}
	public void affiche()
	{
		System.out.println(err);
	}
}