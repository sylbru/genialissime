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
import android.view.MotionEvent;
import android.view.View;
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
	TextView logT;

	public void makeToast(String s, boolean court)
	{
		final String rS = s;
		final boolean rCourt = court;
		runOnUiThread(new Runnable()
		{
			@Override
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
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ecran_jeu);

		// TextView pour log
		logT = (TextView)findViewById(R.id.log);
		
		ImageView imgV = new ImageView(this);
		try
		{
			imgV.setImageDrawable((new CarteGraphique(42)).mImageView.getDrawable());
		}
		catch (CarteUIDInvalideException e)
		{
			e.printStackTrace();
		}
		
		// RelativeLayout principal (plateau)
		RelativeLayout rl = (RelativeLayout) findViewById(R.id.mainLayout);
		LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		
		rl.addView(imgV, lp); // ajout d’une image
		setContentView(rl); // bizarre, on remplace tout le layout ecran_jeu par le RelativeLayout qui était dedans
							// à changer peut-être
		
		// Bouton pour lancer la partie (temporaire)
		Button bLancer = (Button)findViewById(R.id.bLancer);
		bLancer.setOnClickListener(new View.OnClickListener()
		{
			@Override
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
				
				// P.run(); // ← bloquant
				P.start();  // ← cool
			}
		});		
	}

	public Contrat resultatAnnonce;
	public Contrat demanderAnnonce(Contrat contrat)
	{
		resultatAnnonce = Contrat.AUCUN;
		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
						
				AlertDialog.Builder alert = new AlertDialog.Builder(EcranJeu.this);
			
				alert.setTitle("Annonce");
				
				// TODO: proposer uniquement les annonces valides (et construire automatiquement)
				// final CharSequence[] listeAnnonces = {"Passe", "Petite", "Garde", "Garde sans", "Garde contre"};
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
					@Override
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
							resultatAnnonce = null; // ou aucun ? ou Passe ?
						}
					}
				});
				alert.show();
			}
		});
		
		// On a lancé l’affichage de la boîte de dialogue, on attend la réponse
		while(resultatAnnonce == Contrat.AUCUN)
		{
			// ne rien faire ? Il faut juste attendre que resultatAnnonce soit différent de Contrat.AUCUN
		}
		System.out.println("On va retourner "+resultatAnnonce);
		return resultatAnnonce;
	}
	
	public void direMain(Vector<Carte> main)
	{
		String texteMain = "Main : ";
		for(Carte c: main)
		{
			texteMain += c.toString() + ", ";
		}
		makeToast(texteMain);
	}
	
	Handler h = new Handler();
	
	public void log(String s)
	{
		//System.out.println("Log : "+s);
		final String msg = s;
		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				System.out.println("Log : "+msg);
				logT.append((CharSequence)"\n"+msg);
			}
		});
	}
}
