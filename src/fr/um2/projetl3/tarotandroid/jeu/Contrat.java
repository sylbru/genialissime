package fr.um2.projetl3.tarotandroid.jeu;

public class Contrat {

		private NomDesContrats nom;
		private int score;
		// ? score Definit comme ça dans l'uml mais réellement utile ou à quoi cela correspond
		private int poids;// IMPORTANCE DU CONTRAT 
		private boolean chienRevele;// on voit le chien ou pas ?
		private boolean chienPourAttaque;// le chien est pour l'attaque
		
		
		
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
		public int getPoids() {
			return poids;
		}
		public void setPoids(int poids) {
			this.poids = poids;
		}
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
