package fr.um2.projetl3.tarotandroid.jeu;

import fr.um2.projetl3.tarotandroid.exceptions.IdAtoutInvalideException;

public class CarteAtout extends Carte
{

	private Integer id;//? pourquoi un integer et pas un int 
	
	public Integer getId()
	{
		return id;
	}
	
	public void setId(int id) throws IdAtoutInvalideException 
	{
		if(id>=0 && id <=21)
		{
			this.id = id;
		}
		else throw new IdAtoutInvalideException();
		/**
		 * id represente l'atout en question
		 * 0 etant l'excuse
		 */
	}
	
	private boolean isBout()
	{
		switch(id)
		{
			case 0:  // Excuse
				return true;
			case 1:  // Petit
				return true;
			case 21: // … 21
				return true;
			default:
				return false;
		}
	}
	
	public CarteAtout()
	{
		// TODO Auto-generated constructor stub
	}

}
