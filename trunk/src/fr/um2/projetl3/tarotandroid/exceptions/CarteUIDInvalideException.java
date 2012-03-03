package fr.um2.projetl3.tarotandroid.exceptions;

public class CarteUIDInvalideException extends Exception
{
	public CarteUIDInvalideException()
	{
		System.out.println("Tentative de créer une Carte à partir d’un UID invalide");
	}
	
	public CarteUIDInvalideException(int uid)
	{
		System.out.println("Tentative de créer une Carte à partir d’un UID invalide : " + uid);
	}
}
