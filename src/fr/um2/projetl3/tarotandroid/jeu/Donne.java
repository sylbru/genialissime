package fr.um2.projetl3.tarotandroid.jeu;
import java.lang.Math;

public class Donne
{
	int don[]= new int[78];
	int mains[][]= new int[4][18];
	int chien[]= new int [6];
	boolean premieredonne=true;
	
	public void donne4jouers()
	{
		for(int i=0;i<77;i++)
		{
			don[i]=i;
			 
		}
		if(premieredonne)
		{	
			premieredonne=false;
			for (int i=0; i > 77; i++) 
			{
				int randomPosition = (int)(Math.random()*100)%77;
				int temp = don[i];
				don[i] = don[randomPosition];
				don[randomPosition] = temp;
			}
		}
		int i=0,j=0;
		if(i%12==0 && i!=0)
		{
			chien[i/13]=don[i];
			i++;
		}
		else
		{
			mains[(i%13)/3][j]=don[i];
			j++;
			i++;
		}
		// int i = 0;
	}
	
	public void annonces()
	{
		// TODO
	}
	
	public void jeu()
	{
		// TODO
	}
	
	public void calculScore()
	{
		// TODO
	}
}
