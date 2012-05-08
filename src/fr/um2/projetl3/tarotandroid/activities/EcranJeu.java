package fr.um2.projetl3.tarotandroid.activities;

import static fr.um2.projetl3.tarotandroid.jeu.Context.P;
import static fr.um2.projetl3.tarotandroid.activities.Contexts.*;

import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.text.Html.TagHandler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
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
import fr.um2.projetl3.tarotandroid.jeu.PrefsRegles;

public class EcranJeu extends Activity
{
	IJoueur moi, ia1, ia2, ia3;
	IJoueur moi1;
	TextView logT;
	ScrollView logSV;
	RelativeLayout rl;
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ecran_jeu);

		// TextView pour log
		logT = (TextView)findViewById(R.id.log);
		logSV = (ScrollView)findViewById(R.id.logSV);
		
		// RelativeLayout principal (plateau)
		rl = (RelativeLayout) findViewById(R.id.mainLayout);		

		// Lancement de la partie
		PrefsRegles.sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		P = new Partie();
		moi = new JoueurGraphique("Moi", EcranJeu.this);
		ia1 = new JoueurIA("IA1", 42);
		ia2 = new JoueurIA("IA2", 43);
		ia3 = new JoueurIA("IA3", 44);
		P.setJoueur(0, moi);
		P.setJoueur(1, ia1);
		P.setJoueur(2, ia2);
		P.setJoueur(3, ia3);
		
		// P.start() permet de lancer le thread, fait appel à P.run(), lequel fait appel à P.lancerPartie()
		P.start();
	}
	
	/**/
	public void afficherMain(final Vector<Carte> main)
	{
		//final Vector<Carte> cartesLegales = P.donne().indiquerCartesLegalesJoueur();
		runOnUiThread(new Runnable()
		{
			public void run()
			{
				for(int i = 0; (i < 26); ++i)
				{
					int imageViewId = 0;
					Carte card = (i >= main.size()) ? null : main.get(i);
					String imageViewIdName = "imageCarte"+Integer.toString(i);
					//Carte cardL = (i >= cartesLegales.size()) ? null : main.get(i);
					//System.out.println(cardL + "est une carte legale!");
			
					try {
						imageViewId = R.id.class.getDeclaredField(imageViewIdName).getInt(null);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (NoSuchFieldException e) {
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
	private boolean ecartOK;
	private AlertDialog alerte = null;
	private void afficherDemandeAnnonce()
	{
		runOnUiThread(new Runnable()
		{
			public void run()
			{
				AlertDialog.Builder alert = new AlertDialog.Builder(EcranJeu.this);
				alert.setTitle("Annonce");
				
				Log.d(TAG, "Passage dans afficherDemandeAnnonce");
				
				Vector<Contrat> listeAnnoncesDispo = (Vector<Contrat>) Contrat.getListeContratsDisponibles().clone();
				Log.d(TAG, "dispos : "+listeAnnoncesDispo.toString());
				Contrat contratMax = Annonces.getContratMax();
				Vector<CharSequence> listeAnnoncesCS = new Vector<CharSequence>();
				for(Contrat c: listeAnnoncesDispo)
				{
					if(c.getPoids() > contratMax.getPoids() || c == Contrat.PASSE)
					{
						listeAnnoncesCS.add((CharSequence)c.toString());
					}
				}
				Log.d(TAG, "autorisés : "+listeAnnoncesCS.toString());
				
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
		lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		
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
	
	public Vector<Carte> resultatEcart = new Vector<Carte>();
	public Vector<Carte> demanderEcart()
	{
		resultatEcart.clear();
		ecartOK = false;
		final Vector<Carte> cartesLegalesEcart = P.donne().indiquerCartesLegalesEcart();
		checked = new boolean[cartesLegalesEcart.size()];
		Arrays.fill(checked, false);
				
		final Button bDemandeEcart = new Button(this);
		bDemandeEcart.setText("Faire son écart");
		bDemandeEcart.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				afficherDemandeEcart(cartesLegalesEcart);
			}
		});
		final Button bValideEcart = new Button(this);
		bValideEcart.setText("Valider");
		bValideEcart.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				if(resultatEcart.size() == P.getnombreDeCartesPourLeChien())
				{
					ecartOK = true;
					
				}
				else
					makeToast("Il faut "+P.getnombreDeCartesPourLeChien()+" cartes dans le chien, pas "+resultatEcart.size()+".");
			}
		});
		
		final LinearLayout llEcart = new LinearLayout(this);
		final RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.ABOVE, R.id.horizontalScrollView1);
		lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		
		runOnUiThread(new Runnable()
		{
			public void run()
			{
				rl.addView(llEcart, lp);
				llEcart.addView(bDemandeEcart);
				llEcart.addView(bValideEcart);
			}
		});
				
		while(!ecartOK)
		{}
		
		runOnUiThread(new Runnable()
		{
			public void run()
			{
				rl.removeView(llEcart);
			}
		});
		
		return resultatEcart;
	}
	
	boolean[] checked;

	public void afficherDemandeEcart(final Vector<Carte> cartesLegalesEcart)
	{
		runOnUiThread(new Runnable()
		{
			public void run()
			{
				AlertDialog.Builder alert = new AlertDialog.Builder(EcranJeu.this);
				alert.setTitle("Faire son écart");
				
				Vector<CharSequence> vListeCartesLegalesCS = new Vector<CharSequence>();
				for(Carte c: cartesLegalesEcart)
				{
					vListeCartesLegalesCS.add(c.toStringShort());
				}
				
				final CharSequence[] listeCartesLegalesCS = new CharSequence[vListeCartesLegalesCS.size()];
				vListeCartesLegalesCS.toArray(listeCartesLegalesCS);
				
				alert.setMultiChoiceItems(listeCartesLegalesCS, checked, new DialogInterface.OnMultiChoiceClickListener()
				{	
					public void onClick(DialogInterface arg0, int i, boolean b)
					{
						if(!b) // décochage
						{
							for(Carte c: resultatEcart)
							{
								if(c.uid() == cartesLegalesEcart.get(i).uid())
								{
									resultatEcart.remove(c);
									checked[i] = false;
									break;
								}
							}
						}
						else // cochage
						{
							resultatEcart.add(cartesLegalesEcart.get(i));
							checked[i] = true;
						}
					}
				});
				alerte = alert.create();
				alerte.show();
			}
		});
	}
	
	private Carte resultatCarte;
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
	                    alerte.dismiss();
	                }
	            });
	            alerte = alert.create();
            alerte.show();
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
		lp.addRule(RelativeLayout.ABOVE, R.id.horizontalScrollView1);
		lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		
		afficherMain(P.donne().indiquerCartesLegalesJoueur());
		runOnUiThread(new Runnable()
		{
			
			public void run()
			{
				rl.addView(bDemandeCarte, lp);
			}
		});
		
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
		//afficherMain(P.donne().indiquerCartesLegalesJoueur());
		return resultatCarte;
	}
	
	public void direMain(Vector<Carte> main)
	{
		/* *
		String texteMain = "Main : ";
		for(Carte c: main)
		{
			texteMain += c.toStringShort() + ", ";
		}
		makeToast(texteMain);
		log(texteMain); /**/
		afficherMain(main);
	}
	
	public void direAnnonce(final Contrat c, int j)
	{
		final TextView tvAnnonce;
		boolean ok = true;
		switch(j)
		{
		case 0:
			tvAnnonce = (TextView) findViewById(R.id.annonceS);
			break;
		case 1:
			tvAnnonce = (TextView) findViewById(R.id.annonceO);
			break;
		case 2:
			tvAnnonce = (TextView) findViewById(R.id.annonceN);
			break;
		case 3:
			tvAnnonce = (TextView) findViewById(R.id.annonceE);
			break;
		default:
			log("Annonce de joueur inconnu "+j);
			tvAnnonce = null;
			ok = false;
		}
		
		if(ok)
		{
			hh.post(new Runnable()
			{
				public void run()
				{
					tvAnnonce.setText(c.toString());
				}
			});
		}
	}

	Handler hh = new Handler();
	
	public void makeToast(String s, boolean court)
	{
		final String rS = s;
		final boolean rCourt = court;
		runOnUiThread(new Runnable()
		{
			
			public void run()
			{
				Toast.makeText(EcranJeu.this, rS, (rCourt ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG)).show();
				// log("Toast : " + rS);				
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
				Log.d("Genialissime","Log : "+msg);
				logT.append((CharSequence)"\n"+msg);
				logSV.fullScroll(ScrollView.FOCUS_DOWN);
				
			}
		});
	}


}
