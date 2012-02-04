package fr.um2.projetl3.tarotandroid.jeu;

public class Contrat {

		private NomDesContrats nom;
		private int score;
		// ? score Definit comme ça dans l'uml mais réellement utile ou à quoi cela correspond
		//! remplace par une mehtode voir plus bas private int poids;// IMPORTANCE DU CONTRAT 
		private boolean chienRevele;// on voit le chien ou pas ?
		private boolean chienPourAttaque;// le chien est pour l'attaque
		
		public Contrat()
		{
			this.nom = NomDesContrats.AucuneGarde;
		}
		
		public Contrat(NomDesContrats nom)
		{
			this.nom=nom;
		}
		
		public NomDesContrats getName() {
			return nom;
		}
		public void setName(NomDesContrats name) {
			this.nom = name;
		}
		public int getScore() {
			return score;
		}
		public void setScore(int score) {
			this.score = score;
		}
		public int getPoids() 
		{
			if (nom==NomDesContrats.AucuneGarde) return 0;
			if (nom==NomDesContrats.Pouce) return 1;
			if (nom==NomDesContrats.Petite) return 2;
			if (nom==NomDesContrats.Garde) return 3;
			if (nom==NomDesContrats.GardeSans) return 4;
			if (nom==NomDesContrats.GardeContre) return 5;
			return 0;
		}
/* remplace par une methode qui renvoit le poids en fonction du nom
* il faut avoir le poids par contrat quelquepart et ici me semble le
* plus intuitif, 
		public void setPoids(int poids) {
			this.poids = poids;
		}
		*/
		public boolean isChienRevele() {
			return chienRevele;
		}
		public void setChienRevele(boolean chienRevele) {
			this.chienRevele = chienRevele;
		}
		public boolean isChienPourAttaque() {
			return chienPourAttaque;
		}
		public void setChienPourAttaque(boolean chienPourAttaque) {
			this.chienPourAttaque = chienPourAttaque;
		}
		
		
}
