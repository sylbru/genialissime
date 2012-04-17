package fr.um2.projetl3.tarotandroid.activities;

import static fr.um2.projetl3.tarotandroid.jeu.Context.P;

import java.util.Random;
import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import fr.um2.projetl3.tarotandroid.R;
import fr.um2.projetl3.tarotandroid.clients.IJoueur;
import fr.um2.projetl3.tarotandroid.clients.JoueurGraphique;
import fr.um2.projetl3.tarotandroid.clients.JoueurIA;
import fr.um2.projetl3.tarotandroid.exceptions.CarteUIDInvalideException;
import fr.um2.projetl3.tarotandroid.jeu.Annonces;
import fr.um2.projetl3.tarotandroid.jeu.Carte;
import fr.um2.projetl3.tarotandroid.jeu.CarteGraphique;
import fr.um2.projetl3.tarotandroid.jeu.Contrat;
import fr.um2.projetl3.tarotandroid.jeu.Partie;

public class EcranJeu extends Activity
{
	IJoueur moi, ia1, ia2, ia3;
	IJoueur moi1;
	TextView logT;
	RelativeLayout rl;
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ecran_jeu);

		// TextView pour log
		logT = (TextView)findViewById(R.id.log);
		
		ImageView imgV = new ImageView(this);
		/*try
		{
			imgV.setImageDrawable((new CarteGraphique(42)).mImageView.getDrawable());
		}
		catch (CarteUIDInvalideException e)
		{
			e.printStackTrace();
		}*/
		
		// RelativeLayout principal (plateau)
		rl = (RelativeLayout) findViewById(R.id.mainLayout);
		LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		
		rl.addView(imgV, lp); // ajout d’une image
		setContentView(rl); // bizarre, on remplace tout le layout ecran_jeu par le RelativeLayout qui était dedans
							// à changer peut-être 
							// Il est possible de le faire en xml et ce serait plus propre, non ?
		
		// Bouton pour lancer la partie (temporaire)
		Button bLancer = (Button)findViewById(R.id.bLancer);
		bLancer.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View view)
			{
				P = new Partie();
				moi = new JoueurGraphique("Moi", EcranJeu.this);
				ia1 = new JoueurIA("IA1", getResources().getXml(R.xml.intelligence), 42);
				ia2 = new JoueurIA("IA2", getResources().getXml(R.xml.intelligence), 42);
				ia3 = new JoueurIA("IA3", getResources().getXml(R.xml.intelligence), 42);
				P.setJoueur(0, moi);
				P.setJoueur(1, ia1);
				P.setJoueur(2, ia2);
				P.setJoueur(3, ia3);
				
				log("Lancement de la partie");
				
				// P.start() permet de lancer le thread, fait appel à P.run(), lequel fait appel à P.lancerPartie()
				P.start();
				//afficherMain(P.donne().getMain().getCartes());
			}
		});		
	}
	
	/**/
	public void afficherMain(final Vector<Carte> main)
	{
		runOnUiThread(new Runnable()
		{
			public void run()
			{
				for(int i = 1; (i < 26); ++i)
				{
					int imageViewId = 0;
					Carte card = (i >= main.size()) ? null : main.get(i);
					String imageViewIdName = "imageCarte"+Integer.toString(i);
			
					try {
						imageViewId = R.id.class.getDeclaredField(imageViewIdName).getInt(null);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchFieldException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
					ImageView imageView = (ImageView) findViewById(imageViewId);
			
					if (card == null) {
						imageView.setVisibility(View.GONE);
					} else {
						imageView.setVisibility(View.VISIBLE);
						// si visible on lui donne la bonne image....
						//imageView.setDrawableResource(card.getResource());
						try {
							imageView.setImageDrawable((new CarteGraphique(card.uid())).mImageView.getDrawable());
						} catch (CarteUIDInvalideException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		});
	}/**/

	private Contrat resultatAnnonce;
	private AlertDialog alerte = null;
	private void afficherDemandeAnnonce()
	{
		runOnUiThread(new Runnable()
		{
			
			public void run()
			{
				AlertDialog.Builder alert = new AlertDialog.Builder(EcranJeu.this);
				alert.setTitle("Annonce");
				
				Vector<Contrat> listeAnnoncesDispo = Contrat.getListeContratsDisponibles();
				Contrat contratMax = Annonces.getContratMax();
				Vector<CharSequence> listeAnnoncesCS = new Vector<CharSequence>();
				for(Contrat c: listeAnnoncesDispo)
				{
					if(c.getPoids() > contratMax.getPoids() || c == Contrat.PASSE)
					{
						listeAnnoncesCS.add((CharSequence)c.toString());
					}
				}
				
				final CharSequence[] listeAnnonces = new CharSequence[listeAnnoncesCS.size()];
				listeAnnoncesCS.toArray(listeAnnonces);
				alert.setSingleChoiceItems(listeAnnonces, -1, new DialogInterface.OnClickListener()
				{	
					
					public void onClick(DialogInterface arg0, int i)
					{
						makeToast((String)listeAnnonces[i]);
						if(listeAnnonces[i] == "Passe")
						{
							resultatAnnonce = Contrat.PASSE;
						}
						else if(listeAnnonces[i] == "Petite")
						{
							resultatAnnonce = Contrat.PETITE;
						}
						else if(listeAnnonces[i] == "Garde")
						{
							resultatAnnonce = Contrat.GARDE;
						}
						else if(listeAnnonces[i] == "Garde sans")
						{
							resultatAnnonce = Contrat.GARDE_SANS;
						}
						else if(listeAnnonces[i] == "Garde contre")
						{
							resultatAnnonce = Contrat.GARDE_CONTRE;
						}
						else
						{
							resultatAnnonce = Contrat.AUCUN; 
						}
						alerte.dismiss();
					}
				});
				alerte = alert.create();
				alerte.show();
			}
		});
	}
	public Contrat demanderAnnonce(Contrat contrat)
	{
		resultatAnnonce = Contrat.AUCUN;
		final Button bDemandeAnnonce = new Button(this);
		bDemandeAnnonce.setText("Annoncer");
		bDemandeAnnonce.setOnClickListener(new View.OnClickListener()
		{
			
			public void onClick(View v)
			{
				afficherDemandeAnnonce();
			}
		});
		final RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.ABOVE, R.id.horizontalScrollView1);
		//lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		
		runOnUiThread(new Runnable()
		{
			
			public void run()
			{
				rl.addView(bDemandeAnnonce, lp);
			}
		});
				
		while(resultatAnnonce == Contrat.AUCUN)
		{} // On attend que resultatAnnonce soit différent de Contrat.AUCUN
		
		runOnUiThread(new Runnable()
		{
			
			public void run()
			{
				rl.removeView(bDemandeAnnonce);
			}
		});
		
		// resultatAnnonce != Contrat.AUCUN
		System.out.println("On va retourner "+resultatAnnonce);
		return resultatAnnonce;
	}
	
	Carte resultatCarte;
	public void afficherDemandeCarte()
	{
		runOnUiThread(new Runnable()
		{
			
			public void run()
			{
				AlertDialog.Builder alert = new AlertDialog.Builder(EcranJeu.this);
				alert.setTitle("Jouer une carte");
				
				final Vector<Carte> cartesLegales = P.donne().indiquerCartesLegalesJoueur();
				Vector<CharSequence> vListeCartesLegalesCS = new Vector<CharSequence>();
				for(Carte c: cartesLegales)
				{
					vListeCartesLegalesCS.add(c.toStringShort());
				}
				
				final CharSequence[] listeCarteLegales = new CharSequence[vListeCartesLegalesCS.size()];
				vListeCartesLegalesCS.toArray(listeCarteLegales);
				alert.setSingleChoiceItems(listeCarteLegales, -1, new DialogInterface.OnClickListener()
				{	
					
					public void onClick(DialogInterface arg0, int i)
					{
						resultatCarte = cartesLegales.get(i);
					}
				});
				alert.show();
			}
		});
	
	}
	
	public Carte demanderCarte()
	{
		resultatCarte = null;
		final Button bDemandeCarte = new Button(this);
		bDemandeCarte.setText("Jouer une carte");
		bDemandeCarte.setOnClickListener(new View.OnClickListener()
		{
			
			public void onClick(View v)
			{
				afficherDemandeCarte();
			}
		});
		final RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		//lp.addRule(RelativeLayout.ABOVE, R.id.scrollView1);
		lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		
		runOnUiThread(new Runnable()
		{
			
			public void run()
			{
				rl.addView(bDemandeCarte, lp);
			}
		});
		// On a lancé l’affichage de la boîte de dialogue, on attend la réponse
		while(resultatCarte == null)
		{} // On attend.
		
		runOnUiThread(new Runnable()
		{
			
			public void run()
			{
				rl.removeView(bDemandeCarte);
			}
		});
		
		System.out.println("On va retourner "+resultatCarte);
		return resultatCarte;
	}
	
	public void direMain(Vector<Carte> main)
	{
		String texteMain = "Main : ";
		for(Carte c: main)
		{
			texteMain += c.toStringShort() + ", ";
		}
		// makeToast(texteMain);
		log(texteMain);
		afficherMain(main);
	}

	public void makeToast(String s, boolean court)
	{
		final String rS = s;
		final boolean rCourt = court;
		runOnUiThread(new Runnable()
		{
			
			public void run()
			{
				Toast.makeText(EcranJeu.this, rS, (rCourt ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG)).show();
				log("Toast : " + rS);				
			}	
		});
	}
	
	public void makeToast(String s)
	{
		makeToast(s, false);
	}
	
	public void log(String s)
	{
		//System.out.println("Log : "+s);
		final String msg = s;
		runOnUiThread(new Runnable()
		{
			
			public void run()
			{
				System.out.println("Log : "+msg);
				logT.append((CharSequence)"\n"+msg);
			}
		});
	}

}
